package br.xtool.command.converter;

import br.xtool.type.JpaEntity;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

@Component
public class JpaEntityConverter implements CommandLine.ITypeConverter<JpaEntity> {
    @Override
    public JpaEntity convert(String value) throws Exception {
        return null;
    }
}
