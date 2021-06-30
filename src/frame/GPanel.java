package frame;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.util.Vector;

import javax.swing.JPanel;

import main.GConstants.EAction;
import main.GConstants.EDrawingStyle;
import menu.GUndoStack;
import shapeTools.GShapeTool;
import transformer.GMover;
import transformer.GResizer;
import transformer.GRotator;
import transformer.GTransformer;


public class GPanel extends JPanel implements java.awt.print.Printable{
	////////////////////variables/////////////////


	//attributes
	private static final long serialVersionUID = 1L;

	//components
	private  GMouseHandler mouseHandler;
	private Vector<GShapeTool> shapes;
	private GUndoStack undoStack;

	//association

	public void setShapes(Vector<GShapeTool> shapes) {
		this.shapes = shapes;
		this.repaint();
	}

	public Vector<GShapeTool> getShapes(){
		return this.shapes;
	}


	//working objects
	private GShapeTool shapeTool; //도구 선택된 애
	private GShapeTool selectedShape; // 지금 그리고 있는 애
	private GShapeTool copyShape;
	private GTransformer transformer;
	private boolean bModified;




	/////////////////////////////////////////////
	// getters and setters

	public void setSelection(GShapeTool shapeTool) {
		this.shapeTool = shapeTool;
	}

	public boolean isModified() {
		return this.bModified;
	}

	public void setModified(boolean bModified) {
		//new, save, open, save as에서 false로 초기화
		this.bModified = bModified;
	}

	public boolean getModified() {
		//new, save, open, save as에서 false로 초기화
		return this.bModified;
	}

	public void setLineColor(Color color) {
		if(this.selectedShape!=null) {
			this.selectedShape.setLineColor(color);
			this.bModified = true;
			this.undoStack.push(this.deepCopy(this.shapes));
			this.repaint();
		}
	}

	public void setFillColor(Color color) {
		if(this.selectedShape!=null) {
			this.selectedShape.setFillColor(color);
			this.bModified = true;
			this.undoStack.push(this.deepCopy(this.shapes));
			this.repaint();
		}
	}

	public boolean canUndo() {
		if(this.undoStack.canUndo()) {
			return true;
		} else {
			return false;
		}
	}

	public boolean canRedo() {
		if(this.undoStack.canRedo()) {
			return true;
		} else {
			return false;
		}
	}



	// constructors
	public GPanel () {

		this.undoStack = new GUndoStack();
		Vector<GShapeTool> nullShape = new Vector<GShapeTool>();
		this.undoStack.push(nullShape);


		this.shapes = new Vector<GShapeTool>();


		this.mouseHandler = new GMouseHandler();


		//mouseHandler 하나로 다 붙일 수 있음.
		this.addMouseListener(this.mouseHandler);
		this.addMouseMotionListener(this.mouseHandler);




		this.bModified = false;



	}

	public void initialize() {
		// TODO Auto-generated method stub
		this.setBackground(Color.white);


	}

	public void clearScreen() {
		// TODO Auto-generated method stub
		this.shapes.clear();
		this.repaint();
	}


	// methods

	public Vector<GShapeTool> deepCopy(Vector<GShapeTool> original){
		@SuppressWarnings("unchecked")
		Vector<GShapeTool> clonedShapes = 
		(Vector<GShapeTool>) this.shapes.clone();
		for (int i = 0; i<this.shapes.size(); i++) {
			clonedShapes.set(i, (GShapeTool) this.shapes.get(i).clone());
		}
		return clonedShapes;

	}

	public void undo() {
		this.shapes = this.undoStack.undo();
		this.shapes = this.deepCopy(this.shapes);
		this.repaint();
	}

	public void redo() {
		this.shapes = this.undoStack.redo();
		this.shapes = this.deepCopy(this.shapes);
		this.repaint();

	}

	public void cutSelectedShape() {
		this.copyShape = (GShapeTool) this.selectedShape.clone();
		this.shapes.remove(this.selectedShape);
		this.repaint();
	}


	public void copySelectedShape() {
		this.copyShape = (GShapeTool) this.selectedShape.clone();
	}

