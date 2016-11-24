package joe.game.manager;

import java.awt.Graphics2D;

import joe.classes.language.IMessages;
import joe.game.window.state.GameWindowState;
import joe.input.IODeviceList;

public interface IGameManager {
	IODeviceList getIOReader();
	
	GameWindowState getGameState();
	
	IMessages getLanguage();
	
	void setManager(IManager manager);
	
	void update();
	
	void draw(Graphics2D g);
}
