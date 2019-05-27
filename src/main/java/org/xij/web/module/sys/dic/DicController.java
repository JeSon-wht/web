package org.xij.web.module.sys.dic;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.xij.util.Result;
import org.xij.util.Strings;
import org.xij.web.module.base.BaseController;

import java.util.List;

/**
 * @author XiongKai
 * @version 0.0.1
 * @since 2016年11月20日 下午12:25:56
 */
@Controller
@RequestMapping("dic")
public class DicController extends BaseController<Dic, DicService> {
    public DicController(DicService dicService) {
        super(dicService);
    }

    @RequestMapping(value = "children",method = RequestMethod.POST)
    public Result queryChildren(@RequestParam(value = "pId", required = false) String pId){
        if (Strings.isBlank(pId))
            pId = "0";
        List<Dic> dicList = service.queryChildren(pId);
        return Result.ok(dicList);
    }
}
