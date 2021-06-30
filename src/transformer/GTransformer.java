package transformer;

import java.awt.Graphics2D;

import shapeTools.GShapeTool;

public abstract class GTransformer {

	protected GShapeTool selectedShape;
	protected double px, py;
	public GTransformer(GShapeTool selectedShape) {
		this.selectedShape = selectedShape;
	}

	abstract public void initTransforming(GShapeTool selectedShape, Graphics2D graphics2d, int x, int y) ;

	abstract public void keepTransforming(Graphics2D graphics2d, double x, double y) ;

	public void finishTransforming(Graphics2D graphics2d, int x, int y) {
	}

	public void continueTransforming(Graphics2D graphics2d, int x, int y) {

	}


}