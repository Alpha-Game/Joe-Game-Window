package joe.game.window;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

import joe.classes.base.Timer;
import joe.classes.geometry2D.Dimension2D;
import joe.game.layout.implementation.LayoutCalculator;
import joe.game.manager.GameManager;
import joe.game.window.state.GameWindowState;
import joe.game.window.statistics.GameWindowStatistics;
import joe.input.IODeviceList;

public class GameWindow extends JPanel implements ComponentListener, WindowStateListener {
	private static final long serialVersionUID = -4210063677703964386L;
	
	protected GameWindowState fState;
	protected GameWindowStatistics fStatistics;
	protected GameManager fManager;
		
	private JFrame fWindow;
	private IODeviceList fIODevices;
	
	private Timer fLastCalculationUpdate;
	private Timer fLastDrawUpdate;
	
	private GameCalulationLoop fCalulationLoop;
	private GameDrawingLoop fDrawingLoop;
	
	public GameWindow(int windowWidth, int windowHeight, boolean startMaximized, boolean startMinimized, Color backgroundColor) {
		super();
		
		fState = createState();
		fStatistics = createStatistics();
		fIODevices = createDeviceList();
		fWindow = new JFrame();
		fManager = createManager();
		
		setPreferredSize(new Dimension(windowWidth, windowHeight));
		setFocusable(true);
		requestFocus();
		
		fWindow.addComponentListener(this);
		fWindow.addWindowStateListener(this);
		fWindow.setContentPane(this);
		fWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close when 'X' is clicked
		fWindow.setResizable(true); // Allow user to dynamically resize window
		
		fWindow.pack();
		fWindow.dispose();
		createWindow(startMaximized, startMinimized);
		setBackground(backgroundColor);
		
		fLastCalculationUpdate = new Timer();
		fLastDrawUpdate = new Timer();
	}
	
	protected GameWindowState createState() {
		return new GameWindowState();
	}
	
	protected GameWindowStatistics createStatistics() {
		return new GameWindowStatistics();
	}
	
	protected IODeviceList createDeviceList() {
		return new IODeviceList();
	}
	
	protected GameManager createManager() {
		return new GameManager(this);
	}
	
	protected void setTitle(String title) {
		fWindow.setTitle(title);
	}
	
	public GameWindowState getState() {
		return fState;
	}
	
	public GameWindowStatistics getStatistics() {
		return fStatistics;
	}
	
	public IODeviceList getIODevices() {
		return fIODevices;
	}
	
	public void start() {
		createCalculationLoop();
		createDrawingLoop(fState.getGameWidth(), fState.getGameHeight());
	}
	
	private void createCalculationLoop() {
		if (fCalulationLoop != null) {
			fCalulationLoop.stop();
		}
		fCalulationLoop = new GameCalulationLoop(this);
		fCalulationLoop.start();
	}
	
	private void createDrawingLoop(int width, int height) {
		if (fDrawingLoop != null) {
			fDrawingLoop.stop();
		}
		fDrawingLoop = new GameDrawingLoop(this, width, height);
		fDrawingLoop.start();
	}
	
	public void stop() {
		if (fCalulationLoop != null) {
			fCalulationLoop.stop();
		}
		if (fDrawingLoop != null) {
			fDrawingLoop.stop();
		}
	}
	
	public boolean isRunning() {
		return (fCalulationLoop != null && fCalulationLoop.isRunning()) || (fCalulationLoop != null && fCalulationLoop.isRunning());
	}
	
	protected long update() {
		long startTime = System.nanoTime();
		fManager.update();
		fStatistics.nextComputeTime(fLastCalculationUpdate.lap());
		return startTime + fState.getComputeTimeMinimum();
	}
	
	protected long draw(java.awt.Graphics2D graphic, BufferedImage image) {
		// Check for game dimension change
		if (image.getWidth() != fManager.getGameRectangle().getWidth() || image.getHeight() != fManager.getGameRectangle().getHeight()) {
			fDrawingLoop.updateDimensions(LayoutCalculator.createDimension(fManager.getGameRectangle().getWidth(), fManager.getGameRectangle().getHeight()));
			return 0;
		}
		
		// Draw next frame
		long startTime = System.nanoTime();
		fManager.draw(graphic);
		try {
			Graphics g2 = getGraphics();
			g2.drawImage(image, fState.getGameX(), fState.getGameY(), fState.getScaledWidth(), fState.getScaledHeight(), null);
			g2.dispose();
		} catch (NullPointerException e) {
			createDrawingLoop(fState.getGameWidth(), fState.getGameHeight());
		}
		fStatistics.nextFrameTimes(fLastDrawUpdate.lap());
		return startTime + fState.getFrameTimeMinimum();
	}

	@Override
	public void componentHidden(ComponentEvent e) {}

	@Override
	public void componentMoved(ComponentEvent e) {}

	@Override
	public void componentResized(ComponentEvent e) {
		fState.setWindowDimensions(new Dimension2D(getSize()));
	}

	@Override
	public void componentShown(ComponentEvent e) {}

	private void createWindow(boolean maximize, boolean minimize) {
		if (maximize) {
			fWindow.setUndecorated(true); // Remove the border of the window
			fWindow.setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximizes the window
		} else {
			fWindow.setUndecorated(false); // Restores the border of the window
			fWindow.setLocationRelativeTo(null); // Centers the window
		}
		
		if (minimize) {
			fWindow.setUndecorated(false); // Restores the border of the window
			fWindow.setExtendedState(JFrame.ICONIFIED); // Minimizes the window
		}
		
		fWindow.setVisible(true); // Make the window appear
		fState.setWindowDimensions(new Dimension2D(getSize()));
	}
	
	@Override
	public void windowStateChanged(WindowEvent e) {
		if ((e.getNewState() & JFrame.MAXIMIZED_BOTH) == JFrame.MAXIMIZED_BOTH){
			fWindow.dispose();
			createWindow(true, false);
			createDrawingLoop(fState.getGameWidth(), fState.getGameHeight());
		} else if ((e.getOldState() & JFrame.MAXIMIZED_BOTH) == JFrame.MAXIMIZED_BOTH) {
			fWindow.dispose();
			createWindow(false, false);
			createDrawingLoop(fState.getGameWidth(), fState.getGameHeight());
		}
	}
}
