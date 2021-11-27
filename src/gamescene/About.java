package gamescene;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import main.GameStage;
import parentclass.GameScene;

public class About extends GameScene{
	private StackPane pane;
	
	public About(GameStage gameStage){
		this.pane = new StackPane();
		this.scene = new Scene(pane, GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);
		this.canvas = new Canvas(GameStage.WINDOW_WIDTH, GameStage.WINDOW_HEIGHT);
		this.gc = canvas.getGraphicsContext2D();
		this.gameStage = gameStage;
	}
	
	@Override
	public void initializeProperties() {
		setAudioProperties();
		
	}
	
	private void setAudioProperties() {
		AUDIO_MANAGER.playAudioPlayer("Main Theme");
	}
		
	@Override
	public void update(GraphicsContext gc) {					//Write all logic for this scene here
		
	}

	@Override
	public void onExit() {
		// TODO Auto-generated method stub
		
	}


}
