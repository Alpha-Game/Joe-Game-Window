package joe.game.manager;

import java.awt.Graphics2D;

import joe.classes.geometry2D.Rectangle2D;
import joe.classes.language.IMessages;
import joe.game.layout.image.sprite.ISprite;
import joe.game.layout.implementation.LayoutCalculator;
import joe.game.layout.implementation.image.sprite.Sprites;
import joe.game.window.GameWindow;
import joe.game.window.state.GameWindowState;
import joe.input.ControllerAction;
import joe.input.IODeviceList;

public class GameManager implements IGameManager {	
	protected GameWindow fGame;
	protected IManager fCurrentManager;
	
	public GameManager(GameWindow game) {
		fGame = game;
	}
	
	@Override
	public IODeviceList getIOReader() {
		return fGame.getIODevices();
	}
	
	@Override
	public GameWindowState getGameState() {
		return fGame.getState();
	}
	
	@Override
	public IMessages getLanguage() {
		return fGame.getState().getLanguage();
	}
	
	@Override
	public void setManager(IManager manager) {
		manager.setPreviousManager(fCurrentManager);
		fCurrentManager = manager;
	}
	
	@Override
	public void update() {
		if (fCurrentManager != null) {
			fCurrentManager.update();
		}
	}
	
	@Override
	public void draw(Graphics2D g) {
		if (fCurrentManager != null) {
			fCurrentManager.draw(g);
			
			g.drawString("CPS: " + (int)fGame.getStatistics().getInstanteousCPS(), 10, 30);
			g.drawString("FPS: " + (int)fGame.getStatistics().getInstanteousFPS(), 10, 60);
		}
	}
	
	public Sprites getSprites() {
		return getGameState().getSprites();
	}
	
	public ISprite getSprite(String sprite) {
		return getGameState().getSprites().getSprite(sprite).getSprite();
	}
	
	public Rectangle2D getGameRectangle() {
		return LayoutCalculator.createRectangle(0, 0, getGameState().getGameWidth(), getGameState().getGameHeight());
	}
	
	public ControllerAction getController(String deviceID, String componentID) {
		if (deviceID == null) {
			if (componentID != null) {
				return new ControllerAction(getIOReader().getControllerMap().getControllerForComponentOnAllDevices(componentID));
			} else {
				return new ControllerAction(getIOReader().getControllerMap().getControllerForAllComponentsOnAllDevices());
			}
		} else {
			if (componentID != null) {
				return new ControllerAction(getIOReader().getControllerMap().getControllerForComponentOnDevice(deviceID, componentID));
			} else {
				return new ControllerAction(getIOReader().getControllerMap().getControllerForAllComponentsOnDevice(deviceID));
			}
		}
	}
}
