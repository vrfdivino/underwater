package component;

import java.io.File;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

/**
 * Handles a single audio file
 * @author Dave
 *
 */
public class AudioPlayer {
	
	private Media media;
	private MediaPlayer mediaPlayer;

	private boolean isPlaying = false;
	
	/**
	 * Creates a new AudioPlayer. Doesn't loop by default.
	 * @param file The path to the media file
	 * @author Dave
	 */
	public AudioPlayer(String file){
		setMedia(file);
		setLoop(false);
	}
	/**
	 * Creates a new AudioPlayer. Doesn't loop by default.
	 * @param file The path to the media file
	 * @param canLoop Sets if the audio should loop. 
	 */
	public AudioPlayer(String file, boolean canLoop){
		setMedia(file);
		setLoop(canLoop);
	}
	
	/**
	 * Sets a new audio file to the current AudioPlayer
	 * @param file The path to the media file
	 */
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
	
	//Setters
	public void setVolume(double volume) {
		mediaPlayer.setVolume(volume);
	}
	
	//Getters
	public double getVolume() {
		return mediaPlayer.getVolume();
	}
	
	public boolean isPlaying() {
		return isPlaying;
	}
}
