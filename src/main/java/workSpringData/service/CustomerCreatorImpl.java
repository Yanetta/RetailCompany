package workSpringData.service;

import entities.Customers;
import org.springframework.stereotype.Service;
import workSpringData.dto.CustomerDetails;
import workSpringData.dto.CustomerDto;

import java.math.BigDecimal;

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
