package luongdev.switchconfig.common.esl;

import luongdev.freeswitch.esl.transport.message.EslMessage;

import java.util.List;

public interface CliExecutor {

    List<EslMessage> submit(Cli cli);

}
