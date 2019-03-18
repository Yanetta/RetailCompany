package workSpringData.service;

import entities.Customers;

import java.math.BigDecimal;
import java.util.Set;

public interface CustomersService {

    Set<Customers> getAllCustomers();

    Customers findCustomerById(BigDecimal custNum);

    Set<Customers> findByCompanyEndsWith(String end);

    void insertCustomers(Customers customer);

    void updateCustomers(Customers customer);

    void deleteCustomers(BigDecimal custNum);
}

