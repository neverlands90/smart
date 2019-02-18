package com.fakefantasy.framework.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

public abstract class AspectProxy implements Proxy {

    private static final Logger LOGGER = LoggerFactory.getLogger(AspectProxy.class);
    @Override
    public Object doProxy(ProxyChain proxyChain) throws Throwable {
        Object result = null;
        Class<?> cls = proxyChain.getTargetClass();
        Method method = proxyChain.getTargetMethod();
        Object[] params = proxyChain.getMethodParams();

        try {
            //如果符合切点定义的条件，对该方法进行拦截
            if (intercept(cls, method, params)) {
                //前置增强
                before(cls, method, params);
                //执行代理链中的代理方法
                result = proxyChain.doProxyChain();
                //后置增强
                after(cls, method, params, result);
            } else {
                result = proxyChain.doProxyChain();
            }
        } catch (Exception e) {
            LOGGER.error("proxy failure", e);
            throw e;
        }
        return result;
    }

    //切点定义
    public boolean intercept(Class<?> cls, Method method, Object[] params) throws Throwable{
        return true;
    }
    //前置增强
    public void before(Class<?> cls, Method method, Object[] params) throws Throwable {}
    //后置增强
    public void after(Class<?> cls, Method method, Object[] params, Object result) throws Throwable {}
}
