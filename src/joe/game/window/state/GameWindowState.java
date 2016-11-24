package joe.game.window.state;

import java.util.Locale;

import joe.classes.constants.GlobalConstants;
import joe.classes.geometry2D.Dimension2D;
import joe.classes.geometry2D.Point2D;
import joe.classes.language.IMessages;
import joe.game.layout.implementation.LayoutCalculator;
import joe.game.layout.implementation.image.sprite.Sprites;

public class GameWindowState {	
	private IMessages fLanguage; // Language of the game
	private Dimension2D fWindowDimension; // Size of the window
	private Dimension2D fGameDimension; // Size of the game
	private Point2D fGameCoordinates; // Where the game is located on the screen
	private Double fScale; // How big or small to make the window vs the actual game size
	private Double fFrameRate; // Rate at which the game outputs frames in MilliSeconds
	private Double fComputeRate; // Rate at which the game compute in MilliSeconds
	private Sprites fSprites; // Sprites in the game
	
	public Sprites getSprites() {
		if (fSprites == null) {
			fSprites = new Sprites();
		}		
		return fSprites;
	}
	
	protected int getDefaultWidth() {
		return 320;
	}
	
	protected int getDefaultHeight() {
		return 240;
	}
		
	protected double getDefaultCPS() {
		return 240;
	}
	
	protected double getDefaultFPS() {
		return 60;
	}
	
	private void updateScale() {
		if (fWindowDimension == null) {
			fWindowDimension = LayoutCalculator.createDimension(0, 0);
		}
		if (fGameDimension == null) {
			fGameDimension = LayoutCalculator.createDimension(getDefaultWidth(), getDefaultHeight());
		}
		
		double wScale = fWindowDimension.getWidth() / fGameDimension.getWidth();
		double hScale = fWindowDimension.getHeight() / fGameDimension.getHeight();
		fScale = wScale < hScale ? wScale : hScale;
		
		int x = ((int)(fWindowDimension.getWidth() - (fGameDimension.getWidth() * fScale))) / 2;
		int y = ((int)(fWindowDimension.getHeight() - (fGameDimension.getHeight() * fScale))) / 2;
		if (fGameCoordinates == null) {
			fGameCoordinates = LayoutCalculator.createPoint(x, y);
		} else {
			fGameCoordinates = LayoutCalculator.createPoint(x, y);
		}
	}
	
	public void setWindowDimensions(Dimension2D dimension) {
		if (fWindowDimension == null) {
			fWindowDimension = LayoutCalculator.createDimension(dimension.getWidth(), dimension.getHeight());
		} else {
			fWindowDimension = LayoutCalculator.createDimension(dimension.getWidth(), dimension.getHeight());
		}
		updateScale();
	}
	
	public void setGameDimensions(Dimension2D dimension) {
		if (fGameDimension == null) {
			fGameDimension = LayoutCalculator.createDimension(dimension.getWidth(), dimension.getHeight());
		} else {
			fGameDimension = LayoutCalculator.createDimension(dimension.getWidth(), dimension.getHeight());
		}
		updateScale();
	}
	
	public int getWindowWidth() {
		if (fWindowDimension == null) {
			return 0;
		}
		return (int)fWindowDimension.getWidth();
	}
	
	public int getGameWidth() {
		if (fGameDimension == null) {
			return getDefaultWidth();
		}
		return (int)fGameDimension.getWidth();
	}
	
	public int getScaledWidth() {
		return (int)(getGameWidth() * getGameXScale());
	}
	
	public int getGameX() {
		if (fGameCoordinates == null) {
			return 0;
		}
		return (int)fGameCoordinates.getX();
	}
	
	public int getWindowHeight() {
		if (fWindowDimension == null) {
			return 0;
		}
		return (int)fWindowDimension.getHeight();
	}
	
	public int getGameHeight() {
		if (fGameDimension == null) {
			return getDefaultHeight();
		}
		return (int)fGameDimension.getHeight();
	}
	
	public int getScaledHeight() {
		return (int)(getGameHeight() * getGameYScale());
	}
	
	public int getGameY() {
		if (fGameCoordinates == null) {
			return 0;
		}
		return (int)fGameCoordinates.getY();
	}
	
	public double getGameYScale() {
		if (fScale == null) {
			return 1;
		}
		return fScale;
	}
	
	public double getGameXScale() {
		if (fScale == null) {
			return 1;
		}
		return fScale;
	}
	
	public double getFPSCap() {
		if (fFrameRate == null) {
			fFrameRate = getDefaultFPS();
		}
		return fFrameRate;
	}
	
	public void setFPSCap(double fps) {
		fFrameRate = fps;
	}
	
	/**
	 * Gets the rate at which frame cycles should be displayed (Nanoseconds)
	 */
	public long getFrameTimeMinimum() {
		return (long)(GlobalConstants.NANOSECONDS / getFPSCap());
	}
	
	public double getCPSCap() {
		if (fComputeRate == null) {
			fComputeRate = getDefaultCPS();
		}
		return fComputeRate;
	}
	
	/**
	 * Gets the rate at which computational cycles should be computed (Nanoseconds)
	 */
	public long getComputeTimeMinimum() {
		return (long)(GlobalConstants.NANOSECONDS / getCPSCap());
	}
	
	public void setCPSCap(double cps) {
		fComputeRate = cps;
	}
	
	public IMessages getLanguage() {
		if (fLanguage == null) {
			fLanguage = getLanguage(Locale.getDefault());
		}		
		return fLanguage;
	}
	
	public IMessages getLanguage(Locale language) {
		// Should be overwritten by
		return null;
	}
		
	public void setLanguage(Locale language) {
		fLanguage = getLanguage(language);
	}
}
