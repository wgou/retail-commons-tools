/**  
 * Project Name:OSUtil  
 * File Name:DBTypeFactory.java  
 * Package Name:org.common  
 * Date:2016年4月3日上午11:46:12  
 * Copyright (c) 2016, 成都瑞泰尔科技有限公司 All Rights Reserved.  
 *  
*/  
  
package com.retail.commons.tools.type;  

import com.retail.commons.tools.type.mysql.DBTypeMysql;
import com.retail.commons.tools.type.oracle.DBTypeOracle;

/**  
 * 描述: <br/>TODO; <br/>
 * ClassName:DBTypeFactory <br/>  
 * Date:     2016年4月3日 上午11:46:12 <br/>  
 * @author   苟伟(gouewi@retail-tek.com) 
 * @version enclosing_typetags  
 * @see        
 */
public class DBTypeFactory {
	private static final String mysql = "com.mysql.jdbc.Driver";
	private static final String oracle = "oracle.jdbc.driver.OracleDriver";
	private static DBType dbType = null;
	
	public synchronized static DBType bind(String database) {
		try{
			if(dbType == null){
				if(database.equals(mysql))
					dbType = DBTypeMysql.class.newInstance();
				else if(database.equals(oracle))
					dbType = DBTypeOracle.class.newInstance();
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return dbType;
	}
}
  
