package org.xij.web.module.base;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.xij.annotation.Pageable;
import org.xij.util.Result;

import java.util.List;

@ResponseBody
public class BaseController<T extends Entity, S extends Service<T>> {
    protected S service;

    public BaseController(S service) {
        this.service = service;
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    public Result save(T entity) {
        Integer num = service.save(entity);
        return num == 1 ? Result.ok(entity.getId()) : Result.resp(Result.CODE_DATA_ADD, num);
    }

    @RequestMapping(value = "del/{id}", method = RequestMethod.POST)
    public Result delete(@PathVariable("id") String id) {
        Integer num = service.delete(id);
        return num == 1 ? Result.OK : Result.resp(Result.CODE_DATA_DEL, num);
    }

    @Pageable
    @RequestMapping(value = "query", method = RequestMethod.POST)
    public Result query(T entity) {
        List<T> data = service.query(entity);
        return Result.ok(data);
    }

    @RequestMapping(value = "id/{id}", method = RequestMethod.POST)
    public Result queryById(@PathVariable("id") String id) {
        T entity = service.queryById(id);
        return Result.ok(entity);
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    public Result update(T entity) {
        service.update(entity);
        return Result.OK;
    }
}
