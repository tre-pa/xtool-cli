package br.xtool.core.representation;

import java.util.List;

import org.jboss.forge.roaster.model.source.AnnotationSource;
import org.jboss.forge.roaster.model.source.JavaSource;

/**
 * Representação de uma annotation java.
 * 
 * @author jcruz
 *
 */
public interface EJavaAnnotation<T extends JavaSource<T>> extends Comparable<EJavaAnnotation<T>> {

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

	AnnotationSource<T> getAnnotationValue();

	AnnotationSource<T> getAnnotationValue(String name);

	String getQualifiedName();

	<T extends Enum<T>> T getEnumValue(Class<T> type);

	List<AnnotationSource<T>> getAnnotationArrayValue();

	<T extends Enum<T>> T getEnumValue(Class<T> type, String name);

	List<AnnotationSource<T>> getAnnotationArrayValue(String name);

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

	AnnotationSource<T> setEnumValue(String name, Enum<?> value);

	AnnotationSource<T> setEnumValue(Enum<?>... value);

	AnnotationSource<T> setEnumArrayValue(String name, Enum<?>... values);

	public AnnotationSource<T> setEnumArrayValue(Enum<?>... values);

	AnnotationSource<T> setLiteralValue(String value);

	AnnotationSource<T> setLiteralValue(String name, String value);

	AnnotationSource<T> setStringValue(String value);

	AnnotationSource<T> setStringValue(String name, String value);

	AnnotationSource<T> setAnnotationValue();

	AnnotationSource<T> setAnnotationValue(String name);

	AnnotationSource<T> setClassValue(String name, Class<?> value);

	AnnotationSource<T> setClassValue(Class<?> value);

	AnnotationSource<T> setClassArrayValue(String name, Class<?>... values);

	AnnotationSource<T> setClassArrayValue(Class<?>... values);

	AnnotationSource<T> setStringArrayValue(String name, String[] values);

	AnnotationSource<T> setStringArrayValue(String[] values);
}
