package org.xij.web.core;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.xij.extension.ExtensionLoader;
import org.xij.serialize.Serialization;
import org.xij.serialize.Serializations;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
@PropertySource("classpath:config.properties")
public class WebMvcConfiguration implements WebMvcConfigurer {
    private XIJInterceptor interceptor;
    @Value("${skipURI}")
    private String skipURI;

    public WebMvcConfiguration(
            XIJInterceptor interceptor,
            @Value("${jwt.secret}") String secret,
            @Value("${serializer}") String serializer
    ) {
        this.interceptor = interceptor;
        Jwts.init(secret);

        Serialization serialization = ExtensionLoader.getExtensionLoader(Serialization.class).getExtension(serializer);
        Serializations.setSerialization(serialization);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor).excludePathPatterns(skipURI.split(","));
        registry.addInterceptor(new HandlerInterceptor() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                response.addHeader("Access-Control-Allow-Origin", "*");
                return true;
            }

            @Override
            public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

            }

            @Override
            public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

            }
        });
    }
}
