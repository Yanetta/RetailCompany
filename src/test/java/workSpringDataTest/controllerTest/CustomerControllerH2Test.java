package workSpringDataTest.controllerTest;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import javax.ws.rs.core.Response;
import javax.sql.DataSource;
import workSpringData.entities.Customers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import workSpringData.config.MainMvcConfig;

@DirtiesContext
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MainMvcConfig.class})
@WebAppConfiguration
@TestPropertySource("classpath:test.properties")
public class CustomerControllerH2Test {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private DataSource dataSource;

    private MockMvc mockMvc;
    private ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setup() {
        Resource initSchema = new ClassPathResource("schema.sql");
        Resource data = new ClassPathResource("data.sql");
        DatabasePopulator databasePopulator = new ResourceDatabasePopulator(initSchema, data);
        DatabasePopulatorUtils.execute(databasePopulator, dataSource);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testGetAllCustomers() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/customer")).andDo(print()).andReturn();
        assertEquals(Response.Status.OK.getStatusCode(), mvcResult.getResponse().getStatus());
        assertEquals("application/json;charset=UTF-8", mvcResult.getResponse().getContentType());
    }

    @Test
    public void testGetExistCustomerById() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/customer/id").param("id", "5555")).andDo(print()).andReturn();
        Customers customers = mapper.readValue(mvcResult.getResponse().getContentAsString(), Customers.class);
        assertEquals(Response.Status.OK.getStatusCode(), mvcResult.getResponse().getStatus());
        assertNotNull(customers);
    }

    @Test
    public void testGetNotExistCustomerById() throws Exception {
        assertNotEquals(Response.Status.OK.getStatusCode(), mockMvc.perform(get("/customer/id/{id}", "9999")).andDo(print()).andReturn().getResponse().getStatus());
        assertTrue(mockMvc.perform(get("/customer/id/{id}", "9999")).andDo(print()).andReturn().getResponse().getContentAsString().length() == 0);
    }

    @Test
    public void testDeleteExistingCustomer() throws Exception {
        MvcResult mvcResult = mockMvc.perform(delete("/customer/delete/{id}", "7777")).andDo(print()).andReturn();
        assertEquals(Response.Status.OK.getStatusCode(), mvcResult.getResponse().getStatus());
    }

    @Test
    public void testDeleteNotExistCustomerById() throws Exception {
        MvcResult mvcResult = mockMvc.perform(delete("/customer/delete/{id}", "1111")).andDo(print()).andReturn();
        assertEquals(500, mvcResult.getResponse().getStatus());
        assertEquals("application/json;charset=UTF-8", mvcResult.getResponse().getContentType());
    }

    @Test
    public void testUpdateCustomerById() throws Exception {
        MvcResult mvcResult = mockMvc.perform(put("/customer/update/{idd}", "5555").param("creditLimit", "400")).andDo(print()).andReturn();
        assertEquals(Response.Status.OK.getStatusCode(), mvcResult.getResponse().getStatus());
    }

    @Test
    public void testUpdateNotExistCustomer() throws Exception {
        MvcResult mvcResult = mockMvc.perform(put("/customer/update/{idd}", "8888").param("creditLimit", "400")).andDo(print()).andReturn();
        assertEquals(500, mvcResult.getResponse().getStatus());
        assertEquals("application/json;charset=UTF-8", mvcResult.getResponse().getContentType());
    }

    @Test
    public void testGetCustomerByCompany() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/customer/company/{company}", "Intel")).andDo(print()).andReturn();
        assertEquals(Response.Status.OK.getStatusCode(), mvcResult.getResponse().getStatus());
        assertEquals("application/json;charset=UTF-8", mvcResult.getResponse().getContentType());
    }
}

