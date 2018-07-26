package br.xtool.core.representation;

/**
 * Representação de um layout angular.
 * 
 * @author jcruz
 *
 */
public interface ENgLayout extends ENgComponent {

	enum LayoutType {
		NAV_VIEW, NAV_LIST
	}

	LayoutType getLayoutType();
}
