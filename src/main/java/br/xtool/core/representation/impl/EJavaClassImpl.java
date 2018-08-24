package br.xtool.core.representation.impl;

import java.lang.annotation.Annotation;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.jboss.forge.roaster.model.JavaDoc;
import org.jboss.forge.roaster.model.JavaType;
import org.jboss.forge.roaster.model.SyntaxError;
import org.jboss.forge.roaster.model.Visibility;
import org.jboss.forge.roaster.model.source.JavaClassSource;

import br.xtool.core.representation.EBootProject;
import br.xtool.core.representation.EJavaAnnotation;
import br.xtool.core.representation.EJavaClass;
import br.xtool.core.representation.EJavaField;
import br.xtool.core.representation.EJavaMethod;
import br.xtool.core.representation.EJavaPackage;
import lombok.EqualsAndHashCode;
import lombok.ToString;

public class EJavaClassImpl implements EJavaClass {

	protected JavaClassSource javaClassSource;

	private EBootProject project;

	public EJavaClassImpl(EBootProject project, JavaClassSource javaClassSource) {
		super();
		this.project = project;
		this.javaClassSource = javaClassSource;
	}

	@Override
	public EBootProject getProject() {
		return this.project;
	}

	@Override
	public String getInstanceName() {
		return StringUtils.uncapitalize(this.getName());
	}

	/**
	 * Nome da classe
	 * 
	 * @return
	 */
	@Override
	public String getName() {
		return this.javaClassSource.getName();
	}

	/**
	 * Retorna o nome qualificado da classe: pacote+class
	 * 
	 * @return
	 */
	@Override
	public String getQualifiedName() {
		return this.javaClassSource.getQualifiedName();
	}

	/**
	 * Retorna o pacote da classe
	 * 
	 * @return
	 */
	@Override
	public EJavaPackage getJavaPackage() {
		return EJavaPackageImpl.of(this.javaClassSource.getPackage());
	}

	/**
	 * Verifica se a entidade possui a annotation
	 * 
	 * @param name
	 *            Nome da annotation
	 * @return
	 */
	@Override
	public boolean hasAnnotation(String name) {
		return this.javaClassSource.hasAnnotation(name);
	}

	/**
	 * Retorna o caminho da classe no projeto.
	 * 
	 * @return
	 */
	@Override
	public Path getPath() {
		return this.project.getPath().resolve(String.format("src/main/java/%s/%s.java", this.getJavaPackage().getDir(), this.getName()));
	}

	@Override
	public Collection<EJavaField> getJavaFields() {
		// @formatter:off
		return this.javaClassSource.getFields()
				.stream()
				.map(fieldSource -> new EJavaFieldImpl(this, fieldSource))
				.collect(Collectors.toList());
		// @formatter:on
	}

	@Override
	public EJavaField addField(String name) {
		// @formatter:off
		return this.getJavaFields().stream()
				.filter(javaField -> javaField.getName().equals(name))
				.findAny()
				.orElseGet(() -> new EJavaFieldImpl(this,this.javaClassSource.addField().setName(name)));
		// @formatter:on
	}

	@Override
	public EJavaMethod<JavaClassSource> addMethod(String name) {
		// @formatter:off
		return this.getJavaMethods().stream()
				.filter(javaMethod -> javaMethod.getName().equals(name))
				.findAny()
				.orElseGet(() -> new EJavaMethodImpl<JavaClassSource>(this.javaClassSource, this.javaClassSource.addMethod().setName(name)));
		// @formatter:on
	}

	@Override
	public SortedSet<EJavaAnnotation<JavaClassSource>> getJavaAnnotations() {
		// @formatter:off
		return this.javaClassSource.getAnnotations()
				.stream()
				.map(EJavaAnnotationImpl::new)
				.collect(Collectors.toCollection(TreeSet::new));
		// @formatter:on
	}

	@Override
	public EJavaAnnotation<JavaClassSource> addAnnotation(Class<? extends Annotation> type) {
		// @formatter:off
		return this.getJavaAnnotations().stream()
				.filter(javaAnn -> javaAnn.getName().equals(type.getSimpleName()))
				.findAny()
				.orElseGet(() -> new EJavaAnnotationImpl<JavaClassSource>(this.javaClassSource.addAnnotation(type)));
		// @formatter:on
	}

	@Override
	public SortedSet<EJavaMethod<JavaClassSource>> getJavaMethods() {
		// @formatter:off
		return this.javaClassSource.getMethods()
				.stream()
				.map(methodSource -> new EJavaMethodImpl<JavaClassSource>(this.javaClassSource, methodSource))
				.collect(Collectors.toCollection(TreeSet::new));
		// @formatter:on
	}

	@Override
	public JavaClassSource getRoasterJavaClass() {
		return this.javaClassSource;
	}

