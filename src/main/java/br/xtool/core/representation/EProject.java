package br.xtool.core.representation;

import java.io.File;

import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationObserver;

import br.xtool.core.representation.enums.ProjectType;
import lombok.Getter;

public abstract class EProject implements FileAlterationListener {

	@Getter
	private String path;

	@Getter
	private EDirectory directory;

	public EProject(String path) {
		super();
		this.path = path;
		this.directory = EDirectory.of(path);
	}

	/**
	 * Retorna o nome do projeto.
	 * 
	 * @return
	 */
	public String getName() {
		return this.getDirectory().getBaseName();
	}

	/**
	 * Retorna o tipo de projeto atual.
	 * 
	 * @return
	 */
	public ProjectType getProjectType() {
		return this.getDirectory().getProjectType();
	}

	@Override
	public void onStart(FileAlterationObserver observer) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDirectoryCreate(File directory) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDirectoryChange(File directory) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDirectoryDelete(File directory) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStop(FileAlterationObserver observer) {
		// TODO Auto-generated method stub

	}

}
