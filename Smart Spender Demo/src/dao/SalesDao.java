package dao;

import java.math.BigInteger;
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
	
	public BigInteger getReferenceNumber(String query)
	{
		List list = dbOperation.session.createSQLQuery(query).list();
		return (BigInteger) list.get(0);
	}
	
	public SalesVo getSalesById(String salesId)
	{
		list = dbOperation.session.createQuery("from SalesVo where salesId='" + salesId + "'").list();
		return list.get(0);
	}
}