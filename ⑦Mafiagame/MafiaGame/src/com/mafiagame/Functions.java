/**
 * 
 */
package com.mafiagame;

import java.nio.ByteBuffer;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * @author 윤성
 *
 */
public class Functions {
	
	
	public static String IP="192.168.1.1";
	public static int PORT=9090;
	
	public static String extractFlag(byte[] msgContent){
		
		byte flagByte[]=new byte[2];
		
		System.arraycopy(msgContent, 0, flagByte, 0, flagByte.length);

		String flag;
		Log.i("extra Flag" , "flagBytes leng: "+flagByte.length);
		flag=new String(flagByte);
		Log.i("extra Flag", "handle message flag : "+flag);
		
		return flag;
	}
	
	public static String[] extractMsgStringArray(int msglength, byte[] msgContent){
		
		/**
		 * 3번째~5번째 byte에 저장한 이후 msglength 숫자 뽑아내기
		 */
		byte[] rawContentLengthByte=new byte[3];
		System.arraycopy(msgContent, 2, rawContentLengthByte, 0, rawContentLengthByte.length);
		int rawContentLength=Integer.parseInt(new String(rawContentLengthByte));
		
		Log.i("extractMsgStringArray", "rawString length : "+rawContentLength);
		
		
		/**
		 *  위에서 뽑은 msglength에 따라서 String 뽑아내기
		 */
		byte[] rawContentByte=new byte[rawContentLength];
		
		System.arraycopy(msgContent, 5, rawContentByte, 0, rawContentByte.length);
		
		String rawContent=new String(rawContentByte);
		
		Log.i("extractMsgStringArray", "rawString : "+rawContent);
		
		
		/**
		 * 뽑ㅇ낸 String : 문자로 파싱해서, 내용물들 String 배열에 담기
		 */
		String[] contentArr=rawContent.split(":");
		
		for(int i=0; i<contentArr.length ; i++){
			Log.i("extractMsgStringArray", "String array ["+i+"] : "+contentArr[i]);
		}
		
		return contentArr;
		
	}
	
	public static byte[] makeMsgFromAndroid(String flag, String[] content){
		
		String msg="";
		char divider=':';
		
		for(int i=0; i<content.length; i++){
			msg=msg+content[i];
			
			if(i==content.length-1){
				
			}else{
				msg=msg+divider;
			}
		}
		
		int msgLengthInt=msg.length();
		
		char hun=Character.forDigit(msgLengthInt/100, 10);
		char ten=Character.forDigit((msgLengthInt%100)/10, 10);
		char ill=Character.forDigit(msgLengthInt%10, 10);
		
		String msgLengthStr=""+hun+ten+ill;
		Log.i("making msg", "msgLengthStr : "+msgLengthStr);
		
		byte[] forSendBytes_msgType=flag.getBytes();
		byte[] forSendBytes_msgClength=msgLengthStr.getBytes();
		byte[] forSendBytes_msgContent=msg.getBytes();


		ByteBuffer byteBuffer = ByteBuffer.allocate(forSendBytes_msgType.length
				+ forSendBytes_msgClength.length
				+ forSendBytes_msgContent.length);
		byteBuffer.put(forSendBytes_msgType); // message type flag
		byteBuffer.put(forSendBytes_msgClength); // msg Length
		byteBuffer.put(forSendBytes_msgContent);

		Log.i("making msg", "byte buffer puts end");

		
		byte[] bufferArr = byteBuffer.array();
		
		Log.i("making msg", "byte bufferArray created & return");

		return bufferArr;
	}
	
