package rpg.server.core.attribute;

import java.text.NumberFormat;





import org.w3c.dom.Element;

import rpg.server.core.formula.GameFormula;
import rpg.server.core.formula.SymbolResolver;
import rpg.server.util.io.XmlUtils;

/**
 * 相关属性 <br/>
 * 单个属性对单个下级属性的影响（主要用于显示）
 * 
 * @author lincy
 * 
 */
public class RelatedAttr {

	// private Attributes base, related;

	private GameFormula formula;

	// private UITemplate textTemplate;
	private String textTemplate;

	public RelatedAttr(String base, Element e) {
		// this.base = Attributes.fromString(base);
		// related = Attributes.fromString(XmlUtils.getAttribute(e, "id"));
		formula = new GameFormula(XmlUtils.getAttribute(e, "f").replaceAll(
				"this", base));
		// textTemplate = new UITemplate(XmlUtils.getAttribute(e, "t"));
		textTemplate = XmlUtils.getAttribute(e, "t");
	}

	public String getText(SymbolResolver obj) {
		
		String val = "";
		try {
			NumberFormat formatter = NumberFormat.getNumberInstance();
			formatter.setMaximumFractionDigits(1);
			val = formatter.format(formula.evaluate(obj));
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		String text = textTemplate.replaceAll("\\{val\\}", val);
		return text;
	}
}
