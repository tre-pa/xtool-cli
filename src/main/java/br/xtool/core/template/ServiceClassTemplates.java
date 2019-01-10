package br.xtool.core.template;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.xtool.core.representation.RepositoryRepresentation;
import br.xtool.core.representation.ServiceClassRepresentation;
import br.xtool.core.representation.SpringBootProjectRepresentation;
import br.xtool.core.representation.impl.EBootServiceImpl;
import br.xtool.core.util.RoasterUtil;

public class ServiceClassTemplates {

	public static ServiceClassRepresentation newServiceClassRepresentation(SpringBootProjectRepresentation bootProject, RepositoryRepresentation repository) {
		String serviceName = repository.getTargetEntity().getName().concat("Service");
		ServiceClassRepresentation service = new EBootServiceImpl(bootProject, RoasterUtil.createJavaClassSource(serviceName));
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