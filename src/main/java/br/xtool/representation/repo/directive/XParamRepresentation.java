package br.xtool.representation.repo.directive;

/**
 * Classe que representa um parametro de um componente.
 */
public interface XParamRepresentation {

    /**
     * Retorna o Id do parametro
     *
     * @return
     */
    String getId();

    /**
     * Retorna no nome o parametro.
     *
     * @return
     */
    String getLabel();

    /**
     * Retorna a descrição do parametro.
     *
     * @return
     */
    String getDescription();

    /**
     * Retorna se o parametro é requerido.
     *
     * @return
     */
    Boolean isRequired();

    /**
     * Retorna o tipo de parametro.
     *
     * @return
     */
    Class<?> getType();

}
