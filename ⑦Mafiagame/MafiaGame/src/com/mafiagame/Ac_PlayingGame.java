/**
 * 
 */
package com.mafiagame;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author 윤성
 *
 */
public class Ac_PlayingGame extends Activity implements OnClickListener {

	ClntSock mSock;
	ClntSender mSender;
	ClntReciever mReciever;
	Handler mHandler;

	TextView txtView_name;
	TextView txtView_game_state;
	TextView txtView_position;
	TextView txtView_result;
	TextView txtView_num_total;
	TextView txtView_num_mafia;
	TextView txtView_user_01;
	TextView txtView_user_02;
	TextView txtView_user_03;
	TextView txtView_user_04;
	TextView txtView_company;
	TextView txtView_user_content_01;
	TextView txtView_user_content_02;
	TextView txtView_user_content_03;
	TextView txtView_user_content_04;

	RadioButton radio_user_01;
	RadioButton radio_user_02;
	RadioButton radio_user_03;
	RadioButton radio_user_04;

	Button btn_up;
	Button btn_down;
	Button btn_point;

	int numUsers;
	int checkedRadio = 0;

	int game_state_day;
	private static int DAY = 1;
	private static int NIGHT = 2;

	private static final String POLICE = "police";
	private static final String MAFIA = "mafia";
	private static final String CITIZEN = "citizen";

	AlertDialog.Builder dialog;

	private String getPointedUserName() {
		String userName = "";

		if (checkedRadio == 1) {
			userName = txtView_user_01.getText().toString();
		} else if (checkedRadio == 2) {
			userName = txtView_user_02.getText().toString();
		} else if (checkedRadio == 3) {
			userName = txtView_user_03.getText().toString();
		} else if (checkedRadio == 4) {
			userName = txtView_user_04.getText().toString();
		}

		return userName;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		String flag;
		String[] content;
		switch (v.getId()) {
		case R.id.PlayingGame_btn_up:

			Log.i("Ac_PlayingGame", "UP button click");
			flag = "13";
			content = new String[2];
			content[0] = txtView_name.getText().toString();
			content[1] = "UP";

			mSender.sendMsg(Functions.makeMsgFromAndroid(flag, content));

			setUpDownButton(false);
			break;

		case R.id.PlayingGame_btn_down:

			Log.i("Ac_PlayingGame", "UP button click");
			flag = "13";
			content = new String[2];
			content[0] = txtView_name.getText().toString();
			content[1] = "DOWN";

			mSender.sendMsg(Functions.makeMsgFromAndroid(flag, content));
			setUpDownButton(false);
			break;

		case R.id.PlayingGame_btn_point:

			if (checkedRadio == 0) {
				Toast.makeText(getApplicationContext(),
						"지목할 유저를 선택한 후 '지목'버튼을 누르세요", Toast.LENGTH_SHORT)
						.show();
			} else {

				if (game_state_day == DAY) {
					/**
					 * 11xxx지목대상(유저이름)
					 */
					Log.i("Ac_PlayingGame", "Point button click");
					flag = "11";
					content = new String[1];
					content[0] = getPointedUserName();

					mSender.sendMsg(Functions.makeMsgFromAndroid(flag, content));

				} else if (game_state_day == NIGHT) {

					if (txtView_position.getText().toString().equals(POLICE)) {
						/**
						 * 17xxx유저1 : 경찰이 지목
						 */
						Log.i("Ac_PlayingGame", "POLICE - Point button click");
						flag = "17";
						content = new String[1];
						content[0] = getPointedUserName();

						mSender.sendMsg(Functions.makeMsgFromAndroid(flag,
								content));

					} else if (txtView_position.getText().toString()
							.equals(MAFIA)) {
						/**
						 * 20xxx유저1 : 마피아가 지목
						 */
						Log.i("Ac_PlayingGame", "POLICE - Point button click");
						flag = "20";
						content = new String[1];
						content[0] = getPointedUserName();

						mSender.sendMsg(Functions.makeMsgFromAndroid(flag,
								content));
					}

				}
				setPointButton(false);
			}
			break;
		}
	}

	private void setGameStateDay(int value) {
		this.game_state_day = value;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_playinggame);

