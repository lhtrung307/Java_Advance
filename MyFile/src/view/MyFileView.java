package view;

import java.util.Scanner;

public class MyFileView {
	Scanner input = new Scanner(System.in);
	public MyFileView() {
		// TODO Auto-generated constructor stub
	}
	
	public int getChoose(){
		return Integer.parseInt(input.nextLine());
	}
}
