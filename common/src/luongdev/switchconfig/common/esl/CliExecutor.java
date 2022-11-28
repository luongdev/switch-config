package luongdev.switchconfig.common.esl;

import luongld.freeswitch.esl.transport.message.EslMessage;

import java.util.List;

public interface CliExecutor {

    List<EslMessage> submit(Cli cli);

}
