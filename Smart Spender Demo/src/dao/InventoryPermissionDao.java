package dao;

import java.util.List;

import global.DbOperation;
import vo.InventoryPermissionVo;

public class InventoryPermissionDao {

	DbOperation dbOperation;
	List<InventoryPermissionVo> list;
	
	public InventoryPermissionDao()
	{
		dbOperation=new DbOperation();
	}
	
	public void addPermission(InventoryPermissionVo inventoryPermissionVo)
	{
		dbOperation.insert(inventoryPermissionVo);
	}
	
	public List<InventoryPermissionVo> getAllPermissionList()
	{
		list=dbOperation.session.createQuery("from InventoryPermissionVo where requestStatus='0'").list();
		return list;
	}
}