package br.xtool.representation.repo;

import picocli.CommandLine;

import java.nio.file.Path;
import java.util.Set;

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
    String getName();

    /**
     * Caminho com o diretório de templates.
     *
     * @return
     */
    Path getTplPath();

    /**
     * Retorna a representação do arquivo descritor xtool.yml
     *
     * @return
     */
    DescriptorRepresentation getDescriptor();


    /**
     * Retorna o módulo do componente.
     *
     * @return
     */
    ModuleRepresentation getModule();
//
//    /**
//     * Retorna o CommandSpec do componente.
//     *
//     * @return
//     */
//    CommandLine.Model.CommandSpec getCommandSpec();
//
//    /**
//     * Retorna os parametros do componente.
//     *
//     * @return
//     */
//    Set<ParamDirectiveRepresentation> getComponentParams();
}
