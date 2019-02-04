package vo;


import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name="category_master")
public class CategoryVo {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="category_id",length=5)
	int categoryId;
	
	@Column(name="for_category",length=10)
	String forCategory;
	
	@Column(name="category_name",length=20)
	String categoryName;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	@JoinColumn(name="category_id")
	Set<SubCategoriesVo> subCategories;

	@ManyToOne
	@JoinColumn(name="user_id",referencedColumnName="user_id")
	UserVo userVo;
	
	public UserVo getUserVo() {
		return userVo;
	}

	public void setUserVo(UserVo userVo) {
		this.userVo = userVo;
	}

	public Set<SubCategoriesVo> getSubCategories() {
		return subCategories;
	}

	public void setSubCategories(Set<SubCategoriesVo> subCategories) {
		this.subCategories = subCategories;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getForCategory() {
		return forCategory;
	}

	public void setForCategory(String forCategory) {
		this.forCategory = forCategory;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
}
