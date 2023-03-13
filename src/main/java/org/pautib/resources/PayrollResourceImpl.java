package org.pautib.resources;

import org.pautib.services.PayrollService;

import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.container.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Path("/v1/payroll")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class PayrollResourceImpl implements PayrollResource {

    @Resource
    private ManagedExecutorService managedExecutorService;

    @Inject
    private PayrollService payrollService;

    @POST
    @Path("/run")
    public void run(@Suspended AsyncResponse asyncResponse) {

        final String currentThread = Thread.currentThread().getName();
        asyncResponse.setTimeout(5000, TimeUnit.SECONDS);
        asyncResponse.setTimeoutHandler( aR -> {
            aR.resume(Response.status(Response.Status.REQUEST_TIMEOUT)
                    .entity("Sorry, the request timed out. Please try again!").build()
            );
        });

        asyncResponse.register(CompletionCallbackHandler.class);

        managedExecutorService.submit( () -> {

            final String spawnedThreadName = Thread.currentThread().getName();

            payrollService.computePayroll();
            asyncResponse
                    .resume(
                            Response.ok()
                                    .header("Original Thread", currentThread)
                                    .header("Spawned Thread", spawnedThreadName)
                                    .status(Response.Status.OK).build()
                    );
        });

    }

    static class CompletionCallbackHandler implements CompletionCallback {

        @Override
        public void onComplete(Throwable throwable) {

        }
    }

    static class ConnectionCallbackHandler implements ConnectionCallback {

        @Override
        public void onDisconnect(AsyncResponse asyncResponse) {

        }
    }

    @POST
    @Path("run-cf")
    public void computePayrollCF(@Suspended AsyncResponse asyncResponse, @QueryParam("i") @DefaultValue("3") long number) {
        CompletableFuture.runAsync(() -> payrollService.fibonacci(number), managedExecutorService)
                .thenApply(result -> asyncResponse.resume(Response.ok(result).build()));
    }

}
