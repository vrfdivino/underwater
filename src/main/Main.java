package main;

import javafx.application.Application;
import javafx.stage.Stage;
import services.GameServer;

public class Main extends Application{
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		GameStage gameStage = new GameStage();		
		
		// PLEASE DO NOT EDIT UNTIL PRODUCTION
		GameServer gameServer = new GameServer();
		gameServer.listen();
		
		gameStage.setStage(stage);
		gameStage.start();
		

	}
}
