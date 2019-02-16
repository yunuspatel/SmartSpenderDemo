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
		list=dbOperation.session.createQuery("from TransactionVo where userVo.userId='" + userVo.getUserId() + "' and forTransaction='" + forTransaction + "'").list();
		return list;
	}
	
	public BigInteger getIdentificationNumber(String query)
	{
		dbOperation.transaction=dbOperation.session.beginTransaction();
		List list=dbOperation.session.createSQLQuery(query).list();
		return (BigInteger)list.get(0);
	}
	
	public List<TransactionVo> getLastTransaction(UserVo userVo)
	{
		dbOperation.transaction=dbOperation.session.beginTransaction();
		list=dbOperation.session.createQuery("from TransactionVo where isDeleted='0' and userVo.userId='" + userVo.getUserId() + "' order by transactionId desc").list();
		return list;
	}
	
	public List<TransactionVo> getTransactionForDisplay(UserVo userVo,String forTransaction)
	{
		dbOperation.transaction=dbOperation.session.beginTransaction();
		list=dbOperation.session.createQuery("from TransactionVo where userVo.userId='" + userVo.getUserId() + "' and forTransaction='" + forTransaction + "' and isDeleted=0").list();
		return list;
	}
	
	public TransactionVo  getTransactionByIdentificationNumber(String transactionIdentificationNumber)
	{
		dbOperation.transaction=dbOperation.session.beginTransaction();
		list=dbOperation.session.createQuery("from TransactionVo where transactionIdentificationNumber='" + transactionIdentificationNumber + "'").list();
		return list.get(0);
	}
	
	public void updateReceiptImage(TransactionVo transactionVo)
	{
		dbOperation.update(transactionVo);
	}
	
	public void updateTransaction(TransactionVo transactionVo)
	{
		dbOperation.update(transactionVo);
	}
	
	public List<TransactionVo> getAllTransactions(UserVo userVo)
	{
		list=dbOperation.session.createQuery("from TransactionVo where userVo.userId='" + userVo.getUserId() + "' and isDeleted='0'").list();
		return list;
	}
	
	public List<TransactionVo> getPreviousTransaction(TransactionVo transactionVo,UserVo userVo)
	{
		list=dbOperation.session.createQuery("from TransactionVo where userVo.userId='" + userVo.getUserId() + "' and transactionId<" + transactionVo.getTransactionId()).list();
		return list;
	}
	
	public List<TransactionVo> getLastTransactionForBalance(UserVo userVo)
	{
		list=dbOperation.session.createQuery("from TransactionVo where isDeleted='0' and userVo.userId='" + userVo.getUserId() + "' order by transactionId desc").list();
		return list;
	}
}