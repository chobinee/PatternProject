package transformer;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;

import shapeTools.GShapeTool;

public class GResizer extends GTransformer {

	public GResizer(GShapeTool selectedShape) {
		super(selectedShape);
		
	}
	
	@Override
	public void initTransforming(GShapeTool selectedShape, Graphics2D graphics2d, int x, int y) {
		this.selectedShape.initTransform(graphics2d, x, y);
		this.px = x;
		this.py = y;
	}

	@Override
	public void keepTransforming(Graphics2D graphics2d, double x, double y) {

		this.selectedShape.resize(graphics2d, new Point2D.Double(px,py), new Point2D.Double(x,y));
		this.px = x;
		this.py = y;
		
	}

}
