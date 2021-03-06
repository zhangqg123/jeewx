<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<!-- #parse("content/base/back/common/head.vm") -->
<link href="<%=basePath%>/plug-in-ui/css/treetable/default/treeTable.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath%>/content/qywx/plug-in/groupMsg/css/index.css" type="text/css" rel="stylesheet"/>
<link href="<%=basePath%>/plug-in-ui/css/bootstrap.css" type="text/css" rel="stylesheet"/>
<link href="<%=basePath%>/plug-in-ui/css/style.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript" src="<%=basePath%>/plug-in-ui/js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="<%=basePath%>/plug-in-ui/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/plug-in-ui/js/zTree/jquery.ztree.core.js$!{version}"></script>
<script type="text/javascript" src="<%=basePath%>/plug-in-ui/js/zTree/jquery.ztree.excheck.js$!{version}"></script>
<script type="text/javascript" src="<%=basePath%>/plug-in-ui/js/zTree/jquery.ztree.exedit.js$!{version}"></script>
<script type="text/javascript" src="<%=basePath%>/plug-in-ui/js/zTree/bootstrap.min.js$!{version}"></script>
<script type="text/javascript" src="<%=basePath%>/plug-in-ui/js/bootstrap.js$!{version}"></script>
<title>群发消息</title>
</head>
<body> 
<input type="hidden" id="msgType"/>
<!--弹出层-->
<div class="zz"></div>
<div class="cc">
<div class="c_head"><i>转发微博</i><span></span></div>
<div class="c_main">
<div class="cbot">
<!-- 弹出页面 -->
<iframe src="" id="groupmessage" width="100%" height="400" frameborder="0" scrolling="yes"></iframe>
</div>
</div>
<div class="tj">
	<input class="zleft c_btn" type="button" value="确定"/>
	<input class="zright" type="button" value="取消"/></div>
</div>
<!--弹出层 end-->
<div class="jmain">
<div class="jtit">
<!--list1-->
<div class="list1">
	<div class="jtxt">群发对象</div>
	<div class="jcont">
<!-- 			<div class="c_one"> -->
<!-- 				 <select name="toAgent" id="toAgent"> -->
<!-- 				 #foreach($item in $agentList) -->
<%-- 					<option value="${item.wxAgentid}">$!{item.agentName}</option> --%>
<!-- 				 #end -->
<!-- 				</select> -->
<!-- 			</div> -->
			<div class="c_one" name="toModel" id="toModel" onclick="modellist();">
			<select>
			<option value="1">高级模式</option>
			<option value="2">全发模式</option>
			</select>
			</div>
			<div class="c_two" id="departmentid">
<!-- 				<input type="text" value="请选择标签" style="height: 30px;" id="parentid" readonly class="form-control" onclick="showMenu();"/> -->
<!-- 	            <input type="hidden" value="$!{list.parentid}" name="parentid" id="pId" /> -->
				<select name="parentid" id="pId">
					<option value="" name="tagId" >---请选择---</option>
					<c:forEach items="${tagList}" var="item">
						<option value="${item.id}" name="tagId" >${item.name}</option>
					</c:forEach>
				</select>
			</div>
			<div class="c_two" id="memberid">
				<input type="text" id="userid" value="请选择用户" style="height: 30px;"readonly class="form-control" onclick="userlist('用户列表');"/>
			</div>
	 </div>
</div>	
<!--list1 end-->
</div>
<!--c_cont-->
<div class="c_cont">
<div class="c_tree">
<ul>

	<li>
		<a href="javascript:void(0);">
			<i id="text"></i>
		</a>
	</li>
	
<!-- 	<li>
		<a href="javascript:void(0);">
			<i id="image"></i>
		</a>
	</li>
	
	<li>
		<a href="javascript:void(0);">
			<i id="video"></i>
		</a>
	</li>
	
	<li>
		<a href="javascript:void(0);">
			<i id="voice"></i>
		</a>
	</li>
	 -->
	<li>
		<a href="javascript:void(0);">
			<i id="news"></i>
		</a>
	</li>
