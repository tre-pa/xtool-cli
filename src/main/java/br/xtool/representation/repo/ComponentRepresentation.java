package br.xtool.representation.repo;

import picocli.CommandLine;

import java.nio.file.Path;
import java.util.Map;

/**
 * Classe que representa um componente xtool.
 *
 * @author jcruz
 */
public interface ComponentRepresentation {

    /**
     * Nome do arquivo descritor xtool.
     */
    static String DESCRIPTOR_FILE = "xtool.yml";

    /**
     * Nome do componente. Por conveção é o mesmo nome do diretório.
     *
     * @return
     */
    String getName();

    /**
     * Nome do Repository + Nome do Módulo + Nome do Componente
     *
     * @return
     */
    String getFullyQualifiedName();

    /**
     * Caminho com o diretório de templates.
     *
     * @return
     */
    Path getTplPath();

    /**
     * Mapa com todas as chaves/valores do arquivo descritor do componente (xtool.yaml).
     *
     * @return Map<String, Object> com as chaves/valores do arquivo xtool.yaml do componente.
     */
    Map<String, Object> getDescriptor();

    /**
     * Retorna o CommandSpec do componente.
     *
     * @return
     */
    CommandLine.Model.CommandSpec getCommandSpec();

    /**
     * Retorna o módulo do componente.
     *
     * @return
     */
    ModuleRepresentation getModule();
}
