package org.pautib;


import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.isA;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class IntegrationTest {

    private String input;
    private String expectedOutput;

    public IntegrationTest(String input, String expectedOutput) {
        this.input = input;
        this.expectedOutput = expectedOutput;
    }

    @Parameterized.Parameters
    public static Collection<String[]> testConditions() {

        String expectedOutputs[][] = {  {"AACD", "AACD"}, // input expectedOutput
                                        {"ACD", "ACD"}      // input expectedOutput
                                    };

        return Arrays.asList(expectedOutputs);
    }

    @Test
    public void testParameterizedObjects () {
        assertEquals( this.expectedOutput, this.input );
    }

    /**
    @EJB
    private PersistenceService persistenceService;

    @Inject
    private QueryService queryService;

    private Client client;

    private WebTarget webTarget;

    @ArquillianResource
    private URL base;

    @PersistenceContext
    EntityManager entityManager;

    @Deployment
    public static JavaArchive createDeployment() {

        return ShrinkWrap.create(JavaArchive.class, "humanresources.war")
                .addClasses(PersistenceService.class, Employee.class, JAXRSConfiguration.class, EmployeeResource.class)
                //.addPackage(PersistenceService.class.getPackage())
                //.addPackage(Employee.class.getPackage())
                //.addPackage(JAXRSConfiguration.class.getPackage())
                //.addPackage(EmployeeResource.class.getPackage())
                //.addAsResource("META-INF/persistence.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");

    }
    */

    @Before
    public void init() {
        System.out.println("Before every test");
    }

    @After
    public void end() {
        System.out.println("After every test");
    }

    @BeforeClass //must be a static method
    public static void beforeClass() {
        System.out.println("Just run once when class instance is generated");
    }

    @AfterClass
    public static void afterClass() {
        System.out.println("Run once after tests are done");
    }

    @Test
    public void greet() {
        assertEquals(2,2);
        assertFalse("AB".equals("A")); // it's valid if condition is false
        assertTrue("A".equals("A")); // valid if condition is true

        assertArrayEquals(new int[]{1, 3, 4, 2}, new int[]{1, 3, 4, 2});
    }

    @Test
    public void test() {
        assertEquals(1,1);

        assertThat(12, isA(Number.class));
    }

    @Test(expected=NullPointerException.class)
    public void testException() {
        int[] numbers = null;
        Arrays.sort(numbers);
    }

    @Test(timeout = 100)
    public void testPerformance () {
        int array[] = {12, 23, 4};

        for (int i = 1; i <= 1000000; i++) {
            array[0] = i;
            Arrays.sort(array);
        }
    }
}
