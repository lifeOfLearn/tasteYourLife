package iot.tyl.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import iot.tyl.config.PathConfig;
import iot.tyl.model.ResponseDto;
import iot.tyl.util.contant.ErrorKeyType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
public class ErrController implements ErrorController {

	@Autowired
	private PathConfig pathConfig;
	
	@RequestMapping("/error")
	public ResponseDto errorHandler(HttpServletRequest request, HttpServletResponse response) {
		ResponseDto dto = new ResponseDto();
		Object status = response.getStatus();
		String msg = "";
		if (status != null) {
			int code = Integer.valueOf(status.toString());
			switch (code) {
				case 400:
					msg = "請查閱請求公開說明書";
					break;
				case 401:
					msg = "逾時";
					break;
				case 403:
					msg = "你要找的東西不再這邊喔";
					break;
				case 404:
					dto.setRedirect(pathConfig.getRoot());
					msg = "我是誰我在哪裡,請查閱說明書";
					break;
				case 405:
					msg = "請查閱說明書，確認使用的method喔";
					break;
				case 500:
					dto.setRedirect(pathConfig.getRoot());
					msg = "有錯誤喔";
					break;
			}
		}
		dto.setCode(Integer.valueOf(status.toString()));
		Map<ErrorKeyType, String> errMap = new HashMap<>();
		errMap.put(ErrorKeyType.other, msg);
		dto.setError(errMap);
		return dto;
	}
}
