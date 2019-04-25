package com.liyulin.demo.common.web.aop;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.liyulin.demo.common.constants.CommonConstants;
import com.liyulin.demo.common.properties.CommonProperties;
import com.liyulin.demo.common.util.MockUtil;

/**
 * mock切面
 *
 * @author liyulin
 * @date 2019年4月21日下午3:32:44
 */
@Aspect
@Component
@ConditionalOnProperty(prefix = CommonConstants.COMMON_PROPERTIES_PREFIX, name = CommonProperties.PropertiesName.MOCK, havingValue = "true", matchIfMissing = false)
public class MockAspect {

	@Around(CommonConstants.LOG_AOP_EXECUTION)
	public Object around(ProceedingJoinPoint jp) throws Throwable {
		return generateMockData(jp);
	}

	/**
	 * 生成mock数据
	 * 
	 * @param jp
	 * @return
	 */
	private Object generateMockData(ProceedingJoinPoint jp) {
		Signature s = jp.getSignature();
		MethodSignature ms = (MethodSignature) s;
		Method m = ms.getMethod();
		ParameterizedType parameterizedType = (ParameterizedType) m.getGenericReturnType();

		return MockUtil.mock(m.getReturnType(), parameterizedType.getActualTypeArguments()[0]);
	}

}