package com.hydt.app.config.dataSource;

/**
 * Created by bean_huang on 2017/7/11.
 */
public class DynamicDataSourceHolder {

    private static final ThreadLocal<String> THREAD_DATA_SOURCE = new ThreadLocal<String>();

    public static String getDataSource(){
        return THREAD_DATA_SOURCE.get();
    }

    public static void setDataSource(String dataSource){
        THREAD_DATA_SOURCE.set(dataSource);
    }

    public static void clearDataSource(){
        THREAD_DATA_SOURCE.remove();
    }
}
