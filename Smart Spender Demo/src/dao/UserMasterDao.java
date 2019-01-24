package dao;

import java.util.List;

import vo.UserVo;

public class UserMasterDao {

	DbOperation dbOperation;
	List<UserVo> list;
	boolean value;
	
	public UserMasterDao() {
		// TODO Auto-generated constructor stub
		dbOperation=new DbOperation();
	}
	
	public void registerUser(UserVo userVo)
	{
		dbOperation.insert(userVo);
	}
	
	public List<UserVo> loginUser(UserVo userVo)
	{
		dbOperation.transaction=dbOperation.session.beginTransaction();
		list=dbOperation.session.createQuery("from UserVo where userEmail='" + userVo.getUserEmail() + "' and userPassword='" + userVo.getUserPassword() + "' and isDeleted=0").list();
		value=checkIsActive(list.get(0));
		
		UserVo vo;
		if(value==true) {
			vo=list.get(0);
			vo.setIsActive("1");
			list.set(0, vo);
		}
		
		return list;
	}
	
	private boolean checkIsActive(UserVo userVo)
	{
		dbOperation.transaction=dbOperation.session.beginTransaction();
		list=dbOperation.session.createQuery("from UserVo where userId=" + userVo.getUserId() + " and isActive=0").list();
		if(list.isEmpty()) {
			return true;
		}
		return false;
	}

	public List<UserVo> getUserDetails(UserVo userVo)
	{
		dbOperation.transaction=dbOperation.session.beginTransaction();
		list=dbOperation.session.createQuery("from UserVo where userId=" + userVo.getUserId() + " and isDeleted=0").list();
		return list;
	}
}
