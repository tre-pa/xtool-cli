package br.xtool.context;

import br.xtool.representation.ProjectRepresentation;
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

    /**
     * Define o projeto de trabalho.
     *
     * @param projectRepresentation
     */
    void setWorkingProject(ProjectRepresentation projectRepresentation);


    /**
     * Retorna o projeto de trabalho.
     *
     * @return
     */
    ProjectRepresentation getWorkingProject();
}
