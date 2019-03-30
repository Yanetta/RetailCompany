package app.service;

import app.model.Customers;
import org.springframework.stereotype.Service;
import app.dto.CustomerDetails;
import app.dto.CustomerDto;

@Service
public class CustomerCreatorImpl implements CustomerCreator {
    @Override
    public Customers createCustomer(CustomerDto customerDto) {
        Customers customers = new Customers();
        customers.setCust_num(customerDto.getCust_num());

        CustomerDetails customerDetails = customerDto.getCustomerDetails();
        customers.setCompany(customerDetails.getCompany());
        customers.setCredit_limit(customerDetails.getCredit_limit());
        return customers;
    }
}
