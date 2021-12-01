package runnableobject;

import javafx.scene.canvas.GraphicsContext;

public interface RunnableObject {
	/**
	 * This will contain all the behaviors of a RunnableObject. This will vary per RunnableObject.
	 * @author Dave
	 * @param gc The GraphicsContext of the current Scene
	 */
	
	public void update(GraphicsContext gc);	//Main Logic Loop for each runnable object
}
