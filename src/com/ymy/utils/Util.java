package com.ymy.utils;

import java.awt.event.KeyAdapter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Map.Entry;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.persistence.SearchFilter.Operator;
import org.springside.modules.security.utils.Digests;
import org.springside.modules.utils.Encodes;

import com.ymy.utils.MD5;
import com.sun.xml.internal.bind.v2.runtime.output.Encoded;

import net.sf.json.JSONArray;
import net.sf.json.JSONNull;
import net.sf.json.JSONObject;

/**
 * 工具�?
 * 
 * @author lyf
 *
 */
public class Util {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		//System.out.println(
				//URLEncoder.encode("rtmp://60.12.240.106:1935/live|/hik9610/192.168.1.246/8088/9/0/MAIN/TCP", "utf-8"));
	}

	/**
	 * 
	 * GET请求
	 * 
	 * @author lyf
	 * @date 2015�?6�?2�? 下午2:04:24
	 * @param url
	 * @param param
	 * @return
	 */
	public static String sendGet(String url, Map<String, String> headers) {
		String result = "";
		// HttpGet httpRequst = new HttpGet(URI uri);
		// HttpGet httpRequst = new HttpGet(String uri);
		// 创建HttpGet或HttpPost对象，将要请求的URL通过构�?�方法传入HttpGet或HttpPost对象�?
		HttpGet httpRequst = new HttpGet(url);
		if (headers != null) {
			for (String key : headers.keySet()) {
				httpRequst.setHeader(key, headers.get(key));
			}
		}

		// new DefaultHttpClient().execute(HttpUriRequst requst);
		try {
			// 使用DefaultHttpClient类的execute方法发�?�HTTP GET请求，并返回HttpResponse对象�?
			HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequst);// 其中HttpGet是HttpUriRequst的子�?
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				HttpEntity httpEntity = httpResponse.getEntity();
				result = EntityUtils.toString(httpEntity);// 取出应答字符�?
				// �?般来说都要删除多余的字符
				result.replaceAll("\r", "");// 去掉返回结果中的"\r"字符，否则会在结果字符串后面显示�?个小方格
			} else {
				httpRequst.abort();
				System.out.println("error:" + httpResponse.getStatusLine().getStatusCode());
			}

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = e.getMessage().toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = e.getMessage().toString();
		}
		System.out.println("msg:" + result);
		return result;
	}

	/**
	 * 
	 * post 请求
	 * 
	 * @author lyf
	 * @date 2015�?6�?2�? 下午2:05:18
	 * @param url
	 * @param param
	 * @return
	 */
	public static String sendPost(String url, Map<String, Object> param) {
		String result = "";
		HttpPost httpRequst = new HttpPost(url);// 创建HttpPost对象
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		if (param != null) {
			for (String key : param.keySet()) {
				params.add(new BasicNameValuePair(key, param.get(key).toString()));
			}
		}
		try {
			httpRequst.setEntity(new UrlEncodedFormEntity(params));
			HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequst);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				HttpEntity httpEntity = httpResponse.getEntity();
				result = EntityUtils.toString(httpEntity, "UTF-8");// 取出应答字符�?
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = e.getMessage().toString();
		}
		return result;
	}

	/**
	 * https post
	 * 
	 * @param url
	 * @param map
	 * @param charset
	 * @return
	 */
	public static String doPost(String url, Map<String, Object> map, String charset) {
		HttpClient httpClient = null;
		HttpPost httpPost = null;
		String result = null;
		try {
			httpClient = new SSLClient();
			httpPost = new HttpPost(url);
			// 设置参数
			List<NameValuePair> list = new ArrayList<NameValuePair>();
			Iterator iterator = map.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<String, String> elem = (Entry<String, String>) iterator.next();
				list.add(new BasicNameValuePair(elem.getKey(), elem.getValue()));
			}
			if (list.size() > 0) {
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, charset);
				httpPost.setEntity(entity);
			}
			HttpResponse response = httpClient.execute(httpPost);
			if (response != null) {
				HttpEntity resEntity = response.getEntity();
				if (resEntity != null) {
					result = EntityUtils.toString(resEntity, charset);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}

	/**
	 * 
	 * map转JSON字符�?
	 * 
	 * @author lyf
	 * @date 2015�?7�?1�? 上午10:22:14
	 * @param map
	 * @return
	 */
	public static String transMapToString(Map<?, ?> map) {
		return JSONObject.fromObject(map).toString();
	}

	/**
	 * 
	 * list转JSON字符�?
	 * 
	 * @author lyf
	 * @date 2015�?7�?1�? 上午10:22:14
	 * @param map
	 * @return
	 */
	public static String transListToString(List<Object> list) {
		return JSONArray.fromObject(list).toString();
	}

	/**
	 * 
	 * jsonarry 字符串转LIST
	 * 
	 * @author lyf
	 * @date 2015�?7�?1�? 上午10:26:17
	 * @param jsonStr
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<Object> parseJSON2List(String jsonStr) {
		List<Object> list = new ArrayList<Object>();
		Iterator<JSONObject> it = JSONArray.fromObject(jsonStr).iterator();
		while (it.hasNext()) {
			Object json2 = it.next();
			if (json2 instanceof JSONObject) {
				list.add(parseJSON2Map(json2.toString()));
			} else if (json2 instanceof JSONArray) {
				list.add(parseJSON2List(json2.toString()));
			} else {
				list.add(json2);
			}
		}
		return list;
	}

	/**
	 * 
	 * json字符串转MAP
	 * 
	 * @author lyf
	 * @date 2015�?7�?1�? 上午10:26:41
	 * @param jsonStr
	 * @return
	 */
	public static Map<String, Object> parseJSON2Map(String jsonStr) {
		Map<String, Object> map = new HashMap<String, Object>();
		// �?外层解析
		JSONObject json = JSONObject.fromObject(jsonStr);
		for (Object k : json.keySet()) {
			Object v = json.get(k);
			// 如果内层还是数组的话，继续解�?
			if (v instanceof JSONArray) {
				map.put(k.toString(), parseJSON2List(v.toString()));
			} else if (v instanceof JSONObject) {
				map.put(k.toString(), parseJSON2Map(v.toString()));
			} else if (v instanceof JSONNull) {
				map.put(k.toString(), null);
			} else {
				map.put(k.toString(), v);
			}
		}
		return map;
	}

	/**
	 * 生成签名
	 * 
	 * @param map
	 * @param token
	 * @return
	 */
	public static String getsign(Map<String, Object> map, String token) {
		Collection<String> keyset = map.keySet();
		List<String> list = new ArrayList<String>(keyset);
		// 对key键�?�按字典升序排序
		Collections.sort(list);
		String result = "";
		for (String s : list) {
			if (map.get(s) != null)
				result += s + "=" + map.get(s) + "&";
		}
		result += "key=" + token;
		result = MD5.GetMD5Code(result).toUpperCase();
		return result;
	}

	/**
	 * 
	 * 获取项目本地路径
	 * 
	 * @author lyf
	 * @date 2015�?4�?9�? 下午4:21:05
	 * @return
	 */
	public static String getRootPath() {
		String baseUrl = "";
		try {
			baseUrl = Util.class.getResource("").toURI().getPath();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return baseUrl.substring(0, baseUrl.indexOf("WEB-INF"));
	}

	/**
	 * 获取配置文件中属性数�?
	 * 
	 * @param name
	 * @return
	 */
	public static String getValueFromProperties(String name) {
		String value = "";
		try {
			Properties p = new Properties();
			InputStream in = Util.class.getClassLoader().getResourceAsStream("application.properties");
			p.load(in);
			value = p.getProperty(name);
		} catch (IOException e) {
			e.printStackTrace();
			value = null;
		}

		return value;
	}

	/**
	 * 获取指定长度的随机字符串
	 * 
	 * @param length
	 * @return
	 */
	public static String getRandomString(int length) {
		String randString = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			sb.append(randString.charAt((int) (Math.random() * 35 + 1)));
		}
		return sb.toString();
	}

	/**
	 * 返回6位随机数
	 *
	 * @author tmac
	 */
	public static String getCaptcha() {
		// TODO Auto-generated method stub
		Random random = new Random();
		String result = "";
		for (int i = 0; i < 6; i++) {
			result += random.nextInt(10);
		}
		return result;
	}

	/**
	 * 获取指定长度的随机数字序�?
	 * 
	 * @param length
	 * @return
	 */
	public static String getRandomNumber(int length) {
		String randString = "0123456789";
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			sb.append(randString.charAt((int) (Math.random() * 9 + 1)));
		}
		return sb.toString();
	}

	/**
	 * luyf 创建动�?�查询条件组�?.
	 * 
	 * @param searchParams
	 *            搜索条件 EQ, LIKE, GT, LT, GTE, LTE (XX_xxxxx) eq相等 ne、neq不相等，
	 *            gt大于�? lt小于 gte、ge大于等于 lte、le 小于等于 not�? mod求模
	 * @param lsf
	 * @param entityClazz
	 * @return
	 * 
	 */
	public static <T> Specification<T> buildSpecification(Map<String, Object> searchParams, Class<T> entityClazz) {
		Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
		// SearchFilter.parse(searchParams);//直接转换不是string会报�?
		SearchFilter searchFilter;
		for (Map.Entry<String, Object> entry : searchParams.entrySet()) {
			String key = entry.getKey();// EQ_state
			if ("EQ".equals(key.substring(0, 2))) {
				searchFilter = new SearchFilter(entry.getKey().substring(3), Operator.EQ, entry.getValue());
				filters.put(key, searchFilter);
			} else if ("LIKE".equals(key.substring(0, 4))) {
				searchFilter = new SearchFilter(entry.getKey().substring(5), Operator.LIKE, entry.getValue());
				filters.put(key, searchFilter);
			} else if ("GTE".equals(key.substring(0, 3))) {
				searchFilter = new SearchFilter(entry.getKey().substring(4), Operator.GTE, entry.getValue());
				filters.put(key, searchFilter);
			} else if ("LTE".equals(key.substring(0, 3))) {
				searchFilter = new SearchFilter(entry.getKey().substring(4), Operator.LTE, entry.getValue());
				filters.put(key, searchFilter);
			} else if ("GT".equals(key.substring(0, 2))) {
				searchFilter = new SearchFilter(entry.getKey().substring(3), Operator.GT, entry.getValue());
				filters.put(key, searchFilter);
			} else if ("LT".equals(key.substring(0, 2))) {
				searchFilter = new SearchFilter(entry.getKey().substring(3), Operator.LT, entry.getValue());
				filters.put(key, searchFilter);
			}
		}

		Specification<T> spec = DynamicSpecifications.bySearchFilter(filters.values(), entityClazz);
		return spec;
	}

	/**
	 * 比较版本号的大小,前�?�大则返回一个正�?,后�?�大返回�?个负�?,相等则返�?0
	 * 
	 * @param version1
	 * @param version2
	 * @return
	 */
	public static int compareVersion(String version1, String version2) {
		if (version1 == null || version2 == null) {
			return 0;
		}
		String[] versionArray1 = version1.split("\\.");// 注意此处为正则匹配，不能�?"."�?
		String[] versionArray2 = version2.split("\\.");
		int idx = 0;
		int minLength = Math.min(versionArray1.length, versionArray2.length);// 取最小长度�??
		int diff = 0;
		while (idx < minLength && (diff = versionArray1[idx].length() - versionArray2[idx].length()) == 0// 先比较长�?
				&& (diff = versionArray1[idx].compareTo(versionArray2[idx])) == 0) {// 再比较字�?
			++idx;
		}
		// 如果已经分出大小，则直接返回，如果未分出大小，则再比较位数，有子版本的为大；
		diff = (diff != 0) ? diff : versionArray1.length - versionArray2.length;
		return diff;
	}

	/**
	 * 
	 * @param strUrlParam
	 * @return
	 */
	public static Map<String, String> URLRequest(String strUrlParam) {
		Map<String, String> mapRequest = new HashMap<String, String>();

		String[] arrSplit = null;

		if (strUrlParam == null) {
			return mapRequest;
		}
		// 每个键�?�为�?�? www.2cto.com
		arrSplit = strUrlParam.split("[&]");
		for (String strSplit : arrSplit) {
			String[] arrSplitEqual = null;
			arrSplitEqual = strSplit.split("[=]");

			// 解析出键�?
			if (arrSplitEqual.length > 1) {
				// 正确解析
				mapRequest.put(arrSplitEqual[0], arrSplitEqual[1]);

			} else {
				if (arrSplitEqual[0] != "") {
					// 只有参数没有值，不加�?
					mapRequest.put(arrSplitEqual[0], "");
				}
			}
		}
		return mapRequest;
	}
}
