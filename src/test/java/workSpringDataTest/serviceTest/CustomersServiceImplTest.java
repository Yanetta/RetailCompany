package workSpringDataTest.serviceTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import java.math.BigDecimal;
import java.util.*;
import workSpringData.entities.Customers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import workSpringData.repository.CustomersRepository;
import workSpringData.service.CustomersService;
import workSpringData.service.CustomersServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class CustomersServiceImplTest {
    @Spy
    @InjectMocks
    private CustomersService customersService = new CustomersServiceImpl();
    @Mock
    private CustomersRepository customersRepository;

    private Customers customers1 = new Customers();
    private Customers customers2 = new Customers();

    @Test
    public void testGetAllCustomers() {
        List<Customers> customers = Arrays.asList(new Customers[]{customers1, customers2});
        doReturn(customers).when(customersRepository).findAll();
        Set<Customers> customersSet = customersService.getAllCustomers();
        assertTrue(customers.containsAll(customersSet) && customersSet.containsAll(customers));
    }

    @Test
    public void testFindOrderById() {
        doReturn(Optional.of(customers1)).when(customersRepository).findById(any());
        Customers customers = customersService.findCustomerById(BigDecimal.valueOf(2115));
        assertEquals(customers1, customers);
    }

    @Test
    public void testFindByCompany() {
        Set<Customers> customersSet = new HashSet<Customers>();
        customersSet.add(customers1);
        doReturn(customersSet).when(customersRepository).findCustomersByCompanyLike((any()));
        Set<Customers> result = customersService.findByCompany("Ро%");
        assertTrue(customersSet.containsAll(result) && result.containsAll(customersSet));
    }

    @Test
    public void testInsertCustomer() {
        doReturn(customers1).when(customersRepository).save(any());
        customersService.insertCustomers(customers1);
        verify(customersRepository, times(1)).save(any());
    }

    @Test
    public void testUpdateCustomer() {
        doReturn(customers1).when(customersRepository).save(any());
        customersService.updateCustomers(customers1);
        verify(customersRepository, times(1)).save(any());
    }

    @Test
    public void testDeleteCustomer() {
        doNothing().when(customersRepository).deleteById(any());
        customersService.deleteCustomers(customers1.getCust_num());
        verify(customersRepository, times(1)).deleteById(any());
    }
}


