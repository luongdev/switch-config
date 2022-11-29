package luongdev.switchconfig.domain.extension.events.handlers;

import luongdev.switchconfig.domain.extension.events.ExtensionCreatedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ExtensionCreatedEventHandler implements ApplicationListener<ExtensionCreatedEvent> {

    @Override
    public void onApplicationEvent(ExtensionCreatedEvent event) {
        System.out.println("extension created trigger from " + event.getSource() + " " + event.getExtension().getExtension());
    }
}
