package br.xtool.generator.springboot;

import java.io.IOException;
import java.util.Map;

import org.jdom2.JDOMException;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import com.google.common.collect.ImmutableMap;

import br.xtool.XtoolCliApplication;
import br.xtool.core.Log;
import br.xtool.core.command.SpringBootCommand;
import br.xtool.core.representation.EEntity;

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
		
		this.getProject().getRests()
			.stream()
			.forEach(rest -> {
				System.out.println(rest.getName()); 
				rest.getHttpPostMethods()
						.stream()
						.forEach(method -> {
							System.out.println(method.getAnnotations());
							System.out.println(method.getParameters());
						});
				System.out.println("\n");
			});
		
	}

	private void updateApplicationProperties() {
		String context = this.getProject()
			.getApplicationProperties()
				.get("server.contextPath")
				.orElse("Sem context");
		
		System.out.println("Context: "+context);
		
		this.getProject().getApplicationProperties()
			.set("abc", "1");
		this.getProject().getApplicationProperties()
			.commitUpdates();
	}

	private void showCollectionAssociations(EEntity entity) {
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

	private void showSingleAssociations(EEntity entity) {
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

	private void showEntityAnnotations(EEntity entity) {
		System.out.println(Log.green("\nLista de annotations da class\n"));

		entity.getAnnotations().stream().forEach(a -> System.out.println(a.getName()));
	}

	private void showEntityAttributes(EEntity entity) {
		System.out.println(Log.green("Lista de atributos da classe "));
		entity.getAttributes().stream().forEach(a -> System.out.println(a.getName().concat(" : ").concat(a.getType().getName())));
	}
}
