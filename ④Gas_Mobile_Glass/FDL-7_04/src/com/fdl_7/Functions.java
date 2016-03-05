/**
 * Fucntion class�� ����, �۽� ������ ó���� ����ȯ, ������ üũ �� ���⿡ ���õ� �Լ����� static ���� �����Ѵ�.
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
	 * byte �迭�� ��� �����Ͱ� hex�����ͷ� ��������  String���� ��Ÿ���� �Լ��̴�. 
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
	 * hex���� �������� ��Ÿ���� String ���� �� hex number�� �ش��ϴ� ���� character String�� �������� ��Ÿ���� �Լ��̴�.
	 * �� : 0x30 : 0			0x33 : 3
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
	 * byte �迭�� ���� �޼����� ��ȿ�� ppm�����͸� �����ϴ� ������ ���߾����� Ȯ���ϴ� �Լ��̴�. 
	 * 
	 * ��ȿ�ϴٸ� true��, �ƴϸ� false �� �����Ѵ�.
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
	 * �ռ� Validity�� true�� �Ǹ� ���ٸ�, �� �� ���� SOH, EOH�� index���� count���� �������� 
	 * Valid Area�� hex �����͵鸸 �����Ͽ� String �迭�� ��ȯ�Ͽ� �����Ѵ�. 
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
	 * �ռ� ��ȯ�� String �迭 ���� ppm ���� ������ ��ȿ�� ���� �ִ��� Ȯ���ϴ� �Լ��̴�.
	 * 
	 * ��� ��ȿ�ϴٸ� true�� �ϳ��� ��ȿ�ϴٸ� false�� �����Ѵ�.
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
	 * �Էµ� String ���� 0~9 �Ǵ� " " ������ Ȯ���Ѵ�. 
	 * 
	 * �ش����� �ʴ´ٸ� false�� �´ٸ� true�� �����Ѵ�.
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
	 * byte �迭�� UTFCustom ���Ŀ� �´� String���� ��ȯ�Ѵ�.
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
