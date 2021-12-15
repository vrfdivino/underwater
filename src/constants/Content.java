package constants;

import java.util.ArrayList;

public class Content {
	
	public static String ABOUT_TITLE = "About us";
	public static String INSTSRUCTION_TITLE = "Instruction";
	
	private ArrayList<String> about_content = new ArrayList<String>();
	private ArrayList<String> instruction_content = new ArrayList<String>();
	
	
	public Content() {
		buildAboutContent();
		buildInstructionContent();
	}
	
	private void buildAboutContent() {
		about_content.add("This project was created by Dave Jimenez & Von Divino as a final requirement in CMSC 22: Object-Oriented Programming.");
		about_content.add("Assets used in this made by hand by the developers. Libraries used include JavaFX, JDBC, and the Java Programming Language.");
		about_content.add("All Rights Reserved | 2021");
	}

	
	private void buildInstructionContent() {
		instruction_content.add("Create New Game");
		instruction_content.add("To start, enter your username at the splash screen. Then, click the new game button.");
		
		instruction_content.add("Game Play");
		instruction_content.add("Have you played the battleship in an old Nokia phone before? Underwater plays the same. Use your arrow keys to move the diver and press space to shoot the enemies.");
	
		instruction_content.add("How To Win?");
		instruction_content.add("Your goal is to kill the enemies as fast as you can, as much as you can in a minute. Good luck!");
		
		instruction_content.add("Be the Best!");
		instruction_content.add("Show to the world that you are the best in our leaderboard.");
	}
	
	public ArrayList<String> getAboutContent() {
		return this.about_content;
	}
	
	public ArrayList<String> getInstructionContent() {
		return this.instruction_content;
	}

}
