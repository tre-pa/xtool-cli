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

import org.apache.commons.vfs2.FileChangeEvent;
import org.apache.commons.vfs2.FileListener;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.VFS;
import org.apache.commons.vfs2.impl.DefaultFileMonitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import br.xtool.core.Workspace;
import br.xtool.core.representation.plantuml.PlantClassDiagramRepresentation;
import br.xtool.core.representation.springboot.SpringBootProjectRepresentation;
import lombok.SneakyThrows;

@Component
@Lazy
public class ShowClassDiagramView extends JFrame implements FileListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private Workspace workspace;

	private SpringBootProjectRepresentation springBootProjectRepresentation;

	private JLabel picDiagram;
	
	DefaultFileMonitor fm;
	
	PlantClassDiagramRepresentation diagram;

	@PostConstruct
	private void init() {
		setLayout(new BorderLayout());
		this.setSize(new Dimension(550, 500));
		setLocationRelativeTo(null);
		setResizable(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		getContentPane().setBackground(Color.WHITE);
		this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		this.picDiagram = new JLabel();
		JScrollPane scrollPane = new JScrollPane(this.picDiagram);
		this.add(scrollPane, BorderLayout.CENTER);
		this.addWindowListener(this.onClose());
	}

	public void showDialog(PlantClassDiagramRepresentation diagram) {
		this.diagram = diagram;
		updateTitle();
		showDiagramPng();
		this.initListener();
		this.setVisible(true);
	}

	private void updateTitle() {
		this.springBootProjectRepresentation = workspace.getWorkingProject(SpringBootProjectRepresentation.class);
		setTitle(String.format("Diagrama: %s", this.springBootProjectRepresentation.getName()));
	}

	private void showDiagramPng() {
		picDiagram.setIcon(new ImageIcon(diagram.getPng()));
	}
	
	@SneakyThrows
	private void initListener() {
		FileSystemManager manager = VFS.getManager();
		FileObject file = manager.resolveFile(diagram.getPath().getParent().toFile().toString());
		fm = new DefaultFileMonitor(this);
		fm.setDelay(2500);
		fm.setRecursive(false);
		fm.addFile(file);
		fm.start();
	}

	private WindowAdapter onClose() {
		return new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				fm.stop();
			}
		};
	}

	@Override
	public void fileCreated(FileChangeEvent event) throws Exception {

	}

	@Override
	public void fileDeleted(FileChangeEvent event) throws Exception {

	}

	@Override
	public void fileChanged(FileChangeEvent event) throws Exception {
		showDiagramPng();
	}

}
