package org.xij.web.core;

import com.github.pagehelper.PageHelper;
import org.apache.catalina.connector.ClientAbortException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.handler.AbstractHandlerMethodMapping;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;
import org.xij.annotation.Pageable;
import org.xij.util.Result;
import org.xij.util.XIJException;
import org.xij.web.module.HomeController;
import org.xij.util.JSON;
import org.xij.web.util.Webs;
//import org.xij.web.module.sys.right.Right;
//import org.xij.web.module.sys.right.RightService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.Map;

@Component
public class XIJInterceptor implements HandlerInterceptor {
    private final Logger LOG = LoggerFactory.getLogger(XIJInterceptor.class);
    private String packageName = HomeController.class.getPackage().getName();
    private int startIdx = packageName.length() + 1;
    private int suffixLen = Controller.class.getSimpleName().length();
//    @Autowired
//    private ApplicationContext context;
//    @Resource
//    private RightService rightService;

    private AuthService authService;

    public XIJInterceptor(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
//
//        AbstractHandlerMethodMapping<RequestMappingInfo> objHandlerMethodMapping = (AbstractHandlerMethodMapping<RequestMappingInfo>)context.getBean("requestMappingHandlerMapping");
//        Map<RequestMappingInfo, HandlerMethod> mapRet = objHandlerMethodMapping.getHandlerMethods();
//
//        mapRet.forEach((info, method) -> {
//            String className = method.getBeanType().getName();
//            if (!className.startsWith(packageName)) {
//                return;
//            }
//            String module = className.substring(startIdx, className.length() - suffixLen);
//            String func = method.getMethod().getName();
//            Right t = new Right();
//            t.setName(module + "." + func);
//            t.setType("func");
//            rightService.save(t);
//        });

        if (!(handler instanceof HandlerMethod) || !request.getMethod().equals("POST")) {
            if (!(handler instanceof ResourceHttpRequestHandler) && !(handler instanceof HandlerMethod)) {
                LOG.info("[{}] no handle resource: {}", request.getRequestURI(), handler.getClass().getName());
            }
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        String className = handlerMethod.getBeanType().getName();
        if (!className.startsWith(packageName)) {
            return true;
        }

        String token = request.getHeader("Authorization");
        Result result = authService.parseToken(token);
        if (!result.ok()) {
            write(response, result);
            return false;
        }
        String module = className.substring(startIdx, className.length() - suffixLen);
        String func = method.getName();
        result = authService.check(module, func, request);
        if (result.ok()) {
            Pageable pageable = method.getAnnotation(Pageable.class);
            if (pageable != null) {
                PageHelper.startPage(getIntFromRequestParam(request, "pageNo", 1, false), getIntFromRequestParam(request, "limit", pageable.limit(), true));
            }
            return true;
        }
        write(response, result);
        return false;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        if (ex == null)
            return;

        /*
         * 异常处理：
         *      1、忽略客户端中断异常；
         *      2、反馈前台消息；
         *      3、打印异常栈。
         */
        if (ex instanceof ClientAbortException) {
            return;
        }

        if (ex instanceof IllegalArgumentException) {
            write(response, Result.resp(Result.CODE_PARAM, ex.getMessage()));
        } else if (ex instanceof XIJException) {
            write(response, ((XIJException) ex).toResult());
        } else {
            write(response, Result.resp(Result.CODE_SERVER, ex.getMessage()));
        }
    }

    private int getIntFromRequestParam(HttpServletRequest request, String name, int defaultVal, boolean elt) {
        try {
            String strValue = request.getParameter(name);
            if (strValue == null) {
                return defaultVal;
            } else {
                int value = Integer.parseInt(strValue, 10);
                return elt && defaultVal < value ? defaultVal : value;
            }
        } catch (Throwable t) {
            LOG.trace("{}[{}] param failed parse to integer", name, request.getParameter(name), t);
        }
        return defaultVal;
    }

    private void write(HttpServletResponse response, Result result) {
        response.reset();
        response.addHeader("Content-Type", "application/json;charset=utf-8");
        try (OutputStream out = response.getOutputStream()) {
            JSON.writeJSONString(result, out);
            out.flush();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public int getStartIdx() {
        return startIdx;
    }

    public void setStartIdx(int startIdx) {
        this.startIdx = startIdx;
    }

    public int getSuffixLen() {
        return suffixLen;
    }

    public void setSuffixLen(int suffixLen) {
        this.suffixLen = suffixLen;
    }
}