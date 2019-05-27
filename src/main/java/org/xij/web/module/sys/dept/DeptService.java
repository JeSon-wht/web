package org.xij.web.module.sys.dept;

import org.xij.web.module.base.Service;
import org.xij.web.module.base.TreeService;

public interface DeptService extends Service<Dept>, TreeService<Dept> {
    Integer updateDocSeq(int docSeq,String deptId);
}
