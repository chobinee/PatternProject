package frame;

import java.awt.BorderLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

import main.GConstants.CFrame;
import menu.GFileMenu;
//panel을 담을 수 있는 JFrame, 상속을 받아야 함.
public class GFrame extends JFrame{
	//attributes
	private static final long serialVersionUID = 1L;

	//자식이 밖에 보이게 노출 시켜줄 수도 있음.
	
	//components (aggregation의 자식)
	private GPanel panel;
	private GToolBar toolBar;
	private GMenuBar menuBar;
	
	private  GWindowEventer windowEventer;

	public GFrame() {

		//constructor


		//frame 안의 내부 속성 정의
		//initialize attributes 속성 세팅
		this.setLocation(CFrame.point);
		this.setSize(CFrame.dimension);
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		
		
		
		
		//frame 안에 들어있는 panel, main 입장에서는 손자임, 자식인 frame이 만들어야 함.
		//initialize components 컴포넌트 만들기
		this.menuBar = new GMenuBar();
		this.setJMenuBar(this.menuBar); //content pane이 아니라 직접 붙임
		BorderLayout layoutManager = new BorderLayout();
		this.getContentPane().setLayout(layoutManager);
		
		//자식 등록
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
		//new와 initialize는 항상 같이.
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