package org.xij.web.module.sys.dept;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.xij.util.Result;
import org.xij.util.Strings;
import org.xij.web.core.AuthContext;
import org.xij.web.module.base.BaseController;

import java.util.List;

@Controller
@RequestMapping(value = "dept", method = RequestMethod.POST)
public class DeptController extends BaseController<Dept, DeptService> {
    public DeptController(DeptService deptService) {
        super(deptService);
    }

    @RequestMapping(value = "children")
    public Result queryChildren(@RequestParam(value = "pId", required = false) String pId) {
        if (Strings.isBlank(pId))
            pId = "0";
        List<Dept> DeptList = service.queryChildren(pId);
        return Result.ok(DeptList);
    }

    @Override
    public Result queryById(@PathVariable("id") String id) {
        Dept t = service.queryById(id);
        String pId = t.getpId();
        if ("0".equals(pId)) {
            t.setpId("");
        } else {
            Dept upDept = service.queryById(pId);
            t.setpId(upDept.getName());
        }
        return Result.ok(t);
    }

    @RequestMapping(value = "updateDocSeq", method = RequestMethod.POST)
    public Result updateDocSeq(int docSeq) {
        String deptId = AuthContext.get().getDeptId();
        docSeq = docSeq + 1;
        Integer row = service.updateDocSeq(docSeq, deptId);
        return Result.OK;
    }
}