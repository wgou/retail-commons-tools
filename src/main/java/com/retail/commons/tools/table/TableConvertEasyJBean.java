package com.retail.commons.tools.table;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.retail.commons.tools.bean.EasyJJavaBean;
import com.retail.commons.tools.jdbc.Columns;
import com.retail.commons.tools.jdbc.ConfigJdbc;
import com.retail.commons.tools.jdbc.TableColumn;
import com.retail.commons.tools.jdbc.easyj.PrmiryKey;
import com.retail.commons.tools.jdbc.easyj.Table;
import com.retail.commons.tools.utils.ClassUtil;
import com.retail.commons.tools.utils.FileUtil;
import com.retail.commons.tools.utils.TableUtilFactory;
import com.retail.commons.tools.utils.Tools;

/**
 * 将用户所选table生成可用实体bean
 * @author 苟伟
 *
 */
public class TableConvertEasyJBean {
	private static final String annotationColumn = "org.abs.annotation.Column";
	private static final String annotationKeyType = "org.abs.annotation.KeyType";
	private static final String annotationPrimaryKey = "org.abs.annotation.PrimaryKey";
	private static final String annotationTable = "org.abs.annotation.Table";
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
			List<TableColumn> listTableC = ClassUtil.columnConverList(database,listTable);
			List<String> pkNames = TableUtilFactory.bind(database).getPrimaryKeyName(bean);
			Table table = new Table();
			if(pkNames.size() > 0) {
				List<PrmiryKey> pkList = new ArrayList<PrmiryKey>(); //存放组件，可能存在联合主键
				PrmiryKey pk = new PrmiryKey();
				Class<?> pkType = null;
				for(String pkName : pkNames){
					TableColumn rc = null;
					for(TableColumn c : listTableC ){
						if(c.getField().equals(pkName)){
							pkType = c.getTypeClazz();
							rc = c;
							break ;
						}
					}
					pk.setPrimaryKeyName(pkName);//主键  
					pk.setPrimaryKeyType(pkType);//主键  类型
					pkList.add(pk);
					listTableC.remove(rc);
				}
				table.setPrmiryKey(pkList);
			}
			table.setTableName(bean); //表名
			table.setPropertyName(listTableC);//属性名 - 属性类型
			//外键
			tableList.add(table);
		}
		return createJavaBean(tableList,javaPath,pageName);
	}
	
	/**
	 * 转化为javaBean实体对象
	 * @param tableList table对象集合
	 * @param javaPath 
	 * @param pageName
	 * @return
	 * @throws IOException
	 */
	private static int createJavaBean(List<Table> tableList,String javaPath,String pageName) throws IOException{
		int count  = 0;
		for(Table table : tableList){
			 List<String> importList = new ArrayList<String>();
			 importList.add(annotationTable);
			 importList.add(annotationColumn);
			 importList.add(annotationPrimaryKey);
			 importList.add(annotationKeyType);
			 Map<String,String> propertyMap = new HashMap<String, String>();
			 Map<String,Map<String,Map<String,String>>> getMehtodMapBean = new HashMap<String, Map<String,Map<String,String>>>();
			 Map<String,Map<String,String>> setMehtodMap = new HashMap<String, Map<String,String>>();
			if(table.getPrmiryKey() != null){
				for(PrmiryKey pk : table.getPrmiryKey()){
					String imp = Tools.checkImport(pk.getPrimaryKeyType());
					String pName = Tools.columnNameConvertPropertyName(pk.getPrimaryKeyName());
					String pType = Tools.propertyType(pk.getPrimaryKeyType().getName());
					String getName = Tools.getMethodName(pName,pk.getPrimaryKeyType());
					String setName = Tools.setMethodName(pName);
					if(imp != null)
						importList.add(imp);
					propertyMap.put(pName,pType);
					Map<String,String> getM = new HashMap<String, String>(1);
					getM.put(getName, pType);
					Map<String,Map<String,String>> getMehtodMap = new HashMap<String, Map<String,String>>();
					getMehtodMap.put(pName, getM);
					
					getMehtodMapBean.put(pk.getPrimaryKeyName(), getMehtodMap);
					
					Map<String,String> setM = new HashMap<String, String>(1);
					setM.put(pName, pType);
					setMehtodMap.put(setName, setM);
					
				}
			}
			for(TableColumn tc : table.getPropertyName()){
				String imp = Tools.checkImport(tc.getTypeClazz());
				String pName = Tools.columnNameConvertPropertyName(tc.getField());
				String pType = Tools.propertyType(tc.getTypeClazz().getName());
				String getName = Tools.getMethodName(pName,tc.getTypeClazz());
				String setName = Tools.setMethodName(pName);
				if(imp != null)
					importList.add(imp);
				propertyMap.put(pName,pType);

				Map<String,String> getM = new HashMap<String, String>();
				getM.put(getName, pType);
				Map<String,Map<String,String>> getMehtodMap = new HashMap<String, Map<String,String>>();
				getMehtodMap.put(pName, getM);
				
				getMehtodMapBean.put(tc.getField(), getMehtodMap);
				
				Map<String,String> setM = new HashMap<String, String>();
				setM.put(pName, pType);
				setMehtodMap.put(setName, setM);
			}
			EasyJJavaBean javaBean = new EasyJJavaBean();
			javaBean.setPageName(pageName);
			javaBean.setImportPage(importList);
			javaBean.setClassName(Tools.tableNameConvertClassName(table.getTableName()));
			javaBean.setProperty(propertyMap);
			javaBean.setGetMethod(getMehtodMapBean);
			javaBean.setSetMethod(setMehtodMap);
			javaBean.setPkName(table.getPrmiryKey());
			javaBean.setTableName(table.getTableName());
			FileUtil.writeJavaFile(javaPath, javaBean.getClassName()+".java", javaBean.toString());
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
