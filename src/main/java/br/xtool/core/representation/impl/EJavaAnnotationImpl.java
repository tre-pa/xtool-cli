package br.xtool.core.representation.impl;

import java.util.Arrays;
import java.util.List;

import org.jboss.forge.roaster.model.source.AnnotationSource;
import org.jboss.forge.roaster.model.source.JavaSource;

import br.xtool.core.representation.JavaAnnotationRepresentation;

/**
 * Representação de uma annotation.
 * 
 * @author jcruz
 *
 */
public class EJavaAnnotationImpl<T extends JavaSource<T>> implements JavaAnnotationRepresentation<T> {

	private AnnotationSource<T> annotation;

	public EJavaAnnotationImpl(AnnotationSource<T> annotation) {
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
	public AnnotationSource<T> getRoasterAnnotation() {
		return this.annotation;
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.EJavaAnnotation#getAnnotationValue()
	 */
	@Override
	public AnnotationSource<T> getAnnotationValue() {
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
	public AnnotationSource<T> getAnnotationValue(String name) {
		return this.annotation.getAnnotationValue(name);
	}

	@Override
	public String getQualifiedName() {
		return this.annotation.getQualifiedName();
	}

	@Override
	public <K extends Enum<K>> K getEnumValue(Class<K> type) {
		return this.annotation.getEnumValue(type);
	}

	@Override
	public List<AnnotationSource<T>> getAnnotationArrayValue() {
		return Arrays.asList(this.annotation.getAnnotationArrayValue());
	}

	@Override
	public <K extends Enum<K>> K getEnumValue(Class<K> type, String name) {
		return this.annotation.getEnumValue(type, name);
	}

	@Override
	public List<AnnotationSource<T>> getAnnotationArrayValue(String name) {
		return Arrays.asList(this.annotation.getAnnotationArrayValue(name));
	}

	@Override
	public <K extends Enum<K>> List<K> getEnumArrayValue(Class<K> type) {
		return Arrays.asList(this.annotation.getEnumArrayValue(type));
	}

	@Override
	public <K extends Enum<K>> List<K> getEnumArrayValue(Class<K> type, String name) {
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
	public AnnotationSource<T> setEnumValue(String name, Enum<?> value) {
		return this.annotation.setEnumValue(name, value);
	}

	@Override
	public AnnotationSource<T> setEnumValue(Enum<?>... value) {
		return this.annotation.setEnumValue(value);
	}

	@Override
	public AnnotationSource<T> setEnumArrayValue(String name, Enum<?>... values) {
		return this.annotation.setEnumArrayValue(name, values);
	}

	@Override
	public AnnotationSource<T> setEnumArrayValue(Enum<?>... values) {
		return this.annotation.setEnumArrayValue(values);
	}

	@Override
	public AnnotationSource<T> setLiteralValue(String value) {
		return this.annotation.setLiteralValue(value);
	}

	@Override
	public AnnotationSource<T> setLiteralValue(String name, String value) {
		return this.annotation.setLiteralValue(name, value);
	}

	@Override
	public AnnotationSource<T> setStringValue(String value) {
		return this.annotation.setStringValue(value);
	}

	@Override
	public AnnotationSource<T> setStringValue(String name, String value) {
		return this.annotation.setStringValue(name, value);
	}

	@Override
	public AnnotationSource<T> setAnnotationValue() {
		return this.annotation.setAnnotationValue();
	}

	@Override
	public AnnotationSource<T> setAnnotationValue(String name) {
		return this.annotation.setAnnotationValue(name);
	}

	@Override
	public AnnotationSource<T> setClassValue(String name, Class<?> value) {
		return this.annotation.setClassValue(name, value);
	}

	@Override
	public AnnotationSource<T> setClassValue(Class<?> value) {
		return this.annotation.setClassValue(value);
	}

	@Override
	public AnnotationSource<T> setClassArrayValue(String name, Class<?>... values) {
		return this.annotation.setClassArrayValue(name, values);
	}

	@Override
	public AnnotationSource<T> setClassArrayValue(Class<?>... values) {
		return this.annotation.setClassArrayValue(values);
	}

	@Override
	public AnnotationSource<T> setStringArrayValue(String name, String[] values) {
		return this.annotation.setStringArrayValue(name, values);
	}

	@Override
	public AnnotationSource<T> setStringArrayValue(String[] values) {
		return this.annotation.setStringArrayValue(values);
	}

	@Override
	public int compareTo(JavaAnnotationRepresentation<T> o) {
		return this.getName().compareTo(o.getName());
	}

}
