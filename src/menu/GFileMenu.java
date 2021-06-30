package menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;

import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import frame.GPanel;
import main.GConstants.EFileMenuItem;
import shapeTools.GShapeTool;

public class GFileMenu extends JMenu {
	private static final long serialVersionUID = 1L;

	//private JMenuItem newItem;
	//components

	//associations
	private GPanel panel;
	private String file;


	public GFileMenu(String text) {
		super(text);

		ActionHandler actionHandler = new ActionHandler();

		for(EFileMenuItem eFileMenuItem : EFileMenuItem.values()) {
			JMenuItem menuItem = new JMenuItem(eFileMenuItem.getText());
			menuItem.setActionCommand(eFileMenuItem.name()); //name�� string �̸�
			menuItem.addActionListener(actionHandler);
			this.add(menuItem);
		}
		this.file = null;
	}


	public void setAsscociation(GPanel panel) {
		this.panel = panel;

	}

	@SuppressWarnings("unchecked") //Ÿ�� üũ���� ����
	private void openFile() {

		try {
			ObjectInputStream objectInputStream =  new ObjectInputStream(
					new BufferedInputStream(
							new FileInputStream(this.file)));

			Vector<GShapeTool> shapes = (Vector<GShapeTool>) objectInputStream.readObject();
			this.panel.setShapes(shapes);
			objectInputStream.close(); //�� ������ ���߿� �� ���� �� ���� ����.
			System.out.println("������ �������ϴ�. ���� ��� : "+this.file);
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 


	}

	private void saveFile() {

		try {
			ObjectOutputStream objectOutputStream
			=  new ObjectOutputStream(
					new BufferedOutputStream(
							new FileOutputStream(this.file)));

			objectOutputStream.writeObject(this.panel.getShapes());
			objectOutputStream.close(); //�� ������ ���߿� �� ���� �� ���� ����.
			this.panel.setModified(false);
			System.out.println("������ ����Ǿ����ϴ�. ���� ��� : "+this.file);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private boolean checkSaveOrNot() {
		//cancel�� ������ �ƹ��͵� ���� ���ƾ� ��,
		boolean bCancel = true;
		if(this.panel.isModified()) {
			//save
			int reply
			= JOptionPane.showConfirmDialog(this.panel, "���� ������ �����ұ��?");
			if(reply == JOptionPane.OK_OPTION) {
				this.save();
				bCancel = false;
			} else if(reply == JOptionPane.NO_OPTION) {
				this.panel.setModified(false);
				bCancel = false;
			}	
		} else {
			bCancel = false;
		}
		return bCancel;
	}

	private void nnew() {
		if(!checkSaveOrNot()) {
			this.panel.clearScreen();
			this.file = null;
		}
	}

	private void open() {
		if(!checkSaveOrNot()) { // if not cancel
			JFileChooser chooser = new JFileChooser();
			chooser.addChoosableFileFilter(new FileNameExtensionFilter("Milky (*.milky)","milky"));
			int returnVal = chooser.showOpenDialog(this.panel);
			if(returnVal == JFileChooser.APPROVE_OPTION) {
				this.file = chooser.getSelectedFile().toString();
				this.openFile();
			} 
		}
	}

	private void save() {
		if(this.panel.isModified()) { 
			if(this.file == null) {
				this.saveAs();
			} else {
				this.saveFile();
			}

		}
	}

	private void saveAs() {
		//save
		JFileChooser chooser = new JFileChooser();
		//chooser.setAcceptAllFileFilterUsed(false);
		chooser.addChoosableFileFilter(new FileNameExtensionFilter("MILKY (*.milky)", "milky"));
		int returnVal = chooser.showSaveDialog(this.panel);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			this.file = chooser.getSelectedFile().toString();
			if(!this.file.endsWith(".milky")&&!this.file.endsWith(".MILKY")) {
				this.file += ".milky";
			}

			this.saveFile();
		}
	}
	
	public void exitProgram() {
		if(!checkSaveOrNot()) {
			System.exit(0);
		} 
	}
	
	private void print() {
		Paper paper = new Paper();
		final PrinterJob printerJob = PrinterJob.getPrinterJob();
		PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
		PageFormat pageFormat = new PageFormat();
		pageFormat.setPaper(paper);
		pageFormat.setOrientation(PageFormat.LANDSCAPE);
		printerJob.setPrintable(this.panel, pageFormat);
		boolean ok = printerJob.printDialog(printRequestAttributeSet);
		if(ok) {
			try {
				printerJob.print();
			} catch(PrinterException ex){
				ex.getStackTrace();
			}
		}
	}

	private class ActionHandler implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			EFileMenuItem eMenuItem = EFileMenuItem.valueOf(e.getActionCommand());
			switch(eMenuItem) {
			case eNew:
				nnew();
				break;
			case eOpen:
				open();
				break;
			case eSave:
				save();
				break;
			case eSaveAs:
				saveAs();
				break;
			case ePrint:
				print();
				break;
			case eExit:
				exitProgram();
				break;

			default:
				break;
			}
		}
	}

}
