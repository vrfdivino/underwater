package manager;

import java.util.ArrayList;
import java.util.HashMap;

import component.AudioPlayer;

/**
 * Singleton Class.
 * Handles playing audio files.
 * @author Dave
 *
 */
public class AudioManager {
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
	
	public void addAudioPlayer(String name, AudioPlayer audioPlayer) {
		if (!audioplayer_list.containsKey(name)) {
			audioplayer_list.put(name, audioPlayer);
			audioplayer_list.get(name).setVolume(volume);
		}
	}
	
	public void removeAudioPlayer(String name) {
		audioplayer_list.remove(name);
	}
	
	public void playAudioPlayer(String name) {
		audioplayer_list.get(name).play();
	}
	
	public void stopAudioPlayer(String name) {
		audioplayer_list.get(name).stop();
	}
	
	public void pauseAll() {
		for (AudioPlayer audioplayer: audioplayer_list.values()) {
			audioplayer.pause();
		}
	}
	
	public void stopAll() {
		for (AudioPlayer audioplayer: audioplayer_list.values()) {
			audioplayer.stop();
		}
	}
	
	public void restartAudioPlayer(String name) {
		this.audioplayer_list.get(name).restart();
	}
	
	public void setVolume(double volume) {
		this.volume = volume;
		for (AudioPlayer audioplayer: audioplayer_list.values()) {
			audioplayer.setVolume(volume);
		}
	}
	
	public double getVolume() {
		return volume;
	}
	
	public double getVolumeOfAudioPlayer(String name) {
		return audioplayer_list.get(name).getVolume();
	}
	
	public void setLoopOfAudioPlayer(String name, boolean canLoop) {
		audioplayer_list.get(name).setLoop(canLoop);
	}
	
	public ArrayList<String> getplaying_audioplayer_names() {
		ArrayList<String> playing_audioplayer_names = new ArrayList<String>();
		for (String audioplayer_name: audioplayer_list.keySet()) {
			if (audioplayer_list.get(audioplayer_name).isPlaying()) {
				playing_audioplayer_names.add(audioplayer_name);
			}
		}
		
		return playing_audioplayer_names;
	}
}
