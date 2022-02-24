package com.gitee.fubluesky.vea.system.account.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.gitee.fubluesky.kernel.db.mybatisplus.pojo.BaseServiceImpl;
import com.gitee.fubluesky.vea.system.account.mapper.AccountMapper;
import com.gitee.fubluesky.vea.system.account.service.IAccountService;
import com.gitee.fubluesky.vea.system.account.thread.AccountThread;
import com.gitee.fubluesky.vea.system.api.domain.Account;
import com.gitee.fubluesky.vea.system.api.exception.SystemException;
import com.gitee.fubluesky.vea.system.api.exception.enums.SystemExceptionEnum;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.List;
import java.util.Random;

/**
 * <p>
 * 账号库 服务实现类
 * </p>
 *
 * @author
 * @since 2020-07-01
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AccountServiceImpl extends BaseServiceImpl<AccountMapper, Account> implements IAccountService {

	@Resource
	private AccountThread accountThread;

	@Override
	public Account findByUserName(String userName) {
		LambdaQueryWrapper<Account> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(Account::getUserName, userName);
		List<Account> list = this.list(queryWrapper);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public void creator(Long count) {
		// 循环次数
		int cycleSize = 0;
		// 循环创建成功的次数
		Integer cycleSaveSize = 0;
		// 循环成功总条数
		Integer cycleSaveCount = 0;
		while (count - cycleSaveSize > 0) {
			cycleSaveSize = saveAccount(count - cycleSaveCount, 8);
			cycleSaveCount += cycleSaveSize;
			cycleSize++;
			if (cycleSize == 6) {
				long l = count - cycleSaveCount;
				if (l > 0) {
					String message = MessageFormat.format(SystemExceptionEnum.ACCOUNT_CREATED_ERROR.getMessage(),
							"" + l);
					throw new SystemException(SystemExceptionEnum.ACCOUNT_CREATED_ERROR, message);
				}
			}
		}
	}

	/**
	 * 创建获取新账号
	 * @return
	 */
	@Override
	public Account getNewAccount() {
		Account account = this.saveAccount();
		account.setState(1);
		this.updateById(account);
		accountThread.run(1L);
		return account;
	}

	private Account saveAccount() {
		Account account = null;
		int countSize = 0;
		Random random = new Random();
		Integer min = getMin(8);
		Integer max = getMax(8);
		while (countSize == 0) {
			int un = random.nextInt(max) % (max - min + 1) + min;
			String username = String.valueOf(un);
			account = this.findByUserName(username);
			if (account == null) {
				account = new Account();
				account.setUserName(username);
				account.setState(0);
				this.save(account);
				countSize++;
			}
		}
		return account;
	}

	private Integer saveAccount(Long count, Integer num) {
		Account account = null;
		Random random = new Random();
		Integer min = getMin(num);
		Integer max = getMax(num);
		Integer countSize = 0;
		for (int i = 0; i < count; i++) {
			int un = random.nextInt(max) % (max - min + 1) + min;
			String username = String.valueOf(un);
			account = this.findByUserName(username);
			if (account == null) {
				account = new Account();
				account.setUserName(username);
				account.setState(0);
				this.save(account);
				countSize++;
			}
		}
		return countSize;
	}

	private Integer getMin(Integer size) {
		StringBuilder minStr = new StringBuilder("1");
		for (int i = 1; i < size; i++) {
			minStr.append("0");
		}
		return Integer.parseInt(minStr.toString());
	}

	private Integer getMax(Integer size) {
		StringBuilder maxStr = new StringBuilder("9");
		int maxNum = 10;
		if (size <= maxNum) {
			for (int i = 1; i < size; i++) {
				maxStr.append("9");
			}
		}
		else {
			maxStr = new StringBuilder("2147483647");
		}
		return Integer.parseInt(maxStr.toString());
	}

}
