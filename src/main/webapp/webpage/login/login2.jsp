<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no">
<link rel="shortcut icon" href="resources/fc/images/icon/favicon.ico">
<!--[if lt IE 9]>
   <script src="plug-in/login/js/html5.js"></script>
  <![endif]-->
<!--[if lt IE 7]>
  <script src="plug-in/login/js/iepng.js" type="text/javascript"></script>
  <script type="text/javascript">
	EvPNG.fix('div, ul, img, li, input'); //EvPNG.fix('包含透明PNG图片的标签'); 多个标签之间用英文逗号隔开。
</script>
  <![endif]-->
<link href="plug-in/login2/css/zice.style.css" rel="stylesheet" type="text/css" />
<link href="plug-in/login2/css/buttons.css" rel="stylesheet" type="text/css" />
<link href="plug-in/login2/css/icon.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="plug-in/login2/css/tipsy.css" media="all" />
<style type="text/css">
html {
	background-image: none;
}

label.iPhoneCheckLabelOn span {
	padding-left: 0px
}

#versionBar {
	background-color: #212121;
	position: fixed;
	width: 100%;
	height: 35px;
	bottom: 0;
	left: 0;
	text-align: center;
	line-height: 35px;
	z-index: 11;
	-webkit-box-shadow: black 0px 10px 10px -10px inset;
	-moz-box-shadow: black 0px 10px 10px -10px inset;
	box-shadow: black 0px 10px 10px -10px inset;
}

.copyright {
	text-align: center;
	font-size: 10px;
	color: #CCC;
}

.copyright a {
	color: #A31F1A;
	text-decoration: none
}

.on_off_checkbox {
	width: 0px;
}

#login .logo {
	width: 98%;
	height: 51px;
}
</style>
</head>
<body>
    <div id="alertMessage"></div>
    <div id="successLogin"></div>
    <div class="text_success"><img src="plug-in/login/images/loader_green.gif" alt="Please wait" /> <span>登陆成功!请稍后....</span></div>
 
    <div id="login">
<!--         <div class="ribbon" style="background-image: url(plug-in/login/images/typelogin.png);"></div> -->
        <div class="inner">
            <div class="logo"><img src="plug-in/weixin/logo/logo_weixin.png" /></div>
            <div class="formLogin">
                <form name="formLogin" id="formLogin" action="loginController.do?login2" check="loginController.do?checkuser" method="post">
                    <input name="userKey" type="hidden" id="userKey" value="D1B5CC2FE46C4CC983C073BCA897935608D926CD32992B5900" />
                    <div class="tip">
                        <input class="userName" name="userName" type="text" id="userName" title="用户名" iscookie="true" value="" nullmsg="请输入用户名!" />
                    </div>
                    <div class="tip">
                        <input class="password" name="password" type="password" id="password" title="密码" value="" nullmsg="请输入密码!" />
                    </div>
                    <div class="tip">
                        <input class="randCode" name="randCode" type="text" id="randCode" title="" value="" nullmsg="请输入验证码!" />
                        <div style="float: right; margin-left:-220px; margin-right: 10px;">
<!--                             <img id="randCodeImage" src="randCodeImage" /> -->
                        </div>
                    </div>
                    <%--update-end--Author:zhangguoming  Date:20140226 for：添加验证码--%>
                    <div class="loginButton">
                        <div style="float: left; margin-left: -9px;">
                            <img id="randCodeImage" src="randCodeImage" />
<!--                             <input type="checkbox" id="on_off" name="remember" checked="ture" class="on_off_checkbox" value="0" /> -->
<!--                             <span class="f_help">是否记住用户名 ?</span> -->
                        </div>

                        <div style="float: right; padding: 3px 0; margin-right: -12px;">
                            <div>
                                <ul class="uibutton-group">
                                    <li><a class="uibutton normal" href="#" id="but_login">登陆</a></li>
                                    <li><a class="uibutton normal" href="#" id="forgetpass">重置</a></li>
                                </ul>
                            </div>
                        </div>
                        <div class="clear"></div>
                    </div>
                     <div>
                        <div style="float: right; margin-left:-220px; margin-right: 40px;">
                           	  版本号：<font color="red">BOS</font>  &nbsp;&nbsp;&nbsp;  
<!--                            	   论坛：<a href="http://www.bos.org" target="_blank"><font color="red">www.bos.org</font></a> &nbsp;&nbsp;&nbsp;  -->
                           	   支持: <a href="http://www.bos.com" target="_blank"> <font color="red">www.bos.com</font></a> &nbsp;&nbsp;&nbsp; 
                        </div>
                    </div>
                </form>
            </div>
        </div>
<!-- 
        <div class="shadow"></div>
-->
    </div>
    <!--Login div-->
    <div class="clear"></div>
<!--
	<div id="versionBar">
        <div class="copyright">&copy; 版权所有 <span class="tip"> 珲春市农业局   &nbsp;&nbsp;&nbsp;网站备案号: <a href="http://www.miitbeian.gov.cn" title="备案号" target="_blank">吉ICP备17001915号</a></span></div>
    </div>
-->    
    <!-- Link JScript-->
    <script type="text/javascript" src="plug-in/jquery/jquery-1.8.3.min.js"></script>
    <script type="text/javascript" src="plug-in/jquery/jquery.cookie.js"></script>
    <script type="text/javascript" src="plug-in/login/js/jquery-jrumble.js"></script>
    <script type="text/javascript" src="plug-in/login/js/jquery.tipsy.js"></script>
    <script type="text/javascript" src="plug-in/login/js/iphone.check.js"></script>
    <script type="text/javascript" src="plug-in/login/js/login.js"></script>
    <script type="text/javascript" src="plug-in/lhgDialog/lhgdialog.min.js"></script>
</body>
</html>