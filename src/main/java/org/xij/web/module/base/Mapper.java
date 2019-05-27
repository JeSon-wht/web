package org.xij.web.module.base;

import java.util.List;

public interface Mapper<T extends Entity> {
    Integer insert(T t);

    Integer update(T t);

    Integer delete(BusinessEntity t);

    List<T> query(T t);

    T queryById(String id);
}
