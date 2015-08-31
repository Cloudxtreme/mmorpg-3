package rpg.server.db.entity;

public class PlayerDB {
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

	public void setId(long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setProfession(int profession) {
		this.profession = profession;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

}
