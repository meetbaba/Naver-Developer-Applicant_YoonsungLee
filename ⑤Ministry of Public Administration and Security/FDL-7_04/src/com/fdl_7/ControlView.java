/**
 * ���ø����̼��� �� ���� ȭ��
 *
 * Input Device�� �����Ͽ� ��������� �����ϰ�,
 * Output Device�� �����Ͽ� ��������� �����ϸ�
 * 
 * Input Device���� ���޵� �����͸� ȭ�鿡 �׷����� text�� ǥ���ϰ�, Output Device�� �����Ѵ�.
 *
 */

package com.fdl_7;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendForm;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.LimitLine.LimitLabelPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Highlight;

public class ControlView extends Activity implements OnClickListener, OnChartGestureListener, OnChartValueSelectedListener{

	// Debugging
		private static final String TAG = "Main";
		
		// Intent request code
		private static final int ROLE_INPUT =1;
		private static final int ROLE_OUTPUT =2;
		
		private static final int INPUT_REQUEST_CONNECT_DEVICE = 1;
		private static final int OUTPUT_REQUEST_ENABLE_BT = 2;
		
		private static final int OUTPUT_REQUEST_CONNECT_DEVICE = 3;
		private static final int INPUT_REQUEST_ENABLE_BT = 4;

		private static final int MSG_DATA=0;
		private static final int MSG_DEVICE_NAME=1;
		
		private static final float Y_LIMIT_DEFAULT=40;
		private static final float Y_LIMIT_40=40f;
		private static final float Y_LIMIT_60=65f;
		private static final float Y_LIMIT_80=80f;
		private static final float Y_LIMIT_100=100f;

		private static final int TIME_ANIMATE=100;
		private static final int NUM_X_AXIS=30;
		
		private BluetoothService btOutput = null;
		private BluetoothService btInput = null;
		
		TextView txtView_inputDevice;
		TextView txtView_outputDevice;
		TextView txtView_ppmData;

		Button btn_exit;
		
		String ppmData;
		String inputDevice_name;
		String outputDevice_name;
		
		private MsgHandler mHandler;
		private LineChart mChart;
		
		YAxis leftAxis=null;		
		float[] linedata;

		
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			Log.e(TAG, "onCreate");
			
			setContentView(R.layout.activity_control_view);
			
			mHandler = new MsgHandler(Looper.getMainLooper());
			/** Main Layout **/
			txtView_inputDevice=(TextView)findViewById(R.id.txtView_input_device);
			txtView_outputDevice=(TextView)findViewById(R.id.txtView_output_device);
			txtView_ppmData=(TextView)findViewById(R.id.txtView_data_ppm);
			btn_exit=(Button)findViewById(R.id.button_exit);
			
			btn_exit.setOnClickListener(this);
			txtView_inputDevice.setOnClickListener(this);
			txtView_outputDevice.setOnClickListener(this);
			
			// BluetoothService Ŭ���� ����
			if(btInput == null) {
				btInput = new BluetoothService(this, mHandler, ROLE_INPUT);
			}
			if(btOutput==null){
				btOutput=new BluetoothService(this, mHandler, ROLE_OUTPUT);
			}
			
			//chart ����.
			setChart();
			
		}

		/**
		 * chart �⺻�� �����ϱ����� �Լ��̴�.
		 */
		public void setChart(){
			
			mChart = (LineChart) findViewById(R.id.mpandroidchart);
	        mChart.setOnChartGestureListener(this);
	        mChart.setOnChartValueSelectedListener(this);

	        // no description text
	        mChart.setDescription("");
	        mChart.setNoDataTextDescription("You need to provide data for the chart.");

	        // enable value highlighting
	        mChart.setHighlightEnabled(true);

	        // enable touch gestures
	        mChart.setTouchEnabled(true);

	        // enable scaling and dragging
	        mChart.setDragEnabled(true);
	        mChart.setScaleEnabled(true);
	        // mChart.setScaleXEnabled(true);
	        // mChart.setScaleYEnabled(true);

	        // if disabled, scaling can be done on x- and y-axis separately
	        mChart.setPinchZoom(true);

	        // set an alternative background color
	        // mChart.setBackgroundColor(Color.GRAY);

	        // create a custom MarkerView (extend MarkerView) and specify the layout
	        // to use for it
	        MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker_view);

	        // set the marker to the chart
	        mChart.setMarkerView(mv);

	        // enable/disable highlight indicators (the lines that indicate the
	        // highlighted Entry)
	        mChart.setHighlightIndicatorEnabled(false);
	        
	        // x-axis limit line
