package com.yunpay.h5merch.service.impl;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yunpay.common.core.page.PageBean;
import com.yunpay.common.core.page.PageParam;
import com.yunpay.common.core.utils.DateUtils;
import com.yunpay.h5merch.permission.dao.TransLsDao;
import com.yunpay.h5merch.permission.entity.SumTransLs;
import com.yunpay.h5merch.permission.service.TransLsService;
@Service("transLsService")
public class TransLsServiceImpl implements TransLsService {
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
	@Autowired
	private TransLsDao transLsDao;
	
	/**
	 * 营业日报
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PageBean<SumTransLs> dayReport(PageParam pageParam,Map<String, Object> param) {
		return transLsDao.listPage(pageParam, param);
	}
	
	
	/**
	 * 
	* 输出日交易图形报表数据
	* @param 
	* @return List<SumTransLs>
	* @throws
	 */
	public List<SumTransLs> printDayReport(Map<String,Object> map){
		List<SumTransLs> list = this.transLsDao.printDayReport(map);
		// 一个队列，用于存放交易数据
		List<SumTransLs> queue = new ArrayList<SumTransLs>();
		this.listFilter(list, queue);
		return queue;
	}
	
	/**
	 * 
	* 集合数据过滤,以00-24点之间 的 间隔4小时内的区间数据进行汇总
	* @param 
	* @return void
	* @throws
	 */
	private void listFilter(List<SumTransLs> data,List<SumTransLs> queue){   
		if(data != null){
			List<SumTransLs> list = new ArrayList<SumTransLs>();
			int totalLenth = data.size(); // 总记录数 
			int step = 4; // 区间
			int length = (totalLenth / step);
			for(int n = 0; n<length; n++){ 	// 总共要计算的次数 
				// 指定索引
				int index = n * step;  
				// 每次取多少条记录
				int limit = (n * step) + step;
				String transTime = String.valueOf(limit);// String.valueOf(index);
				// 每次对相邻的4条记录进行区间汇总
				float sum = 0;
				for(int j=index; j<limit; j++){  
					SumTransLs s = data.get(j);
					float currentSum = s.getSumTran();
					sum = sum + currentSum;
				}
				list.add(new SumTransLs(transTime,sum));
			}
			queue.addAll(list);
		}else{
			throw new NullPointerException("ArrayList is null");
		}
	}
	
	/**
	 * 营业日报明细
	 */
	@Override
	public List<SumTransLs> reportDetail(Map<String, Object> param) {
		List<SumTransLs> list = this.transLsDao.listBy(param);
		return list;
	}

	@Override
	public PageBean<SumTransLs> bill(PageParam pageParam,Map<String, Object> param) {
		
		PageBean<SumTransLs> pageBean = this.transLsDao.bill(pageParam, param);
		return pageBean;
	
	}


	/**
	 *  按时间段 查询某个商户的总交易金额、总交易笔数
	 */
	@Override
	public SumTransLs sumTrans(Map<String, Object> map) {
		return this.transLsDao.sumTrans(map);
	}
	
	/**
	 * 按日统计某个商户的总交易笔数和总交易金额 
	* 
	* @param 
	* @return SumTransLs
	* @throws
	 */
	public SumTransLs sumTransByDay(Map<String,Object> map){
		// 获取当日的上一个月的日期:例如,当日为2017-10-30，则获取到的日期为2017-09-30
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, -1);
		String upDate = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
		map.put("transTime", upDate);
		
