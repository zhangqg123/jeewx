package weixin.cms.dao;

import java.util.List;
import java.util.Map;

import org.jeecgframework.minidao.annotation.Arguments;
import org.jeecgframework.minidao.annotation.MiniDao;
import org.jeecgframework.minidao.annotation.ResultType;

import weixin.cms.entity.CmsRecordEntity;

@MiniDao
public interface CmsRecordDao{

	@Arguments( { "cmsRecordEntity", "page", "rows" })
	@ResultType(CmsRecordEntity.class)
	public List<CmsRecordEntity> list(CmsRecordEntity cmsArticleEntity);

	@Arguments( { "cmsRecordEntity", "page", "rows" })
	@ResultType(CmsRecordEntity.class)
	public List<CmsRecordEntity> list(CmsRecordEntity cmsArticleEntity,
			int page, int rows);

	@Arguments( { "params" })
	@ResultType(CmsRecordEntity.class)
	public List<CmsRecordEntity> listByMap(Map params);

	@Arguments( { "params", "page", "rows" })
	@ResultType(CmsRecordEntity.class)
	public List<CmsRecordEntity> listByMap(Map params, int page, int rows);

	@Arguments( { "params" })
	public Integer getCount(Map params);

}
