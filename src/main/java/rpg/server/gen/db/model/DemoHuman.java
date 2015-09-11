package rpg.server.gen.db.model;

public class DemoHuman {
	/**  */
	private Long id;
	/** 服务器编号 */
	private Integer serverId;
	/** 账号 */
	private String account;
	/** 姓名 */
	private String name;
	/** 职业(1战士,2刺客,3咒术师) */
	private Integer profession;
	/** 性别 */
	private Integer sex;
	/** 在线时间 */
	private Integer timeSecOnline;
	/** 最后一次登录时间 */
	private Long timeLogin;
	/** 最后一次登出时间 */
	private Long timeLogout;
	/** 角色创建时间 */
	private Long timeCreate;
	/** 当前经验 */
	private Long expCur;
	/** 当前等级 */
	private Integer level;
	/** 当前生命 */
	private Integer hpCur;
	/** 当前法力 */
	private Integer mpCur;
	
	public void setId(Long id){
		this.id = id;
	}
	public Long getId(){
		return this.id;
	}
	public void setServerid(Integer serverId){
		this.serverId = serverId;
	}
	public Integer getServerid(){
		return this.serverId;
	}
	public void setAccount(String account){
		this.account = account;
	}
	public String getAccount(){
		return this.account;
	}
	public void setName(String name){
		this.name = name;
	}
	public String getName(){
		return this.name;
	}
	public void setProfession(Integer profession){
		this.profession = profession;
	}
	public Integer getProfession(){
		return this.profession;
	}
	public void setSex(Integer sex){
		this.sex = sex;
	}
	public Integer getSex(){
		return this.sex;
	}
	public void setTimeseconline(Integer timeSecOnline){
		this.timeSecOnline = timeSecOnline;
	}
	public Integer getTimeseconline(){
		return this.timeSecOnline;
	}
	public void setTimelogin(Long timeLogin){
		this.timeLogin = timeLogin;
	}
	public Long getTimelogin(){
		return this.timeLogin;
	}
	public void setTimelogout(Long timeLogout){
		this.timeLogout = timeLogout;
	}
	public Long getTimelogout(){
		return this.timeLogout;
	}
	public void setTimecreate(Long timeCreate){
		this.timeCreate = timeCreate;
	}
	public Long getTimecreate(){
		return this.timeCreate;
	}
	public void setExpcur(Long expCur){
		this.expCur = expCur;
	}
	public Long getExpcur(){
		return this.expCur;
	}
	public void setLevel(Integer level){
		this.level = level;
	}
	public Integer getLevel(){
		return this.level;
	}
	public void setHpcur(Integer hpCur){
		this.hpCur = hpCur;
	}
	public Integer getHpcur(){
		return this.hpCur;
	}
	public void setMpcur(Integer mpCur){
		this.mpCur = mpCur;
	}
	public Integer getMpcur(){
		return this.mpCur;
	}
}