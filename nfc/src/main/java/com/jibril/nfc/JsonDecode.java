package com.jibril.nfc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.HashMap;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;



public class JsonDecode {
	static String serverip = "192.168.0.12";
	static String phone = "601123064474";
	static String tagid = "12345";
	static String json;
	static URL url;

	public static void main(String[] args) {
		String link = "http://"+serverip+":8080/json.jsp?mobileno="+phone+"&tagid="+tagid;
		InputStream in = getInputStreamFromUrl(link); //
		
		String rt = GET(link).substring(4);
        //System.out.println(rt);
		Dao dao = new Dao();
        User us = new User();
        User user = dao.userTest(1);
        Gson gson = new Gson();
        
	    User user2 = gson.fromJson(rt, User.class); 
	    System.out.println(user2.getMobileno());
        
        //System.out.println(gson2.toJson(user));
        ///String js = "{'id':1,'username':'auwal','password':'auwal','roles':['ADMIN','MANAGER']}";
        ///System.out.println(gson.fromJson(rt,  User.class));
        
        
        
        /**
        try {
        URL url2 = new URL(url);
        Reader reader =  new InputStreamReader(url2.openStream()); //new InputStreamReader(JsonDecode.class.getResourceAsStream("/Server1.json"));
        } catch(Exception e2) { System.out.println(e2); }
        */
        

        /**
        try {
        	URL url2 = new URL(url);
        	//BufferedReader reader = new BufferedReader(new InputStreamReader(url2.openStream()));
        	
        	Gson gson = new Gson();
            //Gson gson = new GsonBuilder().create();
        	String js = reader.toString();
        	
        	final JsonObject jsonObject = js.getAsJsonObject();
            JsonElement titleElement = jsonObject.get("title");
            
            User u = gson.fromJson(reader, User.class);
            System.out.println(u);
     
        } catch(Exception e2) { System.out.println(e2); }
        */
        /**
        User user = new User();
        User us = user.userTest();
        //System.out.println(us.getUsername());
        
        
        //String json = gson.toJson(us);
        //System.out.println(json);
        
        try {  json = readUrl(url); } catch(Exception e3) { System.out.println(e3); }
        System.out.println(json);
        
        User obj2 = gson.fromJson(json, User.class);
        System.out.println(obj2.getUsername());
        */        
        
        
        
        
        
        
        
        
        
        //
        
        //User user = new User();
        //;
        //
        
        //
        //System.out.println(obj2.getUsername());
        
       
        
        
        //String json = gson.toJson(user);  
        //==> json is {"value1":1,"value2":"abc"}


        //User obj2 = gson.fromJson(json, User.class); 

        /**
        try {  json = readUrl(url);
        Gson gson = new Gson();       
 
        User user = gson.fromJson(json, User.class);
 
        //System.out.println(user.username);
       //System.out.println(user.getUsername());
        
        } catch(Exception e2) { System.out.println(e2); }
        
        
        JsonElement root = new JsonParser().parse(json);
        String value1 = root.getAsJsonObject().get("data").getAsJsonObject().get("username").getAsString();
        System.out.println(value1);
        */

	}
	
	private static String readUrl(String urlString) throws Exception {
        BufferedReader reader = null;
        try { 
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read); 
 
            return buffer.toString();
        } finally { 
            if (reader != null)
                reader.close();
        } 
 
    } 
	
	private static String makeGetRequest(String url) {
    	String rtn = "";
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(url);
		HttpResponse response;
		try {
			response = client.execute(request);
			rtn = response.toString();
		} catch (ClientProtocolException e) {
			rtn = e.toString();
			e.printStackTrace();
		} catch (IOException e) {
			rtn = e.toString();
			e.printStackTrace();
		}
		
		return rtn;

	}
    public static String GET(String url){
        InputStream inputStream = null;
        String result = "";
        try {
 
            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();
 
            // make GET request to the given URL
            HttpResponse httpResponse = httpclient.execute(new HttpGet(url));
 
            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();
 
            // convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";
 
        } catch (Exception e) {        }
 
        return result;
    }
 
    private static String convertInputStreamToString(InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;
 
        inputStream.close();
        return result;
 
    }
    
    public static InputStream getInputStreamFromUrl(String url) {
  	  InputStream content = null;
  	  try {
  	    HttpClient httpclient = new DefaultHttpClient();
  	    HttpResponse response = httpclient.execute(new HttpGet(url));
  	    content = response.getEntity().getContent();
  	  } catch (Exception e) {
  	  }
  	    return content;
  	}

}
