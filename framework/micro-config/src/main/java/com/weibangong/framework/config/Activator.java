package com.weibangong.framework.config;

import lombok.extern.slf4j.Slf4j;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * Created by jianghailong on 16/5/9.
 */
@Slf4j
public class Activator implements BundleActivator {

    @Override
    public void start(BundleContext context) throws Exception {
        log.info("public void start(BundleContext context) throws Exception");
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        log.info("public void stop(BundleContext context) throws Exception");
    }
}
