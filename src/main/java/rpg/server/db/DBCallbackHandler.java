package rpg.server.db;

import rpg.server.util.Params;

@FunctionalInterface
public interface DBCallbackHandler {
	void handleResult(Params p);
}
