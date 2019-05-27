package org.xij.web.module.base;

import org.springframework.transaction.annotation.Transactional;
import org.xij.util.XIJException;
import org.xij.util.Id;
import org.xij.util.Result;
import org.xij.util.StringManager;
import org.xij.web.core.AuthContext;
import org.xij.web.core.AuthInfo;

import java.util.Date;
import java.util.List;

public class ServiceImpl<T extends Entity, M extends Mapper<T>> implements Service<T> {
    private static final StringManager MANAGER = StringManager.getManager(ServiceImpl.class);
    protected final M mapper;

    public ServiceImpl(M mapper) {
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public Integer update(T t) {
        if (t instanceof BusinessEntity) {
            BusinessEntity entity = (BusinessEntity) t;
            AuthInfo authInfo = AuthContext.get();
            entity.setUpdateBy(authInfo.getUserId());
            entity.setUpdateDept(authInfo.getDeptId());
            entity.setUpdateTime(new Date());
        }
        Integer num = mapper.update(t);
        if (num != 1) {
            XIJException.throwEx(Result.CODE_DATA_UPDATE, MANAGER.getString("update", num));
        }
        return num;
    }

    @Override
    @Transactional
    public Integer delete(String id) {
        BusinessEntity cond = new BusinessEntity(id);
        AuthInfo authInfo = AuthContext.get();
        cond.setUpdateBy(authInfo.getUserId());
        cond.setUpdateDept(authInfo.getDeptId());
        cond.setUpdateTime(new Date());
        return mapper.delete(cond);
    }

    @Override
    public List<T> query(T t) {
        return mapper.query(t);
    }

    @Override
    public T queryById(String id) {
        return mapper.queryById(id);
    }

    @Override
    @Transactional
    public Integer save(T t) {
        if (t.getId() != null) {
            XIJException.throwEx(Result.CODE_PARAM, MANAGER.getString("pk", t.getClass().getName(), t.getId()));
        }
        t.setId(Id.generateId());
        if (t instanceof BusinessEntity) {
            BusinessEntity entity = (BusinessEntity) t;
            AuthInfo authInfo = AuthContext.get();
            entity.setCreateBy(authInfo.getUserId());
            entity.setCreateDept(authInfo.getDeptId());
        }
        return mapper.insert(t);
    }
}
