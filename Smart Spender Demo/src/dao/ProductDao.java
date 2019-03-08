package dao;

import java.util.List;

import global.DbOperation;
import vo.ProductVo;
import vo.UserVo;

public class ProductDao {

	DbOperation dbOperation;
	List<ProductVo> list;
	
	public ProductDao() {
		dbOperation=new DbOperation();
	}
	
	public void addProduct(ProductVo productVo)
	{
		dbOperation.insert(productVo);
	}
	
	public List<ProductVo> loadProducts(UserVo userVo)
	{
		list=dbOperation.session.createQuery("from ProductVo where userVo.userId='" + userVo.getUserId() + "'").list();
		return list;
	}
	
	public void deleteProduct(ProductVo productVo)
	{
		dbOperation.delete(productVo);
	}
	
	public void updateProduct(ProductVo productVo)
	{
		dbOperation.update(productVo);
	}
}