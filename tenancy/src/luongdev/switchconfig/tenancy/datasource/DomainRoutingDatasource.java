package luongdev.switchconfig.tenancy.datasource;

import luongdev.switchconfig.tenancy.Domains;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;

//@Component
public class DomainRoutingDatasource extends AbstractRoutingDataSource {

    private final DomainIdentifierResolver domainIdentifierResolver;

    public DomainRoutingDatasource(
            Domains domainRepo,
            DomainIdentifierResolver domainIdentifierResolver) {
        this.domainIdentifierResolver = domainIdentifierResolver;
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return domainIdentifierResolver.resolveCurrentTenantIdentifier();
    }
}
