package workSpringData;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import workSpringData.repository.CustomersRepository;
import workSpringData.service.CustomersService;
import workSpringData.service.CustomersServiceImpl;

public class Main {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = new AnnotationConfigApplicationContext("workSpringData.config");
        CustomersRepository customersRepository = context.getBean(CustomersRepository.class);

        customersRepository.findAll().forEach(System.out::println);

       CustomersServiceImpl customersService = context.getBean(CustomersServiceImpl.class);
       customersService.getAllCustomers();

        context.close();
    }
}
