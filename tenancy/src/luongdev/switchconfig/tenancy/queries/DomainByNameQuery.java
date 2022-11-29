package luongdev.switchconfig.tenancy.queries;

import luongdev.switchconfig.tenancy.Domain;
import luongld.cqrs.Request;

public class DomainByNameQuery implements Request<Domain> {

    private String domain;

    private DomainByNameQuery() {
    }

    public DomainByNameQuery(String domain) {
        this.domain = domain;
    }

    public String getDomain() {
        return domain;
    }
}
