package joe.game.manager;

public abstract class Manager implements IManager {
	
	private IGameManager fManager;
	private IManager fPreviousManager;
	
	protected Manager(IGameManager manager) {
		fManager = manager;
		fPreviousManager = null;
	}
	
	public IGameManager getManager() {
		return fManager;
	}
	
	public void setPreviousManager(IManager previousManager) {
		fPreviousManager = previousManager;
	}
	
	public IManager getPreviousManager() {
		return fPreviousManager;
	}
	
	public abstract void update();
	public abstract void draw(java.awt.Graphics2D g);	
}
