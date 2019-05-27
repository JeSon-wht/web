package org.xij.web.module.base;

import java.util.List;

public interface TreeService<T extends TreeEntity> {
    List<T> queryChildren(String pId);
}
