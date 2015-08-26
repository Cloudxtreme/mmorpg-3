package rpg.server.module.stage;

import java.util.HashMap;
import java.util.Map;

import rpg.server.core.obj.GameObject;
import rpg.server.module.player.PlayerCharacter;

import com.google.protobuf.Message;

public class StageCell {
	public int i; // 行
	public int j; // 列
	private long stageId; // 所属地图
	private Map<Long, GameObject> worldObjects = new HashMap<Long, GameObject>(); // 该cell内所有地图单位
	private Map<Long, PlayerCharacter> players = new HashMap<>(); // 该cell内所有玩家

	public StageCell(long stageId, int i, int j) {
		this.stageId = stageId;
		this.i = i;
		this.j = j;
	}

	public void cleanup() {

	}

	/**
	 * 是否属于同一张地图的Cell
	 * 
	 * @return
	 */
	public boolean isInSameStage(StageCell cell) {
		return this.stageId == cell.stageId;
	}

	/**
	 * 添加地图单元
	 * 
	 * @param obj
	 */
	public void addWorldObject(GameObject obj) {
	}

	/**
	 * 删除地图单元
	 * 
	 * @param obj
	 */
	public void delWorldObject(GameObject obj) {
	}

	void sendMsgToAll(Message msg) {

	}

	/**
	 * 判断两Cell是否为同一个
	 * 
	 * @param cell
	 * @return
	 */
	public boolean equals(StageCell cell) {
		if (this.i == cell.i && this.j == cell.j)
			return true;
		return false;
	}

}
