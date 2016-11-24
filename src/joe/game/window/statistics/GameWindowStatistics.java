package joe.game.window.statistics;

import java.util.LinkedList;

import joe.classes.constants.GlobalConstants;

public class GameWindowStatistics {
	private static final int PREVIOUS_COMPUTE_TIME_MAX_INDEX = 60;
	private static final int PREVIOUS_FRAME_TIME_MAX_INDEX = 60; 
	
	private LinkedList<Long> fComputeTimes;
	private LinkedList<Long> fFrameTimes;
	
	private Long fTotalComputeTimes;
	private Long fTotalFrameTimes;
	
	public GameWindowStatistics() {
		fComputeTimes = new LinkedList<Long>();
		fFrameTimes = new LinkedList<Long>();
		
		fTotalComputeTimes = (long) 0;
		fTotalFrameTimes = (long) 0;
	}
	
	public void nextComputeTime(Long time) {
		fTotalComputeTimes += time;
		fComputeTimes.addLast(time);
		if (fComputeTimes.size() > PREVIOUS_COMPUTE_TIME_MAX_INDEX) {
			fTotalComputeTimes -= fComputeTimes.removeFirst();
		}
	}
	
	public void nextFrameTimes(Long time) {
		fTotalFrameTimes += time;
		fFrameTimes.addLast(time);
		if (fFrameTimes.size() > PREVIOUS_FRAME_TIME_MAX_INDEX) {
			fTotalFrameTimes -= fFrameTimes.removeFirst();
		}
	}
	
	public float getInstanteousCPS() {
		if (fComputeTimes.size() < 1) {
			return (float) 0.0;
		}
		return (GlobalConstants.NANOSECONDS / fComputeTimes.getLast());
	}
	
	public float getAverageCPS() {
		if (fComputeTimes.size() < 1) {
			return (float) 0.0;
		}
		return ((GlobalConstants.NANOSECONDS / fTotalComputeTimes) / fComputeTimes.size());
	}
	
	public float getInstanteousFPS() {
		if (fFrameTimes.size() < 1) {
			return (float) 0.0;
		}
		return (GlobalConstants.NANOSECONDS / fFrameTimes.getLast());
	}
	
	public float getAverageFPS() {
		if (fFrameTimes.size() < 1) {
			return (float) 0.0;
		}
		return ((GlobalConstants.NANOSECONDS / fTotalFrameTimes) / fFrameTimes.size());
	}
}
