package joe.game.manager;

public interface IManager {
	IGameManager getManager();
	void setPreviousManager(IManager previousManager);
	IManager getPreviousManager();
	void update();
	void draw(java.awt.Graphics2D g);	
}
