package com.gitee.fubluesky.vea.system.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.gitee.fubluesky.kernel.auth.api.AuthApi;
import com.gitee.fubluesky.kernel.auth.api.LoginApi;
import com.gitee.fubluesky.kernel.auth.api.password.PasswordEncryptApi;
import com.gitee.fubluesky.kernel.auth.api.pojo.login.LoginRequest;
import com.gitee.fubluesky.kernel.auth.api.pojo.login.LoginResponse;
import com.gitee.fubluesky.kernel.auth.api.pojo.login.LoginUser;
import com.gitee.fubluesky.kernel.auth.api.pojo.login.UserLoginInfoDTO;
import com.gitee.fubluesky.kernel.core.exception.ServiceException;
import com.gitee.fubluesky.kernel.core.util.ListUtils;
import com.gitee.fubluesky.kernel.core.util.RandomUtil;
import com.gitee.fubluesky.kernel.db.api.pojo.page.PageResult;
import com.gitee.fubluesky.kernel.db.mybatisplus.factory.PageFactory;
import com.gitee.fubluesky.kernel.db.mybatisplus.factory.PageResultFactory;
import com.gitee.fubluesky.kernel.db.mybatisplus.pojo.BaseServiceImpl;
import com.gitee.fubluesky.kernel.file.all.utils.UploadUtils;
import com.gitee.fubluesky.kernel.file.api.utils.FileUrlUtils;
import com.gitee.fubluesky.vea.system.account.service.IAccountService;
import com.gitee.fubluesky.vea.system.api.UserServiceApi;
import com.gitee.fubluesky.vea.system.api.constants.SystemConstants;
import com.gitee.fubluesky.vea.system.api.domain.Account;
import com.gitee.fubluesky.vea.system.api.domain.Group;
import com.gitee.fubluesky.vea.system.api.domain.Role;
import com.gitee.fubluesky.vea.system.api.domain.User;
import com.gitee.fubluesky.vea.system.api.enums.RoleEnum;
import com.gitee.fubluesky.vea.system.api.enums.UserStatusEnum;
import com.gitee.fubluesky.vea.system.api.enums.UserTypeEnum;
import com.gitee.fubluesky.vea.system.api.exception.SystemException;
import com.gitee.fubluesky.vea.system.api.exception.enums.SystemExceptionEnum;
import com.gitee.fubluesky.vea.system.api.pojo.SystemProperties;
import com.gitee.fubluesky.vea.system.group.service.IGroupService;
import com.gitee.fubluesky.vea.system.menu.service.IMenuService;
import com.gitee.fubluesky.vea.system.role.service.IRoleService;
import com.gitee.fubluesky.vea.system.user.cache.UserPermissionsCache;
import com.gitee.fubluesky.vea.system.user.mapper.UserMapper;
import com.gitee.fubluesky.vea.system.user.pojo.param.LoginParam;
import com.gitee.fubluesky.vea.system.user.service.IUserRoleService;
import com.gitee.fubluesky.vea.system.user.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * <p>
 * 系统用户 服务实现类
 * </p>
 *
 * @author yanghq
 * @since 2020-07-01
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<UserMapper, User> implements IUserService, UserServiceApi {

	@Resource
	protected SystemProperties systemProperties;

	@Resource
	protected IUserRoleService userRoleService;

	@Resource
	protected IMenuService menuService;

	@Resource
	protected LoginApi loginApi;

	@Resource
	protected AuthApi authApi;

	@Resource
	protected PasswordEncryptApi passwordEncryptApi;

	@Resource
	protected UserPermissionsCache userPermissionsCache;

	@Resource
	private IGroupService groupService;

	@Resource
	private IRoleService roleService;

	@Resource
	private IAccountService accountService;

	/**
	 * 获取用户登录信息
	 * @param account 账号
	 * @return 用户登录信息
	 */
	@Override
	public UserLoginInfoDTO getUserLoginInfo(String account) {
		UserLoginInfoDTO result = new UserLoginInfoDTO();
		QueryWrapper<User> wrapper = Wrappers.query();
		wrapper.eq("username", account).or(i -> i.eq("mobile", account));

		List<User> userList = this.list(wrapper);
		if (userList == null || userList.size() == 0) {
			throw new SystemException(SystemExceptionEnum.USER_LOGIN_ERROR);
		}
		User user = userList.get(0);
		// 禁用
		if (UserStatusEnum.DISABLE.getValue() == user.getStatus()) {
			throw new SystemException(SystemExceptionEnum.USER_ACCOUNT_FORBIDDEN);
		}
		// 用户未激活
		if (UserStatusEnum.UN_ACTIVE.getValue() == user.getStatus()) {
			throw new SystemException(SystemExceptionEnum.USER_UN_ACTIVE);
		}
		result.setUserStatus(user.getStatus());
		result.setUserPassword(user.getPassword());
		String userIcon = getUserIcon(user.getUserId());
		result.setLoginUser(this.getLoginUser(user, userIcon));
		return result;
	}

	@Override
	public LoginUser getLoginUser(Long userId) {
		String userIcon = getUserIcon(userId);
		User user = this.get(userId);
		if (user == null) {
			return null;
		}
		LoginUser loginUser = this.getLoginUser(user, userIcon);
		Set<String> roles = this.getUserRole(loginUser.getUserId());
		loginUser.setRoles(roles);
		return loginUser;
	}

	@Override
	public String getUserIcon(String userIcon) {
		if (StringUtils.isEmpty(userIcon)) {
			userIcon = systemProperties.getUserIcon();
		}
		else {
			userIcon = FileUrlUtils.getFullAddress(UploadUtils.getHttpPrefix(), userIcon);
		}
		return userIcon;
	}

	/**
	 * 获取用户头像
	 * @param userId 用户id
	 * @return 用户头像
	 */
	@Override
	public String getUserIcon(Long userId) {
		User user = this.get(userId);
		if (user != null) {
			return getUserIcon(user.getUserIcon());
		}
		return systemProperties.getUserIcon();
	}

	/**
	 * 设置用户头像
	 * @param userId 用户id
	 * @param userIcon 用户头像
	 */
	@Override
	public void setUserIcon(Long userId, String userIcon) {
		String userIconTmp = FileUrlUtils.getRelativeAddress(UploadUtils.getHttpPrefix(), userIcon);
		User user = this.get(userId);
		if (user == null) {
			throw new SystemException(SystemExceptionEnum.USER_UN_EXIST);
		}
		user.setUserIcon(userIconTmp);
		this.updateById(user);
	}

	/**
	 * 用户登录，获取token
	 * @param loginParam 登录参数
	 */
	@Override
	public LoginResponse login(LoginParam loginParam) {
		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setPassword(loginParam.getPassword());
		loginRequest.setAccount(loginParam.getUsername());
		// 设置 UserService
		loginRequest.setUserServiceApi(this);

		return authApi.login(loginRequest);
	}

	@Override
	public Set<String> getUserRole(Long userId) {
		Set<String> dbAuthsSet = new HashSet<>();
		List<Long> roleIds = userRoleService.findRoleIdList(userId);
		Set<String> permissions = this.getUserPermissions(userId);
		if (ListUtils.isNotEmpty(roleIds)) {
			// 获取角色
			for (Long role : roleIds) {
				dbAuthsSet.add(SystemConstants.ROLE + role);
			}
			// 获取资源
			dbAuthsSet.addAll(permissions);
		}
		else if (SystemConstants.SUPER_ADMIN_ID.compareTo(userId) == 0) {
			dbAuthsSet.addAll(permissions);
		}
		return dbAuthsSet;
	}

	/**
	 * 通过用户名获取
	 * @param username 用户名
	 * @return 用户
	 */
	@Override
	public User getByUserName(String username) {
		return getUser(username, null);
	}

	private User getUser(String username, String mobile) {
		QueryWrapper<User> wrapper = Wrappers.query();
		wrapper.eq(StringUtils.isNotBlank(username), "username", username);
		wrapper.eq(StringUtils.isNotBlank(mobile), "mobile", mobile);
		wrapper.eq("status", UserStatusEnum.ENABLE.getValue());
		List<User> userList = this.list(wrapper);
		if (userList != null && userList.size() > 0) {
			return userList.get(0);
		}
		return null;
	}

	/**
	 * 通过手机号获取
	 * @param mobile 手机号
	 * @return 用户
	 */
	@Override
	public User getByMobile(String mobile) {
		return getUser(null, mobile);
	}

	@Override
	public Set<String> getUserPermissions(Long userId) {
		Set<String> cache = userPermissionsCache.get(userId + "");
		if (cache != null && !cache.isEmpty()) {
			return cache;
		}
		List<String> permsList;

		// 系统管理员，拥有最高权限
		if (SystemConstants.SUPER_ADMIN_ID.equals(userId)) {
			List<String> menuList = menuService.queryAllPerm();
			permsList = new ArrayList<>(menuList.size());
			permsList.addAll(menuList);
		}
		else {
			permsList = menuService.queryUserAllPerm(userId);
		}
		// 用户权限列表
		Set<String> permsSet = new HashSet<>();
		for (String perms : permsList) {
			if (StringUtils.isBlank(perms)) {
				continue;
			}
			permsSet.addAll(Arrays.asList(perms.trim().split(",")));
		}
		userPermissionsCache.add(userId + "", permsSet);
		return permsSet;
	}

	/**
	 * 删除用户权限缓存
	 * @param userId 用户id
	 */
	@Override
	public void deleteUserPermissionsCache(Long userId) {
		userPermissionsCache.delete(userId + "");
	}

	/**
	 * 退出登录
	 */
	@Override
	public void logout() {
		this.deleteUserPermissionsCache(loginApi.getLoginUser().getUserId());
		// 删除菜单导航 缓存
		menuService.deleteMenuNavCache();
		// 删除token 缓存
		authApi.logout();
	}

	/**
	 * 重置用户密码
	 * @param userId 用户id
	 */
	@Override
	public void resetPassword(Long userId) {
		User user = this.getById(userId);
		if (user == null) {
			throw new SystemException(SystemExceptionEnum.CURRENT_USER_UN_EXIST);
		}
		user.setPassword(passwordEncryptApi.encode(systemProperties.getUserPassword()));
		this.updateById(user);
	}

	/**
	 * 校验手机号时候存在
	 * @param userId
	 * @param mobile
	 */
	@Override
	public void checkMobile(Long userId, String mobile) {
		User user = this.getByMobile(mobile);
		if (user != null && !user.getUserId().equals(userId)) {
			throw new SystemException(SystemExceptionEnum.MOBILE_HAS_EXISTED);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean save(User user) {
		if (StringUtils.isNotBlank(user.getPassword())) {
			user.setPassword(passwordEncryptApi.encode(user.getPassword()));
		}
		else {
			user.setPassword(passwordEncryptApi.encode(systemProperties.getUserPassword()));
		}

		if (StringUtils.isNotBlank(user.getMobile())) {
			this.checkMobile(null, user.getMobile());
		}
		super.save(user);

		// 保存用户与角色关系
		userRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
		return true;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean update(User domain) {
		try {
			if (StringUtils.isNotBlank(domain.getPassword())) {
				domain.setPassword(passwordEncryptApi.encode(domain.getPassword()));
			}
			else {
				domain.setPassword(null);
			}
			if (StringUtils.isNotBlank(domain.getMobile())) {
				this.checkMobile(domain.getUserId(), domain.getMobile());
			}
			if (checkId(domain.getUserId())) {
				this.updateById(domain);
			}

			// 保存用户与角色关系
			userRoleService.saveOrUpdate(domain.getUserId(), domain.getRoleIdList());
			return true;
		}
		catch (ServiceException e) {
			log.error("user update error.", e);
			throw e;
		}
		catch (Exception e) {
			log.error("user update error.", e);
			throw new SystemException(SystemExceptionEnum.USER_UPDATE_ERROR);
		}
	}

	@Override
	public PageResult findPage(User domain) {
		User search = new User();
		LambdaQueryWrapper<User> wrapper = Wrappers.lambdaQuery(search);

		wrapper.like(StringUtils.isNotBlank(domain.getUsername()), User::getUsername, domain.getUsername());
		wrapper.like(StringUtils.isNotBlank(domain.getMobile()), User::getMobile, domain.getMobile());
		wrapper.eq(domain.getUserType() != null, User::getUserType, domain.getUserType());
		wrapper.eq(domain.getStatus() != null, User::getStatus, domain.getStatus());

		IPage<User> page = this.page(PageFactory.defaultPage(), wrapper);
		return PageResultFactory.createPageResult(page);
	}

	/**
	 * 批量删除
	 * @param ids id数组
	 * @return {@code boolean}
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean delete(Long[] ids) {
		Long userId = loginApi.getLoginUser().getUserId();
		if (ids != null && ids.length > 0) {
			boolean flag = false;
			for (Long id : ids) {
				if (id.compareTo(userId) == 0) {
					flag = true;
					break;
				}
			}
			if (flag) {
				throw new SystemException(SystemExceptionEnum.UN_DELETE_CURRENT_USER);
			}
			return super.delete(ids);
		}
		return true;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updatePart(User user) {
		User coreUser = this.getById(user.getUserId());
		BeanUtils.copyProperties(user, coreUser);
		this.updateById(coreUser);
		// 保存用户与角色关系
		userRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updatePassword(Long userId, String password, String newPassword) {
		User user = this.getById(userId);
		if (user == null) {
			throw new SystemException(SystemExceptionEnum.CURRENT_USER_UN_EXIST);
		}
		if (!passwordEncryptApi.matches(password, user.getPassword())) {
			throw new SystemException(SystemExceptionEnum.USER_PASSWORD_ERROR);
		}
		user.setPassword(passwordEncryptApi.encode(newPassword));
		this.updateById(user);
	}

	/**
	 * User -> LoginUser 包装
	 * @param user user
	 * @return LoginUser
	 */
	protected LoginUser getLoginUser(User user, String userIcon) {
		LoginUser loginUser = new LoginUser();
		BeanUtils.copyProperties(user, loginUser);
		loginUser.setUserIcon(userIcon);
		loginUser.setAccount(user.getUsername());
		return loginUser;
	}

	/**
	 * 获取账号
	 * @param prefix 前缀
	 * @return 账号
	 */
	private String getNewAccount(String prefix) {
		int accountLength = 6;
		StringBuilder account = new StringBuilder(prefix + "" + RandomUtil.get4Random());
		if (account.length() < accountLength) {
			for (int i = 0; i < accountLength - account.length(); i++) {
				account.append("0");
			}
		}
		String userName = account.toString();
		// 校验是否已使用
		if (this.getByUserName(userName) == null) {
			return userName;
		}
		return getNewAccount(prefix);
	}

	@Override
	public List<User> findUserList(Long id, String roleCode) {
		return this.baseMapper.findUserList(id, roleCode);
	}

	@Override
	public User createUser(Long ownerId, Long userId, String mobile, String name, String userIcon) {
		return createUser(ownerId, userId, RoleEnum.SUBJECT_TEACHER, mobile, name, userIcon);
	}

	@Override
	public User createUser(Long ownerId, Long userId, RoleEnum roleEnum, String mobile, String name, String userIcon) {
		// 校验手机号是否存在
		if (StringUtils.isNotBlank(mobile)) {
			this.checkMobile(null, mobile);
		}
		List<Long> roleIdList = new ArrayList<>();

		// 获取当前组
		List<Group> groupList = groupService.findGroups(ownerId, 3);

		List<Role> roleList = roleService.findGroupRoles(groupList.get(0).getId(), roleEnum);

		// 获取系统分配的角色
		Role role = roleList.get(0);
		roleIdList.add(role.getRoleId());

		Account accountEntity = accountService.getNewAccount();

		User userEntity = new User();
		userEntity.setUsername(accountEntity.getUserName());
		userEntity.setPassword(systemProperties.getUserPassword());
		userEntity.setMobile(mobile);
		userEntity.setCreateUserId(userId);
		userEntity.setStatus(UserStatusEnum.ENABLE.getValue());
		userEntity.setNickname(name);
		userEntity.setUserIcon(userIcon);
		userEntity.setUserType(UserTypeEnum.TEACHER.getValue());
		userEntity.setRoleIdList(roleIdList);
		this.save(userEntity);

		return userEntity;
	}

	@Override
	public void updateUserData(Long userId, String mobile, UserStatusEnum statusEnum, String nickName,
			String userIcon) {
		// 校验手机号是否存在
		if (StringUtils.isNotBlank(mobile)) {
			this.checkMobile(userId, mobile);
		}

		User userEntity = this.getById(userId);
		userEntity.setMobile(mobile.trim());
		userEntity.setStatus(statusEnum.getValue());
		userEntity.setNickname(nickName);
		userEntity.setUserIcon(FileUrlUtils.getRelativeAddress(UploadUtils.getHttpPrefix(), userIcon));
		this.updateById(userEntity);
		loginApi.deleteLoginUserCache(userId);
	}

	@Override
	public void updateUserStatus(Long userId, UserStatusEnum statusEnum) {
		// 禁用用户
		User userEntity = new User();
		userEntity.setStatus(statusEnum.getValue());
		this.update(userEntity, new QueryWrapper<User>().eq("user_id", userId));
		loginApi.deleteLoginUserCache(userId);
	}

}
