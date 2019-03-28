package workSpringDataTest.dtoTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.hibernate.validator.HibernateValidator;
import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import workSpringData.dto.CustomerDetails;
import workSpringData.dto.CustomerDto;

import java.math.BigDecimal;

public class CustomerDetailsTest {

    private Validator validator;

    @Before
    public void setUp() {
        LocalValidatorFactoryBean localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        localValidatorFactory.afterPropertiesSet();
        validator = localValidatorFactory;
    }

    @Test
    public void testOrderDetailsValid() {
        CustomerDetails customerDetails = CustomersTestModel.customerDetails();
        Errors errors = new BeanPropertyBindingResult(customerDetails, "customerDetails");
        validator.validate(customerDetails, errors);
        assertFalse(errors.hasErrors());
    }

    @Test
    public void testCustomerDetailsCreditLimitBiggerThanMax() {
        CustomerDetails customerDetails = CustomersTestModel.customerDetails();
        customerDetails.setCredit_limit(BigDecimal.valueOf(77777));
        Errors errors = new BeanPropertyBindingResult(customerDetails, "customerDetails");
        validator.validate(customerDetails, errors);
        assertEquals(errors.getFieldError("credit_limit").getDefaultMessage(), "2");
    }

    @Test
    public void testCustomerDetailsCompanyLengthLessThanTwo() {
        CustomerDetails customerDetails = CustomersTestModel.customerDetails();
        customerDetails.setCompany("G");
        Errors errors = new BeanPropertyBindingResult(customerDetails, "customerDetails");
        validator.validate(customerDetails, errors);
        assertEquals(errors.getFieldError("company").getDefaultMessage(), "4");
   }

    @Test
    public void testCustomerDetailsCompanyOutOfFormat() {
        CustomerDetails customerDetails = CustomersTestModel.customerDetails();
        customerDetails.setCompany("G34hu");
        Errors errors = new BeanPropertyBindingResult(customerDetails, "customerDetails");
        validator.validate(customerDetails, errors);
        assertEquals(errors.getFieldError("company").getDefaultMessage(), "5");
    }
}

