package rpg.server.client;

public class Main {
	public static void main(String[] args) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					new Client().startup();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
}
