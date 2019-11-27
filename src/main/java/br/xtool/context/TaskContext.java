package br.xtool.context;

import br.xtool.representation.repo.ComponentRepresentation;
import br.xtool.representation.repo.directive.TaskDefRepresentation;

@Deprecated
public class TaskContext {

    private ComponentRepresentation component;

    private TaskDefRepresentation taskDef;

    private DescriptorContext descriptorContext;

    public TaskContext(ComponentRepresentation component, TaskDefRepresentation taskDef, DescriptorContext descriptorContext) {
        this.component = component;
        this.taskDef = taskDef;
        this.descriptorContext = descriptorContext;
    }

    public ComponentRepresentation getComponent() {
        return component;
    }

    public void setComponent(ComponentRepresentation component) {
        this.component = component;
    }

    public TaskDefRepresentation getTaskDef() {
        return taskDef;
    }

    public void setTaskDef(TaskDefRepresentation taskDef) {
        this.taskDef = taskDef;
    }

    public DescriptorContext getDescriptorContext() {
        return descriptorContext;
    }

    public void setDescriptorContext(DescriptorContext descriptorContext) {
        this.descriptorContext = descriptorContext;
    }
}
