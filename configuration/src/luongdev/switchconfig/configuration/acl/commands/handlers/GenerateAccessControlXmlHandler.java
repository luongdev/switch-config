package luongdev.switchconfig.configuration.acl.commands.handlers;

import luongdev.switchconfig.common.util.JAXBUtil;
import luongdev.switchconfig.common.xml.sections.configuration.accesscontrol.AccessControlConfiguration;
import luongdev.switchconfig.common.xml.sections.configuration.accesscontrol.NetworkContainer;
import luongdev.switchconfig.common.xml.sections.configuration.accesscontrol.NetworkList;
import luongdev.switchconfig.common.xml.sections.configuration.accesscontrol.NetworkNode;
import luongdev.switchconfig.configuration.acl.AccessControl;
import luongdev.switchconfig.configuration.acl.AccessControlDetail;
import luongdev.switchconfig.configuration.acl.AccessControls;
import luongdev.switchconfig.configuration.acl.commands.GenerateAccessControlXmlCommand;
import luongld.cqrs.RequestHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.bind.JAXBException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

@Component
public class GenerateAccessControlXmlHandler implements RequestHandler<String, GenerateAccessControlXmlCommand> {

    private final AccessControls accessControls;

    public GenerateAccessControlXmlHandler(AccessControls accessControls) throws JAXBException {
        this.accessControls = accessControls;
    }

    @Override
    @Transactional
    public String handle(GenerateAccessControlXmlCommand cmd) {
        var networks = new ArrayList<NetworkList>();

        var accessControls = this.accessControls.findAll();

        var eventSocketControl = accessControls.stream().anyMatch(a -> "event_socket".equalsIgnoreCase(a.getName()));

        for (var accessControl : accessControls) {
            var network = networkFrom(accessControl);
            if (network == null) continue;

            networks.add(network);
        }

        if (!eventSocketControl) {
            networks.add(new NetworkList("event_socket", false).node("127.0.0.1/32", null, true));
        }

        return marshal(networks);
    }

    private NetworkList networkFrom(AccessControl accessControl) {
        if (accessControl == null) return null;

        var accessControlDetails = accessControl.getAccessControlDetails() == null
                ? new HashSet<AccessControlDetail>()
                : accessControl.getAccessControlDetails().values();
        var nodes = accessControlDetails
                .stream()
                .map(v -> new NetworkNode(v.getCidr(), v.getDomain(), v.isAllow(), v.getDescription()))
                .toList();

        return new NetworkList(accessControl.getName(), accessControl.isAllow(), nodes);
    }

    private String marshal(List<NetworkList> networks) {
        if (networks == null) networks = Collections.emptyList();

        var accessControlConfiguration = new AccessControlConfiguration(new NetworkContainer(networks));
        try {
            return JAXBUtil.marshallObject(accessControlConfiguration, true);
        } catch (JAXBException ignored) {
            return AccessControlConfiguration.EMPTY_CONFIGURATION;
        }
    }
}
