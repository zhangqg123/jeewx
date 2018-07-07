package weixin.guanjia.message.utils;
import net.sf.json.JSONObject;
import weixin.guanjia.core.util.WeixinUtil;  
  
public class SendGroupMessage {  
    public String sendGroupMessage(){  
        String groupUrl = "https://api.weixin.qq.com/cgi-bin/message/mass/sendall?access_token=zql-zem-lTjWQC1XvzxqRH5gwy_32ZjZ1DdI3YAs8-x4jsGPslKozqgkaW5CWblNxTC9J7_17CebNqalMkkh02386BTRfcfD86H1Q_VneCxm74bpWvQnTrT63K7YTxl4AHCeAEAJQL"; 
        String groupUrl1 = "https://api.weixin.qq.com/cgi-bin/message/mass/send?access_token=zql-zem-lTjWQC1XvzxqRH5gwy_32ZjZ1DdI3YAs8-x4jsGPslKozqgkaW5CWblNxTC9J7_17CebNqalMkkh02386BTRfcfD86H1Q_VneCxm74bpWvQnTrT63K7YTxl4AHCeAEAJQL";//根据openid发群发消息地址  
        String group1data = "{\"filter\":{\"is_to_all\":false,\"group_id\":\"2\"},\"text\":{\"content\":\"群发消息测试\"},\"msgtype\":\"text\"}\";";  
        String openid1data = "{\"touser\":[\"otu4ZwkCFuDXXO9jO9aqSrsDfmoI\",\"otu4ZwpoNOAX8gwbGxPPqGssIWLw\"],\"msgtype\": \"text\",\"text\": {\"content\": \"测试文本消息\"}}";  
//        String openid2data = "{\"touser\":[\"otu4ZwkCFuDXXO9jO9aqSrsDfmoI\",\"obGXiwNu0z2o_RRWaODvaZctdWEM\"], \"voice\": {\"media_id\":\"UfMRvSiXAD5_iUS8u0Gc3JrKGWOABE9ivQbgrX6i-mVrKGBRL9KnKlioK1BxTPc3\"},\"msgtype\":\"voice\"}";  
        String openid3data = "{\"touser\":[\"otu4ZwkCFuDXXO9jO9aqSrsDfmoI\",\"otu4ZwpoNOAX8gwbGxPPqGssIWLw\"], \"image\": {\"media_id\":\"v5msTVaBfVig6eGZm3xaqjp6RRYDhFJtw2arfCx5RpKx46d8LyDH9z220d6eVIiQ\"},\"msgtype\":\"image\"}";  
//        String openid4data = "{\"filter\":{\"is_to_all\":true}, \"news\": {\"media_id\":\"TrL_Iz3P7UXhwfblh2iAufMvdCJXvDiyZJBBHwVbGf5SGaU6u-wwencOoF0rrMjG\"},\"msgtype\":\"news\"}";  
        String openid4data = "{\"touser\":[\"otu4ZwkCFuDXXO9jO9aqSrsDfmoI\",\"otu4ZwpoNOAX8gwbGxPPqGssIWLw\"], \"mpnews\": {\"media_id\":\"IZrMqjPaQ3JRy-WgNOvUBfAnMcFwBL_ajOmvAsWr2HJ8DtrdHB6y0yjASfr9-gg3\"},\"msgtype\":\"mpnews\"}";
        String openid5data = "{\"filter\":{\"is_to_all\":true}, \"mpnews\": {\"media_id\":\"IZrMqjPaQ3JRy-WgNOvUBfAnMcFwBL_ajOmvAsWr2HJ8DtrdHB6y0yjASfr9-gg3\"},\"msgtype\":\"mpnews\"}";
        JSONObject json = WeixinUtil.httpRequest(groupUrl1, "POST", openid4data);  
        return json.toString();  
    }  
    public static void main(String[] args) {  
        System.out.println(new SendGroupMessage().sendGroupMessage());  
    }  
}  
