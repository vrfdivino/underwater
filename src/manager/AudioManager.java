package manager;

import java.util.ArrayList;
import java.util.HashMap;

import component.AudioPlayer;

/**
 * Handles playing audio files.
 * 
 * @author Dave Jimenez
 */

public class AudioManager {
	
	/////////////////// PROPERTIES ///////////////////
	
	private static AudioManager instance;
	protected double volume = 1.0;
	protected HashMap<String, AudioPlayer> audioplayer_list = new HashMap<String, AudioPlayer>();
	
	protected AudioManager() {}
	
	public static AudioManager getInstance() {
		if (instance == null) {
			instance = new AudioManager();
		}
		return instance;
	}
	
	/**
	 * Adds an audio player.
	 * 
	 * @param The name of the audio player.
	 * @param audioPlayer The audio player.
	 * @author Dave Jimenez
	 */
	
	public void addAudioPlayer(String name, AudioPlayer audioPlayer) {
		if (!audioplayer_list.containsKey(name)) {
			audioplayer_list.put(name, audioPlayer);
			audioplayer_list.get(name).setVolume(volume);
		}
	}
	
	/**
	 * Remove the audio player.
	 * 
	 * @param name The name of the audio player.
	 * @author Dave Jimenez
	 */
	
	public void removeAudioPlayer(String name) {
		audioplayer_list.remove(name);
	}
	
	/**
	 * Play the audio player.
	 * 
	 * @param name The name of the audio player.
	 * @author Dave Jimenez
	 */
	
	public void playAudioPlayer(String name) {
		audioplayer_list.get(name).play();
	}
	
	/**
	 * Stop the audio player.
	 * 
	 * @param name The name of the audio player.
	 * @author Dave Jimenez
	 */
	
	public void stopAudioPlayer(String name) {
		audioplayer_list.get(name).stop();
	}
	
	/**
	 * Pause all audio players.
	 * 
	 * @author Dave Jimenez
	 */
	
	public void pauseAll() {
		for (AudioPlayer audioplayer: audioplayer_list.values()) {
			audioplayer.pause();
		}
	}
	
	/**
	 * Stop all audio players.
	 * 
	 * @author Dave Jimenez
	 */
	
	public void stopAll() {
		for (AudioPlayer audioplayer: audioplayer_list.values()) {
			audioplayer.stop();
		}
	}
	
	/**
	 * Restart the audio player.
	 * 
	 * @param name The name of the audio player.
	 * @author Dave Jimenez
	 */
	
	public void restartAudioPlayer(String name) {
		this.audioplayer_list.get(name).restart();
	}
	
	/////////////////// GETTERS & SETTERS ///////////////////
	
	public double getVolume() {return volume;}
	public double getVolumeOfAudioPlayer(String name) {return audioplayer_list.get(name).getVolume();}
	public ArrayList<String> getPlayingAudioPlayerNames() {
		ArrayList<String> playing_audioplayer_names = new ArrayList<String>();
		for (String audioplayer_name: audioplayer_list.keySet()) {
			if (audioplayer_list.get(audioplayer_name).isPlaying()) {
				playing_audioplayer_names.add(audioplayer_name);
			}
		}
		return playing_audioplayer_names;
	}
	public void setLoopOfAudioPlayer(String name, boolean canLoop) {
		audioplayer_list.get(name).setLoop(canLoop);
	}
	public void setVolume(double volume) {
		this.volume = volume;
		for (AudioPlayer audioplayer: audioplayer_list.values()) {
			audioplayer.setVolume(volume);
		}
	}
}
