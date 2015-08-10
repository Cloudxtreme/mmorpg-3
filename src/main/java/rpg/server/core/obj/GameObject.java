package rpg.server.core.obj;

public interface GameObject extends SimulateObject {
	GameObject getTarget();

	int getX();

	int getY();

	boolean isFriend(GameObject obj);
	

}
