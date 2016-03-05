package com.app.smartkidshoes;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Locale;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ShoesMapActivity extends FragmentActivity implements LocationListener, OnClickListener {
	
	private LocationManager locManager; // GPS 값을 수신하기 위하여
	private Geocoder geoCoder;
	private Location androidLocation = null; 
	private double androidLatPoint, androidLngPoint; // 안드로이드 기기의 위도/경도
	private double shoesLatPoint, shoesLngPoint; // 신발의 위도/경도
	private String shoesGPSvalue, setGPSvalue, freq;
	private GoogleMap gmap;
	private Button btnAnd,btnShoes;
	private TextView SpeedView;
	private int setValueFlag,unShoesFlag,timeFlag,kidnapFlag; // setValueFlag 초기값은 0  서버로 값을 받아오면 1  // unShoes 초기값은 0 신발이 벗겨지면 1 // 
	 // timeFlag는 GPS값을 받아온  시간을 계상해주는 Flag // unkidnap은 아이가 납치되었다는 다이알로그를 한번만 띄워주기 위해 
	private LatLng androidPoint,shoesPoint;
	private UDPclient udpThread = new UDPclient();
	private Thread thread;
	private SharedPreferences sp;
	private static String height;
	private static String gender;
	DecimalFormat df = new DecimalFormat("##.##") ;
	 // float 형태의 사이즈 2의 행렬 생성

	private ArrayList<String> gpsList = new ArrayList<String>(); // GPS 이동 거리를 측정하기 위해 List에 저장
	private ArrayList<String> gpsSpeedList = new ArrayList<String>(); // 이동거리를 계산한 결과를 List에 저장
	private ArrayList<String> SpeedList = new ArrayList<String>(); // 아이가 움직인 이동속도를 계산하여 List에 저장
	private ArrayList<String> timeList = new ArrayList<String>(); // GPS가 이동한 시간을 계산하여 List에 저장
	
	
	private Handler mHandler,countHandler;
	MarkerOptions shoesMarkerOptions, androidMarkerOptions;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		setValueFlag=0;unShoesFlag=0;timeFlag=-1;kidnapFlag=0; // Flag 초기화
		shoesGPSvalue= this.getIntent().getStringExtra("GPS"); // 이전 액티비티에서 받아온 GPS 밸류를 저장
		freq= this.getIntent().getStringExtra("FREQ"); // 이전 액티비티에서 받아온 압력센서가 밟힌 횟수를 저장 
		locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		geoCoder = new Geocoder(this, Locale.KOREA);
		gmap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.Gmap)).getMap();
		btnAnd = (Button)findViewById(R.id.button2);
		btnShoes = (Button)findViewById(R.id.button3);
		SpeedView = (TextView)findViewById(R.id.speed);

		sp = getSharedPreferences("HEIGHT", 0); // 안드로이드 프리퍼런스에 저장해둔 아이의 키 값을 불러온다
		height = sp.getString("HEIGHT", "");
		
		sp = getSharedPreferences("GENDER", 0); // 안드로이드 프리퍼런스에 저장해둔 아이의 성별을 불러온다
		gender = sp.getString("GENDER", "");
		
		btnAnd.setOnClickListener(this);
		btnShoes.setOnClickListener(this);
		if(freq.equals("Z"))
			SpeedView.setText("0m/s");
		else
			SpeedView.setText(calcSpeed(freq)+"m/s");
		String[] arr = shoesGPSvalue.split("/"); // GPS값 파싱
		shoesLatPoint = Double.valueOf(arr[0]).doubleValue();
		shoesLngPoint = Double.valueOf(arr[1]).doubleValue();
		 
		shoesPoint = new LatLng(shoesLatPoint,shoesLngPoint);
		shoesMarkerOptions = new MarkerOptions();
		shoesMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.abc));
		shoesMarkerOptions.position(shoesPoint);
		gmap.addMarker(shoesMarkerOptions); //마커 생성
		gmap.moveCamera(CameraUpdateFactory.newLatLngZoom(shoesPoint, 16));
		
		getAndGPS();
		
		
		androidPoint = new LatLng(androidLatPoint,androidLngPoint);
		androidMarkerOptions = new MarkerOptions();
		androidMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher));
		androidMarkerOptions.position(androidPoint);
		gmap.addMarker(androidMarkerOptions); //마커 생성
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		mHandler = new Handler() {
			public void handleMessage(Message msg) {
				thread = new Thread(udpThread); 
				thread.start(); 
				if(setValueFlag == 1){ // 서버에서 값을 받아왔을때 
					gmap.clear(); // 맵위에 마커들을 지운다
					getAndGPS(); // 자신의 위치를 갱신한다.
					androidPoint = new LatLng(androidLatPoint,androidLngPoint);
					androidMarkerOptions = new MarkerOptions(); // 마커를 생성
					androidMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher));
					androidMarkerOptions.position(androidPoint); // 위치 지정
					setValueFlag = 0;
					SpeedView.setText(calcSpeed(freq)+"m/s"); // 속도 계산
					String[] arr = setGPSvalue.split("/"); 
					shoesLatPoint = Double.valueOf(arr[0]).doubleValue();
					shoesLngPoint = Double.valueOf(arr[1]).doubleValue();
					shoesPoint = new LatLng(shoesLatPoint,shoesLngPoint);
					shoesMarkerOptions.position(shoesPoint); 
					
					gmap.addMarker(androidMarkerOptions); // 맵에 마커 찍기
					gmap.addMarker(shoesMarkerOptions);
				}
				mHandler.sendEmptyMessageDelayed(0, 2000); // 3초마다 위치를 갱신하여 마커를 새로찍어준다.
			}
		};
		
		countHandler = new Handler() {
			public void handleMessage(Message msg) {
				double gpsTotal=0, speedTotal=0, timeTotal=0;
				if(unShoesFlag == 1){ // 신발이 벗겨졌을때
					unShoesFlag = 2; // 한번만 다이얼로그 창을 띄워주기 위해
					AlertDialog.Builder alert_confirm = new AlertDialog.Builder(ShoesMapActivity.this);
					alert_confirm.setMessage("아이의 신발이 벗겨졌습니다.").setCancelable(false).setPositiveButton("확인",
					new DialogInterface.OnClickListener() {
					    @Override
					    public void onClick(DialogInterface dialog, int which) {
					    	 dialog.dismiss();
					    }
					}).setNegativeButton("경찰에 신고하기",
					new DialogInterface.OnClickListener() {
					    @Override
					    public void onClick(DialogInterface dialog, int which) {
					    	startActivity(new Intent(Intent.ACTION_CALL,Uri.parse("tel:01028209069")));
					    	dialog.dismiss();
					    return;
					    }
					});
					AlertDialog alert = alert_confirm.create();
					alert.show();
				}
				if(gpsList.size()>8){ // 8개이상의 값이 들어왔을때 부터 확인한다. 
					float [] distance = null;
					for(int i=0; i<gpsList.size()-1; i++){
						double lat1 = Double.valueOf(gpsList.get(i).split("/")[0]).doubleValue();
						double lng1 = Double.valueOf(gpsList.get(i).split("/")[1]).doubleValue();
						double lat2 = Double.valueOf(gpsList.get(i+1).split("/")[0]).doubleValue();
						double lng2 = Double.valueOf(gpsList.get(i+1).split("/")[1]).doubleValue();
						gpsSpeedList.add(getDistance(lat2,lng2,lat1,lng1)+""); // GPS 값을 거리값으로 계산
						
						Location.distanceBetween(lat1, lng1, lat2, lng2, distance);
						
					}
					if(gpsSpeedList.size()>0){
						gpsTotal = caculAverVelocity(gpsSpeedList);
						speedTotal = caculAverVelocity(SpeedList);
						timeTotal = caculAverVelocity(timeList);
						gpsTotal = gpsTotal/timeTotal;
					}
					
					if(gpsTotal-2>speedTotal && kidnapFlag == 0){ // GPS 이동속도 - 2m/s(7.2km/h) > 아이의 이동속도 
						kidnapFlag = 1;
						AlertDialog.Builder alert_confirm = new AlertDialog.Builder(ShoesMapActivity.this);
						alert_confirm.setMessage("아이가 납치된것 같아요.").setCancelable(false).setPositiveButton("확인",
						new DialogInterface.OnClickListener() {
						    @Override
						    public void onClick(DialogInterface dialog, int which) {
						    	 dialog.dismiss();
						    }
						}).setNegativeButton("경찰에 신고하기",
						new DialogInterface.OnClickListener() {
						    @Override
						    public void onClick(DialogInterface dialog, int which) {
						    	startActivity(new Intent(Intent.ACTION_CALL,Uri.parse("tel:01028209069")));
						    	dialog.dismiss();
						    return;
						    }
						});
						AlertDialog alert = alert_confirm.create();
						alert.setMessage("아이가 납치당한것 판단됩니다.");
						alert.show();
					}
					
					gpsTotal=0;
					speedTotal=0;
					timeTotal=0;
					gpsList.remove(0);
					gpsSpeedList.clear();
					SpeedList.remove(0);
					timeList.remove(0);
				}
				countHandler.sendEmptyMessageDelayed(0, 4000);
			}			
		};
		mHandler.sendEmptyMessage(0);
		countHandler.sendEmptyMessage(0);
	} 
	
	private double getDistance(double sLat, double sLong, double dLat, double dLong) // GPS의 위도 경도 값을 이용하여 두 지점의 거리를 계산
	{
	    final int radius=6371009;
	    double uLat=Math.toRadians(sLat-dLat);
	    double uLong=Math.toRadians(sLong-dLong);
	    double a = Math.sin(uLat/2) * Math.sin(uLat/2) + 
	            Math.cos(Math.toRadians(sLong)) * Math.cos(Math.toRadians(dLong)) * Math.sin(uLong/2) * Math.sin(uLong/2);  
	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));  
	    double distance = radius * c;
	    distance = Math.abs(distance);
	    return distance;
	}
	
	public double caculAverVelocity(ArrayList<String> list){ // 속도의 평균을 계산한다. 단 합리적인 평균값을 구하기위해 최대 / 최소값은 빼준다.
	      int i;
	      int hCount=0;
	      double Max=0;
	      double Min=999999999;
	      double sum=0;
	      double aver=0;
	      
	      for(i=0; i<list.size(); i++){
	         if(Double.valueOf(list.get(i)).doubleValue()>Max){
	            Max=Double.valueOf(list.get(i)).doubleValue();
	         }
	         if(Double.valueOf(list.get(i)).doubleValue()<Min){
	            Min=Double.valueOf(list.get(i)).doubleValue();
	         }
	      }
	      
	      for(i=0; i<list.size(); i++){
	            sum+=Double.valueOf(list.get(i)).doubleValue();
	            hCount++;
	      }
	      sum=sum-Max-Min;
	      aver=(sum/hCount);
	      
	      return aver;
	   }
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		mHandler.removeMessages(0);
		SharedPreferences settings = getSharedPreferences("LAST_ACTIVITY", 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString("LAST_ACTIVITY", "BLUETOOTH_ACTIVITY");
		editor.commit();
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v==btnAnd){
			gmap.moveCamera(CameraUpdateFactory.newLatLngZoom(androidPoint, 17));
		}
		else if(v==btnShoes){
			gmap.moveCamera(CameraUpdateFactory.newLatLngZoom(shoesPoint, 17));
		}
	}
	
	public void getAndGPS(){
		if(!locManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
			  Toast.makeText(getApplicationContext(), "GPS를 켜주십시오", Toast.LENGTH_LONG).show();
		}
		else if(locManager != null){
			locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);// 기지국으로부터 위치 정보를 업데이트 요청
			locManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);// 주소를 가져오기 위해 설정 - KOREA, KOREAN 모두 가능
			androidLocation = locManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
			androidLatPoint = androidLocation.getLatitude();
			androidLngPoint = androidLocation.getLongitude();
		}
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return super.onCreateOptionsMenu(menu);
	}
	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
	}
	
	private class UDPclient implements Runnable {
		private static final String serverIP = "202.30.10.6"; // 서버 아이피
		private static final int serverPort = 14444; // ex: 5555 // 접속 포트
		//private Socket inetSocket = null;
		DatagramSocket socket;
		byte[] outputBuf;
		byte[] inputBuf = new byte[50];
		InetAddress serverAddr;
		DatagramPacket packet,receivePacket;
		public void run() {
			try {
				setValueFlag = 0;
				timeFlag++;
				socket = new DatagramSocket();
				serverAddr = InetAddress.getByName(serverIP);
				
				outputBuf = ("REQ/").getBytes();
				packet = new DatagramPacket(outputBuf, outputBuf.length, serverAddr,serverPort);
				socket.send(packet);
				receivePacket = new DatagramPacket(inputBuf, inputBuf.length, serverAddr,serverPort);
				socket.receive(receivePacket);
				outputBuf = ("ACK/").getBytes();
				packet = new DatagramPacket(outputBuf, outputBuf.length, serverAddr,serverPort);
				socket.send(packet);
				String lat = new String(receivePacket.getData()).split("/")[0];
				String lon = new String(receivePacket.getData()).split("/")[1];
				setGPSvalue=lat+"/"+lon;
				freq = new String(receivePacket.getData()).split("/")[2];
				if (setGPSvalue.equals("0/0")||freq.equals("a")) {
					setValueFlag = 0;
				}
				else if (freq.equals("Z")) {
					freq = "0";
					if(unShoesFlag!=2)
						unShoesFlag = 1;
				}
				else{
					setValueFlag = 1;
					double calSpeed = calcSpeed(freq);
					SpeedList.add(df.format(calSpeed)+"");
					gpsList.add(setGPSvalue);
					timeList.add((timeFlag*3)+"");
					timeFlag=0;
				}
			} catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SocketException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}// run
	}
	public double calcSpeed(String freq){
		double speed=0.0;
		if(gender.equals("BOY")){
			speed=((Double.valueOf(height).doubleValue()*0.42)/100);
		}
		else{
			speed=(((Double.valueOf(height).doubleValue()*0.40)/100));
		}
		speed = speed*Double.valueOf(freq).doubleValue();
		return speed/(5);

	}

}
