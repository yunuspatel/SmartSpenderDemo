package dao;

import vo.TrackingVo;

public class TrackingMasterDao {

	DbOperation dbOperation;
	
	public TrackingMasterDao() {
		// TODO Auto-generated constructor stub
		dbOperation=new DbOperation();
	}
	
	public void addTrack(TrackingVo trackingVo)
	{
		dbOperation.insert(trackingVo);
	}
}
