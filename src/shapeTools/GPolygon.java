package shapeTools;

import java.awt.Polygon;

import main.GConstants.EDrawingStyle;

public class GPolygon extends GShapeTool{
	private static final long serialVersionUID = 1L;

	Polygon toCloneP;
	
	public GPolygon() {
		super(EDrawingStyle.eNPointDrawing);
		this.shape = new Polygon();
		this.toCloneP = new Polygon();
	}
	
	public Object clone(){
		 GShapeTool cloned = (GShapeTool) super.clone();
		Polygon polygon = new Polygon(this.toCloneP.xpoints, this.toCloneP.ypoints, this.toCloneP.npoints);
		cloned.shape = polygon;
		
		 return cloned;
	}

	 

	@Override
	public void setInitPoint(int x, int y) {
		
		Polygon polygon = (Polygon)this.shape;
		
		this.toCloneP = new Polygon();
		
		polygon.addPoint(x, y);
		polygon.addPoint(x, y);
		this.toCloneP.addPoint(x, y);
		this.toCloneP.addPoint(x, y);
	}
	
	public void setIntermediatePoint(int x, int y) {
		Polygon polygon = (Polygon)this.shape;
		polygon.addPoint(x, y);
		this.toCloneP.addPoint(x, y);
	}

	@Override
	public void setFinalPoint(int x, int y) {
		
	}



	@Override
	public void movePoint(int x, int y) {
		Polygon polygon = (Polygon)this.shape;
		polygon.xpoints[polygon.npoints-1] = x;
		polygon.ypoints[polygon.npoints-1] = y;
		
		this.toCloneP.xpoints[this.toCloneP.npoints-1] = x;
		this.toCloneP.ypoints[this.toCloneP.npoints-1] = y;
	}
	
}
