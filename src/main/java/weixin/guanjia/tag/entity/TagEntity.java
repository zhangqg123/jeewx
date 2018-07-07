package weixin.guanjia.tag.entity;

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
 * @Description: 标签
 * @author zhangdaihao
 * @date 2017-04-30 12:51:06
 * @version V1.0   
 *
 */
@Entity
@Table(name = "weixin_tag", schema = "")
@SuppressWarnings("serial")
public class TagEntity implements java.io.Serializable {
	/**编号*/
	private String id;
	/**标签名称*/
	private java.lang.String name;
	/**数量*/
	private java.lang.Integer count;
	
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  编号
	 */
	
	@Id
	@Column(name ="ID",nullable=false,length=20 )
	public String getId(){
		return this.id;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  编号
	 */
	public void setId(String id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  标签名称
	 */
	@Column(name ="NAME",nullable=true,length=50)
	public java.lang.String getName(){
		return this.name;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  标签名称
	 */
	public void setName(java.lang.String name){
		this.name = name;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  数量
	 */
	@Column(name ="COUNT",nullable=true,precision=10,scale=0)
	public java.lang.Integer getCount(){
		return this.count;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  数量
	 */
	public void setCount(java.lang.Integer count){
		this.count = count;
	}
}
