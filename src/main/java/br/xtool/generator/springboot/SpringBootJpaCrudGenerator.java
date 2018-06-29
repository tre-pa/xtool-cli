package br.xtool.generator.springboot;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import org.jdom2.JDOMException;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import com.google.common.collect.ImmutableMap;

import br.xtool.XtoolCliApplication;
import br.xtool.core.Log;
import br.xtool.core.command.SpringBootCommand;
import br.xtool.core.provider.EntityRepresentationValueProvider;
import br.xtool.core.representation.EntityRepresentation;

/**
 * Comando que gera um classe CRUD no projeto Spring Boot
 * 
 * @author jcruz
 *
 */
@ShellComponent
public class SpringBootJpaCrudGenerator extends SpringBootCommand {

	@ShellMethod(key = "gen-springboot-jpa-crud", value = "Gera as classes de CRUD (JpaRepository, Service e Rest) para entidade JPA", group = XtoolCliApplication.SPRINGBOOT_COMMAND_GROUP)
	public void run(@ShellOption(help = "Entidade JPA") String entity) throws IOException, JDOMException {
		/*
		 * Cria o mapa com as variáveis do gerador.
		 */
		//// @formatter:off
		Map<String, Object> vars = ImmutableMap.<String, Object>builder()
				.put("entity", entity)
				.build();
		
		
		String context = this.getProject()
			.getApplicationProperties()
				.get("server.contextPath")
				.orElse("Sem context");
		
		System.out.println("Context: "+context);
		
		this.getProject().getApplicationProperties()
			.set("abc", "1");
		this.getProject().getApplicationProperties()
			.commitUpdates();
		//properties.l
		
		
		// @formatter:on

		// getProject().getPom().addDependency(new Pom.Dependency("foo", "lib-foo",
		// "1.0"));
		// getProject().getPom().commitUpdate();

		//System.out.println("Parent Version: " + getProject().getPom().getParentVersion());

		// getProject().getPom().getDependencies().forEach(d -> System.out.println(d));

		// showEntityAttributes(entity);

		// showEntityAnnotations(entity);

		//showSingleAssociations(entity);

		//showCollectionAssociations(entity);

		//addEntityAttribute(entity);
	}

	private void showCollectionAssociations(EntityRepresentation entity) {
		System.out.println(Log.green("\nLista de associações compostas\n"));
		// @formatter:off
		entity.getAttributes().stream()
				.filter(attr -> attr.isAssociation())
				.filter(attr -> attr.getAssociation().get().isCollectionAssociation())
				.forEach(attr -> System.out.println(attr.getName()
						.concat(" : ")
						.concat(attr.getType().getName())
						.concat("<")
						.concat(attr.getAssociation().get().getEntityTarget().getName()).concat(">")
						.concat(" Bidirectional: "+attr.getAssociation().get().isBidirectional())));
		// @formatter:on
	}

	private void showSingleAssociations(EntityRepresentation entity) {
		System.out.println(Log.green("\nLista de associações simples\n"));
		//// @formatter:off
		entity.getAttributes().stream()
			.filter(attr -> attr.isAssociation())
			.filter(attr -> attr.getAssociation().get().isSingleAssociation())
			.forEach(attr -> System.out.println(attr.getName()
						.concat(" : ")
						.concat(attr.getAssociation().get().getEntityTarget().getName())
						.concat(" Bidirectional: "+attr.getAssociation().get().isBidirectional())));
		// @formatter:on
	}

	private void showEntityAnnotations(EntityRepresentation entity) {
		System.out.println(Log.green("\nLista de annotations da class\n"));

		entity.getAnnotations().stream().forEach(a -> System.out.println(a.getName()));
	}

	private void showEntityAttributes(EntityRepresentation entity) {
		System.out.println(Log.green("Lista de atributos da classe "));
		entity.getAttributes().stream().forEach(a -> System.out.println(a.getName().concat(" : ").concat(a.getType().getName())));
	}
}
