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
		//shapeTool에 있는 shape은 얘가 무슨 타입인지 모르기 때문에
		// Rectangle 타입으로 다운캐스팅했다가 clone하고 다시 shape으로 캐스팅
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
