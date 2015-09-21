package com.bs.web.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;


public class SendSmsUtil {
	
	public static void main(String[] args) {
		
		Map<String,String> jsonmap = JSONObject.fromObject("{result:1,message:\" 成功\"}");
		System.out.println(jsonmap.keySet());
		System.out.println(String.valueOf(jsonmap.get("result"))+", "+jsonmap.get("message"));
		//sendSms("15313394710", "", "测试");
	}
	
	/**
	 * 发送短信失败，电话号码写入文件
	 * @param path
	 * @param list
	 * @throws IOException
	 */
	public static void writeFailMobile(String path,List<String> list) throws IOException{
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File(path)));
		for(int i=0;i<list.size();++i){
			bw.write(list.get(i)+"\n");
		}
		bw.flush();
		bw.close();
	}
	
	/**
	 * 从文件读取电话号码
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public static List readFile(String path) throws Exception{
		List<String> list = new ArrayList<String>();
		File f = new File(path);
		BufferedReader br = new BufferedReader(new FileReader(f));
		String line = "";
		while((line=br.readLine())!=null){
			list.add(line);
		}
		return list;
	}
	
	/**
	 * 发送短信
	 * @param mobile 手机号
	 * @param type 模板类型
	 * @param content 短信内容
	 */
	public static Map<String,String> sendSms(String mobile,String type,String content){
		Map<String,String> map = new HashMap<String,String>();
		HttpClient httpClient = new  HttpClient();
		PostMethod method = new PostMethod(PropertiesUtil.prop.getProperty("POST_URL"));
//		PostMethod method = new PostMethod("http://124.192.161.110:8081");
		method.setParameter(
				HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
		try {
			method.setRequestBody( new NameValuePair[]{
					new NameValuePair("serviceid","20120806002"),
					new NameValuePair("sendtarget",mobile),
					new NameValuePair("smcontent",java.net.URLEncoder.encode(content, "UTF-8")),
					new NameValuePair("sign","103"),
					new NameValuePair("priority","0"),
					new NameValuePair("sendTime",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()))

			});
			httpClient.executeMethod(method);
			if(method.getStatusCode()==HttpStatus.SC_OK){
				String response = method.getResponseBodyAsString();
				
				Map jsonmap = JSONObject.fromObject(response);
				map.put(mobile, response);
				method.releaseConnection();
			}
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
}
