package br.xtool.core.implementation.representation;

import java.nio.file.Path;

import br.xtool.core.representation.angular.NgComponentRepresentation;
import br.xtool.core.representation.angular.NgHtmlTemplateRepresentation;
import br.xtool.core.representation.angular.NgRoute;
import br.xtool.core.representation.angular.NgTsClassRepresentation;

public class NgComponentRepresentationImpl extends NgClassRepresentationImpl implements NgComponentRepresentation {

	public NgComponentRepresentationImpl(Path path) {
		super(path);
	}

	@Override
	public String getName() {
		return super.getName().concat("Component");
	}

	@Override
	public String getRoutePath() {
		return this.getTsFileName().replace("-component", "");
	}

	@Override
	public NgHtmlTemplateRepresentation getNgHtmlTemplate() {
		throw new UnsupportedOperationException();
	}

	@Override
	public NgTsClassRepresentation getNgTsClass() {
		return new NgTsClassRepresentationImpl(this.getPath().resolve(String.format("%s.component.ts", this.getTsFileName())));
	}

	@Override
	public NgRoute getDefaultRoute() {
		return new NgRoute();
	}

}
