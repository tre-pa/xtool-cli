package br.xtool.core.template;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.xtool.core.representation.impl.ServiceClassRepresentationImpl;
import br.xtool.core.representation.springboot.RepositoryRepresentation;
import br.xtool.core.representation.springboot.ServiceClassRepresentation;
import br.xtool.core.representation.springboot.SpringBootProjectRepresentation;
import br.xtool.core.util.RoasterUtil;

public class ServiceClassTemplates {

	public static ServiceClassRepresentation newServiceClassRepresentation(SpringBootProjectRepresentation bootProject, RepositoryRepresentation repository) {
		String serviceName = repository.getTargetEntity().getName().concat("Service");
		ServiceClassRepresentation service = new ServiceClassRepresentationImpl(bootProject, RoasterUtil.createJavaClassSource(serviceName));
		service.getRoasterJavaClass().setPackage(bootProject.getRootPackage().getName().concat(".service"));
		service.getRoasterJavaClass().addImport(Autowired.class);
		service.getRoasterJavaClass().addImport(repository.getQualifiedName());
		service.getRoasterJavaClass().addAnnotation(Service.class);
		// @formatter:off
		service.getRoasterJavaClass().addField()
			.setPrivate()
			.setName(repository.getInstanceName())
			.setType(repository)
			.addAnnotation(Autowired.class);
		// @formatter:on
		return service;
	}
}
