package org.xij.web.module.sys.right;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.xij.web.module.base.BaseController;

@Controller
@RequestMapping("right")
public class RightController extends BaseController<Right, RightService> {
    public RightController(RightService rightService){
        super(rightService);
    }
}
