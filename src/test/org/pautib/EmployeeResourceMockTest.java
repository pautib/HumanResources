package org.pautib;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.runners.MockitoJUnitRunner;
import org.pautib.entities.Employee;
import org.pautib.resources.EmployeeResource;
import org.pautib.resources.EmployeeResourceImpl;
import org.pautib.services.PersistenceService;
import org.pautib.services.QueryService;

import javax.persistence.NoResultException;
import javax.ws.rs.core.*;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.logging.Logger;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.any;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;


public class EmployeeResourceMockTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock HttpHeaders httpHeaders;
    @Mock Request request;
    @Mock Response response;
    @Mock QueryService queryService;

    @Mock
    PersistenceService pService;

    @Mock
    UriInfo uriInfo;

    @Mock Logger logger;
    @InjectMocks
    @Spy
    EmployeeResource eResource = new EmployeeResourceImpl();

    @Captor
    ArgumentCaptor<HttpHeaders> headerArgumentCaptor;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetEmployees() {

        Employee e1 = new Employee("Pau Torres", "38875513Q", new Date(), new BigDecimal(2000), new Date());
        Employee e2 = new Employee("Alex Torres", "28875514R", new Date(), new BigDecimal(1500), new Date());
        Employee e3 = new Employee("Ana Bravo", "38575113A", new Date(), new BigDecimal(3000), new Date());

        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(e1);
        employeeList.add(e2);
        employeeList.add(e3);

        List<MediaType> acceptMediaTypes = new ArrayList<>();
        acceptMediaTypes.add(MediaType.APPLICATION_JSON_TYPE);

        when(queryService.getEmployees()).thenReturn(employeeList);
        when(httpHeaders.getAcceptableMediaTypes()).thenReturn(acceptMediaTypes);

        eResource.getEmployees(httpHeaders);

        // Error aquí
        then(eResource).should(atMost(1)).getEmployees(headerArgumentCaptor.capture());

        assertThat(headerArgumentCaptor.getValue(), is(httpHeaders));
        assertThat(acceptMediaTypes, hasItem(MediaType.APPLICATION_JSON_TYPE));

    }


    @Test(expected = NoResultException.class)
    public void testGetEmployeeById() {

        Employee e1 = new Employee("Pau Torres", "38875513Q", new Date(), new BigDecimal(2000), new Date());
        long id0 = 0L;
        long id1 = 1L;

        // Given
        given(queryService.findEmployeeById(Matchers.anyLong())).willReturn(e1);
        given(queryService.findEmployeeById(0L)).willReturn(null);

        // When
        //when(request.evaluatePreconditions(Matchers.any(EntityTag.class))).thenReturn(null);

        // Then
        eResource.getEmployeeById(id0, request);
        eResource.getEmployeeById(id1, request);

        verify(eResource, times(2)).getEmployeeById(anyLong(), Matchers.any(Request.class));
        verify(eResource, never()).getEmployees(Matchers.any(HttpHeaders.class));
        then(eResource).should(never()).createEmployee(Matchers.any(Employee.class)); // then and verify are similar, being verify more BDD oriented
    }

    @Test
    public void testUpdateEmployee() throws URISyntaxException {

        Employee e1 = new Employee("Pau Torres", "38875513Q", new Date(), new BigDecimal(2000), new Date());
        URI uri = new URI("test");
        UriBuilder baseUriBuilder = UriBuilder.fromUri(uri);

        // when
        doAnswer(invocationOnMock -> { e1.setId(1L); return e1; }).when(queryService).findEmployeeByNIE(anyString());

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

        doNothing().when(pService).updateEmployee(e1);

        // then
        eResource.updateEmployee(e1);
    }

    @Test
    public void testDeleteEmployeeById() {

        Employee e1 = new Employee("Pau Torres", "38875513Q", new Date(), new BigDecimal(2000), new Date());

        // when
        when(queryService.findEmployeeById(anyLong())).thenReturn(e1);
        doNothing().when(pService).deleteEntity(e1);

        // then
        eResource.terminateEmployeeById(1L);
    }

    @Test
    public void testDeleteEmployeeByNIE() {

        Employee e1 = new Employee("Pau Torres", "38875513Q", new Date(), new BigDecimal(2000), new Date());

        // when
        when(queryService.findEmployeeByNIE(anyString())).thenReturn(e1);
        doNothing().when(pService).deleteEmployee(e1);

        // then
        eResource.terminateEmployeeByNIE(e1.getSocialSecurityNumber());
    }

    private Response buildMockResponse(Response.Status status, String msgEntity) {

        Response mockResponse = mock(Response.class);
        when(mockResponse.readEntity(String.class)).thenReturn(msgEntity);
        when(mockResponse.getStatus()).thenReturn(status.getStatusCode());
        when(mockResponse.getStatusInfo()).thenReturn(status);

        return mockResponse;
    }


}
