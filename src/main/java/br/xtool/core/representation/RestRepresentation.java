package br.xtool.core.representation;

import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;

public class RestRepresentation implements Comparable<RestRepresentation> {

	private SpringBootProjectRepresentation springBootProject;

	private JavaClassSource javaClassSource;

	private SortedSet<MethodRepresentation> httpGetMethods;

	private SortedSet<MethodRepresentation> httpPutMethods;

	private SortedSet<MethodRepresentation> httpPostMethods;

	private SortedSet<MethodRepresentation> httpDeleteMethods;

	public RestRepresentation(SpringBootProjectRepresentation springBootProject, JavaClassSource javaClassSource) {
		super();
		this.springBootProject = springBootProject;
		this.javaClassSource = javaClassSource;
	}

	/**
	 * Retorna o nome da classe Rest
	 * 
	 * @return
	 */
	public String getName() {
		return this.javaClassSource.getName();
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
	public SortedSet<MethodRepresentation> getHttpGetMethods() {
		if (this.httpGetMethods == null) {
			this.httpGetMethods = this.getHttpMethods("GetMapping", "RequestMethod.GET");
		}
		return this.httpGetMethods;
	}

	/**
	 * Retorna todos os métodos HTTP Put
	 * 
	 * @return
	 */
	public SortedSet<MethodRepresentation> getHttpPutMethods() {
		if (this.httpPutMethods == null) {
			this.httpGetMethods = this.getHttpMethods("PutMapping", "RequestMethod.PUT");
		}
		return httpPutMethods;
	}

	/**
	 * Retorna todos os métodos HTTP Post
	 * 
	 * @return
	 */
	public SortedSet<MethodRepresentation> getHttpPostMethods() {
		if (this.httpPostMethods == null) {
			this.httpPostMethods = this.getHttpMethods("PostMapping", "RequestMethod.POST");
		}
		return httpPostMethods;
	}

	/**
	 * Retorna todos os métodos HTTP Delete
	 * 
	 * @return
	 */
	public SortedSet<MethodRepresentation> getHttpDeleteMethods() {
		if (this.httpDeleteMethods == null) {
			this.httpDeleteMethods = this.getHttpMethods("DeleteMapping", "RequestMethod.DELETE");
		}
		return httpDeleteMethods;
	}

	private SortedSet<MethodRepresentation> getHttpMethods(String httpAnnotation, String requestMappingMethod) {
		Predicate<MethodSource<JavaClassSource>> hasHttpAnnotation = methodSource -> methodSource.hasAnnotation(httpAnnotation);
		Predicate<MethodSource<JavaClassSource>> hasRequestMapping = methodSource -> methodSource.hasAnnotation("RequestMapping");
		Predicate<MethodSource<JavaClassSource>> hasRequestMappingMethod = methodSource -> StringUtils.equals(methodSource.getAnnotation("RequestMapping").getStringValue("method"),
				requestMappingMethod);
		// @formatter:off
		return this.javaClassSource.getMethods()
			.stream()
			.filter(hasHttpAnnotation.or(hasRequestMapping.and(hasRequestMappingMethod)))
			.map(methodSource -> new MethodRepresentation(springBootProject, javaClassSource, methodSource))
			.collect(Collectors.toCollection(TreeSet::new));
		// @formatter:on
	}

	@Override
	public int compareTo(RestRepresentation o) {
		return this.getName().compareTo(o.getName());
	}

}
