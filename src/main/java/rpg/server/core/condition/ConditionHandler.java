package rpg.server.core.condition;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import rpg.server.core.module.Agent;

/**
 * 条件检测处理<br>
 * 必须标注在代理类上才会生效<br>
 * 方法有且仅有两种情况:<br>
 * 1:无参<br>
 * 2:参数为SimpleGameCondition
 * 
 * @see Agent
 * @see SimpleGameCondition
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ConditionHandler {
	ConditionType[] type();
}
