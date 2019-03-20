package vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="super_user_master")
public class SuperUserVo {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="super_user_id",length=5)
	int superUserId;
	
	@Column(name="super_user_name",length=30)
	String superUserName;
	
	@Column(name="super_user_email",length=50)
	String superUserEmail;
	
	@Column(name="super_user_password",length=255)
	String superUserPassword;
		
	@Column(name="pre_loader_class",length=150)
	String preLoaderClass;

	public String getPreLoaderClass() {
		return preLoaderClass;
	}

	public void setPreLoaderClass(String preLoaderClass) {
		this.preLoaderClass = preLoaderClass;
	}

	public int getSuperUserId() {
		return superUserId;
	}

	public void setSuperUserId(int superUserId) {
		this.superUserId = superUserId;
	}

	public String getSuperUserName() {
		return superUserName;
	}

	public void setSuperUserName(String superUserName) {
		this.superUserName = superUserName;
	}

	public String getSuperUserEmail() {
		return superUserEmail;
	}

	public void setSuperUserEmail(String superUserEmail) {
		this.superUserEmail = superUserEmail;
	}

	public String getSuperUserPassword() {
		return superUserPassword;
	}

	public void setSuperUserPassword(String superUserPassword) {
		this.superUserPassword = superUserPassword;
	}
}