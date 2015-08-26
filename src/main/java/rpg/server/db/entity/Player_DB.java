package rpg.server.db.entity;

public class Player_DB {
	private long id;
	private String name;
	private int profession;
	private int level;
	private int gender;

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getProfession() {
		return profession;
	}

	public int getLevel() {
		return level;
	}

	public int getGender() {
		return gender;
	}

}
