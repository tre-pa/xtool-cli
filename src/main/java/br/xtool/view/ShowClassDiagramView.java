package br.xtool.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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
import br.xtool.core.representation.plantuml.PlantClassDiagramRepresentation;
import br.xtool.core.representation.springboot.SpringBootProjectRepresentation;

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
		this.addWindowListener(this.onClose());
	}

	public void showDialog(PlantClassDiagramRepresentation diagram) {
		updateTitle();
		showDiagramPng(diagram);
		this.setVisible(true);
	}

	private void updateTitle() {
		this.springBootProjectRepresentation = workspace.getWorkingProject(SpringBootProjectRepresentation.class);
		setTitle(String.format("Diagrama de Classe do Projeto %s", this.springBootProjectRepresentation.getName()));
	}

	private void showDiagramPng(PlantClassDiagramRepresentation diagram) {
		picDiagram.setIcon(new ImageIcon(diagram.getPng()));
	}
	
	private WindowAdapter onClose() {
		return new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
			}
		};
	}

}
