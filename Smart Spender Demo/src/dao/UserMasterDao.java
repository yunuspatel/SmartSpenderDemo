package dao;

import java.util.List;

import global.DbOperation;
import vo.UserVo;

public class UserMasterDao {

	DbOperation dbOperation;
	List<UserVo> list;
	boolean value;

	public UserMasterDao() {
		// TODO Auto-generated constructor stub
		dbOperation = new DbOperation();
	}

	public void registerUser(UserVo userVo) {
		dbOperation.insert(userVo);
	}

	public List<UserVo> loginUser(UserVo userVo) {
		dbOperation.transaction = dbOperation.session.beginTransaction();
		list = dbOperation.session.createQuery("from UserVo where userEmail='" + userVo.getUserEmail() + "' and userPassword='" + userVo.getUserPassword() + "' and isDeleted=0 and isDeactivated='0'").list();
		if (!list.isEmpty()) {
			UserVo vo=list.get(0);
			value = checkIsActive(vo);
			list = dbOperation.session.createQuery("from UserVo where userEmail='" + userVo.getUserEmail() + "' and userPassword='" + userVo.getUserPassword() + "' and isDeleted=0").list();	
			if (value == true) {
				vo.setIsActive("1");
				list.set(0, vo);
			}
		}
		return list;
	}

	private boolean checkIsActive(UserVo userVo) {
		list = dbOperation.session.createQuery("from UserVo where userId=" + userVo.getUserId() + " and isActive=0").list();
		if (list.isEmpty()) {
			return true;
		}
		return false;
	}

	public List<UserVo> getUserDetails(UserVo userVo) {
		dbOperation.transaction = dbOperation.session.beginTransaction();
		list = dbOperation.session.createQuery("from UserVo where userId=" + userVo.getUserId() + " and isDeleted=0").list();
		return list;
	}

	public void updateUser(UserVo userVo) {
		dbOperation.update(userVo);
	}

	public boolean checkUserExists(UserVo userVo) {
		dbOperation.transaction = dbOperation.session.beginTransaction();
		list = dbOperation.session.createQuery("from UserVo where userMobile=" + userVo.getUserMobile() + " or userEmail='" + userVo.getUserEmail() + "'").list();
		if (list.isEmpty()) {
			return false;
		}
		return true;
	}

	public boolean checkUserForForgot(UserVo userVo) {
		dbOperation.transaction = dbOperation.session.beginTransaction();
		list = dbOperation.session.createQuery("from UserVo where userMobile=" + userVo.getUserMobile() + " and userEmail='" + userVo.getUserEmail() + "'").list();
		if (list.isEmpty()) {
			return false;
		}
		return true;
	}

	public UserVo getForgotUserDeatils(UserVo userVo) {
		dbOperation.transaction = dbOperation.session.beginTransaction();
		list = dbOperation.session.createQuery("from UserVo where userMobile=" + userVo.getUserMobile() + " and userEmail='" + userVo.getUserEmail() + "'").list();
		return list.get(0);
	}
}
