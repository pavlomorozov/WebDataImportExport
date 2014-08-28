package web.data.pull.jango;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HTMLReader{
	private List<String> cookies;
	private HttpURLConnection httpConnection;
  	private final String USER_AGENT = "Mozilla/5.0";
  	private final String USERNAME = "user";
  	private final String PASSWORD = "sdRvb7ds"; 
  	
  	public HTMLReader() throws Exception{
  		getHtml();
  	}
  	
	private void getHtml() throws Exception{
		System.out.println("htmlReader starts");
		String url = "http://10.0.229.249:8080/InfoMonitor";
		URL loginURL = new URL(url);
		httpConnection = (HttpURLConnection) loginURL.openConnection();
		httpConnection.setRequestMethod("GET");
		httpConnection.setUseCaches(false);
		httpConnection.setRequestProperty("User-Agent", USER_AGENT);
		httpConnection.setRequestProperty("Accept",
			"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		httpConnection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		int responseCode = httpConnection.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);
		
		BufferedReader in = new BufferedReader(
				new InputStreamReader(httpConnection.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
	 
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
	 	
		String pageHTML =response.toString(); 
		
		//login page data
		System.out.println(pageHTML);
		
		//login action (post form data)
		Document htmlDoc = Jsoup.parse(pageHTML);
		Element loginform = htmlDoc.getElementById("loginForm");
		Elements inputElements = loginform.getElementsByTag("input");
		
		List<String> paramList = new ArrayList<String>();
		for (Element inputElement : inputElements) {
			String key = inputElement.attr("name");
			String value = inputElement.attr("value");
			
			System.out.println("k: " + key + " v: " + value );
	 
			if (key.equals("j_username"))
				value = USERNAME;
			else if (key.equals("j_password"))
				value = PASSWORD;
			else 
				continue;
			paramList.add(key + "=" + URLEncoder.encode(value, "UTF-8"));
		}
		// build parameters list
		StringBuilder result = new StringBuilder();
		for (String param : paramList) {
			if (result.length() == 0) {
				result.append(param);
			} else {
				result.append("&" + param);
			}
		}
		String loginParamString = result.toString();
		System.out.println(loginParamString);
		//Send post
		url = "http://10.0.229.249:8080/InfoMonitor/j_spring_security_check";
		URL loginPostURL = new URL(url);
		httpConnection = (HttpURLConnection) loginPostURL.openConnection();
		httpConnection.setUseCaches(false);
		httpConnection.setRequestMethod("POST");
		httpConnection.setRequestProperty("Host", "accounts.google.com");
		httpConnection.setRequestProperty("User-Agent", USER_AGENT);
		httpConnection.setRequestProperty("Accept",
			"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		httpConnection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
//		for (String cookie : this.cookies) {
//			httpConnection.addRequestProperty("Cookie", cookie.split(";", 1)[0]);
//		}
		httpConnection.setRequestProperty("Connection", "keep-alive");
		httpConnection.setRequestProperty("Referer", "https://accounts.google.com/ServiceLoginAuth");
		httpConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		httpConnection.setRequestProperty("Content-Length", Integer.toString(loginParamString.length()));
		httpConnection.setDoOutput(true);
		httpConnection.setDoInput(true);
		
		// Send post request
		DataOutputStream wr = new DataOutputStream(httpConnection.getOutputStream());
		wr.writeBytes(loginParamString);
		wr.flush();
		wr.close();
	 
		responseCode = httpConnection.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Post parameters : " + loginParamString);
		System.out.println("Response Code : " + responseCode);
	 
		in = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
		response = new StringBuffer();
	 
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		
		System.out.println(response.toString());		
	}
}
