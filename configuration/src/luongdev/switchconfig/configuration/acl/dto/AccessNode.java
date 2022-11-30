package luongdev.switchconfig.configuration.acl.dto;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

public class AccessNode implements Serializable {

    private String cidr;
    private String domain;
    private String description;

    private AccessNode() {}

    public AccessNode(String cidr, String domain, String description) {
        assert StringUtils.isNotEmpty(cidr) || StringUtils.isNotEmpty(domain);

        this.cidr = cidr;
        this.domain = domain;
        this.description = description;
    }

    public AccessNode(String cidr, String domain) {
        this(cidr, domain, null);
    }

    public String getCidr() {
        return cidr;
    }

    public String getDomain() {
        return domain;
    }

    public String getDescription() {
        return description;
    }
}
