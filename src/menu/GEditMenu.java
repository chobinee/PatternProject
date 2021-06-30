package menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import frame.GPanel;
import main.GConstants.EEditMenuItem;

public class GEditMenu extends JMenu {
	private static final long serialVersionUID = 1L;
	//components

	
	//associations
	private GPanel panel;



	public GEditMenu(String text) {
		super(text);

		ActionHandler actionHandler = new ActionHandler();

		for(EEditMenuItem eEditMenuItem : EEditMenuItem.values()) {
			JMenuItem menuItem = new JMenuItem(eEditMenuItem.getText());
			menuItem.setActionCommand(eEditMenuItem.name()); //name¿∫ string ¿Ã∏ß
			menuItem.addActionListener(actionHandler);
			this.add(menuItem);
		}
		

	}


	public void setAsscociation(GPanel panel) {
		this.panel = panel;

	}


	private void undo() {
		if(this.panel.canUndo()) {
		this.panel.undo();
		}
		
	}

	private void redo() {
		if(this.panel.canRedo()) {
		this.panel.redo();
	}
	}
	
	private void cut() {
		this.panel.cutSelectedShape();
	}

	private void copy() {
		this.panel.copySelectedShape();
	}
	
	private void paste() {
		this.panel.pasteSelectedShape();
	}



	private class ActionHandler implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			EEditMenuItem eEditMenuItem = EEditMenuItem.valueOf(e.getActionCommand());
			switch(eEditMenuItem) {
			case eUndo:
				undo();
				break;
			case eRedo:
				redo();
				break;
			case eCut:
				cut();
				break;
			case eCopy:
				copy();
				break;
			case ePaste:
				paste();
				break;
			case eGroup:
				break;
			case eUnGroup:
				break;

			default:
				break;
			}
		}

	}
}
