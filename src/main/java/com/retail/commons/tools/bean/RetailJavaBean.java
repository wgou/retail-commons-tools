/**  
 * Project Name:retail-commons-tools  
 * File Name:MyBatisJavaBean.java  
 * Package Name:com.retail.commons.tools.bean  
 * Date:2016年4月3日下午12:08:11  
 * Copyright (c) 2016, 成都瑞泰尔科技有限公司 All Rights Reserved.  
 *  
*/  
  
package com.retail.commons.tools.bean;  

import java.util.List;
import java.util.Map;

import com.retail.commons.tools.type.DBType;
import com.retail.commons.tools.type.DBTypeFactory;
import com.retail.commons.tools.type.mysql.MysqlType;

/**  
 * 描述: <br/>TODO; <br/>
 * ClassName:MyBatisJavaBean <br/>  
 * Date:     2016年4月3日 下午12:08:11 <br/>  
 * @author   苟伟(gouewi@retail-tek.com) 
 * @version enclosing_typetags  
 * @see        
 */
public class RetailJavaBean { 
	private String pageName; //包名
	private List<String> importPage; //导入的包
	private String className; //类
	private String pkName; //主键
	private Map<String,String> propertyByComment; //属性k:属性 v:属性注释
	private Map<String,String> property; //属性k:属性 v:属性类型
	private Map<String,String> propertyByClumon; //属性k v：列名
	private Map<String,Map<String,Map<String,String>>> getMethod; //K:列名K:属性 k:get方法名 v:返回类型
	private Map<String,Map<String,String>>  setMethod; //K:set方法名 k:参数 v:参数类型
	private String tableName;
	public String getPageName() {
		return pageName;
	}
	public void setPageName(String pageName) {
		this.pageName = pageName;
	}
	public List<String> getImportPage() {
		return importPage;
	}
	public void setImportPage(List<String> importPage) {
		this.importPage = importPage;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	
	public String getPkName() {
		return pkName;
	}
	public void setPkName(String pkName) {
		this.pkName = pkName;
	}
	public Map<String, String> getPropertyByComment() {
		return propertyByComment;
	}
	public void setPropertyByComment(Map<String, String> propertyByComment) {
		this.propertyByComment = propertyByComment;
	}
	public Map<String,String> getProperty() {
		return property;
	}
	public void setProperty(Map<String,String> property) {
		this.property = property;
	}
	
	public Map<String, String> getPropertyByClumon() {
		return propertyByClumon;
	}
	public void setPropertyByClumon(Map<String, String> propertyByClumon) {
		this.propertyByClumon = propertyByClumon;
	}
	public Map<String,Map<String,Map<String,String>>> getGetMethod() {
		return getMethod;
	}
	public void setGetMethod(Map<String,Map<String,Map<String,String>>> getMethod) {
		this.getMethod = getMethod;
	}
	public Map<String,Map<String,String>> getSetMethod() {
		return setMethod;
	}
	public void setSetMethod(Map<String,Map<String,String>> setMethod) {
		this.setMethod = setMethod;
	}
	
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	@Override
	public String toString() {
		StringBuffer javaBeanBuffer = new StringBuffer();
		if(getPageName() != null && getPageName().length() > 0){
			javaBeanBuffer.append("package ").append(getPageName()).append(";").append("\r\n");  //导入包
		}
		if(getImportPage() != null && getImportPage().size() > 0){
			for(String imp : getImportPage()){
				javaBeanBuffer.append("import ").append(imp).append(";").append("\r\n");  //导入包
			}
		}
		javaBeanBuffer.append("import com.retail.commons.base.BaseEntity;").append("\r\n");
		javaBeanBuffer.append("public class ").append(this.getClassName()).append(" extends BaseEntity ").append("{").append("\r\n"); //写入className
		javaBeanBuffer.append("\r\t");
		javaBeanBuffer.append("private static final long serialVersionUID = 1L;").append("\r\n");
		if(getProperty() != null && getProperty().size() > 0){
			Map<String,String> propertyCommentMap = this.getPropertyByComment();
			for(Map.Entry<String, String> property : getProperty().entrySet()){
				String comment = propertyCommentMap.get(property.getKey());
				if(comment !=null)
					javaBeanBuffer.append("	/** ").append(comment).append(" **/").append("\r\n");  //写入属性
				javaBeanBuffer.append("	private ").append(property.getValue()).append(" ").append(property.getKey()).append(";").append("\r\n");  //写入属性
			
			}
		}
		javaBeanBuffer.append("\r\t");
		if(getGetMethod() != null && getGetMethod().size() > 0){
			Map<String,Map<String,Map<String,String>>> getMap = getGetMethod();
			//输出属性get方法
			for(Map.Entry<String, Map<String,Map<String,String>>> methodTableBean : getMap.entrySet()){
				for(Map.Entry<String, Map<String,String>> methodBean : methodTableBean.getValue().entrySet()){
					for(Map.Entry<String, String> method : methodBean.getValue().entrySet()){
						javaBeanBuffer.append("public ").append(method.getValue()).append(" ").append(method.getKey()).append("(){").append("\r\n");  //写入get方法
						javaBeanBuffer.append("     return ").append(methodBean.getKey()).append(";").append("\r\t").append("}").append("\r\t");
					}
				}
			}
		}
		//输出属性set方法
		if(getSetMethod()!= null && getSetMethod().size() > 0){
			for(Map.Entry<String, Map<String,String>> methodBean : getSetMethod().entrySet()){
				for(Map.Entry<String, String> method : methodBean.getValue().entrySet()){
					javaBeanBuffer.append("public void ").append(methodBean.getKey()).append("(").append(method.getValue()).append(" ").append(method.getKey()).append(" ){").append("\r\n");  //写入set方法
					javaBeanBuffer.append("	 this. ").append(method.getKey()).append(" = ").append(method.getKey()).append(";").append("\r\t").append("}").append("\r\t");
				}
			}
		}
		javaBeanBuffer.append("}");
		return javaBeanBuffer.toString();
	}
	
	/**
	 *  生成mybatisxml
	 * @author luochen  
	 * @return
	 */
	public String mybatisToXml(String database){
		DBType dbType =  DBTypeFactory.bind(database);
		String namespace = "namespace_" + this.getClassName().toLowerCase() ;
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>").append("\r\n");
		sb.append("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\" >").append("\r\n");;
		sb.append("<mapper namespace=").append("\"").append(namespace).append("\"").append(">").append("\r\n");;
		sb.append("<resultMap id=").append("\"").append(this.getClassName().toLowerCase()).append("\"").append(" type=").append("\"").append(getPageName()).append(".").append(this.getClassName()).append("\"").append(" >").append("\r\n");
		for(Map.Entry<String, String> map : getPropertyByClumon().entrySet()){
			sb.append("		<result property=").append("\"").append(map.getKey()).append("\"")
			  .append(" column=").append("\"").append(map.getValue()).append("\"")
			  .append(" jdbcType=").append("\"");
			// for setMethod //K:set方法名 k:参数 v:参数类型
			boolean isType = false;
			for(Map.Entry<String, Map<String,String>> setM : getSetMethod().entrySet()){
				String type =setM.getValue().get(map.getKey());
				if(type !=null){
					String jdbcType = dbType.getJdbcType(type);
					sb.append(jdbcType);
					isType = true;
				}
			}
			if(!isType)
				sb.append( MysqlType.VARCHAR.toString());
			sb.append("\" ");
			sb.append(">").append("</result>").append("\r\n");
		}
		sb.append("  </resultMap>").append("\r\n");
		
		sb.append(selectAll());
		sb.append(select());
		sb.append(insert());
		sb.append(delete());
		sb.append("</mapper>");
		
		
		return sb.toString();
	}
	/**
	 * select all
	 * @author luochen  
	 * @return
	 */
	private String selectAll(){
		 StringBuilder sb = new StringBuilder();
		 sb.append("	<!-- ##########################查询####################### -->").append("\r\n");
		 sb.append(" 	<!-- 查询表 -->").append("\r\n");
		 sb.append("<sql id=\"select_all_cloumn\">").append("\r\n");
		 sb.append("	select ");
		 int i =0;
		 for(Map.Entry<String, String> map : getPropertyByClumon().entrySet()){
			 if(i != 0)
				 sb.append(",");
			 sb.append(map.getValue());
			 i++;
		 }
		 sb.append(" from ").append(this.getTableName()).append("\r\n");
		 sb.append("</sql>").append("\r\n");
		 
		 sb.append("<!-- 条件 -->").append("\r\n");
		 sb.append("<sql id=\"where-all-condition\">").append("\r\n");
		 sb.append(" 	<trim prefix=\"where\" prefixOverrides=\"and\">").append("\r\n");
		 
		 for(Map.Entry<String, String> map : getPropertyByClumon().entrySet()){
			 sb.append(" 	 <if test=").append("\"").append(map.getKey()).append("!= null").append("\"").append(" >").append("\r\n");
			 sb.append("		and ").append(map.getValue()).append("=").append("#{").append(map.getKey()).append("}").append("\r\n");
			 sb.append(" 	 </if>").append("\r\n");
		 }
		 sb.append(" 	</trim>").append("\r\n");
		 sb.append("</sql>").append("\r\n");
		   	
		 
		 return sb.toString();
	}
	
	public String select(){
		String namespace = "namespace_" + this.getClassName().toLowerCase() ;
		StringBuilder sb = new StringBuilder();
		 sb.append(" 	 <!-- 查询总数 -->").append("\r\n");
		 sb.append("<select id=").append("\"").append("count_").append(this.getTableName()).append("\"").append(" ").append("resultType=").append("\"").append("java.lang.Long").append("\"").append(">").append("\r\n");
		 sb.append("	select count(*) from ").append(this.getTableName()).append("\r\n");
		 sb.append("	<include refid=").append("\"").append(namespace).append(".where-all-condition").append("\"").append(" ></include>").append("\r\n");
		 sb.append("</select>").append("\r\n");
		 
		 sb.append(" 	 <!-- 查询单条记录 -->").append("\r\n");
		 sb.append("<select id=").append("\"").append("selectOne_").append(this.getTableName()).append("\"").append(" resultMap=").append("\"").append(this.getClassName().toLowerCase()).append("\"").append( ">").append("\r\n");
	 	 sb.append("	<include refid=").append("\"").append(namespace).append(".select_all_cloumn").append("\"").append(" ></include>").append("\r\n");
		 sb.append("	<include refid=").append("\"").append(namespace).append(".where-all-condition").append("\"").append(" ></include>").append("\r\n");
		 sb.append("	<include refid=").append("\"").append("commonMapper.sortSql").append("\"").append(" ></include>").append("\r\n");
		 sb.append("</select>").append("\r\n");
		 
		 sb.append(" 	  	<!-- 查询集合/分页 -->").append("\r\n");
		 
		 sb.append("<select id=").append("\"").append("select_").append(this.getTableName()).append("\"").append(" ").append("resultMap=").append("\"").append(this.getClassName().toLowerCase()).append("\"").append(">").append("\r\n");
		 sb.append("	select * from ( ").append("\r\n");
		 sb.append("	<include refid=").append("\"").append(namespace).append(".select_all_cloumn").append("\"").append(" ></include>").append("\r\n");
		 sb.append("	<include refid=").append("\"").append(namespace).append(".where-all-condition").append("\"").append(" ></include>").append("\r\n");
		 sb.append("	<include refid=").append("\"").append("commonMapper.sortSql").append("\"").append(" ></include>").append("\r\n");
		 sb.append("	)t").append("\r\n");
		 sb.append("	<include refid=").append("\"").append("commonMapper.limitSql").append("\"").append(" ></include>").append("\r\n");
		 sb.append("</select>").append("\r\n");
		  	
		 return sb.toString();
		   
	}
	
	public String insert(){
		StringBuilder sb = new StringBuilder();
		sb.append(" <!-- ######################### 插入操作 ######################## -->").append("\r\n");	
		sb.append("<!-- 插入单条 -->").append("\r\n");
		sb.append("<insert id=").append("\"").append("insert_").append(this.getTableName()).append("\"");
		if(this.getPkName() !=null){
			sb.append(" useGeneratedKeys=").append("\"").append("true").append("\"").append(" keyProperty=").append("\"").append(this.getPkName()).append("\"");
		}
		sb.append(" >").append("\r\n");
		sb.append("	insert into ").append(this.getTableName()).append(" (");
		int i = 0;
		StringBuilder values = new StringBuilder();
		values.append("(");
		for(Map.Entry<String, String> m : this.getPropertyByClumon().entrySet()){
			if(i!=0){
				sb.append(",");
				values.append(",");
			}
			sb.append(m.getValue());
			values.append("#{").append(m.getKey()).append("}");
			i++;
		}
		sb.append(")");
		values.append(")");
		sb.append(" values ").append(values.toString()).append("\r\n");
		sb.append("</insert>").append("\r\n");
		return sb.toString();
	}
	
	public String delete(){
		String namespace = "namespace_" + this.getClassName().toLowerCase() ;
		StringBuilder sb = new StringBuilder();
		sb.append("<!-- ######################## 删除操作 ############################## -->").append("\r\n");
		sb.append(" <!-- 删除数据 -->").append("\r\n");
	  	sb.append("<delete id=").append("\"").append("delete_").append(this.getTableName()).append("\"").append(" >").append("\r\n");
		sb.append("	delete from ").append(this.getTableName()).append("\r\n"); 
		sb.append("	<include refid=").append("\"").append(namespace).append(".where-all-condition").append("\"").append(" ></include>").append("\r\n");
		sb.append("</delete>").append("\r\n");
		return sb.toString();
	}
	
	/**
	 * daoJava:生成Dao <br/>  
	 * @author gouwei  
	 * @return
	 */
	
	public String daoJava(){
		StringBuffer sb = new StringBuffer();
		sb.append("package ").append(getPageName()).append(";").append("\r\n");
		sb.append("import com.retail.commons.base.BaseDao").append(";").append("\r\n");
		sb.append("import org.springframework.stereotype.Repository").append(";").append("\r\n");
		sb.append("import com.retail.commons.dao.ext.DAOProperties").append(";").append("\r\n");
		sb.append("\r\n").append("\r\n").append("\r\n").append("\r\n");
		sb.append("@DAOProperties").append("(").append("tableName=").append("\"").append(this.getTableName()).append("\"").append(",")
		.append("nameSpace=").append("\"").append("namespace_" + this.getClassName().toLowerCase() ).append("\"").append(")").append("\r\n");
		sb.append("@Repository").append("\r\n");
		sb.append("public ").append(" class ").append(this.getClassName()).append("Dao").append(" extends BaseDao").append("{").append("\r\n");
		sb.append("\r\n");
		sb.append("}").append("\r\n");
		return sb.toString();
		
	}
	
}
  
