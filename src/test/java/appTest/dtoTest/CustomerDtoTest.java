package appTest.dtoTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.math.BigDecimal;

import org.hibernate.validator.HibernateValidator;
import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import app.dto.CustomerDto;

public class CustomerDtoTest {

    private Validator validator;

    @Before
    public void setUp() {
        LocalValidatorFactoryBean localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        localValidatorFactory.afterPropertiesSet();
        validator = localValidatorFactory;
    }

    @Test
    public void testCustomerDtoValid() {
        CustomerDto customerDto = CustomersTestModel.customerDto();
        Errors errors = new BeanPropertyBindingResult(customerDto, "customerDto");
        validator.validate(customerDto, errors);
        assertFalse(errors.hasErrors());
    }

    @Test
    public void testCustomerDtoWrongFormat() {
        CustomerDto customerDto = CustomersTestModel.customerDto();
        customerDto.setCust_num(BigDecimal.valueOf(99));
        Errors errors = new BeanPropertyBindingResult(customerDto, "customerDto");
        validator.validate(customerDto, errors);
        assertEquals(errors.getFieldError("cust_num").getDefaultMessage(), "3");
    }

    @Test
    public void testCustomerDtoCustomerDetailsIsNull() {
        CustomerDto customerDto = CustomersTestModel.customerDto();
        customerDto.setCustomerDetails(null);
        Errors errors = new BeanPropertyBindingResult(customerDto, "customerDto");
        validator.validate(customerDto, errors);
        assertEquals(errors.getFieldError("customerDetails").getDefaultMessage(), "1");
    }
}

