package shapeTools;

import java.awt.Rectangle;
import java.awt.Shape;

import main.GConstants.EDrawingStyle;

public class GRectangle extends GShapeTool {
	//attributes
	private static final long serialVersionUID = 1L;
	
	//components
	 //shape을 implements 하고 있음
	
	// constructors
	public GRectangle() {
		super(EDrawingStyle.e2PointDrawing);
		this.shape = new Rectangle();
		
	}
	

	public Object clone() {
		//shapeTool에 있는 shape은 얘가 무슨 타입인지 모르기 때문에
		// Rectangle 타입으로 다운캐스팅했다가 clone하고 다시 shape으로 캐스팅
		 GShapeTool cloned = (GShapeTool) super.clone();
		cloned.shape =  (Shape) ((Rectangle) this.shape).clone();
		cloned.affineTransform.createTransformedShape(cloned.shape);
		
		return cloned;
	}
	

	//methods

	@Override
	public void setInitPoint(int x, int y) {
		Rectangle rectangle = (Rectangle)this.shape;
		rectangle.setLocation(x,y);
		rectangle.setSize(0, 0);
	}

	@Override
	public void setFinalPoint(int x, int y) {
		
	}
	





	@Override
	public void movePoint(int x, int y) {
		// ShapeTool에 있는 추상 메소드 draw를 overriding
		Rectangle rectangle = (Rectangle)this.shape;
		rectangle.setSize(x-rectangle.x, y-rectangle.y);
		
	}




}
