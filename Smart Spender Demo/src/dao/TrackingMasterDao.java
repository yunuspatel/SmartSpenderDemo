package dao;

import java.util.List;

import global.DbOperation;
import vo.TrackingVo;
import vo.UserVo;

public class TrackingMasterDao {

	DbOperation dbOperation;
	List<TrackingVo> trackList;
	
	public TrackingMasterDao() {
		// TODO Auto-generated constructor stub
		dbOperation=new DbOperation();
	}
	
	public void addTrack(TrackingVo trackingVo)
	{
		dbOperation.insert(trackingVo);
	}
	
	public List<TrackingVo> getTrackingDetails(UserVo userVo)
	{
		dbOperation.transaction=dbOperation.session.beginTransaction();
		trackList=dbOperation.session.createQuery("from TrackingVo where userVo.userId='" + userVo.getUserId() + "' order by trackingId desc").list();
		return trackList;
	}
}