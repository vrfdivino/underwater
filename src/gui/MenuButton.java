package gui;

import datatype.Vector2;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import main.GameStage;
import parentclass.GameScene;

public class MenuButton extends Button{
	private final int HEIGHT = 40;
	private final int WIDTH = 328;
	
	private boolean isClicked = false;
	
	private GameStage gameStage;
	private GameScene gameScene;
	private String buttonPressedStyle;
	private String buttonFreeStyle;
	
	public MenuButton(GameStage gameStage, String pressedPath, String freePath, GameScene gameScene) {
		buttonPressedStyle = "-fx-background-color: transparent; -fx-background-image: url('"+ pressedPath +"');";
		buttonFreeStyle = "-fx-background-color: transparent; -fx-background-image: url('"+ freePath +"');";
		
		setPrefWidth(WIDTH);
		setPrefHeight(HEIGHT);
		setButtonFreeStyle();
		initializeButtonListeners();
		this.gameStage = gameStage;
		this.gameScene = gameScene;
	}
	
	private void setButtonPressedStyle() {
		setStyle(buttonPressedStyle);
		setPrefHeight(HEIGHT);
	}
	
	private void setButtonFreeStyle() {
		setStyle(buttonFreeStyle);
		setPrefHeight(HEIGHT);
	}
	
	private void setButtonReleasedStyle() {
		setStyle(buttonFreeStyle);
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
					setToClicked();
					gameStage.setGameScene(gameScene);	
				}
			}
		);
		
		setOnMouseReleased(
				(MouseEvent event) -> {
				if (event.getButton().equals(MouseButton.PRIMARY)) {
					setButtonReleasedStyle();
				}
			}
		);
		
		setOnMouseEntered(
				(MouseEvent event) -> {
				setButtonPressedStyle();
				setEffect(new DropShadow());
			}
		);
		
		setOnMouseExited(
				(MouseEvent event) -> {
				setEffect(null);
				setButtonReleasedStyle();
			}
		);
	}
}
