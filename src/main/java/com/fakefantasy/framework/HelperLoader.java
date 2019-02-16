package com.fakefantasy.framework;

import com.fakefantasy.framework.helper.BeanHelper;
import com.fakefantasy.framework.helper.ClassHelper;
import com.fakefantasy.framework.helper.ControllerHelper;
import com.fakefantasy.framework.helper.IocHelper;
import com.fakefantasy.framework.util.ClassUtil;

public final class HelperLoader {
    public static void init() {
        Class<?>[] classList = {
                ClassHelper.class,
                BeanHelper.class,
                IocHelper.class,
                ControllerHelper.class
        };
        for (Class<?> cls : classList) {
            ClassUtil.loadClass(cls.getName(), true);
        }
    }
}
