package com.zjht.jfmall.action.appApi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zjht.jfmall.common.dto.JsonResult;

@RestController
@RequestMapping("/appenv")
public class AppEnvController {

	@Value(value = "${actonoff}")
	private String actonoff;
	
	
	@RequestMapping(value = "/getActOnOff")
	public JsonResult getActOnOff() {
		JsonResult jr = new JsonResult();
		jr.setSuccess(true);
		jr.setData(actonoff);
		return jr;
	}
	
	
	
	
}
