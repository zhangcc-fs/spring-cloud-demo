package com.liyulin.demo.common.business.autoconfigure;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.boot.test.context.AnnotatedClassFinder;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import com.liyulin.demo.common.support.annotation.YamlScan;
import com.liyulin.demo.common.util.ArrayUtil;

/**
 * 解析{@link YamlScan}
 * 
 * @author liyulin
 * @date 2019年6月21日 下午12:58:22
 */
public class YamlEnvironmentPostProcessor implements EnvironmentPostProcessor {

	@Override
	public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
		if (isRegisterShutdownHook(application)) {
			loadYaml(environment, application);
		}
	}

	private boolean isRegisterShutdownHook(SpringApplication application) {
		boolean registerShutdownHook = false;
		try {
			Field field = SpringApplication.class.getDeclaredField("registerShutdownHook");
			field.setAccessible(true);
			registerShutdownHook = field.getBoolean(application);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		
		return registerShutdownHook;
	}

	/**
	 * 将启动类注解上配置的yaml文件加到environment中
	 * 
	 * @param environment
	 * @param application
	 */
	private void loadYaml(ConfigurableEnvironment environment, SpringApplication application) {
		String[] locationPatterns = getLocationPatterns(application.getMainApplicationClass());
		if (ArrayUtil.isEmpty(locationPatterns)) {
			return;
		}

		loadYaml(locationPatterns, environment);
	}

	/**
	 * 将匹配的yaml文件加到environment中
	 * <p>
	 * <b>NOTE</b>：此时日志配置还没有加载，还打不了日志
	 * 
	 * @param locationPatterns
	 * @param environment
	 */
	private void loadYaml(String[] locationPatterns, ConfigurableEnvironment environment) {
		// 1、获取每个文件对应的Resource对象
		ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
		Set<Resource> resourceSet = new HashSet<>();
		for (String locationPattern : locationPatterns) {
			try {
				Resource[] resources = resourcePatternResolver.getResources(locationPattern);
				if (resources != null && resources.length > 0) {
					for (Resource resource : resources) {
						resourceSet.add(resource);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (resourceSet.isEmpty()) {
			return;
		}

		// 2、将所有Resource加入Environment中
		try {
			for (Resource resource : resourceSet) {
				System.out.println("load yaml ==> " + resource.getFilename());

				YamlPropertySourceLoader yamlPropertySourceLoader = new YamlPropertySourceLoader();
				List<PropertySource<?>> propertySources = yamlPropertySourceLoader.load(resource.getFilename(),
						resource);
				for (PropertySource<?> propertySource : propertySources) {
					environment.getPropertySources().addLast(propertySource);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取yaml文件路径
	 * 
	 * @param mainApplicationClass
	 * @return
	 */
	private String[] getLocationPatterns(Class<?> mainApplicationClass) {
		String[] locationPatterns = getLocationPatternsOnSpringBoot(mainApplicationClass);
		if (ArrayUtil.isNotEmpty(locationPatterns)) {
			return locationPatterns;
		}

		return getLocationPatternsOnSpringBootTest(mainApplicationClass);
	}

	/**
	 * application作为test启动
	 * 
	 * @param mainApplicationClass
	 * @return
	 */
	private String[] getLocationPatternsOnSpringBootTest(Class<?> mainApplicationClass) {
		Class<?> found = new AnnotatedClassFinder(YamlScan.class).findFromClass(mainApplicationClass);
		if (Objects.isNull(found)) {
			return new String[0];
		}
		return getLocationPatternsOnSpringBoot(found);
	}

	/**
	 * case：application正常启动
	 * 
	 * @param mainApplicationClass
	 * @return
	 */
	private String[] getLocationPatternsOnSpringBoot(Class<?> mainApplicationClass) {
		YamlScan yamlScan = AnnotationUtils.findAnnotation(mainApplicationClass, YamlScan.class);
		if (Objects.isNull(yamlScan)) {
			return new String[0];
		}

		return yamlScan.locationPatterns();
	}

}