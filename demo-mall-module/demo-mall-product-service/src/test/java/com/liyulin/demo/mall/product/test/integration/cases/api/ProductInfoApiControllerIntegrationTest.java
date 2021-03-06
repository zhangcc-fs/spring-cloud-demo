package com.liyulin.demo.mall.product.test.integration.cases.api;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.TypeReference;
import com.liyulin.demo.common.business.dto.BasePageResp;
import com.liyulin.demo.common.business.dto.Resp;
import com.liyulin.demo.common.business.exception.enums.ReturnCodeEnum;
import com.liyulin.demo.common.business.test.AbstractIntegrationTest;
import com.liyulin.demo.common.business.util.ReqUtil;
import com.liyulin.demo.mall.product.test.data.ProductInfoData;
import com.liyulin.demo.rpc.product.response.api.PageProductRespBody;

@Rollback
@Transactional
public class ProductInfoApiControllerIntegrationTest extends AbstractIntegrationTest {

	@Autowired
	private ProductInfoData productInfoData;

	@Test
	public void testPageProduct() throws Exception {
		productInfoData.batchInsertTestData();

		Resp<BasePageResp<PageProductRespBody>> result = super.postWithNoHeaders("/api/identity/product/productInfo/pageProduct",
				ReqUtil.build(null, 1, 10), new TypeReference<Resp<BasePageResp<PageProductRespBody>>>() {
				});

		Assertions.assertThat(result).isNotNull();
		Assertions.assertThat(result.getHead()).isNotNull();
		Assertions.assertThat(result.getHead().getCode()).isEqualTo(ReturnCodeEnum.SUCCESS.getCode());
		Assertions.assertThat(result.getBody()).isNotNull();
		Assertions.assertThat(result.getBody().getDatas()).isNotEmpty();
	}

}