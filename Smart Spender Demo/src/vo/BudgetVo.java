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
@Table(name="budget_master")
public class BudgetVo {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="budget_id",length=10)
	int budgetId;
	
	@Column(name="budget_name",length=50)
	String budgetName;
	
	@ManyToOne
	@JoinColumn(name="user_id",referencedColumnName="user_id")
	UserVo userVo;
	
	@Column(name="budget_start_date",length=100)
	String budgetStartDate;
	
	@Column(name="budget_end_date",length=100)
	String budgetEndDate;
	
	@Column(name="budget_amount",length=10)
	float budgetAmount;

	@Column(name="budget_amount_left",length=10)
	float budgetAmountLeft;
	
	@Column(name="budget_alert_amount",length=10)
	float budgetAlertAmount;
	
	@Column(name="budget_description",length=250)
	String budgetDescription;
		
	@Column(name="is_deleted",length=6)
	boolean isDeleted;

	public int getBudgetId() {
		return budgetId;
	}

	public void setBudgetId(int budgetId) {
		this.budgetId = budgetId;
	}

	public String getBudgetName() {
		return budgetName;
	}

	public void setBudgetName(String budgetName) {
		this.budgetName = budgetName;
	}

	public UserVo getUserVo() {
		return userVo;
	}

	public void setUserVo(UserVo userVo) {
		this.userVo = userVo;
	}

	public String getBudgetStartDate() {
		return budgetStartDate;
	}

	public void setBudgetStartDate(String budgetStartDate) {
		this.budgetStartDate = budgetStartDate;
	}

	public String getBudgetEndDate() {
		return budgetEndDate;
	}

	public void setBudgetEndDate(String budgetEndDate) {
		this.budgetEndDate = budgetEndDate;
	}

	public float getBudgetAmount() {
		return budgetAmount;
	}

	public void setBudgetAmount(float budgetAmount) {
		this.budgetAmount = budgetAmount;
	}

	public float getBudgetAlertAmount() {
		return budgetAlertAmount;
	}

	public void setBudgetAlertAmount(float budgetAlertAmount) {
		this.budgetAlertAmount = budgetAlertAmount;
	}

	public String getBudgetDescription() {
		return budgetDescription;
	}

	public void setBudgetDescription(String budgetDescription) {
		this.budgetDescription = budgetDescription;
	}

	public float getBudgetAmountLeft() {
		return budgetAmountLeft;
	}

	public void setBudgetAmountLeft(float budgetAmountLeft) {
		this.budgetAmountLeft = budgetAmountLeft;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
}