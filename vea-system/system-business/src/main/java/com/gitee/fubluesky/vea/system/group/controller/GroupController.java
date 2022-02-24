package com.gitee.fubluesky.vea.system.group.controller;

import com.gitee.fubluesky.kernel.db.mybatisplus.pojo.BaseRestController;
import com.gitee.fubluesky.vea.system.api.domain.Group;
import com.gitee.fubluesky.vea.system.group.service.IGroupService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户组 前端控制器
 * </p>
 *
 * @author yanghq
 * @since 2021-08-17
 */
@RestController
@RequestMapping("/system/group")
public class GroupController extends BaseRestController<Group, IGroupService> {

}
