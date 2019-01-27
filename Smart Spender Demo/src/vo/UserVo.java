package vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="user_master")
public class UserVo {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="user_id",length=5)
	int userId;
	
	@Column(name="user_name",length=25,nullable=false)
	String userName;
	
	@Column(name="user_password",length=20,nullable=false)
	String userPassword;
	
	@Column(name="user_email",length=30,nullable=false)
	String userEmail;
	
	@Column(name="user_mobile",length=13,nullable=false)
	String userMobile;
	
	@Column(name="user_creation_date",length=50,nullable=false)
	String userCreationDate;
	
	@Column(name="user_active",length=2)
	String isActive;
	
	@Column(name="user_is_deleted",length=5)
	String isDeleted;
	
	@Column(name="user_confirmed",length=6)
	boolean isConfirmed;

	public boolean isConfirmed() {
		return isConfirmed;
	}

	public void setConfirmed(boolean isConfirmed) {
		this.isConfirmed = isConfirmed;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserMobile() {
		return userMobile;
	}

	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}

	public String getUserCreationDate() {
		return userCreationDate;
	}

	public void setUserCreationDate(String userCreationDate) {
		this.userCreationDate = userCreationDate;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}
}
