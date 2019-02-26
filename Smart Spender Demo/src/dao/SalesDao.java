package dao;

import java.util.List;

import global.DbOperation;
import vo.SalesVo;
import vo.UserVo;

public class SalesDao {

	DbOperation dbOperation;
	List<SalesVo> list;
	
	public SalesDao() {
		dbOperation=new DbOperation();
	}
	
	public void addSales(SalesVo salesVo)
	{
		dbOperation.insert(salesVo);
	}
	
	public void updateSales(SalesVo salesVo)
	{
		dbOperation.update(salesVo);
	}
	
	public void deleteSales(SalesVo salesVo)
	{
		dbOperation.delete(salesVo);
	}
	
	public List<SalesVo> loadSales(UserVo userVo)
	{
		list=dbOperation.session.createQuery("from SalesVo where userVo.userId='" + userVo.getUserId() + "'").list();
		return list;
	}
}