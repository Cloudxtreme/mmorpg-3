<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${daoPackage}.${objName}Mapper">
	<resultMap id="BaseResultMap" type="${modelPackage}.${objName}">
	<#list primaryKeyColumns as col>
		<id column="${col.name}" jdbcType="${col.jdbcTypeName}" property="${col.name}" />
	</#list>
	<#list baseColumns as col>
		<result column="${col.name}" jdbcType="${col.jdbcTypeName}" property="${col.name}" />
	</#list>
	<#list blobColumns as col>
		<result column="${col.name}" jdbcType="${col.jdbcTypeName}" property="${col.name}" />
	</#list>
	</resultMap>
	<sql id="Example_Where_Clause">
		<where>
			<foreach collection="oredCriteria" item="criteria" separator="or">
				<if test="criteria.valid">
					<trim prefix="(" prefixOverrides="and" suffix=")">
						<foreach collection="criteria.criteria" item="criterion">
							<choose>
								<when test="criterion.noValue">
									and ${'$'}{criterion.condition}
								</when>
								<when test="criterion.singleValue">
									and ${'$'}{criterion.condition} ${'#'}{criterion.value}
								</when>
								<when test="criterion.betweenValue">
									and ${'$'}{criterion.condition} ${'#'}{criterion.value} and ${'#'}{criterion.secondValue}
								</when>
								<when test="criterion.listValue">
									and ${'$'}{criterion.condition}
									<foreach close=")" collection="criterion.value" item="listItem" open="(" separator=",">
										${'#'}{listItem}
									</foreach>
								</when>
							</choose>
						</foreach>
					</trim>
				</if>
			</foreach>
		</where>
	</sql>
	<sql id="Update_By_Example_Where_Clause">
		<where>
			<foreach collection="example.oredCriteria" item="criteria" separator="or">
				<if test="criteria.valid">
					<trim prefix="(" prefixOverrides="and" suffix=")">
						<foreach collection="criteria.criteria" item="criterion">
							<choose>
								<when test="criterion.noValue">
									and ${'$'}{criterion.condition}
								</when>
								<when test="criterion.singleValue">
									and ${'$'}{criterion.condition} ${'#'}{criterion.value}
								</when>
								<when test="criterion.betweenValue">
									and ${'$'}{criterion.condition} ${'#'}{criterion.value} and ${'#'}{criterion.secondValue}
								</when>
								<when test="criterion.listValue">
									and ${'$'}{criterion.condition}
									<foreach close=")" collection="criterion.value" item="listItem" open="(" separator=",">
										${'#'}{listItem}
									</foreach>
								</when>
							</choose>
						</foreach>
					</trim>
				</if>
			</foreach>
		</where>
	</sql>
	
	<sql id="Base_Column_List">
		<#list columnsList as col>${col.name}<#if col_has_next>,</#if></#list>
	</sql>
	
	<#if enableSelectByExample>
    <select id="selectByExample" parameterType="${modelPackage}.${objName}Example" resultMap="BaseResultMap">
		select
		<if test="distinct">
			distinct
		</if>
		<include refid="Base_Column_List" />
			from ${tableName}
		<if test="_parameter != null">
			<include refid="Example_Where_Clause" />
		</if>
		<if test="orderByClause != null">
			order by ${'$'}{orderByClause}
		</if>
	</select>
	</#if>
	
	<#if enableSelectByPrimaryKey>
	<select id="selectByPrimaryKey" parameterType="java.lang.Long" resultMap="BaseResultMap">
		select 
		<include refid="Base_Column_List" />
		from ${tableName}
		where
		<#list primaryKeyColumns as col>
		${col.name} = ${'#'}{${col.name},jdbcType=${col.jdbcTypeName}}<#if col_has_next>
		and</#if>
		</#list>
	</select>
	</#if>
   
	<#if enableDeleteByPrimaryKey>
    <delete id="deleteByPrimaryKey" parameterType="java.lang.Long">
		delete from ${tableName}
		where
		<#list primaryKeyColumns as col>
		${col.name} = ${'#'}{${col.name},jdbcType=${col.jdbcTypeName}}<#if col_has_next>
		and</#if>
		</#list>
	</delete>
	</#if>
	
	<#if enableDeleteByExample>
	<delete id="deleteByExample" parameterType="${modelPackage}.${objName}Example">
		delete from ${tableName}
		<if test="_parameter != null">
			<include refid="Example_Where_Clause" />
		</if>
	</delete>
	</#if>
	
	<#if enableInsert>
	<insert id="insert" parameterType="${modelPackage}.${objName}">
		insert into ${tableName} (<#list columnsList as col>${col.name}<#if col_has_next>,</#if></#list>)
		values (
		<#list columnsList as col>${'#'}{${col.name},jdbcType=${col.jdbcTypeName}}<#if col_has_next>,</#if></#list>
		)
	</insert>
	
	<insert id="insertSelective" parameterType="${modelPackage}.${objName}">
		insert into ${tableName}
		<trim prefix="(" suffix=")" suffixOverrides=",">
		<#list columnsList as col>
			<if test="${col.name} != null">
				${col.name},
			</if>
		</#list>
		</trim>
		<trim prefix="values (" suffix=")" suffixOverrides=",">
		<#list columnsList as col>
			<if test="${col.name} != null">
				${'#'}{${col.name},jdbcType=${col.jdbcTypeName}},
			</if>
		</#list>
		</trim>
	</insert>
	</#if>
   
	<#if enableCountByExample>
	<select id="countByExample" parameterType="${modelPackage}.${objName}Example" resultType="java.lang.Integer">
		select count(*) from ${tableName}
		<if test="_parameter != null">
			<include refid="Example_Where_Clause" />
		</if>
	</select>
	</#if>
	
	<#if enableUpdateByExample>
	<update id="updateByExampleSelective" parameterType="map">
		update ${tableName}
		<set>
		<#list columnsList as col>
			<if test="record.${col.name} != null">
				${col.name} = ${'#'}{record.${col.name},jdbcType=${col.jdbcTypeName}}<#if col_has_next>,</#if>
			</if>
		</#list>
		</set>
		<if test="_parameter != null">
			<include refid="Update_By_Example_Where_Clause" />
		</if>
	</update>
	
	<update id="updateByExample" parameterType="map">
		update ${tableName} set
		<#list columnsList as col>
			${col.name} = ${'#'}{record.${col.name},jdbcType=${col.jdbcTypeName}}<#if col_has_next>,</#if>
		</#list>
		<if test="_parameter != null">
			<include refid="Update_By_Example_Where_Clause" />
		</if>
	</update>
	</#if>
	
	<#if enableUpdateByPrimaryKey>
	<update id="updateByPrimaryKeySelective" parameterType="${modelPackage}.${objName}">
		update ${tableName}
		<set>
		<#list baseColumns as col>
			<if test="${col.name} != null">
				${col.name} = ${'#'}{${col.name},jdbcType=${col.jdbcTypeName}},
			</if>
		</#list>
		<#list blobColumns as col>
			<if test="${col.name} != null">
				${col.name} = ${'#'}{${col.name},jdbcType=${col.jdbcTypeName}},
			</if>
		</#list>
		</set>
		where
		<#list primaryKeyColumns as col>
		${col.name} = ${'#'}{${col.name},jdbcType=${col.jdbcTypeName}}<#if col_has_next>
		and</#if>
		</#list>
	</update>
	
	<update id="updateByPrimaryKey" parameterType="${modelPackage}.${objName}">
		update ${tableName} set
		<#list otherColumns as col>
			${col.name} = ${'#'}{${col.name},jdbcType=${col.jdbcTypeName}}<#if col_has_next>,</#if>
		</#list>
		where
		<#list primaryKeyColumns as col>
		${col.name} = ${'#'}{${col.name},jdbcType=${col.jdbcTypeName}}<#if col_has_next>
		and</#if>
		</#list>
	</update>
	</#if>
</mapper>