1.proto文件需存储为UTF8且无BOM编码方式,换行符均转为UNIX格式（即\n为换行符）
2.工具会将目录中的proto文件生成对应的类
3.协议文件按照模块划分,需要分配协议ID范围,避免重复ID
4.客户端上行协议以C_开头，服务器下行协议以S_开头，且必须指明msgid。
5.结构体以D_开头,枚举类型以E_开头，不单独作为传输协议，无需指明msgid。
6.公共的或者被引用较多的结构体和枚举尽量放到Common.proto中。
7.如果需要引用其他协议中的协议、结构体、枚举等，需要在开头加入import。所有协议都应引入"options.proto"和"Common.proto";
