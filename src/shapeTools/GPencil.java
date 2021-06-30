package shapeTools;

import java.awt.Shape;
import java.awt.geom.Path2D;

import main.GConstants.EDrawingStyle;

public class GPencil extends GShapeTool {
	private static final long serialVersionUID = 1L;
	
	public GPencil() {
		super(EDrawingStyle.penDrawing);
		this.shape = new Path2D.Double();
	}
	
	public Object clone() {
		//shapeTool�� �ִ� shape�� �갡 ���� Ÿ������ �𸣱� ������
		// Rectangle Ÿ������ �ٿ�ĳ�����ߴٰ� clone�ϰ� �ٽ� shape���� ĳ����
		 GShapeTool cloned = (GShapeTool) super.clone();
		cloned.shape = (Shape) ((Path2D) (this.shape)).clone();
		return cloned;
	}
	
	
	@Override
	public void setInitPoint(int x, int y) {
		// TODO Auto-generated method stub
		Path2D path = (Path2D)this.shape;
		path.moveTo(x, y);
		
	}
	@Override
	public void setFinalPoint(int x, int y) {
	}

	@Override
	public void movePoint(int x, int y) {
	
		Path2D path = (Path2D)this.shape;
		path.lineTo(x, y);
	}
	

}
