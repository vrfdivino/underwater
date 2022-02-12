package constants;

import component.AnimatedSprite;
import datatype.Vector2;
import javafx.scene.image.Image;
import main.GameStage;

public class Layout {
	
	public static AnimatedSprite STATIC_BACKGROUND =  new AnimatedSprite(new Image[] {new Image(Assets.BACKGROUND_001)}, 1, new Vector2(GameStage.WINDOW_WIDTH/2, GameStage.WINDOW_HEIGHT/2), new Vector2(1024, 1024));
	public static AnimatedSprite STATIC_TITLE = new AnimatedSprite(new Image[] {new Image(Assets.TITLECARD)}, 1, new Vector2(GameStage.WINDOW_WIDTH/2, GameStage.WINDOW_HEIGHT/4 - 150), new Vector2(360, 160));
}