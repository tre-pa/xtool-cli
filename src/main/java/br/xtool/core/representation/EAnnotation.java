package br.xtool.core.representation;

import java.util.Arrays;
import java.util.List;

import org.jboss.forge.roaster.model.source.AnnotationSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;

/**
 * Representação de uma annotation.
 * 
 * @author jcruz
 *
 */
public class EAnnotation implements Comparable<EAnnotation> {

	private AnnotationSource<JavaClassSource> annotation;

	public EAnnotation(AnnotationSource<JavaClassSource> annotation) {
		super();
		this.annotation = annotation;
	}

	/**
	 * Retorna o nome da annotation.
	 * 
	 * @return
	 */
	public String getName() {
		return annotation.getName();
	}

	public boolean isSingleValue() {
		return annotation.isSingleValue();
	}

	public AnnotationSource<JavaClassSource> getAnnotationValue() {
		return annotation.getAnnotationValue();
	}

	public boolean isMarker() {
		return annotation.isMarker();
	}

	public boolean isNormal() {
		return annotation.isNormal();
	}

	public AnnotationSource<JavaClassSource> getAnnotationValue(String name) {
		return annotation.getAnnotationValue(name);
	}

	public String getQualifiedName() {
		return annotation.getQualifiedName();
	}

	public <T extends Enum<T>> T getEnumValue(Class<T> type) {
		return annotation.getEnumValue(type);
	}

	public List<AnnotationSource<JavaClassSource>> getAnnotationArrayValue() {
		return Arrays.asList(annotation.getAnnotationArrayValue());
	}

	public <T extends Enum<T>> T getEnumValue(Class<T> type, String name) {
		return annotation.getEnumValue(type, name);
	}

	public List<AnnotationSource<JavaClassSource>> getAnnotationArrayValue(String name) {
		return Arrays.asList(annotation.getAnnotationArrayValue(name));
	}

	public <T extends Enum<T>> List<T> getEnumArrayValue(Class<T> type) {
		return Arrays.asList(annotation.getEnumArrayValue(type));
	}

	public <T extends Enum<T>> List<T> getEnumArrayValue(Class<T> type, String name) {
		return Arrays.asList(annotation.getEnumArrayValue(type, name));
	}

	public String getLiteralValue() {
		return annotation.getLiteralValue();
	}

	public String getStringValue() {
		return annotation.getStringValue();
	}

	public String getStringValue(String name) {
		return annotation.getStringValue(name);
	}

	public String getLiteralValue(String name) {
		return annotation.getLiteralValue(name);
	}

	public List<String> getStringArrayValue() {
		return Arrays.asList(annotation.getStringArrayValue());
	}

	public List<String> getStringArrayValue(String name) {
		return Arrays.asList(annotation.getStringArrayValue(name));
	}

	public Class<?> getClassValue() {
		return annotation.getClassValue();
	}

	public Class<?> getClassValue(String name) {
		return annotation.getClassValue(name);
	}

	public List<Class<?>> getClassArrayValue() {
		return Arrays.asList(annotation.getClassArrayValue());
	}

	public List<Class<?>> getClassArrayValue(String name) {
		return Arrays.asList(annotation.getClassArrayValue(name));
	}

	public AnnotationSource<JavaClassSource> setName(String className) {
		return annotation.setName(className);
	}

	public AnnotationSource<JavaClassSource> setEnumValue(String name, Enum<?> value) {
		return annotation.setEnumValue(name, value);
	}

	public AnnotationSource<JavaClassSource> setEnumValue(Enum<?>... value) {
		return annotation.setEnumValue(value);
	}

	public AnnotationSource<JavaClassSource> setEnumArrayValue(String name, Enum<?>... values) {
		return annotation.setEnumArrayValue(name, values);
	}

	public AnnotationSource<JavaClassSource> setEnumArrayValue(Enum<?>... values) {
		return annotation.setEnumArrayValue(values);
	}

	public AnnotationSource<JavaClassSource> setLiteralValue(String value) {
		return annotation.setLiteralValue(value);
	}

	public AnnotationSource<JavaClassSource> setLiteralValue(String name, String value) {
		return annotation.setLiteralValue(name, value);
	}

	public AnnotationSource<JavaClassSource> setStringValue(String value) {
		return annotation.setStringValue(value);
	}

	public AnnotationSource<JavaClassSource> setStringValue(String name, String value) {
		return annotation.setStringValue(name, value);
	}

	public AnnotationSource<JavaClassSource> setAnnotationValue() {
		return annotation.setAnnotationValue();
	}

	public AnnotationSource<JavaClassSource> setAnnotationValue(String name) {
		return annotation.setAnnotationValue(name);
	}

	public AnnotationSource<JavaClassSource> setClassValue(String name, Class<?> value) {
		return annotation.setClassValue(name, value);
	}

	public AnnotationSource<JavaClassSource> setClassValue(Class<?> value) {
		return annotation.setClassValue(value);
	}

	public AnnotationSource<JavaClassSource> setClassArrayValue(String name, Class<?>... values) {
		return annotation.setClassArrayValue(name, values);
	}

	public AnnotationSource<JavaClassSource> setClassArrayValue(Class<?>... values) {
		return annotation.setClassArrayValue(values);
	}

	public AnnotationSource<JavaClassSource> setStringArrayValue(String name, String[] values) {
		return annotation.setStringArrayValue(name, values);
	}

	public AnnotationSource<JavaClassSource> setStringArrayValue(String[] values) {
		return annotation.setStringArrayValue(values);
	}

	@Override
	public int compareTo(EAnnotation o) {
		return this.getName().compareTo(o.getName());
	}

}
