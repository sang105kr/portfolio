package com.kh.portfolio.openapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.springframework.stereotype.Service;

import com.kh.portfolio.common.JsonUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CovidSVC {

	static final String serviceKey = "0OhBU7ZCGIobDVKDeBJDpmDRqK3IRNF6jlf%2FJB2diFAf%2FfR2czYO9A4UTGcsOwppV6W2HVUeho%2FFPwXoL6DwqA%3D%3D";
	
	public String covidInfoJson(String startDt,String endDt) {
		return JsonUtil.xmlToJson(covidInfo(startDt,endDt));
	}
	
	public String covidInfoXml(String startDt,String endDt) {
		return covidInfo(startDt,endDt);		
	}
	
	private String covidInfo(String startDt,String endDt) {
		
		StringBuilder sb = new StringBuilder();
		
		try {
			
			StringBuilder urlBuilder = new StringBuilder(
					"http://openapi.data.go.kr/openapi/service/rest/Covid19/getCovid19InfStateJson"); /* URL */
			urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8") + "=" + serviceKey); /* Service Key */
			urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /* 페이지번호 */
			urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /* 한 페이지 결과 수 */
			urlBuilder.append("&" + URLEncoder.encode("startCreateDt", "UTF-8") + "="
					+ URLEncoder.encode(startDt, "UTF-8")); /* 검색할 생성일 범위의 시작 */
			urlBuilder.append("&" + URLEncoder.encode("endCreateDt", "UTF-8") + "="
					+ URLEncoder.encode(endDt, "UTF-8")); /* 검색할 생성일 범위의 종료 */
			URL url = new URL(urlBuilder.toString());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-type", "application/json");
			System.out.println("Response code: " + conn.getResponseCode());
			BufferedReader rd;
			if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
				rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			} else {
				rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
			}

			String line;
			while ((line = rd.readLine()) != null) {
				sb.append(line);
			}
			rd.close();
			conn.disconnect();
			
			log.info(sb.toString());

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return sb.toString();
	}
}
