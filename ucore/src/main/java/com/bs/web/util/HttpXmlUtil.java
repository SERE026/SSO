package com.bs.web.util;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**此版本使用document 对象封装XML，解决发送短信内容包涵特殊字符而出现无法解析，如 短信为：“你好，<%&*&*&><<<>fds测试短信”
 */
public class HttpXmlUtil {

		// ############################此部分参数需要修改############################
		public static String content = "尊敬的用户您好，我平台由于新推出的注册返10元现金活动太给力，客户参与较多，部分时段超过网站浏览量负载，致使网页打开缓慢，建议您避开浏览高峰，给您带来的不便，敬请谅解！"; // 短信内容
		public static String sign = "【钱趣多】"; // 短信内容
		public static String msgid ="";
		public static String subcode ="";
		public static String sendtime ="";
		//public static String url="http://3tong.net/http/sms/Submit"; //无限通使用地址
		//public static String url="http://3tong.net/http/sms/Submit"; 
		public static String url = "http://www.yxuntong.com:8081/emmpdata/sms/Submit"; 
		// ############################此部分参数需要修改############################
	

		public static void main(String[] args) throws Exception {

			// 发送短信
			System.out.println("*************发送短信*************");
			List<String> list = readFile("C:\\Users\\sere\\Desktop\\mobile.csv");
			List result = new ArrayList();
			/*Map map = sendTest("15311168656","尊敬的用户您好，我平台由于新推出的注册返10元现金活动太给力，客户参与较多，部分时段超过网站浏览量负载，致使网页打开缓慢，建议您避开浏览高峰，给您带来的不便，敬请谅解！");
			System.out.println("返回结果["+map.get("result")+":"+map.get("desc")+"]");*/
			for(int i=0;i<list.size();++i){
				String userInfo = list.get(i);
//				String[] user = userInfo.split(",");
//				Map map = sendTest(user[0],content.replace("USER", user[1]));
				Map map = sendTest(userInfo,content);
				System.out.println("返回结果["+map.get("result")+":"+map.get("desc")+"]");
			}
			/*for(int i=0;i<result.size();++i){
				Map map = (Map) result.get(i);
				System.out.println("返回结果["+map.get("result")+":"+map.get("desc")+"]");
			}*/
			// 获取状态报告
/*			System.out.println("*************状态报告*************");
			getReport();

			// 获取余额
			System.out.println("*************获取余额*************");
			getBalance();

			// 获取上行
			System.out.println("*************获取上行*************");
			getSms();*/
	
		}

