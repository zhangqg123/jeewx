package weixin.guanjia.message.service;

import java.io.IOException;

import net.sf.json.JSONObject;

import org.jeecgframework.core.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import weixin.guanjia.account.service.WeixinAccountServiceI;
import weixin.guanjia.core.util.WeixinUtil;
import weixin.guanjia.message.model.TextMessageGroup;
import weixin.guanjia.message.utils.FileUpload;

/**
 * 客服消息service
 * @author 付明星
 *
 */
@Service("groupMessageService")
public class GroupMessageService {
    String sendUrl = "http://file.api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=image";//ACCESS_TOKEN是获取到的access_token  
	String mediaUrl = "https://api.weixin.qq.com/cgi-bin/media/uploadnews?access_token=ACCESS_TOKEN";// ACCESS_TOKEN是获取到的access_token
//    String groupUrl1 = "https://api.weixin.qq.com/cgi-bin/message/mass/send?access_token=ACCESS_TOKEN";//根据openid发群发消息地址  

    //这个地址是根据分组id来群发消息  
    String group_url = "https://api.weixin.qq.com/cgi-bin/message/mass/sendall?access_token=ACCESS_TOKEN"; 
    //这个地址是根据openid来群发消息
    String group_url_openid = "https://api.weixin.qq.com/cgi-bin/message/mass/send?access_token=ACCESS_TOKEN"; 

//    @Autowired
//	private WeixinAccountServiceI weixinAccountService;

    public String sendGroupMessage(TextMessageGroup sendMessage,String accessTocken){
    	// 调用接口获取access_token
       	System.out.println("....token...."+accessTocken);
       	String url = null;
       	JSONObject jsonObject = null;
		JSONObject jsonObj = JSONObject.fromObject(sendMessage);
       	if(sendMessage.getTouser().length>1){
//    		JSONObject jsonObj = JSONObject.fromObject(sendMessage);
//           	System.out.println("....send message...."+jsonObj.toString());
       		url = group_url_openid.replace("ACCESS_TOKEN",accessTocken);
//           	jsonObject = WeixinUtil.httpRequest(url, "POST", jsonObj.toString());
       	}else{
       		String test = sendMessage.getTouser()[0];
       		if(sendMessage.getTouser()[0].equals("@all")){
       			url = group_url.replace("ACCESS_TOKEN",accessTocken);
        	}else{
        		if(sendMessage.getFilter().getGroup_id().length()>0){
           			url = group_url.replace("ACCESS_TOKEN",accessTocken);
//           	        String group1data = "{\"filter\":{\"is_to_all\":false,\"group_id\":\"GROUP_ID\"},\"text\":{\"content\":\"CONTENT\"},\"msgtype\":\"text\"}\";";  
//           			String content = sendMessage.getText().getContent();
//           			String groupData = group1data.replace("GROUP_ID", sendMessage.getGroup_id()).replace("CONTENT",content);
//           			jsonObject = WeixinUtil.httpRequest(url, "POST", groupData);        			
        		}
        	}
       	}
		jsonObject = WeixinUtil.httpRequest(url, "POST", jsonObj.toString());
       	System.out.println("...jsonObject..."+jsonObject.toString());
       	return jsonObject.toString();
    }
    
    public String sendNewsGroupMessage(String json,String accessTocken) throws IOException{
    	String groupalldata = "{\"filter\":{\"is_to_all\":true},\"text\":{\"content\":\"公众号群发消息测试\"},\"msgtype\":\"text\"}";
    	// 调用接口获取access_token
        String surl = sendUrl.replace("ACCESS_TOKEN",accessTocken);
//      String filePath = "/Users/hongda/Downloads/bg-1.jpg";//本地或服务器文件路径  
        String filePath = "f:/aaa.jpg";//本地或服务器文件路径  
        FileUpload fileUpload = new FileUpload();  
        String imageMedia = fileUpload.send(surl, filePath); 
        System.out.println(imageMedia);

        JSONObject obj = JSONObject.fromObject(imageMedia);
        String murl = mediaUrl.replace("ACCESS_TOKEN",accessTocken);
		// 上传的图文消息数据，其中thumb_media_id是文件上传图片上传的id
		String data = "{\"articles\": [{\"thumb_media_id\":\"THUMB_MEDIA_ID\",\"author\":\"xxx\",\"title\":\"Happy Day\",\"content_source_url\":\"www.qq.com\",\"content\":\"content\",\"digest\":\"digest\",\"show_cover_pic\":\"0\"}]}";
		String data1 = "{\"articles\":[{\"author\":\"王传清|毕强|Wang Chuanqing|Bi Qiang\",\"content\":\"基于关联关系维度的数字资源聚合是数字资源知识发现的重要基础和工具。超网络是由多个类型的同质和异质子网络组成的网络，通过多种关联维度聚合的数字资源即形成了拥有相同以及不同性质的结点和关系的数字资源超网络，这些不同性质的关联与链接是知识关联、挖掘、发现与创新的脉络线索。结合超网络理论，构建和描述数字资源超网络，并分析超网络中不同性质的关系类型，如引用关系、共现关系、耦合关系等，从关联维度探讨数字资源深度聚合的模式，进而分析利用数字资源超网络进行知识发现的具体应用方法，最后构建数字资源超网络聚合系统模型。\",\"content_source_url\":\"http://d.g.wanfangdata.com.cn/Periodical_qbxb201501002.aspx\",\"digest\":\"测试\",\"show_cover_pic\":1,"
				+ "\"thumb_media_id\":\"THUMB_MEDIA_ID\",\"title\":\"超网络视域下的数字资源深度聚合研究\"}]}";
    	String data2 = data1.replace("THUMB_MEDIA_ID",obj.getString("media_id"));
    	JSONObject json2 = WeixinUtil.httpRequest(murl, "POST", data2);
        System.out.println(json2.toString());

        String gurl = group_url_openid.replace("ACCESS_TOKEN",accessTocken);
        String openid4data = "{\"touser\":[\"otu4ZwkCFuDXXO9jO9aqSrsDfmoI\",\"otu4ZwpoNOAX8gwbGxPPqGssIWLw\"], \"mpnews\": {\"media_id\":\"MEDIA_ID\"},\"msgtype\":\"mpnews\"}";
    	String data3 = openid4data.replace("MEDIA_ID",json2.getString("media_id"));
        JSONObject json3 = WeixinUtil.httpRequest(gurl, "POST", data3);  
        System.out.println(json3.toString());
        return json3.toString();  

//      JSONObject jsonObject = WeixinUtil.httpRequest(url, "POST", groupalldata);
//      System.out.println("...jsonObject..."+jsonObject.toString());
//      return jsonObject.toString();
    }
    
}

