/**  
 * Project Name:retail-commons-tools  
 * File Name:Table.java  
 * Package Name:com.retail.commons.tools.jdbc.retail  
 * Date:2016年4月3日下午12:19:58  
 * Copyright (c) 2016, 成都瑞泰尔科技有限公司 All Rights Reserved.  
 *  
*/  
  
package com.retail.commons.tools.jdbc.retail;  

import java.util.List;

import com.retail.commons.tools.jdbc.TableColumn;

/**  
 * 描述: <br/>TODO; <br/>
 * ClassName:Table <br/>  
 * Date:     2016年4月3日 下午12:19:58 <br/>  
 * @author   苟伟(gouewi@retail-tek.com) 
 * @version enclosing_typetags  
 * @see        
 */
public class Table {

	private String tableName; // 表名
	private String pkName; //组建
	private  List<TableColumn> fieldByType; //字段对应类型 - 注释
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	public String getPkName() {
		return pkName;
	}
	public void setPkName(String pkName) {
		this.pkName = pkName;
	}
	public  List<TableColumn> getFieldByType() {
		return fieldByType;
	}
	public void setFieldByType( List<TableColumn> fieldByType) {
		this.fieldByType = fieldByType;
	}
	
	
	 
	
}
  
