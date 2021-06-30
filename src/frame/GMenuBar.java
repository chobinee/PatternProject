package frame;

import javax.swing.JMenuBar;

import main.GConstants.EMenu;
import menu.GColorMenu;
import menu.GEditMenu;
import menu.GFileMenu;

public class GMenuBar extends JMenuBar {
	private static final long serialVersionUID = 1L;

	private GFileMenu fileMenu;
	private GEditMenu editMenu;
	private GColorMenu colorMenu;
	
	public GMenuBar() {

		this.fileMenu = new GFileMenu(EMenu.eFile.getText());
		this.add(this.fileMenu);
		
		this.editMenu = new GEditMenu(EMenu.eEdit.getText());
		this.add(this.editMenu);
		
		this.colorMenu = new GColorMenu(EMenu.eColor.getText());
		this.add(this.colorMenu);
	}

	public void setAssociation(GPanel panel) {
		this.fileMenu.setAsscociation(panel);
		this.editMenu.setAsscociation(panel);
		this.colorMenu.setAsscociation(panel);
	}

}
