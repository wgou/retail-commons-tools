package com.retail.commons.tools.table;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.retail.commons.tools.bean.RetailJavaBean;
import com.retail.commons.tools.jdbc.Columns;
import com.retail.commons.tools.jdbc.ConfigJdbc;
import com.retail.commons.tools.jdbc.TableColumn;
import com.retail.commons.tools.jdbc.retail.Table;
import com.retail.commons.tools.utils.ClassUtil;
import com.retail.commons.tools.utils.FileUtil;
import com.retail.commons.tools.utils.TableUtilFactory;
import com.retail.commons.tools.utils.Tools;

/**
 * 将用户所选table生成可用实体bean
 * @author 苟伟
 *
 */
public class TableConvertRetailBean {
	/**
	 * table convert bean
	 * @param tableNames 表名集合
	 * @param javaPath 生成文件路径
	 * @param pageName 生成java文件包名
	 * @throws SQLException
	 * @throws IOException
	 */
	public  int convertBean(String database,List<String> tableNames,String javaPath,String pageName) throws SQLException, IOException{
		List<Table> tableList = new ArrayList<Table>();
		for(String bean : tableNames){
			 //根据tableName查询table详细信息
			List<Columns>  listTable = TableUtilFactory.bind(database).getTableColumn(bean);
			List<TableColumn> listTablec = ClassUtil.columnConverList(database,listTable);
			List<String> listPk = TableUtilFactory.bind(database).getPrimaryKeyName(bean);
			Table table = new Table();
			table.setTableName(bean); //表名
			String pkName = listPk.size() > 0 ? listPk.get(0):null;
			table.setPkName(pkName);
			table.setFieldByType(listTablec);;//属性名 - 属性类型 - 
			tableList.add(table);
		}
		return createJavaBean(tableList,javaPath,pageName,database);
	}
	
	/**
	 * 转化为javaBean实体对象
	 * @param tableList table对象集合
	 * @param javaPath 
	 * @param pageName
	 * @return
	 * @throws IOException
	 */
	private  int createJavaBean(List<Table> tableList,String javaPath,String pageName,String database) throws IOException{
		int count  = 0;
		for(Table table : tableList){
			List<String> importList = new ArrayList<String>();
			 Map<String,String> propertyByCommentMap = new HashMap<String, String>();
			 Map<String,String> propertyMap = new HashMap<String, String>();
			 Map<String,String> propertyByClumonMap = new HashMap<String, String>();
			 Map<String,Map<String,Map<String,String>>> getMehtodMapBean = new HashMap<String, Map<String,Map<String,String>>>();
			 Map<String,Map<String,String>> setMehtodMap = new HashMap<String, Map<String,String>>();
			for(TableColumn tc : table.getFieldByType()){
				String imp = Tools.checkImport(tc.getTypeClazz());
				String clumonName = tc.getField(); //列名
				String pName = Tools.columnNameConvertPropertyName(clumonName); //属性
				String pType = Tools.propertyType(tc.getTypeClazz().getName());
				String getName = Tools.getMethodName(pName,tc.getTypeClazz());
				String setName = Tools.setMethodName(pName);
				if(imp != null){
					boolean isImp = false;
					for(String _imp : importList){
						if(_imp.equals(imp)){
							isImp = true;
						}
					}
					if(!isImp)
						importList.add(imp);
				}
				propertyByCommentMap.put(pName,tc.getComment());
				propertyMap.put(pName,pType);
				propertyByClumonMap.put(pName, clumonName);
				
				Map<String,String> getM = new HashMap<String, String>();
				getM.put(getName, pType);
				Map<String,Map<String,String>> getMehtodMap = new HashMap<String, Map<String,String>>();
				getMehtodMap.put(pName, getM);
				
				getMehtodMapBean.put(tc.getField(), getMehtodMap);
				
				Map<String,String> setM = new HashMap<String, String>();
				setM.put(pName, pType);
				setMehtodMap.put(setName, setM);
			}
			RetailJavaBean javaBean = new RetailJavaBean();
			javaBean.setPageName(pageName);
			javaBean.setImportPage(importList);
			javaBean.setClassName(Tools.tableNameConvertClassName(table.getTableName()));
			if(table.getPkName() !=null){
				String pk = Tools.columnNameConvertPropertyName(table.getPkName()); // 主键 -> 属性
				javaBean.setPkName(pk);
			}
			javaBean.setPropertyByComment(propertyByCommentMap);
			javaBean.setProperty(propertyMap);
			javaBean.setPropertyByClumon(propertyByClumonMap);
			javaBean.setGetMethod(getMehtodMapBean);
			javaBean.setSetMethod(setMehtodMap);
			javaBean.setTableName(table.getTableName());
			FileUtil.writeJavaFile(javaPath, javaBean.getClassName()+".java", javaBean.toString());
			FileUtil.writeJavaFile(javaPath, javaBean.getClassName()+"_Mapper.xml", javaBean.mybatisToXml(database));
			FileUtil.writeJavaFile(javaPath, javaBean.getClassName()+"Dao.java", javaBean.daoJava());
			++count;
		}
		return count;
	}
	//创建abs.properties文件
	public static int createAbsProperties(final String path,String driver,String url,String name , String pass){
		try {
			ConfigJdbc config = new ConfigJdbc();
			config.setDriver(driver);
			config.setUrl(url);
			config.setName(name);
			config.setPass(pass);
			FileUtil.writeJavaFile(path, "abs.properties", config.toString());
		} catch (IOException e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
}
