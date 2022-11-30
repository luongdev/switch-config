package luongdev.switchconfig.domain.extension.events.handlers;

import luongdev.switchconfig.common.Caches;
import luongdev.switchconfig.common.util.JAXBUtil;
import luongdev.switchconfig.common.xml.Document;
import luongdev.switchconfig.common.xml.sections.DirectorySection;
import luongdev.switchconfig.common.xml.sections.directory.Directory;
import luongdev.switchconfig.common.xml.sections.directory.DirectoryDomain;
import luongdev.switchconfig.domain.extension.events.ExtensionCreatedEvent;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBException;

@Component
public class ExtensionCreatedEventHandler implements ApplicationListener<ExtensionCreatedEvent> {

    private final RedisTemplate<String, String> redisTemplate;

    public ExtensionCreatedEventHandler(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void onApplicationEvent(ExtensionCreatedEvent event) {
        var extension = event.getExtension();
        if (extension == null) return;

        var domainName = StringUtils.isEmpty(extension.getDomain()) ? "public" : extension.getDomain();
        var domainExtension = String.format("%s@%s", extension.getExtension(), domainName);

        var directory = new Directory(extension.getExtension())
                .callerIdName(extension.getExtension())
                .callerIdNumber(extension.getExtension())
                .extension(extension.getExtension(), domainName)
                .callTimeout(extension.getCallTimeout())
                .limitMax(extension.getLimitMax())
                .password(extension.getPassword());

        var document = new Document(new DirectorySection(new DirectoryDomain(domainName, directory)));

        try {
            var xml = JAXBUtil.marshallObject(document, false);
            System.out.println(xml);
            redisTemplate.opsForHash().put(Caches.DIRECTORIES.name(), domainExtension, xml);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
