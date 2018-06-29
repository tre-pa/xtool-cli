package br.xtool.core.representation.updater;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.jboss.forge.roaster.model.source.AnnotationSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;

import br.xtool.core.Log;
import br.xtool.core.representation.EntityRepresentation;
import br.xtool.core.representation.updater.core.AnnotationPayload;
import br.xtool.core.representation.updater.core.UpdateRequest;

/**
 * 
 * AnnotationSource<O> setEnumValue(String name, Enum<?> value);
 * 
 * AnnotationSource<O> setEnumValue(Enum<?>... value);
 * 
 * AnnotationSource<O> setEnumArrayValue(String name, Enum<?>... values);
 * 
 * AnnotationSource<O> setEnumArrayValue(Enum<?>... values);
 * 
 * AnnotationSource<O> setLiteralValue(String value);
 * 
 * AnnotationSource<O> setLiteralValue(String name, String value);
 * 
 * AnnotationSource<O> setStringValue(String value);
 * 
 * AnnotationSource<O> setStringValue(String name, String value);
 * 
 * AnnotationSource<O> setStringArrayValue(String name, String[] values);
 * 
 * AnnotationSource<O> setStringArrayValue(String[] values);
 *
 * AnnotationSource<O> setClassValue(String name, Class<?> value);
 * 
 * AnnotationSource<O> setClassValue(Class<?> value);
 * 
 * AnnotationSource<O> setClassArrayValue(String name, Class<?>... values);
 * 
 * AnnotationSource<O> setClassArrayValue(Class<?>... values);
 * 
 * @author jcruz
 *
 */
public class AddEntityAnnotation implements UpdateRequest<EntityRepresentation> {

	private String annotationName;

	private String annotationImportName;

	private Collection<AnnotationPayload> payloads = new ArrayList<>();

	private AddEntityAnnotation(String annotationName, String annotationImportName, Collection<AnnotationPayload> payloads) {
		super();
		this.annotationName = annotationName;
		this.annotationImportName = annotationImportName;
		this.payloads = payloads;
	}

	@Override
	public boolean updatePolicy(EntityRepresentation representation) {
		return !representation.getSource().hasAnnotation(annotationName);
	}

	@Override
	public void apply(EntityRepresentation representation) {
		representation.getSource().addImport(annotationImportName);
		AnnotationSource<JavaClassSource> annotationSource = representation.getSource().addAnnotation();
		annotationSource.setName(annotationName);
		Log.print(Log.bold(Log.green("\t    [+] ")), Log.gray("import : "), Log.white(this.annotationImportName));
		Log.print(Log.bold(Log.green("\t    [+] ")), Log.gray("annotation : @"), Log.white(this.annotationName));
		// @formatter:off
		payloads
			.stream()
			.filter(Objects::nonNull)
			.forEach(payload -> payload.apply(representation, annotationSource));
		// @formatter:on
	}

	/**
	 * 
	 * @param qualifiedAnnotationName
	 * @param payloads
	 * @return
	 */
	public static Optional<AddEntityAnnotation> of(String qualifiedAnnotationName, AnnotationPayload... payloads) {
		String[] annotationTokens = StringUtils.split(qualifiedAnnotationName, ".");
		String annotationName = annotationTokens[annotationTokens.length - 1];
		String annotationImportName = qualifiedAnnotationName;
		if (StringUtils.isNotBlank(annotationImportName) && StringUtils.isNotBlank(annotationName)) {
			return Optional.of(new AddEntityAnnotation(annotationName, annotationImportName, Arrays.asList(payloads)));
		}
		return Optional.empty();
	}

}
