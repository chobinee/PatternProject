package shapeTools;

import java.awt.Rectangle;
import java.awt.Shape;

import main.GConstants.EDrawingStyle;

public class GRectangle extends GShapeTool {
	//attributes
	private static final long serialVersionUID = 1L;
	
	//components
	 //shape�� implements �ϰ� ����
	
	// constructors
	public GRectangle() {
		super(EDrawingStyle.e2PointDrawing);
		this.shape = new Rectangle();
		
	}
	

	public Object clone() {
		//shapeTool�� �ִ� shape�� �갡 ���� Ÿ������ �𸣱� ������
		// Rectangle Ÿ������ �ٿ�ĳ�����ߴٰ� clone�ϰ� �ٽ� shape���� ĳ����
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
		// ShapeTool�� �ִ� �߻� �޼ҵ� draw�� overriding
		Rectangle rectangle = (Rectangle)this.shape;
		rectangle.setSize(x-rectangle.x, y-rectangle.y);
		
	}




}
