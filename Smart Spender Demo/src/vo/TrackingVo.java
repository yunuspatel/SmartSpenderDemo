package vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="tracking_login_master")
public class TrackingVo {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="tracking_id",length=5)
	int trackingId;
	
	@Column(name="user_name",length=25,nullable=false)
	String userName;
	
	@ManyToOne
	@JoinColumn(name="user_id",referencedColumnName="user_id")
	UserVo userVo;
	
	@Column(name="login_date_time",length=50)
	String loginDateTime;
	
	@Column(name="ip_address",length=25)
	String ipAddress;
	
	@Column(name="browser_name",length=150)
	String browserName;
	
	@Column(name="host_name",length=20)
	String hostName;
	
	@Column(name="port_name",length=5)
	String portNumber;

	public int getTrackingId() {
		return trackingId;
	}

	public void setTrackingId(int trackingId) {
		this.trackingId = trackingId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public UserVo getUserVo() {
		return userVo;
	}

	public void setUserVo(UserVo userVo) {
		this.userVo = userVo;
	}

	public String getLoginDateTime() {
		return loginDateTime;
	}

	public void setLoginDateTime(String loginDateTime) {
		this.loginDateTime = loginDateTime;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getBrowserName() {
		return browserName;
	}

	public void setBrowserName(String browserName) {
		this.browserName = browserName;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getPortNumber() {
		return portNumber;
	}

	public void setPortNumber(String portNumber) {
		this.portNumber = portNumber;
	}
}