		// MD5加密函数
		public static String MD5Encode(String sourceString) {
			String resultString = null;
			try {
				resultString = new String(sourceString);
				MessageDigest md = MessageDigest.getInstance("MD5");
				resultString = byte2hexString(md.digest(resultString.getBytes()));
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			return resultString;
		}

		public static final String byte2hexString(byte[] bytes) {
			StringBuffer bf = new StringBuffer(bytes.length * 2);
			for (int i = 0; i < bytes.length; i++) {
				if ((bytes[i] & 0xff) < 0x10) {
					bf.append("0");
				}
				bf.append(Long.toString(bytes[i] & 0xff, 16));
			}
			return bf.toString();
		}

		// 发送短信  
		/**
		 * 发送短信方法使用document 对象方法封装XML字符串
		 */
		private static void send(String phone,String content) {
			//String url = "http://3tong.net/http/sms/Submit";
			Map<String, String> params = new LinkedHashMap<String, String>();
			HttpXmlUtil docXml = new HttpXmlUtil();
/*			String message = "<?xml version='1.0' encoding='UTF-8'?><message>"
					+ "<account>" + userName + "</account>" + "<password>"
					+ MD5Encode(password) + "</password>"
					+ "<msgid></msgid><phones>" + phone + "</phones><content>"
					+ content + "</content>"
					+ "<sign>"+sign+"</sign><subcode></subcode><sendtime></sendtime></message>";*/
			String message=docXml.DocXml(PropertiesUtil.prop.getProperty("notify_account"), 
					MD5Encode(PropertiesUtil.prop.getProperty("notify_password")), msgid, 
					phone, 
					content, sign, subcode, sendtime);
			System.out.println(message);
			params.put("message", message);

			String resp = doPost(url, params);
			System.out.println(resp);
		}
		private static Map sendTest(String phone,String content) {
			//String url = "http://3tong.net/http/sms/Submit";
			Map<String, String> params = new LinkedHashMap<String, String>();
			HttpXmlUtil docXml = new HttpXmlUtil();
/*			String message = "<?xml version='1.0' encoding='UTF-8'?><message>"
					+ "<account>" + userName + "</account>" + "<password>"
					+ MD5Encode(password) + "</password>"
					+ "<msgid></msgid><phones>" + phone + "</phones><content>"
					+ content + "</content>"
					+ "<sign>"+sign+"</sign><subcode></subcode><sendtime></sendtime></message>";*/
			
			String message=docXml.DocXml(PropertiesUtil.prop.getProperty("notify_account"), 
					MD5Encode(PropertiesUtil.prop.getProperty("notify_password")), msgid, phone, content, 
					/*PropertiesUtil.prop.getProperty("sign")*/sign, subcode, sendtime);
			System.out.println(message);
		//	params.put("message", message);
			params.put("message", message);
			String resp = doPost(url, params);
			return parseDocXml(resp);
		}
		

		// 状态报告
		private static void getReport() {
			//String url = "http://3tong.net/http/sms/Submit";
			Map<String, String> params = new LinkedHashMap<String, String>();
			String message = "<?xml version='1.0' encoding='UTF-8'?><message>"
					+ "<account>" + PropertiesUtil.prop.getProperty("notify_account") + "</account>" + "<password>"
					+ MD5Encode(PropertiesUtil.prop.getProperty("notify_password")) + "</password>"
					+ "<msgid></msgid><phone>18221177661</phone></message>";
			params.put("message", message);

			String resp = doPost(url, params);
			System.out.println(resp);
		}

		// 查询余额
		private static void getBalance() {
			//String url = "http://3tong.net/http/sms/Submit";
			Map<String, String> params = new LinkedHashMap<String, String>();
			String message = "<?xml version='1.0' encoding='UTF-8'?><message><account>"
					+ PropertiesUtil.prop.getProperty("notify_account")
					+ "</account>"
					+ "<password>"
					+ MD5Encode(PropertiesUtil.prop.getProperty("notify_password"))
					+ "</password></message>";
			params.put("message", message);

			String resp = doPost(url, params);
			System.out.println(resp);
		}

		// 获取上行
		private static void getSms() {
			
			Map<String, String> params = new LinkedHashMap<String, String>();
			String message = "<?xml version='1.0' encoding='UTF-8'?><message><account>"
					+ PropertiesUtil.prop.getProperty("notify_account")
					+ "</account>"
					+ "<password>"
					+ MD5Encode(PropertiesUtil.prop.getProperty("notify_password"))
					+ "</password></message>";
			params.put("message", message);

			String resp = doPost(url, params);
			System.out.println(resp);
		}
	
		/**
		 * 执行一个HTTP POST请求，返回请求响应的HTML
		 * 
		 * @param url
		 *            请求的URL地址
		 * @param params
		 *            请求的查询参数,可以为null
		 * @return 返回请求响应的HTML
		 */
		private static String doPost(String url, Map<String, String> params) {
			String response = null;
			HttpClient client = new HttpClient();
			//设置超时时间
			HttpConnectionManagerParams httpConnectionManagerParams = client.getHttpConnectionManager().getParams();
			httpConnectionManagerParams.setConnectionTimeout(30 * 1000);
			httpConnectionManagerParams.setSoTimeout(30 * 1000);
			
			PostMethod postMethod = new PostMethod(url);
			postMethod.getParams().setParameter(
					HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");

			// 设置Post数据
			if (!params.isEmpty()) {
				int i = 0;
				NameValuePair[] data = new NameValuePair[params.size()];
				for (Entry<String, String> entry : params.entrySet()) {
					data[i] = new NameValuePair(entry.getKey(), entry.getValue());
					i++;
				}

				postMethod.setRequestBody(data);

			}
			try {
				client.executeMethod(postMethod);
				if (postMethod.getStatusCode() == HttpStatus.SC_OK) {
					BufferedReader reader = new BufferedReader(new InputStreamReader(postMethod.getResponseBodyAsStream()));  
				    StringBuffer stringBuffer = new StringBuffer();  
				    String str = null;  
				    while((str = reader.readLine())!=null){  
				        stringBuffer.append(str);  
				    }  
				    response = stringBuffer.toString(); 
				    reader.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				postMethod.releaseConnection();
				client.getHttpConnectionManager().closeIdleConnections(0);
			}
			return response;
		}

		/**
		 * 使用document 对象封装XML
		 * @param userName
		 * @param pwd
		 * @param id
		 * @param phone
		 * @param contents
		 * @param sign
		 * @param subcode
		 * @param sendtime
		 * @return
		 */
		public  String DocXml(String userName,String pwd,String msgid,String  phone,String contents,String sign,String  subcode,String sendtime) {
			Document doc = DocumentHelper.createDocument();
			doc.setXMLEncoding("UTF-8");
			Element message = doc.addElement("response");
			Element account = message.addElement("account");
			account.setText(userName);
			Element password = message.addElement("password");
			password.setText(pwd);
			Element msgid1 = message.addElement("msgid");
			msgid1.setText(msgid);
			Element phones = message.addElement("phones");
			phones.setText(phone);
			Element content = message.addElement("content");
			content.setText(contents);
			Element sign1 = message.addElement("sign");
			sign1.setText(sign);
			Element subcode1 = message.addElement("subcode");
			subcode1.setText(subcode);
			Element sendtime1 = message.addElement("sendtime");
			sendtime1.setText(sendtime);
			return message.asXML();
			//System.out.println(message.asXML());
	        
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
			/*for(int i=0;i<list.size();++i){
				System.out.println(list.get(i));
			}*/
			return list;
		}
		/**
		 * 解析发送短信的结果
		 * @param xmlResult
		 * @return
		 */
		public static Map parseDocXml(String xmlResult){
			//<?xml version='1.0' encoding='UTF-8'?><response><msgid>168380</msgid><result>0</result><desc>提交成功</desc><blacklist></blacklist></response>
			Map<String,String> map = new ConcurrentHashMap<String,String>();
			try{
				Document doc = DocumentHelper.parseText(xmlResult); // 将字符串转为XML
	            Element parent = doc.getRootElement(); // 获取根节点
	            map.put("result", parent.elementTextTrim("result")) ;
	            map.put("desc", parent.elementTextTrim("desc")) ;
	            map.put("msgid", parent.elementTextTrim("msgid")) ;
	            map.put("blacklist", parent.elementTextTrim("blacklist")) ;
			}catch(Exception e){
				e.printStackTrace();
				return null;
			}
			return map;
		}
}
