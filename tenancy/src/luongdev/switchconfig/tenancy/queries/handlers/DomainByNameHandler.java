package luongdev.switchconfig.tenancy.queries.handlers;

import luongdev.cqrs.RequestHandler;
import luongdev.switchconfig.tenancy.Domain;
import luongdev.switchconfig.tenancy.Domains;
import luongdev.switchconfig.tenancy.exceptions.DomainNotFoundException;
import luongdev.switchconfig.tenancy.queries.DomainByNameQuery;
import org.springframework.stereotype.Component;

@Component
public class DomainByNameHandler implements RequestHandler<Domain, DomainByNameQuery> {

    private final Domains domains;

    public DomainByNameHandler(Domains domains) {
        this.domains = domains;
    }

    @Override
    public Domain handle(DomainByNameQuery query) {
        return domains.findById(query.getDomain()).orElseThrow(() -> new DomainNotFoundException(query.getDomain()));
    }
}
