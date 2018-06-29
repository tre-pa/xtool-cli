package br.xtool.core.command;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellMethodAvailability;

import br.xtool.core.WorkContext;
import br.xtool.core.representation.DirectoryRepresentation.Type;

public class RegularCommand {

	@Autowired
	private WorkContext workContext;

	/**
	 * Define a disponibilidade dos comando do grupo Spring Boot.
	 * 
	 * @return
	 * @throws IOException
	 */
	@ShellMethodAvailability
	public Availability availabilitySpringBootCommand() throws IOException {
		return workContext.getDirectory().getType().equals(Type.REGULAR) ? Availability.available() : Availability.unavailable("O commando não é aplicável ao diretório atual.");
	}

}
