package com.liyulin.demo.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "响应对象")
public class Resp<T extends BaseDto> extends BaseDto {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "响应头部")
	private ReqHead head;

	@ApiModelProperty(value = "响应体")
	private T body;

	public Resp(ReqHead head) {
		this.head = head;
	}

	public Resp(T body) {
		this.body = body;
	}

}