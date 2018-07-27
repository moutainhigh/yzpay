package com.yunpay.wx.rep.card;

import com.yunpay.common.utils.ResultEnum.ResultCode;

/**
 * 文件名称:     UpdCardRep.java
 * 内容摘要:    微信卡券修改结果返回封装类
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2018年3月1日上午10:26:03 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2018年3月1日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2018
 * 公司:   深圳市至高通信技术发展有限公司
 */
public class UpdCardRep extends AppBaseRep{
	//是否需要提审
	private boolean send_check;
	
	public UpdCardRep(boolean sendCheck){
		setReturn_code(ResultCode.SUCCESS.getCode());
		setSend_check(sendCheck);
	}
	
	public UpdCardRep(String errCode,String errCodeDes){
		super(errCode,errCodeDes);
	}

	public boolean isSend_check() {
		return send_check;
	}

	public void setSend_check(boolean send_check) {
		this.send_check = send_check;
	}
	
	
}
