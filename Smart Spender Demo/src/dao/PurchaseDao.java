package dao;

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
}