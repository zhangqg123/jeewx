package weixin.guanjia.message.model;

public class TextMessageGroup {
	private TextItem text;
	private String[] touser;
	private String msgtype;
	private SendFilter filter;
	
	public String[] getTouser() {
		return touser;
	}
	public void setTouser(String[] touser) {
		this.touser = touser;
	}
	public String getMsgtype() {
		return msgtype;
	}
	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}

	public TextItem getText() {
		return text;
	}
	public void setText(TextItem text) {
		this.text = text;
	}
	public SendFilter getFilter() {
		return filter;
	}
	public void setFilter(SendFilter filter) {
		this.filter = filter;
	}
}
