package br.xtool.core.representation;

import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;

import lombok.Getter;

public class ERest extends EClass implements Comparable<ERest> {

	private ESpringBootProject springBootProject;

	@Getter(lazy = true)
	private final SortedSet<EMethod> httpGetMethods = buildHttpGetMethods();

	@Getter(lazy = true)
	private final SortedSet<EMethod> httpPutMethods = buildHttpPutMethods();

	@Getter(lazy = true)
	private final SortedSet<EMethod> httpPostMethods = buildHttpPostMethods();

	@Getter(lazy = true)
	private final SortedSet<EMethod> httpDeleteMethods = buildHttpDeleteMethods();

	public ERest(ESpringBootProject springBootProject, JavaClassSource javaClassSource) {
		super(springBootProject, javaClassSource);
		this.springBootProject = springBootProject;
	}

	/**
	 * Retorna o contexto raiz da api rest.
	 * 
	 * @return Contexto raiz
	 */
	public Optional<String> getRootPath() {
		return Optional.ofNullable(this.javaClassSource.getAnnotation("RequestMapping").getStringValue());
	}

	/**
	 * Retorna todos os métodos HTTP Get
	 * 
	 * @return
	 */
	private SortedSet<EMethod> buildHttpGetMethods() {
		return this.getHttpMethods("GetMapping", "RequestMethod.GET");
	}

	/**
	 * Retorna todos os métodos HTTP Put
	 * 
	 * @return
	 */
	private SortedSet<EMethod> buildHttpPutMethods() {
		return this.getHttpMethods("PutMapping", "RequestMethod.PUT");
	}

	/**
	 * Retorna todos os métodos HTTP Post
	 * 
	 * @return
	 */
	private SortedSet<EMethod> buildHttpPostMethods() {
		return this.getHttpMethods("PostMapping", "RequestMethod.POST");
	}

	/**
	 * Retorna todos os métodos HTTP Delete
	 * 
	 * @return
	 */
	private SortedSet<EMethod> buildHttpDeleteMethods() {
		return this.getHttpMethods("DeleteMapping", "RequestMethod.DELETE");
	}

	private SortedSet<EMethod> getHttpMethods(String httpAnnotation, String requestMappingMethod) {
		Predicate<MethodSource<JavaClassSource>> hasHttpAnnotation = methodSource -> methodSource.hasAnnotation(httpAnnotation);
		Predicate<MethodSource<JavaClassSource>> hasRequestMapping = methodSource -> methodSource.hasAnnotation("RequestMapping");
		Predicate<MethodSource<JavaClassSource>> hasRequestMappingMethod = methodSource -> StringUtils.equals(methodSource.getAnnotation("RequestMapping").getStringValue("method"),
				requestMappingMethod);
		// @formatter:off
		return this.javaClassSource.getMethods()
			.stream()
			.filter(hasHttpAnnotation.or(hasRequestMapping.and(hasRequestMappingMethod)))
			.map(EMethod::new)
			.collect(Collectors.toCollection(TreeSet::new));
		// @formatter:on
	}

	@Override
	public int compareTo(ERest o) {
		return this.getName().compareTo(o.getName());
	}

}
