package dao;

import java.util.List;

import global.DbOperation;
import vo.CategoryVo;
import vo.UserVo;

public class CategoryMasterDao {

	public DbOperation dbOperation;
	List<CategoryVo> list;
	
	public CategoryMasterDao() 
	{
		dbOperation=new DbOperation();
	}
	
	public void addCategory(CategoryVo categoryVo)
	{
		dbOperation.insert(categoryVo);
	}
	
	public List<CategoryVo> getCategoryList(String forCategory,UserVo userVo)
	{
		dbOperation.transaction=dbOperation.session.beginTransaction();
		list=dbOperation.session.createQuery("from CategoryVo where forCategory='" + forCategory + "' and userVo.userId=" + userVo.getUserId()).list();
		return list;
	}
	
	public List<CategoryVo> getSubCategoryBasedOnName(String categoryName,String forCategory,UserVo userVo)
	{
		dbOperation.transaction=dbOperation.session.beginTransaction();
		list=dbOperation.session.createQuery("from CategoryVo where categoryName='" + categoryName + "' and forCategory='" + forCategory + "' and userVo.userId=" + userVo.getUserId()).list();
		return list;
	}
	
	public void updateCategory(CategoryVo categoryVo)
	{
		dbOperation.update(categoryVo);
	}
	
	public void deleteCategory(CategoryVo categoryVo)
	{
		dbOperation.delete(categoryVo);
	}
	
	public List<CategoryVo> checkCategoryBasedOnName(String categoryName,String forCategory,UserVo userVo)
	{
		dbOperation.transaction=dbOperation.session.beginTransaction();
		list=dbOperation.session.createQuery("from CategoryVo where userVo.userId='" + userVo.getUserId() + "' and forCategory='" + forCategory + "' and categoryName='" + categoryName + "'").list();
		return list;
	}
}
