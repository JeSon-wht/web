package org.xij.web.module.base;

import java.util.List;

public interface Service<T extends Entity> {

    Integer update(T t);

    Integer delete(String id);

    List<T> query(T t);

    T queryById(String id);

    Integer save(T t);
}
