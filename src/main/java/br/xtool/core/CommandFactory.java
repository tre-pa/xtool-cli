package br.xtool.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import picocli.CommandLine;

import java.util.List;
import java.util.Objects;

@Service
public class CommandFactory implements CommandLine.IFactory {

    @Autowired(required = false)
    private List<Fabricable> fabricables;

    @Override
    public <K> K create(Class<K> cls) throws Exception {
        if (Objects.nonNull(fabricables)) {
            for (Fabricable fabricable : fabricables) {
                if (cls.getName().equals(fabricable.getClass().getSimpleName())) {
                    return (K) fabricable;
                }
            }
        }
        return CommandLine.defaultFactory().create(cls);
    }
}
