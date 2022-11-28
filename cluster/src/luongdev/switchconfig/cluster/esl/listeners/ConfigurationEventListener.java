package luongdev.switchconfig.cluster.esl.listeners;

import luongdev.switchconfig.common.Caches;
import luongdev.switchconfig.configuration.acl.commands.GenerateAccessControlXmlCommand;
import luongld.cqrs.Bus;
import luongld.freeswitch.esl.IEslEventListener;
import luongld.freeswitch.esl.transport.event.EslEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class ConfigurationEventListener implements IEslEventListener {

    private final Bus bus;
    private final RedisTemplate<String, String> redisTemplate;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public ConfigurationEventListener(Bus bus, RedisTemplate<String, String> redisTemplate) {
        this.bus = bus;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void eventReceived(String addr, EslEvent event) {
        if (event.getSubclass() == null || !"mepbx::config".equalsIgnoreCase(event.getSubclass())) return;

        var xml = bus.execute(new GenerateAccessControlXmlCommand());
        redisTemplate.opsForHash().put(Caches.CONFIGURATIONS.name(), "acl.conf", xml);
    }

    @Override
    public void backgroundJobResultReceived(String addr, EslEvent event) {

    }
}