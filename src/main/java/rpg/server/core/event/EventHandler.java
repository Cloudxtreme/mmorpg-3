package rpg.server.core.event;

@FunctionalInterface
public interface EventHandler {
	/**
	 * Handler the game event
	 * 
	 * @param event
	 */
	public void handleEvent(GameEvent event);
}