</ul>
</div>
<div class="c_bj">
<textarea class="wz" id="msgId" placeholder="请输入内容" name=""></textarea>
</div>
<div class="ts">还可以输入<i>600</i>字</div>
</div>
<!--c_cont end-->
<div class="c_footer">
<input type="button" id="sendMessage" value="群发" class="c_btn"/>
<!--  -->
</div>
</div>
<div id="menuContent" class="menuContent" style="display:none;position: absolute;">
<ul id="treeDemo" class="ztree" style="margin-top:0; width:100px;"></ul>
</div>
<!-- Modal -->
<div class="modal fade" id="privilegeTreeModal" tabindex="-1" role="dialog"
	aria-labelledby="privilegeTreeModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content" style="width: 170%;left: -35%;">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="privilegeTreeModalLabel">用户列表</h4>
			</div>
			<div class="modal-body">
				<iframe name="selectUsers" id="selectUsers" width="100%" height="50%" frameborder="0" scrolling="auto" src="groupMsg.do?getUserList"></iframe>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" id="dialogFormClose" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-default" onclick="CheckAll();" data-dismiss="modal">确定</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<input type="hidden" id="temp"/>
<input type="hidden" id="gzuserinfoid"/>
<input type="hidden" name="accountId" id="accountId" value="$!{accountId}"/>
<input type="hidden" name="listGroupToTree" id="listGroupToTree" value="$!{listGroupToTree}"/>
</body>
		<script type="text/javascript" src="<%=basePath%>/plug-in-ui/js/Validform_v5.3.2.js"></script> 
		<script type="text/javascript" src="<%=basePath%>/plug-in-ui/js/forminit.p3.js"></script>
		<script type="text/javascript">
		$(function(){
			$(".c_tree i").each(function(i, element) {
				var _top=i*-60,_top2=i*-60-30;
				if(i==0){
					$(this).css({"background-position":"0px "+_top2+"px"});
				}else{
					$(this).css({"background-position":"0px "+_top+"px"});
					$(this).hover(function(){
							$(this).css({"background-position":"0px "+_top2+"px"});
						},function(){
				 			$(this).css({"background-position":"0px "+_top+"px"});
					});
				}
			});
			
		    //弹出框
			var _move=false;//移动标记
			var _x,_y;//鼠标离控件左上角的相对位置
			//标记点击的那个按钮
			var checkedPic ="text";
			$(".c_tree i").click(function(){
				var id = $(this).attr("id");
				checkedPic= $("#msgType").val(id);//定义一个变量，把id复制给msgType
				var message = "";
				var srcUrl ="";
				if(id=="text"){
				    var htmlContent = "<textarea class=\"wz\" placeholder=\"请输入内容\" name=\"param\"></textarea>";
				    $(".c_bj").html(htmlContent);
					$(".wz").focus();
					$("#groupmessage").attr("src","");
				}else if(id=="image"){
					message = "选择图片";
					srcUrl+="&symbol=simple&type=image";
					$("#groupmessage").attr("src",srcUrl);
				}else if(id=="video"){
					message = "选择视频";
					srcUrl+="&symbol=simple&type=video";
					$("#groupmessage").attr("src",srcUrl);
				}else if(id=="voice"){
					message = "选择语音";
					srcUrl+="&symbol=simple&type=voice";
					$("#groupmessage").attr("src",srcUrl);
				}else if(id=="news"){
					message = "选择素材";
					srcUrl="sendGroupMessageController.do?getAllUploadNewsTemplate";
//					srcUrl+="&symbol=page";
					$("#groupmessage").attr("src",srcUrl);
				}
				//设置弹出框标题
				if(id!="text"){
					$(".c_head").find("i").html(message);
					$('.cc').fadeIn('slow');
					$('.zz').css("display","block");
				}
			});
	
		 //群发，群发所有用户，群发部门，群发用户
		$("#sendMessage").click(function(){
// 		      var toAgent = $("#toAgent option:selected").val();
 			  var toparty = $("#pId").val();//群发类型为组织机构id
 			  var msgtype =$("#msgType").val();//群发类型
 			  if(msgtype==""){
 				msgtype="text";
 			  }
// 			  var touserId=$("#gzuserinfoid").val();//群发类型为用户
 			  var toModel = $("#toModel option:selected").val();//群发对象为全部成员发送@all
 			  var param = $("#param").val();//消息内容
 			  var templateId=$('#temp').val();//当群发图文素材时候需要 templateId
 			  var msgId=$('#msgId').val();//文本域内容
			  var url ="sendGroupMessageController.do?send";
			  if(msgtype == "text"){
				param=$(".wz").val();
			  }
		      	if(param==""){
					alert("请输入群发内容！");
// 				}
// 				if(msgId==""){
// 					alert("请输入群发内容！");
				}else{
					if (confirm("你确定群发消息吗？"))  {  			
						jQuery.ajax({ 
				            url:url,
							type:"POST",
							dataType:"JSON",
//							data:{"msgtype":msgtype,"param":param,"toAgent":toAgent,"toparty":toparty,"templateId":templateId,"touserId":touserId,"toModel":toModel},
							data:{"msgtype":msgtype,"param":param,"toModel":toModel,"toparty":toparty,"templateId":templateId},
							success:function(data,status){
				            if (data.obj=='sucess') {
				                alert("发送成功！");
				               }else{
				               alert("发送失败！");
				               }
							}
					
						});		
					}	
				}
		});
			
		$('.cc span,.zright').click(function(){
			$('.cc').hide('fast');
			$('.zz').css("display","none");
		})	
		//图文弹出框
		$(".zleft").click(function(){
			var iFrame= document.getElementById('groupmessage').contentWindow;
			var templateId=iFrame.getcheckedNews();//获取到id
			$('#temp').val(templateId)//把templateId赋值给定义的一个隐藏属性
			//关闭弹出框
			$('.cc').hide('fast');
			$('.zz').css("display","none");
	        var msgtype =$("#msgType").val()
			if(msgtype=='news'){
			jQuery.ajax({
					url:"sendGroupMessageController.do?toGroupNewsSend",
					data:{"templateId":templateId},
					dataType:"JSON",
					method:"POST",
					success:function(data){
					 console.log(data);
					    if(data.success){
					        var list = data.obj;
					        if(list.length>0){
							    var htmlContent = "<div class=\"media_preview_area pp\" style=\"width:320px;\">"+
				    							  "<div class=\"appmsg multi editing\"><div id=\"js_appmsg_preview\" class=\"appmsg_content\">";
								for(var i=0;i<list.length;i++){
									if(i==0){
										htmlContent += "<div id=\"appmsgItem1\" class=\"js_appmsg_item has_thumb\" data-id=\"1\" data-fileid=\"200159570\">"+
				          							   "<div class=\"appmsg_info\"> <h3>"+list[i].title+"</h3><em class=\"appmsg_date\"></em></div><div class=\"cover_appmsg_item\">"+
				                                        "<h4 class=\"appmsg_title\"> <a target=\"_blank\" onclick=\"return false;\" href=\"javascript:void(0);\">"+list[i].description+"</a> </h4>"+
				                                        "<div class=\"appmsg_thumb_wrp\"> <img class=\"js_appmsg_thumb appmsg_thumb\" src=\"" + '<%=basePath%>' +'/'+ list[i].imagePath+"\"></div></div></div>";
									}else{
										htmlContent += "<div id=\"appmsgItem2\" class=\"appmsg_item js_appmsg_item has_thumb\" data-id=\"2\" data-fileid=\"200159577\"> <img class=\"js_appmsg_thumb appmsg_thumb\" src=\"" + '<%=basePath%>' +'/' +list[i].imagePath+"\">"+
				          							   "<h4 class=\"appmsg_title\"> <a target=\"_blank\" href=\"javascript:void(0);\">"+list[i].title+"</a></h4></div>";
									}
								}
								htmlContent += "</div></div></div>";
								$(".c_bj").html(htmlContent);
							}
						}
					}
			  });
			}
		});
	});
		
		//部门树的加载
