package br.xtool.config;

import br.xtool.core.AbstractCommand;
import br.xtool.core.RepositoryContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import picocli.CommandLine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class CommandConfig {

    @Autowired
    private List<AbstractCommand> commands;

    @Autowired
    private RepositoryContext repositoryContext;

    /**
     * Retorna todos os comandos do sistema.
     *
     * @return
     */
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

