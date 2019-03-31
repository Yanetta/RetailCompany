package app.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Locale;
import java.util.Properties;

@Configuration
@ComponentScan("app.service")
@EnableCaching
@EnableTransactionManagement
@EnableJpaRepositories("app.repository")
@PropertySource("classpath:app/config/springData.properties")
public class DataConfig  {
    private static final Logger LOG = LogManager.getLogger(DataConfig.class);

    @Value("${driver}")
    private String propertyDriver;
    @Value("${url}")
    private String propertyUrl;
    @Value("${user}")
    private String propertyUsername;
    @Value("${password}")
    private String propertyPassword;
    @Value("${maxactive}")
    private String propertyMaxActive;
    @Value("${initialsize}")
    private String propertyInitialSize;
    @Value("${hibernate.show_sql}")
    private String propertyShowSql;
    @Value("${hibernate.dialect}")
    private String propertyDialect;

    @Bean("entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean lfb = new LocalContainerEntityManagerFactoryBean();
        lfb.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        lfb.setDataSource(dataSource());
        lfb.setPackagesToScan("app/model");
        lfb.setJpaProperties(hibernateProps());
        return lfb;
    }
    @Bean
    public DataSource dataSource() {
        Locale.setDefault(Locale.ENGLISH);
        ComboPooledDataSource cpds = new ComboPooledDataSource();
        try {
            cpds.setDriverClass(propertyDriver);
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        cpds.setJdbcUrl(propertyUrl);
        cpds.setUser(propertyUsername);
        cpds.setPassword(propertyPassword);
        // Optional Settings
        cpds.setInitialPoolSize(Integer.parseInt(propertyInitialSize));
        cpds.setMaxPoolSize(Integer.parseInt(propertyMaxActive));

        LOG.debug("Establish connection to DB={}", propertyUrl);
        return cpds;

    }
    private Properties hibernateProps() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", propertyDialect);
        properties.setProperty("hibernate.show_sql", propertyShowSql);
        properties.setProperty("hibernate.format_sql", "true");
        return properties;
    }
    @Bean
    public JpaTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }
@Bean
    public CacheManager cacheManager(){
        return new ConcurrentMapCacheManager();
}

}
