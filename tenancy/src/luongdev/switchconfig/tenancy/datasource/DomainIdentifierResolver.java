package luongdev.switchconfig.tenancy.datasource;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class DomainIdentifierResolver implements CurrentTenantIdentifierResolver, HibernatePropertiesCustomizer {

    private String currentDomain = "public";

    public void setCurrentDomain(String domain) {
        currentDomain = domain;
    }

    public void publicDomain() {
        this.setCurrentDomain("public");
    }

    @Override
    public String resolveCurrentTenantIdentifier() {
        return StringUtils.isEmpty(currentDomain) ? "public" : currentDomain;
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }

    @Override
    public void customize(Map<String, Object> hibernateProperties) {
        hibernateProperties.put(AvailableSettings.MULTI_TENANT_IDENTIFIER_RESOLVER, this);
    }

}
