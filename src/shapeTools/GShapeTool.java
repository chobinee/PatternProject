package shapeTools;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.io.Serializable;

import main.GConstants;
import main.GConstants.EAction;
import main.GConstants.EDrawingStyle;



abstract public class GShapeTool implements Serializable, Cloneable{

	//이름만 있는 object, 함수가 추상 함수이므로 추상 클래스여야 함.
	//자식이 볼 수 있게 protect
	//attributes
	private static final long serialVersionUID = 1L;
	
	
	public enum EAnchors{
		x0y0,
		x0y1,
		x0y2,
		x1y0,
		x1y2,
		x2y0,
		x2y1,
		x2y2,
		RR //rotate
		
	}
	

	private EDrawingStyle eDrawingStyle;
	protected Shape shape;
	private Ellipse2D[] anchors;
	private boolean isSelected;
	private EAnchors selectedAnchor;
	private EAction eAction;
	protected AffineTransform affineTransform;
	private Color lineColor;
	private Color fillColor;


	//working variables



	public GShapeTool(EDrawingStyle eDrawingState) {
		this.anchors = new Ellipse2D.Double[EAnchors.values().length];
		for(EAnchors eAnchor : EAnchors.values())
		{
			this.anchors[eAnchor.ordinal()] = new Ellipse2D.Double();
		}
		this.eDrawingStyle = eDrawingState;
		this.isSelected = false;
		this.selectedAnchor = null;
		
		this.affineTransform = new AffineTransform();
		this.affineTransform.setToIdentity();
		
		this.lineColor = Color.black;
		this.fillColor = null;
		
	}
	
	
	public Object clone(){
		GShapeTool cloned = null;
		try {
			cloned = (GShapeTool) super.clone();
			for(EAnchors eAnchor : EAnchors.values())
			{
				cloned.anchors[eAnchor.ordinal()] 
						= (Ellipse2D) this.anchors[eAnchor.ordinal()].clone();
			}
			
			cloned.affineTransform = 
					(AffineTransform) this.affineTransform.clone();
			
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}
		return cloned;
	}




	//getters & setters
	public EDrawingStyle geteDrawingStyle() {
		return this.eDrawingStyle;
	}

	public EAction getAction() {
		return this.eAction;
	}
	
	public EAnchors getSelectedAnchor() {
		return this.selectedAnchor;

	}


	//interface


	public  EAction containes (int x, int y) { //선택됐는가?
		this.eAction = null;
		if(this.isSelected) {
		//앵커 체크
			for(int i  = 0; i<this.anchors.length-1;  i++) {
				if(this.affineTransform.createTransformedShape(this.anchors[i]).contains(x,y)) {
					this.selectedAnchor = EAnchors.values()[i];
					this.eAction = EAction.eResize;
				}
			}
			if(this.affineTransform.createTransformedShape(
					this.anchors[EAnchors.RR.ordinal()]).contains(x,y)) {
				this.eAction = EAction.eRotate;
			}
		} if(this.affineTransform.createTransformedShape(this.shape).contains(x,y)) {
			this.eAction = EAction.eMove;
		}
		
		return this.eAction;
		//true면 setSelected 만들고 앵커를 그려야 함
		
	}
	
