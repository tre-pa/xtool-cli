package br.xtool.core.representation.impl;

import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;

import br.xtool.core.representation.EMethod;
import br.xtool.core.representation.ERest;
import br.xtool.core.representation.EBootProject;

public class ERestImpl extends EClassImpl implements ERest {

	private EBootProject springBootProject;

	public ERestImpl(EBootProject springBootProject, JavaClassSource javaClassSource) {
		super(springBootProject, javaClassSource);
		this.springBootProject = springBootProject;
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.ERest#getRootPath()
	 */
	@Override
	public Optional<String> getRootPath() {
		return Optional.ofNullable(this.javaClassSource.getAnnotation("RequestMapping").getStringValue());
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.ERest#getHttpGetMethods()
	 */
	@Override
	public SortedSet<EMethod> getHttpGETMethods() {
		return this.getHttpMethods("GetMapping", "RequestMethod.GET");
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.ERest#getHttpPutMethods()
	 */
	@Override
	public SortedSet<EMethod> getHttpPUTMethods() {
		return this.getHttpMethods("PutMapping", "RequestMethod.PUT");
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.ERest#getHttpPostMethods()
	 */
	@Override
	public SortedSet<EMethod> getHttpPOSTMethods() {
		return this.getHttpMethods("PostMapping", "RequestMethod.POST");
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.ERest#getHttpDeleteMethods()
	 */
	@Override
	public SortedSet<EMethod> getHttpDELETEMethods() {
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
