package br.xtool.command.completer;

import br.xtool.context.WorkspaceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Iterator;

/**
 * Classe Completer para entidades JPA.
 */
@Component
public class JpaEntityCompleter implements Iterable<String> {

    @Autowired
    private WorkspaceContext workspaceContext;

    @Override
    public Iterator<String> iterator() {
        return null;
    }

}
