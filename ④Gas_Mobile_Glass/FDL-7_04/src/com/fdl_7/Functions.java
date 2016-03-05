/**
 * Fucntion class는 수신, 송신 데이터 처리와 형변환, 데이터 체크 및 추출에 관련된 함수들을 static 으로 관리한다.
 * 
 * 
 */

package com.fdl_7;

import android.util.Log;

public class Functions {

	private static final String TAG="FUNCTIONS";
	private static final int MSG_LENGTH=55;
	private static final int INVALID=0;
	private static final int VALID=1;
	
	private static int indexSOH=-1;
	private static int indexEOH=-1;
	
	private static int flagSOH=INVALID;
	private static int flagEOH=INVALID;
	
	private static int count=0;
	
	final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();
	
	/**
	 * 
	 * @param bytes
	 * @return String
	 * 
	 * byte 배열에 담긴 데이터가 hex데이터로 무엇인지  String으로 나타내는 함수이다. 
	 */
	public static String bytesToHex(byte[] bytes) {
		
	    char[] hexChars = new char[bytes.length * 2];
	    for ( int j = 0; j < bytes.length; j++ ) {
	        int v = bytes[j] & 0xFF;
	        hexChars[j * 2] = hexArray[v >>> 4];
	        hexChars[j * 2 + 1] = hexArray[v & 0x0F];
	    }
	    return new String(hexChars);
	}
	
	/**
	 * 
	 * @param String hex
	 * @return String
	 * 
	 * hex값이 무엇인지 나타내는 String 으로 그 hex number에 해당하는 실제 character String은 무엇인지 나타내는 함수이다.
	 * 예 : 0x30 : 0			0x33 : 3
	 */
	public static String convertHexToString(String hex) {

		StringBuilder sb = new StringBuilder();
		StringBuilder temp = new StringBuilder();

		// 49204c6f7665204a617661 split into two characters 49, 20, 4c...
		for (int i = 0; i < hex.length() - 1; i += 2) {

			// grab the hex in pairs
			String output = hex.substring(i, (i + 2));
			// convert hex to decimal
			int decimal = Integer.parseInt(output, 16);
			// convert the decimal to character
			sb.append((char) decimal);

			temp.append(decimal);
		}
		Log.i(TAG, "result : "+sb.toString());

		return sb.toString();
	}

	/**
	 * 
	 * @param strArr
	 * @return
	 */
	public static String extractPPMValue(String[] strArr) {

		Log.i(TAG, "extractPPMValue");
		int countDigits=0;
		double sum = 0.0;
		
		String[] strValueArr=new String[strArr.length];

		for (int i = 0; i < strArr.length; i++) {
			
			strValueArr[strValueArr.length-1-i]=convertHexToString(strArr[strArr.length-1-i]);
			
			if(strValueArr[strValueArr.length-1-i] != " "){
				countDigits++;
			}else{
				break;
			}
		}

		for (int i = 0; i < countDigits; i++) {

			String tmp=strValueArr[strValueArr.length-1-i];
			if(tmp.equals(" ")){
				break;
			}
			
			double value = Double.parseDouble(tmp)
					* Math.pow(10.0, (double) i);
			Log.i("Value", "Digit : "+i +"// value : "+value);
			sum += value;
		}

		Log.i(TAG, "result sum : "+String.valueOf(sum));

		return String.valueOf(sum);
	}

