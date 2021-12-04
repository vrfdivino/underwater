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
import services.Data;
import services.Database;
import services.GameDB;
import services.PlayerData;

public class Scoreboard extends GameScene {
	
	private BorderPane root;
	private VBox scoreboard;
	private Label title;
	private MenuButton backButton;
	private GameDB gameDB;
	
	Scoreboard(GameStage gameStage) {
		
		this.root   = new BorderPane();
		this.scene  = new Scene(root, GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);
		this.canvas = new Canvas(GameStage.WINDOW_WIDTH, GameStage.WINDOW_HEIGHT);
		this.gc     = canvas.getGraphicsContext2D();
		
		this.gameStage = gameStage;
		
		this.gameDB = new GameDB(Database.DEV_DB);
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
		
		this.title = new Label("Scoreboard");
		this.backButton = new MenuButton(gameStage, Assets.BACK_SELECTED,  Assets.BACK_UNSELECTED,  new SplashScreen(gameStage));
		
		this.scoreboard = new VBox();

		this.scoreboard.getChildren().add(this.title);
		for(Node component: this.buildScoreboard()) {
			this.scoreboard.getChildren().add(component);
		}
		this.scoreboard.getChildren().add(this.backButton);
		
		this.scoreboard.setAlignment(Pos.CENTER);
		this.root.setCenter(this.scoreboard);
		
	}
	
	private ArrayList<Node> buildScoreboard() {
		
		ArrayList<Node> components = new ArrayList<Node>();
		
		// players
		if (this.gameDB.connecToDb()) {
			
			for (Data player: this.gameDB.retrieveAllData()) {
				PlayerData p = (PlayerData) player;
				components.add(new Label(p.getName() + " with a score of " + p.getScore()));
			}
			
			this.gameDB.closeDb();
		}
		
		
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
