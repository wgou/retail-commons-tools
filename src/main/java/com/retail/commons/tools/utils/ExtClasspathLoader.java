package com.retail.commons.tools.utils;


import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

/** *//**
 * 根据properties中配置的路径把jar和配置文件加载到classpath中。
 * @author 苟伟
 *
 */
public final class ExtClasspathLoader {
    /** *//** URLClassLoader的addURL方法 */
    private static Method addURL = initAddMethod();

    private static URLClassLoader classloader = (URLClassLoader) ClassLoader.getSystemClassLoader();

    /** *//**
     * 初始化addUrl 方法.
     * @return 可访问addUrl方法的Method对象
     */
    private static Method initAddMethod() {
        try {
            Method add = URLClassLoader.class.getDeclaredMethod("addURL", new Class[] { URL.class });
            add.setAccessible(true);
            return add;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static void loadClasspath(String jar) {
        File file = new File(jar);
        loopFiles(file);
    }

    /** *//**
     * 循环遍历目录，找出所有的jar包。
     * @param file 当前遍历文件
     */
    private static void loopFiles(File file) {
        if (file.isDirectory()) {
            File[] tmps = file.listFiles();
            for (File tmp : tmps) {
                loopFiles(tmp);
            }
        }
        else {
            if (file.getAbsolutePath().endsWith(".jar") || file.getAbsolutePath().endsWith(".zip")) {
                addURL(file);
            }
        }
    }
    /** 
     * 通过filepath加载文件到classpath。
     * @param filePath 文件路径
     * @return URL
     * @throws Exception 异常
     */
    private static void addURL(File file) {
        try {
            addURL.invoke(classloader, new Object[] { file.toURI().toURL() });
        }
        catch (Exception e) {
        }
    }

    public static void main(String[] args) {
        ExtClasspathLoader.loadClasspath("D:\\jar");
    }
}