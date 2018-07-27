package br.xtool.core.service.impl;

import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import br.xtool.core.representation.EBootProject;
import br.xtool.core.representation.EBootProject.BootSupport;
import br.xtool.core.representation.EJavaClass;
import br.xtool.core.representation.impl.EJavaClassImpl;
import br.xtool.core.service.BootService;
import br.xtool.core.util.RoasterUtil;

@Service
public class BootServiceImpl implements BootService {

	@Autowired
	private ApplicationContext applicationContext;

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.service.BootService#addSupport(br.xtool.core.representation.EBootProject, java.lang.Class)
	 */
	@Override
	public <T extends BootSupport> void addSupport(EBootProject bootProject, Class<T> supportClass) {
		this.applicationContext.getBean(supportClass).apply(bootProject);
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.service.BootService#hasSupport(br.xtool.core.representation.EBootProject, java.lang.Class)
	 */
	@Override
	public <T extends BootSupport> boolean hasSupport(EBootProject bootProject, Class<T> supportClass) {
		return this.applicationContext.getBean(supportClass).has(bootProject);
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.service.BootService#findJavaClassByName(br.xtool.core.representation.EBootProject, java.lang.String)
	 */
	@Override
	public EJavaClass findJavaClassByName(EBootProject bootProject, String name) {
		// @formatter:off
		return bootProject.getRoasterJavaUnits().stream()
				.filter(javaUnit -> javaUnit.getGoverningType().isClass())
				.filter(javaUnit -> javaUnit.getGoverningType().getName().equals(name))
				.map(javaUnit -> new EJavaClassImpl(bootProject, javaUnit.<JavaClassSource>getGoverningType()))
				.findFirst()
				.orElseGet(() -> new EJavaClassImpl(bootProject, RoasterUtil.createJavaClassSource(name)));
		// @formatter:on
	}

}
