/*
Navicat MySQL Data Transfer

Source Server         : spring
Source Server Version : 50726
Source Host           : localhost:3306
Source Database       : springsecurity

Target Server Type    : MYSQL
Target Server Version : 50726
File Encoding         : 65001

Date: 2022-06-20 18:21:13
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `aa_session_menu`
-- ----------------------------
DROP TABLE IF EXISTS `aa_session_menu`;
CREATE TABLE `aa_session_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父级ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `title` varchar(100) DEFAULT NULL COMMENT '菜单名称',
  `level` int(11) DEFAULT NULL COMMENT '菜单级数',
  `sort` int(11) DEFAULT NULL COMMENT '菜单排序',
  `name` varchar(100) DEFAULT NULL COMMENT '前端名称',
  `icon` varchar(200) DEFAULT NULL COMMENT '前端图标',
  `hidden` int(11) DEFAULT NULL COMMENT '前端隐藏',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='后台菜单表';

-- ----------------------------
-- Records of aa_session_menu
-- ----------------------------
INSERT INTO `aa_session_menu` VALUES ('1', '0', '2021-08-27 22:02:40', '权限', '0', '0', 'ums', 'ums', '0');
INSERT INTO `aa_session_menu` VALUES ('2', '1', '2021-08-27 22:04:28', '人员列表', '1', '0', 'admin', 'ums-admin', '0');
INSERT INTO `aa_session_menu` VALUES ('3', '1', '2021-08-27 22:06:18', '角色列表', '1', '0', 'role', 'ums-role', '0');
INSERT INTO `aa_session_menu` VALUES ('4', '1', '2021-08-27 22:06:54', '菜单列表', '1', '0', 'menu', 'ums-menu', '0');
INSERT INTO `aa_session_menu` VALUES ('5', '1', '2021-08-27 22:07:30', '资源列表', '1', '0', 'resource', 'ums-resource', '0');
INSERT INTO `aa_session_menu` VALUES ('6', '0', '2021-08-29 19:20:54', '博文', '0', '0', 'bgms', 'bgms', '0');
INSERT INTO `aa_session_menu` VALUES ('7', '1', '2021-08-29 19:20:54', '博文列表', '1', '0', 'bgblogs', 'bgblogs', '0');
INSERT INTO `aa_session_menu` VALUES ('8', '1', '2021-08-29 19:20:54', '博文分类', '1', '0', 'bgclassify', 'bgclassify', '0');

-- ----------------------------
-- Table structure for `aa_session_resource`
-- ----------------------------
DROP TABLE IF EXISTS `aa_session_resource`;
CREATE TABLE `aa_session_resource` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `name` varchar(200) DEFAULT NULL COMMENT '资源名称',
  `url` varchar(200) DEFAULT NULL COMMENT '资源URL',
  `description` varchar(500) DEFAULT NULL COMMENT '描述',
  `category_id` bigint(20) DEFAULT NULL COMMENT '资源分类ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='后台资源表';

-- ----------------------------
-- Records of aa_session_resource
-- ----------------------------
INSERT INTO `aa_session_resource` VALUES ('1', '2021-08-27 21:38:44', '后台人员管理', '/api/admin/**', '后台增删改查人员账号', '1');
INSERT INTO `aa_session_resource` VALUES ('2', '2021-08-27 21:40:09', '后台菜单管理', '/menu/**', '后台增删改查菜单', '1');
INSERT INTO `aa_session_resource` VALUES ('3', '2021-08-27 21:45:37', '后台资源管理', '/resource/**', '后台增删改查资源账号', '1');
INSERT INTO `aa_session_resource` VALUES ('4', '2021-08-27 21:46:09', '后台资源分类管理', '/resourceCategory/**', '后台资源分类管理', '1');
INSERT INTO `aa_session_resource` VALUES ('5', '2021-08-29 19:32:47', '后台博文管理', '/bolg/**', '可以管理所有博文', '2');
INSERT INTO `aa_session_resource` VALUES ('6', '2021-08-29 19:32:47', '后台博文分类管理', '/classify/**', '可以管理所有博文分类', '2');
INSERT INTO `aa_session_resource` VALUES ('7', '2021-08-29 22:07:40', '后台角色管理', '/role/**', '后台增删改查角色', '1');

-- ----------------------------
-- Table structure for `aa_session_role`
-- ----------------------------
DROP TABLE IF EXISTS `aa_session_role`;
CREATE TABLE `aa_session_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL COMMENT '名称',
  `description` varchar(500) DEFAULT NULL COMMENT '描述',
  `admin_count` int(11) DEFAULT NULL COMMENT '后台用户数量',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `status` int(11) DEFAULT '1' COMMENT '启用状态：0->禁用；1->启用',
  `sort` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='后台用户角色表';

-- ----------------------------
-- Records of aa_session_role
-- ----------------------------
INSERT INTO `aa_session_role` VALUES ('1', '后台权限管理员', '后台权限管理员', '0', '2021-08-27 22:08:38', '1', '0');
INSERT INTO `aa_session_role` VALUES ('2', '后台博文管理员', '后台博文管理员', '0', '2021-08-29 21:59:54', '1', '0');

-- ----------------------------
-- Table structure for `aa_session_role_menu_relation`
-- ----------------------------
DROP TABLE IF EXISTS `aa_session_role_menu_relation`;
CREATE TABLE `aa_session_role_menu_relation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `menu_id` bigint(20) DEFAULT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='后台角色菜单关系表';

-- ----------------------------
-- Records of aa_session_role_menu_relation
-- ----------------------------
INSERT INTO `aa_session_role_menu_relation` VALUES ('1', '1', '1');
INSERT INTO `aa_session_role_menu_relation` VALUES ('2', '1', '2');
INSERT INTO `aa_session_role_menu_relation` VALUES ('3', '1', '3');
INSERT INTO `aa_session_role_menu_relation` VALUES ('4', '1', '4');
INSERT INTO `aa_session_role_menu_relation` VALUES ('5', '1', '5');
INSERT INTO `aa_session_role_menu_relation` VALUES ('6', '2', '6');
INSERT INTO `aa_session_role_menu_relation` VALUES ('7', '2', '7');
INSERT INTO `aa_session_role_menu_relation` VALUES ('8', '2', '8');

-- ----------------------------
-- Table structure for `aa_session_role_resource_relation`
-- ----------------------------
DROP TABLE IF EXISTS `aa_session_role_resource_relation`;
CREATE TABLE `aa_session_role_resource_relation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `resource_id` bigint(20) DEFAULT NULL COMMENT '资源ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='后台角色资源关系表';

-- ----------------------------
-- Records of aa_session_role_resource_relation
-- ----------------------------
INSERT INTO `aa_session_role_resource_relation` VALUES ('1', '1', '1');
INSERT INTO `aa_session_role_resource_relation` VALUES ('2', '1', '2');
INSERT INTO `aa_session_role_resource_relation` VALUES ('3', '1', '3');
INSERT INTO `aa_session_role_resource_relation` VALUES ('4', '1', '4');
INSERT INTO `aa_session_role_resource_relation` VALUES ('5', '2', '5');
INSERT INTO `aa_session_role_resource_relation` VALUES ('6', '2', '6');
INSERT INTO `aa_session_role_resource_relation` VALUES ('7', '1', '7');

-- ----------------------------
-- Table structure for `aa_session_user`
-- ----------------------------
DROP TABLE IF EXISTS `aa_session_user`;
CREATE TABLE `aa_session_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_uuid` varchar(36) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `real_name` varchar(255) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `sex` int(11) DEFAULT NULL,
  `birth_day` datetime DEFAULT NULL,
  `user_level` int(11) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `role` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `image_path` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `last_active_time` datetime DEFAULT NULL,
  `deleted` bit(1) DEFAULT NULL,
  `wx_open_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of aa_session_user
-- ----------------------------
INSERT INTO `aa_session_user` VALUES ('1', 'd2d29da2-dcb3-4013-b874-727626236f47', 'student', 'D1AGFL+Gx37t0NPG4d6biYP5Z31cNbwhK5w1lUeiHB2zagqbk8efYfSjYoh1Z/j1dkiRjHU+b0EpwzCh8IGsksJjzD65ci5LsnodQVf4Uj6D3pwoscXGqmkjjpzvSJbx42swwNTA+QoDU8YLo7JhtbUK2X0qCjFGpd+8eJ5BGvk=', '学生', '18', '1', '2019-09-01 16:00:00', '1', '19171171610', '1', '1', 'https://www.mindskip.net:9008/image/ba607a75-83ba-4530-8e23-660b72dc4953/头像.jpg', '2019-09-07 18:55:02', '2020-02-04 08:26:54', null, '', null);
INSERT INTO `aa_session_user` VALUES ('2', '52045f5f-a13f-4ccc-93dd-f7ee8270ad4c', 'admin', 'D1AGFL+Gx37t0NPG4d6biYP5Z31cNbwhK5w1lUeiHB2zagqbk8efYfSjYoh1Z/j1dkiRjHU+b0EpwzCh8IGsksJjzD65ci5LsnodQVf4Uj6D3pwoscXGqmkjjpzvSJbx42swwNTA+QoDU8YLo7JhtbUK2X0qCjFGpd+8eJ5BGvk=', '管理员', '30', '1', '2019-09-07 18:56:07', null, null, '3', '1', null, '2019-09-07 18:56:21', null, null, '', null);
INSERT INTO `aa_session_user` VALUES ('3', 'd0ed2cd2-dc7e-4dc4-a9b3-db2ec52905bf', 'ceshiadm', 'D1AGFL+Gx37t0NPG4d6biYP5Z31cNbwhK5w1lUeiHB2zagqbk8efYfSjYoh1Z/j1dkiRjHU+b0EpwzCh8IGsksJjzD65ci5LsnodQVf4Uj6D3pwoscXGqmkjjpzvSJbx42swwNTA+QoDU8YLo7JhtbUK2X0qCjFGpd+8eJ5BGvk=', 's', null, null, null, '1', null, '3', '1', null, '2021-11-18 14:06:53', '2021-11-18 14:12:37', '2021-11-18 14:06:53', '', null);
INSERT INTO `aa_session_user` VALUES ('4', 'e31aea6c-dc7e-4d0b-b945-97b0f1ba3318', 'ceshistu', 'D1AGFL+Gx37t0NPG4d6biYP5Z31cNbwhK5w1lUeiHB2zagqbk8efYfSjYoh1Z/j1dkiRjHU+b0EpwzCh8IGsksJjzD65ci5LsnodQVf4Uj6D3pwoscXGqmkjjpzvSJbx42swwNTA+QoDU8YLo7JhtbUK2X0qCjFGpd+8eJ5BGvk=', null, null, null, null, '2', null, '1', '1', null, '2021-11-18 14:16:36', null, '2021-11-18 14:16:36', '', null);
INSERT INTO `aa_session_user` VALUES ('5', 'a0ae2b83-056f-48fe-8abc-7b12bda9830e', 'ad', 'btLRhMbtogg5sUxjaHkL1qTpnWNiBHpsewoQA1XGNHo3Jtrw2U6cqH8cyc6K9+okn1Pc/tUTpNMxQgb4T9Q3M4ALQraIHsJPdY6H1qIhPGOQ1BHZ7La+YGDOm3sZPuzGXOgCVeGKwmJDIiU9TZwunRrt6EIr6pgn9FEWnGFLlYI=', 'ad', null, null, null, null, null, '3', '1', null, '2021-11-18 14:17:26', null, '2021-11-18 14:17:26', '', null);
INSERT INTO `aa_session_user` VALUES ('6', 'ce2ad62a-9796-4d51-9929-0c3931f2e6a3', 'ad1', 'FhDhGBKAmlf8mSjB6o6aNcgGmHOeEkXD88v+gi9OOeb75IkhDK2IbLw6GnSmEiQmJriMK+4qTfBeCnwOQ7epwtWWhseAuFwNoCvEdAtNCCjELHHHhTD7kMJlPeuUC/ksh8qFYuCJZz8DLkyQVssaZqZ1hiCLPga2Ko9bzAao0wE=', 'ad1', null, null, null, null, null, '3', '1', null, '2021-11-18 14:19:11', null, '2021-11-18 14:19:11', '', null);

-- ----------------------------
-- Table structure for `aa_session_user_role_relation`
-- ----------------------------
DROP TABLE IF EXISTS `aa_session_user_role_relation`;
CREATE TABLE `aa_session_user_role_relation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `admin_id` bigint(20) DEFAULT NULL,
  `role_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='后台用户和角色关系表';

-- ----------------------------
-- Records of aa_session_user_role_relation
-- ----------------------------
INSERT INTO `aa_session_user_role_relation` VALUES ('3', '2', '1');
INSERT INTO `aa_session_user_role_relation` VALUES ('4', '2', '2');

-- ----------------------------
-- Table structure for `authen_session_user`
-- ----------------------------
DROP TABLE IF EXISTS `authen_session_user`;
CREATE TABLE `authen_session_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_uuid` varchar(36) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `real_name` varchar(255) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `sex` int(11) DEFAULT NULL,
  `birth_day` datetime DEFAULT NULL,
  `user_level` int(11) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `role` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `image_path` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `last_active_time` datetime DEFAULT NULL,
  `deleted` bit(1) DEFAULT NULL,
  `wx_open_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of authen_session_user
-- ----------------------------
INSERT INTO `authen_session_user` VALUES ('1', 'd2d29da2-dcb3-4013-b874-727626236f47', 'student', 'D1AGFL+Gx37t0NPG4d6biYP5Z31cNbwhK5w1lUeiHB2zagqbk8efYfSjYoh1Z/j1dkiRjHU+b0EpwzCh8IGsksJjzD65ci5LsnodQVf4Uj6D3pwoscXGqmkjjpzvSJbx42swwNTA+QoDU8YLo7JhtbUK2X0qCjFGpd+8eJ5BGvk=', '学生', '18', '1', '2019-09-01 16:00:00', '1', '19171171610', '1', '1', 'https://www.mindskip.net:9008/image/ba607a75-83ba-4530-8e23-660b72dc4953/头像.jpg', '2019-09-07 18:55:02', '2020-02-04 08:26:54', null, '', null);
INSERT INTO `authen_session_user` VALUES ('2', '52045f5f-a13f-4ccc-93dd-f7ee8270ad4c', 'admin', 'D1AGFL+Gx37t0NPG4d6biYP5Z31cNbwhK5w1lUeiHB2zagqbk8efYfSjYoh1Z/j1dkiRjHU+b0EpwzCh8IGsksJjzD65ci5LsnodQVf4Uj6D3pwoscXGqmkjjpzvSJbx42swwNTA+QoDU8YLo7JhtbUK2X0qCjFGpd+8eJ5BGvk=', '管理员', '30', '1', '2019-09-07 18:56:07', null, null, '3', '1', null, '2019-09-07 18:56:21', null, null, '', null);
INSERT INTO `authen_session_user` VALUES ('3', 'd0ed2cd2-dc7e-4dc4-a9b3-db2ec52905bf', 'ceshiadm', 'D1AGFL+Gx37t0NPG4d6biYP5Z31cNbwhK5w1lUeiHB2zagqbk8efYfSjYoh1Z/j1dkiRjHU+b0EpwzCh8IGsksJjzD65ci5LsnodQVf4Uj6D3pwoscXGqmkjjpzvSJbx42swwNTA+QoDU8YLo7JhtbUK2X0qCjFGpd+8eJ5BGvk=', 's', null, null, null, '1', null, '3', '1', null, '2021-11-18 14:06:53', '2021-11-18 14:12:37', '2021-11-18 14:06:53', '', null);
INSERT INTO `authen_session_user` VALUES ('4', 'e31aea6c-dc7e-4d0b-b945-97b0f1ba3318', 'ceshistu', 'D1AGFL+Gx37t0NPG4d6biYP5Z31cNbwhK5w1lUeiHB2zagqbk8efYfSjYoh1Z/j1dkiRjHU+b0EpwzCh8IGsksJjzD65ci5LsnodQVf4Uj6D3pwoscXGqmkjjpzvSJbx42swwNTA+QoDU8YLo7JhtbUK2X0qCjFGpd+8eJ5BGvk=', null, null, null, null, '2', null, '1', '1', null, '2021-11-18 14:16:36', null, '2021-11-18 14:16:36', '', null);
INSERT INTO `authen_session_user` VALUES ('5', 'a0ae2b83-056f-48fe-8abc-7b12bda9830e', 'ad', 'btLRhMbtogg5sUxjaHkL1qTpnWNiBHpsewoQA1XGNHo3Jtrw2U6cqH8cyc6K9+okn1Pc/tUTpNMxQgb4T9Q3M4ALQraIHsJPdY6H1qIhPGOQ1BHZ7La+YGDOm3sZPuzGXOgCVeGKwmJDIiU9TZwunRrt6EIr6pgn9FEWnGFLlYI=', 'ad', null, null, null, null, null, '3', '1', null, '2021-11-18 14:17:26', null, '2021-11-18 14:17:26', '', null);
INSERT INTO `authen_session_user` VALUES ('6', 'ce2ad62a-9796-4d51-9929-0c3931f2e6a3', 'ad1', 'FhDhGBKAmlf8mSjB6o6aNcgGmHOeEkXD88v+gi9OOeb75IkhDK2IbLw6GnSmEiQmJriMK+4qTfBeCnwOQ7epwtWWhseAuFwNoCvEdAtNCCjELHHHhTD7kMJlPeuUC/ksh8qFYuCJZz8DLkyQVssaZqZ1hiCLPga2Ko9bzAao0wE=', 'ad1', null, null, null, null, null, '3', '1', null, '2021-11-18 14:19:11', null, '2021-11-18 14:19:11', '', null);

-- ----------------------------
-- Table structure for `authen_token_jwt_user`
-- ----------------------------
DROP TABLE IF EXISTS `authen_token_jwt_user`;
CREATE TABLE `authen_token_jwt_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_uuid` varchar(36) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `real_name` varchar(255) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `sex` int(11) DEFAULT NULL,
  `birth_day` datetime DEFAULT NULL,
  `user_level` int(11) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `role` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `image_path` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `last_active_time` datetime DEFAULT NULL,
  `deleted` bit(1) DEFAULT NULL,
  `wx_open_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of authen_token_jwt_user
-- ----------------------------
INSERT INTO `authen_token_jwt_user` VALUES ('1', 'd2d29da2-dcb3-4013-b874-727626236f47', 'student', 'D1AGFL+Gx37t0NPG4d6biYP5Z31cNbwhK5w1lUeiHB2zagqbk8efYfSjYoh1Z/j1dkiRjHU+b0EpwzCh8IGsksJjzD65ci5LsnodQVf4Uj6D3pwoscXGqmkjjpzvSJbx42swwNTA+QoDU8YLo7JhtbUK2X0qCjFGpd+8eJ5BGvk=', '学生', '18', '1', '2019-09-01 16:00:00', '1', '19171171610', '1', '1', 'https://www.mindskip.net:9008/image/ba607a75-83ba-4530-8e23-660b72dc4953/头像.jpg', '2019-09-07 18:55:02', '2020-02-04 08:26:54', null, '', null);
INSERT INTO `authen_token_jwt_user` VALUES ('2', '52045f5f-a13f-4ccc-93dd-f7ee8270ad4c', 'admin', 'D1AGFL+Gx37t0NPG4d6biYP5Z31cNbwhK5w1lUeiHB2zagqbk8efYfSjYoh1Z/j1dkiRjHU+b0EpwzCh8IGsksJjzD65ci5LsnodQVf4Uj6D3pwoscXGqmkjjpzvSJbx42swwNTA+QoDU8YLo7JhtbUK2X0qCjFGpd+8eJ5BGvk=', '管理员', '30', '1', '2019-09-07 18:56:07', null, null, '3', '1', null, '2019-09-07 18:56:21', null, null, '', null);
INSERT INTO `authen_token_jwt_user` VALUES ('3', 'd0ed2cd2-dc7e-4dc4-a9b3-db2ec52905bf', 'ceshiadm', '$2a$10$BU5KghBWrmCbNVwHLdAaa.Uq.tAYvj8bnmjg0kYGr8fTPw0MB9j2a', 's', null, null, null, '1', null, '3', '1', null, '2021-11-18 14:06:53', '2021-11-18 14:12:37', '2021-11-18 14:06:53', '', null);
INSERT INTO `authen_token_jwt_user` VALUES ('4', 'e31aea6c-dc7e-4d0b-b945-97b0f1ba3318', 'ceshistu', '$2a$10$BU5KghBWrmCbNVwHLdAaa.Uq.tAYvj8bnmjg0kYGr8fTPw0MB9j2a', null, null, null, null, '2', null, '1', '1', null, '2021-11-18 14:16:36', null, '2021-11-18 14:16:36', '', null);
INSERT INTO `authen_token_jwt_user` VALUES ('5', 'a0ae2b83-056f-48fe-8abc-7b12bda9830e', 'ad', 'btLRhMbtogg5sUxjaHkL1qTpnWNiBHpsewoQA1XGNHo3Jtrw2U6cqH8cyc6K9+okn1Pc/tUTpNMxQgb4T9Q3M4ALQraIHsJPdY6H1qIhPGOQ1BHZ7La+YGDOm3sZPuzGXOgCVeGKwmJDIiU9TZwunRrt6EIr6pgn9FEWnGFLlYI=', 'ad', null, null, null, null, null, '3', '1', null, '2021-11-18 14:17:26', null, '2021-11-18 14:17:26', '', null);
INSERT INTO `authen_token_jwt_user` VALUES ('6', 'ce2ad62a-9796-4d51-9929-0c3931f2e6a3', 'ad1', 'FhDhGBKAmlf8mSjB6o6aNcgGmHOeEkXD88v+gi9OOeb75IkhDK2IbLw6GnSmEiQmJriMK+4qTfBeCnwOQ7epwtWWhseAuFwNoCvEdAtNCCjELHHHhTD7kMJlPeuUC/ksh8qFYuCJZz8DLkyQVssaZqZ1hiCLPga2Ko9bzAao0wE=', 'ad1', null, null, null, null, null, '3', '1', null, '2021-11-18 14:19:11', null, '2021-11-18 14:19:11', '', null);

-- ----------------------------
-- Table structure for `authorities `
-- ----------------------------
DROP TABLE IF EXISTS `authorities `;
CREATE TABLE `authorities ` (
  `username` varchar(50) NOT NULL,
  `authority` varchar(50) NOT NULL,
  KEY `fk_authorities_users` (`username`),
  CONSTRAINT `fk_authorities_users` FOREIGN KEY (`username`) REFERENCES `users` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of authorities 
-- ----------------------------

-- ----------------------------
-- Table structure for `ums_admin`
-- ----------------------------
DROP TABLE IF EXISTS `ums_admin`;
CREATE TABLE `ums_admin` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(64) DEFAULT NULL,
  `password` varchar(64) DEFAULT NULL,
  `icon` varchar(500) DEFAULT NULL COMMENT '头像',
  `phone` varchar(100) DEFAULT NULL COMMENT '联系方式',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `nick_name` varchar(200) DEFAULT NULL COMMENT '昵称',
  `note` varchar(500) DEFAULT NULL COMMENT '备注信息',
  `status` int(11) DEFAULT '1' COMMENT '帐号启用状态：0->禁用；1->启用',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='后台用户表';

-- ----------------------------
-- Records of ums_admin
-- ----------------------------
INSERT INTO `ums_admin` VALUES ('1', 'admin', '$2a$10$NrkT52AtW6EWQ5n1zTcx7eDb2FmsrbFqQjYV1dHPCgcwmkz0eQeJ2', 'http://', '12312312310', '18803260895@163.com', 'admin', 'note', '1', '2022-01-01 10:33:55', '2022-01-01 10:34:00');

-- ----------------------------
-- Table structure for `ums_admin_role_relation`
-- ----------------------------
DROP TABLE IF EXISTS `ums_admin_role_relation`;
CREATE TABLE `ums_admin_role_relation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `admin_id` bigint(20) DEFAULT NULL,
  `role_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='后台用户和角色关系表';

-- ----------------------------
-- Records of ums_admin_role_relation
-- ----------------------------
INSERT INTO `ums_admin_role_relation` VALUES ('1', '1', '1');

-- ----------------------------
-- Table structure for `ums_menu`
-- ----------------------------
DROP TABLE IF EXISTS `ums_menu`;
CREATE TABLE `ums_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父级ID',
  `title` varchar(100) DEFAULT NULL COMMENT '菜单名称',
  `level` int(11) DEFAULT NULL COMMENT '菜单级数',
  `sort` int(11) DEFAULT NULL COMMENT '菜单排序',
  `name` varchar(100) DEFAULT NULL COMMENT '前端名称',
  `icon` varchar(200) DEFAULT NULL COMMENT '前端图标',
  `hidden` int(11) DEFAULT NULL COMMENT '前端隐藏0显示1隐藏，即便有该权限也不会显示，前端要确认判断是否显示',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='后台菜单表';

-- ----------------------------
-- Records of ums_menu
-- ----------------------------
INSERT INTO `ums_menu` VALUES ('1', '0', '权限', '0', '0', 'ums', 'ums', '0', '2022-01-01 10:33:55', '2022-01-01 10:34:00');
INSERT INTO `ums_menu` VALUES ('2', '1', '人员列表', '1', '0', 'admin', 'ums-admin', '0', '2022-01-01 10:33:55', '2022-01-01 10:34:00');
INSERT INTO `ums_menu` VALUES ('3', '1', '角色列表', '1', '0', 'role', 'ums-role', '0', '2022-01-01 10:33:55', '2022-01-01 10:34:00');
INSERT INTO `ums_menu` VALUES ('4', '1', '菜单列表', '1', '0', 'menu', 'ums-menu', '0', '2022-01-01 10:33:55', '2022-01-01 10:34:00');
INSERT INTO `ums_menu` VALUES ('5', '1', '资源列表', '1', '0', 'resource', 'ums-resource', '0', '2022-01-01 10:33:55', '2022-01-01 10:34:00');

-- ----------------------------
-- Table structure for `ums_resource`
-- ----------------------------
DROP TABLE IF EXISTS `ums_resource`;
CREATE TABLE `ums_resource` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) DEFAULT NULL COMMENT '资源名称',
  `url` varchar(200) DEFAULT NULL COMMENT '资源URL，对应后端接口,也对应前端菜单和按钮',
  `description` varchar(500) DEFAULT NULL COMMENT '描述',
  `category_id` bigint(20) DEFAULT NULL COMMENT '资源分类ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='后台资源表';

-- ----------------------------
-- Records of ums_resource
-- ----------------------------
INSERT INTO `ums_resource` VALUES ('1', '后台人员管理', '/admin/**', '后台增删改查人员账号', '1', '2022-01-01 10:33:55', '2022-01-01 10:34:00');
INSERT INTO `ums_resource` VALUES ('2', '后台菜单管理', '/menu/**', '后台增删改查菜单', '1', '2022-01-01 10:33:55', '2022-01-01 10:34:00');
INSERT INTO `ums_resource` VALUES ('3', '后台资源管理', '/resource/**', '后台增删改查资源账号', '1', '2022-01-01 10:33:55', '2022-01-01 10:34:00');
INSERT INTO `ums_resource` VALUES ('4', '新增后台人员管理', '/admin/update/**', '后台新增人员账号', '1', '2022-01-01 10:33:55', '2022-01-01 10:33:55');

-- ----------------------------
-- Table structure for `ums_role`
-- ----------------------------
DROP TABLE IF EXISTS `ums_role`;
CREATE TABLE `ums_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL COMMENT '名称',
  `description` varchar(500) DEFAULT NULL COMMENT '描述',
  `admin_count` int(11) DEFAULT NULL COMMENT '后台用户数量',
  `status` int(11) DEFAULT '1' COMMENT '启用状态：0->禁用；1->启用',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='后台用户角色表';

-- ----------------------------
-- Records of ums_role
-- ----------------------------
INSERT INTO `ums_role` VALUES ('1', '后台权限管理员', '后台权限管理员', '0', '1', '2022-01-01 10:33:55', '2022-01-01 10:34:00');
INSERT INTO `ums_role` VALUES ('2', '后台人员管理', '后台人员管理', '0', '1', '2022-01-01 10:33:55', '2022-01-01 10:34:00');
INSERT INTO `ums_role` VALUES ('3', '后台菜单管理', '后台菜单管理', '0', '1', '2022-01-01 10:33:55', '2022-01-01 10:34:00');
INSERT INTO `ums_role` VALUES ('4', '后台资源管理', '后台资源管理', '0', '1', '2022-01-01 10:33:55', '2022-01-01 10:34:00');

-- ----------------------------
-- Table structure for `ums_role_menu_relation`
-- ----------------------------
DROP TABLE IF EXISTS `ums_role_menu_relation`;
CREATE TABLE `ums_role_menu_relation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `menu_id` bigint(20) DEFAULT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='后台角色菜单关系表';

-- ----------------------------
-- Records of ums_role_menu_relation
-- ----------------------------
INSERT INTO `ums_role_menu_relation` VALUES ('1', '1', '1');
INSERT INTO `ums_role_menu_relation` VALUES ('2', '1', '2');
INSERT INTO `ums_role_menu_relation` VALUES ('3', '1', '3');
INSERT INTO `ums_role_menu_relation` VALUES ('4', '1', '4');
INSERT INTO `ums_role_menu_relation` VALUES ('5', '1', '5');

-- ----------------------------
-- Table structure for `ums_role_resource_relation`
-- ----------------------------
DROP TABLE IF EXISTS `ums_role_resource_relation`;
CREATE TABLE `ums_role_resource_relation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `resource_id` bigint(20) DEFAULT NULL COMMENT '资源ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='后台角色资源关系表';

-- ----------------------------
-- Records of ums_role_resource_relation
-- ----------------------------
INSERT INTO `ums_role_resource_relation` VALUES ('1', '1', '1');
INSERT INTO `ums_role_resource_relation` VALUES ('2', '1', '2');

-- ----------------------------
-- Table structure for `users`
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `username` varchar(50) NOT NULL,
  `password` varchar(500) NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of users
-- ----------------------------

-- ----------------------------
-- Table structure for `users4_2`
-- ----------------------------
DROP TABLE IF EXISTS `users4_2`;
CREATE TABLE `users4_2` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `roles` text,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of users4_2
-- ----------------------------
INSERT INTO `users4_2` VALUES ('1', 'admin', '$2a$10$qESAOs7jKemVAIfXQURePeXyJBwFnoytDOjvq8XQKY.qmsg6XWpj2', 'ROLE_ADMIN', '1');
INSERT INTO `users4_2` VALUES ('2', 'user', '$2a$10$qESAOs7jKemVAIfXQURePeXyJBwFnoytDOjvq8XQKY.qmsg6XWpj2', 'ROLE_USER', '1');
