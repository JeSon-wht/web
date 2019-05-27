package org.xij.web.module.sys.dept;

import org.springframework.stereotype.Service;
import org.xij.util.Result;
import org.xij.util.XIJException;
import org.xij.web.module.base.ServiceImpl;

import java.util.List;

@Service
public class DeptServiceImpl extends ServiceImpl<Dept, DeptMapper> implements DeptService {
    public DeptServiceImpl(DeptMapper mapper) {
        super(mapper);
    }

    @Override
    public Integer save(Dept dept) {
        Dept cond = new Dept();
        cond.setCode(dept.getCode());
        List<Dept> depts = query(cond);
        if (depts == null || depts.isEmpty()) {
            return super.save(dept);
        }

        return XIJException.throwEx(Result.CODE_PARAM, "dept code already exists");
    }

    @Override
    public List<Dept> queryChildren(String pId) {
        return mapper.queryChildren(pId);
    }

    @Override
    public Integer updateDocSeq(int docSeq, String deptId) {
        return mapper.updateDocSeq(docSeq, deptId);
    }
}
