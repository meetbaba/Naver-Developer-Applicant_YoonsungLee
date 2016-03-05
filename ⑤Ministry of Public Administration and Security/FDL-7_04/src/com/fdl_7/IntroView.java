/**
 * ���۽� 1~2�ʰ� �ߴ� intro ȭ���� �����Ѵ�.
 * ȭ���� �׸��� �ٲٰ� ������ res ������ drawable ������ �ش� �̹����� �ְ�, 
 * activity_intro_view.xml����     android:background="@drawable/initial_page"  �� �κ��� �ٲپ��ָ� �ȴ�.
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
                // �ڷΰ��� ������� �ȳ������� �����ֱ� >> finish!!
                finish();
            }
        }, 2000); //�� ���� 2000�� ���ϴ� �ð���ŭ ������ �� �ִ�. ms����
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
