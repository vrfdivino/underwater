package gamescene;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

import constants.Assets;
import gui.MenuButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Shape;
import main.GameStage;
import parentclass.GameScene;

public class About extends GameScene {
	
	private BorderPane root;
	private VBox aboutBox;
	private MenuButton backButton;
	private ImageView logo;
	private ImageView title;
	
	About(GameStage gameStage) {
		
		this.root   = new BorderPane();
		this.scene  = new Scene(root, GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);
		this.canvas = new Canvas(GameStage.WINDOW_WIDTH, GameStage.WINDOW_HEIGHT);
		this.gc     = canvas.getGraphicsContext2D();
		this.logo = new ImageView(Assets.LOGO);
		this.title = new ImageView(Assets.ABOUT_US_TITLE);
		
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
		
		this.backButton = new MenuButton(gameStage, Assets.BACK_SELECTED,  Assets.BACK_UNSELECTED,  new SplashScreen(gameStage));
		
		this.aboutBox = new VBox();
		
		this.logo.setFitHeight(100d);
		this.logo.setPreserveRatio(true);
		this.aboutBox.getChildren().add(this.logo);
		
		this.title.setFitHeight(80d);
		this.title.setPreserveRatio(true);
		this.aboutBox.getChildren().add(this.title);
		
//		this.aboutBox.getChildren().add(this.title);
//		for(Node component: this.buildAbout()) {
//			this.aboutBox.getChildren().add(component);
//		}

		

		ImageView content = new ImageView(Assets.ABOUT_US_CONTENT);
		content.setFitHeight(280d);
		content.setPreserveRatio(true);
		this.aboutBox.getChildren().add(content);
		
		this.aboutBox.getChildren().add(this.backButton);
		
		this.aboutBox.setAlignment(Pos.CENTER);
		this.aboutBox.setSpacing(50d);
		this.root.setCenter(this.aboutBox);
		this.root.setStyle("-fx-background-image: url('" + Assets.BG + "');-fx-background-size: 1000, 1000;-fx-background-repeat: no-repeat;");
		
	}
	
	private ArrayList<Node> buildAbout() {
		
		ArrayList<Node> components = new ArrayList<Node>();
		
		// The DEVELOPERS
		Label developer1 = new Label("Dave Jimenez");
		Label developer2 = new Label("Vonzzzzz Divino");
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
