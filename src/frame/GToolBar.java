package frame;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JToolBar;

import main.GConstants.EShapeTool;

public class GToolBar extends JToolBar {
//attributes
	private static final long serialVersionUID = 1L;
	
	
	//association
	private GPanel gPanel; //친구 주소 저장 pointer
	
	public GToolBar() {
		//initialize components
		ActionHandler actionHandler = new ActionHandler();

		
		for(EShapeTool eButton : EShapeTool.values()) {
			JButton button = new JButton(eButton.getText());
			button.setBackground(Color.white);
			button.setActionCommand(eButton.toString()); //action command가 e'Shape'으로 바뀜
			button.addActionListener(actionHandler);
			this.add(button);
		}
		
		
		
		

	}
	

	public void initialize() {
		// TODO Auto-generated method stub
		//첫 시작 버튼 Rectangle
		((JButton)this.getComponent(EShapeTool.eRectangle.ordinal())).doClick(); 
		
	}


	
	public void setAssociation(GPanel gPanel) {
		// TODO Auto-generated method stub
		this.gPanel = gPanel;

	}
	
	private class ActionHandler implements ActionListener {
		private JButton exSelected;
		
		@Override
		//눌렀는지 알 수 있는 함수
		public void actionPerformed(ActionEvent event) {
			// "eRectangle"이라는 String으로 바꿨다가 다시 variable로 바꿈.
			EShapeTool eShapeTool = EShapeTool.valueOf(event.getActionCommand());
			
			JButton button = (JButton) event.getSource();
			if(exSelected!=null) {
				exSelected.setBackground(Color.white);
			}
			
			button.setBackground(Color.pink);
			
			this.exSelected = button;
			
			gPanel.setSelection(eShapeTool.getShapeTool());
		}
		
	}

	
	
}
