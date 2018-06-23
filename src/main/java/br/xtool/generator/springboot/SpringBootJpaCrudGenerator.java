package br.xtool.generator.springboot;

import java.io.IOException;
import java.util.Map;

import org.jboss.forge.roaster.model.JavaClass;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import com.google.common.collect.ImmutableMap;

import br.xtool.XtoolCliApplication;
import br.xtool.core.annotation.ShellGeneratorComponent;
import br.xtool.core.command.SpringBootCommand;
import br.xtool.core.model.Entity;
import br.xtool.core.provider.EntityValueProvider;

/**
 * Comando que gera um classe CRUD no projeto Spring Boot
 * 
 * @author jcruz
 *
 */
@ShellGeneratorComponent(templatePath = "generators/springboot/crud")
public class SpringBootJpaCrudGenerator extends SpringBootCommand {

	@ShellMethod(key = "gen-springboot-jpa-crud", value = "Gera as classes de CRUD (JpaRepository, Service e Rest) para entidade JPA", group = XtoolCliApplication.SPRINGBOOT_COMMAND_GROUP)
	public void run(@ShellOption(help = "Entidade JPA", valueProvider = EntityValueProvider.class) Entity entity) throws IOException {
		/*
		 * Cria o mapa com as variáveis do gerador.
		 */
		//// @formatter:off
		Map<String, Object> vars = ImmutableMap.<String, Object>builder()
				.put("entity", entity)
				.build();
		// @formatter:on
		
		entity.getAttributes().stream()
			.forEach(a -> System.out.println(a.getName().concat(" : ").concat(a.getType().getName())));
		
		System.out.println("=================================");
		
		entity.getAnnotations().stream()
			.forEach(a -> System.out.println(a.getName()));
		
		System.out.println("=========== Single Associations =========");
		
		entity.getSingleAssociations().stream()
			.forEach(attr -> System.out.println(attr.getName().concat(" : ").concat(attr.getAssociation().get().getName())));
		
		System.out.println("=========== Collection Associations =========");
		
		entity.getCollectionAssociations().stream()
			.forEach(attr -> System.out.println(attr.getName()
					.concat(" : ")
					.concat(attr.getType().getName())
					.concat("<")
					.concat(attr.getAssociation().get().getName())
					.concat(">")));
		
		entity.update(javaClass -> {
			javaClass.addField()
				.setPublic()
				.setName("Abc")
				.addAnnotation("com.fasterxml.jackson.annotation.JsonIgnoreProperties")
				.setStringArrayValue(new String[] { "pessoa", "id", "unidade" });
		});
	}
}
