package com.gitee.fubluesky.vea.system.role.controller;

import com.gitee.fubluesky.kernel.auth.api.LoginApi;
import com.gitee.fubluesky.kernel.core.pojo.ResponseResult;
import com.gitee.fubluesky.kernel.db.mybatisplus.pojo.BaseRestController;
import com.gitee.fubluesky.vea.system.api.constants.SystemConstants;
import com.gitee.fubluesky.vea.system.api.domain.Role;
import com.gitee.fubluesky.vea.system.role.service.IRoleMenuService;
import com.gitee.fubluesky.vea.system.role.service.IRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 角色 前端控制器
 * </p>
 *
 * @author yanghq
 * @since 2020-07-01
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/role")
public class RoleController extends BaseRestController<Role, IRoleService> {

	private final IRoleMenuService roleMenuService;

	private final LoginApi loginApi;

	/**
	 * 角色列表
	 */
	@GetMapping("/select")
	public ResponseResult select() {
		Long userId = loginApi.getLoginUser().getUserId();
		// 如果不是超级管理员，则只查询自己所拥有的角色列表
		if (SystemConstants.SUPER_ADMIN_ID.equals(userId)) {
			userId = null;
		}
		List<Role> list = service.findListByUser(userId);

		return ResponseResult.success("", list);
	}

	@GetMapping("/select/{groupId}")
	public ResponseResult selectByGroupId(@PathVariable("groupId") Long groupId) {
		List<Role> list = service.findListByGroupId(groupId);

		return ResponseResult.success("", list);
	}

	/**
	 * 新增
	 * @param domain 领域模型
	 */
	@Override
	public ResponseResult save(@Valid @RequestBody Role domain) {
		Long userId = loginApi.getLoginUser().getUserId();
		domain.setCreateUserId(userId);
		return super.save(domain);
	}

	/**
	 * 修改
	 * @param domain 领域模型
	 */
	@Override
	public ResponseResult update(@Valid @RequestBody Role domain) {
		Long userId = loginApi.getLoginUser().getUserId();
		domain.setCreateUserId(userId);
		return super.update(domain);
	}

	/**
	 * 获取
	 * @param id {@code Long}
	 */
	@Override
	public ResponseResult get(@PathVariable Long id) {
		Role domain = service.get(id);

		// 查询角色对应的菜单
		List<Long> menuIdList = roleMenuService.queryMenuIdList(id);
		domain.setMenuIdList(menuIdList);

		return ResponseResult.success(domain);
	}

}
