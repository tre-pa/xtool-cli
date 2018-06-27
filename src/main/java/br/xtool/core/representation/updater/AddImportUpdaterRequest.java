package br.xtool.core.representation.updater;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

import br.xtool.core.Log;
import br.xtool.core.representation.EntityRepresentation;
import br.xtool.core.representation.updater.core.UpdateRequest;

public class AddImportUpdaterRequest implements UpdateRequest<EntityRepresentation> {

	private String name;

	private AddImportUpdaterRequest(String name) {
		super();
		this.name = name;
	}

	@Override
	public boolean updatePolicy(EntityRepresentation representation) {
		return !representation.getSource().hasAnnotation(name);
	}

	@Override
	public void apply(EntityRepresentation representation) {
		representation.getSource().addImport(name);
		Log.print(Log.bold(Log.green("\t\t[+] ")), Log.gray("import : "), Log.white(this.name));
	}

	public static Optional<AddImportUpdaterRequest> of(String name) {
		if (StringUtils.isNotBlank(name)) {
			return Optional.of(new AddImportUpdaterRequest(name));
		}
		return Optional.empty();
	}

}