	public void pasteSelectedShape() {
		this.shapes.add(this.copyShape);
		this.bModified = true;
		this.undoStack.push(this.deepCopy(this.shapes));
		this.repaint();
	}






	//JFrame이 해야할 일을 내가 대신 하겠다
	public void paint(Graphics graphics) {
		super.paint(graphics); //부모가 원래 할 일을 하게 함.

		for(GShapeTool shape : this.shapes) {
			shape.draw((Graphics2D)graphics);
		}


	}
	private void setSelected(GShapeTool selectedShape) {
		for(GShapeTool shape : this.shapes) {
			shape.setSelected(false);
		}
		
		this.selectedShape = selectedShape;
		
		if(this.selectedShape!=null) {
			this.selectedShape.setSelected(true);
			this.repaint();
		} else {
			this.repaint();
		}


	}
	private GShapeTool onShape(int x, int y) {
		//어느 앵커 위에 있냐를 return 해줘야 함.
		
		for (GShapeTool shape : this.shapes) {
			EAction eAction = shape.containes(x, y);
			if(eAction != null) {
				return shape;
			}
		}

		return null;
	}

	private void initDrawing(int x, int y) {

		this.selectedShape = (GShapeTool) this.shapeTool.clone();
		this.selectedShape.setInitPoint(x,y);
	}

	private void keepDrawing(int x, int y) {
		Graphics2D graphics2D = (Graphics2D) getGraphics();
		//exclusive or mode (그림을 지우는 게 무슨 뜻인가?)
		graphics2D.setXORMode(getBackground());
		//erase
		this.selectedShape.animate(graphics2D,x,y);

	}

	private void keepPenDrawing(int x, int y) {
		Graphics2D graphics2D = (Graphics2D) getGraphics();
		graphics2D.setPaintMode();
		
		BasicStroke stroke = new BasicStroke(1);
		this.selectedShape = (GShapeTool) stroke.createStrokedShape((Shape) this.selectedShape);
		
		this.selectedShape.penAnimate(graphics2D,x,y, this.selectedShape);
	}

	private void setIntermediatePoint(int x, int y) {

		this.selectedShape.setIntermediatePoint(x,y);
	}

	private void finishDrawing(int x, int y) {
		this.selectedShape.setFinalPoint(x, y);
		this.shapes.add(this.selectedShape); //자기를 복제
		this.bModified = true;
		this.undoStack.push(this.deepCopy(this.shapes));
	}

	private void finishPenDrawing(int x, int y) {
		this.selectedShape.setFinalPoint(x, y);
		this.shapes.add(this.selectedShape);
		this.undoStack.push(this.deepCopy(this.shapes));

	}

	private void initTransforming(GShapeTool selectedShape, int x, int y) {
		//press

		this.selectedShape = selectedShape;
		EAction eAction = this.selectedShape.getAction();
		switch(eAction) {
		case eMove:
			this.transformer = new GMover(this.selectedShape);
			break;
		case eResize:
			this.transformer = new GResizer(this.selectedShape);
			break;			
		case eRotate:
			this.transformer = new GRotator(this.selectedShape);
			break;			
		default:
			break;
		}

		Graphics2D graphics2d =(Graphics2D) this.getGraphics();
		graphics2d.setXORMode(this.getBackground());
		this.transformer.initTransforming(selectedShape, graphics2d, x, y);

	}
	private void keepTransforming(int x, int y) {
		//drag
		Graphics2D graphics2d =(Graphics2D) this.getGraphics();
		graphics2d.setXORMode(this.getBackground());
		this.repaint();
		this.transformer.keepTransforming(graphics2d, x, y);
	}
	private void finishTransforming(int x, int y) {
		//release
		Graphics2D graphics2d =(Graphics2D) this.getGraphics();
		graphics2d.setXORMode(this.getBackground());
		this.transformer.finishTransforming(graphics2d, x, y);
		this.bModified = true;
		this.setSelected(this.selectedShape);
		this.undoStack.push(this.deepCopy(this.shapes));


	}