// 			var IDMark_A = "_a";
			
// 			function showMenu() {
// 			$('#parentid').val("")//当点击事件后，把空串赋值给他赋值给定义的一个隐藏属性
// 			var cityObj = $("#parentid");
// 			var cityOffset = $("#parentid").offset();
// 				if($("#menuContent").is(':hidden')){
// 			     $("#menuContent").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");
// 				}else{
// 				  $("#menuContent").fadeOut("fast");
// 				}
// 			    $("body").bind("mousedown", onBodyDown);
// 			}
// 			function hideMenu() {
// 				$("#menuContent").fadeOut("fast");
// 				$("body").unbind("mousedown", onBodyDown);
// 			}
// 			function onBodyDown(event) {
// 				if (!(event.target.id == "parentid" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length>0)) {
// 					hideMenu();
// 				}
// 			}
// 			var setting = {
// 				check: {
// 					enable: true,
// 					radioType: "all",
// 					chkboxType:{"Y" : "undefined", "N" : "" }
// 				},
// 				view: {
// 					addHoverDom: addHoverDom,
// 					removeHoverDom: removeHoverDom,
// 					dblClickExpand: false
// 				},
// 				data: {
// 					simpleData: {
// 						enable: true,
// 						idKey: "id",
// 						pIdKey: "pId",
// 						rootPId: null
// 					}
// 				},
// 				callback: {
				
