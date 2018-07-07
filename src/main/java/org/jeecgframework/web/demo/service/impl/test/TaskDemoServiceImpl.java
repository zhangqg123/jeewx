package org.jeecgframework.web.demo.service.impl.test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.jeecgframework.core.common.model.json.Highchart;
import org.jeecgframework.web.demo.service.test.TaskDemoServiceI;
import org.jeecgframework.web.system.service.SystemService;

import java.util.Calendar;

@Service("taskDemoService")
public class TaskDemoServiceImpl implements TaskDemoServiceI {
	@Autowired
	private SystemService systemService;

	
	public void work() {
		org.jeecgframework.core.util.LogUtil.info(new Date().getTime());
		org.jeecgframework.core.util.LogUtil.info("----------任务测试-------");
	}
	
	/**
	 * 乡镇数据生成
	 * 	 
	 * @return
	 */
	public void xiangzhenTask() {
		String[] flDay = firstlastday();
		String sqlStr=null;
		sqlStr="SELECT x.name name ,count(s.column_id) y FROM  xzkx_menu x "
			+ "left join xzkx_article s on s.column_id=x.id and s.publish_date "
			+ "between '"+flDay[0]+"' and '"+flDay[1]+"'  group by x.id order by y desc;";

		StringBuffer sb = new StringBuffer();
		List<Map<String,Object>> userBroswerList=systemService.findForJdbc(sqlStr, null);
		Long count = systemService.getCountForJdbc("SELECT COUNT(0) FROM xzkx_article WHERE 1=1");
		List lt = new ArrayList();
		if (userBroswerList.size() > 0) {
			for (Map<String,Object> map : userBroswerList) {
				String tmpName = map.get("name").toString();
				map.put("name", tmpName.replace("珲春市", ""));
				System.out.println("name: " + map.get("name") +"     num: "+map.get("y"));
			}
		}
	}

	  public String[] firstlastday() {
		  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		  //获取前一个月第一天
	        Calendar calendar1 = Calendar.getInstance();
	        calendar1.add(Calendar.MONTH, -1);
	        calendar1.set(Calendar.DAY_OF_MONTH,1);
	        String firstDay = sdf.format(calendar1.getTime());
	        //获取前一个月最后一天
	        Calendar calendar2 = Calendar.getInstance();
	        calendar2.set(Calendar.DAY_OF_MONTH, 0);
	        String lastDay = sdf.format(calendar2.getTime());

	        String[] flDay=new String[2];
			flDay[0]=firstDay;
			flDay[1]=lastDay;
	        return flDay;
	  }
	  
	  public static void main(String[] args) {
		  TaskDemoServiceImpl tdsl=new TaskDemoServiceImpl();
		  tdsl.xiangzhenTask();
//	      System.out.println("first: " + flDay[0] +"     last: "+flDay[1]);
	  }
}
