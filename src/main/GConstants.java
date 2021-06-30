package main;


import java.awt.Dimension;
import java.awt.Point;

import shapeTools.GLine;
import shapeTools.GOval;
import shapeTools.GPencil;
import shapeTools.GPolygon;
import shapeTools.GRectangle;
import shapeTools.GShapeTool;

//실제 클래스에서 오타 나도 영향 x
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
	
	//자식들이 쓸 수 있는 포인터
	public enum EAction {
		eDraw,
		eMove,
		eResize,
		eRotate,
		eShear
	}
	
	public enum EShapeTool {
		//버튼에 들어가는 모든 상수를 초기화시키면서 enumulation 시켜주는 type
		//array처럼 집단적 class를 만듦
		//만드는 동시에 new

		eRectangle(new GRectangle(), "Rectangle"),  // "eRectangle"이라는 String으로 바꿨다가 다시 variable로 바꿈.
		//int variable; => "variable" (문자열)
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
		eFile("파일"),
		eEdit("편집"),
		eColor("색상"),
		eHelp("도움말");
		
		private String text;

		private EMenu(String text) {
			this.text = text;

		}
		public String getText() {
			return this.text;
		}
		
	}
	public enum EFileMenuItem {
		eNew("새로만들기"),
		eOpen("열기"),
		eSave("저장"),
		eSaveAs("다른이름으로저장"),
		ePrint("프린트"),
		eExit("나가기");
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
		eLineColor("선 색상"),
		eFillColor("채우기 색상");
		private String text;
		private EColorMenuItem(String text) {
			this.text = text;
		}
		public String getText() {
			return this.text;
		}
	
	}
	
	
}
