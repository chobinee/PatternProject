package menu;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JColorChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import frame.GPanel;
import main.GConstants.EColorMenuItem;

public class GColorMenu extends JMenu {
	private static final long serialVersionUID = 1L;

	//private JMenuItem newItem;
	//components

	
	//associations
	private GPanel panel;



	public GColorMenu(String text) {
		super(text);

		ActionHandler actionHandler = new ActionHandler();

		for(EColorMenuItem eColorMenuItem : EColorMenuItem.values()) {
			JMenuItem menuItem = new JMenuItem(eColorMenuItem.getText());
			menuItem.setActionCommand(eColorMenuItem.name()); //name¿∫ string ¿Ã∏ß
			menuItem.addActionListener(actionHandler);
			this.add(menuItem);
		}

		

	}


	public void setAsscociation(GPanel panel) {
		this.panel = panel;

	}


	
	private void setLineColor() {
		JColorChooser chooser=new JColorChooser();
		@SuppressWarnings("static-access")
		Color selectedColor = chooser.showDialog(null, "Color", Color.yellow);
		
		this.panel.setLineColor(selectedColor);
		
	}
	
	private void setFillColor() {
		JColorChooser chooser=new JColorChooser();
		@SuppressWarnings("static-access")
		Color selectedColor = chooser.showDialog(null, "Color", Color.yellow);
		
		this.panel.setFillColor(selectedColor);
		
	}



	private class ActionHandler implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			EColorMenuItem eColorMenuItem = EColorMenuItem.valueOf(e.getActionCommand());
			switch(eColorMenuItem) {

			case eLineColor:
				setLineColor();
				break;
				
			case eFillColor:
				setFillColor();
				break;

			default:
				break;
			}
		}




	}
}
