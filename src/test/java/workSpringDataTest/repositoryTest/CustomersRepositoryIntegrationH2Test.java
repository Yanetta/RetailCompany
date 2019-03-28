package workSpringDataTest.repositoryTest;

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
import workSpringData.config.DataConfig;
import workSpringData.repository.CustomersRepository;
import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

@DirtiesContext
@ContextConfiguration(classes = DataConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource("classpath:test.properties")
public class CustomersRepositoryIntegrationH2Test {
    private static final Customers NOT_EXIST_CUSTOMER_INSERT = new Customers(BigDecimal.valueOf(8888));
    private static final Customers ALREADY_EXIST_CUSTOMER_UPDATE = new Customers(BigDecimal.valueOf(5555));
    private static final Customers ALREADY_EXIST_CUSTOMER_DELETE = new Customers(BigDecimal.valueOf(7777));

    @Autowired
    private CustomersRepository customersRepository;

    @Autowired
    private DataSource dataSource;

    @Before
    public void initDb() {
        Resource initSchema = new ClassPathResource("schema.sql");
        Resource data = new ClassPathResource("data.sql");
        DatabasePopulator databasePopulator = new ResourceDatabasePopulator(initSchema, data);
        DatabasePopulatorUtils.execute(databasePopulator, dataSource);
    }

    @Test
    public void testGetAllCustomers() {
        List<Customers> customers = customersRepository.findAll();
        System.out.println(customers);
        assertTrue(customers.size() > 1);
    }

    @Test
    public void testInsertCustomer() {
        customersRepository.save(NOT_EXIST_CUSTOMER_INSERT);
    }

    @Test
    public void testUpdateCustomer() {
        ALREADY_EXIST_CUSTOMER_UPDATE.setCredit_limit(BigDecimal.valueOf(777));
        ALREADY_EXIST_CUSTOMER_UPDATE.setCompany("Intel");
        customersRepository.save(ALREADY_EXIST_CUSTOMER_UPDATE);
    }

    @Test
    public void testDeleteCustomer() {
        customersRepository.delete(ALREADY_EXIST_CUSTOMER_DELETE);
    }

    @Test
    public void testFindCustomerById() {
        assertTrue(customersRepository.findById(BigDecimal.valueOf(5555)).isPresent());
    }
}