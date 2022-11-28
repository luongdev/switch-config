package luongdev.switchconfig.configuration.acl.commands.handlers;

import luongdev.switchconfig.configuration.acl.AccessControl;
import luongdev.switchconfig.configuration.acl.AccessControls;
import luongdev.switchconfig.configuration.acl.commands.CreateAccessControlCommand;
import luongld.cqrs.RequestHandler;
import org.springframework.stereotype.Component;

@Component
public class CreateAccessControlHandler implements RequestHandler<AccessControl, CreateAccessControlCommand> {

    private final AccessControls accessControls;

    public CreateAccessControlHandler(AccessControls accessControls) {
        this.accessControls = accessControls;
    }

    @Override
    public AccessControl handle(CreateAccessControlCommand cmd) {
        var accessControl = new AccessControl(cmd.getName(), cmd.isAllow(), cmd.getDescription());

        for (var allow : cmd.getAllows().entrySet()) accessControl.addDetail(allow.getKey(), allow.getValue(), true);
        for (var deny : cmd.getDenies().entrySet()) accessControl.addDetail(deny.getKey(), deny.getValue(), false);

        accessControls.save(accessControl);

        return accessControl;
    }

}
