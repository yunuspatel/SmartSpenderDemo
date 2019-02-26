package vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="sales_master")
public class SalesVo {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="sales_id",length=5)
	int salesId;
	
	@Column(name="customer_name",length=30)
	String customerName;
	
	@ManyToOne
	@JoinColumn(name="product_id",referencedColumnName="product_id")
	ProductVo productVo;
	
	@ManyToOne
	@JoinColumn(name="user_id",referencedColumnName="user_id")
	UserVo userVo;
	
	@Column(name="sales_price",length=10)
	float salesPrice;
	
	@Column(name="discount_price",length=10)
	String discountPrice;
	
	@Column(name="sales_quantity",length=10)
	int salesQuantity;
	
	@Column(name="sales_date_time",length=150)
	String salesDateTime;

	public int getSalesId() {
		return salesId;
	}

	public void setSalesId(int salesId) {
		this.salesId = salesId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
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

	public float getSalesPrice() {
		return salesPrice;
	}

	public void setSalesPrice(float salesPrice) {
		this.salesPrice = salesPrice;
	}

	public String getDiscountPrice() {
		return discountPrice;
	}

	public void setDiscountPrice(String discountPrice) {
		this.discountPrice = discountPrice;
	}

	public int getSalesQuantity() {
		return salesQuantity;
	}

	public void setSalesQuantity(int salesQuantity) {
		this.salesQuantity = salesQuantity;
	}

	public String getSalesDateTime() {
		return salesDateTime;
	}

	public void setSalesDateTime(String salesDateTime) {
		this.salesDateTime = salesDateTime;
	}
}