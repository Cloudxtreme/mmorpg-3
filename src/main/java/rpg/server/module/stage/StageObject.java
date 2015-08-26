package rpg.server.module.stage;

import java.util.HashMap;
import java.util.Map;

import com.google.protobuf.Message;
import com.google.protobuf.Message.Builder;

import rpg.server.core.obj.GameObject;
import rpg.server.util.task.TickTask;

public class StageObject implements TickTask {
	private double cellWidth = 16; // 单元格宽
	private double cellHeight = 9; // 单元格高

	private double width; // 地图的宽
	private double height; // 地图的高
	private long id;
	private String name;
	private StageCell[][] cells;
	/**
	 * 地图内所有游戏对象<br>
	 * key:游戏对象ID,value:游戏对象
	 */
	private Map<Long, GameObject> worldObjs = new HashMap<>();

	public void init() {
		// 将地图进行单元格分割
		int w = (int) (Math.ceil(width / cellWidth));
		int h = (int) (Math.ceil(height / cellHeight));
		cells = new StageCell[h][w];
		for (int j = 0; j < w; j++) {
			for (int i = 0; i < h; i++) {
				StageCell cell = new StageCell(id, i, j);
				cells[i][j] = cell;
			}
		}
		// 生成怪物
		// 生成NPC
	}

	public void tick() {
		for (GameObject obj : worldObjs.values()) {
			obj.tick();
		}
	}

	public void enter(GameObject obj) {

	}

	public void leave(GameObject obj) {

	}

	public void sendMsgToArea(Builder builder, GameObject stageObj) {
		this.sendMsgToArea(builder.build(), stageObj);
	}

	public void sendMsgToArea(Message msg, GameObject stageObj) {

	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

}
