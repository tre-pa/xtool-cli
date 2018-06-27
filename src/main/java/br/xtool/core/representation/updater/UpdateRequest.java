package br.xtool.core.representation.updater;

public interface UpdateRequest<T extends Updatable<?>> {

	public boolean updatePolicy(T representation);

	public void apply(T representation);
}
