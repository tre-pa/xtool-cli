package br.xtool.context;

import br.xtool.representation.ProjectRepresentation;
import br.xtool.representation.WorkspaceRepresentation;

import java.util.Map;

/**
 * Classe com o context para a execução dos comandos através das tasks.
 */
public interface DescriptorContext {
    /**
     * Retorna a referência do projeto.
     *
     * @return
     */
    ProjectRepresentation getProject();

    /**
     * Retorna a referência do workspace.
     *
     * @return
     */
    WorkspaceRepresentation getWorkspace();

    /**
     * Retorna a referência dos parametros.
     *
     * @return
     */
    Map<String, Object> getParams();
}
