package com.liyulin.demo.mall.order.test.cases.api;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.TypeReference;
import com.liyulin.demo.common.business.dto.Req;
import com.liyulin.demo.common.business.dto.Resp;
import com.liyulin.demo.common.business.exception.enums.ReturnCodeEnum;
import com.liyulin.demo.common.business.test.AbstractUnitTest;
import com.liyulin.demo.common.business.util.ReqUtil;
import com.liyulin.demo.common.business.util.RespUtil;
import com.liyulin.demo.mall.order.mapper.base.OrderBillBaseMapper;
import com.liyulin.demo.mall.order.mapper.base.OrderDeliveryInfoBaseMapper;
import com.liyulin.demo.mall.order.service.api.OrderApiService;
import com.liyulin.demo.rpc.order.request.api.CreateOrderProductInfoReqBody;
import com.liyulin.demo.rpc.order.request.api.CreateOrderReqBody;
import com.liyulin.demo.rpc.order.response.api.CreateOrderRespBody;
import com.liyulin.demo.rpc.product.ProductInfoRpc;
import com.liyulin.demo.rpc.product.response.rpc.QryProductByIdRespBody;
import com.liyulin.demo.rpc.product.response.rpc.QryProductByIdsRespBody;

public class OrderApiControllerTest extends AbstractUnitTest {

	@Mock
	private ProductInfoRpc productInfoRpc;
	@InjectMocks 
	@Autowired
	private OrderApiService orderApiService;
	@Autowired
	private OrderBillBaseMapper orderBillBaseMapper;
	@Autowired
	private OrderDeliveryInfoBaseMapper orderDeliveryInfoBaseMapper;
	
	@Before
	public void setBefore() {
		orderBillBaseMapper.deleteAll();
		orderDeliveryInfoBaseMapper.deleteAll();
	}
	
	@Test
	public void testCreate() throws Exception {
		// 1、构建请求
		// build args
		List<CreateOrderProductInfoReqBody> buyProducts = new ArrayList<>();
		for (long i = 1; i <= 3; i++) {
			CreateOrderProductInfoReqBody createOrderProductInfoReqBody = new CreateOrderProductInfoReqBody();
			createOrderProductInfoReqBody.setProductId(i);
			createOrderProductInfoReqBody.setBuyCount((int) i);

			buyProducts.add(createOrderProductInfoReqBody);
		}
		
		CreateOrderReqBody reqBody = new CreateOrderReqBody();
		reqBody.setProducts(buyProducts);
		Req<CreateOrderReqBody> req = ReqUtil.buildWithHead(reqBody);
		req.setSign("test");
				
		// 2、mock 行为
		mockStubbing(buyProducts);
		
		Resp<CreateOrderRespBody> resp = postJson("/api/auth/order/order/create", req, new TypeReference<Resp<CreateOrderRespBody>>() {
		});

		// 3、断言结果
		Assertions.assertThat(resp).isNotNull();
		Assertions.assertThat(resp.getHead()).isNotNull();
		Assertions.assertThat(resp.getHead().getCode()).isEqualTo(ReturnCodeEnum.SUCCESS.getInfo().getCode());
	}
	
	private void mockStubbing(List<CreateOrderProductInfoReqBody> buyProducts) {
		// 2.1qryProductByIds
		// response
		List<QryProductByIdRespBody> productInfos = new ArrayList<>();
		for(CreateOrderProductInfoReqBody buyProduct:buyProducts){
			Long productId = buyProduct.getProductId();
			
			// response
			QryProductByIdRespBody qryProductByIdRespBody = new QryProductByIdRespBody();
			qryProductByIdRespBody.setId(productId);
			qryProductByIdRespBody.setName("手机"+productId);
			qryProductByIdRespBody.setSellPrice(productId*10000);
			qryProductByIdRespBody.setStock(productId*10000);
			productInfos.add(qryProductByIdRespBody);
		}
		QryProductByIdsRespBody qryProductByIdsRespBody = new QryProductByIdsRespBody(productInfos);
		// stubbing
		Mockito.when(productInfoRpc.qryProductByIds(Mockito.any())).thenReturn(RespUtil.success(qryProductByIdsRespBody));
		
		// 2.2updateStock
		// stubbing
		Mockito.when(productInfoRpc.updateStock(Mockito.any())).thenReturn(RespUtil.success());
	}

}