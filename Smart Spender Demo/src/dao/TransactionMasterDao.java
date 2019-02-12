package dao;

import java.math.BigInteger;
import java.util.List;

import global.DbOperation;
import vo.TransactionVo;
import vo.UserVo;

public class TransactionMasterDao {

	DbOperation dbOperation;
	List<TransactionVo> list;
	
	public TransactionMasterDao()
	{
		dbOperation=new DbOperation();
	}
	
	public void addTransaction(TransactionVo transactionVo)
	{
		dbOperation.insert(transactionVo);
	}
	
	public List<TransactionVo> getTransactionList(UserVo userVo,String forTransaction)
	{
		dbOperation.transaction=dbOperation.session.beginTransaction();
		list=dbOperation.session.createQuery("from TransactionVo where userVo.userId='" + userVo.getUserId() + "' and forTransaction='" + forTransaction + "'").list();
		return list;
	}
	
	public BigInteger getIdentificationNumber(String query)
	{
		dbOperation.transaction=dbOperation.session.beginTransaction();
		List list=dbOperation.session.createSQLQuery(query).list();
		return (BigInteger)list.get(0);
	}
	
	public List<TransactionVo> getLastTransaction(UserVo userVo,String forTransaction)
	{
		dbOperation.transaction=dbOperation.session.beginTransaction();
		list=dbOperation.session.createQuery("from TransactionVo where userVo.userId='" + userVo.getUserId() + "' and forTransaction='income' order by transactionId desc").list();
		return list;
	}
}