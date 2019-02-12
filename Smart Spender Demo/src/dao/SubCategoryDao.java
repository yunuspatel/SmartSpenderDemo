package dao;

import java.util.List;

import global.DbOperation;
import vo.SubCategoriesVo;
import vo.UserVo;

public class SubCategoryDao {
	
	public DbOperation dbOperation;
	List<SubCategoriesVo> list;
	
	public SubCategoryDao()
	{
		dbOperation=new DbOperation();
	}
	
	public void deleteSubCategory(int categoryId)
	{
		dbOperation.transaction=dbOperation.session.beginTransaction();
		dbOperation.session.createSQLQuery("delete from sub_category_master where category_id='" + categoryId + "'");
	}
	
	public List<SubCategoriesVo> checkSubCategory(String subCategoryName,UserVo userVo)
	{
		dbOperation.transaction=dbOperation.session.beginTransaction();
		list=dbOperation.session.createQuery("from SubCategoriesVo where userVo.userId='" + userVo.getUserId() + "' and subCategoryName='" + subCategoryName + "'").list();
		return list;
	}
	
	public void addSubCategory(SubCategoriesVo subCategoriesVo)
	{
		dbOperation.insert(subCategoriesVo);
	}
	
	public void updateSubCategory(SubCategoriesVo subCategoriesVo)
	{
		dbOperation.update(subCategoriesVo);
	}
}