		txtView_name = (TextView) findViewById(R.id.PlayingGame_txtview_name);
		txtView_game_state = (TextView) findViewById(R.id.PlayingGame_txtView_game_state);
		txtView_position = (TextView) findViewById(R.id.PlayingGame_txtView_position);
		txtView_result = (TextView) findViewById(R.id.PlayingGame_txtView_result);
		txtView_num_total = (TextView) findViewById(R.id.PlayingGame_txtView_num_total);
		txtView_num_mafia = (TextView) findViewById(R.id.PlayingGame_txtView_num_mafia);
		txtView_user_01 = (TextView) findViewById(R.id.PlayingGame_txtView_user01_name);
		txtView_user_02 = (TextView) findViewById(R.id.PlayingGame_txtView_user02_name);
		txtView_user_03 = (TextView) findViewById(R.id.PlayingGame_txtView_user03_name);
		txtView_user_04 = (TextView) findViewById(R.id.PlayingGame_txtView_user04_name);
		txtView_company = (TextView) findViewById(R.id.PlayingGame_txtView_company);

		txtView_user_content_01 = (TextView) findViewById(R.id.PlayingGame_txtView_user01_content);
		txtView_user_content_02 = (TextView) findViewById(R.id.PlayingGame_txtView_user02_content);
		txtView_user_content_03 = (TextView) findViewById(R.id.PlayingGame_txtView_user03_content);
		txtView_user_content_04 = (TextView) findViewById(R.id.PlayingGame_txtView_user04_content);

		radio_user_01 = (RadioButton) findViewById(R.id.PlayingGame_radio_user01);
		radio_user_02 = (RadioButton) findViewById(R.id.PlayingGame_radio_user02);
		radio_user_03 = (RadioButton) findViewById(R.id.PlayingGame_radio_user03);
		radio_user_04 = (RadioButton) findViewById(R.id.PlayingGame_radio_user04);

		btn_up = (Button) findViewById(R.id.PlayingGame_btn_up);
		btn_down = (Button) findViewById(R.id.PlayingGame_btn_down);
		btn_point = (Button) findViewById(R.id.PlayingGame_btn_point);

		btn_up.setOnClickListener(this);
		btn_down.setOnClickListener(this);
		btn_point.setOnClickListener(this);

		mHandler = new MsgHandler(Looper.getMainLooper());

		mSock = ClntSock.getInstance(Functions.IP, Functions.PORT);
		mSender = ClntSender.getInstance(mSock);
		mReciever = ClntReciever.getInstance(mSock);
		mReciever.setHandler(mHandler);

		Intent intent = getIntent();
		numUsers = Integer.parseInt(intent.getStringExtra("NUM_USERS"));
		txtView_name.setText(intent.getStringExtra("PLAYER_NAME"));
		txtView_num_total.setText("" + numUsers);

		String[] tmpStr = new String[numUsers];
		if (numUsers == 1) {
			tmpStr[0] = intent.getStringExtra("USER_1");
		} else if (numUsers == 2) {
			tmpStr[0] = intent.getStringExtra("USER_1");
			tmpStr[1] = intent.getStringExtra("USER_2");
		} else if (numUsers == 3) {
			tmpStr[0] = intent.getStringExtra("USER_1");
			tmpStr[1] = intent.getStringExtra("USER_2");
			tmpStr[2] = intent.getStringExtra("USER_3");
		} else if (numUsers == 4) {
			tmpStr[0] = intent.getStringExtra("USER_1");
			tmpStr[1] = intent.getStringExtra("USER_2");
			tmpStr[2] = intent.getStringExtra("USER_3");
			tmpStr[3] = intent.getStringExtra("USER_4");
		}

		allocateUsers(tmpStr);

		blockAllButton();

