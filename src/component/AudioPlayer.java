package component;

import java.io.File;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class AudioPlayer {
	
	private Media media;
	private MediaPlayer mediaPlayer;
	private Duration currentTime  = new Duration(0.0);
	private boolean isPlaying = false;
	
	public AudioPlayer(String file){
		this.setMedia(file);
		this.setLoop(false);
	}
	
	public AudioPlayer(String file, boolean canLoop){
		this.setMedia(file);
		this.setLoop(canLoop);
	}
	
	public void setMedia(String file) {
		this.media = new Media(new File(file).toURI().toString());
		this.mediaPlayer = new MediaPlayer(this.media);
	}
	
	public void play() {
		this.currentTime = Duration.ZERO;
		this.mediaPlayer.play();
		this.isPlaying = true;
	}
	
	public void stop() {
		this.currentTime = this.mediaPlayer.getCurrentTime();
		this.mediaPlayer.stop();
		this.isPlaying = false;
	}
	
	public void pause() {
		this.mediaPlayer.pause();
		this.isPlaying = false;
	}
	
	public void restart() {
		this.mediaPlayer.seek(Duration.ZERO);
		this.isPlaying = true;
	}
	
	public double getVolume() {
		return this.mediaPlayer.getVolume();
	}
	
	public void setVolume(double volume) {
		this.mediaPlayer.setVolume(volume);
	}
	public boolean isPlaying() {
		return this.isPlaying;
	}
	
	public void setLoop(boolean canLoop) {
		if (canLoop) {
			this.mediaPlayer.setOnEndOfMedia(new Runnable() {
		       public void run() {
		    	   restart();
		         }
			});
		}
		else {
			this.mediaPlayer.setOnEndOfMedia(new Runnable() {
		       public void run() {
		         }
			});
		}
	}
}
