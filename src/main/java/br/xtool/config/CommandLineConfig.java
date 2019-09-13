package br.xtool.config;

import br.xtool.core.AbstractCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import picocli.CommandLine;

import java.util.List;

@Configuration
public class CommandLineConfig {

    @Autowired
    private List<AbstractCommand> commands;

    @Bean
    public CommandLine getCommandLine() {
        CommandLine cmdLine = new CommandLine(new CoreCommand());
        for(AbstractCommand cmd : commands) {
            cmd.setup(cmdLine);
        }
        return cmdLine;
    }

    @CommandLine.Command(name = "", description = "Example interactive shell with completion")
    public static class CoreCommand implements Runnable {
        public void run() {
        }
    }
}

