package br.xtool.command.core;


import br.xtool.annotation.OptionFn;
import br.xtool.core.Console;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import picocli.CommandLine;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe base para todos os comandos Xtool.
 */
public abstract class AbstractCommand implements Runnable {

    @Setter
    @Getter
    private CommandLine.ParseResult parseResult;

    @Autowired
    private Console console;

    private Map<String, Method> optionsFnList = new HashMap<>();

    @Setter
    @Getter
    private Map<String, AbstractCommand> commandList;

//    @Autowired
//    @Qualifier("commands")
//    private Map<String, AbstractCommand> commandList;

    @PostConstruct
    private void init() {
//        this.getClass().getMethods().
        for(int i=0; i < this.getClass().getDeclaredMethods().length; i++) {
            Method method = this.getClass().getDeclaredMethods()[i];
            if(method.isAnnotationPresent(OptionFn.class)) {
                String optionName = method.getAnnotation(OptionFn.class).value();
                optionsFnList.put(optionName, method);
            }
        }
    }

    /**
     * Envia o mainCommandLine para inclusao de subcomandos.
     * @param mainCommandLine
     */
    public void setup(CommandLine mainCommandLine) {
        String commandName = this.getClass().getAnnotation(CommandLine.Command.class).name();
        mainCommandLine.addSubcommand(commandName, this);
    }

    protected void execSubcommands() {
        String commandName = parseResult.subcommand().subcommand().commandSpec().name();
        if(commandList.containsKey(commandName)) {
            commandList.get(commandName).run();
        }
    }

    protected void execOptions() {
        parseResult.subcommand().matchedOptions().stream()
                .filter(op -> optionsFnList.containsKey(op.names()[0]))
                .forEach(op -> invoke(op));
    }

    @SneakyThrows
    private Object invoke(CommandLine.Model.OptionSpec op)  {
        console.debug("%s.invoke(%s)", this.getClass().getSimpleName(), op.names()[0]);
        return optionsFnList.get(op.names()[0]).invoke(this);
    }

    @Override
    public void run() {
        if(parseResult.subcommand().hasSubcommand()) {
//            console.debug("Executando subcommand %s", parseResult.subcommand().commandSpec().name());
            execSubcommands();
            return;
        }
//        System.out.println(parseResult.subcommand().matchedOptions());
        execOptions();
    }
}
