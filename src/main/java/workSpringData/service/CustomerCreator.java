package workSpringData.service;

import entities.Customers;
import workSpringData.dto.CustomerDto;

public interface CustomerCreator {
    Customers createCustomer (CustomerDto customerDto);
}
