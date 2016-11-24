package joe.game.window;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import joe.classes.base.ILoopable;
import joe.classes.base.Loopable;
import joe.classes.geometry2D.Dimension2D;

class GameDrawingLoop implements ILoopable {
	private Loopable fLoopable;
	private GameWindow fWindow;
	private int fGameWidth;
	private int fGameHeight;
	
	private long fNextDrawUpdate;
	private BufferedImage fImage;
	private Graphics2D fGraphics;
	
	public GameDrawingLoop(GameWindow window, int width, int height) {
		fWindow = window;
		fGameWidth = width;
		fGameHeight = height;
	}
	
	@Override
	public void initialize() {
		fImage = new BufferedImage(fGameWidth, fGameHeight, BufferedImage.TYPE_INT_ARGB);
		fGraphics = (Graphics2D) fImage.getGraphics();
	}
	
	@Override
	public long run() {
		if (fNextDrawUpdate < System.nanoTime()) {
			fNextDrawUpdate = fWindow.draw(fGraphics, fImage);
		}	
		
		// Wait for designated period of time
		long waitTime = fNextDrawUpdate - System.nanoTime();
		if (waitTime < 0) {
			return 0;
		}
		return waitTime;
	}
	
	public void updateDimensions(Dimension2D dimensions) {
		fGameWidth = (int) dimensions.getWidth();
		fGameHeight = (int) dimensions.getHeight();
		fImage = new BufferedImage(fGameWidth, fGameHeight, BufferedImage.TYPE_INT_ARGB);
		fGraphics = (Graphics2D) fImage.getGraphics();
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
		return "Drawing Loop";
	}
}
