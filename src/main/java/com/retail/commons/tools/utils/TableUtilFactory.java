/**  
 * Project Name:OSUtil  
 * File Name:TableUtilFactory.java  
 * Package Name:org.util  
 * Date:2016年4月3日上午11:09:02  
 * Copyright (c) 2016, 成都瑞泰尔科技有限公司 All Rights Reserved.  
 *  
*/  
  
package com.retail.commons.tools.utils;  

import com.retail.commons.tools.jdbc.mysql.utils.TableUtilMysql;
import com.retail.commons.tools.jdbc.oracle.utils.TableUtilOracle;

/**  
 * 描述: <br/>TODO; <br/>
 * ClassName:TableUtilFactory <br/>  
 * Date:     2016年4月3日 上午11:09:02 <br/>  
 * @author   苟伟(gouewi@retail-tek.com) 
 * @version enclosing_typetags  
 * @see        
 */
public class TableUtilFactory {

	private static final String mysql = "com.mysql.jdbc.Driver";
	private static final String oracle = "oracle.jdbc.driver.OracleDriver";
	private static TableUtil tableUtil = null;
	
	public synchronized static TableUtil bind(String database) {
		try{
			if(tableUtil == null){
				if(database.equals(mysql))
					tableUtil = TableUtilMysql.class.newInstance();
				else if(database.equals(oracle))
					tableUtil = TableUtilOracle.class.newInstance();
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return tableUtil;
	}
}
  
