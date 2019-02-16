package vo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="transaction_master")
public class TransactionVo {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="transaction_id",length=10)
	int transactionId;
	
	@Column(name="transaction_identification_number",length=50)
	String transactionIdentificationNumber;
	
	@Column(name="payee_name",length=30)
	String payeeName;
	
	@ManyToOne
	@JoinColumn(name="user_id",referencedColumnName="user_id")
	UserVo userVo;
	
	@Column(name="for_transaction",length=10)
	String forTransaction;
	
	@Column(name="transaction_amount",length=10)
	float transactionAmount;
	
	@Column(name="transaction_date",length=50)
	String transactionDateTime;
	
	@Column(name="total_available_balance",length=15)
	float totalAvailableBalance;
	
	@ManyToOne
	@JoinColumn(name="category_id",referencedColumnName="category_id")
	CategoryVo categoryVo;
	
	@ManyToOne
	@JoinColumn(name="sub_category_id",referencedColumnName="sub_category_id")
	SubCategoriesVo subCategoriesVo;
	
	@Column(name="payment_method",length=20)
	String paymentMethod;
	
	@Column(name="status_of_transaction",length=20)
	String statusOfTransaction;
	
	@Column(name="transaction_number",length=50)
	String transactionNumber;
	
	@Column(name="extra_description",length=250)
	String extraDescription;
	
	@Column(name="receipt_image",length=80)
	String transactionReceiptImage;
	
	@Column(name="is_deleted",length=6)
	boolean isDeleted;

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getTransactionReceiptImage() {
		return transactionReceiptImage;
	}

	public void setTransactionReceiptImage(String transactionReceiptImage) {
		this.transactionReceiptImage = transactionReceiptImage;
	}

	public String getPayeeName() {
		return payeeName;
	}

	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}

	public int getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}

	public String getTransactionIdentificationNumber() {
		return transactionIdentificationNumber;
	}

	public void setTransactionIdentificationNumber(String transactionIdentificationNumber) {
		this.transactionIdentificationNumber = transactionIdentificationNumber;
	}

	public UserVo getUserVo() {
		return userVo;
	}

	public void setUserVo(UserVo userVo) {
		this.userVo = userVo;
	}

	public String getForTransaction() {
		return forTransaction;
	}

	public void setForTransaction(String forTransaction) {
		this.forTransaction = forTransaction;
	}

	public float getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(float transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public String getTransactionDateTime() {
		return transactionDateTime;
	}

	public void setTransactionDateTime(String transactionDateTime) {
		this.transactionDateTime = transactionDateTime;
	}

	public float getTotalAvailableBalance() {
		return totalAvailableBalance;
	}

	public void setTotalAvailableBalance(float totalAvailableBalance) {
		this.totalAvailableBalance = totalAvailableBalance;
	}

	public CategoryVo getCategoryVo() {
		return categoryVo;
	}

	public void setCategoryVo(CategoryVo categoryVo) {
		this.categoryVo = categoryVo;
	}

	public SubCategoriesVo getSubCategoriesVo() {
		return subCategoriesVo;
	}

	public void setSubCategoriesVo(SubCategoriesVo subCategoriesVo) {
		this.subCategoriesVo = subCategoriesVo;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getStatusOfTransaction() {
		return statusOfTransaction;
	}

	public void setStatusOfTransaction(String statusOfTransaction) {
		this.statusOfTransaction = statusOfTransaction;
	}

	public String getTransactionNumber() {
		return transactionNumber;
	}

	public void setTransactionNumber(String transactionNumber) {
		this.transactionNumber = transactionNumber;
	}

	public String getExtraDescription() {
		return extraDescription;
	}

	public void setExtraDescription(String extraDescription) {
		this.extraDescription = extraDescription;
	}
}