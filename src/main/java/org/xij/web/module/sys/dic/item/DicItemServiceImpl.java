package org.xij.web.module.sys.dic.item;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DicItemServiceImpl implements DicItemService {
    private DicItemMapper mapper;

    public DicItemServiceImpl(DicItemMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<DicItem> queryDic(String type, String filter) {
        return mapper.queryDic(type, filter);
    }
}
