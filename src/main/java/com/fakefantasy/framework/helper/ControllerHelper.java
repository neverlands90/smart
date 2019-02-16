package com.fakefantasy.framework.helper;

import com.fakefantasy.framework.annotation.Action;
import com.fakefantasy.framework.bean.Handler;
import com.fakefantasy.framework.bean.Request;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class ControllerHelper {
    private static final Map<Request, Handler> ACTION_MAP = new HashMap<Request, Handler>();

    static {
        Set<Class<?>> controllerClassSet = ClassHelper.getControllerClassSet();
        if (CollectionUtils.isNotEmpty(controllerClassSet)) {
            //Traversal controllerClassSet
            for (Class<?> controllerClass : controllerClassSet) {
                //get controller methods
                Method[] methods = controllerClass.getMethods();
                if (ArrayUtils.isNotEmpty(methods)) {
                    //Traversal methods
                    for (Method method : methods) {
                        //find Action annotation
                        if (method.isAnnotationPresent(Action.class)) {
                            Action action = method.getAnnotation(Action.class);
                            String mapping = action.value();
                            //URL regex
                            if (mapping.matches("\\w+:/\\w*")) {
                                String[] array = mapping.split(":");
                                if (ArrayUtils.isNotEmpty(array) && array.length == 2) {
                                    //get method and path
                                    String requestMethod = array[0];
                                    String requestPath = array[1];
                                    Request request = new Request(requestMethod, requestPath);
                                    Handler handler = new Handler(controllerClass, method);
                                    ACTION_MAP.put(request, handler);
                                }
                            }

                        }
                    }
                }
            }
        }
    }

    public static Handler getHandler(String RequestMethod, String RequestPath) {
        Request request = new Request(RequestMethod, RequestPath);
        return ACTION_MAP.get(request);
    }
}
