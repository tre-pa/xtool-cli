package br.xtool.core.diagram.mapper.field;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import br.xtool.core.diagram.mapper.FieldMapper;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class DateFieldMapper extends FieldMapper {

	@Override
	public void map() {
		String fieldName = this.getName();
		String fieldType = this.getType();
		if (StringUtils.equalsIgnoreCase(fieldType, "Date")) {
			log.info("Gerando atributo 'Date {}' na classe {}", fieldName, this.getClassName());
			// @formatter:off
			this.getJavaClass().addField()
				.setPrivate()
				.setType(Date.class)
				.setName(fieldName);
			// @formatter:on
		}
	}

}
