package com.yunpay.permission.service.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yunpay.permission.entity.AttachmentDao;
import com.yunpay.permission.entity.SysAttach;
import com.yunpay.permission.service.AttachmentService;
/**
 * 
 * 文件名称:
 * 内容摘要: 附件信息
 * @author:   duan zhang quan
 * @version:  1.0  
 * @Date:     2017年4月14日上午9:56:12 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年4月14日     duan zhang quan   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
@Service("attachmentService")
public class AttachmentServiceImpl implements AttachmentService {
	@Autowired
	private AttachmentDao attachmentDao;
	
	/**
	 * 根据附件id查询附件信息
	 */
	@Override
	public SysAttach getAttachById(int attachId) {
		SysAttach attach = (SysAttach)this.attachmentDao.getById((long) attachId);
		return attach;
	}
	
	public void updateAttach(SysAttach attach){
		this.attachmentDao.updateAttach(attach);
	}
	
}
