package component;

import java.io.File;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class AudioPlayer {
	
	private Media media;
	private MediaPlayer mediaPlayer;

	private boolean isPlaying = false;
	
	public AudioPlayer(String file){
		setMedia(file);
		setLoop(false);
	}
	
	public AudioPlayer(String file, boolean canLoop){
		setMedia(file);
		setLoop(canLoop);
	}
	
	public void setMedia(String file) {
		media = new Media(new File(file).toURI().toString());
		mediaPlayer = new MediaPlayer(media);
	}
	
	public void play() {
		mediaPlayer.play();
		isPlaying = true;
	}
	
	public void stop() {
		mediaPlayer.stop();
		isPlaying = false;
	}
	
	public void pause() {
		mediaPlayer.pause();
		isPlaying = false;
	}
	
	public void restart() {
		mediaPlayer.seek(Duration.ZERO);
		isPlaying = true;
	}
	
	public double getVolume() {
		return mediaPlayer.getVolume();
	}
	
	public void setVolume(double volume) {
		mediaPlayer.setVolume(volume);
	}
	public boolean isPlaying() {
		return isPlaying;
	}
	
	public void setLoop(boolean canLoop) {
		if (canLoop) {
			mediaPlayer.setOnEndOfMedia(
		       () -> {
		    	   restart();
		         }
			);
		}
		else {
			mediaPlayer.setOnEndOfMedia(
		       () -> {}
			);
		}
	}
}
