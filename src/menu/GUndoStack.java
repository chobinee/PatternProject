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
			//포인터 담을 array 10개 만들기
			this.elements.add(new Vector<GShapeTool>());
		}
	}
	
	public void push(Vector<GShapeTool> shapes) {
		this.topIndex ++;
		this.elements.set(this.topIndex%maxIndex, shapes);
		
		if(this.topIndex - this.bottomIndex == maxIndex) {
			this.bottomIndex ++;
		} else {
			//10을 넘어가지 않음.
			this.size++;
		}
	}

	public Vector<GShapeTool> undo() {
		Vector<GShapeTool> undoElement = null;
		//bottom보다 크거나 같은 바로 이전 데이터를 꺼내야 함.
		if(this.topIndex >= this.bottomIndex) {
			this.topIndex --;
			undoElement =  this.elements.get(this.topIndex%maxIndex);
		}
		//top과 bottom index가 같아지면 끝에 온 것.
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
