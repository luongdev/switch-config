package luongdev.switchconfig.configuration.acl.commands;

import luongdev.cqrs.Request;
import luongdev.switchconfig.configuration.acl.AccessControl;
import luongdev.switchconfig.configuration.acl.dto.AccessNode;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class AllowAccessControlCommand implements Request<AccessControl> {

    private String name;
    private Set<AccessNode> accessNodes;

    private AllowAccessControlCommand() {
        accessNodes = new HashSet<>();
    }

    public AllowAccessControlCommand(String name, String cidr, String domain, String description) {
        this(name);

        node(cidr, domain, description);
    }

    public AllowAccessControlCommand(String name, String cidr, String domain) {
        this(name, cidr, domain, null);
    }

    public AllowAccessControlCommand(String name) {
        this();

        assert StringUtils.isNotEmpty(name);

        this.name = name;
    }

    public AllowAccessControlCommand(String name, Collection<AccessNode> accessNodes) {
        this(name);

        if (accessNodes != null && !accessNodes.isEmpty()) this.accessNodes.addAll(accessNodes);
    }

    public AllowAccessControlCommand node(String cidr, String domain, String description) {
        this.accessNodes.add(new AccessNode(cidr, domain, description));

        return this;
    }

    public AllowAccessControlCommand node(String cidr, String domain) {
        return this.node(cidr, domain, null);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<AccessNode> getAccessNodes() {
        return accessNodes;
    }

    public void setAccessNodes(Set<AccessNode> accessNodes) {
        this.accessNodes = accessNodes;
    }
}
