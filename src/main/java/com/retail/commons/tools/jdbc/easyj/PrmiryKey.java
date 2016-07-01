/**  
 * Project Name:retail-commons-tools  
 * File Name:PrimiryKey.java  
 * Package Name:com.retail.commons.tools.jdbc.oracle  
 * Date:2016年4月3日下午12:06:01  
 * Copyright (c) 2016, 成都瑞泰尔科技有限公司 All Rights Reserved.  
 *  
*/  
  
package com.retail.commons.tools.jdbc.easyj;  
/**  
 * 描述: <br/>TODO; <br/>
 * ClassName:PrimiryKey <br/>  
 * Date:     2016年4月3日 下午12:06:01 <br/>  
 * @author   苟伟(gouewi@retail-tek.com) 
 * @version enclosing_typetags  
 * @see        
 */
public class PrmiryKey {
	private String primaryKeyName;
	private Class<?> primaryKeyType;
	public String getPrimaryKeyName() {
		return primaryKeyName;
	}
	public void setPrimaryKeyName(String primaryKeyName) {
		this.primaryKeyName = primaryKeyName;
	}
	public Class<?> getPrimaryKeyType() {
		return primaryKeyType;
	}
	public void setPrimaryKeyType(Class<?> primaryKeyType) {
		this.primaryKeyType = primaryKeyType;
	}


}
  
