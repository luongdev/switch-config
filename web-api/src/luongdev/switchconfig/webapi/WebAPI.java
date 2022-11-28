package luongdev.switchconfig.webapi;

import luongdev.switchconfig.common.Caches;
import luongdev.switchconfig.configuration.acl.commands.GenerateAccessControlXmlCommand;
import luongdev.switchconfig.domain.extension.Extensions;
import luongdev.switchconfig.tenancy.Domains;
import luongdev.switchconfig.tenancy.datasource.DomainIdentifierResolver;
import luongld.cqrs.Bus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootApplication(scanBasePackages = {
        "luongdev.switchconfig.tenancy",
        "luongdev.switchconfig.domain",
        "luongdev.switchconfig.cluster",
        "luongdev.switchconfig.configuration",
        "luongdev.switchconfig.webapi"
})
public class WebAPI implements CommandLineRunner {

    @Autowired
    DomainIdentifierResolver resolver;

    @Autowired
    Extensions extensions;

    @Autowired
    Domains domains;

    @Autowired
    Bus bus;

    @Autowired
    RedisTemplate<String, String> redisTemplate;


    public static void main(String[] args) {
        SpringApplication.run(WebAPI.class, args);
    }

    @Override
    public void run(String... args) {

//        redisTemplate.opsForHash().put("Object_key", "Hash_key", "Hash_value");
//        resolver.publicDomain();
//
//        var a = bus.execute(new CreateAccessControlCommand(
//                        "event_socket",
//                        false
//                )
//                        .allow("127.0.0.1/32", "voice.metechvn.com")
//                        .allow("192.168.100.10/32", "voice.metechvn.com")
//                        .allow("172.16.88.0/24", "voice.metechvn.com")
//                        .deny("172.16.86.0/24", "voice.metechvn.com")
//
//        );
//
        var xml = bus.execute(new GenerateAccessControlXmlCommand());

        redisTemplate.opsForHash().put(Caches.CONFIGURATIONS.name(), "acl.conf", xml);

        System.out.println(xml);

//        var domain = new Domain("voice.metechvn.com");
//        domain.setDbHost("localhost");
//        domain.setDbPassword("Default");
//
//        domains.save(domain);
//
//        resolver.setCurrentDomain("voice.metechvn.com");
//
//        var ext = new Extension();
//        ext.setExtension(1000);
//        ext.setAccountCode("");
//        ext.setCallTimeout(20);
//        ext.setDomain("public");
//        ext.setPassword("public");
//        ext.setEnabled(true);
//        ext.setLimitMax(0);
//        ext.setDialString("");
//
//        extensions.save(ext);
    }
}
