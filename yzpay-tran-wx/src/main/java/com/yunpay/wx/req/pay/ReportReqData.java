package com.yunpay.wx.req.pay;

import com.yunpay.common.utils.CommonUtil;

/**
 * 微信接口调用耗时上报请求类
 * 文件名称:     ReportReqData.java
 * 内容摘要: 
 * @author:   Zeng Dongcheng
 * @version:  1.0  
 * @Date:     2017年6月16日下午3:31:17 
 * 
 * 修改历史:  
 * 修改日期                     修改人员                                   版本	            修改内容  
 * ----------------------------------------------  
 * 2017年6月16日     Zeng Dongcheng   1.0     新建
 *
 * 版权:   版权所有(C)2017
 * 公司:   深圳市至高通信技术发展有限公司
 */
public class ReportReqData {
    //每个字段具体的意思请查看API文档
    private String appid;
    private String mch_id;
    private String sub_mch_id;
    private String device_info;
    private String nonce_str;
    private String sign;
    //上报对应的接口的完整URL，类似：https://api.mch.weixin.qq.com/pay/unifiedorder
    private String interface_url;
    //接口耗时情况，单位为毫秒
    private int execute_time_cost;
    //发起接口调用时的机器IP
    private String user_ip;
    //上报该统计请求时的系统时间，格式为yyyyMMddHHmmss
    private String time;

    //以下是API接口返回的对应数据
    private String return_code;
    private String return_msg;
    private String result_code;
    private String err_code;
    private String err_code_des;
    private String out_trade_no;
    

    /**
     * 微信接口耗时上报请求类
     * @param appId  
     * @param merchId
     * @param deviceInfo  设备编号
     * @param interfaceUrl
     * @param executeTimeCost 接口消耗时间
     * @param returnCode  	  接口返回码
     * @param returnMsg      返回内容
     * @param resultCode     执行结果码
     * @param errCode        错误码
     * @param errCodeDes     错误内容
     * @param outTradeNo     提交的订单号
     * @param userIp         支付发起的ip
     */
    public ReportReqData(String appId,String merchId,String deviceInfo, String interfaceUrl,
    		int executeTimeCost, String returnCode,String returnMsg,String resultCode,
    		String errCode,String errCodeDes, String outTradeNo,String userIp){
        //微信分配的公众号ID（开通公众号之后可以获取到）
        setAppid(appId);
        //商户系统自己生成的唯一的订单号
        setOut_trade_no(outTradeNo);
        //微信支付分配的商户号ID（开通公众号的微信支付功能之后可以获取到）
        setMch_id(merchId);
        setSub_mch_id("");
        setDevice_info(deviceInfo);
        setInterface_url(interfaceUrl);
        setExecute_time_cost(executeTimeCost);
        setReturn_code(returnCode);
        setReturn_msg(returnMsg);
        setResult_code(resultCode);
        setErr_code(errCode);
        setErr_code_des(errCodeDes);
        setUser_ip(userIp);
        setTime(getTime());
        //随机字符串，不长于32 位
        setNonce_str(CommonUtil.getRandomStringByLength(32));
    }
  
    
    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getMch_id() {
        return mch_id;
    }

    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    public String getSub_mch_id() {
        return sub_mch_id;
    }

    public void setSub_mch_id(String sub_mch_id) {
        this.sub_mch_id = sub_mch_id;
    }

    public String getDevice_info() {
        return device_info;
    }

    public void setDevice_info(String device_info) {
        this.device_info = device_info;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getInterface_url() {
        return interface_url;
    }

    public void setInterface_url(String interface_url) {
        this.interface_url = interface_url;
    }

    public int getExecute_time_cost() {
        return execute_time_cost;
    }

    public void setExecute_time_cost(int execute_time) {
        this.execute_time_cost = execute_time;
    }

    public String getReturn_code() {
        return return_code;
    }

    public void setReturn_code(String return_code) {
        this.return_code = return_code;
    }

    public String getReturn_msg() {
        return return_msg;
    }

    public void setReturn_msg(String return_msg) {
        this.return_msg = return_msg;
    }

    public String getResult_code() {
        return result_code;
    }

    public void setResult_code(String result_code) {
        this.result_code = result_code;
    }

    public String getErr_code() {
        return err_code;
    }

    public void setErr_code(String err_code) {
        this.err_code = err_code;
    }

    public String getErr_code_des() {
        return err_code_des;
    }

    public void setErr_code_des(String err_code_des) {
        this.err_code_des = err_code_des;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getUser_ip() {
        return user_ip;
    }

    public void setUser_ip(String user_ip) {
        this.user_ip = user_ip;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    

}
