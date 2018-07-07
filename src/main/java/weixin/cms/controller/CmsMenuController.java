package weixin.cms.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.ComboTree;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.model.json.TreeGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.oConvertUtils;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.tag.vo.easyui.TreeGridModel;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import weixin.cms.entity.AdEntity;
import weixin.cms.entity.CmsMenuEntity;
import weixin.cms.service.CmsMenuServiceI;
import weixin.guanjia.account.service.WeixinAccountServiceI;
import weixin.guanjia.menu.entity.MenuEntity;

/**
 * @Title: Controller
 * @Description: 后台管理：文章标题栏目
 * @author zhangdaihao
 * @date 2014-06-10 20:07:00
 * @version V1.0
 * 
 */
@Controller
@RequestMapping("/cmsMenuController")
public class CmsMenuController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(CmsMenuController.class);

	@Autowired
	private CmsMenuServiceI cmsMenuService;
	@Autowired
	private WeixinAccountServiceI weixinAccountService;
	@Autowired
	private SystemService systemService;
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * cms首页
	 * 
	 * @return
	 */
	@RequestMapping(params = "index")
	public ModelAndView index(HttpServletRequest request, String accountid,
			String userid) {
		List<CmsMenuEntity> columnList = cmsMenuService.findByProperty(
				CmsMenuEntity.class, "accountid", accountid);
		List<AdEntity> adList = systemService.findByProperty(AdEntity.class,
				"accountid", accountid);
		request.setAttribute("columnList", columnList);
		request.setAttribute("adList", adList);
		request.setAttribute("accountid", accountid);
		request.setAttribute("userid", userid);
		return new ModelAndView("weixin/cms/index");
	}

	/**
	 * 栏目管理列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "menu")
	public ModelAndView menu(HttpServletRequest request) {
		return new ModelAndView("weixin/cms/cmsMenuList");
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
	public void datagrid(CmsMenuEntity menu, HttpServletRequest request,
			HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(CmsMenuEntity.class, dataGrid);
		cq.eq("accountid", ResourceUtil.getShangJiaAccountId());
		// 查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq,
				menu, request.getParameterMap());
		this.cmsMenuService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	@RequestMapping(params = "cmsDatagrid")
	@ResponseBody
	public List<TreeGrid> cmsDatagrid(TreeGrid treegrid,
			HttpServletRequest request, HttpServletResponse response,
			DataGrid dataGrid) {

		CriteriaQuery cq = new CriteriaQuery(CmsMenuEntity.class);
		cq.eq("accountid", ResourceUtil.getWeiXinAccountId());
//		cq.eq("accountid", ResourceUtil.getShangJiaAccountId());
		if (treegrid.getId() != null) {
			cq.eq("cmsMenuEntity.id", treegrid.getId());
		} else {

			cq.isNull("cmsMenuEntity");
		}

//		cq.addOrder("orders", SortDirection.asc);
		cq.add();

		List<CmsMenuEntity> cmsMenuList = systemService.getListByCriteriaQuery(cq,
				false);
		List<TreeGrid> treeGrids = new ArrayList<TreeGrid>();
		TreeGridModel treeGridModel = new TreeGridModel();
		// treeGridModel.setIcon("orders");
		treeGridModel.setTextField("name");
		treeGridModel.setParentText("url");
//		treeGridModel.setOrder("orders");
		treeGridModel.setSrc("type");
		treeGridModel.setIdField("id");
		treeGridModel.setChildList("cmsMenuList");
		// 添加排序字段
		treeGrids = systemService.treegrid(cmsMenuList, treeGridModel);
		return treeGrids;
	}
	
	
	@RequestMapping(params = "treeMenu")
	@ResponseBody
	public List<TreeGrid> treeMenu(HttpServletRequest request, ComboTree comboTree) {
		CriteriaQuery cq = new CriteriaQuery(CmsMenuEntity.class);
		if (StringUtil.isNotEmpty(comboTree.getId())) {
			cq.eq("cmsMenuEntity.id", comboTree.getId());
		}
		if (StringUtil.isEmpty(comboTree.getId())) {
			cq.isNull("cmsMenuEntity.id");
		}
		cq.eq("accountid", ResourceUtil.getWeiXinAccountId());
		cq.add();
		List<CmsMenuEntity> cmsMenuList = systemService.getListByCriteriaQuery(cq,
				false);
		
		List<TreeGrid> treeGrids = new ArrayList<TreeGrid>();
		TreeGridModel treeGridModel = new TreeGridModel();
		treeGridModel.setTextField("name");
		treeGridModel.setParentText("url");
//		treeGridModel.setOrder("orders");
		treeGridModel.setSrc("type");
		treeGridModel.setIdField("id");
		treeGridModel.setChildList("cmsMenuList");
		treeGrids = systemService.treegrid(cmsMenuList, treeGridModel);
		return treeGrids;
	}

	/**
	 * 删除栏目管理
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(CmsMenuEntity menu, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		menu = systemService.getEntity(CmsMenuEntity.class, menu.getId());
		message = "栏目管理删除成功";
		cmsMenuService.delete(menu);
		systemService.addLog(message, Globals.Log_Type_DEL,
				Globals.Log_Leavel_INFO);

		j.setMsg(message);
		return j;
	}

	/**
	 * 添加栏目管理
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(CmsMenuEntity menu, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String accountId = ResourceUtil.getShangJiaAccountId();
		if (StringUtil.isEmpty(accountId) || "-1".equals(accountId)) {
			j.setSuccess(false);
			message = "请添加一个公众帐号。";
		} else {
			if (StringUtil.isNotEmpty(menu.getId())) {
				message = "栏目管理更新成功";
				CmsMenuEntity t = cmsMenuService.get(CmsMenuEntity.class, menu
						.getId());
				try {
					MyBeanUtils.copyBeanNotNull2Bean(menu, t);
					cmsMenuService.saveOrUpdate(t);
					systemService.addLog(message, Globals.Log_Type_UPDATE,
							Globals.Log_Leavel_INFO);
				} catch (Exception e) {
					e.printStackTrace();
					message = "栏目管理更新失败";
				}
			} else {
				message = "栏目管理添加成功";
				menu.setAccountid(accountId);
				cmsMenuService.save(menu);
				systemService.addLog(message, Globals.Log_Type_INSERT,
						Globals.Log_Leavel_INFO);
			}
		}
		j.setMsg(message);
		return j;
	}
	
	@RequestMapping(params = "su")
	@ResponseBody
	public AjaxJson su(CmsMenuEntity cmsMenuEntity, HttpServletRequest req,String fatherName) {
		AjaxJson j = new AjaxJson();
		String id = oConvertUtils.getString(req.getParameter("id"));
			if (StringUtil.isNotEmpty(cmsMenuEntity.getId())) {
				
				CmsMenuEntity tempMenu = cmsMenuService.getEntity(CmsMenuEntity.class, cmsMenuEntity.getId());
				//update-begin--author:zhangjiaqiang date:20161213 for:修订bug 对已有的一级菜单进行编辑后保存无效 
			    if(oConvertUtils.isNotEmpty(fatherName)){
			    	CmsMenuEntity menuTemp=new CmsMenuEntity();
			    	menuTemp.setId(fatherName);
			    	tempMenu.setCmsMenuEntity(menuTemp);
			    	tempMenu.getCmsMenuEntity().getId();
			    }else{
			    	tempMenu.setCmsMenuEntity(null);
			    }
			    //update-end--author:zhangjiaqiang date:20161213 for:修订bug 对已有的一级菜单进行编辑后保存无效
				this.message = "更新" + tempMenu.getName() + "的菜单信息信息成功！";
				try {
					MyBeanUtils.copyBeanNotNull2Bean(cmsMenuEntity, tempMenu);
					cmsMenuService.saveOrUpdate(tempMenu);
					systemService.addLog(message, Globals.Log_Type_UPDATE,Globals.Log_Leavel_INFO);
				} catch (Exception e) {
					this.message = "更新" + tempMenu.getName() + "的菜单信息信息成功！";
					systemService.addLog(message, Globals.Log_Type_UPDATE,Globals.Log_Leavel_INFO);
					e.printStackTrace();
				}
	
			} else {
				this.message = "添加" + cmsMenuEntity.getName() + "的信息成功！";
				String fatherId = req.getParameter("fatherId");
				if (StringUtil.isNotEmpty(fatherName)) {
					CmsMenuEntity tempMenu = this.systemService.getEntity(CmsMenuEntity.class, fatherName);
					cmsMenuEntity.setCmsMenuEntity(tempMenu);
				}
				String accountId = ResourceUtil.getWeiXinAccountId();
				if (!"-1".equals(accountId)) {
					cmsMenuService.save(cmsMenuEntity);
				} else {
					j.setSuccess(false);
					j.setMsg("请添加一个公众帐号。");
				}
				systemService.addLog(message, Globals.Log_Type_INSERT,
						Globals.Log_Leavel_INFO);
			}
		return j;
	}

	/**
	 * 栏目管理列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(CmsMenuEntity menu, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(menu.getId())) {
			menu = cmsMenuService
					.getEntity(CmsMenuEntity.class, menu.getId());
			req.setAttribute("columnPage", menu);
		}
		return new ModelAndView("weixin/cms/cmsMenu");
	}
	
	@RequestMapping(params = "jumpSuView")
	public ModelAndView jumpSuView(CmsMenuEntity cmsMenuEntity, HttpServletRequest req) {

		org.jeecgframework.core.util.LogUtil.info("...menuEntity.getId()..." + cmsMenuEntity.getId());
		if (StringUtil.isNotEmpty(cmsMenuEntity.getId())) {
			cmsMenuEntity = this.systemService.getEntity(CmsMenuEntity.class,
					cmsMenuEntity.getId());
//			menu = cmsMenuService
//					.getEntity(CmsMenuEntity.class, menu.getId());
			req.setAttribute("columnPage", cmsMenuEntity);
			if (cmsMenuEntity.getCmsMenuEntity() != null
					&& cmsMenuEntity.getCmsMenuEntity().getId() != null) {
				req.setAttribute("fatherId", cmsMenuEntity.getCmsMenuEntity()
								.getId());
				req.setAttribute("fatherName", cmsMenuEntity.getCmsMenuEntity()
						.getName());
			}
		}
		String fatherId = req.getParameter("fatherId");
		if (StringUtil.isNotEmpty(fatherId)) {
			CmsMenuEntity fatherCmsMenuEntity = this.systemService.getEntity(
					CmsMenuEntity.class, fatherId);
			req.setAttribute("fatherId", fatherId);
			req.setAttribute("fatherName", fatherCmsMenuEntity.getName());
			org.jeecgframework.core.util.LogUtil.info(".....fatherName...."
					+ fatherCmsMenuEntity.getName());
		}
		return new ModelAndView("weixin/cms/cmsMenu");
	}
	
	
}
