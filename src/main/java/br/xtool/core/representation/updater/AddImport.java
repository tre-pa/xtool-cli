package br.xtool.core.representation.updater;

import org.apache.commons.lang3.StringUtils;

import br.xtool.core.Log;
import br.xtool.core.representation.EEntity;
import br.xtool.core.representation.updater.core.UpdateRequest;

/**
 * Requisição de atualização para adição de import.
 * 
 * @author jcruz
 *
 */
public class AddImport implements UpdateRequest<EEntity> {

	private String name;

	private AddImport(String name) {
		super();
		this.name = name;
	}

	@Override
	public boolean updatePolicy(EEntity representation) {
		return !representation.getSource().hasAnnotation(name);
	}

	@Override
	public void apply(EEntity representation) {
		representation.getSource().addImport(name);
		Log.print(Log.bold(Log.green("\t    [+] ")), Log.gray("import : "), Log.white(this.name));
	}

	/**
	 * 
	 * @param name
	 * @return
	 */
	public static AddImport of(String name) {
		if (StringUtils.isNotBlank(name)) {
			return new AddImport(name);
		}
		return null;
	}

}
