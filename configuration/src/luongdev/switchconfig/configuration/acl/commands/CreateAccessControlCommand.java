package luongdev.switchconfig.configuration.acl.commands;

import luongdev.switchconfig.configuration.acl.AccessControl;
import luongld.cqrs.Request;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class CreateAccessControlCommand implements Request<AccessControl> {

    private String name;
    private boolean allow;
    private String description;
    private final Map<String, String> allows = new HashMap<>();
    private final Map<String, String> denies = new HashMap<>();

    private CreateAccessControlCommand() {
        this.allow = false;
    }

    public CreateAccessControlCommand(String name, boolean allow, String description) {
        this();
        assert StringUtils.isNotEmpty(name);

        this.name = name;
        this.allow = allow;
        this.description = description;
    }

    public CreateAccessControlCommand(String name, boolean allow) {
        this(name, allow, null);
    }

    public CreateAccessControlCommand(String name, String description) {
        this(name, false, description);
    }

    public CreateAccessControlCommand allow(String cidr, String domain) {
        if (StringUtils.isNotEmpty(cidr)) this.allows.put(cidr, domain);

        return this;
    }

    public CreateAccessControlCommand allow(String cidr) {
        return allow(cidr, null);
    }

    public CreateAccessControlCommand deny(String cidr, String domain) {
        if (StringUtils.isNotEmpty(cidr)) this.denies.put(cidr, domain);

        return this;
    }

    public CreateAccessControlCommand deny(String cidr) {
        return deny(cidr, null);
    }

    public String getName() {
        return name;
    }

    public boolean isAllow() {
        return allow;
    }

    public String getDescription() {
        return description;
    }

    public Map<String, String> getAllows() {
        return allows;
    }

    public Map<String, String> getDenies() {
        return denies;
    }
}
