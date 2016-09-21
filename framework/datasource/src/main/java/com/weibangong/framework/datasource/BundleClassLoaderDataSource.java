package com.weibangong.framework.datasource;

import com.mysql.jdbc.Driver;
import org.apache.commons.dbcp2.BasicDataSource;

/**
 * Created by jianghailong on 15/12/12.
 */
public class BundleClassLoaderDataSource extends BasicDataSource {

    public BundleClassLoaderDataSource() {
        setDriverClassLoader(Driver.class.getClassLoader());
        setDriverClassName(Driver.class.getName());
    }
}
