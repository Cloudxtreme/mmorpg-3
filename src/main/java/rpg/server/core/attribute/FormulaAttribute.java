package rpg.server.core.attribute;

import rpg.server.core.formula.GameFormula;
import rpg.server.core.formula.SymbolResolver;
import rpg.server.core.obj.GameObject;
import rpg.server.util.log.Log;

/**
 * 公式属性 <br/>
 * 属性值 = (base + addVal) * (1 + addPer) <br/>
 * base由指定公式计算 <br/>
 * addVal和addPer通过set方法修改 <br/>
 * 主要用于前期公式参数调整频繁的时候 <br/>
 * 
 * @author lincy
 * 
 */
public class FormulaAttribute extends Attribute {

	FormulaAttribute(Attributes id, GameObject owner) {
		this.id = id;
		this.config = (FormulaAttrConfig) owner.getAttrConfig();
		this.formula = new GameFormula(config.getFormula(id));

	}

	public void calBase(SymbolResolver sr) {
		try {
			base = formula.evaluate(sr);
			calculate();
		} catch (Exception e) {
			Log.game.error("formul cal error" + formula.toString(), e);
		}
	}

	public void changeAddPer(float change) {
		setAddPer(addPer + change);
	}

	public void changeAddVal(float change) {
		setAddVal(addVal + change);
	}

	public void changeBase(float change) {
		setBase(base + change);
	}

	public void setFormula(String formula, SymbolResolver sr) {
		this.formula.setExpression(formula);
		calBase(sr);
	}

	private void calculate() {
		// float value = 0;
		// if (base + addVal < 0) {
		// value = 0;
		// } else {
		// value = base + addVal;
		// }
		fValue = ((base * (1 + baseAddPer)) + addVal)
				* (1 + addPer + basePointAddPer);
		if (fValue < config.getMinValue(id))
			fValue = config.getMinValue(id);
		else if (fValue > config.getMaxValue(id))
			fValue = config.getMaxValue(id);
		iValue = Math.round(fValue);
	}

	/**
	 * @return the iValue
	 */
	public int getiValue() {
		return iValue;
	}

	/**
	 * @return the fValue
	 */
	public float getfValue() {
		return fValue;
	}

	/**
	 * @return the base
	 */
	public float getBase() {
		return base;
	}

	@Override
	public float getAddValue() {
		return addVal;
	}

	@Override
	public float getAddPer() {
		return addPer;
	}

	/**
	 * @param base
	 *            the base to set
	 */
	public void setBase(float base) {
		this.base = Math.max(0, base);
		calculate();
	}

	private void setAddVal(float addVal) {
		// this.addVal = Math.max(0, addVal);
		this.addVal = addVal;
		calculate();
	}

	private void setAddPer(float addPer) {
		// this.addPer = Math.max(0, addPer);
		this.addPer = addPer;
		calculate();
	}

	public void resetAddPer() {
		this.addPer = 0;
		calculate();
	}

	public float getBasePointAddPer() {
		return basePointAddPer;
	}

	@Override
	public void setBasePointAddPer(float change) {
		this.basePointAddPer = change;
		calculate();
	}

	public float getBaseAddPer() {
		return baseAddPer;
	}

	public void setBaseAddPer(float baseAddPer) {
		this.baseAddPer = baseAddPer;
		if (this.baseAddPer < -1) {// 不允许出现负属性
			this.baseAddPer = -1.0f;
		}
		calculate();
	}

	public void changeBaseAddPer(float change) {
		setBaseAddPer(this.baseAddPer + change);
	}

	@Override
	public Attributes getId() {
		return id;
	}

	// ////////////////////////////////////////////////////////////////////////

	private Attributes id; // id
	private int iValue; // 属性总值（四舍五入值）
	private float fValue; // 属性总值（精确值）
	private float base = 0.0f, addVal = 0.0f, addPer = 0.0f, basePointAddPer; // 基值，数值加成，百分比加成,基础属性点数影响的百分比加成
	private float baseAddPer = 0.0f;// 基础属性加成百分比
	private GameFormula formula; // 基础值计算公式
	private FormulaAttrConfig config; // 属性配置
}
