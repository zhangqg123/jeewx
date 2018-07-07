package weixin.cms.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jeecg.qywx.base.dao.QywxGroupDao;
import com.jeecg.qywx.base.dao.QywxGzuserinfoDao;
import com.jeecg.qywx.base.entity.QywxGzuserinfo;

import weixin.cms.entity.CmsRecordEntity;
import weixin.cms.service.CmsRecordServiceI;

import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;

/**   
 * @Title: Controller
 * @Description: 文章记录
 * @author zhangdaihao
 * @date 2017-01-11 16:41:59
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/cmsRecordController")
public class CmsRecordController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(CmsRecordController.class);

	@Autowired
	private CmsRecordServiceI cmsRecordService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private QywxGzuserinfoDao qywxGzuserinfoDao;
	@Autowired
	private QywxGroupDao qywxGroupDao;

	
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	/**
	 * 文章记录列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "cmsRecord")
	public ModelAndView cmsRecord(HttpServletRequest request) {
		String articleid = request.getParameter("articleid");
		request.setAttribute("articleid", articleid);
		return new ModelAndView("weixin/cms/cmsRecordList");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(CmsRecordEntity cmsRecord,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		String articleid = request.getParameter("articleid");
		CriteriaQuery cq = new CriteriaQuery(CmsRecordEntity.class, dataGrid);
		cq.eq("articleid", articleid);
//		cq.eq(ACCOUNTID, ResourceUtil.getWeiXinAccountId());
		cq.add();

		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, cmsRecord, request.getParameterMap());
		this.cmsRecordService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除文章记录
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(CmsRecordEntity cmsRecord, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		cmsRecord = systemService.getEntity(CmsRecordEntity.class, cmsRecord.getId());
		message = "文章记录删除成功";
		cmsRecordService.delete(cmsRecord);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加文章记录
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(CmsRecordEntity cmsRecord, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		Long count = systemService.getCountForJdbcParam("SELECT COUNT(1) FROM weixin_cms_record WHERE articleid=\""+cmsRecord.getArticleid()+"\"",null);
		if(count>0){
			message="文件已签收";
			j.setMsg(message);
			return j;
		}
		java.util.Date  date=new java.util.Date();
		java.sql.Date createTime=new java.sql.Date(date.getTime());
		if (StringUtil.isNotEmpty(cmsRecord.getId())) {
			message = "文章记录更新成功";
			CmsRecordEntity t = cmsRecordService.get(CmsRecordEntity.class, cmsRecord.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(cmsRecord, t);
				cmsRecordService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
				message = "文章记录更新失败";
			}
		} else {
			QywxGzuserinfo user = qywxGzuserinfoDao.getByUserid(cmsRecord.getUserid());
			String name = null;
			String department=null;
			if (user != null) {
				name = user.getName();// 查出userid对应的name
				department=qywxGroupDao.get(user.getDepartment()).getName();
				message = "文件签收成功";
				cmsRecord.setName(name);
				cmsRecord.setDepartment(department);
				cmsRecord.setCreateDate(createTime);
				cmsRecordService.save(cmsRecord);
				systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
			}else{
				message="用户未关注企业号";
			}
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 文章记录列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(CmsRecordEntity cmsRecord, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(cmsRecord.getId())) {
			cmsRecord = cmsRecordService.getEntity(CmsRecordEntity.class, cmsRecord.getId());
			req.setAttribute("cmsRecordPage", cmsRecord);
		}
		return new ModelAndView("weixin/cms/cmsRecord");
	}
}
