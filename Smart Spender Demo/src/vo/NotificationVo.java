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
@Table(name="user_notification")
public class NotificationVo {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="notification_id",length=10)
	int notificationId;
	
	@Column(name="notification_title",length=50)
	String notificationTitle;
	
	@Column(name="notification_message",length=250)
	String notificationMessage;
	
	@Column(name="notification_type",length=50)
	String notificationType;
	
	@ManyToOne
	@JoinColumn(name="user_id",referencedColumnName="user_id")
	UserVo userVo;
	
	@Column(name="notification_date_time",length=100)
	String notificationDateTime;
	
	@Column(name="notification_url",length=100)
	String notificationUrl;
	
	@Column(name="is_read",length=6)
	boolean isRead;

	public String getNotificationUrl() {
		return notificationUrl;
	}

	public void setNotificationUrl(String notificationUrl) {
		this.notificationUrl = notificationUrl;
	}

	public String getNotificationTitle() {
		return notificationTitle;
	}

	public void setNotificationTitle(String notificationTitle) {
		this.notificationTitle = notificationTitle;
	}

	public String getNotificationDateTime() {
		return notificationDateTime;
	}

	public void setNotificationDateTime(String notificationDateTime) {
		this.notificationDateTime = notificationDateTime;
	}

	public int getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(int notificationId) {
		this.notificationId = notificationId;
	}

	public String getNotificationMessage() {
		return notificationMessage;
	}

	public void setNotificationMessage(String notificationMessage) {
		this.notificationMessage = notificationMessage;
	}

	public String getNotificationType() {
		return notificationType;
	}

	public void setNotificationType(String notificationType) {
		this.notificationType = notificationType;
	}

	public UserVo getUserVo() {
		return userVo;
	}

	public void setUserVo(UserVo userVo) {
		this.userVo = userVo;
	}

	public boolean isRead() {
		return isRead;
	}

	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}
}