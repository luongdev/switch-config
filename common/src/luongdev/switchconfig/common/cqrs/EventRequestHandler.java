package luongdev.switchconfig.common.cqrs;

import luongdev.cqrs.Request;
import luongdev.cqrs.RequestHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;

public abstract class EventRequestHandler<R, C extends Request<R>> implements RequestHandler<R, C> {

    private ApplicationEventPublisher publisher;


    public <E extends ApplicationEvent> R withEvent(R r, E event) {
        if (this.publisher != null) this.publisher.publishEvent(event);

        return r;
    }

    @Autowired
    public void setPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }
}
