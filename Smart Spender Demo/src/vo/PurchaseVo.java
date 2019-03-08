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
@Table(name="purchase_master")
public class PurchaseVo {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="purchase_id",length=4)
	int purchaseId;
	
	@ManyToOne
	@JoinColumn(name="product_id",referencedColumnName="product_id")
	ProductVo productVo;
	
	@ManyToOne
	@JoinColumn(name="user_id",referencedColumnName="user_id")
	UserVo userVo;
	
	@Column(name="dealer_name",length=30)
	String dealerName;
	
	@Column(name="purchase_price",length=10)
	float purchasePrice;
	
	@Column(name="product_quantity",length=10)
	int quantity;
	
	@Column(name="purchase_date_time",length=150)
	String purchaseDateTime;
	
	@Column(name="purchase_identification_number",length=50)
	String purchaseIdentificationNumber;
	
	@Column(name="purchase_receipt_image",length=100)
	String purchaseReceiptImage;

	public String getPurchaseIdentificationNumber() {
		return purchaseIdentificationNumber;
	}

	public void setPurchaseIdentificationNumber(String purchaseIdentificationNumber) {
		this.purchaseIdentificationNumber = purchaseIdentificationNumber;
	}

	public String getPurchaseReceiptImage() {
		return purchaseReceiptImage;
	}

	public void setPurchaseReceiptImage(String purchaseReceiptImage) {
		this.purchaseReceiptImage = purchaseReceiptImage;
	}

	public String getPurchaseDateTime() {
		return purchaseDateTime;
	}

	public void setPurchaseDateTime(String purchaseDateTime) {
		this.purchaseDateTime = purchaseDateTime;
	}

	public int getPurchaseId() {
		return purchaseId;
	}

	public void setPurchaseId(int purchaseId) {
		this.purchaseId = purchaseId;
	}

	public ProductVo getProductVo() {
		return productVo;
	}

	public void setProductVo(ProductVo productVo) {
		this.productVo = productVo;
	}

	public UserVo getUserVo() {
		return userVo;
	}

	public void setUserVo(UserVo userVo) {
		this.userVo = userVo;
	}

	public String getDealerName() {
		return dealerName;
	}

	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}

	public float getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(float purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}