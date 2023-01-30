package com.kyobo.platform.donots.config;

import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@Slf4j
public class HttpConfig {
    public JSONObject callApi(JSONObject jsonObject, String target_url, String type){
        HttpURLConnection conn = null;
        JSONObject responseJson = null;
        
        try {
            //URL 설정
            URL url = new URL(target_url);
 
            conn = (HttpURLConnection) url.openConnection();
            
            // type의 경우 POST, GET, PUT, DELETE 가능
            conn.setRequestMethod(type);
            if(type.equals("GET")) {
            	conn.setDoOutput(false); //OutputStream을 사용해서 post body 데이터 전송
            } else {
            	conn.setDoOutput(true); //OutputStream을 사용해서 post body 데이터 전송
                conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
                conn.setRequestProperty("Accept", "application/json");
                
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"));
                     
                bw.write(jsonObject.toString());
                bw.flush();
                bw.close();
            }
            
            // 보내고 결과값 받기
            int responseCode = conn.getResponseCode();
            if(responseCode == 200) {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                StringBuilder sb = new StringBuilder();
                String line = "";
                while((line = br.readLine()) != null) {
                    sb.append(line);
                }
                JSONParser parser = new JSONParser();
                Object obj = parser.parse(sb.toString());
                responseJson = (JSONObject) obj;
                
                // 응답 데이터
                log.info("responseJson : " + responseJson);
            } 
        } catch(MalformedURLException e) {
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        } catch(ParseException e) {
			e.printStackTrace();
		}
		return responseJson;
    }
}
