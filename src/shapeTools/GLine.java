package shapeTools;

import java.awt.Shape;
import java.awt.geom.Line2D;

import main.GConstants.EDrawingStyle;

public class GLine extends GShapeTool {
	private static final long serialVersionUID = 1L;

	
	public GLine() {
		// TODO Auto-generated constructor stub
		super(EDrawingStyle.e2PointDrawing);
		this.shape = new Line2D.Double();
	}
	
	
	public Object clone() {
		//shapeTool�� �ִ� shape�� �갡 ���� Ÿ������ �𸣱� ������
		// Rectangle Ÿ������ �ٿ�ĳ�����ߴٰ� clone�ϰ� �ٽ� shape���� ĳ����
		 GShapeTool cloned = (GShapeTool) super.clone();
		cloned.shape = (Shape) ((Line2D) (this.shape)).clone();
		return cloned;
	}
	
	

	
	@Override
	public void setInitPoint(int x, int y) {
		// TODO Auto-generated method stub
		Line2D line = (Line2D)this.shape;
		line.setLine(x, y, x, y);
	}
	@Override
	public void setFinalPoint(int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void movePoint(int x, int y) {
		// TODO Auto-generated method stub
		Line2D line = (Line2D)this.shape;
		line.setLine(line.getX1(), line.getY1(), x, y);
	}
	


}
