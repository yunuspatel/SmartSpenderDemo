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
@Table(name="inventory_permission")
public class InventoryPermissionVo {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="permission_id",length=5)
	int permissionId;
	
	@ManyToOne
	@JoinColumn(name="user_id",referencedColumnName="user_id")
	UserVo userVo;
	
	@Column(name="request_date_time",length=150)
	String requestDateTime;
	
	@Column(name="request_status",length=6)
	boolean requestStatus;
	
	@Column(name="admin_action",length=6)
	boolean adminAction;

	public boolean isAdminAction() {
		return adminAction;
	}

	public void setAdminAction(boolean adminAction) {
		this.adminAction = adminAction;
	}

	public int getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(int permissionId) {
		this.permissionId = permissionId;
	}

	public UserVo getUserVo() {
		return userVo;
	}

	public void setUserVo(UserVo userVo) {
		this.userVo = userVo;
	}

	public String getRequestDateTime() {
		return requestDateTime;
	}

	public void setRequestDateTime(String requestDateTime) {
		this.requestDateTime = requestDateTime;
	}

	public boolean isRequestStatus() {
		return requestStatus;
	}

	public void setRequestStatus(boolean requestStatus) {
		this.requestStatus = requestStatus;
	}
}