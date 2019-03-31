package app.service;

import app.model.Customers;
import app.dto.CustomerDto;

public interface CustomerCreator {
    Customers createCustomer (CustomerDto customerDto);
}
