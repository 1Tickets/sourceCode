package com.jiaxi.tickets.admin.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.jiaxi.tickets.admin.api.dto.UserInfo;
import com.jiaxi.tickets.admin.api.entity.SysUser;
import com.jiaxi.tickets.admin.service.AppService;
import com.jiaxi.tickets.admin.service.SysUserService;
import com.jiaxi.tickets.common.core.util.R;
import com.jiaxi.tickets.common.security.annotation.Inner;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lengleng
 * @date 2021/9/16 移动端登录
 */
@RestController
@AllArgsConstructor
@RequestMapping("/app")
@Api(value = "app", tags = "手机管理模块")
public class AppController {

	private final AppService appService;

	private final SysUserService userService;

	@Inner(value = false)
	@GetMapping("/{mobile}")
	public R<Boolean> sendSmsCode(@PathVariable String mobile) {
		return appService.sendSmsCode(mobile);
	}

	/**
	 * 获取指定用户全部信息
	 * @param phone 手机号
	 * @return 用户信息
	 */
	@Inner
	@GetMapping("/info/{phone}")
	public R<UserInfo> infoByMobile(@PathVariable String phone) {
		SysUser user = userService.getOne(Wrappers.<SysUser>query().lambda().eq(SysUser::getPhone, phone));
		if (user == null) {
			return R.failed(String.format("用户信息为空 %s", phone));
		}
		return R.ok(userService.getUserInfo(user));
	}

}
