package gui;

import datatype.Vector2;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import main.GameStage;
import parentclass.GameScene;

public class MenuButton extends Button{
	private final int HEIGHT = 56;
	private final int WIDTH = 360;
	
	private boolean is_clicked = false;
	
	private GameStage game_stage;
	private GameScene game_scene_to_load;
	private String button_hover_style;
	private String button_pressed_style;
	private String button_released_style;
	
	public MenuButton(GameStage game_stage, String hover_path, String pressed_path, String released_path, GameScene game_scene) {
		button_hover_style = "-fx-background-color: transparent; -fx-background-image: url('"+ hover_path +"');";
		button_pressed_style = "-fx-background-color: transparent; -fx-background-image: url('"+ pressed_path +"');";
		button_released_style = "-fx-background-color: transparent; -fx-background-image: url('"+ released_path +"');";
		
		setPrefWidth(WIDTH);
		setPrefHeight(HEIGHT);
		setButtonReleasedStyle();
		initializeButtonListeners();
		this.game_stage = game_stage;
		this.game_scene_to_load = game_scene;
	}
	
	private void setButtonPressedStyle() {
		setStyle(button_pressed_style);
		setPrefHeight(HEIGHT);
	}
	
	private void setButtonHoverStyle() {
		setStyle(button_hover_style);
		setPrefHeight(HEIGHT);
	}
	
	private void setButtonReleasedStyle() {
		setStyle(button_released_style);
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
		return is_clicked;
	}
	
	private void setToClicked() {
		is_clicked = true;
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
					game_stage.setGameScene(game_scene_to_load);
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
