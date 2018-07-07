package weixin.guanjia.weixinuser.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;

import weixin.guanjia.weixinuser.service.WeixinUserServiceI;

@Service("weixinUserService")
@Transactional
public class WeixinUserServiceImpl extends CommonServiceImpl implements WeixinUserServiceI {
	
}