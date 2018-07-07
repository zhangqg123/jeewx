<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tagList" title="标签" actionUrl="tagController.do?datagrid" idField="id" fit="true">
   <t:dgCol title="编号" field="id" ></t:dgCol>
   <t:dgCol title="标签名称" field="name" ></t:dgCol>
   <t:dgCol title="数量" field="count" ></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="tagController.do?del&id={id}" />
   <t:dgToolBar title="录入" icon="icon-add" url="tagController.do?addorupdate" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="tagController.do?addorupdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="tagController.do?addorupdate" funname="detail"></t:dgToolBar>
	<t:dgToolBar title="标签同步到微信" icon="icon-edit" url="tagController.do?doSynch" funname="doSynch()"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script type="text/javascript">
function doSynch(){
	$.ajax({
		url:"tagController.do?doSynch",
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
 