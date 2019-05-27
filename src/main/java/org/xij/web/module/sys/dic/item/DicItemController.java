package org.xij.web.module.sys.dic.item;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.xij.util.Result;

import java.util.List;

@Controller
@ResponseBody
@RequestMapping(value = "dic", method = RequestMethod.POST)
public class DicItemController {
    private DicItemService service;

    public DicItemController(DicItemService service) {
        this.service = service;
    }

    @RequestMapping("{type}/{filter}")
    public Result queryDicItem(@PathVariable("type") String type, @PathVariable(value = "filter") String filter) {
        if ("N".equals(filter))
            filter = "";
        List<DicItem> data = service.queryDic(type, filter);
        return Result.ok(data);
    }
}
