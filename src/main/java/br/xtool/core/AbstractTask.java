package br.xtool.core;

import br.xtool.representation.repo.directive.TaskDefRepresentation;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractTask {

    @Autowired
    private Console console;

    public void exec(TaskDefRepresentation task) {
        console.debug(this.getClass().getSimpleName()+".run()");
        validate();
        console.println(String.format("\tTask: %s ", task.getName()));
        process();
    }

    protected abstract void process();

    protected abstract boolean validate();


}