// 					onClick: onClick,
// 					onCheck: onCheck,
// 				},
				
// 			};
// 			function onClick(e, treeId, treeNode) {
// 			   var tree = zTree.getZTreeObj("treeDemo");
// 				tree.checkNode(treeNode, !treeNode.checked, null, true);
// 				return false;
// 			}
// 			function onCheck(e, treeId, treeNode) {
// 				var tree = zTree.getZTreeObj("treeDemo"),
// 				nodes = tree.getCheckedNodes(true),
// 				v = "",vid = "";
// 				for (var i=0, l=nodes.length; i<l; i++) {
// 					v += nodes[i].name + ",";
// 					vid += nodes[i].id + ",";
// 				}
// 				if (v.length > 0 ) v = v.substring(0, v.length-1);
// 				if (vid.length > 0 ) vid = vid.substring(0, vid.length-1);
// 			    $("#parentid").val(v);
// 				$("#pId").val(vid);
// 			}
// 			function addHoverDom(treeId, treeNode) {
			
// 				if (treeNode.parentNode && treeNode.parentNode.id!=1) return;
// 				var aObj = $("#" + treeNode.tId + IDMark_A);
// 					if ($("#diyBtn_"+treeNode.id).length>0) return;
// 					var editStr = "<span id='diyBtn_" +treeNode.id+ "'></span>";
				
// 					aObj.after(editStr);
// 			}
// 			function removeHoverDom(treeId, treeNode) {
// 					$("#diyBtn_"+treeNode.id).unbind().remove();
// 					$("#diyBtn_space_" +treeNode.id).unbind().remove();
// 			}

// 			var zNodes;
// 			$(document).ready(function(){
// 				jQuery.ajax({  
// 				        async : false,  
// 				        cache:false,  
// 				        type: 'POST',  
// 				        dataType : "json",  
// 				        url: "groupMsg.do?getAuthTree",//请求的action路径  
// 				        error: function () {//请求失败处理函数  
// 				            alert('请求失败');  
// 				        },  
// 				        success:function(data){ //请求成功后处理函数。    
// 				            zNodes = data.obj;   //把后台封装好的简单Json格式赋给zNodes  
// 				        }  
// 				    });  
// 				zTree.init($("#treeDemo"), setting, zNodes);
// 			});  
			//把用户列表页面用弹出框的形式展现出来,在model里面添加添加iframe
// 			function userlist(title){
// 			  var url="groupMsg.do?getUserList";
// 			  if(url.indexOf('?')>-1){
// 			 		url += '&t=' + Math.random(1000);  
// 			 	}else{
// 			 		url += '?t=' + Math.random(1000);  
// 			 		}
// 			 	$('#privilegeTreeModalLabel').html(title);
// 			 	jQuery.ajax({
// 			        url: url,
// 			      	cache: false,
// 				  	dataType: "html",
// 			      	success: function(data){
// 			            }
// 			      });  
// 			      $('#privilegeTreeModal').modal({show:true,backdrop:false});
// 			}
// 			//显示群发用户
// 			function CheckAll(){
// 			  var topWinid= document.getElementById('selectUsers').contentWindow.document.getElementById('framid');
// 			  var topWinname= document.getElementById('selectUsers').contentWindow.document.getElementById('framname');
// 			  var userId=topWinid.value;
// 			  var userName=topWinname.value
// 			  $('#userid').val("");
// 			  $('#userid').val(userName);
// 			  $('#gzuserinfoid').val(userId);
// 			}
			//群发隐藏
			function modellist(){
			 var toModel = $("#toModel option:selected").val();//群发对象为全部成员发送@all
			 if(toModel=="1"){
			     $("#departmentid,#memberid").show();
			     }
			 if(toModel=="2"){
			    $("#departmentid,#memberid").hide();
			  }
			}
</script>
</html>
