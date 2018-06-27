package br.xtool.core.representation.updater;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

import br.xtool.core.Log;
import br.xtool.core.representation.EntityRepresentation;
import br.xtool.core.representation.updater.core.UpdateRequest;

/**
 * Requisição de atualização para adição de import.
 * 
 * @author jcruz
 *
 */
public class AddImport implements UpdateRequest<EntityRepresentation> {

	private String name;

	private AddImport(String name) {
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
		Log.print(Log.bold(Log.green("\t    [+] ")), Log.gray("import : "), Log.white(this.name));
	}

	/**
	 * 
	 * @param name
	 * @return
	 */
	public static Optional<AddImport> of(String name) {
		if (StringUtils.isNotBlank(name)) {
			return Optional.of(new AddImport(name));
		}
		return Optional.empty();
	}

}
