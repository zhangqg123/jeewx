package weixin.guanjia.message.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.velocity.VelocityContext;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.p3.core.util.plugin.ViewVelocity;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.jeewx.api.core.req.model.user.Group;
import org.jeewx.api.wxsendmsg.JwSendMessageAPI;
import org.jeewx.api.wxsendmsg.model.SendMessageResponse;
import org.jeewx.api.wxsendmsg.model.WxArticle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import weixin.guanjia.account.entity.WeixinAccountEntity;
import weixin.guanjia.account.service.WeixinAccountServiceI;
import weixin.guanjia.message.entity.NewsItem;
import weixin.guanjia.message.entity.NewsTemplate;
import weixin.guanjia.message.entity.ReceiveText;
import weixin.guanjia.message.model.SendFilter;
import weixin.guanjia.message.model.TextItem;
import weixin.guanjia.message.model.TextMessageGroup;
import weixin.guanjia.message.model.TextMessageKf;
import weixin.guanjia.message.service.GroupMessageService;
import weixin.guanjia.message.utils.CatchImage;
import weixin.guanjia.tag.entity.TagEntity;
import weixin.guanjia.tag.service.TagServiceI;


/**
 * 文本消息收件箱
* 
 */
@Controller
@RequestMapping("/sendGroupMessageController")
public class SendGroupMessageController {
	
	private SystemService systemService;
	private String message;
	private GroupMessageService groupMessageService;
	@Autowired
	private WeixinAccountServiceI weixinAccountService;
	@Autowired
	private TagServiceI tagService;

	@Autowired
	public void setGroupMessageService(
			GroupMessageService groupMessageService) {
		this.groupMessageService = groupMessageService;
	}

	@Resource(name="systemService")
    public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}
	
	//跳转到图文信息详细页面
	@RequestMapping(params="getAllUploadNewsTemplate",method ={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView getAllUploadNewsTemplate(HttpServletResponse response,HttpServletRequest request) throws Exception{
		//查询所有模版
//			List<QywxNewstemplate> templateList = qywxNewstemplateDao.getAllQywxNewstemplate();
		String hql = "from NewsTemplate";
		org.jeecgframework.core.util.LogUtil.info("...hql..."+hql);
		List<NewsTemplate> templateList = this.systemService.findByQueryString(hql);

		//遍历模版
		//遍历每条模搬对应的子表记录
		for(NewsTemplate  template : templateList){
			String templateId=template.getId();
			String hql2 = "from NewsItem where newsTemplate.id='"+templateId+"' order by orders asc";
			org.jeecgframework.core.util.LogUtil.info("...hql2..."+hql2);
			List<NewsItem> item = this.systemService.findByQueryString(hql2);
			template.setNewsItemList(item);
		}
	    
		request.setAttribute("templateList", templateList);
		return new ModelAndView("weixin/guanjia/message/showGroupMessageNews");
 		
//		String yuming=ConfigUtil.getProperty("ftp_img_domain");
//		velocityContext.put("yuming", yuming);
	}
	
	//文本框内显示图片的详细信息
	@RequestMapping(params="toGroupNewsSend",method ={RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public AjaxJson toGroupNewsSend(HttpServletResponse response,HttpServletRequest request ){
		AjaxJson j = new AjaxJson();
		try{
			String templateId=request.getParameter("templateId");
			//根据templateId查出item集合。
			String hql2 = "from NewsItem where newsTemplate.id='"+templateId+"' order by orders asc";
			org.jeecgframework.core.util.LogUtil.info("...hql2..."+hql2);
			List<NewsItem> item = this.systemService.findByQueryString(hql2);
			j.setObj(item);
			
		} catch (Exception e) {
	//	    log.info(e.getMessage());
			j.setSuccess(false);
			j.setMsg("显示失败!");
		}
		return j;
	
	}
		
	@RequestMapping(params="sendGroupMessage")
	public ModelAndView responseMessage(HttpServletRequest req){
		String id = req.getParameter("id");
		req.setAttribute("id", id);
		List<TagEntity> tagList = tagService.getList(TagEntity.class);
		req.setAttribute("tagList", tagList);
		return new ModelAndView("weixin/guanjia/message/sendgroupmessage");
	}
	
	// 群发信息
	@RequestMapping(params = "send", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public AjaxJson toGroupTextSend(HttpServletResponse response, HttpServletRequest request) {
//		JwSendMessageAPI jwSendMessageAPI = new JwSendMessageAPI();

		AjaxJson j = new AjaxJson();
		try {
			JSONObject receive = null;

			String toallUser=request.getParameter("toModel");
			boolean toall = false;
			if(toallUser.equals("2")){
				toall=true;
			}
			String groupId = request.getParameter("toparty");// 部门Id
			String msgtype = request.getParameter("msgtype");// 类型
			String param = request.getParameter("param");// 文本框的内容
			String accessTocken = weixinAccountService.getAccessToken();
			SendMessageResponse resp = null;
			if ("text".equals(msgtype)) {
				Group group = new Group();
				group.setId(groupId);
				resp =JwSendMessageAPI.sendMessageToGroupOrAllWithText(accessTocken, toall, group, param);
			}
			// 发送图文信信息
			String templateId = request.getParameter("templateId");
			if ("news".equals(msgtype)) {
				// 获取图文信息
				String hql2 = "from NewsItem where newsTemplate.id='"+templateId+"' order by orders asc";
				org.jeecgframework.core.util.LogUtil.info("...hql2..."+hql2);
				List<NewsItem> items = this.systemService.findByQueryString(hql2);
				String bos_upload = ResourceUtil.getConfigByName("bos_upload");
				String domain=ResourceUtil.getConfigByName("domain");
				List<WxArticle> wxArticles = new ArrayList<WxArticle>();
				for(NewsItem  item : items){
					WxArticle article = new WxArticle();
					article.setAuthor(item.getAuthor());
					String content = item.getContent();
					CatchImage cm = new CatchImage();
					//获得html文本内容
					String HTML = content;
					//获取图片标签
					List<String> imgUrl = cm.getImageUrl(HTML);
					List<String> imgSrc = null;
					List<String> uploadSrc = new ArrayList<String>();
					if(imgUrl.size()>0){
					//获取图片src地址
						imgSrc = cm.getImageSrc(imgUrl);
						for (String is :imgSrc){
							String tmpSrc = is.replace(domain+"/",bos_upload);
							String newSrc = JwSendMessageAPI.uploadImg(accessTocken, tmpSrc);
							content=content.replace(is, newSrc);
							uploadSrc.add(newSrc);
						}
					}
					
					System.out.println("uploadSrc:"+uploadSrc);

					article.setContent(content);
					article.setDigest(item.getDescription());
					article.setShow_cover_pic("1");
					article.setTitle(item.getTitle());
//					newsarticle.setPicurl(domain + "/" + picurl);
					String fname = item.getImagePath().substring(item.getImagePath().lastIndexOf("/")+1);
					String dname = item.getImagePath().substring(0,item.getImagePath().lastIndexOf("/")+1);
					article.setFileName(fname);
					article.setFilePath(bos_upload+dname);
					wxArticles.add(article);
				}
				Group group = new Group();
				group.setId(groupId);
				resp = JwSendMessageAPI.sendMessageToGroupOrAllWithArticles(accessTocken,toall, group, wxArticles);
				System.out.println(resp);
			}
			System.out.println(resp);
			String code = resp.getErrcode();
			if("0".equals(code)){
				j.setSuccess(true);
				j.setObj("sucess");
				j.setMsg("发送成功");
			}
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("发送失败");
		}
		return j;

	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}