	/**
	 * 
	 * @param length int
	 * @param content byte[]
	 * @return Boolean
	 * 
	 * byte 배열로 들어온 메세지가 유효한 ppm데이터를 포함하는 형식을 갖추었는지 확인하는 함수이다. 
	 * 
	 * 유효하다면 true를, 아니면 false 를 리턴한다.
	 */
	public static Boolean checkValidArea(int length, byte[] content){
		
		Log.i(TAG, "Check Valid Area");

		indexSOH=-1;
		indexEOH=-1;
		
		flagSOH=INVALID;
		flagEOH=INVALID;
		
		count=0;
		
		
		byte[] tmpByte=new byte[1];
		for(int i=0; i<length; i++){
			tmpByte[0]=content[i];
			if(bytesToHex(tmpByte).equals("0D")){
				Log.i(TAG, "Valid Area - YES 0D-SOH");
				tmpByte[0]=content[i+1];
				
				if(bytesToHex(tmpByte).equals("0A")){
					Log.i(TAG, "Valid Area - YES 0A-SOH");
					indexSOH=i;
					flagSOH=VALID;
					break;
				}else{
					flagSOH=INVALID;
					indexSOH=-1;
				}
	
			}
		}
		
		if(flagSOH==VALID){
			
			for(int i=indexSOH+2; i<length; i++){
				tmpByte[0]=content[i];
				if(bytesToHex(tmpByte).equals("0D")){
					Log.i(TAG, "Valid Area - YES 0D _ EOH");
					tmpByte[0]=content[i+1];
					
					if(bytesToHex(tmpByte).equals("0A")){
						Log.i(TAG, "Valid Area - YES 0A-EOH");
						indexEOH=i;
						flagEOH=VALID;
						return true;
					}else{
						indexSOH=-1;
						indexEOH=-1;
						flagSOH=INVALID;
						flagEOH=INVALID;
					}
				}else{
					count++;
				}
			}
			
		}
		
		return false;

		
	}
	
	/**
	 * 
	 * @param byte[] content
	 * @return String[]
	 * 
	 * 앞서 Validity가 true로 판명 났다면, 그 때 계산된 SOH, EOH의 index값과 count값을 파탕으로 
	 * Valid Area의 hex 데이터들만 추출하여 String 배열로 변환하여 리턴한다. 
	 */
	public static String[] extractValidArea(byte[] content){

		Log.i(TAG, "Extract Valid Area");

		String[] resultString;
		resultString=new String[count];
		
		byte[] tmp = new byte[1];

		for (int i = 0; i < count; i++) {
			tmp[0] = content[indexSOH + 2 + i];
			resultString[i] = bytesToHex(tmp);
		}

		return resultString;
	}

	/**
	 * 
	 * @param String[] strArr
	 * @return Boolean
	 * 
	 * 앞서 변환한 String 배열 내의 ppm 값의 숫자중 무효한 값이 있는지 확인하는 함수이다.
	 * 
	 * 모두 유효하다면 true를 하나라도 무효하다면 false를 리턴한다.
 	 */
	public static Boolean checkValidNumber(String[] strArr){
		
		Log.i(TAG, "Check Valid Number");

		for(int i=0; i<strArr.length; i++){
			if(!checkPPMData(convertHexToString(strArr[i])))
				return false;
		}
		
		return true;
	}
	
	/**
	 * 
	 * @param String str
	 * @return Boolean
	 * 
	 * 입력된 String 값이 0~9 또는 " " 값인지 확인한다. 
	 * 
	 * 해당하지 않는다면 false를 맞다면 true를 리턴한다.
	 */
	public static Boolean checkPPMData(String str) {

		Log.i(TAG, "checkPPMData");

		if (str.equals(" ")) {
			return true;
		} else if (str.equals("0")) {
			return true;
		} else if (str.equals("1")) {
			return true;
		} else if (str.equals("2")) {
			return true;
		} else if (str.equals("3")) {
			return true;
		} else if (str.equals("4")) {
			return true;
		} else if (str.equals("5")) {
			return true;
		} else if (str.equals("6")) {
			return true;
		} else if (str.equals("7")) {
			return true;
		} else if (str.equals("8")) {
			return true;
		} else if (str.equals("9")) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * 
	 * @param byte[] bytes
	 * @return String
	 * 
	 * byte 배열을 UTFCustom 형식에 맞는 String으로 변환한다.
	 */
	public static String bytesToStringUTFCustom(byte[] bytes) {

		Log.i(TAG, "bytesToStringUTFCustom");

		char[] buffer = new char[bytes.length >> 1];

		for (int i = 0; i < buffer.length; i++) {

			int bpos = i << 1;

			char c = (char) (((bytes[bpos] & 0x00FF) << 8) + (bytes[bpos + 1] & 0x00FF));

			buffer[i] = c;

		}
		Log.i(TAG, "result : "+new String(buffer));
		return new String(buffer);

	}

}
