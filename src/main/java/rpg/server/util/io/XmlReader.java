package rpg.server.util.io;

import org.w3c.dom.Element;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.DomReader;

/**
 * 对XStream做了一个包装，将常用功能简化，适合项目开发需求
 * 
 */
public class XmlReader {

	private static XStream xs = new XStream(new DomDriver());

	/**
	 * 从xml中解析出java对象
	 * 
	 * @param xml
	 *            xml输入字符串
	 * @return 解析出的java对象
	 * @throws Exception
	 *             需要在外部程序处理解析错误
	 */
	public static final Object fromXml(String xml) throws Exception {
		return xs.fromXML(xml);
	}

	/**
	 * 从xml中解析出属性，填充到传入的java对象参数
	 * 
	 * @param xml
	 *            xml输入字符串
	 * @param obj
	 *            解析出的java对象
	 * @throws Exception
	 *             需要在外部程序处理解析错误
	 */
	public static final void fromXml(String xml, Object obj) throws Exception {
		xs.fromXML(xml, obj);
	}

	/**
	 * Xml节点解析器接口<br />
	 * 将xml节点对象(Element)解析成对应的java对象
	 * 
	 * @param <T>
	 */
	public static interface XmlElementParser<T> {
		T fromXml(Element e);
	}

	/**
	 * 映射新的xml节点解析器<br />
	 * 对于复杂的xml节点及java对象，可以采用自行解析，而不是通过系统默认的反序列化方式解析<br />
	 * 
	 * @param type
	 *            解析的类型
	 * @param parser
	 *            解析器实例
	 */
	public static final void registerXmlElementParser(final Class<?> type,
			final XmlElementParser<?> parser) {
		xs.registerConverter(new Converter() {
			public boolean canConvert(@SuppressWarnings("rawtypes") Class c) {
				if (c.equals(type)) {
					return true;
				}
				return false;
			}

			public void marshal(Object source, HierarchicalStreamWriter writer,
					MarshallingContext context) {
				// 不做实现
			}

			public Object unmarshal(HierarchicalStreamReader reader,
					UnmarshallingContext context) {
				DomReader realReader = (DomReader) reader.underlyingReader();
				Element e = (Element) realReader.getCurrent();
				return parser.fromXml(e);
			}
		});
	}

	/**
	 * 映射类别名
	 * 
	 * @param name
	 * @param type
	 */
	public static final void alias(String name, Class<?> type) {
		xs.alias(name, type);
	}

	/**
	 * 映射包名
	 * 
	 * @param name
	 * @param pkgName
	 */
	public static final void aliasPackage(String name, String pkgName) {
		xs.aliasPackage(name, pkgName);
	}

	/**
	 * 获取XStream引用，以实现一些更为高级的xml序列化和反序列化方法<br />
	 * 注意！因为此处返回项目公用的XStream引用，除非有特殊需求，否则尽量使用包装方法，不直接调用XStream的方法
	 * 
	 * @return
	 */
	public static final XStream getXStream() {
		return xs;
	}
}
