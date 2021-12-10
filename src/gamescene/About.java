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

public class About extends GameScene {
	
	private BorderPane root;
	private VBox aboutBox;
	private Label title;
	private MenuButton backButton;
	
	About(GameStage gameStage) {
		
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
		
		this.title = new Label("About screen");
		this.backButton = new MenuButton(gameStage, Assets.BACK_PRESSED, Assets.BACK_SELECTED,  Assets.BACK_UNSELECTED,  new SplashScreen(gameStage));
		
		this.aboutBox = new VBox();

		this.aboutBox.getChildren().add(this.title);
		for(Node component: this.buildAbout()) {
			this.aboutBox.getChildren().add(component);
		}
		this.aboutBox.getChildren().add(this.backButton);
		
		this.aboutBox.setAlignment(Pos.CENTER);
		this.root.setCenter(this.aboutBox);
		
	}
	
	private ArrayList<Node> buildAbout() {
		
		ArrayList<Node> components = new ArrayList<Node>();
		
		// The DEVELOPERS
		Label developer1 = new Label("Dave Jimenez");
		Label developer2 = new Label("Von Divino");
		HBox developers = new HBox();
		developers.setAlignment(Pos.CENTER);
		developers.getChildren().addAll(developer1, developer2);
		components.add(developers);
		
		// The REFERENCES, API USED
		Label guiRef = new Label("JavaFX: https://openjfx.io");
		Label jdbcRef = new Label("JDBC: https://www.oracle.com/java/technologies/javase/javase-tech-database.html");
		HBox references = new HBox();
		references.getChildren().addAll(guiRef, jdbcRef);
		references.setAlignment(Pos.CENTER);
		components.add(references);
		
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
