package com.yunpay.common.core.utils.io;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.codec.binary.Base64;

import com.yunpay.common.core.utils.DateUtils;
/**
 * 
 * 文件名称:
 * 内容摘要: 
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
public class ImageUtil {

	 //base64字符串转化成图片  
   public static File GenerateImage(String base64Code,String saveDir){   //对字节数组字符串进行Base64解码并生成图片  
		try{
			if (base64Code == null){
				return null;
			}
			else{
				base64Code = base64Code.split(",")[1];
			}
			// Base64解码
			byte[] b = Base64.decodeBase64(new String(base64Code).getBytes());
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {
					b[i] += 256;
				}
			}
			// 新生成的图片
			Thread.sleep(1500);
			File f = new File(saveDir+"/"+ DateUtils.getReqDate()+"_"+DateUtils.getNowTime()+".jpg");
			
			f.createNewFile();
			OutputStream out = new FileOutputStream(f);
			out.write(b);
			out.flush();
			out.close();
			return f;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
   }
   //base64字符串转化成图片  
   public static File GtImage(String base64Code,String savePath){   //对字节数组字符串进行Base64解码并生成图片  
	   try{
		   if (base64Code == null){
			   return null;
		   }
		   else{
			   base64Code = base64Code.split(",")[1];
		   }
		   // Base64解码
		   byte[] b = Base64.decodeBase64(new String(base64Code).getBytes());
		   for (int i = 0; i < b.length; ++i) {
			   if (b[i] < 0) {
				   b[i] += 256;
			   }
		   }
		   String saveName = new SimpleDateFormat("yyyyMMddHHmmssSSS") .format(new Date() );
		   // 新生成的图片
		   File f = new File(savePath+"/"+saveName+".jpg");
		   f.createNewFile();
		   OutputStream out = new FileOutputStream(f);
		   out.write(b);
		   out.flush();
		   out.close();
		   return f;
	   } catch (Exception e) {
		   e.printStackTrace();
		   return null;
	   }
   }
}
