package br.xtool.core.representation;

import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;

import br.xtool.core.representation.impl.EClassImpl;
import br.xtool.core.representation.impl.EMethodImpl;

public class ERest extends EClassImpl {

	private ESpringBootProject springBootProject;

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
	public SortedSet<EMethod> getHttpGetMethods() {
		return this.getHttpMethods("GetMapping", "RequestMethod.GET");
	}

	/**
	 * Retorna todos os métodos HTTP Put
	 * 
	 * @return
	 */
	public SortedSet<EMethod> getHttpPutMethods() {
		return this.getHttpMethods("PutMapping", "RequestMethod.PUT");
	}

	/**
	 * Retorna todos os métodos HTTP Post
	 * 
	 * @return
	 */
	public SortedSet<EMethod> getHttpPostMethods() {
		return this.getHttpMethods("PostMapping", "RequestMethod.POST");
	}

	/**
	 * Retorna todos os métodos HTTP Delete
	 * 
	 * @return
	 */
	public SortedSet<EMethod> getHttpDeleteMethods() {
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
			.map(EMethodImpl::new)
			.collect(Collectors.toCollection(TreeSet::new));
		// @formatter:on
	}

}
