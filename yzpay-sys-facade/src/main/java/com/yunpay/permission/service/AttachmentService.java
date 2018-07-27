package com.yunpay.permission.service;
import com.yunpay.permission.entity.SysAttach;

public interface AttachmentService {
	/**
	 * 根据附件id查询附件信息
	* 
	* @param 
	* @return Attachment
	* @throws
	 */
	SysAttach getAttachById(int attachId);
	
	/**
	 * 更新附件信息
	* 
	* @param 
	* @return void
	* @throws
	 */
	void updateAttach(SysAttach attach);
}
