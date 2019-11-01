package br.xtool.representation.repo.directive;

import br.xtool.type.JpaEntity;
import br.xtool.type.NgModule;
import picocli.CommandLine;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Classe que representa um parametro de um componente.
 */
public interface ParamDirectiveRepresentation {

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
    boolean isRequired();

    /**
     * Retorna o tipo de parametro.
     *
     * @return
     */
    Class<?> getParamType();

    /**
     * Retorna o converter do parametro.
     *
     * @return
     */
    Optional<? extends CommandLine.ITypeConverter<?>> getConverter();

    /**
     * Retorna o completer do parametro.
     *
     * @return
     */
    Optional<? extends Iterable<String>> getCompleter();

    /**
     * Retorna a representação {@link picocli.CommandLine.Model.OptionSpec} do parametro.
     *
     * @return
     */
    CommandLine.Model.OptionSpec getOptionSpec();

}
