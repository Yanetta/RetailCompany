package workSpringData.service;
import workSpringData.entities.Customers;
import java.math.BigDecimal;
import java.util.Set;

public interface CustomersService {

    Set<Customers> getAllCustomers();

    Customers findCustomerById(BigDecimal custNum);

    Set<Customers> findByCompany(String s);

    void insertCustomers(Customers customer);

    void updateCustomers(Customers customer);

    void deleteCustomers(BigDecimal custNum);
}