	public static byte[] makeMessageFromAndroid(char flag, String[] content ){
		
		char[] msgType_send=new char[1];
		msgType_send[0]=flag;
		String msg="";
		char divider=':';
		
		for(int i=0; i<content.length; i++){
			msg=msg+content[i];
			
			if(i==content.length-1){
				
			}else{
				msg=msg+divider;
			}
		}
		
		Log.i("making msg", "content: "+msg);
		
		byte[] forSendBytes_msgType=Functions.charArrayToBytes(msgType_send);
		Log.i("making msg", "type num of bytes"+forSendBytes_msgType.length);
		byte[] forSendBytes_msgClength=Functions.intToByteArray(msg.length());
		Log.i("making msg", "Clength num of bytes"+forSendBytes_msgClength.length);
	    Log.i("making msg", "int to hex"+Integer.toHexString(msg.length()));
	    Log.i("making msg", "byte to hex"+Functions.byteArrayToHex(forSendBytes_msgClength));


		byte[] forSendBytes_msgContent=msg.getBytes();
		Log.i("making msg", "Content num of bytes"+forSendBytes_msgContent.length);


		ByteBuffer byteBuffer = ByteBuffer.allocate(forSendBytes_msgType.length
				+ forSendBytes_msgClength.length
				+ forSendBytes_msgContent.length);
		byteBuffer.put(forSendBytes_msgType); // message type flag
		byteBuffer.put(forSendBytes_msgClength); // msg Length
		byteBuffer.put(forSendBytes_msgContent);

		Log.i("Ac_Login", "byte buffer puts end");

		
		byte[] bufferArr = byteBuffer.array();
		
		Log.i("Ac_Login", "byte bufferArray created");

		return bufferArr;
	}
	
	public static String byteArrayToHex(byte[] ba) {
	    if (ba == null || ba.length == 0) {
	        return null;
	    }
	 
	    StringBuffer sb = new StringBuffer(ba.length * 2);
	    String hexNumber;
	    for (int x = 0; x < ba.length; x++) {
	        hexNumber = "0" + Integer.toHexString(0xff & ba[x]);
	 
	        sb.append(hexNumber.substring(hexNumber.length() - 2));
	    }
	    return sb.toString();
	} 
	public static String asciiToHex(char[] asciiValue)
    {
        char[] chars = asciiValue;
        StringBuffer hex = new StringBuffer();
        for (int i = 0; i < chars.length; i++)
        {
            hex.append(Integer.toHexString((int) chars[i]));
        }
        return hex.toString();
    }
	
	public static int byte2Int_LittleEndian(byte[] src) {


		int result = java.nio.ByteBuffer.wrap(src).order(java.nio.ByteOrder.LITTLE_ENDIAN).getInt();
//		for (int i = 0; i <= 3; i++)
//		    result = (result << 8) + (src[i] & 0xFF);
//		Log.i("Functions", "function: byte to int : "+result);
//	    Log.i("Functions", "int to hex"+Integer.toHexString(result)); 
//	    Log.i("Functions", "byte to hex"+Functions.byteArrayToHex(src));
//
//		Log.i("Functions", "byte to int bigEndian"
//				+ java.nio.ByteBuffer.wrap(src).getInt());
		Log.i("Functions", "byte to int littleEndian"
				+ java.nio.ByteBuffer.wrap(src).order(java.nio.ByteOrder.LITTLE_ENDIAN).getInt());
		return result;
}
	
	public static byte[] intToByteArray(int value) {

		Log.i("Functions", "function: intToByteArray");

		int numBytes = 4;
		byte[] byteArray = new byte[numBytes];

		for (int i = 0; i < numBytes; i++) {
			byteArray[i] = (byte) (value >> 8 * (numBytes - i)); // shift 8*n
																	// bits
		}
		Log.i("Functions", "function: intToByteArray  _return result");

		return byteArray;
	}

	public static String bytesToStringUTFCustom(byte[] bytes) {
		
		Log.i("Functions", "function: bytesToStringUTFCustom");

		char[] buffer = new char[bytes.length >> 1];

		for (int i = 0; i < buffer.length; i++) {

			int bpos = i << 1;

			char c = (char) (((bytes[bpos] & 0x00FF) << 8) + (bytes[bpos + 1] & 0x00FF));

			buffer[i] = c;

		}

		return new String(buffer);

	}

	public static char[] bytesToCharsUTFCustom(byte[] bytes) {

		Log.i("Functions", "function: bytesToCharsUTFCustom");

		char[] buffer = new char[bytes.length >> 1];

		for (int i = 0; i < buffer.length; i++) {

			int bpos = i << 1;

			char c = (char) (((bytes[bpos] & 0x00FF) << 8) + (bytes[bpos + 1] & 0x00FF));

			buffer[i] = c;

		}

		return buffer;

	}
	
	public static byte[] charArrayToBytes(char[] chars){
		
		byte[] bytes = new byte[chars.length*2];
		for(int i=0;i<chars.length;i++) {
		   bytes[i*2] = (byte) (chars[i] >> 8);
		   bytes[i*2+1] = (byte) chars[i];
		}
		
		return bytes;
	}
	
	

}
