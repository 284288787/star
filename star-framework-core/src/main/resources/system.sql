/*
Navicat MySQL Data Transfer

Source Server         : 47.94.0.136
Source Server Version : 50716
Source Host           : 47.94.0.136:3306
Source Database       : framework

Target Server Type    : MYSQL
Target Server Version : 50716
File Encoding         : 65001

Date: 2018-07-31 11:29:38
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for mgr_coach
-- ----------------------------
DROP TABLE IF EXISTS `mgr_coach`;
CREATE TABLE `mgr_coach` (
  `userId` bigint(20) NOT NULL,
  `sex` tinyint(1) NOT NULL,
  `birthTime` datetime NOT NULL,
  `certificateType` tinyint(1) NOT NULL,
  `certificateCode` varchar(50) NOT NULL,
  `address` varchar(100) NOT NULL,
  `emergencyContact` varchar(10) NOT NULL,
  `emergencyContactNumber` varchar(20) NOT NULL,
  `emergencyContactAddress` varchar(100) NOT NULL,
  `educationBackground` varchar(20) NOT NULL,
  `school` varchar(20) NOT NULL,
  `specialty` varchar(20) NOT NULL,
  `professionalService` tinyint(1) NOT NULL,
  `workExperience` text NOT NULL,
  `awardResume` text,
  `mobile` varchar(11) DEFAULT NULL,
  `name` varchar(100) NOT NULL,
  `identity` tinyint(1) NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  `deleted` tinyint(1) NOT NULL,
  `beforeDeletedMobile` varchar(11) NOT NULL,
  `createTime` datetime NOT NULL,
  `updateTime` datetime NOT NULL,
  PRIMARY KEY (`userId`),
  UNIQUE KEY `memberId` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of mgr_coach
-- ----------------------------
INSERT INTO `mgr_coach` VALUES ('333', '1', '2018-07-16 10:19:33', '1', '6354654651', 'rf打', '阿斯蒂芬撒旦', '134352312323', '大三房', '123', '123', '1', '1', '123', '123', '13142222430', 'sdf阿斯顿法', '1', '1', '0', '13142222430', '2018-07-16 10:20:45', '2018-07-16 10:20:47');

-- ----------------------------
-- Table structure for sys_resource
-- ----------------------------
DROP TABLE IF EXISTS `sys_resource`;
CREATE TABLE `sys_resource` (
  `sourceId` bigint(20) NOT NULL COMMENT '资源id',
  `sourceName` varchar(50) NOT NULL COMMENT '资源名称',
  `sourceIcoCls` varchar(20) DEFAULT NULL COMMENT '如果是菜单，并且有图标，则为图标的class',
  `parentId` bigint(20) NOT NULL DEFAULT '0' COMMENT '父',
  `sourceUrl` varchar(200) DEFAULT NULL COMMENT 'url',
  `enabled` tinyint(1) NOT NULL COMMENT '是否可用 1可用 0禁用',
  `idx` int(10) NOT NULL,
  PRIMARY KEY (`sourceId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sys_resource
-- ----------------------------
INSERT INTO `sys_resource` VALUES ('1', '惠农后台管理系统', 'defaultmenuico', '0', null, '1', '1');
INSERT INTO `sys_resource` VALUES ('2', '系统', 'nav-cost', '1', null, '1', '1');
INSERT INTO `sys_resource` VALUES ('3', '用户管理', null, '2', null, '1', '1');
INSERT INTO `sys_resource` VALUES ('4', '系统用户', 'item', '3', null, '1', '1');
INSERT INTO `sys_resource` VALUES ('5', '系统资源', 'item', '3', null, '1', '5');
INSERT INTO `sys_resource` VALUES ('6', '系统角色', '', '3', null, '1', '6');
INSERT INTO `sys_resource` VALUES ('20', '教练员', 'nav-marketing', '1', null, '1', '2');
INSERT INTO `sys_resource` VALUES ('21', '教练员管理', '', '20', null, '1', '1');
INSERT INTO `sys_resource` VALUES ('23', '教练员列表', '', '21', null, '1', '1');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `roleId` bigint(20) NOT NULL COMMENT '角色id',
  `roleName` varchar(30) NOT NULL COMMENT '角色名称',
  `roleRemark` varchar(255) NOT NULL COMMENT '角色备注',
  `roleIntro` varchar(255) DEFAULT NULL,
  `createTime` datetime NOT NULL COMMENT '创建时间',
  `createUser` varchar(20) NOT NULL COMMENT '创建用户',
  PRIMARY KEY (`roleId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('8', 'ADMIN', '超级管理员', null, '2017-05-25 10:27:58', 'lh');
INSERT INTO `sys_role` VALUES ('9', 'USER', '普通用户', 'fadfasfsaffd', '2017-05-25 10:59:42', 'lh');

-- ----------------------------
-- Table structure for sys_role_source_relation
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_source_relation`;
CREATE TABLE `sys_role_source_relation` (
  `roleId` bigint(20) NOT NULL,
  `sourceId` bigint(20) NOT NULL,
  `uri` varchar(80) NOT NULL,
  `mainUri` tinyint(1) NOT NULL,
  `createTime` datetime NOT NULL,
  `createUser` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sys_role_source_relation
-- ----------------------------
INSERT INTO `sys_role_source_relation` VALUES ('8', '4', '/role/setRole/*', '0', '2018-07-28 11:28:10', 'anonymousUser');
INSERT INTO `sys_role_source_relation` VALUES ('8', '4', '/role/viewRole/*', '0', '2018-07-28 11:28:12', 'anonymousUser');
INSERT INTO `sys_role_source_relation` VALUES ('8', '4', '/userAccount/add', '0', '2018-07-28 11:28:13', 'anonymousUser');
INSERT INTO `sys_role_source_relation` VALUES ('8', '4', '/userAccount/changePassword', '0', '2018-07-28 11:28:14', 'anonymousUser');
INSERT INTO `sys_role_source_relation` VALUES ('8', '4', '/userAccount/deleted', '0', '2018-07-28 11:28:15', 'anonymousUser');
INSERT INTO `sys_role_source_relation` VALUES ('8', '4', '/userAccount/disabled', '0', '2018-07-28 11:28:15', 'anonymousUser');
INSERT INTO `sys_role_source_relation` VALUES ('8', '4', '/userAccount/edit', '0', '2018-07-28 11:28:16', 'anonymousUser');
INSERT INTO `sys_role_source_relation` VALUES ('8', '4', '/userAccount/editBefore/*', '0', '2018-07-28 11:28:16', 'anonymousUser');
INSERT INTO `sys_role_source_relation` VALUES ('8', '4', '/userAccount/enabled', '0', '2018-07-28 11:28:17', 'anonymousUser');
INSERT INTO `sys_role_source_relation` VALUES ('8', '4', '/userAccount/list', '0', '2018-07-28 11:28:17', 'anonymousUser');
INSERT INTO `sys_role_source_relation` VALUES ('8', '4', '/userAccount/lists', '1', '2018-07-28 11:28:18', 'anonymousUser');
INSERT INTO `sys_role_source_relation` VALUES ('8', '4', '/userAccount/locked', '0', '2018-07-28 11:28:19', 'anonymousUser');
INSERT INTO `sys_role_source_relation` VALUES ('8', '4', '/userAccount/unlock', '0', '2018-07-28 11:28:19', 'anonymousUser');
INSERT INTO `sys_role_source_relation` VALUES ('8', '4', '/userInfo/edit', '0', '2018-07-28 11:28:19', 'anonymousUser');
INSERT INTO `sys_role_source_relation` VALUES ('8', '4', '/userInfo/editBefore/*', '0', '2018-07-28 11:28:20', 'anonymousUser');
INSERT INTO `sys_role_source_relation` VALUES ('8', '3', '/,/bui/**,/common/mgr/**,/changeMinePass', '0', '2018-07-28 11:50:05', 'anonymousUser');
INSERT INTO `sys_role_source_relation` VALUES ('8', '2', '/,/bui/**,/common/mgr/**,/changeMinePass', '0', '2018-07-28 11:50:06', 'anonymousUser');
INSERT INTO `sys_role_source_relation` VALUES ('8', '1', '/,/bui/**,/common/mgr/**,/changeMinePass', '0', '2018-07-28 11:50:06', 'anonymousUser');
INSERT INTO `sys_role_source_relation` VALUES ('9', '1', '/,/bui/**,/common/mgr/**,/changeMinePass', '0', '2018-07-28 11:50:09', 'anonymousUser');
INSERT INTO `sys_role_source_relation` VALUES ('8', '5', '/,/bui/**,/common/mgr/**,/changeMinePass', '0', '2018-07-28 16:45:51', 'anonymousUser');
INSERT INTO `sys_role_source_relation` VALUES ('8', '5', '/resource/editBefore/*', '0', '2018-07-28 16:45:51', 'anonymousUser');
INSERT INTO `sys_role_source_relation` VALUES ('8', '5', '/resource/saveResourceUri', '0', '2018-07-28 16:45:52', 'anonymousUser');
INSERT INTO `sys_role_source_relation` VALUES ('8', '5', '/resource/uris', '0', '2018-07-28 16:45:52', 'anonymousUser');
INSERT INTO `sys_role_source_relation` VALUES ('8', '6', '/,/bui/**,/common/mgr/**,/changeMinePass', '0', '2018-07-28 16:45:54', 'anonymousUser');
INSERT INTO `sys_role_source_relation` VALUES ('8', '6', '/role/add', '0', '2018-07-28 16:45:54', 'anonymousUser');
INSERT INTO `sys_role_source_relation` VALUES ('8', '6', '/role/edit', '0', '2018-07-28 16:45:55', 'anonymousUser');
INSERT INTO `sys_role_source_relation` VALUES ('8', '6', '/role/editBefore/*', '0', '2018-07-28 16:45:55', 'anonymousUser');
INSERT INTO `sys_role_source_relation` VALUES ('8', '6', '/role/list', '0', '2018-07-28 16:45:55', 'anonymousUser');
INSERT INTO `sys_role_source_relation` VALUES ('8', '6', '/role/mainUri/*', '0', '2018-07-28 16:45:56', 'anonymousUser');
INSERT INTO `sys_role_source_relation` VALUES ('8', '6', '/role/resources/*', '0', '2018-07-28 16:45:57', 'anonymousUser');
INSERT INTO `sys_role_source_relation` VALUES ('8', '6', '/role/saveRoleRelation', '0', '2018-07-28 16:45:57', 'anonymousUser');
INSERT INTO `sys_role_source_relation` VALUES ('8', '6', '/role/saveRoleResourceRelation', '0', '2018-07-28 16:45:57', 'anonymousUser');
INSERT INTO `sys_role_source_relation` VALUES ('8', '6', '/role/setMainUri', '0', '2018-07-28 16:45:58', 'anonymousUser');
INSERT INTO `sys_role_source_relation` VALUES ('8', '5', '/resource/add', '0', '2018-07-28 16:55:42', 'anonymousUser');
INSERT INTO `sys_role_source_relation` VALUES ('8', '5', '/resource/delete/*', '0', '2018-07-28 16:55:43', 'anonymousUser');
INSERT INTO `sys_role_source_relation` VALUES ('8', '5', '/resource/edit', '0', '2018-07-28 16:55:43', 'anonymousUser');
INSERT INTO `sys_role_source_relation` VALUES ('8', '5', '/resource/resources', '0', '2018-07-28 16:55:45', 'anonymousUser');
INSERT INTO `sys_role_source_relation` VALUES ('8', '5', '/resource/lists', '1', '2018-07-28 16:55:45', 'anonymousUser');
INSERT INTO `sys_role_source_relation` VALUES ('8', '6', '/role/lists', '1', '2018-07-28 16:55:47', 'anonymousUser');
INSERT INTO `sys_role_source_relation` VALUES ('8', '20', '/,/bui/**,/common/mgr/**,/changeMinePass', '0', '2018-07-28 17:03:08', '18610925630');
INSERT INTO `sys_role_source_relation` VALUES ('8', '21', '/,/bui/**,/common/mgr/**,/changeMinePass', '0', '2018-07-28 17:03:08', '18610925630');
INSERT INTO `sys_role_source_relation` VALUES ('8', '23', '/,/bui/**,/common/mgr/**,/changeMinePass', '0', '2018-07-28 17:03:09', '18610925630');
INSERT INTO `sys_role_source_relation` VALUES ('8', '23', '/coach/getCoach', '1', '2018-07-28 17:03:10', '18610925630');
INSERT INTO `sys_role_source_relation` VALUES ('9', '20', '/,/bui/**,/common/mgr/**,/changeMinePass', '0', '2018-07-28 17:03:19', '18610925630');
INSERT INTO `sys_role_source_relation` VALUES ('9', '21', '/,/bui/**,/common/mgr/**,/changeMinePass', '0', '2018-07-28 17:03:19', '18610925630');
INSERT INTO `sys_role_source_relation` VALUES ('9', '23', '/,/bui/**,/common/mgr/**,/changeMinePass', '0', '2018-07-30 14:25:22', '18610925630');
INSERT INTO `sys_role_source_relation` VALUES ('8', '6', '/role/deleted', '0', '2018-07-31 10:19:44', '18610925630');
INSERT INTO `sys_role_source_relation` VALUES ('8', '6', '/role/viewResource/*', '0', '2018-07-31 10:39:56', '18610925630');
INSERT INTO `sys_role_source_relation` VALUES ('9', '23', '/coach/getCoach', '1', '2018-07-31 10:40:38', '18610925630');

-- ----------------------------
-- Table structure for sys_uri
-- ----------------------------
DROP TABLE IF EXISTS `sys_uri`;
CREATE TABLE `sys_uri` (
  `uri` varchar(80) NOT NULL,
  `sourceId` bigint(20) DEFAULT NULL,
  `intro` varchar(50) DEFAULT NULL,
  `createTime` datetime NOT NULL,
  PRIMARY KEY (`uri`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sys_uri
-- ----------------------------
INSERT INTO `sys_uri` VALUES ('/coach/getCoach', null, null, '2018-07-31 11:17:08');
INSERT INTO `sys_uri` VALUES ('/resource/add', '5', null, '2018-07-31 11:17:08');
INSERT INTO `sys_uri` VALUES ('/resource/delete/*', '5', null, '2018-07-31 11:17:08');
INSERT INTO `sys_uri` VALUES ('/resource/edit', '5', null, '2018-07-31 11:17:08');
INSERT INTO `sys_uri` VALUES ('/resource/editBefore/*', '5', null, '2018-07-31 11:17:08');
INSERT INTO `sys_uri` VALUES ('/resource/lists', '5', null, '2018-07-31 11:17:08');
INSERT INTO `sys_uri` VALUES ('/resource/resources', '5', null, '2018-07-31 11:17:08');
INSERT INTO `sys_uri` VALUES ('/resource/saveResourceUri', '5', null, '2018-07-31 11:17:08');
INSERT INTO `sys_uri` VALUES ('/resource/uris', '5', null, '2018-07-31 11:17:08');
INSERT INTO `sys_uri` VALUES ('/role/add', '6', null, '2018-07-31 11:17:08');
INSERT INTO `sys_uri` VALUES ('/role/deleted', '6', null, '2018-07-31 11:17:08');
INSERT INTO `sys_uri` VALUES ('/role/edit', '6', null, '2018-07-31 11:17:08');
INSERT INTO `sys_uri` VALUES ('/role/editBefore/*', '6', null, '2018-07-31 11:17:08');
INSERT INTO `sys_uri` VALUES ('/role/list', '6', null, '2018-07-31 11:17:08');
INSERT INTO `sys_uri` VALUES ('/role/lists', '6', null, '2018-07-31 11:17:08');
INSERT INTO `sys_uri` VALUES ('/role/mainUri/*', '6', null, '2018-07-31 11:17:08');
INSERT INTO `sys_uri` VALUES ('/role/resources/*', '6', null, '2018-07-31 11:17:08');
INSERT INTO `sys_uri` VALUES ('/role/saveRoleRelation', '6', null, '2018-07-31 11:17:08');
INSERT INTO `sys_uri` VALUES ('/role/saveRoleResourceRelation', '6', null, '2018-07-31 11:17:08');
INSERT INTO `sys_uri` VALUES ('/role/setMainUri', '6', null, '2018-07-31 11:17:08');
INSERT INTO `sys_uri` VALUES ('/role/setRole/*', '4', null, '2018-07-31 11:17:08');
INSERT INTO `sys_uri` VALUES ('/role/viewResource/*', '6', null, '2018-07-31 11:17:08');
INSERT INTO `sys_uri` VALUES ('/role/viewRole/*', '4', null, '2018-07-31 11:17:08');
INSERT INTO `sys_uri` VALUES ('/userAccount/add', '4', null, '2018-07-31 11:17:08');
INSERT INTO `sys_uri` VALUES ('/userAccount/changePassword', '4', null, '2018-07-31 11:17:08');
INSERT INTO `sys_uri` VALUES ('/userAccount/deleted', '4', null, '2018-07-31 11:17:08');
INSERT INTO `sys_uri` VALUES ('/userAccount/disabled', '4', null, '2018-07-31 11:17:08');
INSERT INTO `sys_uri` VALUES ('/userAccount/edit', '4', null, '2018-07-31 11:17:08');
INSERT INTO `sys_uri` VALUES ('/userAccount/editBefore/*', '4', null, '2018-07-31 11:17:08');
INSERT INTO `sys_uri` VALUES ('/userAccount/enabled', '4', null, '2018-07-31 11:17:08');
INSERT INTO `sys_uri` VALUES ('/userAccount/list', '4', null, '2018-07-31 11:17:08');
INSERT INTO `sys_uri` VALUES ('/userAccount/lists', '4', null, '2018-07-31 11:17:08');
INSERT INTO `sys_uri` VALUES ('/userAccount/locked', '4', null, '2018-07-31 11:17:08');
INSERT INTO `sys_uri` VALUES ('/userAccount/unlock', '4', null, '2018-07-31 11:17:08');
INSERT INTO `sys_uri` VALUES ('/userInfo/edit', '4', null, '2018-07-31 11:17:08');
INSERT INTO `sys_uri` VALUES ('/userInfo/editBefore/*', '4', null, '2018-07-31 11:17:08');

-- ----------------------------
-- Table structure for sys_user_role_relation
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role_relation`;
CREATE TABLE `sys_user_role_relation` (
  `userId` bigint(20) NOT NULL,
  `roleId` bigint(20) NOT NULL,
  `createTime` datetime NOT NULL,
  `createUser` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sys_user_role_relation
-- ----------------------------
INSERT INTO `sys_user_role_relation` VALUES ('3', '8', '2017-05-25 16:02:16', '1');
INSERT INTO `sys_user_role_relation` VALUES ('4', '9', '2018-06-21 15:45:23', '2');
INSERT INTO `sys_user_role_relation` VALUES ('8', '9', '2018-07-31 11:13:53', '18610925630');

-- ----------------------------
-- Table structure for sys_useraccount
-- ----------------------------
DROP TABLE IF EXISTS `sys_useraccount`;
CREATE TABLE `sys_useraccount` (
  `userId` bigint(20) NOT NULL COMMENT '主键',
  `account` varchar(50) NOT NULL COMMENT '帐号 默认手机号 ',
  `password` varchar(128) NOT NULL COMMENT '密码加密后的字符串',
  `nonExpired` tinyint(1) NOT NULL COMMENT '是否过期 1未过期 0已过期',
  `nonLocked` tinyint(1) NOT NULL COMMENT '是否锁定 1未锁定 0已锁定',
  `enabled` tinyint(1) NOT NULL COMMENT '是否有效 1有效     0无效',
  `deleted` tinyint(1) NOT NULL COMMENT '是否删除 1已删除  0未删除',
  `expiredTime` datetime DEFAULT NULL COMMENT '是否过期最后的操作时间',
  `lockedTime` datetime DEFAULT NULL COMMENT '是否锁定最后的操作时间',
  `enabledTime` datetime DEFAULT NULL COMMENT '是否有效最后的操作时间',
  `deletedTime` datetime DEFAULT NULL COMMENT '是否删除最后的操作时间',
  `createTime` datetime NOT NULL COMMENT '创建时间',
  `lastModifyTime` datetime DEFAULT NULL COMMENT '最后修改的时间',
  `userType` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`userId`),
  UNIQUE KEY `account` (`account`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sys_useraccount
-- ----------------------------
INSERT INTO `sys_useraccount` VALUES ('3', '18610925630', '{bcrypt}$2a$10$g6rDub0M0qEQtvCCtY.V9.KkkOkfe5HZ/ZVOlw3SWhQNH6QNaNrke', '1', '1', '1', '0', null, '2018-07-17 16:40:51', '2018-07-17 16:37:46', null, '2017-05-20 17:57:42', '2018-07-31 11:08:52', 'userInfoService');
INSERT INTO `sys_useraccount` VALUES ('4', '15200858080', '{bcrypt}$2a$10$iC.1Fl65AV1HtM3wZUFRDuZAcTyva.EPtUyT/ogOYx/3pSkTg/aSu', '1', '1', '1', '0', null, '2018-07-17 17:28:05', '2018-07-17 17:28:00', null, '2018-06-21 15:45:02', '2018-06-25 15:54:09', 'userInfoService');
INSERT INTO `sys_useraccount` VALUES ('8', '15212341234', '{bcrypt}$2a$10$iC.1Fl65AV1HtM3wZUFRDuZAcTyva.EPtUyT/ogOYx/3pSkTg/aSu', '1', '1', '1', '0', null, '2018-07-31 11:09:22', '2018-07-31 11:09:09', null, '2018-06-30 15:21:52', '2018-06-30 15:22:31', 'userInfoService');

-- ----------------------------
-- Table structure for sys_userinfo
-- ----------------------------
DROP TABLE IF EXISTS `sys_userinfo`;
CREATE TABLE `sys_userinfo` (
  `userId` bigint(20) NOT NULL COMMENT '用户Id 来自账号表',
  `name` varchar(20) NOT NULL COMMENT '姓名',
  `mobile` varchar(11) NOT NULL COMMENT '电话',
  `lastModifyTime` datetime NOT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sys_userinfo
-- ----------------------------
INSERT INTO `sys_userinfo` VALUES ('3', '欧阳锋', '18610925630', '2017-09-28 17:40:46');
INSERT INTO `sys_userinfo` VALUES ('4', '黄药师', '15200858080', '2018-07-18 16:59:20');
INSERT INTO `sys_userinfo` VALUES ('8', 'asdf1', '15212341234', '2018-06-30 15:22:31');
