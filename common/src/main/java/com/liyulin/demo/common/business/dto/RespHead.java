package com.liyulin.demo.common.business.dto;

import com.liyulin.demo.common.business.exception.enums.IBaseReturnCode;
import com.liyulin.demo.common.util.TransactionIdUtil;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@ApiModel(description = "响应头部")
public class RespHead extends BaseDto {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "全局唯一交易流水号", example = "eb9f81e7cee1c000")
	@Builder.Default
	private String transactionId = TransactionIdUtil.getInstance().nextId();

	@ApiModelProperty(value = "响应状态码", example = "100500")
	private String code;

	@ApiModelProperty(value = "提示信息", example = "服务器异常")
	private String msg;

	@ApiModelProperty(value = "错误详情", example = "错误详细信息")
	private String error;

	@ApiModelProperty(value = "响应时间戳", example = "1554551377629")
	@Builder.Default
	private long timestamp = System.currentTimeMillis();

	public RespHead(IBaseReturnCode returnCode) {
		this.code = returnCode.getCode();
		this.msg = returnCode.getMsg();
	}

	public RespHead(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

}