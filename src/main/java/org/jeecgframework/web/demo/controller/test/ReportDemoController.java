package org.jeecgframework.web.demo.controller.test;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecgframework.web.system.service.SystemService;
import org.apache.batik.transcoder.Transcoder;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.JPEGTranscoder;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.fop.svg.PDFTranscoder;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.dao.jdbc.JdbcDao;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.model.json.Highchart;
import org.jeecgframework.core.util.DBTypeUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


/**
 * 报表demo控制器
 * 
 * @author xiehs
 * 
 */
@Controller
@RequestMapping("/reportDemoController")
public class ReportDemoController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ReportDemoController.class);
	private SystemService systemService;

	@Autowired
	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}

	
	@RequestMapping(params = "listAllxzStatisticByJdbc")
	public void listAllxzStatisticByJdbc(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
//		List<Map<String,Object>> maplist=systemService.findForJdbc("SELECT s.classname classname ,count(className) personcount FROM T_s_Student s group by s.className", null);
		List<Map<String,Object>> maplist=systemService.findForJdbc("SELECT x.name name ,count(column_id) y FROM xzkx_article s left join xzkx_menu x on s.column_id=x.id group by s.column_id order by y desc", null);
//		Long countArticle = systemService.getCountForJdbc("SELECT COUNT(1) FROM T_S_student WHERE 1=1");
		Long countArticle = systemService.getCountForJdbc("SELECT COUNT(0) FROM xzkx_article WHERE 1=1");
		for(Map map:maplist){
			Long articlecount = Long.parseLong(map.get("y").toString());
			Double  percentage = 0.0;
			if (articlecount != null && articlecount.intValue() != 0) {
				percentage = new Double(articlecount)/countArticle;
			}
			
			map.put("rate", String.format("%.2f", percentage*100)+"%");
			String tmpName = map.get("name").toString();
			map.put("name", tmpName.replace("珲春市", ""));
		}
		Long count = 0L;
		if(JdbcDao.DATABSE_TYPE_SQLSERVER.equals(DBTypeUtil.getDBType())){
			count = systemService.getCountForJdbcParam("select count(0) num from (SELECT s.className  classname ,count(className) totalclass FROM T_s_Student  s group by s.className) as t( classname, totalclass)",null);
		}else{
			count = systemService.getCountForJdbcParam("select count(0) num from (SELECT x.name name ,count(column_id) y FROM xzkx_article s left join xzkx_menu x on s.column_id=x.id group by s.column_id order by y desc) t",null);
		}
		
		dataGrid.setTotal(count.intValue());
		dataGrid.setResults(maplist);
		TagUtil.datagrid(response, dataGrid);
	}
	
	@RequestMapping(params = "listAllzfStatisticByJdbc")
	public void listAllzfStatisticByJdbc(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		String fromDate=request.getParameter("fromDate");
		String toDate=request.getParameter("toDate");
		String sb = sqlstr(fromDate,toDate);
		String sqlstr = "SELECT agent_name name ,num y FROM ("+sb+") s";
		List<Map<String,Object>> maplist=systemService.findForJdbc(sqlstr, null);
		Long countArticle = systemService.getCountForJdbc("SELECT sum(num) FROM ("+sb+") s WHERE 1=1");
//		List<Map<String,Object>> maplist=systemService.findForJdbc("SELECT x.name name ,count(column_id) y FROM xzkx_article s left join xzkx_menu x on s.column_id=x.id group by s.column_id order by y desc", null);
//		Long countArticle = systemService.getCountForJdbc("SELECT COUNT(0) FROM xzkx_article WHERE 1=1");
		for(Map map:maplist){
			Long articlecount = Long.parseLong(map.get("y").toString());
			Double  percentage = 0.0;
			if (articlecount != null && articlecount.intValue() != 0) {
				percentage = new Double(articlecount)/countArticle;
			}
			
			map.put("rate", String.format("%.2f", percentage*100)+"%");
//			String tmpName = map.get("name").toString();
//			map.put("name", tmpName.replace("珲春市", ""));
		}
		Long count = 0L;
		if(JdbcDao.DATABSE_TYPE_SQLSERVER.equals(DBTypeUtil.getDBType())){
			count = systemService.getCountForJdbcParam("select count(0) num from (SELECT s.className  classname ,count(className) totalclass FROM T_s_Student  s group by s.className) as t( classname, totalclass)",null);
		}else{
			count = systemService.getCountForJdbcParam("select count(0) num from ("+sb+") t",null);
		}
		
		dataGrid.setTotal(count.intValue());
		dataGrid.setResults(maplist);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 统计集合页面
	 * 
	 * @return
	 */
	@RequestMapping(params = "xiangzhenStatisticTabs")
	public ModelAndView xiangzhenStatisticTabs(HttpServletRequest request) {
		return new ModelAndView("jeecg/demo/base/report/xiangzhen");
	}
	
	@RequestMapping(params = "zhengfuStatisticTabs")
	public ModelAndView zhengfuStatisticTabs(HttpServletRequest request) {
		return new ModelAndView("jeecg/demo/base/report/zhengfu");
	}

	/**
	 * 报表数据生成
	 * 
	 * @return
	 */
	@RequestMapping(params = "xiangzhenCount")
	@ResponseBody
	public List<Highchart> xiangzhenCount(HttpServletRequest request,String reportType, HttpServletResponse response) {
		String fromDate=request.getParameter("fromDate");
		String toDate=request.getParameter("toDate");
		String sqlStr=null;
		if(fromDate!=null&&!"".equals(fromDate)&&toDate!=null&&!"".equals(toDate)){
			sqlStr="SELECT x.name name ,count(s.column_id) y FROM  xzkx_menu x "
				+ "left join xzkx_article s on s.column_id=x.id and s.publish_date "
				+ "between '"+fromDate+"' and '"+toDate+"'  group by x.id order by y desc;";
		}else{
			sqlStr="SELECT x.name name ,count(s.column_id) y FROM  xzkx_menu x "
				+ "left join xzkx_article s on s.column_id=x.id group by x.id order by y desc";
		}
		List<Highchart> list = new ArrayList<Highchart>();
		StringBuffer sb = new StringBuffer();
		List<Map<String,Object>> userBroswerList=systemService.findForJdbc(sqlStr, null);
		Long count = systemService.getCountForJdbc("SELECT COUNT(0) FROM xzkx_article WHERE 1=1");
		List lt = new ArrayList();
		Highchart hc = new Highchart();
		hc.setName("乡镇发文统计分析");
		hc.setType(reportType);
		if (userBroswerList.size() > 0) {
			for (Map<String,Object> map : userBroswerList) {
				Long groupCount = (Long) map.get("y");
				Double  percentage = 0.0;
				if (count != null && count.intValue() != 0) {
					percentage = new Double(groupCount)/count;
				}
				map.put("percentage", percentage*100);
				String tmpName = map.get("name").toString();
				map.put("name", tmpName.replace("珲春市", ""));
			}
		}
		hc.setData(userBroswerList);
		list.add(hc);
		return list;
	}
	
	@RequestMapping(params = "zhengfuCount")
	@ResponseBody
	public List<Highchart> zhengfuCount(HttpServletRequest request,String reportType, HttpServletResponse response) {
		String fromDate=request.getParameter("fromDate");
		String toDate=request.getParameter("toDate");
		List<Highchart> list = new ArrayList<Highchart>();
		String sb = sqlstr(fromDate,toDate);
		String sqlstr = "SELECT qq.agent_name name ,num y FROM qywx_agent qq left join ("+sb+") s on qq.agent_name=s.agent_name where qq.agent_name not in ("+"'通知公告','企业快讯'"+") order by 2 desc";
		List<Map<String,Object>> userBroswerList=systemService.findForJdbc(sqlstr, null);
		Long count = systemService.getCountForJdbc("SELECT sum(num) FROM ("+sb+") s WHERE 1=1");
		Highchart hc = new Highchart();
		hc.setName("市局发文统计分析");
		hc.setType(reportType);
		if (userBroswerList.size() > 0) {
			for (Map<String,Object> map : userBroswerList) {
				Long groupCount= 0L;
				if(map.get("y")!=null&&map.get("y")!="null"){
					groupCount = (Long) map.get("y");
				}
				
				Double  percentage = 0.0;
				if (count != null && count.intValue() != 0) {
					percentage = new Double(groupCount)/count;
				}else{
					percentage= 0.0;
				}
				map.put("percentage", percentage*100);
			}
		}
		hc.setData(userBroswerList);
		list.add(hc);
		return list;
	}


	private String sqlstr(String fromDate,String toDate) {
		String sb = "";
		if(fromDate!=null&&!"".equals(fromDate)&&toDate!=null&&!"".equals(toDate)){
			sb+="select qa.agent_name, count(0) num from lskx_article la left join qywx_agent qa ";
			sb+="on la.agent_id=qa.wx_agentid where publish_date between '"+fromDate+"' and '"+toDate+"' ";
			sb+="union ";
			sb+="select qa.agent_name,count(0) num from lykx_article  ly left join qywx_agent qa ";
			sb+="on ly.agent_id=qa.wx_agentid where publish_date between '"+fromDate+"' and '"+toDate+"' "; 
			sb+="union ";
			sb+="select qa.agent_name, count(0) num from gxkx_article ga left join qywx_agent qa ";
			sb+="on ga.agent_id=qa.wx_agentid where publish_date between '"+fromDate+"' and '"+toDate+"' "; 
			sb+="union ";
			sb+="select qa.agent_name,count(0) num from mykx_article  ma left join qywx_agent qa ";
			sb+="on ma.agent_id=qa.wx_agentid where publish_date between '"+fromDate+"' and '"+toDate+"' "; 
			sb+="union ";
			sb+="select qa.agent_name, count(0) num from notice_article na left join qywx_agent qa ";
			sb+="on na.agent_id=qa.wx_agentid where publish_date between '"+fromDate+"' and '"+toDate+"' "; 
			sb+="union ";
			sb+="select qa.agent_name,count(0) num from nqkd_article nqa left join qywx_agent qa ";
			sb+="on nqa.agent_id=qa.wx_agentid where publish_date between '"+fromDate+"' and '"+toDate+"' "; 
			sb+="union ";
			sb+="select qa.agent_name, count(0) num from qxkx_article qx left join qywx_agent qa ";
			sb+="on qx.agent_id=qa.wx_agentid where publish_date between '"+fromDate+"' and '"+toDate+"' "; 
			sb+="union ";
			sb+="select qa.agent_name,count(0) num from sckx_article sa left join qywx_agent qa ";
			sb+="on sa.agent_id=qa.wx_agentid where publish_date between '"+fromDate+"' and '"+toDate+"' "; 
			sb+="union ";
			sb+="select qa.agent_name, count(0) num from slkx_article sl left join qywx_agent qa ";
			sb+="on sl.agent_id=qa.wx_agentid where publish_date between '"+fromDate+"' and '"+toDate+"' "; 
			sb+="union ";
			sb+="select qa.agent_name,count(0) num from xzkx_article  xa left join qywx_agent qa ";
			sb+="on xa.agent_id=qa.wx_agentid where publish_date between '"+fromDate+"' and '"+toDate+"' "; 
//			sb+="order by 2 desc";
		}else{
			sb+="select qa.agent_name, count(0) num from lskx_article la left join qywx_agent qa ";
			sb+="on la.agent_id=qa.wx_agentid ";
			sb+="union ";
			sb+="select qa.agent_name,count(0) num from lykx_article  ly left join qywx_agent qa ";
			sb+="on ly.agent_id=qa.wx_agentid "; 
			sb+="union ";
			sb+="select qa.agent_name, count(0) num from gxkx_article ga left join qywx_agent qa ";
			sb+="on ga.agent_id=qa.wx_agentid "; 
			sb+="union ";
			sb+="select qa.agent_name,count(0) num from mykx_article  ma left join qywx_agent qa ";
			sb+="on ma.agent_id=qa.wx_agentid "; 
			sb+="union ";
			sb+="select qa.agent_name, count(0) num from notice_article na left join qywx_agent qa ";
			sb+="on na.agent_id=qa.wx_agentid "; 
			sb+="union ";
			sb+="select qa.agent_name,count(0) num from nqkd_article nqa left join qywx_agent qa ";
			sb+="on nqa.agent_id=qa.wx_agentid "; 
			sb+="union ";
			sb+="select qa.agent_name, count(0) num from qxkx_article qx left join qywx_agent qa ";
			sb+="on qx.agent_id=qa.wx_agentid "; 
			sb+="union ";
			sb+="select qa.agent_name,count(0) num from sckx_article sa left join qywx_agent qa ";
			sb+="on sa.agent_id=qa.wx_agentid "; 
			sb+="union ";
			sb+="select qa.agent_name, count(0) num from slkx_article sl left join qywx_agent qa ";
			sb+="on sl.agent_id=qa.wx_agentid "; 
			sb+="union ";
			sb+="select qa.agent_name,count(0) num from xzkx_article  xa left join qywx_agent qa ";
			sb+="on xa.agent_id=qa.wx_agentid "; 
//			sb+="order by 2 desc";

		}
		return sb;
	}
	
	/**
	 * 报表打印
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(params = "export")
	public void export(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		String type = request.getParameter("type");
		String svg = request.getParameter("svg");
		String filename = request.getParameter("filename");

		filename = filename == null ? "chart" : filename;
		ServletOutputStream out = response.getOutputStream();
		try {
			if (null != type && null != svg) {
				svg = svg.replaceAll(":rect", "rect");
				String ext = "";
				Transcoder t = null;
				if (type.equals("image/png")) {
					ext = "png";
					t = new PNGTranscoder();
				} else if (type.equals("image/jpeg")) {
					ext = "jpg";
					t = new JPEGTranscoder();
				} else if (type.equals("application/pdf")) {
					ext = "pdf";
					t = (Transcoder) new PDFTranscoder();
				} else if (type.equals("image/svg+xml"))
					ext = "svg";
				response.addHeader("Content-Disposition",
						"attachment; filename=" + new String(filename.getBytes("GBK"),"ISO-8859-1") + "." + ext);
				response.addHeader("Content-Type", type);

				if (null != t) {
					TranscoderInput input = new TranscoderInput(
							new StringReader(svg));
					TranscoderOutput output = new TranscoderOutput(out);

					try {
						t.transcode(input, output);
					} catch (TranscoderException e) {
						out
								.print("Problem transcoding stream. See the web logs for more details.");
						e.printStackTrace();
					}
				} else if (ext.equals("svg")) {
					// out.print(svg);
					OutputStreamWriter writer = new OutputStreamWriter(out,
							"UTF-8");
					writer.append(svg);
					writer.close();
				} else
					out.print("Invalid type: " + type);
			} else {
				response.addHeader("Content-Type", "text/html");
				out
						.println("Usage:\n\tParameter [svg]: The DOM Element to be converted."
								+ "\n\tParameter [type]: The destination MIME type for the elment to be transcoded.");
			}
		} finally {
			if (out != null) {
				out.flush();
				out.close();
			}
		}
	}
}
