package br.xtool.config;

import br.xtool.command.core.AbstractCommand;
import br.xtool.context.RepositoryContext;
import br.xtool.core.CommandFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import picocli.CommandLine;

import java.util.*;

@Configuration
public class CommandConfig {

    @Autowired
    private List<? extends AbstractCommand> commands;

    @Autowired
    private CommandFactory commandFactory;

    @Autowired
    private RepositoryContext repositoryContext;

    /**
     * Retorna todos os comandos do sistema.
     *
     * @return
     */
    @Bean
    public CommandLine getCommandLine() {
        CommandLine cmdLine = new CommandLine(new CoreCommand(), commandFactory);
        List<String> subcommands = new ArrayList<>();
        for (AbstractCommand cmd : commands) {
            // Registra os subcomandos
            CommandLine.Command cmdAnn = cmd.getClass().getAnnotation(CommandLine.Command.class);
            if(cmdAnn.subcommands().length > 0) {
                Arrays.asList(cmdAnn.subcommands()).stream()
                        .map(subcommandClass -> subcommandClass.getName())
                        .forEach(subcommandClass -> subcommands.add(subcommandClass));
            }
            if(subcommands.stream().noneMatch(s -> s.equals(cmd.getClass().getName()))) cmd.setup(cmdLine);
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

    @CommandLine.Command(name = "", description = "")
    public static class CoreCommand implements Runnable {
        public void run() {
        }
    }
}

