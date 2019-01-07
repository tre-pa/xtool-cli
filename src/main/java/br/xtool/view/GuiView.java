package br.xtool.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.annotation.PostConstruct;
import javax.swing.JDialog;

import org.springframework.stereotype.Component;

@Component
public class GuiView extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@PostConstruct
	private void init() {
		setTitle("Inicialização do Ponto Biométrico.");
		setLayout(new BorderLayout());
		this.setSize(new Dimension(550, 500));
		setLocationRelativeTo(null);
		setModal(true);
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		getContentPane().setBackground(Color.WHITE);
	}

}
