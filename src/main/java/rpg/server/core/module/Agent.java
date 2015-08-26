package rpg.server.core.module;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 代理类标注<br>
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface Agent {
	/** 是否注册action处理 */
	boolean action() default true;

	/** 是否注册condition处理 */
	boolean condition() default true;

	/** 是否注册event处理 */
	boolean event() default true;

	/** 是否注册db回调事件处理 */
	boolean dbEvent() default true;
}
