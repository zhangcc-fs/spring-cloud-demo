package com.liyulin.demo.common.properties;

import com.liyulin.demo.common.business.dto.BaseDto;

import lombok.Getter;
import lombok.Setter;

/**
 * swagger配置信息
 * 
 * @author liyulin
 * @date 2019年6月18日 下午10:23:25
 */
@Getter
@Setter
public class SwaggerProperties extends BaseDto {

	private static final long serialVersionUID = 1L;
	/** swagger开关 （默认false） */
	private boolean enable = false;
	private String groupName;
	private String title;
	private String description;
	private String name; 
	private String url; 
	private String email;

}