package br.xtool.core.diagram;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import br.xtool.core.diagram.mapper.AssociationMapper;
import br.xtool.core.diagram.mapper.FieldMapper;
import br.xtool.core.representation.EJavaPackage;
import br.xtool.core.representation.ESBootProject;
import br.xtool.core.representation.impl.EJavaClassImpl;
import br.xtool.core.representation.impl.EJavaPackageImpl;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.plantuml.BlockUml;
import net.sourceforge.plantuml.SourceStringReader;
import net.sourceforge.plantuml.classdiagram.ClassDiagram;
import net.sourceforge.plantuml.cucadiagram.IGroup;
import net.sourceforge.plantuml.cucadiagram.ILeaf;
import net.sourceforge.plantuml.cucadiagram.Link;
import net.sourceforge.plantuml.cucadiagram.Member;

@Component
@Slf4j
public class ClassDiagramReader {

	@Autowired
	private Collection<FieldMapper> fieldMappers;

	@Autowired
	private Collection<AssociationMapper> associationMappers;

	private Map<String, JavaClassSource> javaClassSources = new HashMap<>();

	public void parse(String diagram) {
		SourceStringReader reader = new SourceStringReader(this.nomalizeDiagram(diagram));
		for (BlockUml blockUml : reader.getBlocks()) {
			if (blockUml.getDiagram() instanceof ClassDiagram) {
				ClassDiagram classDiagram = (ClassDiagram) blockUml.getDiagram();
				this.parseClasses(classDiagram);
				this.parseAssociations(classDiagram);
			}
		}

	}

	private String nomalizeDiagram(String diagram) {
		return diagram.replace("```plantuml", "@startuml").replace("```", "@enduml");
	}

	/*
	 * 
	 */
	private void parseClasses(ClassDiagram classDiagram) {
		Collection<IGroup> groups = classDiagram.getGroups(false);
		for (IGroup group : groups) {
			String packageName = group.getCode().getFullName();
			for (ILeaf leaf : group.getLeafsDirect()) {
				leaf.getParentContainer().getCode().getFullName();
				JavaClassSource javaClass = Roaster.create(JavaClassSource.class);
				javaClass.setPackage(packageName);
				javaClass.setName(leaf.getDisplay().asStringWithHiddenNewLine());
				this.javaClassSources.put(javaClass.getName(), javaClass);
				addJpaAnnotationToClass(javaClass);
				for (Member member : leaf.getBodier().getFieldsToDisplay()) {
					if (!StringUtils.isEmpty(member.getDisplay(false))) {
						log.info("Iniciando parse para atributo: {}/{}", member.getDisplay(false), javaClass.getName());
						this.parserFields(javaClass, member);
					}
				}
			}
		}
	}

	private void addJpaAnnotationToClass(JavaClassSource javaClass) {
		javaClass.addImport("javax.persistence.Entity");
		javaClass.addAnnotation("Entity");
		javaClass.addImport("org.hibernate.annotations.DynamicInsert");
		javaClass.addAnnotation("DynamicInsert");
		javaClass.addImport("org.hibernate.annotations.DynamicUpdate");
		javaClass.addAnnotation("DynamicUpdate");
		javaClass.addImport("lombok.Getter");
		javaClass.addAnnotation("Getter");
		javaClass.addImport("lombok.Setter");
		javaClass.addAnnotation("Setter");
	}

	private void parserFields(JavaClassSource javaClass, Member member) {
		// @formatter:off
		this.fieldMappers.stream()
			.peek(fieldMapper -> fieldMapper.init(javaClass, member))
			.forEach(FieldMapper::map);
		// @formatter:on
	}

	private void parseAssociations(ClassDiagram classDiagram) {
		for (Link link : classDiagram.getEntityFactory().getLinks()) {
			this.associationMappers.forEach(action -> action.map(this.javaClassSources, link));
		}
	}

	@SneakyThrows
	public Map<String, EJavaClassImpl> write(ESBootProject project) {
		Map<String, EJavaClassImpl> classes = new HashMap<>();
		for (JavaClassSource javaClass : this.javaClassSources.values()) {
			EJavaPackage ePackage = EJavaPackageImpl.of(javaClass.getPackage());
			//			String javaFile = FilenameUtils.concat(project.getMainDir(), String.format("%s/%s.java", ePackage.getDir(), javaClass.getName()));
			//			FileUtils.writeStringToFile(new File(javaFile), javaClass.toString(), "UTF-8");
			classes.put(javaClass.getName(), new EJavaClassImpl(project, javaClass));
		}
		return classes;
	}

}
