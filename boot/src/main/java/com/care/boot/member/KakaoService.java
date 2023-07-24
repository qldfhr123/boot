package com.care.boot.member;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class KakaoService {
	
	private String accessToken;
	private String scope;
	
	public void getAccessToken(String code) {
		String redirectUri = "http://localhost/kakaoLogin";
		String reqUrl = "https://kauth.kakao.com/oauth/token";
		String reqParam = "grant_type=authorization_code";
		reqParam += "&client_id=231dcfa0f6c9f5d6db11c3d33c9b755c";
		reqParam += "&redirect_uri="+redirectUri;
		reqParam += "&code="+code;
		
		HttpURLConnection conn;
		try {
			URL url = new URL(reqUrl); //POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
			conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("POST"); //POST 요청을위해 기본값 false에서 setDoOut으로 true변경
			conn.setDoOutput(true);//post 매소드를 이용해서 데이터를 전달하기위한설정
			
			//기본 OutputStram을 통해 문자열로 처리할수있는 OutputStreamWriter변환후 처리속도를 빠르게 처리하기위해
			//BufferedWriter변환한다
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
			bw.write(reqParam);
			bw.flush();
			
			int responseCode = conn.getResponseCode(); //결과 코드가 200이면 성공
			System.out.println("responseCode:"+responseCode);
			
			// 요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기 (반복문)
//			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//			String line ="", result="";
//			while((line = br.readLine()) != null){
//				result += line;
//			}
			
			// 요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
			InputStreamReader isr = new InputStreamReader(conn.getInputStream());
			ObjectMapper om = new ObjectMapper();
			Map<String, String> map = null;
			map = om.readValue(isr, new TypeReference<Map<String, String>>() {} );
			accessToken = map.get("access_token");
			System.out.println("access_token : "+map.get("access_token"));
			System.out.println("scope : "+map.get("scope"));
			
			
		} catch (IOException e) {
		
			e.printStackTrace();
		}
		
	}
	
	public void setNeedsAgreement() {
		
			String redirectUri = "http://localhost/kakaoLogin";
			String reqUrl = "https://kauth.kakao.com/oauth/authorize";
			String reqParam = "?client_id=af3185dbf0396a5bf08b67e29a79b42";
			reqParam += "&redirect_uri="+redirectUri;
			reqParam += "&response_type=code&scope="+scope;
			
			HttpURLConnection conn;
			try {
				URL url = new URL(reqUrl); // POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
				conn = (HttpURLConnection) url.openConnection();
				conn.setDoOutput(true);
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
				bw.write(reqParam);
				bw.flush();

				int responseCode = conn.getResponseCode(); // 결과 코드가 200이라면 성공
				System.out.println("responseCode : " + responseCode);
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	public void getUserInfo() {
		String reqUrl = "https://kapi.kakao.com/v2/user/me";
		HttpURLConnection conn;
		
		try {
			URL url = new URL(reqUrl);
			conn = (HttpURLConnection)url.openConnection();
			
			conn.setRequestMethod("POST"); 
			conn.setRequestProperty("Authorization", "Bearer " + accessToken); //Authorization: Bearer ${ACCESS_TOKEN}
			
			ObjectMapper om = new ObjectMapper();
			JsonNode jsonTree = om.readTree(conn.getInputStream());
			
			System.out.println("jsonTree : "+jsonTree);
			System.out.println("kakao_account : " + jsonTree.get("kakao_account"));
			
			JsonNode kakaoAccount = jsonTree.get("kakao_account");
			System.out.println("profile : " + kakaoAccount.get("profile"));
			System.out.println("email : " + kakaoAccount.get("email"));
			System.out.println("age_range : " + kakaoAccount.get("age_range"));
			System.out.println("gender : " + kakaoAccount.get("gender"));
			System.out.println("nickname : " + kakaoAccount.get("profile").get("nickname"));
			
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void unLink() {
		/** 로그아웃 되어있는 상태면 오류 */
		String reqUrl = "https://kapi.kakao.com/v1/user/unlink";
		HttpURLConnection conn;
		try {
			URL url = new URL(reqUrl);
			conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("POST"); 
			conn.setRequestProperty("Authorization", "Bearer " + accessToken); 
			
			int responseCode = conn.getResponseCode(); //결과 코드가 200이면 성공
			System.out.println("responseCode:"+responseCode);
			
			ObjectMapper om = new ObjectMapper();
			JsonNode jsonTree = om.readTree(conn.getInputStream());
			System.out.println("id : "+jsonTree.get("id"));
			
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
}
