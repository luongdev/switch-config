package luongdev.switchconfig.webapi;

import luongdev.cqrs.Bus;
import luongdev.switchconfig.common.esl.CliExecutor;
import luongdev.switchconfig.common.util.JAXBUtil;
import luongdev.switchconfig.common.xml.sections.DirectorySection;
import luongdev.switchconfig.common.xml.sections.directory.DomainUser;
import luongdev.switchconfig.common.xml.sections.directory.UserBase;
import luongdev.switchconfig.common.xml.sections.directory.UserPointer;
import luongdev.switchconfig.common.xml.sections.directory.UserXml;
import luongdev.switchconfig.domain.directory.Group;
import luongdev.switchconfig.domain.directory.Users;
import luongdev.switchconfig.domain.directory.builder.UserBuilder;
import luongdev.switchconfig.domain.extension.Extensions;
import luongdev.switchconfig.tenancy.Domains;
import luongdev.switchconfig.tenancy.datasource.DomainIdentifierResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.RedisTemplate;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

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
    Users users;

    @Autowired
    Bus bus;

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @Autowired
    CliExecutor cliExecutor;



    public static void main(String[] args) throws JAXBException {
        SpringApplication.run(WebAPI.class, args);
    }

    @Override
    public void run(String... args) throws JAXBException {

        var group1 = new Group("10001", "voice.metechvn.com", "Group 1");
        var group2 = new Group("10002", "voice.metechvn.com", "Group 2");
        var group3 = new Group("10003", "voice.metechvn.com", "Group 3");

        var builder = UserBuilder.builder()
                .group(group1)
                .group(group2)
                .group(group3)
                .name("Agent 1000")
                .extension("1000")
                .record("all")
                .domain("voice.metechvn.com");


        var user = builder.build();


        var xmlUser = new UserXml();
        xmlUser.setId(user.getExtension());

        var section = new DirectorySection(new DomainUser(user.getDomain(), xmlUser));
        if (user.getSettings() != null && !user.getSettings().isEmpty()) {
            for (var entry : user.getSettings().entrySet()) {
                if (entry.getValue() == null) continue;

                if (entry.getValue().isVariable()) {
                    xmlUser.variable(entry.getKey(), entry.getValue().getValue());
                } else {
                    xmlUser.param(entry.getKey(), entry.getValue().getValue());
                }
            }
        }

        user.setXml(JAXBUtil.marshallObject(section, false));

        users.save(user);
//
//        System.out.println(user);

//        resolver.publicDomain();
//
//        var user = new User();
//        user.setExtension("1000");
//        user.setPassword("Abc@123");
//        user.setDomain("public");
//        user
//                .variable("record_stereo", "true")
//                .variable("presence_id", String.format("%s@%s", user.getExtension(), user.getDomain()))
//                .variable("export_vars", "domain_name")
//                .variable("domain_name", user.getDomain())
//                .variable("extension", user.getExtension())
//                .variable("password", user.getPassword())
//                .param("dial-string", "{sip_invite_domain=${domain_name},leg_timeout=${call_timeout},presence_id=${dialed_user}@${dialed_domain}}${sofia_contact(*/${dialed_user}@${dialed_domain})}")
//        ;
//
//        user.join(new Group("1000", "public", "Group_1000"));
//
//        users.save(user);
//
//
//        var users = this.users.findAllIncludeSettingsAndGroups();
//
//        for (var user : users) {
//            System.out.println(user.getExtension());
//
//            for (var entry : user.getSettings().entrySet()) {
//                System.out.printf("%s = %s%n", entry.getKey(), entry.getValue().getValue());
//            }
//
//            for (var entry : user.getGroups().entrySet()) {
//                System.out.println("Group = " + entry.getValue().getName());
//            }
//        }

//
//        var domain = new Domain("voice.metechvn.com");
//        domain.setDbPassword("Default");
//        domain.setDbHost("localhost");
//        domain.setDbPort((short) 5432);
//        domain.setDbUser("postgres");
//        domain.setEnabled(true);
//
//        domains.save(domain);


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
//        var xml = bus.execute(new GenerateAccessControlXmlCommand());
//
//        redisTemplate.opsForHash().put(Caches.CONFIGURATIONS.name(), "acl.conf", xml);
//
//        System.out.println(xml);
//
//        var luaCli = new Lua("fire", "mepbx::agent", "configuration-loaded");
//        var res = cliExecutor.submit(luaCli);
//
//        System.out.println(res.get(0));

//        bus.execute(new CreateExtensionCommand("10000", "voice.metechvn.com"));
    }
}
