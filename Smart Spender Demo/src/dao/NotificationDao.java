package dao;

import java.util.List;

import global.DbOperation;
import vo.NotificationVo;
import vo.UserVo;

public class NotificationDao {

	DbOperation dbOperation;
	List<NotificationVo> list;
	
	public NotificationDao() {
		dbOperation=new DbOperation();
	}
	
	public void addNotification(NotificationVo notificationVo)
	{
		dbOperation.insert(notificationVo);
	}
	
	public void updateNotification(NotificationVo notificationVo)
	{
		dbOperation.update(notificationVo);
	}
	
	public List<NotificationVo> getAllNotifications(UserVo userVo)
	{
		list=dbOperation.session.createQuery("from NotificationVo where isRead='0' and userVo.userId='" + userVo.getUserId() + "' order by notificationId desc").list();
		return list;
	}
	
	public NotificationVo getNotificationById(NotificationVo notificationVo)
	{
		list=dbOperation.session.createQuery("from NotificationVo where notificationId='" + notificationVo.getNotificationId() + "'").list();
		return list.get(0);
	}
}