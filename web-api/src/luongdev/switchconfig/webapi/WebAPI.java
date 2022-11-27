package luongdev.switchconfig.webapi;

import luongdev.switchconfig.domain.extension.Extension;
import luongdev.switchconfig.domain.extension.Extensions;
import luongdev.switchconfig.tenancy.Domain;
import luongdev.switchconfig.tenancy.Domains;
import luongdev.switchconfig.tenancy.datasource.DomainIdentifierResolver;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
        "luongdev.switchconfig.tenancy",
        "luongdev.switchconfig.domain",
        "luongdev.switchconfig.webapi"
})
public class WebAPI implements CommandLineRunner {

    @Autowired
    DomainIdentifierResolver resolver;

    @Autowired
    Extensions extensions;

    @Autowired
    Domains domains;


    public static void main(String[] args) {
        SpringApplication.run(WebAPI.class, args);
    }

    @Override
    public void run(String... args) {
        resolver.publicDomain();

        var domain = new Domain("voice.metechvn.com");
        domain.setDbHost("localhost");
        domain.setDbPassword("Default");

        domains.save(domain);

        resolver.setCurrentDomain("voice.metechvn.com");

        var ext = new Extension();
        ext.setExtension(1000);
        ext.setAccountCode("");
        ext.setCallTimeout(20);
        ext.setDomain("public");
        ext.setPassword("public");
        ext.setEnabled(true);
        ext.setLimitMax(0);
        ext.setDialString("");

        extensions.save(ext);
    }
}
