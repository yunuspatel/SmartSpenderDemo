package dao;

import java.util.List;

import global.DbOperation;
import vo.BudgetVo;
import vo.UserVo;

public class BudgetMasterDao {
	
	DbOperation dbOperation;
	List<BudgetVo> list;
	
	public BudgetMasterDao()
	{
		dbOperation=new DbOperation();
	}

	public void addBudget(BudgetVo budgetVo)
	{
		dbOperation.insert(budgetVo);
	}
	
	public void updateBudget(BudgetVo budgetVo)
	{
		dbOperation.update(budgetVo);
	}
	
	public List<BudgetVo> getAllBudgets(UserVo userVo)
	{
		list=dbOperation.session.createQuery("from BudgetVo where isDeleted='0' and userVo.userId='" + userVo.getUserId() + "'").list();
		return list;
	}
}