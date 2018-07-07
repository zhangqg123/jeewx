<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>文章记录</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
 </head>
 <body style="overflow-y: hidden" scroll="no">
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="div" action="cmsRecordController.do?save">
		<input id="id" name="id" type="hidden" value="${cmsRecordPage.id }">
		<fieldset class="step">
			<div class="form">
		      <label class="Validform_label">账号:</label>
		      <input class="inputxt" id="userid" name="userid" ignore="ignore"
					   value="${cmsRecordPage.userid}">
		      <span class="Validform_checktip"></span>
		    </div>
			<div class="form">
		      <label class="Validform_label">姓名:</label>
		      <input class="inputxt" id="name" name="name" ignore="ignore"
					   value="${cmsRecordPage.name}">
		      <span class="Validform_checktip"></span>
		    </div>
			<div class="form">
		      <label class="Validform_label">部门:</label>
		      <input class="inputxt" id="department" name="department" ignore="ignore"
					   value="${cmsRecordPage.department}">
		      <span class="Validform_checktip"></span>
		    </div>
			<div class="form">
		      <label class="Validform_label">文章编号:</label>
		      <input class="inputxt" id="articleid" name="articleid" ignore="ignore"
					   value="${cmsRecordPage.articleid}">
		      <span class="Validform_checktip"></span>
		    </div>
			<div class="form">
		      <label class="Validform_label">微信账号ID:</label>
		      <input class="inputxt" id="accountid" name="accountid" ignore="ignore"
					   value="${cmsRecordPage.accountid}">
		      <span class="Validform_checktip"></span>
		    </div>
	    </fieldset>
  </t:formvalid>
 </body>