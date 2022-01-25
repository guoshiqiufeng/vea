-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        8.0.23 - MySQL Community Server - GPL
-- 服务器操作系统:                      Win64
-- HeidiSQL 版本:                  11.3.0.6295
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

-- 导出  表 vea.sys_group 结构
CREATE TABLE IF NOT EXISTS `sys_group` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `owner_id` bigint DEFAULT NULL,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `type` tinyint(1) NOT NULL COMMENT '组类型   0=系统,1=组,2=测试,3=其他',
  `create_date` datetime NOT NULL COMMENT '创建日期',
  `modify_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户组';

-- 正在导出表  vea.sys_group 的数据：~2 rows (大约)
DELETE FROM `sys_group`;
/*!40000 ALTER TABLE `sys_group` DISABLE KEYS */;
INSERT INTO `sys_group` (`id`, `owner_id`, `name`, `type`, `create_date`, `modify_date`) VALUES
	(0, NULL, '默认组', 0, '2021-02-20 11:49:52', '2021-02-24 09:14:52');
/*!40000 ALTER TABLE `sys_group` ENABLE KEYS */;

-- 导出  表 vea.sys_group_menu 结构
CREATE TABLE IF NOT EXISTS `sys_group_menu` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `group_id` bigint NOT NULL COMMENT '组id',
  `menu_id` bigint NOT NULL COMMENT '菜单id',
  `create_date` datetime NOT NULL,
  `modify_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='组菜单';

-- 正在导出表  vea.sys_group_menu 的数据：~0 rows (大约)
DELETE FROM `sys_group_menu`;
/*!40000 ALTER TABLE `sys_group_menu` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_group_menu` ENABLE KEYS */;

