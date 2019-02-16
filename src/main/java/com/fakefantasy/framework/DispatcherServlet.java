package com.fakefantasy.framework;

import com.fakefantasy.framework.bean.Data;
import com.fakefantasy.framework.bean.Handler;
import com.fakefantasy.framework.helper.BeanHelper;
import com.fakefantasy.framework.helper.ConfigHelper;
import com.fakefantasy.framework.helper.ControllerHelper;
import com.fakefantasy.framework.util.ReflectionUtil;
import com.google.gson.Gson;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

@WebServlet(urlPatterns = "/*", loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet {
    @Override
    public void init(ServletConfig config) throws ServletException {
        //super.init(config);
        HelperLoader.init();
        ServletContext servletContext = config.getServletContext();
        // asset ?
        ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
        defaultServlet.addMapping(ConfigHelper.getAppAssetPath() + "*");

    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.service(req, resp);
        String requestMethod = req.getMethod().toLowerCase();
        String requestPath = req.getPathInfo();
        // get handler
        Handler handler = ControllerHelper.getHandler(requestMethod, requestPath);
        if (handler != null) {

            // get controller bean & method
            Class<?> controllerClass = handler.getControllerClass();
            Method actionMethod = handler.getActionMethod();
            Object controllerBean = BeanHelper.getBean(controllerClass);
            // action
            Object result = ReflectionUtil.invokeMethod(controllerBean, actionMethod, req);
            // result
            if (result instanceof Data) {
                //json
                Object model = ((Data) result).getModel();
                String json = new Gson().toJson(model);
                if (model != null) {
                    resp.setContentType("application/json");
                    resp.setCharacterEncoding("UTF-8");
                    resp.getWriter().write(json);
                    resp.getWriter().close();
                }
            }
        }
    }
}
