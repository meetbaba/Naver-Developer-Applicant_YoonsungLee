/**
 * 시작시 1~2초간 뜨는 intro 화면을 설정한다.
 * 화면의 그림을 바꾸고 싶으면 res 폴더의 drawable 폴더에 해당 이미지를 넣고, 
 * activity_intro_view.xml에서     android:background="@drawable/initial_page"  이 부분을 바꾸어주면 된다.
 */

package com.fdl_7;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;


public class IntroView extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_view);
        
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                Intent intent = new Intent(IntroView.this, ControlView.class);
                startActivity(intent);
                // 뒤로가기 했을경우 안나오도록 없애주기 >> finish!!
                finish();
            }
        }, 2000); //이 곳의 2000에 원하는 시간만큼 수정할 수 있다. ms단위
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.intro_view, menu);
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
