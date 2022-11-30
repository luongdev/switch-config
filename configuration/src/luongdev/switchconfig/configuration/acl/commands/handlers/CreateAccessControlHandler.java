package luongdev.switchconfig.configuration.acl.commands.handlers;

import luongdev.switchconfig.common.cqrs.EventRequestHandler;
import luongdev.switchconfig.configuration.acl.AccessControl;
import luongdev.switchconfig.configuration.acl.AccessControls;
import luongdev.switchconfig.configuration.acl.commands.CreateAccessControlCommand;
import luongdev.switchconfig.configuration.acl.events.AccessControlPersistedEvent;
import org.springframework.stereotype.Component;

@Component
public class CreateAccessControlHandler extends EventRequestHandler<AccessControl, CreateAccessControlCommand> {

    private final AccessControls accessControls;

    public CreateAccessControlHandler(AccessControls accessControls) {
        this.accessControls = accessControls;
    }

    @Override
    public AccessControl handle(CreateAccessControlCommand cmd) {
        var accessControl = new AccessControl(cmd.getName(), cmd.isAllow(), cmd.getDescription());

        for (var allow : cmd.getAllows().entrySet()) accessControl.addDetail(allow.getKey(), allow.getValue(), true);
        for (var deny : cmd.getDenies().entrySet()) accessControl.addDetail(deny.getKey(), deny.getValue(), false);

        accessControl = accessControls.save(accessControl);

        return withEvent(accessControl, new AccessControlPersistedEvent(this, accessControl));
    }

}
