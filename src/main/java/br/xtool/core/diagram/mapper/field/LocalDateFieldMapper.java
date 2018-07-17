package br.xtool.core.diagram.mapper.field;

import java.time.LocalDate;

import org.apache.commons.lang3.StringUtils;
import org.jboss.forge.roaster.model.source.FieldSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.springframework.stereotype.Component;

import br.xtool.core.diagram.mapper.FieldMapper;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LocalDateFieldMapper extends FieldMapper {

	@Override
	public void map() {
		String fieldName = this.getName();
		String fieldType = this.getType();
		if (StringUtils.equalsIgnoreCase(fieldType, "LocalDate")) {
			log.info("Gerando atributo 'LocalDate {}' na classe {}", fieldName, this.getClassName());
			// @formatter:off
			FieldSource<JavaClassSource> fieldSource = this.getJavaClass().addField()
				.setPrivate()
				.setType(LocalDate.class)
				.setName(fieldName);
			// @formatter:on
			addImport("javax.persistence.Column");
			fieldSource.addAnnotation("Column");
		}
	}

}
