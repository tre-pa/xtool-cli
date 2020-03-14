package br.xtool.implementation.context;

import br.xtool.xtoolcore.context.WorkspaceContext;
import br.xtool.xtoolcore.impl.representation.WorkspaceRepresentationImpl;
import br.xtool.xtoolcore.representation.ProjectRepresentation;
import br.xtool.xtoolcore.representation.WorkspaceRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.file.Path;

@Service
public class WorkspaceContextImpl implements WorkspaceContext {

    @Value("${workspace}")
    private Path home;

    private ProjectRepresentation project;

    /*
     * (non-Javadoc)
     *
     * @see br.xtool.core.service.WorkspaceService#getWorkspace()
     */
    @Override
    public WorkspaceRepresentation getWorkspace() {
        return new WorkspaceRepresentationImpl(home);
    }

    @Override
    public void setWorkingProject(ProjectRepresentation projectRepresentation) {
        this.project = projectRepresentation;
    }

    @Override
    public ProjectRepresentation getWorkingProject() {
        return this.project;
    }
}
