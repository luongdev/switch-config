package luongdev.switchconfig.tenancy;

import org.springframework.data.jpa.repository.JpaRepository;

public interface Domains extends JpaRepository<Domain, String> {

    static Domain domain(String domain) {
        return new Domain(domain);
    }

}
