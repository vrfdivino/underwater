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
	protected HashMap<String, AudioPlayer> audioPlayerList = new HashMap<String, AudioPlayer>();
	
	protected AudioManager() {}
	
	public static AudioManager getInstance() {
		if (instance == null) {
			instance = new AudioManager();
		}
		
		return instance;
	}
	
	public void addAudioPlayer(String name, AudioPlayer audioPlayer) {
		if (!this.audioPlayerList.containsKey(name)) {
			this.audioPlayerList.put(name, audioPlayer);
			this.audioPlayerList.get(name).setVolume(volume);
		}
	}
	
	public void removeAudioPlayer(String name) {
		this.audioPlayerList.remove(name);
	}
	
	public void playAudioPlayer(String name) {
		this.audioPlayerList.get(name).play();
	}
	
	public void stopAudioPlayer(String name) {
		this.audioPlayerList.get(name).stop();
	}
	
	public void pauseAll() {
		for (AudioPlayer audioPlayer: this.audioPlayerList.values()) {
			audioPlayer.pause();
		}
	}
	
	
	public void stopAll() {
		for (AudioPlayer audioPlayer: this.audioPlayerList.values()) {
			audioPlayer.stop();
		}
	}
	public void restartAudioPlayer(String name) {
		this.audioPlayerList.get(name).restart();
	}
	
	public void setVolume(double volume) {
		this.volume = volume;
		for (AudioPlayer audioPlayer: this.audioPlayerList.values()) {
			audioPlayer.setVolume(volume);
		}
	}
	
	public double getVolume() {
		return this.volume;
	}
	
	public double getVolumeOfAudioPlayer(String name) {
		return this.audioPlayerList.get(name).getVolume();
	}
	
	public void setLoopOfAudioPlayer(String name, boolean canLoop) {
		this.audioPlayerList.get(name).setLoop(canLoop);
	}
	
	public ArrayList<String> getPlayingAudioPlayerNames() {
		ArrayList<String> playingAudioPlayerNames = new ArrayList<String>();
		for (String audioPlayerName: this.audioPlayerList.keySet()) {
			if (this.audioPlayerList.get(audioPlayerName).isPlaying()) {
				playingAudioPlayerNames.add(audioPlayerName);
			}
		}
		
		return playingAudioPlayerNames;
	}
}
