package dao;

import java.util.List;

import global.DbOperation;
import vo.StockVo;
import vo.UserVo;

public class StockDao {

	DbOperation dbOperation;
	List<StockVo> list;
	
	public StockDao() {
		dbOperation=new DbOperation();
	}
	
	public List<StockVo> loadStock(UserVo userVo)
	{
		list=dbOperation.session.createQuery("from StockVo where userVo.userId='" + userVo.getUserId() + "'").list();
		return list;
	}
	
	public void addStock(StockVo stockVo)
	{
		dbOperation.insert(stockVo);
	}
	
	public void updateStock(StockVo stockVo)
	{
		dbOperation.update(stockVo);
	}
	
	public void deleteStock(StockVo stockVo)
	{
		dbOperation.delete(stockVo);
	}
}