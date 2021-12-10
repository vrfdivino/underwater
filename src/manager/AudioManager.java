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
		if (!audioPlayerList.containsKey(name)) {
			audioPlayerList.put(name, audioPlayer);
			audioPlayerList.get(name).setVolume(volume);
		}
	}
	
	public void removeAudioPlayer(String name) {
		audioPlayerList.remove(name);
	}
	
	public void playAudioPlayer(String name) {
		audioPlayerList.get(name).play();
	}
	
	public void stopAudioPlayer(String name) {
		audioPlayerList.get(name).stop();
	}
	
	public void pauseAll() {
		for (AudioPlayer audioPlayer: audioPlayerList.values()) {
			audioPlayer.pause();
		}
	}
	
	public void stopAll() {
		for (AudioPlayer audioPlayer: audioPlayerList.values()) {
			audioPlayer.stop();
		}
	}
	
	public void restartAudioPlayer(String name) {
		this.audioPlayerList.get(name).restart();
	}
	
	public void setVolume(double volume) {
		this.volume = volume;
		for (AudioPlayer audioPlayer: audioPlayerList.values()) {
			audioPlayer.setVolume(volume);
		}
	}
	
	public double getVolume() {
		return volume;
	}
	
	public double getVolumeOfAudioPlayer(String name) {
		return audioPlayerList.get(name).getVolume();
	}
	
	public void setLoopOfAudioPlayer(String name, boolean canLoop) {
		audioPlayerList.get(name).setLoop(canLoop);
	}
	
	public ArrayList<String> getPlayingAudioPlayerNames() {
		ArrayList<String> playingAudioPlayerNames = new ArrayList<String>();
		for (String audioPlayerName: audioPlayerList.keySet()) {
			if (audioPlayerList.get(audioPlayerName).isPlaying()) {
				playingAudioPlayerNames.add(audioPlayerName);
			}
		}
		
		return playingAudioPlayerNames;
	}
}