	//	@Override
	//	public EJavaAnnotation<JavaClassSource> addTableAnnotation() {
	//		EJavaAnnotation<JavaClassSource> ann = this.addAnnotation(Table.class);
	//		ann.setStringValue("name", EJpaEntity.genDBTableName(this.getName()));
	//		return ann;
	//	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.EJavaClass#addToString(java.lang.String[])
	 */
	@Override
	public EJavaAnnotation<JavaClassSource> addToStringAnnotation(String... attributes) {
		EJavaAnnotation<JavaClassSource> javaAnnotation = this.addAnnotation(ToString.class);
		javaAnnotation.setStringArrayValue("of", attributes);
		return javaAnnotation;
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.EJavaClass#addEqualsAndHashCode(java.lang.String[])
	 */
	@Override
	public EJavaAnnotation<JavaClassSource> addEqualsAndHashCodeAnnotation(String... attributes) {
		EJavaAnnotation<JavaClassSource> javaAnnotation = this.addAnnotation(EqualsAndHashCode.class);
		javaAnnotation.setStringArrayValue("of", attributes);
		return javaAnnotation;
	}

	@Override
	public int compareTo(EJavaClass o) {
		return this.getName().compareTo(o.getName());
	}

	@Override
	public String getCanonicalName() {
		return this.getRoasterJavaClass().getCanonicalName();
	}

	@Override
	public List<SyntaxError> getSyntaxErrors() {
		return this.getRoasterJavaClass().getSyntaxErrors();
	}

	@Override
	public boolean hasSyntaxErrors() {
		return this.getRoasterJavaClass().hasSyntaxErrors();
	}

	@Override
	public boolean isClass() {
		return this.getRoasterJavaClass().isClass();
	}

	@Override
	public boolean isEnum() {
		return this.getRoasterJavaClass().isEnum();
	}

	@Override
	public boolean isInterface() {
		return this.getRoasterJavaClass().isInterface();
	}

	@Override
	public boolean isAnnotation() {
		return this.getRoasterJavaClass().isAnnotation();
	}

	@Override
	public JavaType<?> getEnclosingType() {
		return this.getRoasterJavaClass().getEnclosingType();
	}

	@Override
	public String toUnformattedString() {
		return this.getRoasterJavaClass().toUnformattedString();
	}

	@Override
	public String getPackage() {
		return this.getRoasterJavaClass().getPackage();
	}

	@Override
	public boolean isDefaultPackage() {
		return this.getRoasterJavaClass().isDefaultPackage();
	}

	@Override
	public List<? extends org.jboss.forge.roaster.model.Annotation<JavaClassSource>> getAnnotations() {
		return this.getRoasterJavaClass().getAnnotations();
	}

	@Override
	public boolean hasAnnotation(Class<? extends Annotation> type) {
		return this.getRoasterJavaClass().hasAnnotation(type);
	}

	@Override
	public org.jboss.forge.roaster.model.Annotation<JavaClassSource> getAnnotation(Class<? extends Annotation> type) {
		return this.getRoasterJavaClass().getAnnotation(type);
	}

	@Override
	public org.jboss.forge.roaster.model.Annotation<JavaClassSource> getAnnotation(String type) {
		return this.getRoasterJavaClass().getAnnotation(type);
	}

	@Override
	public JavaClassSource getOrigin() {
		return this.getRoasterJavaClass().getOrigin();
	}

	@Override
	public Object getInternal() {
		return this.getRoasterJavaClass().getInternal();
	}

	@Override
	public JavaDoc<JavaClassSource> getJavaDoc() {
		return this.getRoasterJavaClass().getJavaDoc();
	}

	@Override
	public boolean hasJavaDoc() {
		return this.getRoasterJavaClass().hasJavaDoc();
	}

	@Override
	public boolean isPackagePrivate() {
		return this.getRoasterJavaClass().isPackagePrivate();
	}

	@Override
	public boolean isPublic() {
		return this.getRoasterJavaClass().isPublic();
	}

	@Override
	public boolean isPrivate() {
		return this.getRoasterJavaClass().isPrivate();
	}

	@Override
	public boolean isProtected() {
		return this.getRoasterJavaClass().isProtected();
	}

	@Override
	public Visibility getVisibility() {
		return this.getRoasterJavaClass().getVisibility();
	}

	public static class EAuditableJavaClassImpl extends EJavaClassImpl implements EAuditableJavaClass {
		public EAuditableJavaClassImpl(EJavaClass javaClass) {
			super(javaClass.getProject(), javaClass.getRoasterJavaClass());
		}
	}

	public static class ECacheableJavaClassImpl extends EJavaClassImpl implements ECacheableJavaClass {
		public ECacheableJavaClassImpl(EJavaClass javaClass) {
			super(javaClass.getProject(), javaClass.getRoasterJavaClass());
		}
	}

	public static class EIndexedJavaClassImpl extends EJavaClassImpl implements EIndexedJavaClass {
		public EIndexedJavaClassImpl(EJavaClass javaClass) {
			super(javaClass.getProject(), javaClass.getRoasterJavaClass());
		}
	}

	public static class EViewJavaClassImpl extends EJavaClassImpl implements EViewJavaClass {
		public EViewJavaClassImpl(EJavaClass javaClass) {
			super(javaClass.getProject(), javaClass.getRoasterJavaClass());
		}
	}

	public static class EReadOnlyJavaClassImpl extends EJavaClassImpl implements EReadOnlyJavaClass {
		public EReadOnlyJavaClassImpl(EJavaClass javaClass) {
			super(javaClass.getProject(), javaClass.getRoasterJavaClass());
		}
	}

	public static class EVersionableJavaClassImpl extends EJavaClassImpl implements EVersionableJavaClass {
		public EVersionableJavaClassImpl(EJavaClass javaClass) {
			super(javaClass.getProject(), javaClass.getRoasterJavaClass());
		}
	}

}
