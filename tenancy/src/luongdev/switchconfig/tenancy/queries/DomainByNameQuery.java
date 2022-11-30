package luongdev.switchconfig.tenancy.queries;

import luongdev.cqrs.Request;
import luongdev.switchconfig.tenancy.Domain;

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
