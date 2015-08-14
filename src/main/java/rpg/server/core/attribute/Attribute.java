package rpg.server.core.attribute;

import rpg.server.core.formula.SymbolResolver;

/**
 * 属性接口
 * 
 * @author lincy
 *
 */
public abstract class Attribute {

	/**
	 * 转化为显示字符串 <br/>
	 * 如果是百分比显示，显示小数点后1位，如果不是，只显示整数
	 * 
	 * @param value
	 * @return
	 */
	public String toShowString() {
		if (getId().isPercentage()) {
			return String.format("%1$.1f", getfValue() * 100) + "%";
		}
		return String.valueOf(getiValue());
	}

	/**
	 * 获取id
	 * 
	 * @return
	 */
	abstract public Attributes getId();

	/**
	 * 计算基值
	 */
	abstract public void calBase(SymbolResolver sr);

	/**
	 * 设置基值
	 * 
	 * @param base
	 */
	abstract public void setBase(float base);

	/**
	 * 获取加成值
	 * 
	 * @return
	 */
	abstract public float getAddValue();

	/**
	 * 获取加成百分比
	 * 
	 * @return
	 */
	abstract public float getAddPer();

	/**
	 * 获取基值
	 * 
	 * @return
	 */
	abstract public float getBase();

	/**
	 * 获取属性值（浮点）
	 * 
	 * @return
	 */
	abstract public float getfValue();

	/**
	 * 获取属性值（四舍五入）
	 * 
	 * @return
	 */
	abstract public int getiValue();

	abstract public void changeBase(float change);

	abstract public void changeAddVal(float change);

	abstract public void changeAddPer(float change);

	/**
	 * 设置基础属性引起的属性百分比变化
	 * 
	 * @param change
	 */
	public void setBasePointAddPer(float change) {
	};

	/**
	 * 设置基础属性百分比加成的变化
	 * 
	 * @param change
	 */
	public void changeBaseAddPer(float change) {
	};

	public void resetAddPer() {
	};

}
