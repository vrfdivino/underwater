package main;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application{
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		GameStage game_stage = new GameStage();		
		game_stage.setStage(stage);
		game_stage.start();
	}
}