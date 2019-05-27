package org.xij.web.module.sys.dic.item;

import java.util.List;

public interface DicItemService {
    List<DicItem> queryDic(String type, String filter);
}
