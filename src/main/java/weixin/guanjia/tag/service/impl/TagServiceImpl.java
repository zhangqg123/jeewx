package weixin.guanjia.tag.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;

import weixin.guanjia.tag.service.TagServiceI;

@Service("tagService")
@Transactional
public class TagServiceImpl extends CommonServiceImpl implements TagServiceI {
	
}