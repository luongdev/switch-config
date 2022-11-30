package luongdev.switchconfig.configuration.acl.commands.handlers;

import luongdev.switchconfig.common.cqrs.EventRequestHandler;
import luongdev.switchconfig.configuration.acl.AccessControl;
import luongdev.switchconfig.configuration.acl.AccessControls;
import luongdev.switchconfig.configuration.acl.commands.UpdateAccessControlCommand;
import luongdev.switchconfig.configuration.acl.events.AccessControlPersistedEvent;
import luongdev.switchconfig.configuration.acl.exceptions.AccessControlNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UpdateAccessControlHandler extends EventRequestHandler<AccessControl, UpdateAccessControlCommand> {

    private final AccessControls accessControls;

    public UpdateAccessControlHandler(AccessControls accessControls) {
        this.accessControls = accessControls;
    }

    @Override
    public AccessControl handle(UpdateAccessControlCommand cmd) {
        var accessControl = accessControls
                .findById(cmd.getName())
                .orElseThrow(() -> new AccessControlNotFoundException(cmd.getName()));

        accessControl.setDescription(cmd.getDescription());

        accessControl = accessControls.save(accessControl);

        return withEvent(accessControl, new AccessControlPersistedEvent(this, accessControl));
    }

}
