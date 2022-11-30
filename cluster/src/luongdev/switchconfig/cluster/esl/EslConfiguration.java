package luongdev.switchconfig.cluster.esl;

import luongdev.cqrs.Bus;
import luongdev.switchconfig.cluster.queries.GetAllInstancesQuery;
import luongdev.switchconfig.common.esl.CliExecutor;
import luongdev.freeswitch.esl.IEslEventListener;
import luongdev.freeswitch.esl.InboundClient;
import luongdev.freeswitch.esl.ServerConnectionListener;
import luongdev.freeswitch.esl.inbound.NettyInboundClient;
import luongdev.freeswitch.esl.inbound.option.InboundClientOption;
import luongdev.freeswitch.esl.inbound.option.ServerOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EslConfiguration {

    private final String defaultPassword;
    private final int defaultTimeout;
    private final int readTimeout;

    private final ApplicationContext applicationContext;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public EslConfiguration(
            ApplicationContext applicationContext,
            @Value("${app.pbx.default-password:ClueCon}") String defaultPassword,
            @Value("${app.pbx.default-timeout-sec:10}") int defaultTimeout,
            @Value("${app.pbx.read-timeout-sec:10}") int readTimeout) {
        this.defaultPassword = defaultPassword;
        this.defaultTimeout = defaultTimeout;
        this.readTimeout = readTimeout;
        this.applicationContext = applicationContext;
    }

    @Bean
    public InboundClient eventSocketRegistry(InboundClientOption option) {
        var inboundClient = new NettyInboundClient(option);
        inboundClient.start();

        return inboundClient;
    }

    @Bean
    public CliExecutor cliExecutor(InboundClient inboundClient) {
        return cli -> cli.apply(inboundClient);
    }


    @Bean
    public InboundClientOption inboundClientOption(Bus bus, ServerConnectionListener serverConnectionListener) {
        var instances = bus.execute(new GetAllInstancesQuery());

        var eventListeners = applicationContext.getBeansOfType(IEslEventListener.class);

        var inboundClientOption = new InboundClientOption()
                .defaultPassword(defaultPassword)
                .defaultTimeoutSeconds(defaultTimeout)
                .readTimeoutSeconds(readTimeout)
                .serverConnectionListener(serverConnectionListener)
                .addEvents("ALL")
                .addEventFilter("Event-Subclass", "mepbx::config");

        for (var entry : eventListeners.entrySet()) {
            inboundClientOption.addListener(entry.getValue());
        }

        for (var instance : instances) {
            var serverOption = new ServerOption(instance.getHost(), instance.getSocketPort())
                    .password(instance.getSocketPassword());
            inboundClientOption.addServerOption(serverOption);
        }

        return inboundClientOption;
    }

    @Bean
    public ServerConnectionListener serverConnectionListener() {

        return new ServerConnectionListener() {
            @Override
            public void onOpened(ServerOption serverOption, InboundClient inboundClient) {
                log.info("\t\tPBX instance [{}] connected", serverOption.addr());
            }

            @Override
            public void onClosed(ServerOption serverOption) {
                log.info("\t\tPBX instance [{}] disconnected", serverOption.addr());
            }
        };
    }

}
