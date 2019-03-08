package dao;

import java.math.BigInteger;
import java.util.List;

import global.DbOperation;
import vo.PurchaseVo;
import vo.UserVo;

public class PurchaseDao {

	DbOperation dbOperation;
	List<PurchaseVo> list;
	
	public PurchaseDao() {
		dbOperation=new DbOperation();
	}
	
	public void addPurchase(PurchaseVo purchaseVo)
	{
		dbOperation.insert(purchaseVo);
	}
	
	public void updatePurchase(PurchaseVo purchaseVo)
	{
		dbOperation.update(purchaseVo);
	}
	
	public void deletePurchase(PurchaseVo purchaseVo)
	{
		dbOperation.delete(purchaseVo);
	}
	
	public List<PurchaseVo> loadPurchases(UserVo userVo)
	{
		list=dbOperation.session.createQuery("from PurchaseVo where userVo.userId='" + userVo.getUserId() + "'").list();
		return list;
	}
	
	public BigInteger getReferenceNumber(String query)
	{
		List list=dbOperation.session.createSQLQuery(query).list();
		return (BigInteger) list.get(0);
	}
	
	public PurchaseVo getPurchaseById(String purchaseId)
	{
		list=dbOperation.session.createQuery("from PurchaseVo where purchaseId='" + purchaseId + "'").list();
		return list.get(0);
	}
}