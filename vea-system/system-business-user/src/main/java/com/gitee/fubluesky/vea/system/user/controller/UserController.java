package com.gitee.fubluesky.vea.system.user.controller;

import com.gitee.fubluesky.kernel.auth.api.LoginApi;
import com.gitee.fubluesky.kernel.core.pojo.ResponseResult;
import com.gitee.fubluesky.kernel.db.mybatisplus.pojo.BaseRestController;
import com.gitee.fubluesky.vea.system.api.domain.Group;
import com.gitee.fubluesky.vea.system.api.domain.User;
import com.gitee.fubluesky.vea.system.group.constants.GroupConstant;
import com.gitee.fubluesky.vea.system.group.service.IGroupService;
import com.gitee.fubluesky.vea.system.user.pojo.param.UpdatePasswordParam;
import com.gitee.fubluesky.vea.system.user.pojo.vo.UserVO;
import com.gitee.fubluesky.vea.system.user.service.IUserRoleService;
import com.gitee.fubluesky.vea.system.user.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/***
 * <p>
 * *系统用户 前端控制器*
 * </p>
 * **
 *
 * @author yanghq
 * @since 2020-07-01
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/user")
public class UserController extends BaseRestController<User, IUserService> {

	private final IUserRoleService coreUserRoleService;

	private final LoginApi loginApi;

	private final IGroupService groupService;

	/**
	 * 获取登录的用户信息
	 */
	@GetMapping("/info")
	public ResponseResult info() {
		Long userId = loginApi.getLoginUser().getUserId();
		User user = service.get(userId);
		UserVO coreUserVO = new UserVO();
		BeanUtils.copyProperties(user, coreUserVO);
		coreUserVO.setUserIcon(service.getUserIcon(userId));
		coreUserVO.setPassword(null);

		// 查询用户学校信息
		List<Group> list = groupService.findUserGroups(user.getUserId(), GroupConstant.OTHER);
		if (list != null && list.size() > 0) {
			Long[] schoolIds = new Long[list.size()];
			for (int i = 0; i < list.size(); i++) {
				schoolIds[i] = list.get(i).getOwnerId();
			}
			coreUserVO.setSchoolIds(schoolIds);
		}

		return ResponseResult.success("", coreUserVO);
	}

	/**
	 * 修改密码
	 */
	@PostMapping("/password")
	public ResponseResult password(@Valid @RequestBody UpdatePasswordParam param) {
		service.updatePassword(loginApi.getLoginUser().getUserId(), param.getPassword(), param.getNewPassword());

		return ResponseResult.success();
	}

	/**
	 * 重置密码
	 */
	@PostMapping("/resetPassword")
	public ResponseResult updatePassword(@RequestBody User user) {
		service.resetPassword(user.getUserId());

		return ResponseResult.success();
	}

	@PostMapping("/updatePart")
	public ResponseResult updatePart(@RequestBody User user) {
		service.updatePart(user);

		return ResponseResult.success();
	}

	/**
	 * 获取
	 * @param id {@code Long}
	 * @return {@link ResponseResult}
	 */
	@Override
	public ResponseResult get(@PathVariable Long id) {
		User domain = service.get(id);
		// 获取用户所属的角色列表
		List<Long> roleIdList = coreUserRoleService.findRoleIdList(id);
		domain.setRoleIdList(roleIdList);
		return ResponseResult.success(domain);
	}

}
