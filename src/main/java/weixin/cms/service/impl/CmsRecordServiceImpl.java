package weixin.cms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import weixin.cms.dao.CmsArticleDao;
import weixin.cms.dao.CmsRecordDao;
import weixin.cms.service.CmsRecordServiceI;

import java.util.Map;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;

@Service("cmsRecordService")
@Transactional
public class CmsRecordServiceImpl extends CommonServiceImpl implements CmsRecordServiceI {
	@Autowired
	private CmsRecordDao cmsRecordDao;
	@Override
	public int getCount(Map params) {
		return cmsRecordDao.getCount(params);
	}
}