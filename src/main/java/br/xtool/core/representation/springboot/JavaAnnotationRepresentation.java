package br.xtool.core.representation.springboot;

import java.util.List;

import org.jboss.forge.roaster.model.source.AnnotationSource;
import org.jboss.forge.roaster.model.source.JavaSource;

/**
 * Representação de uma annotation java.
 * 
 * @author jcruz
 *
 */
public interface JavaAnnotationRepresentation<T extends JavaSource<T>> extends Comparable<JavaAnnotationRepresentation<T>> {

	/**
	 * Retorna o nome da annotation.
	 * 
	 * @return
	 */
	String getName();

	boolean isSingleValue();

	boolean isMarker();

	boolean isNormal();

	AnnotationSource<T> getRoasterAnnotation();

	@Deprecated
	AnnotationSource<T> getAnnotationValue();

	@Deprecated
	AnnotationSource<T> getAnnotationValue(String name);

	String getQualifiedName();

	@Deprecated
	<K extends Enum<K>> K getEnumValue(Class<K> type);

	@Deprecated
	List<AnnotationSource<T>> getAnnotationArrayValue();

	@Deprecated
	<K extends Enum<K>> K getEnumValue(Class<K> type, String name);

	@Deprecated
	List<AnnotationSource<T>> getAnnotationArrayValue(String name);

	@Deprecated
	<K extends Enum<K>> List<K> getEnumArrayValue(Class<K> type);

	@Deprecated
	<K extends Enum<K>> List<K> getEnumArrayValue(Class<K> type, String name);

	@Deprecated
	String getLiteralValue();

	@Deprecated
	String getStringValue();

	@Deprecated
	String getStringValue(String name);

	@Deprecated
	String getLiteralValue(String name);

	@Deprecated
	List<String> getStringArrayValue();

	@Deprecated
	List<String> getStringArrayValue(String name);

	@Deprecated
	Class<?> getClassValue();

	@Deprecated
	Class<?> getClassValue(String name);

	@Deprecated
	List<Class<?>> getClassArrayValue();

	@Deprecated
	List<Class<?>> getClassArrayValue(String name);

	@Deprecated
	AnnotationSource<T> setEnumValue(String name, Enum<?> value);

	@Deprecated
	AnnotationSource<T> setEnumValue(Enum<?>... value);

	@Deprecated
	AnnotationSource<T> setEnumArrayValue(String name, Enum<?>... values);

	@Deprecated
	public AnnotationSource<T> setEnumArrayValue(Enum<?>... values);

	@Deprecated
	AnnotationSource<T> setLiteralValue(String value);

	@Deprecated
	AnnotationSource<T> setLiteralValue(String name, String value);

	@Deprecated
	AnnotationSource<T> setStringValue(String value);

	@Deprecated
	AnnotationSource<T> setStringValue(String name, String value);

	@Deprecated
	AnnotationSource<T> setAnnotationValue();

	@Deprecated
	AnnotationSource<T> setAnnotationValue(String name);

	@Deprecated
	AnnotationSource<T> setClassValue(String name, Class<?> value);

	@Deprecated
	AnnotationSource<T> setClassValue(Class<?> value);

	@Deprecated
	AnnotationSource<T> setClassArrayValue(String name, Class<?>... values);

	@Deprecated
	AnnotationSource<T> setClassArrayValue(Class<?>... values);

	@Deprecated
	AnnotationSource<T> setStringArrayValue(String name, String[] values);

	@Deprecated
	AnnotationSource<T> setStringArrayValue(String[] values);
}
