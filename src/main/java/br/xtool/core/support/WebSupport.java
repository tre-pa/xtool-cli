package br.xtool.core.support;

import br.xtool.core.representation.EBootAppProperties;
import br.xtool.core.representation.EBootPom;
import br.xtool.core.representation.EBootProject;
import br.xtool.core.representation.EBootProject.BootProjectSupport;

public class WebSupport implements BootProjectSupport {

	@Override
	public void apply(EBootProject project) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void apply(EBootAppProperties appProperties) {

	}

	@Override
	public void apply(EBootPom pom) {

	}

	@Override
	public boolean has(EBootProject project) {
		return false;
	}

}
