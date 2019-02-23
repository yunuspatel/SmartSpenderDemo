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
	
	@Column(name="user_city",length=20)
	String userCity;
	
	@Column(name="user_pincode",length=10)
	String userPinCode;
	
	@Column(name="user_image",length=50)
	String userImage;
	
	@Column(name="user_gender",length=7)
	String userGender;
	
	@Column(name="user_dob",length=20)
	String userDob;

	@Column(name="stock_permission",length=6)
	boolean stockPermission;
	
	@Column(name="pre_loader_class",length=150)
	String preLoaderClass;
	
	public String getPreLoaderClass() {
		return preLoaderClass;
	}

	public void setPreLoaderClass(String preLoaderClass) {
		this.preLoaderClass = preLoaderClass;
	}

	public boolean isStockPermission() {
		return stockPermission;
	}

	public void setStockPermission(boolean stockPermission) {
		this.stockPermission = stockPermission;
	}

	public String getUserDob() {
		return userDob;
	}

	public void setUserDob(String userDob) {
		this.userDob = userDob;
	}

	public String getUserGender() {
		return userGender;
	}

	public void setUserGender(String userGender) {
		this.userGender = userGender;
	}

	public String getUserCity() {
		return userCity;
	}

	public void setUserCity(String userCity) {
		this.userCity = userCity;
	}

	public String getUserPinCode() {
		return userPinCode;
	}

	public void setUserPinCode(String userPinCode) {
		this.userPinCode = userPinCode;
	}

	public String getUserImage() {
		return userImage;
	}

	public void setUserImage(String userImage) {
		this.userImage = userImage;
	}

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