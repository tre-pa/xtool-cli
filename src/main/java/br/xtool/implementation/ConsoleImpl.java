package br.xtool.implementation;

import br.xtool.core.Console;
import org.apache.commons.lang3.StringUtils;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;
import org.jline.reader.*;
import org.jline.reader.impl.DefaultParser;
import org.jline.reader.impl.LineReaderImpl;
import org.jline.reader.impl.history.DefaultHistory;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import picocli.CommandLine;
import picocli.shell.jline3.PicocliJLineCompleter;

import java.io.IOException;

@Service
public class ConsoleImpl implements Console {

    private Level level = Level.NORMAL;

    @Autowired
    private CommandLine cmd;

    @Autowired
    private ApplicationEventPublisher publisher;

    private Terminal terminal;

    private LineReader reader;

    @EventListener(ContextRefreshedEvent.class)
    private void init() throws IOException {
        AnsiConsole.systemInstall();
        this.terminal = TerminalBuilder.builder().build();
        this.reader = LineReaderBuilder.builder()
                .terminal(terminal)
                .history(new DefaultHistory())
                .completer(new PicocliJLineCompleter(cmd.getCommandSpec()))
                .parser(new DefaultParser())
                .build();
        String prompt = Ansi.ansi().bold().fg(Ansi.Color.YELLOW).a("xtool:$ ").reset().toString();
        String rightPrompt = null;
        String line;
        try {
            while (true) {
                try {
                    line = reader.readLine(prompt, rightPrompt, (MaskingCallback) null, null);
                    ParsedLine pl = reader.getParser().parse(line, 0);
                    String[] arguments = pl.words().toArray(new String[0]);
                    if (StringUtils.isBlank(arguments[0])) continue;
                    CommandLine.ParseResult parseResult = cmd.parseArgs(arguments); // ver CommandDispatcher.process
                    publisher.publishEvent(parseResult);
                } catch (UserInterruptException e) {
                    this.println("Pressione Ctrl+D para sair.");
                } catch (CommandLine.UnmatchedArgumentException e) {
                    this.println("Comando/Argumento não encontrado. ".concat(e.getMessage()));
                } catch (EndOfFileException e) {
                    return;
                } catch (Exception e) {
                    if(level.equals(Level.NORMAL)) this.printlnError(e.getMessage()); else e.printStackTrace();
                }
            }
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            AnsiConsole.systemUninstall();
        }
    }

    @Override
    public void clearScreen() {
        ((LineReaderImpl) reader).clearScreen();
    }

    @Override
    public void println(String msg) {
        System.out.println(Ansi.ansi().render(msg).reset());
    }

    private void printlnError(String msg) {
        System.out.println(Ansi.ansi().fg(Ansi.Color.RED).render(msg).reset());
    }

    @Override
    public void setLevel(Level level) {
        this.level = level;
    }

    @Override
    public Level getLevel() {
        return this.level;
    }

    @Override
    public void debug(String msg, Object...args) {
        if(level.equals(Level.DEBUG)) {
            System.out.println(Ansi.ansi().render(String.format(msg, args)).reset());
        }
    }
}
