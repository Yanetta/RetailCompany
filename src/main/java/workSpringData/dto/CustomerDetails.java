package workSpringData.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import entities.Orders;
import entities.Salesreps;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class CustomerDetails {
    @Min(value = 100, message = "3")
    @Max(value = 10000, message = "2")
    private BigDecimal credit_limit;

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
