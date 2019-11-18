package br.xtool.implementation.context;

import br.xtool.context.WorkspaceContext;
import br.xtool.implementation.representation.WorkspaceRepresentationImpl;
import br.xtool.representation.WorkspaceRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.file.Path;

@Service
public class WorkspaceContextImpl implements WorkspaceContext {

    @Value("${workspace}")
    private Path home;

    /*
     * (non-Javadoc)
     *
     * @see br.xtool.core.service.WorkspaceService#getWorkspace()
     */
    @Override
    public WorkspaceRepresentation getWorkspace() {
        return new WorkspaceRepresentationImpl(home);
    }

}
