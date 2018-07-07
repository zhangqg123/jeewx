<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="cmsRecordList" title="文章记录" actionUrl="cmsRecordController.do?datagrid&articleid=${articleid}" idField="id" fit="true">
   <t:dgCol title="编号" field="id" hidden="false"></t:dgCol>
   <t:dgCol title="账号" field="userid" ></t:dgCol>
   <t:dgCol title="姓名" field="name" ></t:dgCol>
   <t:dgCol title="部门" field="department" ></t:dgCol>
   <t:dgCol title="文章编号" field="articleid" hidden="false"></t:dgCol>
	<t:dgCol title="签收时间" field="createDate" ></t:dgCol>
<%--    <t:dgCol title="微信账号ID" field="accountid" ></t:dgCol> --%>
<%--    <t:dgCol title="操作" field="opt" width="100"></t:dgCol> --%>
<%--    <t:dgDelOpt title="删除" url="cmsRecordController.do?del&id={id}" /> --%>
<%--    <t:dgToolBar title="录入" icon="icon-add" url="cmsRecordController.do?addorupdate" funname="add"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="编辑" icon="icon-edit" url="cmsRecordController.do?addorupdate" funname="update"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="查看" icon="icon-search" url="cmsRecordController.do?addorupdate" funname="detail"></t:dgToolBar> --%>
  </t:datagrid>
  </div>
 </div>