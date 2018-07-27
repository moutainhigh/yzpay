package com.yunpay.sdk.ctrl;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.yunpay.common.utils.CommonUtil;
import com.yunpay.common.utils.Log;
import com.yunpay.common.utils.ResultEnum.ErrorCode;
import com.yunpay.common.utils.ResultEnum.ResultCode;
import com.yunpay.serv.rep.Message;
import com.yunpay.serv.req.pay.PfMerBbReqDto;
import com.yunpay.serv.req.pay.PfMerBjReqDto;
import com.yunpay.serv.req.pay.PfWikiReqDto;
import com.yunpay.serv.service.pay.PfMerSetService;
import com.yunpay.serv.validate.DataValidateUtil;
import com.yunpay.serv.validate.exception.ValidateException;

/**
 * 浦发渠道商户设置外部接口服务类
 * 文件名称:     PfMerSetCtrl.java
 * 内容摘要: 
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年11月24日上午9:19:21 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年11月24日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
@Controller
public class PfMerSetCtrl extends BaseCtrl{
	
	@Autowired
	private PfMerSetService pfMerSetService;
	
	/**
	 * @Description: 浦发渠道商户报件
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年11月24日上午9:23:07 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/mer/baojian",method=RequestMethod.POST)
	@ResponseBody
	public Object doMerBaojian(HttpServletRequest request, HttpServletResponse response) {
		Map<String,String> reqParamMap = this.getRequestParams(request);
		Log.info("商户报件入口,接收参数：" + reqParamMap);
		Message msg = null;
		try{
			PfMerBjReqDto reqDto = (PfMerBjReqDto) CommonUtil.convertMap(PfMerBjReqDto.class, reqParamMap);
			if(!DataValidateUtil.valid(reqDto)){
				//基础信息验证不通过
				return new Message(ErrorCode.PARAM_FORMAT_ERROR.getCode(),ErrorCode.PARAM_FORMAT_ERROR.getDes());
			}
			//调用报件接口
			msg = pfMerSetService.doMerBaojian(reqDto);
		}catch (ValidateException e) {
			return new Message(e.getErrCode(),e.getErrMsg());
		}catch(Exception e){
			e.printStackTrace();
			return new Message(ResultCode.EXCEPTION.getCode(),ResultCode.EXCEPTION.getDes());
		}
		return msg;
	}
	
	/**
	 * @Description: 浦发渠道商户报备
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年11月24日上午9:23:21 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/mer/baobei",method=RequestMethod.POST)
	@ResponseBody
	public Object doPfMerBaobei(HttpServletRequest request, HttpServletResponse response) {
		Map<String,String> reqParamMap = this.getRequestParams(request);
		Log.info("商户报备入口,接收参数：" + reqParamMap);
		Message msg = null;
		try{
			PfMerBbReqDto reqDto = (PfMerBbReqDto) CommonUtil.convertMap(PfMerBbReqDto.class, reqParamMap);
			if(!DataValidateUtil.valid(reqDto)){
				//基础信息验证不通过
				return new Message(ErrorCode.PARAM_FORMAT_ERROR.getCode(),ErrorCode.PARAM_FORMAT_ERROR.getDes());
			}
			//调用报备接口
			msg = pfMerSetService.doMerBaobei(reqDto);
		}catch (ValidateException e) {
			return new Message(e.getErrCode(),e.getErrMsg());
		}catch(Exception e){
			e.printStackTrace();
			return new Message(ResultCode.EXCEPTION.getCode(),ResultCode.EXCEPTION.getDes());
		}
		return msg;
	}
	
	/**
	 * @Description:商户微信配置 
	 * @author:   Zeng Dongcheng
	 * @Date:     2017年11月24日上午9:23:34 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/mer/wiki",method=RequestMethod.POST)
	@ResponseBody
	public Object doPfMerWiki(HttpServletRequest request, HttpServletResponse response) {
		Map<String,String> reqParamMap = this.getRequestParams(request);
		Log.info("微信配置入口,接收参数：" + reqParamMap);
		Message msg = null;
		try{
			PfWikiReqDto reqDto = (PfWikiReqDto) CommonUtil.convertMap(PfWikiReqDto.class, reqParamMap);
			if(!DataValidateUtil.valid(reqDto)){
				//基础信息验证不通过
				return new Message(ErrorCode.PARAM_FORMAT_ERROR.getCode(),ErrorCode.PARAM_FORMAT_ERROR.getDes());
			}
			//调用微信配置接口
			msg = pfMerSetService.doWikiSet(reqDto);
		}catch (ValidateException e) {
			return new Message(e.getErrCode(),e.getErrMsg());
		}catch(Exception e){
			e.printStackTrace();
			return new Message(ResultCode.EXCEPTION.getCode(),ResultCode.EXCEPTION.getDes());
		}
		return msg;
	}
}
