package br.xtool.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.annotation.PostConstruct;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import br.xtool.core.Workspace;
import br.xtool.core.representation.SpringBootProjectRepresentation;

@Component
@Lazy
public class ShowClassDiagramView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private Workspace workspace;

	private SpringBootProjectRepresentation springBootProjectRepresentation;
	
	private JLabel picDiagram;

	@PostConstruct
	private void init() {
		setLayout(new BorderLayout());
		this.setSize(new Dimension(550, 500));
		setLocationRelativeTo(null);
		setResizable(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		getContentPane().setBackground(Color.WHITE);
		this.picDiagram = new JLabel();
		JScrollPane scrollPane = new JScrollPane(this.picDiagram);
		this.add(scrollPane);
	}

	public void showDialog() {
		updateTitle();
		showDiagramPng();
		this.setVisible(true);
	}

	private void updateTitle() {
		this.springBootProjectRepresentation = workspace.getWorkingProject(SpringBootProjectRepresentation.class);
		setTitle(String.format("Diagrama de Classe do Projeto %s", this.springBootProjectRepresentation.getName()));
	}
	
	private void showDiagramPng() {
		picDiagram.setIcon(new ImageIcon(springBootProjectRepresentation.getMainDomainClassDiagram().getPng()));
	}

}
