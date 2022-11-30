package luongdev.switchconfig.configuration.acl.commands;

import luongdev.cqrs.Request;
import luongdev.switchconfig.configuration.acl.AccessControl;

import java.util.UUID;

public class ToggleAccessControlCommand implements Request<AccessControl> {

    private UUID id;

    private ToggleAccessControlCommand() {
    }

    public ToggleAccessControlCommand(UUID id) {
        assert id != null;
        this.id = id;
    }

    public UUID getId() {
        return id;
    }
}
