package rpg.server.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.google.protobuf.GeneratedMessage;

/**
 * 协议处理注解<br>
 * 1.只能标注在agent内的方法<br>
 * 2.一个协议只能被一个处理方法处理,重复标注将会出现无法预期错误
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MsgHandler {
	Class<? extends GeneratedMessage> value();
}
