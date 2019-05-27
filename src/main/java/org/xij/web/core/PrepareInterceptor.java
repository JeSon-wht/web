package org.xij.web.core;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.xij.web.module.base.BusinessEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@Intercepts({
        @Signature(type = Executor.class, method = "createCacheKey", args = {MappedStatement.class, Object.class, RowBounds.class, BoundSql.class}),
})
@Component
public class PrepareInterceptor implements Interceptor, InitializingBean {
    private Map<String, ParameterValue> params = new HashMap<>();

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();
        BoundSql boundSql = (BoundSql) args[3];
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        for (ParameterMapping parameterMapping : parameterMappings) {
            String property = parameterMapping.getProperty();
            ParameterValue value = params.get(property);
            if (null == value)
                continue;
            Page<Object> page = PageHelper.getLocalPage();
            if (null != page)
                PageHelper.clearPage();
            boundSql.setAdditionalParameter(property, value.getValue());
            if (null != page)
                PageHelper.startPage(page.getPageNum(), page.getPageSize());
        }
        return invocation.proceed();
    }

    @Override
    public void afterPropertiesSet() {
        params.put("USER", () -> AuthContext.get().getUserId());
        params.put("DEPT", () -> AuthContext.get().getDeptId());
        params.put("RDEPT", () -> AuthContext.get().getRightDeptCode());
        params.put("RDEPTID", () -> AuthContext.get().getRightDeptId());
        params.put("STATUS_NORMAL", () -> BusinessEntity.STATUS_NORMAL);
        params.put("STATUS_DELETE", () -> BusinessEntity.STATUS_DELETED);
    }

    public interface ParameterValue {
        Object getValue();
    }
}