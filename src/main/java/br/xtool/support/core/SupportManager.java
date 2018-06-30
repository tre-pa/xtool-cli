package br.xtool.support.core;

import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import br.xtool.core.representation.SpringBootProjectRepresentation;
import br.xtool.core.representation.enums.ProjectType;

@Service
public class SupportManager {

	@Autowired
	private Set<SpringBootSupport> supports;

	private Table<ProjectType, SupportType, SpringBootSupport> supportTable = HashBasedTable.create();

	@PostConstruct
	private void init() {
		supports.forEach(support -> supportTable.put(support.getApplyForType(), support.getType(), support));
	}

	public void addSupport(SpringBootProjectRepresentation project, SupportType supportType) {
		SpringBootSupport support = supportTable.get(project.getProjectType(), supportType);
		support.apply(project);
	}

}
