package com.gitee.fubluesky.vea.system.menu.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.gitee.fubluesky.kernel.core.pojo.ResponseResult;
import com.gitee.fubluesky.kernel.db.mybatisplus.pojo.BaseRestController;
import com.gitee.fubluesky.vea.system.api.domain.Menu;
import com.gitee.fubluesky.vea.system.menu.service.IMenuService;
import com.gitee.fubluesky.vea.system.menu.vo.MenuNavVO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 菜单管理 前端控制器
 * </p>
 *
 * @author yanghq
 * @since 2020-07-01
 */
@RestController
@RequestMapping("/system/menu")
public class MenuController extends BaseRestController<Menu, IMenuService> {

	/**
	 * 导航菜单
	 */
	@GetMapping("/nav")
	public ResponseResult nav() {
		MenuNavVO result = service.getNavData();
		return ResponseResult.success(result);
	}

	/**
	 * 所有组菜单列表
	 */
	@RequestMapping("/groupMenuList/{id}")
	public ResponseResult groupMenuList(@PathVariable("id") Long id) {
		List<? extends Menu> menuList = service.getGroupMenuList(id);

		return ResponseResult.success("", menuList);
	}

	/**
	 * 选择菜单(添加、修改菜单)
	 */
	@GetMapping("/select")
	public ResponseResult select() {
		// 查询列表数据
		List<Menu> menuList = service.queryNotButtonList();

		// 添加顶级菜单
		Menu root = new Menu();
		root.setMenuId(0L);
		root.setName("一级菜单");
		root.setParentId(-1L);
		root.setOpen(true);
		menuList.add(root);

		return ResponseResult.success(menuList);
	}

	/**
	 * 列表查询
	 * @param domain 参数
	 */
	@Override
	public ResponseResult list(@ModelAttribute Menu domain) {
		List<Menu> list = service.list(new LambdaQueryWrapper<Menu>(domain).orderByAsc(Menu::getOrderNum));
		return ResponseResult.success(list);
	}

}
