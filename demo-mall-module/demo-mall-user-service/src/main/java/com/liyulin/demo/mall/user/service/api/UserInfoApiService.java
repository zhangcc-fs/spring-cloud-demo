package com.liyulin.demo.mall.user.service.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.liyulin.demo.common.business.ReqContextHolder;
import com.liyulin.demo.common.business.exception.ParamValidateException;
import com.liyulin.demo.mall.user.biz.api.UserInfoApiBiz;
import com.liyulin.demo.mall.user.config.UserParamValidateMessage;
import com.liyulin.demo.mall.user.entity.base.UserInfoEntity;
import com.liyulin.demo.rpc.user.request.api.user.UserInfoInsertReqBody;
import com.liyulin.demo.rpc.user.response.base.UserInfoBaseRespBody;

@Service
public class UserInfoApiService {

	@Autowired
	private UserInfoApiBiz userInfoApiBiz;

	/**
	 * 根据id查询用户信息
	 * 
	 * @return
	 */
	public UserInfoBaseRespBody query() {
		Long userId = ReqContextHolder.getUserId();
		return userInfoApiBiz.getUserInfoBaseMapper().selectRespById(userId);
	}

	/**
	 * 插入用户信息
	 * 
	 * @param userInfo
	 * @return
	 */
	public UserInfoEntity insert(UserInfoInsertReqBody userInfo) {
		boolean existMobile = userInfoApiBiz.existByMobile(userInfo.getMobile());
		if (existMobile) {
			throw new ParamValidateException(UserParamValidateMessage.REGISTER_MOBILE_EXSITED);
		}

		return userInfoApiBiz.insert(userInfo);
	}

}