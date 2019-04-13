package br.xtool.core.implementation.representation;

import java.nio.file.Path;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang3.tuple.Pair;

import com.fasterxml.jackson.core.type.TypeReference;

import br.xtool.core.helper.JsonHelper;
import br.xtool.core.helper.StringHelper;
import br.xtool.core.representation.angular.NgPageNavigationRepresentation;
import br.xtool.core.representation.angular.NgPageRepresentation;

public class NgPageRepresentationImpl extends NgComponentRepresentationImpl implements NgPageRepresentation {

	public NgPageRepresentationImpl(Path path) {
		super(path);
	}

	@Override
	public List<NgPageNavigationRepresentation> getNavigations() {
		Pattern pattern = Pattern.compile(NgPageRepresentation.NAVIGATION_PATTERN);
		int idx = StringHelper.indexOfPattern(pattern, this.getTsFileContent()).getRight();
		String startNavigationArray = this.getTsFileContent().substring(idx);
		Pair<Integer, Integer> idxOfFirstArray = StringHelper.indexOfFirstArray(startNavigationArray);
		
		String strNavigation = startNavigationArray.substring(idxOfFirstArray.getLeft(), idxOfFirstArray.getRight());
		return JsonHelper.deserialize(strNavigation, new TypeReference<List<NgPageNavigationRepresentation>>() {});
	}

}
