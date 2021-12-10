package gui;

import datatype.Vector2;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import main.GameStage;
import parentclass.GameScene;

public class MenuButton extends Button{
	private final int HEIGHT = 56;
	private final int WIDTH = 360;
	
	private boolean isClicked = false;
	
	private GameStage gameStage;
	private GameScene gameSceneToLoad;
	private String buttonHoverStyle;
	private String buttonPressedStyle;
	private String buttonReleasedStyle;
	
	public MenuButton(GameStage gameStage, String hoverPath, String pressedPath, String releasedPath, GameScene gameScene) {
		buttonHoverStyle = "-fx-background-color: transparent; -fx-background-image: url('"+ hoverPath +"');";
		buttonPressedStyle = "-fx-background-color: transparent; -fx-background-image: url('"+ pressedPath +"');";
		buttonReleasedStyle = "-fx-background-color: transparent; -fx-background-image: url('"+ releasedPath +"');";
		
		setPrefWidth(WIDTH);
		setPrefHeight(HEIGHT);
		setButtonReleasedStyle();
		initializeButtonListeners();
		this.gameStage = gameStage;
		this.gameSceneToLoad = gameScene;
	}
	
	private void setButtonPressedStyle() {
		setStyle(buttonPressedStyle);
		setPrefHeight(HEIGHT);
	}
	
	private void setButtonHoverStyle() {
		setStyle(buttonHoverStyle);
		setPrefHeight(HEIGHT);
	}
	
	private void setButtonReleasedStyle() {
		setStyle(buttonReleasedStyle);
		setPrefHeight(HEIGHT);
	}
	
	public void setLayout(double x, double y) {
		setLayoutX(x);
		setLayoutY(y);
	}
	
	public void setLayout(Vector2 position) {
		setLayoutX(position.x);
		setLayoutY(position.y);
	}
	
	public boolean isClicked() {
		return isClicked;
	}
	
	private void setToClicked() {
		isClicked = true;
	}
	
	private void initializeButtonListeners() {
		setOnMousePressed(
			(MouseEvent event) -> {
				if (event.getButton().equals(MouseButton.PRIMARY)) {
					setButtonPressedStyle();	
				}
			}
		);
		
		setOnMouseReleased(
				(MouseEvent event) -> {
				if (event.getButton().equals(MouseButton.PRIMARY)) {
					setButtonReleasedStyle();
					setToClicked();
					gameStage.setGameScene(gameSceneToLoad);
				}
			}
		);
		
		setOnMouseEntered(
				(MouseEvent event) -> {
				setButtonHoverStyle();
			}
		);
		
		setOnMouseExited(
				(MouseEvent event) -> {
				setButtonReleasedStyle();
			}
		);
	}
}
