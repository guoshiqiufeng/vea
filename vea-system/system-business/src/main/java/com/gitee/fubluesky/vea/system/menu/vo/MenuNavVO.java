package com.gitee.fubluesky.vea.system.menu.vo;

import com.gitee.fubluesky.vea.system.api.domain.Menu;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * @author yanghq
 * @version 1.0
 * @since 2021-08-18 10:56
 */
@Data
public class MenuNavVO implements Serializable {

	private static final long serialVersionUID = -4962873003271766336L;

	private List<Menu> menuList;

	private Set<String> permissions;

}
