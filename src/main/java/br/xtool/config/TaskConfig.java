package br.xtool.config;

import br.xtool.annotation.Task;
import br.xtool.kt.core.AbstractTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class TaskConfig {

    @Autowired(required = false)
    private List<AbstractTask> tasks;

    @Bean("tasks")
    public Map<String, AbstractTask> getTasks() {
        Map<String, AbstractTask> tasksMap = new HashMap<>();
        for(AbstractTask task: tasks) {
            String taskName = task.getClass().getAnnotation(Task.class).type();
            tasksMap.put(taskName, task);
        }
        return tasksMap;
    }

}
