package luongdev.switchconfig.configuration.acl.commands.handlers;

import luongdev.switchconfig.configuration.acl.AccessControl;
import luongdev.switchconfig.configuration.acl.AccessControls;
import luongdev.switchconfig.configuration.acl.commands.DenyAccessControlCommand;
import luongld.cqrs.RequestHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Component
public class DenyAccessControlHandler implements RequestHandler<AccessControl, DenyAccessControlCommand> {

    private final AccessControls accessControls;

    public DenyAccessControlHandler(AccessControls accessControls) {
        this.accessControls = accessControls;
    }

    @Override
    @Transactional
    public AccessControl handle(DenyAccessControlCommand cmd) {
        var accessControl = accessControls
                .findById(cmd.getId())
                .orElseThrow(() -> new EntityNotFoundException("AccessControl[" + cmd.getId() + "]"));

        accessControl.addDetail(cmd.getCidr(), cmd.getDomain(), false, cmd.getDescription());

        return accessControls.save(accessControl);
    }
}
