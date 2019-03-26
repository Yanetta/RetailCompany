package workSpringDataTest;

import entities.Customers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import workSpringData.config.DataConfig;
import workSpringData.repository.CustomersRepository;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.*;

@DirtiesContext
@ContextConfiguration(classes = DataConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@Rollback
public class CustomersRepositoryIntegrationTest {
    @Autowired
    private CustomersRepository customersRepository;
    private static final BigDecimal NOT_EXIST_CUSTOMER = BigDecimal.valueOf(-1);
    private static final BigDecimal ALREADY_EXIST_CUSTOMER = BigDecimal.valueOf(2103);
    private static final Customers CUSTOMERS = new Customers(BigDecimal.valueOf(1234), null, BigDecimal.valueOf(1234), "fjkhskjhfkjs");

    @Test
    public void testGetAllCustomers1() {
        List<Customers> customers = customersRepository.findAll();
        System.out.println(customers);
        assertTrue(customers.size() > 5);
    }

    @Test
    public void testInsertCustomer() {
        customersRepository.save(CUSTOMERS);
    }

    @Test
    public void testUpdateCustomer() {
        Customers customers = customersRepository.findById(ALREADY_EXIST_CUSTOMER).get();
        customers.setCredit_limit(BigDecimal.valueOf(777));
        customers.setCompany("Intel");
        customersRepository.save(customers);
    }

    @Test
    public void testDeleteCustomer() {
        Customers customers = customersRepository.findById(ALREADY_EXIST_CUSTOMER).get();
        customersRepository.delete(customers);
    }

    @Test
    public void testFindCustomerByIdNotPresent() {
        Optional<Customers> customersOptional = customersRepository.findById(NOT_EXIST_CUSTOMER);
        assertFalse(customersOptional.isPresent());
    }

    @Test
    public void testFindCustomerById() {
        Optional<Customers> customersOptional = customersRepository.findById(ALREADY_EXIST_CUSTOMER);
        assertTrue(customersOptional.isPresent());
    }

    @Test
    public void testFindByCompany() {
        Set<Customers> customersSet = customersRepository.findCustomersByCompanyLike("Ро%");
        assertTrue(customersSet.size() >= 1);
    }
}
