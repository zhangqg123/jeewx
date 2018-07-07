<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="weixinUserList" title="微信用户" actionUrl="weixinUserController.do?datagrid" idField="id" fit="true">
   <t:dgCol title="用户标示" field="id"></t:dgCol>
<%--    <t:dgCol title="用户标识" field="openid" ></t:dgCol> --%>
   <t:dgCol title="用户昵称" field="nickname" ></t:dgCol>
   <t:dgCol title="性别" dictionary="wxsex" field="sex" ></t:dgCol>
   <t:dgCol title="城市" field="city" ></t:dgCol>
   <t:dgCol title="国家" field="country" ></t:dgCol>
   <t:dgCol title="省份" field="province" ></t:dgCol>
   <t:dgCol title="语言" dictionary="language" field="language" ></t:dgCol>
   <t:dgCol title="头像url" field="headimgurl" image="true" imageSize="30,30"></t:dgCol>
   <t:dgCol title="用户订阅" dictionary="subscribe" field="subscribe" ></t:dgCol>
<%--    <t:dgCol title="关注时间" field="subscribeTime" ></t:dgCol> --%>
   <t:dgCol title="公众号" field="unionid" ></t:dgCol>
   <t:dgCol title="标签列表" field="tagidList" dictionary="weixin_tag,id,name" query="true" ></t:dgCol>
   <t:dgCol title="备注" field="remark" ></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
<%--    <t:dgDelOpt title="删除" url="weixinUserController.do?del&id={id}" /> --%>
<%--    <t:dgToolBar title="录入" icon="icon-add" url="weixinUserController.do?addorupdate" funname="add"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="编辑" icon="icon-edit" url="weixinUserController.do?addorupdate" funname="update"></t:dgToolBar> --%>
   <t:dgToolBar title="查看" icon="icon-search" url="weixinUserController.do?addorupdate" funname="detail"></t:dgToolBar>
	<t:dgToolBar title="标签同步到微信" icon="icon-edit" url="tagController.do?doSynch" funname="doSynch()"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script type="text/javascript">
function doSynch(){
	$.ajax({
		url:"weixinUserController.do?doSynch",
		type:"GET",
		dataType:"JSON",
		success:function(data){
			if(data.success){
				location.reload();
				tip(data.msg);
			}
		}
	});
}

</script>
 