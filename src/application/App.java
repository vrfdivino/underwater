package application;

import game.GameLoop;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
	
	

	public static void main(String[] args) {
		launch(args);

	}

	public void start(Stage stage) throws Exception {
		GameLoop gameLoop = new GameLoop();
		gameLoop.setStage(stage);
		gameLoop.startApp();
	}

}
