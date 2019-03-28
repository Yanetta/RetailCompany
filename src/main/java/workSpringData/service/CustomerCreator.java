package workSpringData.service;

import workSpringData.entities.Customers;
import workSpringData.dto.CustomerDto;

public interface CustomerCreator {
    Customers createCustomer (CustomerDto customerDto);
}
