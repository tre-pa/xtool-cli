package br.xtool.representation.repo;


import java.nio.file.Path;

/**
 * Classe que representa um componente xtool.
 *
 * @author jcruz
 */
public interface ComponentRepresentation {

    /**
     * Retorna o caminho do componente
     *
     * @return
     */
    Path getPath();

    /**
     * Nome do componente. Por conveção é o mesmo nome do diretório.
     *
     * @return
     */
    String getSimpleName();


    /**
     * Retorna o nome do componente junto com o modulo.
     *
     * @return
     */
    String getName();

    /**
     * Caminho com o diretório de templates.
     *
     * @return
     */
    Path getTplPath();

    /**
     * Caminho para o diretório de templates parciais.
     *
     * @return
     */
    Path getTplPartialsPath();


}
