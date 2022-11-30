package luongdev.switchconfig.domain.extension.events;

import luongdev.switchconfig.domain.extension.Extension;
import org.springframework.context.ApplicationEvent;

public class ExtensionCreatedEvent extends ApplicationEvent {

    private final Extension extension;

    public ExtensionCreatedEvent(Object source, Extension extension) {
        super(source);
        this.extension = extension;
    }

    public Extension getExtension() {
        return extension;
    }
}
