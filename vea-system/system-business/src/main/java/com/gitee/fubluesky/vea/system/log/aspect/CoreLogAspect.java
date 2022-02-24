package com.gitee.fubluesky.vea.system.log.aspect;

import com.gitee.fubluesky.kernel.auth.api.LoginApi;
import com.gitee.fubluesky.kernel.auth.api.pojo.login.LoginUser;
import com.gitee.fubluesky.kernel.core.util.IpUtils;
import com.gitee.fubluesky.vea.system.api.domain.LogDomain;
import com.gitee.fubluesky.vea.system.log.thread.LogThread;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Map;

/**
 * 系统日志，切面处理类
 */
@Slf4j
@Aspect
@Component
@SuppressWarnings("all")
public class CoreLogAspect {

	@Resource
	private LogThread logThread;

	@Resource
	private LoginApi loginApi;

	@Resource
	private HttpServletRequest request;

	@Pointcut("execution(* com.gitee.fubluesky..controller.*.*(..))")
	public void controllerPointCut() {
	}

	@Pointcut("execution(* com.gitee.fubluesky.kernel.db.mybatisplus.pojo.BaseRestController.*(..)))")
	public void baseControllerPointCut() {
	}

	@Around("controllerPointCut() || baseControllerPointCut()")
	public Object update(ProceedingJoinPoint point) throws Throwable {
		long beginTime = System.currentTimeMillis();
		// 执行方法
		Object result = point.proceed();
		// 执行时长(毫秒)
		long time = System.currentTimeMillis() - beginTime;

		// 保存日志
		saveCoreLog(point, time);

		return result;
	}

	private void saveCoreLog(ProceedingJoinPoint joinPoint, long time) {
		try {
			MethodSignature signature = (MethodSignature) joinPoint.getSignature();

			LogDomain coreLogDomain = new LogDomain();

			// 请求的方法名
			String className = joinPoint.getTarget().getClass().getName();

			coreLogDomain.setOperation(className);

			String module = className.substring(className.lastIndexOf(".") + 1);
			coreLogDomain.setModule(module);

			String methodName = signature.getName();
			coreLogDomain.setMethod(className + "." + methodName + "()");

			// update
			String mName = methodName.toLowerCase();
			if (StringUtils.contains(mName, "update")) {
				coreLogDomain.setMotion("update");
			}
			else if (StringUtils.contains(mName, "save") || StringUtils.contains(mName, "insert")) {
				coreLogDomain.setMotion("insert");
			}
			else if (StringUtils.contains(mName, "delete") || StringUtils.contains(mName, "remove")) {
				coreLogDomain.setMotion("delete");
			}
			else {
				return;
			}

			// 请求的参数
			Object[] args = joinPoint.getArgs();

			try {
				String p = new Gson().toJson(args);
				coreLogDomain.setParams(p);
			}
			catch (Exception e) {
				coreLogDomain.setParams(Arrays.toString(args));
			}

			try {
				String p = new Gson().toJson(args[0]);
				if (StringUtils.isNotBlank(p)) {
					Map map = new Gson().fromJson(p, Map.class);
					String schoolId = map.get("schoolId") + "";
					if (map.containsKey("schoolId") && StringUtils.isNotBlank(schoolId)) {
						coreLogDomain.setSchoolId(Long.parseLong(schoolId));
					}
				}
			}
			catch (Exception e) {
				log.error(e.getMessage());
			}
			String appId = request.getHeader("appId");
			// 设置IP地址
			coreLogDomain.setIp(IpUtils.getIpAddress(request));
			LoginUser user = loginApi.getLoginUser();
			// 用户名
			coreLogDomain.setUsername(user.getAccount());
			coreLogDomain.setUserId(user.getUserId());
			coreLogDomain.setNickname(user.getNickname());
			Integer type = 1;
			if ("brand".equals(appId)) {
				type = 2;
			}
			else if ("mobile".equals(appId)) {
				type = 3;
			}
			else if ("miniProgram".equals(appId)) {
				type = 4;
			}
			coreLogDomain.setType(type);

			coreLogDomain.setTime(time);
			// 保存系统日志
			logThread.run(coreLogDomain);
		}
		catch (Exception e) {
			log.error("保存日志失败：" + e);
		}
	}

}
