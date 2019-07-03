package com.liyulin.demo.mall.user.test.integration.cases.api;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.TypeReference;
import com.liyulin.demo.common.business.dto.Resp;
import com.liyulin.demo.common.business.exception.enums.ReturnCodeEnum;
import com.liyulin.demo.common.business.test.AbstractIntegrationTest;
import com.liyulin.demo.mall.user.test.data.UserInfoData;
import com.liyulin.demo.rpc.user.request.api.user.QueryUserInfoByIdReqBody;
import com.liyulin.demo.rpc.user.response.base.UserInfoBaseRespBody;

@Rollback
@Transactional
public class UserInfoApiControllerIntegrationTest extends AbstractIntegrationTest {

	@Autowired
	private UserInfoData userInfoData;

	@Test
	public void testQueryById() throws Exception {
		Long userId = 1L;
		userInfoData.insertTestData(userId);
		// 构造请求参数
		QueryUserInfoByIdReqBody reqBody = new QueryUserInfoByIdReqBody();
		reqBody.setUserId(userId);

		Resp<UserInfoBaseRespBody> result = super.postWithNoHeaders("/api/identity/user/userInfo/queryById", reqBody,
				new TypeReference<Resp<UserInfoBaseRespBody>>() {
				});

		Assertions.assertThat(result).isNotNull();
		Assertions.assertThat(result.getHead()).isNotNull();
		Assertions.assertThat(result.getHead().getCode()).isEqualTo(ReturnCodeEnum.SUCCESS.getCode());
		Assertions.assertThat(result.getBody()).isNotNull();
	}

}