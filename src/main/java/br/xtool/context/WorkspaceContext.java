package br.xtool.context;

import br.xtool.representation.WorkspaceRepresentation;

/**
 * Serviços do workspace.
 *
 * @author jcruz
 */
public interface WorkspaceContext {


    /**
     * Retorna a representação do workspace.
     *
     * @return
     */
    WorkspaceRepresentation getWorkspace();

}
