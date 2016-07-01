/**  
 * Project Name:OSUtil  
 * File Name:DBtYPE.java  
 * Package Name:org.common  
 * Date:2016年4月3日上午11:47:29  
 * Copyright (c) 2016, 成都瑞泰尔科技有限公司 All Rights Reserved.  
 *  
*/  
  
package com.retail.commons.tools.type;  
/**  
 * 描述: <br/>TODO; <br/>
 * ClassName:DBtYPE <br/>  
 * Date:     2016年4月3日 上午11:47:29 <br/>  
 * @author   苟伟(gouewi@retail-tek.com) 
 * @version enclosing_typetags  
 * @see        
 */
public interface DBType {

	Class<?>  converClass(String columnType);
	public String getJdbcType(String type);
}
  
