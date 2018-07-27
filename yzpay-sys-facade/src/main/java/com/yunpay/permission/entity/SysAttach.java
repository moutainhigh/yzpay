package com.yunpay.permission.entity;
import java.util.Date;
import com.yunpay.common.core.utils.annotation.Table;

@Table("t_sys_attach")
public class SysAttach extends PermissionBaseEntity {
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 * 文件名称:
	 * 内容摘要: 系统附件表,存放所有的附件信息 
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
	
	private int id;   // 主键
	private String fileName;  // 原始文件名称
	private String fileSuffix; // 文件后缀名
	private long fileSize;     // 文件占用字节点
	private String savePath;   // 文件位于服务器的路径
	private String saveName;   // 保存后的文件名
	private Date createTime;   // 创建时间
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileSuffix() {
		return fileSuffix;
	}
	public void setFileSuffix(String fileSuffix) {
		this.fileSuffix = fileSuffix;
	}
	public String getSavePath() {
		return savePath;
	}
	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}
	public String getSaveName() {
		return saveName;
	}
	public void setSaveName(String saveName) {
		this.saveName = saveName;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public long getFileSize() {
		return fileSize;
	}
	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}
	
	
	
	
	
}