//	        LimitLine llXAxis = new LimitLine(10f, "Index 10");
//	        llXAxis.setLineWidth(4f);
//	        llXAxis.enableDashedLine(10f, 10f, 0f);
//	        llXAxis.setLabelPosition(LimitLabelPosition.POS_RIGHT);
//	        llXAxis.setTextSize(10f);
//	        
//	        XAxis xAxis = mChart.getXAxis();
//	        xAxis.addLimitLine(llXAxis);
	        
	        LimitLine ll1 = new LimitLine(30f, "WARNING");
	        ll1.setLineWidth(4f);
	        ll1.enableDashedLine(10f, 10f, 0f);
	        ll1.setLabelPosition(LimitLabelPosition.POS_RIGHT);
	        ll1.setTextSize(10f);
//
//	        LimitLine ll2 = new LimitLine(-30f, "Lower Limit");
//	        ll2.setLineWidth(4f);
//	        ll2.enableDashedLine(10f, 10f, 0f);
//	        ll2.setLabelPosition(LimitLabelPosition.POS_RIGHT);
//	        ll2.setTextSize(10f);

	        leftAxis = mChart.getAxisLeft();
	        leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
	        leftAxis.addLimitLine(ll1);
	        
	        leftAxis.setAxisMaxValue(Y_LIMIT_DEFAULT);
	        leftAxis.setAxisMinValue(0f);
	        leftAxis.setStartAtZero(true);
	        leftAxis.enableGridDashedLine(10f, 10f, 0f);
	        
	        // limit lines are drawn behind data (and not on top)
	        leftAxis.setDrawLimitLinesBehindData(true);

	        mChart.getAxisRight().setEnabled(false);

	        // add data
	        linedata=new float[NUM_X_AXIS];
	        for(int i=0; i<NUM_X_AXIS; i++){
	        	linedata[i]=0f;
	        }
	        setData(linedata);
	        
//	        mChart.setVisibleXRange(20);
//	        mChart.setVisibleYRange(20f, AxisDependency.LEFT);
//	        mChart.centerViewTo(20, 50, AxisDependency.LEFT);
	        
	        mChart.animateX(TIME_ANIMATE, Easing.EasingOption.EaseInOutQuart);
