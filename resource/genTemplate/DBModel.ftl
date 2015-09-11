package ${modelPackage};

public class ${objName} {
	<#list columnList as column>
	/** ${column.remarks} */
	private ${column.javaType} ${column.name};
	</#list>
	
	<#list columnList as column>
	public void ${column.sname}(${column.javaType} ${column.name}){
		this.${column.name} = ${column.name};
	}
	public ${column.javaType} ${column.gname}(){
		return this.${column.name};
	}
	</#list>
}