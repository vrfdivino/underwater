package runnableobject;

import javafx.scene.canvas.GraphicsContext;
import main.GameStage;

public interface RunnableObject {
	public void update(GraphicsContext gc);	//Main Logic Loop for each runnable object
}