		RadioButton.OnClickListener radio_listener = new RadioButton.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				switch (v.getId()) {

				case R.id.PlayingGame_radio_user01:
					checkedRadio = 1;
					radio_user_02.setChecked(false);
					radio_user_03.setChecked(false);
					radio_user_04.setChecked(false);
					break;

				case R.id.PlayingGame_radio_user02:
					checkedRadio = 2;
					radio_user_01.setChecked(false);
					radio_user_03.setChecked(false);
					radio_user_04.setChecked(false);
					break;

				case R.id.PlayingGame_radio_user03:
					checkedRadio = 3;
					radio_user_02.setChecked(false);
					radio_user_01.setChecked(false);
					radio_user_04.setChecked(false);
					break;

				case R.id.PlayingGame_radio_user04:
					checkedRadio = 4;
					radio_user_02.setChecked(false);
					radio_user_03.setChecked(false);
					radio_user_01.setChecked(false);
					break;
				}
			}

		};

		radio_user_01.setOnClickListener(radio_listener);
		radio_user_02.setOnClickListener(radio_listener);
		radio_user_03.setOnClickListener(radio_listener);
		radio_user_04.setOnClickListener(radio_listener);

		dialog = new AlertDialog.Builder(Ac_PlayingGame.this);

		dialog.setMessage("게임이 종료되었습니다.")
				.setCancelable(false)
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								finish();
							}
						});

	}

	private void setGameState(String str) {
		txtView_game_state.setText(str);
	}

	private void blockAllButton() {
		btn_up.setEnabled(false);
		btn_down.setEnabled(false);
		btn_point.setEnabled(false);
	}

	private void setUpDownButton(boolean setValue) {
		btn_up.setEnabled(setValue);
		btn_down.setEnabled(setValue);
	}

	private void setPointButton(boolean setValue) {
		btn_point.setEnabled(setValue);
		Log.i("Playing Game", "set Point button" + setValue);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ac__connection, menu);
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

			String f06 = new String("06");
			String f07 = new String("07");
			String f09 = new String("09");
			String f10 = new String("10");
			String f12 = new String("12");
			String f14 = new String("14");
			String f15 = new String("15");
			String f16 = new String("16");
			String f18 = new String("18");
			String f19 = new String("19");
			String f21 = new String("21");
			String f22 = new String("22");

			Log.i("Ac_PlayingGame handler", "" + flag);

			if (Integer.parseInt(flag) == Integer.parseInt(f06)) {
				/**
				 * 상태 밤으로 전환, 모든 버튼 블럭
				 */
				setGameState("밤");
				setGameStateDay(NIGHT);
				blockAllButton();
			} else if (Integer.parseInt(flag) == Integer.parseInt(f07)) {
				/**
				 * 07xxx역할(mafia, citizen, police):마피아수 , 신분전환, 마피아수 업데이트
				 */
				String[] contentArr = Functions.extractMsgStringArray(
						msglength, msgContent);
				txtView_position.setText(contentArr[0]);
				txtView_num_mafia.setText(contentArr[1]);

			} else if (Integer.parseInt(flag) == Integer.parseInt(f09)) {
				/**
				 * 09xxx유저1 , 마피아에게만 마피아 정보 알림
				 */
				String[] contentArr = Functions.extractMsgStringArray(
						msglength, msgContent);

				Log.i("flag 09", "my position string : "
						+ txtView_position.getText().toString());
				if (txtView_position.getText().toString().equals(MAFIA)) {
					txtView_company.setText(contentArr[0]);
				}

			} else if (Integer.parseInt(flag) == Integer.parseInt(f10)) {
				/**
				 * 10 , 상태 - 낮 + 마피아 지목, 지목버튼 on, 나머지 블락.
				 */
				setGameState("낮: 마피아 지목");
				setGameStateDay(DAY);
				setPointButton(true);
				setUpDownButton(false);

			} else if (Integer.parseInt(flag) == Integer.parseInt(f12)) {
				/**
				 * 12xxx유저1>유저2:유저2>유저1:유저3>유저1:유저4>유저1:최다득표자 상태 ->Up down 투표 UP
				 * DOWN 버튼 ON 지목 버튼 OFF
				 */
				String[] contentArr = Functions.extractMsgStringArray(
						msglength, msgContent);
				txtView_user_content_01.setText(contentArr[0]);
				txtView_user_content_02.setText(contentArr[1]);
				txtView_user_content_03.setText(contentArr[2]);
				txtView_user_content_04.setText(contentArr[3]);
				txtView_result.setText("최다 득표자 : " + contentArr[4]);

				txtView_game_state.setText("UP/DOWN 투표");
				setUpDownButton(true);
				setPointButton(false);

			} else if (Integer.parseInt(flag) == Integer.parseInt(f14)) {
				/**
				 * 14xxx유저1>유저2:유저2>유저1:유저3>유저1:유저4>유저1:결과(Up/down중 다득표) 모든 버튼
				 * block
				 */
				String[] contentArr = Functions.extractMsgStringArray(
						msglength, msgContent);
				txtView_user_content_01.setText(contentArr[0]);
				txtView_user_content_02.setText(contentArr[1]);
				txtView_user_content_03.setText(contentArr[2]);
				txtView_user_content_04.setText(contentArr[3]);
				txtView_result.setText(contentArr[4] + "  voting WIN !");

				blockAllButton();

			} else if (Integer.parseInt(flag) == Integer.parseInt(f15)) {
				/**
				 * 15xxx죽은유저이름:인원수::마피아수
				 */
				String[] contentArr = Functions.extractMsgStringArray(
						msglength, msgContent);
				txtView_result.setText("죽은 유저 : " + contentArr[0]);
				txtView_num_total.setText(contentArr[1]);
				txtView_num_mafia.setText(contentArr[2]);

				if (txtView_name.getText().toString().equals(contentArr[0])) {

					dialog.show();
				}

			} else if (Integer.parseInt(flag) == Integer.parseInt(f16)) {
				/**
				 * 16000# 상태->밤
				 */
				setGameState("밤 : 경찰활동");
				setGameStateDay(NIGHT);
				txtView_result.setText("");
				if (txtView_position.getText().toString().equals(POLICE)) {
					setPointButton(true);
				}

			} else if (Integer.parseInt(flag) == Integer.parseInt(f18)) {
				/**
				 * 경찰 클라이언트 에게만, 18xxx유저1:신분(mafia, citizen, police)
				 */
				String[] contentArr = Functions.extractMsgStringArray(
						msglength, msgContent);
				Log.i("flag 18", "getText : "
						+ txtView_position.getText().toString()
						+ "// POLICE : " + POLICE);

				String tmpStr = new String("police");
				if (txtView_position.getText().toString().equals(POLICE)) {
					txtView_result.setText(contentArr[0] + " : "
							+ contentArr[1]);
				} else if (txtView_position.getText().toString().equals(tmpStr)) {
					txtView_result.setText(contentArr[0] + " : "
							+ contentArr[1]);

				}

			} else if (Integer.parseInt(flag) == Integer.parseInt(f19)) {
				/**
				 * 밤 마피아 활동 알림, 마피아만 지목버튼 ON
				 */
				setGameState("밤 : 마피아 타임");
				Log.i("flag 19", "getText : "
						+ txtView_position.getText().toString() + "// MAFIA : "
						+ MAFIA);
				String tmpStr = new String("mafia");
				if (txtView_position.getText().toString() == MAFIA) {
					setPointButton(true);
				} else if (txtView_position.getText().toString().equals(MAFIA)) {
					setPointButton(true);

				}

			} else if (Integer.parseInt(flag) == Integer.parseInt(f21)) {
				/**
				 * 21xxx인원수:유저1:유저2:마피아수
				 */
				String[] contentArr = Functions.extractMsgStringArray(
						msglength, msgContent);
				txtView_num_total.setText(contentArr[0]);
				txtView_user_01.setText(contentArr[1]);
				txtView_user_02.setText(contentArr[2]);
				txtView_num_mafia.setText(contentArr[3]);

			} else if (Integer.parseInt(flag) == Integer.parseInt(f22)) {
				/**
				 * 22000# : 게임 종료
				 */

				dialog.show();

			} else {
				Toast.makeText(getApplicationContext(),
						"등록되지 않은 flag : " + flag, Toast.LENGTH_LONG).show();
			}

		}

	}

	private void allocateUsers(String[] userArr) {

		numUsers = userArr.length;

		if (numUsers == 1) {
			txtView_user_01.setText(userArr[0]);
		} else if (numUsers == 2) {
			txtView_user_01.setText(userArr[0]);
			txtView_user_02.setText(userArr[1]);
		} else if (numUsers == 3) {
			txtView_user_01.setText(userArr[0]);
			txtView_user_02.setText(userArr[1]);
			txtView_user_03.setText(userArr[2]);
		} else if (numUsers == 4) {
			txtView_user_01.setText(userArr[0]);
			txtView_user_02.setText(userArr[1]);
			txtView_user_03.setText(userArr[2]);
			txtView_user_04.setText(userArr[3]);
		}

	}

}
