package app.dto;


import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.math.BigDecimal;

public class CustomerDetails {
    @Max(value = 9999, message = "2")
    private BigDecimal credit_limit;

    @Pattern(message = "5", regexp = "^[A-Za-z]{2,15}$")
    @Length(min = 2, message = "4")
    private String company;

    public BigDecimal getCredit_limit() {
        return credit_limit;
    }

    public void setCredit_limit(BigDecimal credit_limit) {
        this.credit_limit = credit_limit;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
