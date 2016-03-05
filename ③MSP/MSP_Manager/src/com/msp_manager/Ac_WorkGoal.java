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

import java.util.Calendar;




import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.widget.DatePicker;

public class Ac_WorkGoal extends Activity implements OnClickListener {

	
	ClntSock mSock;
	ClntSender mSender;
	ClntReciever mReciever;
	Handler mHandler;

	ListView mListView;
	CustomAdapter mArrayAdapter;
	
	TextView txtView_date;
	Button btn_store;
	
	private int mYear;
	private int mMonth;
	private int mDay;
	
	static final int DATE_DIALOG_ID = 0;
	
	int numOfLine;
	int []numOfWorkerOnLine;
	
	private int realMonth;
	
	@SuppressWarnings("deprecation")
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		String flag;
		String[] content;
		
		switch(v.getId()){
		
		case R.id.WorkGoal_txtView_date :


			
			showDialog(DATE_DIALOG_ID);
			
			
			break;
		case R.id.WorkGoal_button_store :
			/**
			 * B3xxx ��¥:���μ�:����-��ǥ:������ �۾��� ��:�۾���-��ǥ�۾���:�۾���-��ǥ�۾���:..
			 */
			Log.i("Ac_WorkGoal", "���� button click");
			
			flag="b3";
			
			int num_total_worker=0;
			
			for(int k=0; k<numOfWorkerOnLine.length;k++){
				num_total_worker+=numOfWorkerOnLine[k];
			}
			content=new String[2+(numOfLine*2)+num_total_worker];
			
			content[0]=new String(""+mYear+"-"+mMonth+"-"+mDay);
			
			content[1]=new String(""+numOfLine);
			int indexOfCurContentArr=2;
			int indexAdapterArr=0;
			ItemContent tmpItem;
			
			for (int i = 0; i < numOfLine; i++) {
				
				/*����- ��ǥ  �Է�*/
				tmpItem = (ItemContent) mArrayAdapter.getItem(indexAdapterArr);
				content[indexOfCurContentArr] = new String(
						tmpItem.getItem_value_textView() + "-"
								+ tmpItem.getItem_value_editText());
				Log.i("Ac_WorkGoal", "Sending msg : "+content[indexOfCurContentArr]);
				indexOfCurContentArr++;
				indexAdapterArr++;
				
				/*������ �۾��� �� �Է�*/
				content[indexOfCurContentArr]=new String(""+numOfWorkerOnLine[i]);
				indexOfCurContentArr++;
				
				for(int j=0; j<numOfWorkerOnLine[i]; j++){
					/*�۾���- ��ǥ  �Է�*/
					tmpItem = (ItemContent) mArrayAdapter.getItem(indexAdapterArr);
					content[indexOfCurContentArr] = new String(
							tmpItem.getItem_value_textView() + "-"
									+ tmpItem.getItem_value_editText());
					Log.i("Ac_WorkGoal", "Sending msg : "+content[indexOfCurContentArr]);
					indexOfCurContentArr++;
					indexAdapterArr++;
				}
			}
			
			
			mSender.sendMsg(Functions.makeMsgFromAndroid(flag, content));
			
			break;
		}
		
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_workgoal);
	
		Log.e("Start session filter", "start");

		txtView_date=(TextView)findViewById(R.id.WorkGoal_txtView_date);
		btn_store=(Button)findViewById(R.id.WorkGoal_button_store);
		txtView_date.setOnClickListener(this);
		btn_store.setOnClickListener(this);

		mHandler=new MsgHandler(Looper.getMainLooper());
		
		mSock = ClntSock.getInstance(Functions.IP, Functions.PORT);
		mSender=ClntSender.getInstance(mSock);
		mReciever=ClntReciever.getInstance(mSock);
		mReciever.setHandler(mHandler);
		
		mArrayAdapter=new CustomAdapter();
		mListView=(ListView)findViewById(R.id.WorkGoal_listview);
		
		mListView.setAdapter(mArrayAdapter);
		
		Intent intent=getIntent();
		
		
		final Calendar c = Calendar.getInstance();
	        mYear = c.get(Calendar.YEAR);
	        mMonth= c.get(Calendar.MONTH);
	        mDay  = c.get(Calendar.DAY_OF_MONTH);
	        // (4) �νĵ� ��¥�� ���
	        updateDisplay();
		
	        
	}

	 private void updateDisplay() {
		// main.xml�� ���̾ƿ��� ��ġ�� ��¥ �Է� TextView�� �νĵ� ��¥ ���
		 
		txtView_date.setText(new StringBuilder()
				// ���� �ý��ۿ��� 0~11�� �ν��ϱ� ������ 1�� ������
				.append(mYear).append("-").append(mMonth+1).append("-")
				.append(mDay).append(" "));

	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_DIALOG_ID:
			return new DatePickerDialog(this, mDateSetListener, mYear, mMonth,
					mDay);
		}

		return null;
	}

	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {


		String flag;
		String[] content;
		
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;
			
			/**
			 * b1xxx��¥  �޼��� ���� (��¥�� ��-��-��
			 */
			Log.i("Ac_WorkGoal", "Date pick-up");
			flag="b1";
			
			
			
			content=new String[1];
			realMonth=mMonth+1;
			content[0]=new String(""+mYear+"-"+realMonth+"-"+mDay);
			
			Log.i("Ac_WorkGoal", "sended msg : "+mYear+"-"+mMonth+"-"+mDay);
			mSender.sendMsg(Functions.makeMsgFromAndroid(flag, content));
			
			// ����ڰ� ������ ��¥�� ���
			updateDisplay();

		}
	};

	class MsgHandler extends Handler{

		public MsgHandler(Looper mainLooper) {
			// TODO Auto-generated constructor stub
			super(mainLooper);
		}

		@Override
		public void handleMessage(Message inputMessage) {
			// TODO Auto-generated method stub
			
			int msglength;
			msglength=inputMessage.what;
			byte[] msgContent=new byte[msglength];
			msgContent=(byte[])inputMessage.obj;
			
			String flag=Functions.extractFlag(msgContent);
			
			String fb2=new String("b2");

			Log.i("Ac_PlayingGame handler", ""+flag);
			
			if(flag.equals(fb2)){
				/**
				 *b2XXX��¥:���μ�:���� -��ǥ �޼���:������ �۾��� ��:�۾���-��ǥ�۾���:�۾���-��ǥ�۾���:..
				 */
				int indexOfLineNum=0;
				String[] contentArr=Functions.extractMsgStringArray(msglength, msgContent);
				numOfLine=Integer.parseInt(contentArr[1]);
				numOfWorkerOnLine=new int[numOfLine];
				
				int indexOfCurContentArr=2;
				
				for(int i=0; i<numOfLine; i++){
					String firstView;
					String secondView;
					String thirdView;
					
					String tmpStr;
					String[] tmpArr;
					
					/* �� : ���� �ݺ�					 */
					tmpStr=contentArr[indexOfCurContentArr];
					indexOfCurContentArr++;
					tmpArr=tmpStr.split("-");
					
					firstView=tmpArr[0];
					secondView=tmpArr[1];
					thirdView=new String("%");
					
					mArrayAdapter.add(new ItemContent(firstView, secondView, thirdView));
					mArrayAdapter.notifyDataSetChanged();
					Log.i("recieved msg falg b2", "new Addapter : "+firstView+"/"+secondView+"/"+thirdView);
					/*������� �ݺ� */
					
					numOfWorkerOnLine[i]=Integer.parseInt(contentArr[indexOfCurContentArr]);
					indexOfCurContentArr++;
					for(int j=0; j<numOfWorkerOnLine[i]; j++){
						tmpStr=contentArr[indexOfCurContentArr];
						indexOfCurContentArr++;
						tmpArr=tmpStr.split("-");
						
						firstView=tmpArr[0];
						secondView=tmpArr[1];
						thirdView=new String("��");
						
						mArrayAdapter.add(new ItemContent(firstView, secondView, thirdView));
						mArrayAdapter.notifyDataSetChanged();
						Log.i("recieved msg falg b2", "new Addapter : "+firstView+"/"+secondView+"/"+thirdView);

					}
					
					
				}
				
			}else{
				Toast.makeText(getApplicationContext(), "��ϵ��� ���� flag : "+flag, Toast.LENGTH_LONG).show();
			}
		}

	}
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
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

	
}
