package luongdev.switchconfig.common.esl;

import luongdev.freeswitch.esl.InboundClient;
import luongdev.freeswitch.esl.transport.message.EslMessage;

import java.util.List;

public interface Cli {

    List<EslMessage> apply(InboundClient inboundClient);

}
