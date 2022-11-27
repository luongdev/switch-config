package luongdev.switchconfig.configuration.acl.commands.handlers;

import luongdev.switchconfig.configuration.acl.AccessControl;
import luongdev.switchconfig.configuration.acl.AccessControls;
import luongdev.switchconfig.configuration.acl.commands.UpdateAccessControlCommand;
import luongld.cqrs.RequestHandler;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;

@Component
public class UpdateAccessControlHandler implements RequestHandler<AccessControl, UpdateAccessControlCommand> {

    private final AccessControls accessControls;

    public UpdateAccessControlHandler(AccessControls accessControls) {
        this.accessControls = accessControls;
    }

    @Override
    public AccessControl handle(UpdateAccessControlCommand cmd) {
        var accessControl = accessControls
                .findById(cmd.getId())
                .orElseThrow(() -> new EntityNotFoundException("AccessControl[" + cmd.getId() + "]"));

        accessControl.setName(cmd.getName());
        accessControl.setDescription(cmd.getDescription());

        return accessControls.save(accessControl);
    }

}
