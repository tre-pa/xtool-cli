package br.xtool.core.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
public class WorkspaceService {

	@Value("${workspace}")
	@Getter
	private String workspace;

}
