package br.xtool.core.model;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jboss.forge.roaster.model.source.JavaClassSource;

/**
 * Classe que representa um entidade JPA
 * 
 * @author jcruz
 *
 */
public class Entity implements Comparable<Entity> {

	private JavaClassSource javaClassSource;

	public Entity(JavaClassSource javaClassSource) {
		super();
		this.javaClassSource = javaClassSource;
	}

	/**
	 * Nome da classe
	 * 
	 * @return
	 */
	public String getName() {
		return javaClassSource.getName();
	}

	/**
	 * Retorna o nome qualificado da classe: pacote+class
	 * 
	 * @return
	 */
	public String getQualifiedName() {
		return javaClassSource.getQualifiedName();
	}

	public String getPackageName() {
		return javaClassSource.getPackage();
	}

	public String getParentPackageName() {
		List<String> packageItems = Arrays.asList(StringUtils.split(javaClassSource.getPackage(), "."));
		return StringUtils.join(packageItems.subList(0, packageItems.size()-1), ".");
	}
	
	public String getParentPackageDir() {
		List<String> packageItems = Arrays.asList(StringUtils.split(javaClassSource.getPackage(), "."));
		return StringUtils.join(packageItems.subList(0, packageItems.size()-1), "/");
	}

	@Override
	public int compareTo(Entity o) {
		return this.getName().compareTo(o.getName());
	}

}
