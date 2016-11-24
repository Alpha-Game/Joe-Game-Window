package joe.game.window;

import joe.classes.base.ILoopable;
import joe.classes.base.Loopable;

class GameCalulationLoop implements ILoopable {
	private Loopable fLoopable;
	private GameWindow fWindow;
	private long fNextCalculationUpdate;
	
	public GameCalulationLoop(GameWindow window) {
		fWindow = window;
	}

	@Override
	public void initialize() {
		fNextCalculationUpdate = System.nanoTime() - 1;
	}

	@Override
	public long run() {
		if (fNextCalculationUpdate < System.nanoTime()) {
			fNextCalculationUpdate = fWindow.update();
		}	
		
		// Wait for designated period of time
		long waitTime = fNextCalculationUpdate - System.nanoTime();
		if (waitTime < 0) {
			return 0;
		}
		return waitTime;
	}

	@Override
	public void destroy() {
	}
	
	public void start() {
		if (fLoopable == null) {
			fLoopable = new Loopable(this);
		}
		fLoopable.start();
	}
	
	public void stop() {
		if (fLoopable != null) {
			fLoopable.stop();
		}
	}
	
	public boolean isRunning() {
		return fLoopable != null && fLoopable.isRunning();
	}

	@Override
	public String name() {
		return "Calculation Loop";
	}
}
