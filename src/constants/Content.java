package constants;

import java.util.ArrayList;

public class Content {
	
	public static String ABOUT_TITLE = "About us";
	public static String INSTSRUCTION_TITLE = "Instruction";
	
	private ArrayList<String> aboutContent = new ArrayList<String>();
	private ArrayList<String> instructionContent = new ArrayList<String>();
	
	
	public Content() {
		buildAboutContent();
		buildInstructionContent();
	}
	
	private void buildAboutContent() {
		aboutContent.add("This project was created by Dave Jimenez & Von Divino as a final requirement in CMSC 22: Object-Oriented Programming.");
		aboutContent.add("Assets used in this made by hand by the developers. Libraries used include JavaFX, JDBC, and the Java Programming Language.");
		aboutContent.add("All Rights Reserved | 2021");
	}

	
	private void buildInstructionContent() {
		instructionContent.add("Create New Game");
		instructionContent.add("To start, enter your username at the splash screen. Then, click the new game button.");
		
		instructionContent.add("Game Play");
		instructionContent.add("Have you played the battleship in an old Nokia phone before? Underwater plays the same. Use your arrow keys to move the diver and press space to shoot the enemies.");
	
		instructionContent.add("How To Win?");
		instructionContent.add("Your goal is to kill the enemies as fast as you can, as much as you can in a minute. Good luck!");
		
		instructionContent.add("Be the Best!");
		instructionContent.add("Show to the world that you are the best in our leaderboard.");
	}
	
	public ArrayList<String> getAboutContent() {
		return this.aboutContent;
	}
	
	public ArrayList<String> getInstructionContent() {
		return this.instructionContent;
	}

}
