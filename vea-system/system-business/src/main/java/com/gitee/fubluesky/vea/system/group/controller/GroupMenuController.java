package com.gitee.fubluesky.vea.system.group.controller;

import com.gitee.fubluesky.kernel.core.pojo.ResponseResult;
import com.gitee.fubluesky.kernel.db.mybatisplus.pojo.BaseRestController;
import com.gitee.fubluesky.vea.system.group.service.IGroupMenuService;
import com.google.common.collect.Maps;
import com.gitee.fubluesky.vea.system.api.domain.GroupMenu;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 组菜单 前端控制器
 * </p>
 *
 * @author yanghq
 * @since 2021-08-17
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/groupMenu")
public class GroupMenuController extends BaseRestController<GroupMenu, IGroupMenuService> {

	/**
	 * 组信息
	 */
	@GetMapping("/groupInfo/{groupId}")
	public ResponseResult groupInfo(@PathVariable("groupId") Long groupId) {
		HashMap<String, Object> map = Maps.newHashMap();
		List<Long> menuIdList = service.queryMenuIdList(groupId);
		map.put("groupId", groupId);
		map.put("menuIdList", menuIdList);
		return ResponseResult.success("", map);
	}

	/**
	 * 新增
	 * @param domain 领域模型
	 * @return {@link ResponseResult}
	 */
	@Override
	public ResponseResult save(@Valid @RequestBody GroupMenu domain) {
		service.saveGroupMenu(domain);
		return ResponseResult.success("保存成功");
	}

}
