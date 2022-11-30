package luongdev.switchconfig.configuration.acl.commands;

import luongdev.cqrs.Request;
import luongdev.switchconfig.configuration.acl.AccessControl;
import org.apache.commons.lang3.StringUtils;

public class UpdateAccessControlCommand implements Request<AccessControl> {

    private String name;
    private String description;

    private UpdateAccessControlCommand() {
    }

    public UpdateAccessControlCommand(String name, String description) {
        assert StringUtils.isNotEmpty(name);

        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
