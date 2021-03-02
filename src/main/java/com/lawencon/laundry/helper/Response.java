package com.lawencon.laundry.helper;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author Imron Rosyadi
 */

@JsonInclude(Include.NON_NULL)
public class Response<D> {

	private HttpStatus status;
	private Integer code;
	private D dataModel;
	private String msg;

	public Response(HttpStatus status, D dataModel) {
		this.status = status;
		this.code = status.value();
		this.dataModel = dataModel;
	}

	public Response(HttpStatus status, D dataModel, String msg) {
		this.status = status;
		this.code = status.value();
		this.dataModel = dataModel;
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public D getDataModel() {
		return dataModel;
	}

	public void setDataModel(D dataModel) {
		this.dataModel = dataModel;
	}

}
