package br.xtool.core.representation.impl;

import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;

import br.xtool.core.representation.EBootProject;
import br.xtool.core.representation.EBootRest;
import br.xtool.core.representation.EJavaMethod;

public class EJavaRestImpl extends EJavaClassImpl implements EBootRest {

	public EJavaRestImpl(EBootProject bootProject, JavaClassSource javaClassSource) {
		super(bootProject, javaClassSource);
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
	public SortedSet<EJavaMethod<JavaClassSource>> getHttpGETMethods() {
		return this.getHttpMethods("GetMapping", "RequestMethod.GET");
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.ERest#getHttpPutMethods()
	 */
	@Override
	public SortedSet<EJavaMethod<JavaClassSource>> getHttpPUTMethods() {
		return this.getHttpMethods("PutMapping", "RequestMethod.PUT");
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.ERest#getHttpPostMethods()
	 */
	@Override
	public SortedSet<EJavaMethod<JavaClassSource>> getHttpPOSTMethods() {
		return this.getHttpMethods("PostMapping", "RequestMethod.POST");
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.ERest#getHttpDeleteMethods()
	 */
	@Override
	public SortedSet<EJavaMethod<JavaClassSource>> getHttpDELETEMethods() {
		return this.getHttpMethods("DeleteMapping", "RequestMethod.DELETE");
	}

	private SortedSet<EJavaMethod<JavaClassSource>> getHttpMethods(String httpAnnotation, String requestMappingMethod) {
		Predicate<MethodSource<JavaClassSource>> hasHttpAnnotation = methodSource -> methodSource.hasAnnotation(httpAnnotation);
		Predicate<MethodSource<JavaClassSource>> hasRequestMapping = methodSource -> methodSource.hasAnnotation("RequestMapping");
		Predicate<MethodSource<JavaClassSource>> hasRequestMappingMethod = methodSource -> StringUtils.equals(methodSource.getAnnotation("RequestMapping").getStringValue("method"),
				requestMappingMethod);
		// @formatter:off
		return this.javaClassSource.getMethods()
			.stream()
			.filter(hasHttpAnnotation.or(hasRequestMapping.and(hasRequestMappingMethod)))
			.map(methodSource -> new EJavaMethodImpl<JavaClassSource>(this.getRoasterJavaClass(), methodSource))
			.collect(Collectors.toCollection(TreeSet::new));
		// @formatter:on
	}

}
