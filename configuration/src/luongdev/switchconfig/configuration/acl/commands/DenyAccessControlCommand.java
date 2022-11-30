package luongdev.switchconfig.configuration.acl.commands;

import luongdev.cqrs.Request;
import luongdev.switchconfig.configuration.acl.AccessControl;
import luongdev.switchconfig.configuration.acl.dto.AccessNode;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class DenyAccessControlCommand implements Request<AccessControl> {

    private String name;
    private Set<AccessNode> accessNodes;

    private DenyAccessControlCommand() {
        accessNodes = new HashSet<>();
    }

    public DenyAccessControlCommand(String name, String cidr, String domain, String description) {
        this(name);

        node(cidr, domain, description);
    }

    public DenyAccessControlCommand(String name, String cidr, String domain) {
        this(name, cidr, domain, null);
    }

    public DenyAccessControlCommand(String name) {
        this();

        assert StringUtils.isNotEmpty(name);

        this.name = name;
    }

    public DenyAccessControlCommand(String name, Collection<AccessNode> accessNodes) {
        this(name);

        if (accessNodes != null && !accessNodes.isEmpty()) this.accessNodes.addAll(accessNodes);
    }

    public DenyAccessControlCommand node(String cidr, String domain, String description) {
        this.accessNodes.add(new AccessNode(cidr, domain, description));

        return this;
    }

    public DenyAccessControlCommand node(String cidr, String domain) {
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
