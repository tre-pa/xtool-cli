package br.xtool.core.support;

import br.xtool.core.representation.EBootAppProperties;
import br.xtool.core.representation.EBootPom;
import br.xtool.core.representation.EBootProject;
import br.xtool.core.representation.EBootProject.BootProjectSupport;

public class WebsocketSupport implements BootProjectSupport {

	@Override
	public void apply(EBootProject project) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void apply(EBootAppProperties appProperties) {
		// TODO Auto-generated method stub

	}

	@Override
	public void apply(EBootPom pom) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean has(EBootProject project) {
		// TODO Auto-generated method stub
		return false;
	}

}
