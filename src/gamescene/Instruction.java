package gamescene;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;

import java.util.ArrayList;

import constants.Assets;
import gui.MenuButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.GameStage;
import parentclass.GameScene;

public class Instruction extends GameScene {
	
	private BorderPane root;
	private VBox instructionBox;
	private Label title;
	private MenuButton backButton;
	
	Instruction(GameStage gameStage) {
		
		this.root   = new BorderPane();
		this.scene  = new Scene(root, GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);
		this.canvas = new Canvas(GameStage.WINDOW_WIDTH, GameStage.WINDOW_HEIGHT);
		this.gc     = canvas.getGraphicsContext2D();
		
		this.gameStage = gameStage;
	}

	@Override
	protected void updateGUI() {
		// TODO Auto-generated method stub

	}
	
	@Override //Write all logic for the scene here
	public void update(GraphicsContext gc) { 		
		
		this.onStartOfFrame();
		this.updateObjects();	
		this.updateGUI();
		root.requestFocus();
		
	}
	
	@Override
	public void initializeProperties() {
		// TODO Auto-generated method stub
		
		this.setGUIProperties();

	}

	@Override
	protected void setObjectProperties() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void setGUIProperties() {
		
		this.title = new Label("Instruction screen");
		// TODO: Instruction goes here
		
		this.backButton = new MenuButton(gameStage, Assets.BACK_SELECTED,  Assets.BACK_UNSELECTED,  new SplashScreen(gameStage));
		
		this.instructionBox = new VBox();

		this.instructionBox.getChildren().add(this.title);
		for (Node component: this.buildInstruction()) 
			this.instructionBox.getChildren().add(component);
		this.instructionBox.getChildren().add(this.backButton);
		
		this.instructionBox.setAlignment(Pos.CENTER);
		this.root.setCenter(this.instructionBox);
		
	}
	
	private ArrayList<Node> buildInstruction() {
		
		ArrayList<Node> components = new ArrayList<Node>();
		
		// The DEVELOPERS
		Label step1 = new Label("1. step 1");
		Label step2 = new Label("2. step 2");
		components.add(step1);
		components.add(step2);
		
		return components;
		
	}

	@Override
	protected void setAudioProperties() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onExit() {
		// TODO Auto-generated method stub

	}

}
