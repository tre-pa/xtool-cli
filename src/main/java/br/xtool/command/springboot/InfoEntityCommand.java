package br.xtool.command.springboot;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator;

import br.xtool.XtoolCliApplication;
import br.xtool.core.ConsoleLog;
import br.xtool.core.aware.SpringBootAware;
import br.xtool.core.provider.EJpaEntityValueProvider;
import br.xtool.core.representation.EJpaEntity;

//@Profile("in-dev")
@ShellComponent
public class InfoEntityCommand extends SpringBootAware {

//	@Autowired
//	private WorkspaceService workspaceService;

	@ShellMethod(key = "info:entity", value = "Exibe informações sobre as entidades JPA do projeto", group = XtoolCliApplication.XTOOL_COMMAND_GROUP)
	public void run(@ShellOption(help = "Entidade JPA", valueProvider = EJpaEntityValueProvider.class) EJpaEntity entity) throws JsonProcessingException {
//		EBootProject bootProject = this.workspaceService.getWorkingProject(EBootProject.class);
		ObjectMapper mapper = new ObjectMapper();
		JsonSchemaGenerator schemaGen = new JsonSchemaGenerator(mapper);
		JsonSchema schema = schemaGen.generateSchema(entity.getClass());
		mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		ConsoleLog.print(ConsoleLog.white(mapper.writeValueAsString(schema)));
	}

}
