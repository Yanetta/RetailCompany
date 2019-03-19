package workSpringData.service;
import entities.Customers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import workSpringData.exeption.DeleteException;
import workSpringData.repository.CustomersRepository;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class CustomersServiceImpl implements CustomersService {
    private static final Logger LOG = LogManager.getLogger(CustomersServiceImpl.class);

    @Autowired
    private CustomersRepository customersRepository;

    @Override
    public Set<Customers> getAllCustomers() {
        LOG.debug(" in getAllCustomers method");
        HashSet<Customers> customersHashSet = new HashSet<Customers>(customersRepository.findAll());
        return customersHashSet;
    }

    @Override
    public Customers findCustomerById(BigDecimal custNum) {
        LOG.debug(" in findCustomerById method");
        Customers customers = customersRepository.findById(custNum).get();
        return customers;
    }

    @Override
    public Set<Customers> findByCompany(String s) {
        LOG.debug(" in findCustomersByCompany method");
        HashSet<Customers> customersHashSet = new HashSet<Customers>(customersRepository.findCustomersByCompanyLike(s));
        return customersHashSet;
    }

    @Override
    public void insertCustomers(Customers customer) {
        LOG.debug(" in insertCustomers method");
        customersRepository.save(customer);
    }

    @Override
    public void updateCustomers(Customers customer) {
        LOG.debug(" in updateCustomers method");
        customersRepository.save(customer);
    }

    @Override
    public void deleteCustomers(BigDecimal custNum) {
        LOG.debug(" in updateCustomers method");
        try {
            customersRepository.deleteById(custNum);
        } catch (EmptyResultDataAccessException e) {
           LOG.warn("Cannot delete Customer with id{}, becouse dont present", custNum);
            throw new DeleteException("Cannot delete Customer by Id=" + custNum + ", because it doesn't exist");
        }
       LOG.debug("deleteOrder completed");
    }
}
