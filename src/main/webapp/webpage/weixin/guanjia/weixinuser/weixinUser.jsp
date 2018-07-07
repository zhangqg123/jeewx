<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>微信用户</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
 </head>
 <body style="overflow-y: hidden" scroll="no">
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="weixinUserController.do?save">
			<input id="id" name="id" type="hidden" value="${weixinUserPage.id }">
			<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							用户订阅:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="subscribe" name="subscribe" ignore="ignore"
							   value="${weixinUserPage.subscribe}" datatype="n">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							用户标识:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="openid" name="openid" 
							   value="${weixinUserPage.openid}" datatype="*">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							用户昵称:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="nickname" name="nickname" ignore="ignore"
							   value="${weixinUserPage.nickname}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							性别:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="sex" name="sex" ignore="ignore"
							   value="${weixinUserPage.sex}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							城市:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="city" name="city" ignore="ignore"
							   value="${weixinUserPage.city}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							国家:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="country" name="country" ignore="ignore"
							   value="${weixinUserPage.country}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							省份:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="province" name="province" ignore="ignore"
							   value="${weixinUserPage.province}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							语言:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="language" name="language" ignore="ignore"
							   value="${weixinUserPage.language}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							头像url:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="headimgurl" name="headimgurl" ignore="ignore"
							   value="${weixinUserPage.headimgurl}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							关注时间:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="subscribeTime" name="subscribeTime" ignore="ignore"
							   value="${weixinUserPage.subscribeTime}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							公众号:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="unionid" name="unionid" ignore="ignore"
							   value="${weixinUserPage.unionid}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							备注:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="remark" name="remark" ignore="ignore"
							   value="${weixinUserPage.remark}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							标签列表:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="tagidList" name="tagidList" ignore="ignore"
							   value="${weixinUserPage.tagidList}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
			</table>
		</t:formvalid>
 </body>