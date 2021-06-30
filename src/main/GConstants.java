package main;


import java.awt.Dimension;
import java.awt.Point;

import shapeTools.GLine;
import shapeTools.GOval;
import shapeTools.GPencil;
import shapeTools.GPolygon;
import shapeTools.GRectangle;
import shapeTools.GShapeTool;

//���� Ŭ�������� ��Ÿ ���� ���� x
public class GConstants {
	public static class CFrame {
		public final static Point point = new Point(500,100);
		public final static Dimension dimension = new Dimension(400,600);
	}
	
	public enum EDrawingStyle {
		e2PointDrawing,
		eNPointDrawing, 
		penDrawing,
		textDrawing
	}
	
	public final static int wAnchor = 10;
	public final static int hAnchor = 10;
	
	//�ڽĵ��� �� �� �ִ� ������
	public enum EAction {
		eDraw,
		eMove,
		eResize,
		eRotate,
		eShear
	}
	
	public enum EShapeTool {
		//��ư�� ���� ��� ����� �ʱ�ȭ��Ű�鼭 enumulation �����ִ� type
		//arrayó�� ������ class�� ����
		//����� ���ÿ� new

		eRectangle(new GRectangle(), "Rectangle"),  // "eRectangle"�̶�� String���� �ٲ�ٰ� �ٽ� variable�� �ٲ�.
		//int variable; => "variable" (���ڿ�)
		eOval(new GOval(), "Oval"),
		eLine(new GLine(), "Line"),
		ePoligon(new GPolygon(),  "Polygon"),
		ePencil(new GPencil(), "Pencil");
		
		private GShapeTool shapeTool;
		private String text;

		private EShapeTool(GShapeTool shapeTool, String text) {
			this.shapeTool = shapeTool;
			this.text = text;
		}
		public GShapeTool getShapeTool() {
			return this.shapeTool;
		}
		
		public String getText() {
			return this.text;
	} 	
}
	public enum EMenu {
		eFile("����"),
		eEdit("����"),
		eColor("����"),
		eHelp("����");
		
		private String text;

		private EMenu(String text) {
			this.text = text;

		}
		public String getText() {
			return this.text;
		}
		
	}
	public enum EFileMenuItem {
		eNew("���θ����"),
		eOpen("����"),
		eSave("����"),
		eSaveAs("�ٸ��̸���������"),
		ePrint("����Ʈ"),
		eExit("������");
		private String text;
		private EFileMenuItem(String text) {
			this.text = text;
		}
		public String getText() {
			return this.text;
		}
	
	}
	

	public enum EEditMenuItem {
		eUndo("Undo"),
		eRedo("Redo"),
		eCut("Cut"),
		eCopy("Copy"),
		ePaste("Paste"),
		eGroup("Group"),
		eUnGroup("UnGroup");
		private String text;
		private EEditMenuItem(String text) {
			this.text = text;
		}
		public String getText() {
			return this.text;
		}
	
	}
	
	public enum EColorMenuItem {
		eLineColor("�� ����"),
		eFillColor("ä��� ����");
		private String text;
		private EColorMenuItem(String text) {
			this.text = text;
		}
		public String getText() {
			return this.text;
		}
	
	}
	
	
}
