package workSpringData.controller;


import entities.Customers;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import workSpringData.exeption.DeleteException;
import workSpringData.exeption.UpdateException;
import workSpringData.service.CustomersService;


import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@RestController
@RequestMapping("/customer")
public class CustomersController {

    private static final Logger LOG = LogManager.getLogger(CustomersController.class);

    @Autowired
    private CustomersService customersService;

    @GetMapping()
    public @ResponseBody Set<Customers> getCustomers()  {
        LOG.info(" in getCustomers");
        Set<Customers> customers = customersService.getAllCustomers();
        LOG.info("getCustomers finished");
        return customers;
    }

    @GetMapping("/id/{id}")
    public @ResponseBody
    Customers getCustomerById(@PathVariable("id") int id)  {
        LOG.info(" in getCustomerById");
        Customers customers = customersService.findCustomerById(BigDecimal.valueOf(id));
        LOG.info("getCustomerById finished");
        return customers;
    }

    @GetMapping("/company/{company}")
    public @ResponseBody Set<Customers> findByCompany(@PathVariable("company") String company)  {
        LOG.info(" in findByCompany");
        Set<Customers> customers = customersService.findByCompany(company);
        LOG.info("findByCompany finished");
        return customers;
    }

    @PutMapping("/update/{idd}")
    public void updateCustomer(@PathVariable("idd") int idd, @RequestParam("creditLimit") Integer creditLimit) {
        LOG.info("in updateCustomer");
        Customers customers = customersService.findCustomerById(BigDecimal.valueOf(idd));
        if (Objects.isNull(customers)) {
            LOG.warn("updateCustomer cannot update not existing customer");
            throw new UpdateException("Can not update customer by id=" + idd + ", because it does'nt exist");
        } else {
            customers.setCredit_limit(BigDecimal.valueOf(creditLimit));
            customersService.updateCustomers(customers);
        }
        LOG.info("updateOrderById finished");
    }

    @DeleteMapping("/delete/{id}")
    public void deleteCustomerById(@PathVariable("id") int id) {
        Customers customers = customersService.findCustomerById(BigDecimal.valueOf(id));
        if (Objects.isNull(customers)) {
            LOG.warn("deleteCustomerById is not successfull");
            throw new DeleteException("Can not not delete Customer by id=" + id + ", because it doesn't exist.");
        } else {
            customersService.deleteCustomers(BigDecimal.valueOf(id));
        }
        LOG.info("deleteCustomerById finished.");
    }
}