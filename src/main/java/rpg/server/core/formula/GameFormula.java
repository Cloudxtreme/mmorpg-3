package rpg.server.core.formula;


public class GameFormula {

	private Formula formula;
	
	public GameFormula(String expression) {
		if(expression == null || expression.length() == 0){
			return;
		}
		formula = new Formula(expression);		
	}

	public GameFormula(String expression, SymbolResolver resolver) {
		formula = new Formula(expression,resolver);	
	}

	public float evaluate(SymbolResolver symbol) throws Exception {
		return formula.evaluate(symbol);
	}


	public void setExpression(String formula2) {
		this.formula = null;
		formula = new Formula(formula2);	
	}

	public String toString(){
		return formula.toString();
	}
}
