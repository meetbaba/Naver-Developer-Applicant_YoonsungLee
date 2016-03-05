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

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author ����
 *
 */
public class Tab_Monthly extends Activity implements OnClickListener {

	ClntSock mSock;
	ClntSender mSender;
	ClntReciever mReciever;
	Handler mHandler;

	TextView txtView_date;

	private int mYear;
	private int mMonth;
	private int mDay;

	private int realMonth;

	static final int DATE_DIALOG_ID = 0;

	int rate_success;
	int rate_fail;

	AlertDialog.Builder dialog;
	
	@SuppressWarnings("deprecation")
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch (v.getId()) {

		case R.id.TabMonthly_txtView_date:

			showDialog(DATE_DIALOG_ID);
			
			break;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_monthly);

		Log.e("Start session filter", "start");

		txtView_date=(TextView)findViewById(R.id.TabMonthly_txtView_date);
		txtView_date.setOnClickListener(this);
		
		mHandler = new MsgHandler(Looper.getMainLooper());

		mSock = ClntSock.getInstance(Functions.IP, Functions.PORT);
		mSender = ClntSender.getInstance(mSock);
		mReciever = ClntReciever.getInstance(mSock);
		mReciever.setHandler(mHandler);

		final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth= c.get(Calendar.MONTH);
        mDay  = c.get(Calendar.DAY_OF_MONTH);
        // (4) �νĵ� ��¥�� ���
        updateDisplay();
	

		dialog = new AlertDialog.Builder(Tab_Monthly.this);

		dialog.setMessage("���� �߻����� ����ġ �̻��Դϴ�. ���� ������ Ȯ���ϼ���.")
				.setCancelable(false)
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								
							}
						});
	}

	private void updateDisplay() {
		// main.xml�� ���̾ƿ��� ��ġ�� ��¥ �Է� TextView�� �νĵ� ��¥ ���

		txtView_date.setText(new StringBuilder()
		// ���� �ý��ۿ��� 0~11�� �ν��ϱ� ������ 1�� ������
				.append(mYear).append("-").append(mMonth+1).append(" "));

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
			// ����ڰ� ������ ��¥�� ���
			

			/**
			 * c3xxx��¥  �޼��� ���� (��¥�� ��-��-��
			 */
			Log.i("Ac_WorkGoal", "Date pick-up");
			flag="c3";
			
			
			
			content=new String[1];
			realMonth=mMonth+1;
			content[0]=new String(""+mYear+"-"+realMonth+"-"+mDay);
			
			Log.i("Ac_WorkGoal", "sended msg : "+mYear+"-"+mMonth+"-"+mDay);
			mSender.sendMsg(Functions.makeMsgFromAndroid(flag, content));
			
			updateDisplay();

		}
	};

	class MsgHandler extends Handler {

		public MsgHandler(Looper mainLooper) {
			// TODO Auto-generated constructor stub
			super(mainLooper);
		}

		@Override
		public void handleMessage(Message inputMessage) {
			// TODO Auto-generated method stub

			int msglength;
			msglength = inputMessage.what;
			byte[] msgContent = new byte[msglength];
			msgContent = (byte[]) inputMessage.obj;

			String flag = Functions.extractFlag(msgContent);

			String fc6 = new String("c6");

			Log.i("Tab_Monthly handler", "" + flag);

			if (flag.equals(fc6)) {
				/**
				 * C6xxx�޼���:������
				 */

				String[] contentArr = Functions.extractMsgStringArray(
						msglength, msgContent);
				rate_success = Integer.parseInt(contentArr[0]);
				rate_fail = Integer.parseInt(contentArr[1]);

				showGraphSuccess(rate_success);

				showGraphFail(rate_fail);
				
				if(rate_fail>5){
					
					
					dialog.show();
					
				}

			} else {
				Toast.makeText(getApplicationContext(),
						"��ϵ��� ���� flag : " + flag, Toast.LENGTH_LONG).show();
			}
		}

	}

	public void showGraphSuccess(int value_success) {
		// �׷��� ������ ����
		DefaultRenderer renderer = new DefaultRenderer();

		// ��� ǥ�� ����� ���� ũ��
		renderer.setChartTitle("��ǥ �޼���");
		renderer.setChartTitleTextSize(70);


		int tmpValue=100-value_success;
		// �� �׸� ���� ������
		int[] values = new int[] { value_success, tmpValue };


		
		// �� �׸� ���� ����
		CategorySeries series = new CategorySeries("��ǥ �޼���");
		series.add("��ǥ �޼��� : "+value_success+"(%)" , values[0]);
		series.add("�޼� �̴� : "+tmpValue+"(%)", values[1]);


		// �׷����� �Բ� ǥ�õǴ� �׸� ǥ�� ������ ���� ũ��
		renderer.setLabelsTextSize(50);

		// �ϴܺο� ǥ�õǴ� ������ ���� ũ��
		renderer.setLegendTextSize(40);

		// �� �׸� ������ �÷� ����
		int[] colors = new int[] { Color.GREEN, Color.RED };
		for (int color : colors) {
			SimpleSeriesRenderer r = new SimpleSeriesRenderer();
			r.setColor(color);
			renderer.addSeriesRenderer(r);
		}

		// Zoom ��ư ǥ�� ����
		renderer.setZoomButtonsVisible(true);
		// Zoom ��� Ȱ��ȭ
		renderer.setZoomEnabled(true);

		// �׷����� View�� ���´�.
		GraphicalView gv = ChartFactory.getPieChartView(this, series, renderer);

		// �׷����� ȭ�� ���
		LinearLayout llBody = (LinearLayout) findViewById(R.id.TabMonthly_linearlayout_graph_success);
		llBody.addView(gv);
	}

	public void showGraphFail(int value_fail) {
		// �׷��� ������ ����
		DefaultRenderer renderer = new DefaultRenderer();

		// ��� ǥ�� ����� ���� ũ��
		renderer.setChartTitle("���� �߻���");
		renderer.setChartTitleTextSize(70);



		int tmpValue=100-value_fail;
		// �� �׸� ���� ������
		int[] values = new int[] { tmpValue, value_fail };


		// �� �׸� ���� ����
		CategorySeries series = new CategorySeries("���� �߻���");
		series.add("����ǰ ���� : "+tmpValue+"(%)", values[0]);
		series.add("���� �߻��� : "+value_fail+"(%)", values[1]);


		// �׷����� �Բ� ǥ�õǴ� �׸� ǥ�� ������ ���� ũ��
		renderer.setLabelsTextSize(50);

		// �ϴܺο� ǥ�õǴ� ������ ���� ũ��
		renderer.setLegendTextSize(40);

		// �� �׸� ������ �÷� ����
		int[] colors = new int[] { Color.BLUE, Color.RED };
		for (int color : colors) {
			SimpleSeriesRenderer r = new SimpleSeriesRenderer();
			r.setColor(color);
			renderer.addSeriesRenderer(r);
		}

		// Zoom ��ư ǥ�� ����
		renderer.setZoomButtonsVisible(true);
		// Zoom ��� Ȱ��ȭ
		renderer.setZoomEnabled(true);

		// �׷����� View�� ���´�.
		GraphicalView gv = ChartFactory.getPieChartView(this, series, renderer);

		// �׷����� ȭ�� ���
		LinearLayout llBody = (LinearLayout) findViewById(R.id.TabMonthly_linearlayout_graph_fail);
		llBody.addView(gv);
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
