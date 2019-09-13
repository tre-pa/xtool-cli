package br.xtool.implementation.representation;

import java.nio.file.Path;

import br.xtool.representation.NoneProjectRepresentation;

public class NoneProjectRepresentationImpl extends ProjectRepresentationImpl implements NoneProjectRepresentation {

	public NoneProjectRepresentationImpl(Path path) {
		super(path);
	}

	@Override
	public void refresh() {

	}

	@Override
	public String getType() {
		return "none";
	}

	@Override
	public String getVersion() {
		return "noe";
	}

}
