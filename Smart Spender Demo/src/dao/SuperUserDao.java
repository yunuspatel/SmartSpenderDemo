package dao;

import java.util.List;

import global.DbOperation;
import vo.SuperUserVo;

public class SuperUserDao {

	DbOperation dbOperation;
	List<SuperUserVo> list;
	
	public SuperUserDao() {
		dbOperation=new DbOperation();
	}
	
	public List<SuperUserVo> loginSuperUser(SuperUserVo superUserVo)
	{
		list=dbOperation.session.createQuery("from SuperUserVo where superUserEmail='" + superUserVo.getSuperUserEmail() + "' and superUserPassword='" + superUserVo.getSuperUserPassword() + "'").list();
		return list;
	}
	
	public void updateSuperUser(SuperUserVo superUserVo)
	{
		dbOperation.update(superUserVo);
	}
}