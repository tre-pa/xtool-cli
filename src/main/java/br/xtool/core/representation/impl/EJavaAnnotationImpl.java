package br.xtool.core.representation.impl;

import java.util.Arrays;
import java.util.List;

import org.jboss.forge.roaster.model.source.AnnotationSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;

import br.xtool.core.representation.EJavaAnnotation;

/**
 * Representação de uma annotation.
 * 
 * @author jcruz
 *
 */
public class EJavaAnnotationImpl implements EJavaAnnotation {

	private AnnotationSource<JavaClassSource> annotation;

	public EJavaAnnotationImpl(AnnotationSource<JavaClassSource> annotation) {
		super();
		this.annotation = annotation;
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.EJavaAnnotation#getName()
	 */
	@Override
	public String getName() {
		return this.annotation.getName();
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.EJavaAnnotation#isSingleValue()
	 */
	@Override
	public boolean isSingleValue() {
		return this.annotation.isSingleValue();
	}

	@Override
	public AnnotationSource<JavaClassSource> getRoasterAnnotation() {
		return this.annotation;
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.EJavaAnnotation#getAnnotationValue()
	 */
	@Override
	public AnnotationSource<JavaClassSource> getAnnotationValue() {
		return this.annotation.getAnnotationValue();
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.EJavaAnnotation#isMarker()
	 */
	@Override
	public boolean isMarker() {
		return this.annotation.isMarker();
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.EJavaAnnotation#isNormal()
	 */
	@Override
	public boolean isNormal() {
		return this.annotation.isNormal();
	}

	@Override
	public AnnotationSource<JavaClassSource> getAnnotationValue(String name) {
		return this.annotation.getAnnotationValue(name);
	}

	@Override
	public String getQualifiedName() {
		return this.annotation.getQualifiedName();
	}

	@Override
	public <T extends Enum<T>> T getEnumValue(Class<T> type) {
		return this.annotation.getEnumValue(type);
	}

	@Override
	public List<AnnotationSource<JavaClassSource>> getAnnotationArrayValue() {
		return Arrays.asList(this.annotation.getAnnotationArrayValue());
	}

	@Override
	public <T extends Enum<T>> T getEnumValue(Class<T> type, String name) {
		return this.annotation.getEnumValue(type, name);
	}

	@Override
	public List<AnnotationSource<JavaClassSource>> getAnnotationArrayValue(String name) {
		return Arrays.asList(this.annotation.getAnnotationArrayValue(name));
	}

	@Override
	public <T extends Enum<T>> List<T> getEnumArrayValue(Class<T> type) {
		return Arrays.asList(this.annotation.getEnumArrayValue(type));
	}

	@Override
	public <T extends Enum<T>> List<T> getEnumArrayValue(Class<T> type, String name) {
		return Arrays.asList(this.annotation.getEnumArrayValue(type, name));
	}

	@Override
	public String getLiteralValue() {
		return this.annotation.getLiteralValue();
	}

	@Override
	public String getStringValue() {
		return this.annotation.getStringValue();
	}

	@Override
	public String getStringValue(String name) {
		return this.annotation.getStringValue(name);
	}

	@Override
	public String getLiteralValue(String name) {
		return this.annotation.getLiteralValue(name);
	}

	@Override
	public List<String> getStringArrayValue() {
		return Arrays.asList(this.annotation.getStringArrayValue());
	}

	@Override
	public List<String> getStringArrayValue(String name) {
		return Arrays.asList(this.annotation.getStringArrayValue(name));
	}

	@Override
	public Class<?> getClassValue() {
		return this.annotation.getClassValue();
	}

	@Override
	public Class<?> getClassValue(String name) {
		return this.annotation.getClassValue(name);
	}

	@Override
	public List<Class<?>> getClassArrayValue() {
		return Arrays.asList(this.annotation.getClassArrayValue());
	}

	@Override
	public List<Class<?>> getClassArrayValue(String name) {
		return Arrays.asList(this.annotation.getClassArrayValue(name));
	}

	@Override
	public AnnotationSource<JavaClassSource> setEnumValue(String name, Enum<?> value) {
		return this.annotation.setEnumValue(name, value);
	}

	@Override
	public AnnotationSource<JavaClassSource> setEnumValue(Enum<?>... value) {
		return this.annotation.setEnumValue(value);
	}

	@Override
	public AnnotationSource<JavaClassSource> setEnumArrayValue(String name, Enum<?>... values) {
		return this.annotation.setEnumArrayValue(name, values);
	}

	@Override
	public AnnotationSource<JavaClassSource> setEnumArrayValue(Enum<?>... values) {
		return this.annotation.setEnumArrayValue(values);
	}

	@Override
	public AnnotationSource<JavaClassSource> setLiteralValue(String value) {
		return this.annotation.setLiteralValue(value);
	}

	@Override
	public AnnotationSource<JavaClassSource> setLiteralValue(String name, String value) {
		return this.annotation.setLiteralValue(name, value);
	}

	@Override
	public AnnotationSource<JavaClassSource> setStringValue(String value) {
		return this.annotation.setStringValue(value);
	}

	@Override
	public AnnotationSource<JavaClassSource> setStringValue(String name, String value) {
		return this.annotation.setStringValue(name, value);
	}

	@Override
	public AnnotationSource<JavaClassSource> setAnnotationValue() {
		return this.annotation.setAnnotationValue();
	}

	@Override
	public AnnotationSource<JavaClassSource> setAnnotationValue(String name) {
		return this.annotation.setAnnotationValue(name);
	}

	@Override
	public AnnotationSource<JavaClassSource> setClassValue(String name, Class<?> value) {
		return this.annotation.setClassValue(name, value);
	}

	@Override
	public AnnotationSource<JavaClassSource> setClassValue(Class<?> value) {
		return this.annotation.setClassValue(value);
	}

	@Override
	public AnnotationSource<JavaClassSource> setClassArrayValue(String name, Class<?>... values) {
		return this.annotation.setClassArrayValue(name, values);
	}

	@Override
	public AnnotationSource<JavaClassSource> setClassArrayValue(Class<?>... values) {
		return this.annotation.setClassArrayValue(values);
	}

	@Override
	public AnnotationSource<JavaClassSource> setStringArrayValue(String name, String[] values) {
		return this.annotation.setStringArrayValue(name, values);
	}

	@Override
	public AnnotationSource<JavaClassSource> setStringArrayValue(String[] values) {
		return this.annotation.setStringArrayValue(values);
	}

	@Override
	public int compareTo(EJavaAnnotation o) {
		return this.getName().compareTo(o.getName());
	}

}
