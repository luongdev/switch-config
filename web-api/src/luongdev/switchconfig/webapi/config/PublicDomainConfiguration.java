package luongdev.switchconfig.webapi.config;

import luongdev.switchconfig.tenancy.AbstractPublicDomainConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = {
                "luongdev.switchconfig.tenancy",
                "luongdev.switchconfig.cluster",
                "luongdev.switchconfig.configuration",
        },
        transactionManagerRef = "publicTransactionManager",
        entityManagerFactoryRef = "publicEntityManagerFactory"
)
public class PublicDomainConfiguration extends AbstractPublicDomainConfiguration {

    public PublicDomainConfiguration() {
        addScanPackages("luongdev.switchconfig.cluster");
        addScanPackages("luongdev.switchconfig.configuration");
    }
}