	/////////////////////////////////////////
	//inner class
	private class GMouseHandler
	implements MouseListener, MouseMotionListener{

		private boolean isDrawing;
		private boolean isTransforming;

		public GMouseHandler(){
			this.isDrawing = false;
			this.isTransforming = false;
		}

		@Override
		public void mousePressed(MouseEvent e) {
			if(!isDrawing) {
				//onShape일 경우 뭐할지 판단
				GShapeTool selectedShape = onShape(e.getX(),e.getY());
				if(selectedShape == null) {
					if(shapeTool.geteDrawingStyle() == EDrawingStyle.e2PointDrawing) {
						initDrawing(e.getX(), e.getY()); //초기화, 그리기 시작
						this.isDrawing = true;
					} else if(shapeTool.geteDrawingStyle() == EDrawingStyle.penDrawing) {
						initDrawing(e.getX(), e.getY());
						this.isDrawing = true;
					}
				}
				else {
					initTransforming(selectedShape, e.getX(),e.getY());
					this.isTransforming = true;

				}
			}
		}
		@Override
		public void mouseDragged(MouseEvent e) {	
			if(isDrawing) {
				if(shapeTool.geteDrawingStyle() == EDrawingStyle.e2PointDrawing) {
					keepDrawing(e.getX(),e.getY());
				} else if(shapeTool.geteDrawingStyle() == EDrawingStyle.penDrawing) {
					keepPenDrawing(e.getX(), e.getY());
				}
			}
			else if(this.isTransforming) {
				keepTransforming(e.getX(),e.getY());

			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			if(isDrawing) {
				if(shapeTool.geteDrawingStyle() == EDrawingStyle.e2PointDrawing) {
					finishDrawing(e.getX(),e.getY());	
					this.isDrawing = false;
				} else if(shapeTool.geteDrawingStyle() == EDrawingStyle.penDrawing) {
					finishPenDrawing(e.getX(), e.getY());
					this.isDrawing = false;
				}
			}else if(this.isTransforming) {
				finishTransforming(e.getX(),e.getY());
				this.isTransforming = false;
			}
		}	



		@Override
		public void mouseMoved(MouseEvent e) {
			if(isDrawing) {
				if(shapeTool.geteDrawingStyle() == EDrawingStyle.eNPointDrawing) {
					keepDrawing(e.getX(),e.getY());		
				}
			}
		}		

		private void mouseLButton1Clicked(MouseEvent e) {
			if(!isDrawing) {
				GShapeTool selectedShape = onShape(e.getX(), e.getY());
				if (selectedShape==null){
					setSelected(selectedShape);
					if(shapeTool.geteDrawingStyle() == EDrawingStyle.eNPointDrawing) {
						//polygon 그리기
						initDrawing(e.getX(), e.getY());
						this.isDrawing = true;
					}
				} else if(shapeTool.geteDrawingStyle() == EDrawingStyle.penDrawing){

				}

				else {
					setSelected(selectedShape);
				}
			}else {
				if(shapeTool.geteDrawingStyle() == EDrawingStyle.eNPointDrawing) {
					setIntermediatePoint(e.getX(), e.getY());
				}
			}
		}


		private void mouseLButton2Clicked(MouseEvent e) {
			if(isDrawing) {
				if(shapeTool.geteDrawingStyle() == EDrawingStyle.eNPointDrawing) {
					finishDrawing(e.getX(), e.getY());
					this.isDrawing = false;
				}
			}
		}	

		@SuppressWarnings("unused")
		private void mouseRButton1Clicked(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}

		@Override
		public void mouseClicked(MouseEvent e) {

			if(e.getButton() == MouseEvent.BUTTON1) { //왼쪽 클릭
				if(e.getClickCount() == 1) { //싱글 클릭
					this.mouseLButton1Clicked(e);
				} else if(e.getClickCount() == 2) { //더블 클릭
					this.mouseLButton2Clicked(e);
				}
			} 
		}

	}





	@Override
	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
		Graphics2D grapchis2D;
		
		if(pageIndex == 0) {
			grapchis2D = (Graphics2D) graphics;
			this.paint(grapchis2D);
			
			return (PAGE_EXISTS);
		}
		
		return (NO_SUCH_PAGE);
	}

























}
