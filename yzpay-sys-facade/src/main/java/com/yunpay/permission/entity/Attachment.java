package com.yunpay.permission.entity;
import java.util.Date;
import com.yunpay.common.core.utils.annotation.Table;
@SuppressWarnings("serial")
@Table("t_sys_attach")
public class Attachment extends PermissionBaseEntity {
	private int id;   // 主键
	private String fileName;  // 原始文件名
	private String fileSuffix; // 文件后缀名
	private int fileSize;  // 文件大小(字节)
	private String savePath; // 保存目录
	private String saveName;  // 保存后的文件名
	private Date createTime;  // 上传日期
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
	public int getFileSize() {
		return fileSize;
	}
	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
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
	
	
}
