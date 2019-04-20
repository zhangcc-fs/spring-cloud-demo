package com.liyulin.demo.common.web.aop.util;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import com.liyulin.demo.common.util.ArrayUtil;
import com.liyulin.demo.common.util.ObjectUtil;

import io.swagger.annotations.ApiOperation;
import lombok.experimental.UtilityClass;

/**
 * 切面工具类
 *
 * @author liyulin
 * @date 2019年4月21日上午12:55:33
 */
@UtilityClass
public class AspectUtil {

	private ConcurrentMap<String, String> apiDescMap = new ConcurrentHashMap<>();
	
	/**
	 * 获取controller method的描述
	 * 
	 * @param joinPoint
	 * @param path
	 * @return
	 */
	public String getControllerMethodDesc(JoinPoint joinPoint, String path) {
		// 先从缓存取
		String apiDesc = apiDescMap.get(path);
		if (ObjectUtil.isNotNull(apiDesc)) {
			return apiDesc;
		}

		// 缓存没有，则通过反射获取
		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		Method method = methodSignature.getMethod();
		ApiOperation operation = method.getAnnotation(ApiOperation.class);
		apiDesc = ObjectUtil.isNotNull(operation) ? operation.value() : StringUtils.EMPTY;
		if (StringUtils.isBlank(apiDesc)) {
			// 如果为空，则从接口类rpc取
			Object controller = joinPoint.getTarget();
			Class<?> controllerClass = controller.getClass();
			Class<?>[] interfaces = controllerClass.getInterfaces();
			if (ArrayUtil.isNotEmpty(interfaces)) {
				Class<?> rpcClass = interfaces[0];
				Method[] methods = rpcClass.getMethods();
				for (Method rpcMethod : methods) {
					if (isSameMethod(rpcMethod, method)) {
						operation = rpcMethod.getAnnotation(ApiOperation.class);
						apiDesc = ObjectUtil.isNotNull(operation) ? operation.value() : StringUtils.EMPTY;
						break;
					}
				}
			}
		}

		apiDescMap.putIfAbsent(path, apiDesc);

		return apiDesc;
	}
	
	/**
	 * 获取feign method的描述
	 * 
	 * @param joinPoint
	 * @param path
	 * @return
	 */
	public String getFeignMethodDesc(JoinPoint joinPoint, String path) {
		// 先从缓存取
		String apiDesc = apiDescMap.get(path);
		if (ObjectUtil.isNotNull(apiDesc)) {
			return apiDesc;
		}

		// 缓存没有，则通过反射获取
		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		Method method = methodSignature.getMethod();
		ApiOperation operation = method.getAnnotation(ApiOperation.class);
		apiDesc = ObjectUtil.isNotNull(operation) ? operation.value() : StringUtils.EMPTY;
		
		apiDescMap.putIfAbsent(path, apiDesc);

		return apiDesc;
	}

	/**
	 * 是否是同一个method
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	private boolean isSameMethod(Method a, Method b) {
		return (a.getReturnType() == b.getReturnType()) && ObjectUtil.equals(a.getName(), b.getName());
	}
	
}