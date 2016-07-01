/**  
 * Project Name:retail-commons-tools  
 * File Name:Columns.java  
 * Package Name:com.retail.commons.tools.jdbc.retail  
 * Date:2016年4月6日下午1:43:33  
 * Copyright (c) 2016, 成都瑞泰尔科技有限公司 All Rights Reserved.  
 *  
 */
package com.retail.commons.tools.jdbc;

/**  
 * 描述:<br/>TODO; <br/>  
 * ClassName: Columns <br/>  
 * date: 2016年4月6日 下午1:43:33 <br/>  
 * @author  苟伟(gouewi@retail-tek.com)   
 * @version   
 */
public class Columns {

	private String columnName; //列名
	private String type;//类型
	private String comment;//注释
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	
}
