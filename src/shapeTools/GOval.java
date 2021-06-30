package shapeTools;

import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import main.GConstants.EDrawingStyle;

public class GOval extends GShapeTool {
	private static final long serialVersionUID = 1L;
	
	public GOval() {
		// TODO Auto-generated constructor stub
		super(EDrawingStyle.e2PointDrawing);
		this.shape = new Ellipse2D.Float();
	}
	
	public Object clone() {
		//shapeTool�� �ִ� shape�� �갡 ���� Ÿ������ �𸣱� ������
		// Rectangle Ÿ������ �ٿ�ĳ�����ߴٰ� clone�ϰ� �ٽ� shape���� ĳ����
		 GShapeTool cloned = (GShapeTool) super.clone();
		cloned.shape = (Shape) ((Ellipse2D) (this.shape)).clone();
		return cloned;
	}


	
	@Override
	public void setInitPoint(int x, int y) {
		// TODO Auto-generated method stub
		Ellipse2D ellipse = (Ellipse2D)this.shape;
		ellipse.setFrame(x,y,0,0);
	}
	@Override
	public void setFinalPoint(int x, int y) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void movePoint(int x, int y) {

		Ellipse2D ellipse = (Ellipse2D)this.shape;
		ellipse.setFrame(
				ellipse.getX(),ellipse.getY(),x-ellipse.getX(),y-ellipse.getY());
		//�����簢��
		
	}
	
	
	
	
//	public GshapeTool clone() {
//
//		GshapeTool cloned = new GOval();
//		//�θ� �����ؼ� ���� ����, ���� ���� ���� x
//		cloned.x0 = this.x0;
//		cloned.y0 = this.y0;
//		cloned.x1 = this.x1;
//		cloned.y1 = this.y1;		
//		return cloned;
//
//}
	
	
	
	
}