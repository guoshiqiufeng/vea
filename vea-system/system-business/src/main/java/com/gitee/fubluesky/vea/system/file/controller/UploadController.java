package com.gitee.fubluesky.vea.system.file.controller;

import com.gitee.fubluesky.kernel.core.exception.enums.defaults.DefaultUserExceptionEnum;
import com.gitee.fubluesky.kernel.core.pojo.ResponseResult;
import com.gitee.fubluesky.kernel.file.all.utils.UploadUtils;
import com.gitee.fubluesky.kernel.security.sign.annotation.Sign;
import com.gitee.fubluesky.vea.system.api.exception.SystemException;
import com.gitee.fubluesky.vea.system.api.exception.enums.SystemExceptionEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author yanghq
 */
@Sign(false)
@RestController
public class UploadController {

	@PostMapping("/upload")
	public ResponseResult upload(@RequestParam("file") MultipartFile file,
			@RequestParam(value = "schoolId", required = false) Long schoolId) throws Exception {
		if (file.isEmpty()) {
			throw new SystemException(SystemExceptionEnum.UPLOAD_FILE_NOT_EMPTY);
		}
		if (StringUtils.isBlank(file.getOriginalFilename())) {
			throw new SystemException(SystemExceptionEnum.UPLOAD_FILE_NAME_NOT_EMPTY);
		}
		if (schoolId == null) {
			throw new SystemException(DefaultUserExceptionEnum.USER_OPERATION_ERROR);
		}
		// 上传文件
		String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		String url = UploadUtils.upload(file.getBytes(), "/" + schoolId, suffix);

		return ResponseResult.success("", url);
	}

}
