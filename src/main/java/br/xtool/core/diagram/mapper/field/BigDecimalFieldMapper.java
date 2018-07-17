package br.xtool.core.diagram.mapper.field;

import java.math.BigDecimal;

import org.jboss.forge.roaster.model.source.FieldSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.springframework.stereotype.Component;

import br.xtool.core.diagram.mapper.FieldMapper;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class BigDecimalFieldMapper extends FieldMapper {

	@Override
	public void map() {
		String fieldName = this.getName();
		if (isBigDecimal()) {
			log.info("Gerando atributo 'String {}' na classe {}", fieldName, this.getClassName());
			// @formatter:off
			FieldSource<JavaClassSource> fieldSource = this.getJavaClass().addField()
				.setPrivate()
				.setType(BigDecimal.class)
				.setName(fieldName);
			// @formatter:on
			addImport("javax.persistence.Column");
			fieldSource.addAnnotation("Column");
		}
	}

}
