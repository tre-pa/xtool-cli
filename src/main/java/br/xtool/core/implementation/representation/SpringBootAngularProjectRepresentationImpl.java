package br.xtool.core.implementation.representation;

import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.SortedSet;

import org.jboss.forge.roaster.model.JavaUnit;

import br.xtool.core.representation.ProjectRepresentation;
import br.xtool.core.representation.SpringBootAngularProjectRepresentation;
import br.xtool.core.representation.angular.NgProjectRepresentation;
import br.xtool.core.representation.plantuml.PlantClassDiagramRepresentation;
import br.xtool.core.representation.springboot.SpringBootProjectRepresentation;

public class SpringBootAngularProjectRepresentationImpl extends ProjectRepresentationImpl
		implements SpringBootAngularProjectRepresentation {

	public SpringBootAngularProjectRepresentationImpl(Path path) {
		super(path);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getFrameworkVersion() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Version getProjectVersion() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void refresh() {
		// TODO Auto-generated method stub

	}

	@Override
	public Type getProjectType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SpringBootProjectRepresentation getSpringBootProject() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NgProjectRepresentation getAngularProject() {
		// TODO Auto-generated method stub
		return null;
	}

}
