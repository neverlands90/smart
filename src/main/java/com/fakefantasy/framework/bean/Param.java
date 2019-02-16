package com.fakefantasy.framework.bean;

import org.apache.commons.beanutils.ConvertUtils;

import java.util.Map;

public class Param {
    private Map<String, Object> paramMap;

    public Param(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }

    public Map<String, Object> getParamMap() {
        return paramMap;
    }

    //get long parameter
    public Long getLong(String name) {
        return (Long) ConvertUtils.convert(paramMap.get(name), Long.class);
    }
}
