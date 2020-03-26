package br.xtool.console;

import br.xtool.xtoolcore.context.WorkspaceContext;
import br.xtool.xtoolcore.core.AbstractXtoolComponent;
import br.xtool.xtoolcore.core.Console;
import br.xtool.xtoolcore.representation.ProjectRepresentation;
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
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import picocli.CommandLine;
import picocli.shell.jline3.PicocliJLineCompleter;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
public class ConsoleImpl implements Console {

    private Level level = Level.NORMAL;

    @Autowired
    private List<? extends AbstractXtoolComponent> components;

    @Autowired
    private WorkspaceContext workspaceContext;

    private Terminal terminal;

    private LineReader reader;

    private ProjectRepresentation project;

    @EventListener(ContextRefreshedEvent.class)
    private void init() throws IOException {
        AnsiConsole.systemInstall();
        CoreCommand coreCommand = new CoreCommand();
        CommandLine cmd = new CommandLine(coreCommand);
        this.registerXtoolComponents(cmd);
        this.terminal = TerminalBuilder.builder().build();
        this.reader = LineReaderBuilder.builder()
                .terminal(terminal)
                .history(new DefaultHistory())
                .completer(new PicocliJLineCompleter(cmd.getCommandSpec()))
                .parser(new DefaultParser())
                .build();
        String rightPrompt = null;
        String line;
        try {
            while (true) {
                try {
                    String prompt = Ansi.ansi().render(getPromptFormat()).reset().toString();
                    line = reader.readLine(prompt, rightPrompt, (MaskingCallback) null, null);
                    if (line.matches("^\\s*#.*")) continue;
                    ParsedLine pl = reader.getParser().parse(line, 0);
                    String[] arguments = pl.words().toArray(new String[0]);
                    if (StringUtils.isBlank(arguments[0])) continue;
                    cmd.execute(arguments);
                } catch (UserInterruptException e) {
                    this.println("Pressione Ctrl+D para sair.");
                } catch (CommandLine.UnmatchedArgumentException e) {
                    this.println("Comando/Argumento não encontrado. ".concat(e.getMessage()));
//                    this.println(cmd.getUsageMessage());
                } catch (EndOfFileException e) {
                    return;
                } catch (Exception e) {
                    if (level.equals(Level.NORMAL)) this.printlnError(e.getMessage());
                    else e.printStackTrace();
                }
            }
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            AnsiConsole.systemUninstall();
        }
    }

    @CommandLine.Command(name = "", description = "", requiredOptionMarker = '*')
    public static class CoreCommand implements Runnable {
        public void run() {
        }
    }

    private void registerXtoolComponents(CommandLine coreCommandLine) {
        for(AbstractXtoolComponent component: components) {
            coreCommandLine.addSubcommand(component);
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

    @Override
    public void println(String msg, Object... args) {
        System.out.println(Ansi.ansi().render(String.format(msg, args)).reset());
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
    public void registerPromptProject(ProjectRepresentation project) {
        this.project = project;
    }

    private String getPromptFormat() {
        if (Objects.nonNull(this.project)) {
            String promptFormat = "@|bold,yellow xtool|@:@|bold,green %s|@/@|bold,white %s|@:$ ";
            return String.format(promptFormat, workspaceContext.getWorkspace().getPath().getFileName(), workspaceContext.getWorkingProject().getName());
        }
        String promptFormat = "@|bold,yellow xtool|@:@|bold,green %s|@:$ ";
        return String.format(promptFormat, workspaceContext.getWorkspace().getPath().getFileName());
    }

    @Override
    public void debug(String msg, Object... args) {
        if (level.equals(Level.DEBUG)) {
            System.out.println(Ansi.ansi().render(String.format(msg, args)).reset());
        }
    }
}
