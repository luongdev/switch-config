package luongdev.switchconfig.domain.extension.commands.handlers;

import luongdev.switchconfig.domain.extension.Extension;
import luongdev.switchconfig.domain.extension.Extensions;
import luongdev.switchconfig.domain.extension.commands.CreateExtensionCommand;
import luongdev.switchconfig.domain.extension.events.ExtensionCreatedEvent;
import luongdev.switchconfig.tenancy.datasource.DomainIdentifierResolver;
import luongdev.switchconfig.tenancy.queries.DomainByNameQuery;
import luongld.cqrs.Bus;
import luongld.cqrs.RequestHandler;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class CreateExtensionHandler implements RequestHandler<Extension, CreateExtensionCommand> {

    private final Bus bus;
    private final Extensions extensions;
    private final DomainIdentifierResolver resolver;
    private final ApplicationEventPublisher publisher;

    public CreateExtensionHandler(
            Bus bus,
            Extensions extensions,
            DomainIdentifierResolver resolver,
            ApplicationEventPublisher publisher) {
        this.bus = bus;
        this.extensions = extensions;
        this.resolver = resolver;
        this.publisher = publisher;
    }

    @Override
    public Extension handle(CreateExtensionCommand cmd) {
        resolver.publicDomain();

        var domain = bus.execute(new DomainByNameQuery(cmd.getDomain()));

        resolver.setCurrentDomain(domain.getDomain());

        var extension = new Extension(cmd.getExtension(), cmd.getDomain());
        extension.setPassword(cmd.getPassword());

        publisher.publishEvent(new ExtensionCreatedEvent(this, extension));

        return extensions.save(extension);
    }
}
