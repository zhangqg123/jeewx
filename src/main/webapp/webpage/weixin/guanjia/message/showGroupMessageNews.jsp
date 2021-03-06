﻿<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>图文消息展示</title>
<meta name="viewport" content="width=device-width,user-scalable=no,initial-scale=1, maximum-scale=1">
<meta http-equiv="cache-control" content="no-cache">
<script type="text/javascript" src="<%=basePath %>/plug-in-ui/js/jquery-1.9.1.js"></script>
<link href="<%=basePath %>/content/qywx/plug-in/groupMsg/css/index.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript">
	var template_id = "";
	function checkedNews(o){
	    $(".cover").removeClass("pf");
		var showCover = $(o).find(".cover");
		showCover.addClass("pf");
		template_id = $(o).find("#template_id").val();
	}
	function getcheckedNews(){
		var result=template_id;
		return result;
	}
</script>
</head>

<body>

<div class="pmain"> 
<c:forEach items="${templateList}" var="template" varStatus="status">
	<c:if test="${status.index % 2 eq 0}">
 		<div class="pleft" onclick="checkedNews(this)">
	</c:if>
	<c:if test="${status.index % 2 eq 1}">
		<div class="pright" onclick="checkedNews(this)">
	</c:if>

<%--  	<input type="hidden" name="media_id" id="media_id" value="${template.media_id}"/> --%>
 	<input type="hidden" name="template_id" id="template_id" value="${template.id}" />
	  <!--list1-->
	  <div class="media_preview_area pp" style="position:relative;" >
	   <div class="cover"><i style="display:block;position:absolute;top:45%;left:45%;font-size:30px;">√</i></div>
	    <div class="appmsg multi editing">
	      <div id="js_appmsg_preview" class="appmsg_content">
<!-- 	      	#set($newsCount=0) -->
<%-- 	      	#foreach ($items in ${template.newsItemList}) --%>
				<c:forEach items="${template.newsItemList}" var="newsItem" varStatus="itemStatus">
<!-- 	      			#if($newsCount==0) -->
					<c:if test="${itemStatus.index eq 0}">
		      			<div id="appmsgItem1" class="js_appmsg_item has_thumb" data-id="1" data-fileid="200159570">
			   		       	<div class="appmsg_info">
			        		   	<h3>${newsItem.title}</h3>
			            		<em class="appmsg_date"></em> 
			          		</div>
			          		<div class="cover_appmsg_item">
			            		<h4 class="appmsg_title"> <a target="_blank" onclick="return false;" href="javascript:void(0);">${newsItem.description}</a> </h4>
			            		<div class="appmsg_thumb_wrp"> <img class="js_appmsg_thumb appmsg_thumb" src="<%=basePath %>/${newsItem.imagePath}"></img></div>
			          		</div>
			        	</div>
			        </c:if>
					<c:if test="${itemStatus.index > 0}">
	      				<div id="appmsgItem2"  class="appmsg_item js_appmsg_item has_thumb" data-id="2" data-fileid="200159577"> <img class="js_appmsg_thumb appmsg_thumb" src="<%=basePath %>/${newsItem.imagePath}"></img>
			          	<h4 class="appmsg_title"> <a target="_blank" href="javascript:void(0);" onclick="return false;">${newsItem.title}</a> </h4>
			        </div> 
			        </c:if>
			   </c:forEach> 
		</div>
	    </div>
	  </div>
	  <!--list1 end--> 
  </div>
</c:forEach>
</div>
</body>
</html>
