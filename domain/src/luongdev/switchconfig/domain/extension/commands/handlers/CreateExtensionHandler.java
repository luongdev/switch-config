package luongdev.switchconfig.domain.extension.commands.handlers;

import luongdev.cqrs.Bus;
import luongdev.switchconfig.common.cqrs.EventRequestHandler;
import luongdev.switchconfig.domain.extension.Extension;
import luongdev.switchconfig.domain.extension.Extensions;
import luongdev.switchconfig.domain.extension.commands.CreateExtensionCommand;
import luongdev.switchconfig.domain.extension.events.ExtensionCreatedEvent;
import luongdev.switchconfig.tenancy.datasource.DomainIdentifierResolver;
import luongdev.switchconfig.tenancy.queries.DomainByNameQuery;
import org.springframework.stereotype.Component;

@Component
public class CreateExtensionHandler extends EventRequestHandler<Extension, CreateExtensionCommand> {

    private final Bus bus;
    private final Extensions extensions;
    private final DomainIdentifierResolver resolver;

    public CreateExtensionHandler(
            Bus bus,
            Extensions extensions,
            DomainIdentifierResolver resolver) {
        this.bus = bus;
        this.extensions = extensions;
        this.resolver = resolver;
    }

    @Override
    public Extension handle(CreateExtensionCommand cmd) {
        resolver.publicDomain();

        var domain = bus.execute(new DomainByNameQuery(cmd.getDomain()));

        resolver.setCurrentDomain(domain.getDomain());

        var extension = new Extension(cmd.getExtension(), cmd.getDomain());
        extension.setPassword(cmd.getPassword());

        extensions.save(extension);

        return withEvent(extension, new ExtensionCreatedEvent(this, extension));
    }
}
