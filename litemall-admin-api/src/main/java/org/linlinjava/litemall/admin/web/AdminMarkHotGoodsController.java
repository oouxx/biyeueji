package org.linlinjava.litemall.admin.web;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.linlinjava.litemall.admin.annotation.RequiresPermissionsDesc;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.service.MarkHotGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/task")
@Validated
public class AdminMarkHotGoodsController {
    @Autowired
    private MarkHotGoodsService markHotGoodsService;
    /**
     * 标记热门商品
     *
      */
    @RequiresPermissions("admin:task:markHotGoods")
    @RequiresPermissionsDesc(menu = {"商品管理", "商品管理"}, button = "标记热门商品")
    @GetMapping("/markHotGoods")
    public Object markHotGoods(){
       markHotGoodsService.markHotGoods();
       return ResponseUtil.ok();
    }
}
