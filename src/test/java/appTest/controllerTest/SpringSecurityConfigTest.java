package appTest.controllerTest;

import app.dto.CustomerDto;
import appTest.dtoTest.CustomersTestModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import app.config.MainMvcConfig;
import app.model.Customers;

import javax.sql.DataSource;
import javax.ws.rs.core.Response;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

    @DirtiesContext
    @RunWith(SpringJUnit4ClassRunner.class)
    @ContextConfiguration(classes = {MainMvcConfig.class})
    @WebAppConfiguration
    @TestPropertySource("classpath:test.properties")

    public class SpringSecurityConfigTest {

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
            this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(SecurityMockMvcConfigurers.springSecurity()).build();
        }

        @Test
        @WithMockUser(roles = "ADMIN")
        public void testDeleteExistingCustomerAuth() throws Exception {
            MvcResult mvcResult = mockMvc.perform(delete("/customer/delete/{id}", "7777")).andDo(print()).andReturn();
            assertEquals(Response.Status.OK.getStatusCode(), mvcResult.getResponse().getStatus());
        }

        @Test
        public void testDeleteExistingCustomer() throws Exception {
            MvcResult mvcResult = mockMvc.perform(delete("/customer/delete/{id}", "7777")).andDo(print()).andReturn();
            assertEquals(Response.Status.UNAUTHORIZED.getStatusCode(), mvcResult.getResponse().getStatus());
        }

        @Test
        @WithMockUser(roles = "ADMIN")
        public void testUpdateCustomerByIdAuth() throws Exception {
            MvcResult mvcResult = mockMvc.perform(put("/customer/update/{id}", "7777").param("creditLimit", "400")).andDo(print()).andReturn();
            assertEquals(Response.Status.OK.getStatusCode(), mvcResult.getResponse().getStatus());
        }

        @Test
        public void testUpdateCustomerById() throws Exception {
            MvcResult mvcResult = mockMvc.perform(put("/customer/update/{id}", "5555").param("creditLimit", "400")).andDo(print()).andReturn();
            assertEquals(Response.Status.UNAUTHORIZED.getStatusCode(), mvcResult.getResponse().getStatus());
        }

        @Test
        @WithMockUser(roles = "ADMIN")
               public void testInsertCustomerAuth() throws Exception {
            String json = mapper.writeValueAsString(CustomersTestModel.customerDto());
            MvcResult mvcResult = mockMvc.perform(post("/customer/insert").contentType(MediaType.APPLICATION_JSON).content(json)).andDo(print()).andReturn();
            assertEquals(Response.Status.OK.getStatusCode(), mvcResult.getResponse().getStatus());
        }

        @Test
        public void testInsertCustomer() throws Exception {
            String json = mapper.writeValueAsString(CustomersTestModel.customerDto());
            MvcResult mvcResult = mockMvc.perform(post("/customer/insert").contentType(MediaType.APPLICATION_JSON).content(json)).andDo(print()).andReturn();
            assertEquals(Response.Status.UNAUTHORIZED.getStatusCode(), mvcResult.getResponse().getStatus());
        }
    }

