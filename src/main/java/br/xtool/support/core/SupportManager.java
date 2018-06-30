package br.xtool.support.core;

import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import br.xtool.core.representation.ESpringBootProject;
import br.xtool.core.representation.enums.ProjectType;

/**
 * Classe que gerencia a adcição dos suports ao projeto.
 * 
 * @author jcruz
 *
 */
@Service
public class SupportManager {

	@Autowired
	private Set<SpringBootSupport> supports;

	private Table<ProjectType, SupportType, SpringBootSupport> supportTable = HashBasedTable.create();

	@PostConstruct
	private void init() {
		supports.forEach(support -> supportTable.put(support.getApplyForType(), support.getType(), support));
	}

	/**
	 * Adiciona um suport ao projeto Spring Boot.
	 * 
	 * @param project
	 *            Projeto Spring Boot
	 * @param supportType
	 *            Enum com tipo de support a ser adicionado. {@link SupportType}
	 */
	public void addSupport(ESpringBootProject project, SupportType supportType) {
		SpringBootSupport support = supportTable.get(project.getProjectType(), supportType);
		support.apply(project);
	}

}
