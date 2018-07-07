package weixin.guanjia.tag.controller;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
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
import org.jeewx.api.core.exception.WexinReqException;
import org.jeewx.api.wxuser.tag.JwTagAPI;
import org.jeewx.api.wxuser.tag.model.WxTag;

import weixin.guanjia.account.service.WeixinAccountServiceI;
import weixin.guanjia.tag.entity.TagEntity;
import weixin.guanjia.tag.service.TagServiceI;

/**   
 * @Title: Controller
 * @Description: 标签
 * @author zhangdaihao
 * @date 2017-04-30 12:51:06
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tagController")
public class TagController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TagController.class);

	@Autowired
	private TagServiceI tagService;
	@Autowired
	private SystemService systemService;
	private String message;
	@Autowired
	private WeixinAccountServiceI weixinAccountService;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	/**
	 * 标签列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "tag")
	public ModelAndView tag(HttpServletRequest request) {
		return new ModelAndView("weixin/guanjia/tag/tagList");
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
	public void datagrid(TagEntity tag,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TagEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tag, request.getParameterMap());
		this.tagService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除标签
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(TagEntity tag, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String accessToken = weixinAccountService.getAccessToken();
		JSONObject result = JwTagAPI.delete(accessToken,tag.getId());
		if (null != result) {
			if (result.has("errcode") && result.getInt("errcode") != 0) {
				message = "标签更新失败";
			}else{
				tag = systemService.getEntity(TagEntity.class, tag.getId());
				message = "标签删除成功";
				tagService.delete(tag);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加标签
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(TagEntity tag, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String accessToken = weixinAccountService.getAccessToken();
//		List<WxTag> ls = JwTagAPI.getTags(accessToken);
		if (StringUtil.isNotEmpty(tag.getId())) {
			TagEntity t = tagService.get(TagEntity.class, tag.getId());
			try {
				JSONObject result = JwTagAPI.updateTag(accessToken, Integer.valueOf(tag.getId()), tag.getName());
				if (null != result) {
					if (result.has("errcode") && result.getInt("errcode") != 0) {
						message = "标签更新失败";
					}else{
						MyBeanUtils.copyBeanNotNull2Bean(tag, t);
						tagService.saveOrUpdate(t);
						message = "标签更新成功";
						systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
					}
				}
			}	catch (Exception e) {
				e.printStackTrace();
				message = "标签更新失败";
			}
		} else {
			try {
				JSONObject result = JwTagAPI.createTag(accessToken, tag.getName());
				//{"tag":{"id":106,"name":"test3"}}
				if (null != result) {
					if (result.has("tag")) {
						String tagStr = result.getString("tag");	
						JSONObject jsonTag=JSONObject.fromObject(tagStr);
						int id = jsonTag.getInt("id");	
						message = "标签添加成功";
						tag.setId(id+"");
						tagService.save(tag);
						systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
					}else{
						message = "标签添加失败";
					}
				}
			} catch (WexinReqException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 标签列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(TagEntity tag, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tag.getId())) {
			tag = tagService.getEntity(TagEntity.class, tag.getId());
			req.setAttribute("tagPage", tag);
		}
		return new ModelAndView("weixin/guanjia/tag/tag");
	}
	
	@RequestMapping(params="doSynch",method = RequestMethod.GET)
	@ResponseBody
	public AjaxJson doSynch(){
			AjaxJson j = new AjaxJson();
			try {
				String accessToken = weixinAccountService.getAccessToken();
				List<WxTag> ls = JwTagAPI.getTags(accessToken);
				for(WxTag po:ls){
					//判断数据库是否存在，无则插入
					TagEntity t = tagService.getEntity(TagEntity.class, po.getId().toString());
					if(t==null){
						t = new TagEntity();
						t.setId(po.getId()+"");
						t.setName(po.getName());
						t.setCount(po.getCount());
						tagService.save(t);
						message="同步标签成功";
						systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
					}else{
						t.setId(po.getId()+"");
						t.setName(po.getName());
						t.setCount(po.getCount());
						tagService.saveOrUpdate(t);
						message="同步标签成功";
						systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
					}
				}
				
				j.setSuccess(true);
				j.setMsg(message);
			} catch (Exception e) {
//			    log.info(e.getMessage());
			    e.getStackTrace();
				j.setSuccess(false);
				message="同步标签失败";
			}
			j.setMsg(message);
			return j;
	}

}
