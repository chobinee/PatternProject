package menu;

import java.util.Vector;

import shapeTools.GShapeTool;
public class GUndoStack{
	
	private static final int maxIndex = 10;
	private int bottomIndex;
	private int topIndex;
	private int size;
	
	private Vector<Vector<GShapeTool>> elements;

	
	public GUndoStack() {

		this.bottomIndex = 0;
		this.topIndex = 0;
		this.size = 0;
		this.elements = new Vector<Vector<GShapeTool>>();
		for (int i = 0; i < maxIndex; i++) {
			//������ ���� array 10�� �����
			this.elements.add(new Vector<GShapeTool>());
		}
	}
	
	public void push(Vector<GShapeTool> shapes) {
		this.topIndex ++;
		this.elements.set(this.topIndex%maxIndex, shapes);
		
		if(this.topIndex - this.bottomIndex == maxIndex) {
			this.bottomIndex ++;
		} else {
			//10�� �Ѿ�� ����.
			this.size++;
		}
	}

	public Vector<GShapeTool> undo() {
		Vector<GShapeTool> undoElement = null;
		//bottom���� ũ�ų� ���� �ٷ� ���� �����͸� ������ ��.
		if(this.topIndex >= this.bottomIndex) {
			this.topIndex --;
			undoElement =  this.elements.get(this.topIndex%maxIndex);
		}
		//top�� bottom index�� �������� ���� �� ��.
		return undoElement;
	
		}

	public boolean canUndo() {
		if(this.topIndex<1) {
		return false;
		} 
		return true;
	}
	
	public boolean canRedo() {
		if(this.topIndex-this.bottomIndex < this.size) {
		return true;
		} 
		return false;
	}

	public Vector<GShapeTool> redo() {
		Vector<GShapeTool> redoElement = null;

		if(this.topIndex-this.bottomIndex < this.size) {
			this.topIndex ++;
			redoElement = this.elements.get(this.topIndex % maxIndex);
		}
		
		return redoElement;



}
	
}
