package weixin.guanjia.message.utils;

import weixin.guanjia.core.util.WeixinUtil;
import net.sf.json.JSONObject;

public class ImageArticleUpload {
	public String upload() {
		String url = "https://api.weixin.qq.com/cgi-bin/media/uploadnews?access_token=yOT22Mak72sixQyWpiViabjUt9HpCrCwxaHMwe7z24SsbuF5HiRQvAFYMSFfNVNttjGqMvBDE_ss4dYLofkbRQvRn8g4v8wJocX5EjOLw1sLSUfAFAXEG";// ACCESS_TOKEN是获取到的access_token
		// 上传的图文消息数据，其中thumb_media_id是文件上传图片上传的id
		String data = "{\"articles\": [{\"thumb_media_id\":\"v5msTVaBfVig6eGZm3xaqjp6RRYDhFJtw2arfCx5RpKx46d8LyDH9z220d6eVIiQ\",\"author\":\"xxx\",\"title\":\"Happy Day\",\"content_source_url\":\"www.qq.com\",\"content\":\"content\",\"digest\":\"digest\",\"show_cover_pic\":\"0\"}]}";
		String data1 = "{\"articles\":[{\"author\":\"王传清|毕强|Wang Chuanqing|Bi Qiang\",\"content\":\"基于关联关系维度的数字资源聚合是数字资源知识发现的重要基础和工具。超网络是由多个类型的同质和异质子网络组成的网络，通过多种关联维度聚合的数字资源即形成了拥有相同以及不同性质的结点和关系的数字资源超网络，这些不同性质的关联与链接是知识关联、挖掘、发现与创新的脉络线索。结合超网络理论，构建和描述数字资源超网络，并分析超网络中不同性质的关系类型，如引用关系、共现关系、耦合关系等，从关联维度探讨数字资源深度聚合的模式，进而分析利用数字资源超网络进行知识发现的具体应用方法，最后构建数字资源超网络聚合系统模型。\",\"content_source_url\":\"http://d.g.wanfangdata.com.cn/Periodical_qbxb201501002.aspx\",\"digest\":\"测试\",\"show_cover_pic\":1,\"thumb_media_id\":\"wnHijygFWIBo7Wa4Di02BOQ_pbdxEbG5CSJwQ11gaGozAjnFEOfcZK75tzk3R8RC\",\"title\":\"超网络视域下的数字资源深度聚合研究\"}]}";
		JSONObject json = WeixinUtil.httpRequest(url, "POST", data);
		return json.toString();
	}

	public static void main(String[] args) {
		System.out.println(new ImageArticleUpload().upload());
	}
}