		String transTimeStr = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime())+"至"+DateUtils.getReqDate(new Date());
		SumTransLs s =  this.transLsDao.sumTransByDay(map);
		if(s == null){
			s = new SumTransLs();
			s.setCountTran(0);
			s.setSumTran(0.00f);
			s.setTransTime(transTimeStr);
		}
		s.setTransTime(transTimeStr);
		return s;
	}
		
	
	
	/**
	 * 按周统计某个商户的总交易笔数和总交易金额 
	* 
	* @param 
	* @return SumTransLs
	* @throws
	 */
	public SumTransLs sumTransByWeek(Map<String,Object> map){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try{
			Calendar c = Calendar.getInstance();
			// 获取本周的周日的日期
			Date thisWeekDay = format.parse(getThisWeekday(null));
			c.setTime(thisWeekDay);
			//获取本周周日的过去14周的周一
			c.add(Calendar.DATE, -(14 * 7) + 1);
			Date d = c.getTime();
			String day = format.format(d); // 获取过去14周的周一的日期
			map.put("transTime", day);
	    
			String monday =  getThisWeekday(null);  // 获取本周的周日的日期 
			map.put("monday", monday);
			
			String transTimeStr = format.format(d)+"至"+DateUtils.getReqDate(new Date());
			SumTransLs s = this.transLsDao.sumTransByWeek(map);
			if(s == null){
				s = new SumTransLs();
				s.setCountTran(0);
				s.setSumTran(0.00f);
				s.setTransTime(transTimeStr);
			}
			s.setTransTime(transTimeStr);
			return s;
		}catch(Exception e){
			e.printStackTrace();
			return new SumTransLs();
		}
	}
	
	
	/**
	 * 按月统计某个商户的总交易笔数和总交易金额 
	* 
	* @param 
	* @return SumTransLs
	* @throws
	 */
	public SumTransLs sumTransByMonth(Map<String,Object> map){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		//过去12个月
		c.setTime(new Date());
		c.add(Calendar.MONTH, -12);
		Date m = c.getTime();
		String upMonth = format.format(m); // 获取当日的过去12个月的年月日
		map.put("transTime", upMonth);
		
	
		String transTimeStr = format.format(m)+"至";
	
		c = Calendar.getInstance(); // 获取当前的年月日
		c.setTime(new Date());
		m = c.getTime();
		String nowMonth = format.format(m);
		map.put("nowMonth", nowMonth);
		
		transTimeStr = transTimeStr+format.format(m);
		SumTransLs s = this.transLsDao.sumTransByMonth(map);
		if(s == null){
			s = new SumTransLs();
			s.setCountTran(0);
			s.setSumTran(0.00f);
			s.setTransTime(transTimeStr);
		}
		s.setTransTime(transTimeStr);
		
		return s;
	}
	
	
	/**
	 * 输出 按日交易的报表 
	* @param 
	* @return SumTransLs
	* @throws
	 */
	public List<SumTransLs> printReportByDay(Map<String,Object> map){
		// 获取当日的上一个月的日期:例如,当日为2017-10-30，则获取到的日期为2017-09-30
		Calendar c = Calendar.getInstance();
	    c.add(Calendar.MONTH, -1);
	    String upDate = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
		map.put("transTime", upDate);
		
		String now = DateUtils.getReqDate(new Date()); 
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
		Date dBegin = null;
		Date dEnd = null;
		try{
			dBegin = sdf.parse(upDate);
			dEnd = sdf.parse(now); 
			sdf = new SimpleDateFormat("yyyy.MM.dd");  
			List<Date> lDate = findDates(dBegin, dEnd);  
			List<String> unionDate = new ArrayList<String>();
			for (Date date : lDate){  
				unionDate.add(sdf.format(date));
			}
			map.put("unionDate",unionDate);
			List<SumTransLs> queue = new ArrayList<SumTransLs>();
			List<SumTransLs> list = this.transLsDao.printReportByDay(map);
			this.calcTotalValueByDate(list,queue);
			return queue;
		}catch (ParseException e) {
			e.printStackTrace();
			return null;
		} 
	}
	
	
	
	/**
	 * 
	* 每4日为一组统计总交易笔数
	* @param 
	* @return void
	* @throws
	 */
	public void calcTotalValueByDate(List<SumTransLs> data,List<SumTransLs> queue){
		int totalLength = data.size();  // 总记录数
		int length = 0;  // 总共要计算的次数
		int step = 4;  // 步长
		if(totalLength % step == 0){
			length = totalLength / step;
		}else{
			length = totalLength / step + 1;
		}
		for(int n=0; n<length; n++){
			int index = n * step;
			int limit = n * step + step;
			if(limit > totalLength){
				int k = (limit - totalLength); // 超出的索引
				limit = limit - k;
			}
			float sum = 0.0f;
			String transTime = null;
			for(int j=index; j<limit; j++){
				SumTransLs s = data.get(j);
				float currentSum = s.getSumTran();
				sum = sum + currentSum;
				transTime = s.getTransTime();
			}
			queue.add(new SumTransLs(transTime,sum));
		}
	}
	
	 /**
	  * 遍历输出指定日期范围的日期
	 * 
	 * @param 
	 * @return List<Date>
	 * @throws
	  */
	 public static List<Date> findDates(Date dBegin, Date dEnd){  
		  List<Date> lDate = new ArrayList<Date>();  
		  lDate.add(dBegin);  
		  Calendar calBegin = Calendar.getInstance();  
		  // 使用给定的 Date 设置此 Calendar 的时间  
		  calBegin.setTime(dBegin);  
		  Calendar calEnd = Calendar.getInstance();  
		  // 使用给定的 Date 设置此 Calendar 的时间  
		  calEnd.setTime(dEnd);  
		  // 测试此日期是否在指定日期之后  
		  while (dEnd.after(calBegin.getTime())){  
			  // 根据日历的规则，为给定的日历字段添加或减去指定的时间量  
			  calBegin.add(Calendar.DAY_OF_MONTH, 1);  
			  lDate.add(calBegin.getTime());  
		  }  
		  return lDate;  
	 }
	 
	 

		/**
		 * 输出 按周交易的报表 
		* 
		* @param 
		* @return SumTransLs
		* @throws
		 */
		public List<SumTransLs> printReportByWeek(Map<String,Object> map){
			try {
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		        Calendar c = Calendar.getInstance();
		        // 获取本周的周日的日期 
		        Date thisWeekDay = format.parse(getThisWeekday(null));  
		        c.setTime(thisWeekDay);
		        //获取本周周日的过去14周的周一
		        c.add(Calendar.DATE, -(14 * 7) + 1);
		        Date d = c.getTime();
		        String day = format.format(d); // 获取过去14周的周一的日期
		        map.put("transTime", day);
		        
		        String monday =  getThisWeekday(null);  // 获取本周的周日的日期 
				map.put("monday", monday);
				Date dBegin = null;
				Date dEnd = null;
				dBegin = format.parse(day);
				dEnd = format.parse(monday);
				format = new SimpleDateFormat("yyyy.MM.dd");  
				List<Date> lDate = findDates(dBegin, dEnd);  
				List<String> unionDate = new ArrayList<String>();
				for (Date date : lDate){  
					unionDate.add(format.format(date));
				}
				map.put("unionDate",unionDate);
				List<SumTransLs> queue = new ArrayList<SumTransLs>();
				List<SumTransLs> data = this.transLsDao.printReportByWeek(map);
				this.calcTotalValueByWeek(data, queue);
				String transTime = queue.get(queue.size() - 1).getTransTime();
				queue.get(queue.size() - 1).setTransTime(transTime.split("-")[0]+"-"+format.format(new Date()));
				return queue;
			} catch (ParseException e) {
				e.printStackTrace();
				return null;
			} 
		}
		
		/**
		 * 
		* 每两周为一组统计总交易笔数
		* @param 
		* @return void
		* @throws
		 */
		public void calcTotalValueByWeek(List<SumTransLs> data,List<SumTransLs> queue){
			int totalLength = data.size();  // 总记录数
			int step = 14;  // 步长
			int length = totalLength / step;   // 总共要计算的次数
			for(int n=0; n<length; n++){
				int index = n * step;  
				int limit = n * step + step;
				float sum = 0;
				String transTime = data.get(index).getTransTime()+"-"+data.get(limit-1).getTransTime();
				for(int j=index; j<limit; j++){
					SumTransLs s = data.get(j);
					float currentSum = s.getSumTran();
					sum = sum + currentSum;	
				}
				queue.add(new SumTransLs(transTime,sum));
			}
		}
		
	
		/**
		 * 获取本周的周一日期
		* 
		* @param 
		* @return Date
		* @throws
		 */
	    public static Date getThisWeekMonday(Date date){  
	        Calendar cal = Calendar.getInstance();  
	        cal.setTime(date);  
	        // 获得当前日期是一个星期的第几天  
	        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);  
	        if (1 == dayWeek) {  
	            cal.add(Calendar.DAY_OF_MONTH, -1);  
	        }  
	        // 设置一个星期的第一天,星期一  
	        cal.setFirstDayOfWeek(Calendar.MONDAY);  
	        // 获得当前日期是一个星期的第几天  
	        int day = cal.get(Calendar.DAY_OF_WEEK);  
	        // 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值  
	        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);  
	        return cal.getTime();  
	    }
	    
	    /**
	     * 获取本周的周日 日期
	    * 
	    * @param 
	    * @return String
	    * @throws
	     */
	    public static String getThisWeekday(String foramt) {  
	    	SimpleDateFormat df = null;
	    	if(foramt == null){
	    		df = new SimpleDateFormat("yyyy-MM-dd");
	    	}else{
	    		df = new SimpleDateFormat("yyyy年MM月dd日");
	    	}
	    	Calendar cal =Calendar.getInstance();
	    	
	    	cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); //获取本周一的日期
	    	//这种输出的是上个星期周日的日期，因为老外那边把周日当成第一天
	    	cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
	    	  //增加一个星期，才是我们中国人理解的本周日的日期
	    	cal.add(Calendar.WEEK_OF_YEAR, 1);
	    	return df.format(cal.getTime());
	    }
	
			 /**
	  * 遍历输出指定日期范围的日期
	 * 
	 * @param 
	 * @return List<Date>
	 * @throws
	  */
	 public static List<Date> findDates2(Date dBegin, Date dEnd){  
		  List<Date> lDate = new ArrayList<Date>();  
		  lDate.add(dBegin);  
		  Calendar calBegin = Calendar.getInstance();  
		  // 使用给定的 Date 设置此 Calendar 的时间  
		  calBegin.setTime(dBegin);  
		  Calendar calEnd = Calendar.getInstance();  
		  // 使用给定的 Date 设置此 Calendar 的时间  
		  calEnd.setTime(dEnd);  
		  // 测试此日期是否在指定日期之后  
		  while (dEnd.after(calBegin.getTime())){  
			  // 根据日历的规则，为给定的日历字段添加或减去指定的时间量  
			  calBegin.add(Calendar.MONTH, 1);  
			  lDate.add(calBegin.getTime());  
		  }  
		  return lDate;  
	 }
	    
		/**
		 * 输出 按月交易的报表 
		* 
		* @param 
		* @return SumTransLs
		* @throws
		 */
		public List<SumTransLs> printReportByMonth(Map<String,Object> map){
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
			Calendar c = Calendar.getInstance();
			//过去12个月
			c.setTime(new Date());
			c.add(Calendar.MONTH, -12);
			Date m = c.getTime();
			String upMonth = format.format(m); // 获取当日的过去12个月的年月
			map.put("transTime", upMonth);
			
			c = Calendar.getInstance();
			c.setTime(new Date());
			c.add(Calendar.MONTH, 1);
			m = c.getTime();
			String nextMonth = format.format(m);
			map.put("transTime2", nextMonth);   // 获取下一个月的年月
			
			Date dBegin = null;
			Date dEnd = null;
			try {
				dBegin = format.parse(upMonth);  
				c = Calendar.getInstance(); // 获取当前的年月
				c.setTime(new Date());
				m = c.getTime();
				String nowMonth = format.format(m);
				dEnd = format.parse(nowMonth);
				
				format = new SimpleDateFormat("yyyy.MM");  
				List<Date> lDate = findDates2(dBegin, dEnd);  
				List<String> unionDate = new ArrayList<String>();
				for (Date date : lDate){  
					unionDate.add(format.format(date));
				}
				map.put("unionDate",unionDate);
				List<SumTransLs> queue = new ArrayList<SumTransLs>();
				List<SumTransLs> data = this.transLsDao.printReportByMonth(map);
				this.calcTotalValueByMonth(data, queue);
				return queue;
			}catch (ParseException e) {
				e.printStackTrace();
				return null;
			} 
		}
		
		/**
		 * 
		* 每两个月为一组统计总交易笔数
		* @param
		* @return void  11月-12月 、1月-2月 、3月-4月、5月-6月、7月-8月 、9月-10月、11月
		* 				
		* @throws
		 */
		public void calcTotalValueByMonth(List<SumTransLs> data,List<SumTransLs> queue){
			int totalLength = data.size();  // 总记录数
			int step = 2;  // 步长
			int length = (totalLength / step) + 1;   // 总共要计算的次数
			for(int n=0; n<length; n++){ 
				int index = n * step;  
				int limit = n * step + step;
				
				String transTime = null;
				if(n < length - 1){
					transTime = data.get(index).getTransTime()+"-"+data.get(limit-1).getTransTime();
				}else{
					transTime = data.get(index).getTransTime();
				}
				if(limit >totalLength){
					int k = (limit - totalLength); // 超出的索引
					limit = limit - k;
				}
				float sum = 0.0f;
				for(int j=index; j<limit; j++){                   
					SumTransLs s = data.get(j);
					float currentSum = s.getSumTran();
					sum = sum + currentSum;
				}
				queue.add(new SumTransLs(transTime,sum));
			}
		}
		
		

		

		
		
		
		
		
		
		
}
