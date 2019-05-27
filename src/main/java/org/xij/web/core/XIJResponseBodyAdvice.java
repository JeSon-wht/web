package org.xij.web.core;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.xij.util.Result;

@ControllerAdvice
public class XIJResponseBodyAdvice implements org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice<Result> {
    private static final Logger LOG = LoggerFactory.getLogger(XIJResponseBodyAdvice.class);

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return Result.class.equals(returnType.getGenericParameterType());
    }

    @Override
    public Result beforeBodyWrite(Result body,
                                  MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request,
                                  ServerHttpResponse response) {
        if (body.getData() instanceof Page) {
            PageInfo<?> pageInfo = new PageInfo<>((Page<?>) body.getData());
            Result result = Result.ok(pageInfo.getList());
            result.setTotal(pageInfo.getTotal());
            result.setPageNo(pageInfo.getPageNum());
            result.setPages(pageInfo.getPages());
            return result;
        }
        return body;
    }
}