package main;

import frame.GFrame;

public class GMain {
	private static GFrame frame;
	public static void main(String[] args) {
		frame = new GFrame();
		frame.initialize(); //초기화
		//내부 속성 아님
		frame.setVisible(true);
		
	}

}
