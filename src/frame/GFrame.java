package frame;

import java.awt.BorderLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

import main.GConstants.CFrame;
import menu.GFileMenu;
//panel�� ���� �� �ִ� JFrame, ����� �޾ƾ� ��.
public class GFrame extends JFrame{
	//attributes
	private static final long serialVersionUID = 1L;

	//�ڽ��� �ۿ� ���̰� ���� ������ ���� ����.
	
	//components (aggregation�� �ڽ�)
	private GPanel panel;
	private GToolBar toolBar;
	private GMenuBar menuBar;
	
	private  GWindowEventer windowEventer;

	public GFrame() {

		//constructor


		//frame ���� ���� �Ӽ� ����
		//initialize attributes �Ӽ� ����
		this.setLocation(CFrame.point);
		this.setSize(CFrame.dimension);
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		
		
		
		
		//frame �ȿ� ����ִ� panel, main ���忡���� ������, �ڽ��� frame�� ������ ��.
		//initialize components ������Ʈ �����
		this.menuBar = new GMenuBar();
		this.setJMenuBar(this.menuBar); //content pane�� �ƴ϶� ���� ����
		BorderLayout layoutManager = new BorderLayout();
		this.getContentPane().setLayout(layoutManager);
		
		//�ڽ� ���
		this.toolBar = new GToolBar();
		this.getContentPane().add(this.toolBar, BorderLayout.NORTH);
		
		this.panel = new GPanel();
		this.getContentPane().add(this.panel, BorderLayout.CENTER);
		
		
		
		//set associations
		this.menuBar.setAssociation(this.panel);
		this.toolBar.setAssociation(this.panel);

		this.windowEventer = new GWindowEventer(this.panel);
		this.addWindowListener(this.windowEventer);
		

		
		
	}




	public void initialize() {
		//new�� initialize�� �׻� ����.
		this.toolBar.initialize();
		this.panel.initialize();
		
	}
	
	private class GWindowEventer
	implements WindowListener{
		private GPanel panel;
		private GFileMenu fileMenu;
		
		GWindowEventer(GPanel panel){
			this.panel = panel;
			this.fileMenu = new GFileMenu(getTitle());
			this.fileMenu.setAsscociation(this.panel);
		}
		

		@Override
		public void windowClosing(WindowEvent e) {
			this.fileMenu.exitProgram();
		}
		
		@Override
		public void windowOpened(WindowEvent e) {
		}
		@Override
		public void windowClosed(WindowEvent e) {
		}
		@Override
		public void windowIconified(WindowEvent e) {
		}
		@Override
		public void windowDeiconified(WindowEvent e) {
		}
		@Override
		public void windowActivated(WindowEvent e) {
		}
		@Override
		public void windowDeactivated(WindowEvent e) {
		}
}
}