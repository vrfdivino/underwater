package gamescene;

import component.AudioPlayer;
import constants.Path;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import main.GameStage;
import parentclass.GameScene;

public class Level_001 extends GameScene{
	private AnchorPane root;
	private BorderPane pane;
	
	private Label label;

	public Level_001(GameStage gameStage){
		
		this.root = new AnchorPane();
		this.pane = new BorderPane();
		this.scene = new Scene(root, GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);
		this.canvas = new Canvas(GameStage.WINDOW_WIDTH, GameStage.WINDOW_HEIGHT);
		this.gc = canvas.getGraphicsContext2D();
		this.gameStage = gameStage;
	}
	
	public void initializeProperties(){
		
		setObjectProperties();
		setGUIProperties();
		setAudioProperties();
	}
	
	protected void setObjectProperties() {

	}
	
	protected void setGUIProperties() {
		
		label = new Label("This is the Main Game Scene!");
		label.setAlignment(Pos.CENTER);
		
		pane.setRight(label);
		root.getChildren().add(this.pane);
		
	}
	
	protected void setAudioProperties() {
		
		AudioPlayer under_pressure = new AudioPlayer(Path.UNDER_PRESSURE, false);
		AudioPlayer underwater = new AudioPlayer(Path.UNDERWATER, true);
		
		AUDIO_MANAGER.addAudioPlayer("Under Pressure", under_pressure);
		SFX_MANAGER.addAudioPlayer("Underwater", underwater);
		
		AUDIO_MANAGER.playAudioPlayer("Under Pressure");
		SFX_MANAGER.playAudioPlayer("Underwater");
		
	}
	
	@Override
	public void onExit() {
		
	}
	
	@Override
	public void update(GraphicsContext gc) {					//Write all logic for this scene here
		onStartOfFrame();
	}

	@Override
	protected void updateGUI() {
		// TODO Auto-generated method stub
		
	}
}