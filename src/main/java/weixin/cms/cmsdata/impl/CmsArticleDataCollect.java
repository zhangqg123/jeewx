package weixin.cms.cmsdata.impl;

import java.util.List;
import java.util.Map;

import org.jeecgframework.core.util.ApplicationContextUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;

import weixin.cms.cmsdata.CmsDataCollectI;
import weixin.cms.common.CmsConstant;
import weixin.cms.common.CmsDataContent;
import weixin.cms.entity.CmsArticleEntity;
import weixin.cms.entity.CmsRecordEntity;
import weixin.cms.service.CmsArticleServiceI;
import weixin.cms.service.CmsRecordServiceI;

public class CmsArticleDataCollect implements CmsDataCollectI {
	
	@Override
	public void collect(Map<String, String> params) {
		CmsArticleServiceI cmsArticleService = (CmsArticleServiceI) ApplicationContextUtil.getContext().getBean("cmsArticleService");
		SystemService systemService = (SystemService) ApplicationContextUtil.getContext().getBean("systemService");
		
		String articleid = params.get("articleid") != null ? params.get("articleid").toString() : "-";
		CmsArticleEntity cmsArticleEntity = cmsArticleService.getCmsArticleEntity(articleid);
		if (cmsArticleEntity != null) {
			CmsDataContent.put(CmsConstant.CMS_DATA_MAP_ARTICLE, cmsArticleEntity);
			CmsDataContent.put(CmsConstant.CMS_DATA_STR_TITLE, cmsArticleEntity.getTitle());
		}else{
			CmsDataContent.put(CmsConstant.CMS_DATA_MAP_ARTICLE, new CmsArticleEntity());
			CmsDataContent.put(CmsConstant.CMS_DATA_STR_TITLE, "文章明细");
		}
		String res = CmsConstant.CMS_ROOT_PATH + "/" + params.get(CmsConstant.CMS_STYLE_NAME);
		//资源路径
		CmsDataContent.put(CmsConstant.BASE, res);
		if(params.get("qyUserId")!=null){
			Long count = systemService.getCountForJdbcParam("SELECT COUNT(1) FROM weixin_cms_record WHERE articleid=\""+articleid+"\"",null);
			if(count<1){
				CmsDataContent.put(CmsConstant.QYUSERID , params.get("qyUserId"));
				CmsDataContent.put(CmsConstant.SIGN, "文章签收");
			}else{
				if(CmsDataContent.get(CmsConstant.QYUSERID)!=null){
					CmsDataContent.remove(CmsConstant.QYUSERID);
				}
				if(CmsDataContent.get(CmsConstant.SIGN)!=null){
					CmsDataContent.remove(CmsConstant.SIGN);
				}
				CmsDataContent.put(CmsConstant.QYUSERID , "");
			}
		}else{
			if(CmsDataContent.get(CmsConstant.QYUSERID)!=null){
				CmsDataContent.remove(CmsConstant.QYUSERID);
			}
			if(CmsDataContent.get(CmsConstant.SIGN)!=null){
				CmsDataContent.remove(CmsConstant.SIGN);
			}
			CmsDataContent.put(CmsConstant.QYUSERID , "");
		}
		
	}

}
