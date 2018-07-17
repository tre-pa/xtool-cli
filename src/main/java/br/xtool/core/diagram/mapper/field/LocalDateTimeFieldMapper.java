package br.xtool.core.diagram.mapper.field;

import java.time.LocalDateTime;

import org.jboss.forge.roaster.model.source.FieldSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.springframework.stereotype.Component;

import br.xtool.core.diagram.mapper.FieldMapper;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LocalDateTimeFieldMapper extends FieldMapper {

	@Override
	public void map() {
		String fieldName = this.getName();
		if (isLocalDateTime()) {
			log.info("Gerando atributo 'LocalDateTime {}' na classe {}", fieldName, this.getClassName());
			// @formatter:off
			FieldSource<JavaClassSource> fieldSource = this.getJavaClass().addField()
				.setPrivate()
				.setType(LocalDateTime.class)
				.setName(fieldName);
			// @formatter:on
			addImport("javax.persistence.Column");
			fieldSource.addAnnotation("Column");
		}
	}

}
