package org.xij.web.module.sys.menu;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.xij.util.Result;
import org.xij.util.Strings;

import java.util.List;

/**
 * @author XiongKai
 * @version 0.0.1
 * @since 2016年12月6日 下午10:58:25
 */
@Controller
@RequestMapping("menu")
public class MenuController {
    private MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @ResponseBody
    @RequestMapping(value = "query", method = RequestMethod.POST)
    public Result query(Menu menu) {
        List<Menu> data = menuService.query(menu);
        return Result.ok(data);
    }

    @ResponseBody
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public Result save(Menu menu) {
        menuService.save(menu);
        return Result.ok(menu.getId());
    }

    @ResponseBody
    @RequestMapping(value = "del/{id}", method = RequestMethod.POST)
    public Result delete(@PathVariable("id") String id) {
        try {
            menuService.delete(id);
            return Result.OK;
        }catch (Throwable t){
            return Result.ok(t.getMessage());
        }
    }


    @ResponseBody
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public Result update(Menu menu) {
        menuService.update(menu);
        return Result.OK;
    }

    @ResponseBody
    @RequestMapping(value = "id/{id}", method = RequestMethod.POST)
    public Result queryById(@PathVariable("id") String id) {
        Menu menu = menuService.queryById(id);
        return Result.ok(menu);
    }

    @ResponseBody
    @RequestMapping(value = "children", method = RequestMethod.POST)
    public Result queryChildren(@RequestParam(value = "pId", required = false) String pId){
        List<Menu> MenuList = menuService.queryChildren(pId);
        return Result.ok(MenuList);
    }
}
