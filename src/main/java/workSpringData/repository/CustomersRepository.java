package workSpringData.repository;

import entities.Customers;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Repository
public interface CustomersRepository extends JpaRepository<Customers, BigDecimal> {

//    @Cacheable("customersCached")
//    Set<Customers> findByCompanyEndsWith(String end);
}
