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
@Table(name="product_master")
public class ProductVo {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="product_id",length=5)
	int productId;
	
	@Column(name="product_name",length=50)
	String productName;
	
	@Column(name="brand_name",length=50)
	String brandName;
	
	@Column(name="unit_of_mesaurement",length=30)
	String unitOfMesaurement;
	
	@ManyToOne
	@JoinColumn(name="user_id",referencedColumnName="user_id")
	UserVo userVo;

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getUnitOfMesaurement() {
		return unitOfMesaurement;
	}

	public void setUnitOfMesaurement(String unitOfMesaurement) {
		this.unitOfMesaurement = unitOfMesaurement;
	}

	public UserVo getUserVo() {
		return userVo;
	}

	public void setUserVo(UserVo userVo) {
		this.userVo = userVo;
	}
}