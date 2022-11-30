package luongdev.switchconfig.configuration.acl.events.handlers;

import luongdev.cqrs.Bus;
import luongdev.switchconfig.common.Caches;
import luongdev.switchconfig.configuration.acl.commands.GenerateAccessControlXmlCommand;
import luongdev.switchconfig.configuration.acl.events.AccessControlPersistedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;

@Component
public class AccessControlPersistedHandler implements ApplicationListener<AccessControlPersistedEvent> {

    private final Bus bus;
    private final RedisTemplate<String, String> redisTemplate;

    public AccessControlPersistedHandler(
            Bus bus,
            RedisTemplate<String, String> redisTemplate) {
        this.bus = bus;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void onApplicationEvent(@Nullable AccessControlPersistedEvent event) {
        var xml = bus.execute(new GenerateAccessControlXmlCommand());

        redisTemplate.opsForHash().put(Caches.CONFIGURATIONS.name(), "acl.conf", xml);
    }
}
