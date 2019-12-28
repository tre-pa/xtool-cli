package br.xtool.representation.repo.directive;

/**
 * Representação do descritor de componente (xtool.yml)
 */
public interface ComponentDescriptorRepresentation {

    /**
     * Retorna o nome do componente.
     *
     * @return
     */
    String getName();

    /**
     * Retorna a descrição do componente
     *
     * @return
     */
    String getDescription();

    /**
     * Retorna a versão do componente.
     *
     * @return
     */
    String getVersion();

}

