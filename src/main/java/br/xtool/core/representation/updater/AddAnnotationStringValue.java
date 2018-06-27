package br.xtool.core.representation.updater;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.jboss.forge.roaster.model.source.AnnotationSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;

import br.xtool.core.Log;
import br.xtool.core.representation.EntityRepresentation;
import br.xtool.core.representation.updater.core.AnnotationPayload;

public class AddAnnotationStringValue implements AnnotationPayload {

	private String paramName;

	private String[] paramValues;

	private AddAnnotationStringValue(String paramName, String[] paramValues) {
		super();
		this.paramName = paramName;
		this.paramValues = paramValues;
	}

	private AddAnnotationStringValue(String[] paramValues) {
		super();
		this.paramValues = paramValues;
	}

	@Override
	public void apply(EntityRepresentation representation, AnnotationSource<JavaClassSource> annotationSource) {
		if (StringUtils.isNotBlank(paramName)) {
			annotationSource.setStringArrayValue(paramName, paramValues);
			Log.print(Log.bold(Log.green("\t        [+] ")), Log.gray("params : "), Log.white(this.paramName), Log.white(Arrays.deepToString(paramValues)));
			return;
		}
		annotationSource.setStringArrayValue(paramValues);
		Log.print(Log.bold(Log.green("\t         [+] ")), Log.gray("params : "), Log.white(this.paramName));
	}

	/**
	 * 
	 * @param name
	 * @param value
	 * @return
	 */
	public static AddAnnotationStringValue of(String name, String value) {
		if (StringUtils.isNotBlank(name) && StringUtils.isNotEmpty(name)) {
			return new AddAnnotationStringValue(name, new String[] { value });
		}
		return null;
	}

	/**
	 * 
	 * @param name
	 * @param values
	 * @return
	 */
	public static AddAnnotationStringValue of(String name, String[] values) {
		if (StringUtils.isNotBlank(name) && Objects.nonNull(name)) {
			return new AddAnnotationStringValue(name, values);
		}
		return null;
	}

	/**
	 * 
	 * @param values
	 * @return
	 */
	public static AddAnnotationStringValue of(String[] values) {
		if (Objects.nonNull(values)) {
			return new AddAnnotationStringValue(values);
		}
		return null;
	}

	/**
	 * 
	 * @param value
	 * @return
	 */
	public static AddAnnotationStringValue of(String value) {
		if (StringUtils.isNotBlank(value)) {
			return new AddAnnotationStringValue(new String[] { value });
		}
		return null;
	}

}
