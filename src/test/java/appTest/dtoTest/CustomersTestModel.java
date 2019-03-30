package appTest.dtoTest;

import app.model.Customers;
import app.dto.CustomerDetails;
import app.dto.CustomerDto;
import java.math.BigDecimal;

public class CustomersTestModel {

    private static final BigDecimal CUSTNUM = BigDecimal.valueOf(5000);
    private static final BigDecimal CREDITLIMIT = BigDecimal.valueOf(800);
    private static final String COMPANY = "Movie";

    public static Customers customers() {
        Customers expected = new Customers();
        expected.setCust_num(CUSTNUM);
        expected.setCredit_limit(CREDITLIMIT);
        expected.setCompany(COMPANY);
        return expected;
    }

    public static CustomerDto customerDto() {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setCust_num(CUSTNUM);
        customerDto.setCustomerDetails(customerDetails());
        return customerDto;
    }

    public static CustomerDetails customerDetails() {
        CustomerDetails customerDetails = new CustomerDetails();
        customerDetails.setCompany(COMPANY);
        customerDetails.setCredit_limit(CREDITLIMIT);
        return customerDetails;
    }
}

