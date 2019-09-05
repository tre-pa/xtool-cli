package br.xtool.core.representation.springboot;

import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.commons.lang3.StringUtils;

import br.xtool.core.implementation.representation.JavaPackageRepresentationImpl;
import br.xtool.core.implementation.representation.PomRepresentationImpl;
import br.xtool.core.representation.ProjectRepresentation;
import br.xtool.core.representation.angular.NgProjectRepresentation;
import strman.Strman;

/**
 * Representação de um projeto multi-módulo SpringBoot e Angular.
 * 
 * @author jcruz
 *
 */
public interface SpringBootFullStackProjectRepresentation extends ProjectRepresentation {

	/**
	 * Retorna a referência ao projeto SpringBoot.
	 * 
	 * @return
	 */
	SpringBootProjectRepresentation getSpringBootProject();

	/**
	 * Retorna a referência ao projeto Angular.
	 * 
	 * @return
	 */
	NgProjectRepresentation getAngularProject();

	/**
	 * Retorna a representação do pom.xml do projeto multi-módulo.
	 * 
	 * @return {@link PomRepresentation}
	 */
	PomRepresentation getPom();

	/**
	 * Verifica se o path possui um projeto spring boot válido.
	 * 
	 * @param path Caminho do projeto
	 * @return
	 */
	static boolean isValid(Path path) {
		Path pomFile = path.resolve("pom.xml");
		if (Files.exists(pomFile)) {
			PomRepresentation ePom = PomRepresentationImpl.of(pomFile);
			if (ePom.getParentVersion().isPresent() && ePom.isMultiModule()) {
				return ePom.getParentGroupId().get().equals("org.springframework.boot");
			}
		}
		return false;
	}

	/**
	 * Gera um nome de projeto válido.
	 * 
	 * @param commomName
	 * @return
	 */
	static String genProjectName(String commomName) {
		return StringUtils.lowerCase(Strman.toKebabCase(commomName));
	}

	static String genBaseClassName(String projectName) {
		return Strman.toStudlyCase(projectName.endsWith("Application") ? projectName.replace("Application", "") : projectName);

	}

	static JavaPackageRepresentation genRootPackage(String projectName) {
		String packageName = JavaPackageRepresentation.getDefaultPrefix().concat(".").concat(StringUtils.join(StringUtils.split(Strman.toKebabCase(projectName), "-"), "."));
		return JavaPackageRepresentationImpl.of(packageName);
	}
}