-- 导出  表 vea.sys_menu 结构
CREATE TABLE IF NOT EXISTS `sys_menu` (
  `menu_id` bigint NOT NULL AUTO_INCREMENT,
  `parent_id` bigint DEFAULT NULL COMMENT '父菜单ID，一级菜单为0',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `url` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `perms` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `type` int DEFAULT NULL COMMENT '类型   0：目录   1：菜单   2：按钮 3: app资源 4: 失效',
  `icon` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `order_num` int DEFAULT NULL COMMENT '排序',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `is_deleted` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=125 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='菜单管理';

-- 正在导出表  vea.sys_menu 的数据：~48 rows (大约)
DELETE FROM `sys_menu`;
/*!40000 ALTER TABLE `sys_menu` DISABLE KEYS */;
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`, `create_date`, `modify_date`, `is_deleted`) VALUES
	(1, 0, '系统管理', NULL, NULL, 0, 'menu', 0, '2020-07-03 17:00:00', '2021-02-22 09:28:59', 0),
	(2, 1, '用户管理', 'system/user', NULL, 1, 'admin', 2, '2020-07-03 17:00:00', '2022-01-21 13:40:35', 0),
	(3, 2, '查看', NULL, 'system:user:list,system:user:info', 2, NULL, 0, '2020-07-03 17:00:00', '2022-01-21 13:40:41', 0),
	(4, 2, '新增', NULL, 'system:user:save,system:role:select', 2, NULL, 0, '2020-07-03 17:00:00', '2022-01-21 13:40:48', 0),
	(5, 2, '修改', NULL, 'system:user:update,system:role:select', 2, NULL, 0, '2020-07-03 17:00:00', '2022-01-21 13:40:58', 0),
	(6, 2, '删除', NULL, 'system:user:delete', 2, NULL, 0, '2020-07-03 17:00:00', '2022-01-21 13:41:00', 0),
	(20, 1, '菜单管理', 'system/menu', NULL, 1, 'menu', 1, '2020-07-03 17:00:00', '2022-01-15 13:52:44', 0),
	(21, 20, '查看', NULL, 'system:menu:list,system:menu:info', 2, NULL, 0, '2020-07-03 17:00:00', '2022-01-15 13:52:55', 0),
	(22, 20, '新增', NULL, 'system:menu:save,system:menu:select', 2, NULL, 0, '2020-07-03 17:00:00', '2022-01-21 13:41:10', 0),
	(23, 20, '修改', NULL, 'system:menu:update,system:menu:select', 2, NULL, 0, '2020-07-03 17:00:00', '2022-01-21 13:41:19', 0),
	(24, 20, '删除', NULL, 'system:menu:delete', 2, NULL, 0, '2020-07-03 17:00:00', '2022-01-21 13:41:21', 0),
	(30, 1, '角色管理', 'system/role', NULL, 1, 'admin_role_management', 6, '2020-07-03 17:00:00', '2022-01-21 13:37:24', 0),
	(31, 30, '查看', NULL, 'core:role:list,core:role:info', 2, NULL, 0, '2020-07-03 17:00:00', '2021-02-22 09:29:20', 0),
	(32, 30, '新增', NULL, 'core:role:save,core:menu:list', 2, NULL, 0, '2020-07-03 17:00:00', '2021-02-22 09:29:20', 0),
	(33, 30, '修改', NULL, 'core:role:update,core:menu:list', 2, NULL, 0, '2020-07-03 17:00:00', '2021-02-22 09:29:20', 0),
	(34, 30, '删除', NULL, 'core:role:delete', 2, NULL, 0, '2020-07-03 17:00:00', '2021-02-22 09:29:20', 0),
	(40, 1, '用户组', 'system/group', NULL, 1, 'admin_user_group', 6, '2020-07-03 17:00:00', '2022-01-21 13:37:27', 0),
	(41, 40, '查看', NULL, 'core:group:list,core:group:info', 2, NULL, 6, '2020-07-03 17:00:00', '2021-02-22 09:29:20', 0),
	(42, 40, '新增', NULL, 'core:group:save', 2, NULL, 6, '2020-07-03 17:00:00', '2021-02-22 09:29:20', 0),
	(43, 40, '修改', NULL, 'core:group:update', 2, NULL, 6, '2020-07-03 17:00:00', '2021-02-22 09:29:20', 0),
	(44, 40, '删除', NULL, 'core:group:delete', 2, NULL, 6, '2020-07-03 17:00:00', '2021-02-22 09:29:20', 0),
	(50, 1, '组菜单', 'system/groupmenu', NULL, 1, 'admin_group_menu', 6, '2020-07-03 17:00:00', '2022-01-21 13:37:29', 0),
	(51, 50, '查看', NULL, 'core:groupmenu:list,core:groupmenu:info', 2, NULL, 6, '2020-07-03 17:00:00', '2021-02-22 09:29:20', 0),
	(52, 50, '新增', NULL, 'core:groupmenu:save', 2, NULL, 6, '2020-07-03 17:00:00', '2021-02-22 09:29:20', 0),
	(53, 50, '修改', NULL, 'core:groupmenu:update', 2, NULL, 6, '2020-07-03 17:00:00', '2021-02-22 09:29:20', 0),
	(54, 50, '删除', NULL, 'core:groupmenu:delete', 2, NULL, 6, '2020-07-03 17:00:00', '2021-02-22 09:29:20', 0),
	(70, 1, '账号库', 'system/account', NULL, 1, 'admin_account_bank', 7, '2020-07-03 17:00:00', '2022-01-21 13:37:31', 0),
	(71, 70, '查看', NULL, 'core:account:list,core:account:info', 2, NULL, 6, '2020-07-03 17:00:00', '2021-02-22 09:29:20', 0),
	(72, 70, '新增', NULL, 'core:account:save', 2, NULL, 6, '2020-07-03 17:00:00', '2021-02-22 09:29:20', 0),
	(73, 70, '修改', NULL, 'core:account:update', 2, NULL, 6, '2020-07-03 17:00:00', '2021-02-22 09:29:20', 0),
	(74, 70, '删除', NULL, 'core:account:delete', 2, NULL, 6, '2020-07-03 17:00:00', '2021-02-22 09:29:20', 0),
	(90, 1, '系统日志', 'system/log', 'core:log:list', 1, 'admin_system_log', 8, '2020-07-03 17:00:00', '2022-01-21 13:37:35', 0),
	(95, 0, '基础数据管理', '', '', 0, 'shouye', 2, '2021-02-24 13:21:35', '2022-01-25 10:16:26', 1),
	(100, 95, '学校管理', 'teaching/school', NULL, 1, 'basic_data_school_information', 6, '2021-02-24 13:21:36', '2022-01-25 10:14:15', 1),
	(101, 100, '查看', NULL, 'teaching:school:list,teaching:school:info', 2, NULL, 6, '2021-02-24 13:21:36', '2022-01-25 10:15:58', 1),
	(102, 100, '新增', NULL, 'teaching:school:save', 2, NULL, 6, '2021-02-24 13:21:37', '2022-01-25 10:15:58', 1),
	(103, 100, '修改', NULL, 'teaching:school:update', 2, NULL, 6, '2021-02-24 13:21:38', '2022-01-25 10:15:58', 1),
	(104, 100, '删除', NULL, 'teaching:school:delete', 2, NULL, 6, '2021-02-24 13:21:39', '2022-01-25 10:15:58', 1),
	(110, 95, '厂商管理', 'equipment/firm', NULL, 1, 'config', 6, '2021-02-24 13:21:40', '2022-01-25 10:16:16', 1),
	(111, 110, '查看', NULL, 'equipment:firm:list,equipment:firm:info', 2, NULL, 6, '2021-02-24 13:21:41', '2022-01-25 10:16:26', 1),
	(112, 110, '新增', NULL, 'equipment:firm:save', 2, NULL, 6, '2021-02-24 13:21:43', '2022-01-25 10:16:26', 1),
	(113, 110, '修改', NULL, 'equipment:firm:update', 2, NULL, 6, '2021-02-24 13:21:44', '2022-01-25 10:16:26', 1),
	(114, 110, '删除', NULL, 'equipment:firm:delete', 2, NULL, 6, '2021-02-24 13:21:45', '2022-01-25 10:16:26', 1),
	(120, 95, '设备管理', 'equipment/device', NULL, 1, 'config', 6, '2021-02-24 13:21:46', '2022-01-25 10:16:16', 1),
	(121, 120, '查看', NULL, 'equipment:device:list,equipment:device:info', 2, NULL, 6, '2021-02-24 13:21:48', '2022-01-25 10:16:26', 1),
	(122, 120, '新增', NULL, 'equipment:device:save', 2, NULL, 6, '2021-02-24 13:21:49', '2022-01-25 10:16:26', 1),
	(123, 120, '修改', NULL, 'equipment:device:update', 2, NULL, 6, '2021-02-24 13:21:50', '2022-01-25 10:16:26', 1),
	(124, 120, '删除', NULL, 'equipment:device:delete', 2, NULL, 6, '2021-02-24 13:21:51', '2022-01-25 10:16:26', 1);
/*!40000 ALTER TABLE `sys_menu` ENABLE KEYS */;

-- 导出  表 vea.sys_role 结构
CREATE TABLE IF NOT EXISTS `sys_role` (
  `role_id` bigint NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `group_id` bigint DEFAULT NULL COMMENT '组id',
  `code` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '角色编码',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '角色名称',
  `remark` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `default_role` tinyint(1) DEFAULT '0' COMMENT '系统默认的角色（=0）还是管理员自定义的角色（=1）如果是系统默认的角色，则不允许管理员进行修改',
  `user_type` tinyint(1) NOT NULL DEFAULT '1' COMMENT '用户类别：1：管理员',
  `create_user_id` bigint DEFAULT NULL COMMENT '创建者ID',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `modify_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`role_id`) USING BTREE,
  KEY `group_id` (`group_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色';

-- 正在导出表  vea.sys_role 的数据：~0 rows (大约)
DELETE FROM `sys_role`;
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;

-- 导出  表 vea.sys_role_menu 结构
CREATE TABLE IF NOT EXISTS `sys_role_menu` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `role_id` bigint DEFAULT NULL COMMENT '角色ID',
  `menu_id` bigint DEFAULT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色与菜单对应关系';

-- 正在导出表  vea.sys_role_menu 的数据：~0 rows (大约)
DELETE FROM `sys_role_menu`;
/*!40000 ALTER TABLE `sys_role_menu` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_role_menu` ENABLE KEYS */;

-- 导出  表 vea.sys_user 结构
CREATE TABLE IF NOT EXISTS `sys_user` (
  `user_id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户名',
  `nickname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户昵称',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '密码',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '手机号',
  `user_icon` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户头像',
  `user_type` tinyint(1) DEFAULT NULL COMMENT '用户类别 ：1 管理员； 2 普通用户',
  `status` tinyint(1) DEFAULT NULL COMMENT '状态  0：禁用   1：正常  2 : 未激活',
  `create_user_id` bigint DEFAULT NULL COMMENT '创建人id',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint DEFAULT NULL COMMENT '修改人id',
  `modify_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统用户';

-- 正在导出表  vea.sys_user 的数据：~2 rows (大约)
DELETE FROM `sys_user`;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
INSERT INTO `sys_user` (`user_id`, `username`, `nickname`, `password`, `email`, `mobile`, `user_icon`, `user_type`, `status`, `create_user_id`, `create_date`, `update_user_id`, `modify_date`) VALUES
	(1, 'admin', '超级管理员', '$2a$10$p67MtOUoGZuGIZsCSUOWneyv.a/ksydq9TdemQxY1uO3ixchUJl46', 'admin@demo.com', NULL, NULL, 1, 1, 0, '2021-02-20 10:22:39', 0, '2022-01-13 15:25:21'),
	(4, 'test', '测试', '$2a$10$u0cSyE3A/d9vUY90tassLOt09Aqw54xVVLCCSECVO/NfyzOTIsgKK', 'test', '1343', NULL, NULL, 1, NULL, '2022-01-24 13:18:23', NULL, '2022-01-24 13:18:23'),
	(6, 'ces', 'cc', '$2a$10$8lz0PPstE22/QDWbVx8U2eBk8d4WB8vnHdaEBart/e/OAjYkkvCUC', '12', '12', NULL, NULL, 1, NULL, '2022-01-24 16:43:21', NULL, '2022-01-24 16:43:21');
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;

-- 导出  表 vea.sys_user_role 结构
CREATE TABLE IF NOT EXISTS `sys_user_role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint DEFAULT NULL COMMENT '用户ID',
  `role_id` bigint DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户与角色对应关系';

-- 正在导出表  vea.sys_user_role 的数据：~0 rows (大约)
DELETE FROM `sys_user_role`;
/*!40000 ALTER TABLE `sys_user_role` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_user_role` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