	public void initTransform(Graphics2D graphics2d, double x, double y) {

	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
	

	public void move(Graphics2D graphics2d, double dx, double dy) {
		//move
		this.draw(graphics2d);
		this.affineTransform.translate(dx, dy);
		this.draw(graphics2d);

	}
	
	
	public void resize(Graphics2D graphics2d, Point2D prev, Point2D current) {

		this.draw(graphics2d);
		
		
		double px = prev.getX();
		double py = prev.getY();
		double cx = current.getX();
		double cy = current.getY();
		double deltaW = 0;
		double deltaH = 0;
		double width = this.affineTransform.createTransformedShape(this.shape).getBounds().getWidth();
		double height = this.affineTransform.createTransformedShape(this.shape).getBounds().getHeight();
	
		
		switch(this.getSelectedAnchor()) {
		case x2y1 : deltaW = cx-px; 	deltaH = 0; break;
		case x0y1 : deltaW = -(cx-px); deltaH = 0; break;
		case x1y2 : deltaW = 0; deltaH = cy-py; break;
		case x1y0 : deltaW = 0; deltaH = -(cy-py); break;
		case x2y2 : deltaW = cx-px; deltaH = cy-py; break;
		case x2y0 : deltaW = cx-px; deltaH = -(cy-py); break;
		case x0y2 : deltaW = -(cx-px); deltaH = cy-py; break;
		case x0y0 : deltaW = -(cx-px); deltaH = -(cy-py); break;
		
		default : break;
		}
		
		double xRatio = 1.0;
		double yRatio = 1.0;
		
		if(width>0) {
			xRatio = deltaW / width + xRatio;
		} else if(width<0) {
			xRatio = deltaW / xRatio - width;
		}
		if(height>0) {
			yRatio = deltaH / height + yRatio;
		} else if(height<0) {
			yRatio = deltaH / yRatio - height;
			
		}
		
		Point2D p = new Point2D.Double();
		p.setLocation(this.getResizeOrigin());
		
		this.affineTransform.translate(p.getX(), p.getY());
		this.affineTransform.scale(xRatio, yRatio);
		this.affineTransform.translate(-p.getX(), -p.getY());
		
		this.draw(graphics2d);
		
	}
	

	
	public void rotate(Graphics2D graphics2d, Point2D pStart, Point2D pEnd) {

		this.draw(graphics2d);
		
		double centerX = this.shape.getBounds().getCenterX();
		double centerY  = this.shape.getBounds().getCenterY();
		
		double startAngle = Math.toDegrees(
				Math.atan2(centerX-pStart.getX(), centerY-pStart.getY()));
		double endAngle = Math.toDegrees(
				Math.atan2(centerX-pEnd.getX(), centerY-pEnd.getY()));
		
		double rotationAngle = startAngle - endAngle;
		
		if(rotationAngle<0) {
			rotationAngle+=360;
		}
		
		this.affineTransform.rotate(Math.toRadians(rotationAngle), centerX, centerY);
		
		this.draw(graphics2d);
		
		
	}

	public void finishTransforming(Graphics2D graphics2d, int x, int y) {
		
	}


	private void drawAnchors(Graphics2D graphics) {
		int wAnchor = GConstants.wAnchor;
		int hAnchor = GConstants.hAnchor;
		
		Rectangle rectangle = this.shape.getBounds();
		int x0 = rectangle.x-wAnchor/2;
		int x1 = rectangle.x+rectangle.width/2-wAnchor/2;
		int x2 = rectangle.x+rectangle.width-wAnchor/2;
		int y0 = rectangle.y-hAnchor/2;
		int y1 = rectangle.y + rectangle.height/2-hAnchor/2;
		int y2 = rectangle.y + rectangle.height-hAnchor/2;
		
		this.anchors[EAnchors.x0y0.ordinal()].setFrame(x0,y0,wAnchor, hAnchor);
		this.anchors[EAnchors.x0y1.ordinal()].setFrame(x0,y1,wAnchor, hAnchor);
		this.anchors[EAnchors.x0y2.ordinal()].setFrame(x0,y2,wAnchor, hAnchor);
		this.anchors[EAnchors.x1y0.ordinal()].setFrame(x1,y0,wAnchor, hAnchor);
		this.anchors[EAnchors.x1y2.ordinal()].setFrame(x1,y2,wAnchor, hAnchor);
		this.anchors[EAnchors.x2y0.ordinal()].setFrame(x2,y0,wAnchor, hAnchor);
		this.anchors[EAnchors.x2y1.ordinal()].setFrame(x2,y1,wAnchor, hAnchor);
		this.anchors[EAnchors.x2y2.ordinal()].setFrame(x2,y2,wAnchor, hAnchor);
		this.anchors[EAnchors.RR.ordinal()].setFrame(x1, y0-20, wAnchor, hAnchor);
		
		for (EAnchors eAnchor : EAnchors.values()) {
			graphics.setColor(Color.white);
			graphics.fill(this.affineTransform.createTransformedShape(
					this.anchors[eAnchor.ordinal()]));
			graphics.setColor(this.lineColor);
			graphics.draw(this.affineTransform.createTransformedShape(
					this.anchors[eAnchor.ordinal()]));
		}
		

	}


	public void setLineColor(Color color) {
		this.lineColor = color;
	}
	
	public void setFillColor(Color color) {
		this.fillColor = color;
	}

	public void draw(Graphics2D graphics2d) {		
		graphics2d.setColor(this.lineColor);
		graphics2d.draw(this.affineTransform.createTransformedShape(this.shape));
		if(this.fillColor == null) {
			this.fillColor = Color.white;		
		}
		graphics2d.setColor(this.fillColor);
		graphics2d.fill(this.affineTransform.createTransformedShape(this.shape));
		if(isSelected) {
			this.drawAnchors(graphics2d);
		}
	}
	

	public void animate(Graphics2D graphics2d, int x, int y) {
		// ShapeTool에 있는 추상 메소드 draw를 overriding
		this.draw(graphics2d);
		this.movePoint(x, y);
		this.draw(graphics2d);
		
	}
	

	public void penAnimate(Graphics2D graphics2d, int x, int y, GShapeTool selectedShape) {
		this.draw(graphics2d);
		this.movePoint(x, y);
		this.draw(graphics2d);
		
	}


	//interface
	public abstract void setInitPoint(int x, int y);
	public void setIntermediatePoint(int x, int y) {}	
	public abstract void setFinalPoint(int x, int y);
	public abstract void movePoint(int x, int y);





	public Point2D getResizeOrigin() {
		Point2D p = new Point2D.Double();

		switch(this.selectedAnchor) {
		case x2y1 : p.setLocation(this.affineTransform.createTransformedShape(this.anchors[1]).getBounds().getCenterX(), 0);break;
		case x0y1 : p.setLocation(this.affineTransform.createTransformedShape(this.anchors[6]).getBounds().getCenterX(), 0); break;
		case x1y2 : p.setLocation(0, this.affineTransform.createTransformedShape(this.anchors[3]).getBounds().getCenterY()); break;
		case x1y0 : p.setLocation(0, this.affineTransform.createTransformedShape(this.anchors[4]).getBounds().getCenterY()); break;
		case x2y2 : p.setLocation(this.affineTransform.createTransformedShape(this.anchors[0]).getBounds().getCenterX(), this.affineTransform.createTransformedShape(this.anchors[0]).getBounds().getCenterY()); break;
		case x2y0 : p.setLocation(this.affineTransform.createTransformedShape(this.anchors[2]).getBounds().getCenterX(), this.affineTransform.createTransformedShape(this.anchors[2]).getBounds().getCenterY()); break;
		case x0y2 : p.setLocation(this.affineTransform.createTransformedShape(this.anchors[5]).getBounds().getCenterX(), this.affineTransform.createTransformedShape(this.anchors[5]).getBounds().getCenterY()); break;
		case x0y0 : p.setLocation(this.affineTransform.createTransformedShape(this.anchors[7]).getBounds().getCenterX(), this.affineTransform.createTransformedShape(this.anchors[7]).getBounds().getCenterY()); break;
		default:break;
		}
		return p;
	}





















}
