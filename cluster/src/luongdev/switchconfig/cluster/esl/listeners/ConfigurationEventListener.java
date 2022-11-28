package luongdev.switchconfig.cluster.esl.listeners;

import luongdev.switchconfig.common.Caches;
import luongdev.switchconfig.configuration.acl.commands.GenerateAccessControlXmlCommand;
import luongld.cqrs.Bus;
import luongld.freeswitch.esl.IEslEventListener;
import luongld.freeswitch.esl.transport.event.EslEvent;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class ConfigurationEventListener implements IEslEventListener {

    private final Bus bus;
    private final RedisTemplate<String, String> redisTemplate;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final String CONFIG_HEADER_NAME = "Config-Name";
    private final String CONFIG_ACL = "acl.conf";

    public ConfigurationEventListener(Bus bus, RedisTemplate<String, String> redisTemplate) {
        this.bus = bus;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void eventReceived(String addr, EslEvent event) {
        if (event.getSubclass() == null || !"mepbx::config".equalsIgnoreCase(event.getSubclass())) return;

        var headers = event.getEventHeaders() == null ? new HashMap<String, String>() : event.getEventHeaders();
        if (!headers.containsKey(CONFIG_HEADER_NAME) || StringUtils.isEmpty(headers.get(CONFIG_HEADER_NAME))) return;

        if (CONFIG_ACL.equalsIgnoreCase(headers.get(CONFIG_HEADER_NAME))) {
            loadAccessControlConfigs();
        }
    }

    @Override
    public void backgroundJobResultReceived(String addr, EslEvent event) {

    }

    private void loadAccessControlConfigs() {
        var xml = bus.execute(new GenerateAccessControlXmlCommand());
        redisTemplate.opsForHash().put(Caches.CONFIGURATIONS.name(), "acl.conf", xml);
    }
}