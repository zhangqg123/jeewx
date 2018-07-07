package weixin.guanjia.weixinuser.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.SequenceGenerator;

/**   
 * @Title: Entity
 * @Description: 微信用户
 * @author zhangdaihao
 * @date 2017-05-01 14:24:29
 * @version V1.0   
 *
 */
@Entity
@Table(name = "weixin_user", schema = "")
@SuppressWarnings("serial")
public class WeixinUserEntity implements java.io.Serializable {
	/**编号*/
	private java.lang.String id;
	/**用户订阅*/
	private java.lang.Integer subscribe;
	/**用户标识*/
//	private java.lang.String openid;
	/**用户昵称*/
	private java.lang.String nickname;
	/**性别*/
	private java.lang.String sex;
	/**城市*/
	private java.lang.String city;
	/**国家*/
	private java.lang.String country;
	/**省份*/
	private java.lang.String province;
	/**语言*/
	private java.lang.String language;
	/**头像url*/
	private java.lang.String headimgurl;
	/**关注时间*/
	private java.lang.String subscribeTime;
	/**公众号*/
	private java.lang.String unionid;
	/**备注*/
	private java.lang.String remark;
	/**标签列表*/
	private java.lang.String tagidList;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  编号
	 */
	
	@Id
	@Column(name ="ID",nullable=false,length=100)
	public java.lang.String getId(){
		return this.id;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  编号
	 */
	public void setId(java.lang.String id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  用户订阅
	 */
	@Column(name ="SUBSCRIBE",nullable=true,precision=10,scale=0)
	public java.lang.Integer getSubscribe(){
		return this.subscribe;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  用户订阅
	 */
	public void setSubscribe(java.lang.Integer subscribe){
		this.subscribe = subscribe;
	}
//	/**
//	 *方法: 取得java.lang.String
//	 *@return: java.lang.String  用户标识
//	 */
//	@Column(name ="OPENID",nullable=false,length=50)
//	public java.lang.String getOpenid(){
//		return this.openid;
//	}
//
//	/**
//	 *方法: 设置java.lang.String
//	 *@param: java.lang.String  用户标识
//	 */
//	public void setOpenid(java.lang.String openid){
//		this.openid = openid;
//	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  用户昵称
	 */
	@Column(name ="NICKNAME",nullable=true,length=50)
	public java.lang.String getNickname(){
		return this.nickname;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  用户昵称
	 */
	public void setNickname(java.lang.String nickname){
		this.nickname = nickname;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  性别
	 */
	@Column(name ="SEX",nullable=true,length=10)
	public java.lang.String getSex(){
		return this.sex;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  性别
	 */
	public void setSex(java.lang.String sex){
		this.sex = sex;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  城市
	 */
	@Column(name ="CITY",nullable=true,length=50)
	public java.lang.String getCity(){
		return this.city;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  城市
	 */
	public void setCity(java.lang.String city){
		this.city = city;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  国家
	 */
	@Column(name ="COUNTRY",nullable=true,length=50)
	public java.lang.String getCountry(){
		return this.country;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  国家
	 */
	public void setCountry(java.lang.String country){
		this.country = country;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  省份
	 */
	@Column(name ="PROVINCE",nullable=true,length=50)
	public java.lang.String getProvince(){
		return this.province;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  省份
	 */
	public void setProvince(java.lang.String province){
		this.province = province;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  语言
	 */
	@Column(name ="LANGUAGE",nullable=true,length=20)
	public java.lang.String getLanguage(){
		return this.language;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  语言
	 */
	public void setLanguage(java.lang.String language){
		this.language = language;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  头像url
	 */
	@Column(name ="HEADIMGURL",nullable=true,length=200)
	public java.lang.String getHeadimgurl(){
		return this.headimgurl;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  头像url
	 */
	public void setHeadimgurl(java.lang.String headimgurl){
		this.headimgurl = headimgurl;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  关注时间
	 */
	@Column(name ="SUBSCRIBE_TIME",nullable=true,length=50)
	public java.lang.String getSubscribeTime(){
		return this.subscribeTime;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  关注时间
	 */
	public void setSubscribeTime(java.lang.String subscribeTime){
		this.subscribeTime = subscribeTime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  公众号
	 */
	@Column(name ="UNIONID",nullable=true,length=50)
	public java.lang.String getUnionid(){
		return this.unionid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  公众号
	 */
	public void setUnionid(java.lang.String unionid){
		this.unionid = unionid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备注
	 */
	@Column(name ="REMARK",nullable=true,length=190)
	public java.lang.String getRemark(){
		return this.remark;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备注
	 */
	public void setRemark(java.lang.String remark){
		this.remark = remark;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  标签列表
	 */
	@Column(name ="TAGID_LIST",nullable=true,length=100)
	public java.lang.String getTagidList(){
		return this.tagidList;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  标签列表
	 */
	public void setTagidList(java.lang.String tagidList){
		this.tagidList = tagidList;
	}
}
