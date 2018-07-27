package com.yunpay.permission.entity;
import com.yunpay.permission.dao.PermissionBaseDao;
@SuppressWarnings("rawtypes")
public interface AttachmentDao extends PermissionBaseDao {
	
	void updateAttach(SysAttach attach);
}
