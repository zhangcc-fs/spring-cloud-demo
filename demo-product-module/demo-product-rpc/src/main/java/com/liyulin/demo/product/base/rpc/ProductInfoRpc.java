package com.liyulin.demo.product.base.rpc;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Conditional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.liyulin.demo.common.dto.BasePageReq;
import com.liyulin.demo.common.dto.BasePageResp;
import com.liyulin.demo.common.dto.Req;
import com.liyulin.demo.common.dto.Resp;
import com.liyulin.demo.common.web.openfeign.condition.OnFeignClientCondition;
import com.liyulin.demo.product.base.rpc.request.PageProductReqBody;
import com.liyulin.demo.product.base.rpc.response.ProductInfoRespBody;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

@Conditional(OnFeignClientCondition.class)
@FeignClient
@Api(tags = "商品信息rpc相关接口")
@ApiIgnore
public interface ProductInfoRpc {

	@ApiOperation("分页查询商品信息")
	@PostMapping("rpc/pass/product/productInfo/pageProduct")
	Resp<BasePageResp<ProductInfoRespBody>> pageProduct(
			@RequestBody @Valid Req<@NotNull BasePageReq<PageProductReqBody>> req);

}