package rpg.server.gen.proto;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.google.protobuf.CodedInputStream;
import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.Message;

public class MsgUtil {
	public static final int C_LOGIN = 11;
	public static final int C_LOGIN_2 = 21;
	
	//消息CLASS与消息ID的对应关系<消息class, 消息ID>
	private static final Map<Class<? extends Message>, Integer> classToId = new HashMap<>();
	//消息ID与消息CLASS的对应关系<消息ID, 消息class>
	private static final Map<Integer, Class<? extends Message>> idToClass = new HashMap<>();
	
	static {
		//初始化消息CLASS与消息ID的对应关系
		initClassToId();
		//初始化消息ID与消息CLASS的对应关系
		initIdToClass();
	}
	
	/**
	 * 获取消息ID
	 * @param clazz
	 * @return
	 */
	public static int getIdByClass(Class<? extends Message> clazz) {
		return classToId.get(clazz);
	}
	
	/**
	 * 获取消息CLASS
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getClassById(int msgId) {
		return (T) idToClass.get(msgId);
	}
	
	/**
	 * 获取消息名称
	 * @param clazz
	 * @return
	 */
	public static String getNameById(int msgId) {
		return idToClass.get(msgId).getSimpleName();
	}
	
	/**
	 * 初始化消息CLASS与消息ID的对应关系
	 */
	private static void initClassToId() {
		classToId.put(Account.C_LOGIN.class, C_LOGIN);
		classToId.put(Login.C_LOGIN_2.class, C_LOGIN_2);
	}
	
	/**
	 * 初始化消息ID与消息CLASS的对应关系
	 */
	private static void initIdToClass() {
		idToClass.put(C_LOGIN,Account.C_LOGIN.class);
		idToClass.put(C_LOGIN_2,Login.C_LOGIN_2.class);
	}
	/**
	 * 根据消息id解析消息
	 */
	public static GeneratedMessage parseFrom(int type, CodedInputStream s) throws IOException{
		switch(type){
			case C_LOGIN:
				return Account.C_LOGIN.parseFrom(s);
			case C_LOGIN_2:
				return Login.C_LOGIN_2.parseFrom(s);
		}
		return null;
	}
}

