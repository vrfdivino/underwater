package gamescene;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import main.GameStage;
import parentclass.GameScene;

public class MainMenu extends GameScene{
	private StackPane pane;
	private Canvas canvas;
	
	public MainMenu(){
		pane = new StackPane();
		scene = new Scene(pane, GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);
		canvas = new Canvas(GameStage.WINDOW_WIDTH, GameStage.WINDOW_HEIGHT);
		gc = canvas.getGraphicsContext2D();
	}
	
	@Override
	public void initializeProperties() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected void setObjectProperties() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void setGUIProperties() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void setAudioProperties() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void update(GraphicsContext gc) {					//Write all logic for this scene here
		
	}

	@Override
	public void onExit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void updateGUI() {
		// TODO Auto-generated method stub
		
	}




}
