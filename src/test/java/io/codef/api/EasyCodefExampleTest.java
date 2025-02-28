package io.codef.api;

import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.codef.api.EasyCodef;
import io.codef.api.EasyCodefServiceType;

/**
 * <pre>
 * io.codef.easycodef
 *   |_ EasyCodefExampleTest.java
 * </pre>
 *
 * Desc : EasyCodef 사용예시
 * @Company : ©CODEF corp.
 * @Author  : notfound404@codef.io
 * @Date    : Jun 26, 2020 3:42:23 PM
 * @Version : 1.0.1
 */
public class EasyCodefExampleTest {

	@SuppressWarnings("unchecked")
	@Test
	public void usageExample() throws UnsupportedEncodingException, JsonProcessingException, InterruptedException {
		/**
		 * #1.쉬운 코드에프 객체 생성
		 */
		EasyCodef codef = new EasyCodef();

		/**
		 * #2.데모 클라이언트 정보 설정
		 * - 데모 서비스 가입 후 코드에프 홈페이지에 확인 가능(https://codef.io/#/account/keys)
		 * - 데모 서비스로 상품 조회 요청시 필수 입력 항목
		 */
		codef.setClientInfoForDemo(EasyCodefClientInfo.DEMO_CLIENT_ID, EasyCodefClientInfo.DEMO_CLIENT_SECRET);

		/**
		 * #3.정식 클라이언트 정보 설정
		 * - 정식 서비스 가입 후 코드에프 홈페이지에 확인 가능(https://codef.io/#/account/keys)
		 * - 정식 서비스로 상품 조회 요청시 필수 입력 항목
		 */
		codef.setClientInfo(EasyCodefClientInfo.CLIENT_ID, EasyCodefClientInfo.CLIENT_SECRET);

		/**
		 * #4.RSA암호화를 위한 퍼블릭키 설정
		 * - 데모/정식 서비스 가입 후 코드에프 홈페이지에 확인 가능(https://codef.io/#/account/keys)
		 * - 암호화가 필요한 필드에 사용 - encryptValue(String plainText);
		 */
		codef.setPublicKey(EasyCodefClientInfo.PUBLIC_KEY);

		/**
		 * #5.요청 파라미터 설정
		 * - 각 상품별 파라미터를 설정(https://developer.codef.io/products)
		 */
		/*
		HashMap<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("connectedId", "sandbox_connectedId_01");
		parameterMap.put("organization", "0309");
		parameterMap.put("birthDate", "");
		parameterMap.put("inquiryType", "0");
*/
		HashMap<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("connectedId", "sandbox_connectedId_01");
		parameterMap.put("organization", "0004");
		parameterMap.put("courtName", "청주지방법원");
		parameterMap.put("caseNumberYear", "2023");
		parameterMap.put("caseNumberNumber", "3493");



		/**
		 * #6.코드에프 정보 조회 요청
		 * - 서비스타입(API:정식, DEMO:데모, SANDBOX:샌드박스)
		 */
		String productUrl = "/v1/kr/public/ck/auction-events/search";	// (예시)개인 보유카드 조회 URL
		String result = codef.requestProduct(productUrl, EasyCodefServiceType.SANDBOX, parameterMap);

		/**	#7.코드에프 정보 결과 확인	*/
		System.out.println(result);

		HashMap<String, Object> responseMap = new ObjectMapper().readValue(result, HashMap.class);
		HashMap<String, Object> resultMap = (HashMap<String, Object>)responseMap.get("result");

		assertEquals("코드에프 상품 요청 결과 실패(반환된 코드와 메시지 확인 필요)", "CF-00000", (String)resultMap.get("code"));
	}
}
