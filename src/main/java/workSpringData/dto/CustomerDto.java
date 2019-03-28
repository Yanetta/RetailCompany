package workSpringData.dto;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public class CustomerDto {
    @Min(value = 999, message = "3")
    private BigDecimal cust_num;

    @Valid
    @NotNull(message = "1")
    private CustomerDetails customerDetails;

    public CustomerDetails getCustomerDetails() {
        return customerDetails;
    }

    public void setCustomerDetails(CustomerDetails customerDetails) {
        this.customerDetails = customerDetails;
    }

    public BigDecimal getCust_num() {
        return cust_num;
    }

    public void setCust_num(BigDecimal cust_num) {
        this.cust_num = cust_num;
    }
}
