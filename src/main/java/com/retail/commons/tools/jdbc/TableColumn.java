/**  
 * Project Name:retail-commons-tools  
 * File Name:TableColumn.java  
 * Package Name:com.retail.commons.tools.jdbc  
 * Date:2016年4月6日下午1:57:27  
 * Copyright (c) 2016, 成都瑞泰尔科技有限公司 All Rights Reserved.  
 *  
 */
package com.retail.commons.tools.jdbc;

/**  
 * 描述:<br/>TODO; <br/>  
 * ClassName: TableColumn <br/>  
 * date: 2016年4月6日 下午1:57:27 <br/>  
 * @author  苟伟(gouewi@retail-tek.com)   
 * @version   
 */
public class TableColumn {
	
	private String field;
	private Class<?> typeClazz;
	private String comment;
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public Class<?> getTypeClazz() {
		return typeClazz;
	}
	public void setTypeClazz(Class<?> typeClazz) {
		this.typeClazz = typeClazz;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public TableColumn(String field, Class<?> typeClazz, String comment) {
		super();
		this.field = field;
		this.typeClazz = typeClazz;
		this.comment = comment;
	}
	public TableColumn() {
		  
		super();  
		// TODO Auto-generated constructor stub  
		
	}
	
	

}
