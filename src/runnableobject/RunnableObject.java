package runnableobject;

import javafx.scene.canvas.GraphicsContext;

/**
 * All runnable objects should have an update method.
 * 
 * @author Dave Jimenez
 */

public interface RunnableObject {
	
	/**
	 * This will contain all the behaviors of a RunnableObject. This will vary per RunnableObject.
	 * 
	 * @param gc The GraphicsContext of the current Scene.
	 * @author Dave Jimenez
	 */
	
	public void update(GraphicsContext gc);	//Main Logic Loop for each runnable object
}
