package component;

import java.io.File;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

/**
 * Handles a single audio file.
 * 
 * @author Dave Jimenez
 *
 */

public class AudioPlayer {
	
	/////////////////// PROPERTIES ///////////////////
	
	private Media media;
	private MediaPlayer media_player;
	private boolean is_playing = false;
	
	/**
	 * Creates a new AudioPlayer. Doesn't loop by default.
	 * 
	 * @param file ( String ) The path to the media file.
	 * @author Dave Jimenez
	 */
	
	public AudioPlayer(String file){
		setMedia(file);
		setLoop(false);
	}
	
	/**
	 * Creates a new AudioPlayer. Doesn't loop by default.
	 * 
	 * @param file    ( String ) The path to the media file.
	 * @param canLoop ( boolean ) Sets if the audio should loop. 
	 */
	
	public AudioPlayer(String file, boolean canLoop){
		setMedia(file);
		setLoop(canLoop);
	}
	
	/**
	 * Sets a new audio file to the current AudioPlayer.
	 * 
	 * @param file ( String ) The path to the media file.
	 */
	
	public void setMedia(String file) {
		media = new Media(new File(file).toURI().toString());
		media_player = new MediaPlayer(media);
	}
	
	/**
	 * Plays the audio player.
	 * 
	 * @author Dave Jimenez
	 * 
	 */
	
	public void play() {
		media_player.play();
		is_playing = true;
	}
	
	/**
	 * Stops the audio player.
	 * 
	 * @author Dave Jimenez
	 */
	
	public void stop() {
		media_player.stop();
		is_playing = false;
	}
	
	/**
	 * Pause the audio player.
	 * 
	 * @author Dave Jimenez
	 */
	
	public void pause() {
		media_player.pause();
		is_playing = false;
	}
	
	/**
	 * Restarts the audio player.
	 * 
	 * @author Dave Jimenez
	 */
	
	public void restart() {
		media_player.seek(Duration.ZERO);
		is_playing = true;
	}
	
	/////////////////// GETTERS ///////////////////
	
	public double getVolume() {return media_player.getVolume();}
	public boolean isPlaying() {return is_playing;}
	
	/////////////////// SETTERS ///////////////////
	
	public void setVolume(double volume) {media_player.setVolume(volume);}
	public void setLoop(boolean canLoop) {
		if (canLoop)
			media_player.setOnEndOfMedia(() -> {
				restart();
			});
		else 
			media_player.setOnEndOfMedia(() -> {});
	}
}