//	        mChart.invalidate();
	        
	        // get the legend (only possible after setting data)
	        Legend l = mChart.getLegend();

	        // modify the legend ...
	        // l.setPosition(LegendPosition.LEFT_OF_CHART);
	        l.setForm(LegendForm.LINE);

	        // // dont forget to refresh the drawing
	        mChart.invalidate();
		}
		
		
		/**
		 * @param values
		 * 
		 * �Էµ� float �迭�� ����� chart�� ���� �����͸� �����Ѵ�.
		 */
	    private void setData(float[] values) {

	        ArrayList<String> xVals = new ArrayList<String>();
	        ArrayList<Entry> yVals = new ArrayList<Entry>();
	        
	        for (int i = 0; i <NUM_X_AXIS ; i++) {
	            xVals.add((i) + "");
	            yVals.add(new Entry(values[i], i));

	        }


	        // create a dataset and give it a type
	        LineDataSet set1 = new LineDataSet(yVals, "PPM DATA SET");
	        // set1.setFillAlpha(110);
	        // set1.setFillColor(Color.RED);

	        // set the line to be drawn like this "- - - - - -"
	        set1.enableDashedLine(10f, 5f, 0f);
	        set1.setColor(Color.BLACK);
	        set1.setCircleColor(Color.BLACK);
	        set1.setLineWidth(1f);
	        set1.setCircleSize(1.5f);
	        set1.setDrawCircleHole(false);
	        set1.setValueTextSize(8f);
	        set1.setFillAlpha(65);
	        set1.setFillColor(Color.BLACK);
//	        set1.setDrawFilled(true);
	        // set1.setShader(new LinearGradient(0, 0, 0, mChart.getHeight(),
	        // Color.BLACK, Color.WHITE, Shader.TileMode.MIRROR));

	        ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
	        dataSets.add(set1); // add the datasets

	        // create a data object with the datasets
	        LineData data = new LineData(xVals, dataSets);

	        // set data
	        mChart.setData(data);
	    }
	    
	    
		@Override
		/**
		 * ȭ�� ���� Icon�� Ŭ���Ҷ� �Ͼ�� �̺�Ʈ ����
		 */
		public void onClick(View v) {
			
			switch(v.getId()){
			
			case R.id.txtView_input_device : 

				if(btInput.getDeviceState()) {
					// ��������� ���� ������ ����� ��
					btInput.enableBluetooth();
				} else {
					finish();
				}
				
				break;
			
			case R.id.txtView_output_device :

				if(btOutput.getDeviceState()) {
					// ��������� ���� ������ ����� ��
					btOutput.enableBluetooth();
				} else {
					finish();
				}
				break;
				
			case R.id.button_exit : 
				finish();
				break;
			
			}
			
		}
		
		/**
		 * ������� Enable �� Request �� ���� result ��ȯ�� ����.
		 */
		public void onActivityResult(int requestCode, int resultCode, Intent data) {
	        Log.d(TAG, "onActivityResult " + resultCode);
	        
	        switch (requestCode) {
	        
	        /** �߰��� �κ� ���� **/
	        case INPUT_REQUEST_CONNECT_DEVICE:
	            // When DeviceListActivity returns with a device to connect
	            if (resultCode == Activity.RESULT_OK) {
	            	btInput.getDeviceInfo(data);
	            }
	            break;
	        case OUTPUT_REQUEST_CONNECT_DEVICE:
	            // When DeviceListActivity returns with a device to connect
	            if (resultCode == Activity.RESULT_OK) {
	            	btOutput.getDeviceInfo(data);
	            }
	            break;
	        /** �߰��� �κ� �� **/
	        case INPUT_REQUEST_ENABLE_BT:
	            // When the request to enable Bluetooth returns
	            if (resultCode == Activity.RESULT_OK) {
	            	// Next Step
	            	btInput.scanDevice();
	            } else {

	                Log.d(TAG, "input Bluetooth is not enabled");
	            }
	            break;
	        case OUTPUT_REQUEST_ENABLE_BT:
	            // When the request to enable Bluetooth returns
	            if (resultCode == Activity.RESULT_OK) {
	            	// Next Step
	            	btOutput.scanDevice();
	            } else {

	                Log.d(TAG, "output Bluetooth is not enabled");
	            }
	            break;
	        }
		}

	
		/**
		 * 
		 * BluetoothService���� data read�� �ڵ鷯�� ������ �޼����� ���� �ڵ鷯.
		 *
		 */
		private class MsgHandler extends Handler{
			
			public MsgHandler(Looper mainLooper) {
				// TODO Auto-generated constructor stub
				super(mainLooper);
			}

			@Override
			public void handleMessage(Message inputMessage) {

				int flag;
				flag=inputMessage.what;
				
				
				int msglength;
				msglength = inputMessage.arg2;
				
				byte[] msgContent = new byte[msglength];
				msgContent = (byte[]) inputMessage.obj;
				
				
				/**
				 * Data�� ���޵� �޼������
				 */
				if(flag==MSG_DATA){
					
					Log.i(TAG, "Receive MSG Data ");

					/**
					 * valid�� �޼������� üũ
					 */
					if(Functions.checkValidArea(msglength, msgContent)){
						// valid message
						Log.i(TAG, "Receive MSG Data : valid Area");

						String[] strArr=Functions.extractValidArea(msgContent);
						
						/**
						 * Data�� Valid�� �������� Ȯ��
						 */
						if(Functions.checkValidNumber(strArr)){
							
							Log.i(TAG, "Receive MSG Data : valid Number");

							ppmData=Functions.extractPPMValue(strArr);
							
							
							//TextView�� ppm Dataǥ��
							txtView_ppmData.setText(ppmData);

							/**
							 * update chart---------------
							 */
							float[] tmp=new float[NUM_X_AXIS];
							System.arraycopy(linedata, 1, tmp, 0, NUM_X_AXIS-1);
							tmp[NUM_X_AXIS-1]=Float.parseFloat(ppmData);
							
//							for(int i=0; i<60; i++){
//								
//							
//								if(tmp[i]>(float)Y_LIMIT_80){
//									
//							        leftAxis.setAxisMaxValue(Y_LIMIT_100);
//								}else if(tmp[i]>(float)Y_LIMIT_60){
//									leftAxis.setAxisMaxValue(Y_LIMIT_80);
//								}else if(tmp[i]>(float)Y_LIMIT_40){
//									leftAxis.setAxisMaxValue(Y_LIMIT_60);
//								}else if(tmp[i]>(float)Y_LIMIT_DEFAULT){
//									leftAxis.setAxisMaxValue(Y_LIMIT_40);
//								}
//							}
//							
							
							setData(tmp);
							mChart.invalidate();

							System.arraycopy(tmp, 0, linedata, 0, NUM_X_AXIS);

							/**
							 * ���� �۷����� ���� �����͸� ���⿡ btOutput.write()�Լ��� �̿��Ͽ� ����.
							 */
							//btOutput.write(ppmData.getBytes());
							
						}else{
							//Log.i("BT Service", "Invalid Message");
						}
					
					}else{
						//Log.i("BT Service", "Invalid Message");
					}
				}else if(flag==MSG_DEVICE_NAME){
					
					/**
					 * Device �̸��� �����ϸ�, Input Device, Output Device�� ��Ÿ���� textâ�� �ٲ��ش�.
					 */
					int role=inputMessage.arg1;
					
					
					
					if(role==ROLE_INPUT){
						try {
							inputDevice_name=new String(msgContent, "UTF-8");
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						txtView_inputDevice.setText(inputDevice_name);
					}else if(role==ROLE_OUTPUT){
						try {
							outputDevice_name=new String(msgContent, "UTF-8");
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						txtView_outputDevice.setText(outputDevice_name);
					}
				}
				
				
				
			}
		}



	    @Override
	    public void onChartLongPressed(MotionEvent me) {
	        Log.i("LongPress", "Chart longpressed.");
	    }

	    @Override
	    public void onChartDoubleTapped(MotionEvent me) {
	        Log.i("DoubleTap", "Chart double-tapped.");
	    }

	    @Override
	    public void onChartSingleTapped(MotionEvent me) {
	        Log.i("SingleTap", "Chart single-tapped.");
	    }

	    @Override
	    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {
	        Log.i("Fling", "Chart flinged. VeloX: " + velocityX + ", VeloY: " + velocityY);
	    }

	    @Override
	    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {
	        Log.i("Scale / Zoom", "ScaleX: " + scaleX + ", ScaleY: " + scaleY);
	    }

		@Override
		public void onChartTranslate(MotionEvent me, float dX, float dY) {
			Log.i("Translate / Move", "dX: " + dX + ", dY: " + dY);
		}

		@Override
	    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
	        Log.i("Entry selected", e.toString());
	        Log.i("", "low: " + mChart.getLowestVisibleXIndex() + ", high: " + mChart.getHighestVisibleXIndex());
	    }

	    @Override
	    public void onNothingSelected() {
	        Log.i("Nothing selected", "Nothing selected.");
	    }
	
}
