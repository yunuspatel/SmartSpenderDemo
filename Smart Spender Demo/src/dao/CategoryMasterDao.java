package dao;

import java.util.List;

import vo.CategoryVo;
import vo.UserVo;

public class CategoryMasterDao {

	DbOperation dbOperation;
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
}
