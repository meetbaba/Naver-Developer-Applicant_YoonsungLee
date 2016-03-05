/**
 * release 0.1  : ������Ÿ�� (Prototype)
 * 
 * ���� �ϼ� ��¥ : 2015-06-12
 * 
 * ������ : ������ (Yoonsung Lee)
 *  
 *  �� ���α׷��� ���۱��� �����ڿ��� ������ �������� ������� ���� ����, ����, ������ �����մϴ�.
 * 
 * 
 * ���� ���� : 
 * ���ִ��б� �ý��۰��� ����  ������Ʈ
 * ��米�� : �̼���
 * �����ü : MSP (���� ������)
 * 
 * 
 * ���α׷� ���� :
 * 
 * �� ���α׷��� ���ִ��б� 2015�⵵ 1�б� �ý��� ���� �������� ����� ���� ������Ʈ �������� ���������.
 * �� ���α׷� MSP_Manager�� ȸ�� ���� ���� �/���� ������ ��� ����� ��� �� ������ ���� �����ϴ� MSP�� �ý����� �����ϱ����� ���������.
 * �� ���α׷��� MSP_Manager�� ���ؼ� ������ ��å�� �����ڴ� �ս��� �۾����� ������ �� �ְ�, ��� ������ �����޾� �����δ� ���ϰ� 
 * �۾��� �����ϰ� ��ǥ�޼��� ���� ���������� ���̼�, �׸��� ����ũ ��Ȳ�� ���� �ﰢ���� ��ó�� ���� �� �ִ�.
 * 
 * 
 */
package com.msp_manager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * @author ����
 *
 */
@SuppressWarnings("deprecation")
public class Ac_Statistics extends Activity implements OnClickListener{

	
	ClntSock mSock;
	ClntSender mSender;
	ClntReciever mReciever;
	Handler mHandler;
	
	Button btn_daily;
	Button btn_weekly;
	Button btn_monthly;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_statistics);
	
		btn_daily=(Button)findViewById(R.id.Statistics_button_daily);
		btn_weekly=(Button)findViewById(R.id.Statistics_button_weekly);
		btn_monthly=(Button)findViewById(R.id.Statistics_button_monthly);
		
		btn_daily.setOnClickListener(this);
		btn_weekly.setOnClickListener(this);
		btn_monthly.setOnClickListener(this);
		
		Log.e("Start session filter", "start");
//
//		TabHost tabHost = getTabHost();
//		TabHost.TabSpec spec_daily;
//		TabHost.TabSpec spec_weekly;
//		TabHost.TabSpec spec_monthly;
//		
//		LayoutInflater.from(this).inflate(R.layout.activity_statistics, tabHost.getTabContentView(), true);
//		Intent intent_daily=new Intent(Ac_Statistics.this, Tab_Daily.class);
//		
//		spec_daily=tabHost.newTabSpec("Statistics_menu_daily").setIndicator("����");
//		spec_daily.setContent(intent_daily);
//		tabHost.addTab(spec_daily);
//		
//
//		Intent intent_weekly=new Intent(Ac_Statistics.this, Tab_Weekly.class);
//		
//		spec_weekly=tabHost.newTabSpec("Statistics_menu_weekly").setIndicator("�ְ�");
//		spec_weekly.setContent(intent_weekly);
//		tabHost.addTab(spec_weekly);
//		
//		Intent intent_monthly=new Intent(Ac_Statistics.this, Tab_Monthly.class);
//		
//		spec_monthly=tabHost.newTabSpec("Statistics_menu_daily").setIndicator("����");
//		spec_monthly.setContent(intent_monthly);
//		tabHost.addTab(spec_monthly);
//		
//		
		
		
		mSock = ClntSock.getInstance(Functions.IP, Functions.PORT);
		mSender=ClntSender.getInstance(mSock);
		mReciever=ClntReciever.getInstance(mSock);
		
		
	        
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ac__login, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.Statistics_button_daily :
			
			Intent intent_daily=new Intent(Ac_Statistics.this, Tab_Daily.class);
			startActivity(intent_daily);
			onPause();
			break;
		case R.id.Statistics_button_weekly :
			Intent intent_weekly=new Intent(Ac_Statistics.this, Tab_Weekly.class);
			startActivity(intent_weekly);
			onPause();
			break;
		case R.id.Statistics_button_monthly :
			
			Intent intent_monthly=new Intent(Ac_Statistics.this, Tab_Monthly.class);
			startActivity(intent_monthly);
			onPause();
			break;
		}
	}
}
