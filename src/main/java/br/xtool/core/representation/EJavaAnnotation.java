package br.xtool.core.representation;

import java.util.List;

import org.jboss.forge.roaster.model.source.AnnotationSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;

/**
 * Classe que representa uma annotation.
 * 
 * @author jcruz
 *
 */
public interface EJavaAnnotation extends Comparable<EJavaAnnotation> {

	/**
	 * Retorna o nome da annotation.
	 * 
	 * @return
	 */
	String getName();

	boolean isSingleValue();

	boolean isMarker();

	boolean isNormal();

	AnnotationSource<JavaClassSource> getAnnotationValue();

	AnnotationSource<JavaClassSource> getAnnotationValue(String name);

	String getQualifiedName();

	<T extends Enum<T>> T getEnumValue(Class<T> type);

	List<AnnotationSource<JavaClassSource>> getAnnotationArrayValue();

	<T extends Enum<T>> T getEnumValue(Class<T> type, String name);

	List<AnnotationSource<JavaClassSource>> getAnnotationArrayValue(String name);

	<T extends Enum<T>> List<T> getEnumArrayValue(Class<T> type);

	<T extends Enum<T>> List<T> getEnumArrayValue(Class<T> type, String name);

	String getLiteralValue();

	String getStringValue();

	String getStringValue(String name);

	String getLiteralValue(String name);

	List<String> getStringArrayValue();

	List<String> getStringArrayValue(String name);

	Class<?> getClassValue();

	Class<?> getClassValue(String name);

	List<Class<?>> getClassArrayValue();

	List<Class<?>> getClassArrayValue(String name);

	AnnotationSource<JavaClassSource> setEnumValue(String name, Enum<?> value);

	AnnotationSource<JavaClassSource> setEnumValue(Enum<?>... value);

	AnnotationSource<JavaClassSource> setEnumArrayValue(String name, Enum<?>... values);

	public AnnotationSource<JavaClassSource> setEnumArrayValue(Enum<?>... values);

	AnnotationSource<JavaClassSource> setLiteralValue(String value);

	AnnotationSource<JavaClassSource> setLiteralValue(String name, String value);

	AnnotationSource<JavaClassSource> setStringValue(String value);

	AnnotationSource<JavaClassSource> setStringValue(String name, String value);

	AnnotationSource<JavaClassSource> setAnnotationValue();

	AnnotationSource<JavaClassSource> setAnnotationValue(String name);

	AnnotationSource<JavaClassSource> setClassValue(String name, Class<?> value);

	AnnotationSource<JavaClassSource> setClassValue(Class<?> value);

	AnnotationSource<JavaClassSource> setClassArrayValue(String name, Class<?>... values);

	AnnotationSource<JavaClassSource> setClassArrayValue(Class<?>... values);

	AnnotationSource<JavaClassSource> setStringArrayValue(String name, String[] values);

	AnnotationSource<JavaClassSource> setStringArrayValue(String[] values);
}
