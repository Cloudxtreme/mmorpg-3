package rpg.server.core.event;


/**
 * 事件参数条件
 */
public class GameEventParaCondition {

	/**
	 * 从条件语句解析条件
	 * @param cond	格式为"index operator para"
	 * 		<br/>例如："2 = 0"表示该条件要求事件的第2个参数为0
	 * 		<br/>又如："0 != true"表示该条件要求事件的第0个参数不是true
	 * @throws Exception 
	 */
	public GameEventParaCondition(String cond) throws Exception {
		String[] split = cond.split(" ", 3);
		if (split.length < 3) {
			throw new Exception("事件参数条件格式错误！");
		}
		index = Byte.parseByte(split[0]);
		operator = split[1];
		if(operator != null){
			if(operator.equals("=") || operator.equals("==")){
				para = split[2].split("\\|");
			}else if(operator.equals("!=")){
				para = split[2].split("&");
			}			
		}
		
	}
	
	public boolean check(GameEvent event) {
		if (event.getParams().length <= index)	//事件参数个数不足
			return false;
		String temp = event.getParameter(index).toString();
		if (operator.equals("=") || operator.equals("==")){
			for(String str:para){
				if(temp.equals(str)){
					return true;
				}
			}
			return false;
		} else if (operator.equals("!=")){
			boolean result = true;
			for(String str:para){
				if(temp.equals(str)){
					return false;
				}
			}
			return result;
		}else{	//未定义的判断运算符
			return false;
		}
	}
	
	@Override
	public String toString() {
		return "" + index + " " + operator + " " + para;
	}


	private byte index;	//需判断的参数在参数列表中的下标
	
	private String operator;	//判断运算符（目前仅支持=和!=）
	
	private String[] para;	//要求的值
}
