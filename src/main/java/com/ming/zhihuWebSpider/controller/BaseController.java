package com.ming.zhihuWebSpider.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import tk.mybatis.mapper.entity.Example;

import com.alibaba.fastjson.JSON;
import com.ming.zhihuWebSpider.mapping.UserBaseInfoMapper;
import com.ming.zhihuWebSpider.mapping.UserDetailInfoMapper;
import com.ming.zhihuWebSpider.model.UserBaseInfo;
import com.ming.zhihuWebSpider.model.UserDetailInfo;
import com.ming.zhihuWebSpider.model.extend.BarInfoData;
import com.ming.zhihuWebSpider.model.extend.GenderInfo;
import com.ming.zhihuWebSpider.spider.UserBaseInfoCrawl;

@Controller
public class BaseController {

	@Autowired
	private UserBaseInfoMapper userBaseInfoMapper;
	
	@Autowired
	private UserDetailInfoMapper userDetailInfoMapper;
	
	@Autowired
	private UserBaseInfoCrawl userBaseInfoCrawl;
	
	@RequestMapping("zhihu")
	public String showPage(ModelMap model) {
		System.out.println("----------");
		model.put("test", "test");
		return "zhihu";
		
	}
	
	enum Gender{
		female,male,unknow
	}
	public int geAmountByGender(String gender){
		UserDetailInfo record = new UserDetailInfo();
		if(gender.equals(Gender.female.toString()))
			record.setGender(Gender.female.toString());
		else if(gender.equals(Gender.male.toString()))
			record.setGender(Gender.male.toString());
		else
			record.setGender(null);
		
		return userDetailInfoMapper.selectCount(record);	
	}
	
	@RequestMapping("getGenderInfo")
	public @ResponseBody List<GenderInfo> getGenderInfo(){
		List<GenderInfo> results = new ArrayList<GenderInfo>();
	
		UserDetailInfo record4 = new UserDetailInfo();
	   	record4.setGender("icon icon-profile-male");
	   	Integer man = userDetailInfoMapper.selectCount(record4);
	   	
	   	UserDetailInfo record5 = new UserDetailInfo();
	   	record5.setGender("icon icon-profile-female");
	   	Integer girl = userDetailInfoMapper.selectCount(record5);
	   	 
	   	Example example = new Example(UserDetailInfo.class);
	   	example.createCriteria().andIsNull("gender");
	   	Integer unknow = userDetailInfoMapper.selectCountByExample(example);
	   	
	   	results.add(new GenderInfo("男",man));
	   	results.add(new GenderInfo("女",girl));
	   	results.add(new GenderInfo("未知",unknow));
	   	
	   	System.err.println(JSON.toJSONString(results));
		return results;
	}
	
	@RequestMapping("getLocationInfo")
	public @ResponseBody BarInfoData getLocationInfo(){
		List<UserBaseInfo> result =  userBaseInfoMapper.getLocationStatic(10);
 		System.err.println("人群地域分布"+JSON.toJSONString(result));
 		String[] sxAxis = new String[result.size()];
 		Integer[] yAxis = new Integer[result.size()];
 		for(int i=0;i<result.size();i++){
 			sxAxis[i] = result.get(i).getLocation();
 			yAxis[i]  = result.get(i).getItemResultAmount();
 		}
 		BarInfoData data = new BarInfoData(yAxis,sxAxis);
 		data.setsXAxis(sxAxis);
 		data.setyAxis(yAxis);
 		System.err.println(JSON.toJSONString(data));
 		return data;
	}
	
	@RequestMapping("getBusinessStatic")
	public @ResponseBody BarInfoData getBusinessStatic(){
		List<UserDetailInfo> result =  userDetailInfoMapper.getBusinessStatic(10);
 		System.err.println("人群行业分布"+JSON.toJSONString(result));
 		String[] sxAxis = new String[result.size()];
 		Integer[] yAxis = new Integer[result.size()];
 		for(int i=0;i<result.size();i++){
 			sxAxis[i] = result.get(i).getBusiness();
 			yAxis[i]  = result.get(i).getItemResultAmount();
 		}
 		BarInfoData data = new BarInfoData(yAxis,sxAxis);
 		data.setsXAxis(sxAxis);
 		data.setyAxis(yAxis);
 		System.err.println(JSON.toJSONString(data));
 		return data;
	}
	
	@RequestMapping("getEducationStatic")
	public @ResponseBody BarInfoData getEducationStatic(){
		List<UserDetailInfo> result =  userDetailInfoMapper.getEducationStatic(10);
 		System.err.println("人群受教育程度分布"+JSON.toJSONString(result));
 		String[] sxAxis = new String[result.size()];
 		Integer[] yAxis = new Integer[result.size()];
 		for(int i=0;i<result.size();i++){
 			sxAxis[i] = result.get(i).getEducation();
 			yAxis[i]  = result.get(i).getItemResultAmount();
 		}
 		BarInfoData data = new BarInfoData(yAxis,sxAxis);
 		data.setsXAxis(sxAxis);
 		data.setyAxis(yAxis);
 		System.err.println(JSON.toJSONString(data));
 		return data;
	}
	
	@RequestMapping("getEmploymentStatic")
	public @ResponseBody BarInfoData getEmploymentStatic(){
		List<UserDetailInfo> result =  userDetailInfoMapper.getEmploymentStatic(10);
 		System.err.println("人群工作分布"+JSON.toJSONString(result));
 		String[] sxAxis = new String[result.size()];
 		Integer[] yAxis = new Integer[result.size()];
 		for(int i=0;i<result.size();i++){
 			sxAxis[i] = result.get(i).getEmployment();
 			yAxis[i]  = result.get(i).getItemResultAmount();
 		}
 		BarInfoData data = new BarInfoData(yAxis,sxAxis);
 		data.setsXAxis(sxAxis);
 		data.setyAxis(yAxis);
 		System.err.println(JSON.toJSONString(data));
 		return data;
	}
}
