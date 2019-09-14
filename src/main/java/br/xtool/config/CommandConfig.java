package br.xtool.config;

import br.xtool.core.AbstractCommand;
import org.apache.commons.collections.map.AbstractOrderedMapDecorator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import picocli.CommandLine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class CommandConfig {

    @Autowired
    private List<AbstractCommand> commands;

    @Bean
    public CommandLine getCommandLine() {
        CommandLine cmdLine = new CommandLine(new CoreCommand());
        for (AbstractCommand cmd : commands) {
            cmd.setup(cmdLine);
        }
        return cmdLine;
    }

    /**
     * Retorna a lista de comandos com o nome do comando como chave.
     *
     * @return
     */
    @Bean(name = "commands")
    public Map<String, AbstractCommand> getCommands() {
        Map<String, AbstractCommand> commandMap = new HashMap<>();
        for (AbstractCommand cmd : commands) {
            String cmdName = cmd.getClass().getAnnotation(CommandLine.Command.class).name();
            commandMap.put(cmdName, cmd);
        }
        return commandMap;
    }

    @CommandLine.Command(name = "", description = "Example interactive shell with completion")
    public static class CoreCommand implements Runnable {
        public void run() {
        }
    }
}

