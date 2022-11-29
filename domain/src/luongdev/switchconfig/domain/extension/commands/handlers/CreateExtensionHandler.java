package luongdev.switchconfig.domain.extension.commands.handlers;

import luongdev.switchconfig.domain.extension.Extension;
import luongdev.switchconfig.domain.extension.Extensions;
import luongdev.switchconfig.domain.extension.commands.CreateExtensionCommand;
import luongdev.switchconfig.domain.extension.events.ExtensionCreatedEvent;
import luongdev.switchconfig.tenancy.datasource.DomainIdentifierResolver;
import luongld.cqrs.RequestHandler;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class CreateExtensionHandler implements RequestHandler<Extension, CreateExtensionCommand> {

    private final Extensions extensions;
    private final DomainIdentifierResolver resolver;
    private final ApplicationEventPublisher publisher;

    public CreateExtensionHandler(
            Extensions extensions,
            DomainIdentifierResolver resolver,
            ApplicationEventPublisher publisher) {
        this.extensions = extensions;
        this.resolver = resolver;
        this.publisher = publisher;
    }

    @Override
    public Extension handle(CreateExtensionCommand cmd) {

        resolver.setCurrentDomain(cmd.getDomain());

        var extension = new Extension(cmd.getExtension(), cmd.getDomain());
        extension.setPassword(cmd.getPassword());
        extension.setDialString(cmd.getDialString());

        publisher.publishEvent(new ExtensionCreatedEvent(this, extension));

        return extensions.save(extension);
    }
}
