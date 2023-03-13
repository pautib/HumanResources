package org.pautib;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.pautib.entities.AbstractEntity;
import org.pautib.entities.Employee;
import org.pautib.resources.EmployeeResource;
import org.pautib.resources.EmployeeResourceImpl;
import org.pautib.services.PersistenceService;
import org.pautib.services.QueryService;
import org.pautib.services.QueryServiceImpl;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import javax.ws.rs.Path;
import javax.ws.rs.core.*;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(QueryServiceImpl.class)
public class MockingStaticMethodTest {
    @Mock
    QueryService queryService;
    @Mock
    PersistenceService pService;
    @Mock
    Response response;

    @Mock
    UriInfo uriInfo;

    @InjectMocks
    EmployeeResource eResource = new EmployeeResourceImpl();

    public MockingStaticMethodTest() throws URISyntaxException { }

    @Test
    public void testQueryStaticMethod_usingPowerMock() {

        List<MediaType> acceptMediaTypes = new ArrayList<>();
        acceptMediaTypes.add(MediaType.APPLICATION_JSON_TYPE);

        PowerMockito.mockStatic(QueryServiceImpl.class);
        Employee e = new Employee("Pau Torres", "38875513Q", new Date(), new BigDecimal(2000), new Date());

        // when
        when(response.getStatus()).thenReturn(Response.Status.OK.getStatusCode());
        when(QueryServiceImpl.getDummyStaticValue()).thenReturn(4);
        when(queryService.findEmployeeById(anyLong())).thenReturn(e);
        doNothing().when(pService).deleteEntity(e);

        //then
        eResource.terminateEmployeeById(1L);
        verify(pService, times(1)).deleteEntity(e);

    }

    @Test
    public void testCreateEmployee_usingPowerMock() throws Exception {

        Employee e = new Employee("Pau Torres", "38875513Q", new Date(), new BigDecimal(2000), new Date());

        URI uri = new URI("test");
        UriBuilder baseUriBuilder = UriBuilder.fromUri(uri);

        Whitebox.invokeMethod(eResource, "validateDepartmentIfProvided", e);
        Whitebox.invokeMethod(eResource, "validateManagerIfProvided", e);
        Whitebox.invokeMethod(eResource, "validateIfNIEDuplicated", e);

        when(response.getStatus()).thenReturn(Response.Status.CREATED.getStatusCode());
        when(response.readEntity(Matchers.any(Class.class))).thenReturn(URI.class);
        when(response.readEntity(Matchers.any(GenericType.class))).thenReturn(URI.class);

        when(uriInfo.getBaseUriBuilder()).thenReturn(baseUriBuilder);
        when(uriInfo.getAbsolutePathBuilder()).thenReturn(baseUriBuilder);
        when(uriInfo
                .getBaseUriBuilder()
                .path(String.valueOf(any(EmployeeResource.class))))
        .thenReturn(baseUriBuilder);
        when(uriInfo
                .getBaseUriBuilder()
                .path(String.valueOf(any(EmployeeResource.class)))
                .path(EmployeeResource.class, "getEmployees"))
        .thenReturn(baseUriBuilder);

        doAnswer(invocationOnMock -> { e.setId(1L); return null; }).when(pService).saveEmployee(e);

        eResource.createEmployee(e);
    }



}
