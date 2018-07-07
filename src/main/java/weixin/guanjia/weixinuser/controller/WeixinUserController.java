package weixin.guanjia.weixinuser.controller;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
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
import org.jeewx.api.wxuser.tag.JwTagAPI;
import org.jeewx.api.wxuser.tag.model.WxTag;
import org.jeewx.api.wxuser.user.JwUserAPI;
import org.jeewx.api.wxuser.user.model.Wxuser;

import weixin.guanjia.account.service.WeixinAccountServiceI;
import weixin.guanjia.tag.entity.TagEntity;
import weixin.guanjia.weixinuser.entity.WeixinUserEntity;
import weixin.guanjia.weixinuser.service.WeixinUserServiceI;

/**   
 * @Title: Controller
 * @Description: 微信用户
 * @author zhangdaihao
 * @date 2017-05-01 14:24:28
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/weixinUserController")
public class WeixinUserController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(WeixinUserController.class);

	@Autowired
	private WeixinUserServiceI weixinUserService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private WeixinAccountServiceI weixinAccountService;

	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	/**
	 * 微信用户列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "weixinUser")
	public ModelAndView weixinUser(HttpServletRequest request) {
		return new ModelAndView("weixin/guanjia/weixinuser/weixinUserList");
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
	public void datagrid(WeixinUserEntity weixinUser,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(WeixinUserEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, weixinUser, request.getParameterMap());
		this.weixinUserService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除微信用户
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(WeixinUserEntity weixinUser, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		weixinUser = systemService.getEntity(WeixinUserEntity.class, weixinUser.getId());
		message = "微信用户删除成功";
		weixinUserService.delete(weixinUser);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加微信用户
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(WeixinUserEntity weixinUser, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(weixinUser.getId())) {
			message = "微信用户更新成功";
			WeixinUserEntity t = weixinUserService.get(WeixinUserEntity.class, weixinUser.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(weixinUser, t);
				weixinUserService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
				message = "微信用户更新失败";
			}
		} else {
			message = "微信用户添加成功";
			weixinUserService.save(weixinUser);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 微信用户列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(WeixinUserEntity weixinUser, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(weixinUser.getId())) {
			weixinUser = weixinUserService.getEntity(WeixinUserEntity.class, weixinUser.getId());
			req.setAttribute("weixinUserPage", weixinUser);
		}
		return new ModelAndView("weixin/guanjia/weixinuser/weixinUser");
	}
	
	@RequestMapping(params="doSynch",method = RequestMethod.GET)
	@ResponseBody
	public AjaxJson doSynch(){
			AjaxJson j = new AjaxJson();
			try {
				String accessToken = weixinAccountService.getAccessToken();
				List<Wxuser> ls = JwUserAPI.getAllWxuser(accessToken, null);
				for(Wxuser po:ls){
					//判断数据库是否存在，无则插入
					WeixinUserEntity w = weixinUserService.getEntity(WeixinUserEntity.class, po.getOpenid().toString());
					if(w==null){
						w = new WeixinUserEntity();
						w.setId(po.getOpenid()+"");
						w.setNickname(po.getNickname());
						w.setSex(po.getSex());
						w.setCity(po.getCity());
						w.setCountry(po.getCountry());
						w.setProvince(po.getProvince());
						w.setLanguage(po.getLanguage());
						w.setHeadimgurl(po.getHeadimgurl());
						w.setSubscribe(po.getSubscribe());
						w.setSubscribeTime(po.getSubscribe_time());
						w.setUnionid(po.getUnionid());
						w.setRemark(po.getRemark());
//						if(po.getTagid_list()!=null&&po.getTagid_list().size()>0){
//							w.setTagidList(po.getTagid_list().get(0).toString());
//						}
						String[] tl = po.getTagid_list();
						if(po.getTagid_list()!=null&&po.getTagid_list().length>0){
							w.setTagidList(tl[0].toString());
						}
						weixinUserService.save(w);
						message="同步标签成功";
						systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
					}else{
						w.setNickname(po.getNickname());
						w.setSex(po.getSex());
						w.setCity(po.getCity());
						w.setCountry(po.getCountry());
						w.setProvince(po.getProvince());
						w.setLanguage(po.getLanguage());
						w.setHeadimgurl(po.getHeadimgurl());
						w.setSubscribe(po.getSubscribe());
						w.setSubscribeTime(po.getSubscribe_time());
						w.setUnionid(po.getUnionid());
						w.setRemark(po.getRemark());
//						if(po.getTagid_list()!=null&&po.getTagid_list().size()>0){
//							w.setTagidList(po.getTagid_list().get(0).toString());
//						}
						String[] tl = po.getTagid_list();
						if(po.getTagid_list()!=null&&po.getTagid_list().length>0){
							w.setTagidList(tl[0].toString());
						}
						weixinUserService.saveOrUpdate(w);
						message="同步标签成功";
						systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
					}
				}
				
				j.setSuccess(true);
				j.setMsg(message);
			} catch (Exception e) {
				org.jeecgframework.core.util.LogUtil.info(e.getMessage());
//			    e.getStackTrace();
				e.printStackTrace();
				j.setSuccess(false);
				message="同步标签失败";
			}
			j.setMsg(message);
			return j;
	}

}
