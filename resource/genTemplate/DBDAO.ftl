package ${daoPackage};

import java.util.List;
import org.apache.ibatis.annotations.Param;
import ${modelPackage}.${objName};
import ${modelPackage}.${objName}Example;

public interface ${objName}Mapper {
	<#if enableCountByExample>
    int countByExample(${objName}Example example);
	</#if>
	
	<#if enableDeleteByExample>
    int deleteByExample(${objName}Example example);
	</#if>
	
	<#if enableDeleteByPrimaryKey>
    int deleteByPrimaryKey(<#list primaryKeyColumns as pk>@Param("${pk.name}") ${pk.javaType} ${pk.name}<#if pk_has_next>,</#if></#list>);
	</#if>
	
	<#if enableInsert>
    int insert(${objName} record);

    int insertSelective(${objName} record);
	</#if>
	
	<#if enableSelectByExample>
    List<${objName}> selectByExample(${objName}Example example);
	</#if>
	
	<#if enableSelectByPrimaryKey>
    ${objName} selectByPrimaryKey(<#list primaryKeyColumns as pk>@Param("${pk.name}") ${pk.javaType} ${pk.name}<#if pk_has_next>,</#if></#list>);
	</#if>
	
	<#if enableUpdateByExample>
    int updateByExampleSelective(@Param("record") ${objName} record, @Param("example") ${objName}Example example);

    int updateByExample(@Param("record") ${objName} record, @Param("example") ${objName}Example example);
	</#if>
	
	<#if enableUpdateByPrimaryKey>
    int updateByPrimaryKeySelective(${objName} record);

    int updateByPrimaryKey(${objName} record);
	</#if>
}