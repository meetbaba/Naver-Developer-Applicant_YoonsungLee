/**
 * release 0.1  : 프로토타입 (Prototype)
 * 
 * 버전 완성 날짜 : 2015-06-12
 * 
 * 개발자 : 이윤성 (Yoonsung Lee)
 *  
 *  본 프로그램의 저작권은 개발자에게 있으며 개발자의 허락없이 무단 복제, 공개, 배포를 불허합니다.
 * 
 * 
 * 관련 사항 : 
 * 아주대학교 시스템공학 수업  프로젝트
 * 담당교수 : 이성주
 * 관계업체 : MSP (수원 소재지)
 * 
 * 
 * 프로그램 설명 :
 * 
 * 본 프로그램은 아주대학교 2015년도 1학기 시스템 공학 수업에서 진행된 최종 프로젝트 과정에서 만들어졌다.
 * 본 프로그램 MSP_Manager는 회사 생산 라인 운영/관리 사항을 모두 수기로 기록 후 엑셀로 따로 저장하는 MSP의 시스템을 개선하기위해 만들어졌다.
 * 이 프로그램을 MSP_Manager를 통해서 관리자 직책의 관계자는 손쉽게 작업량을 관리할 수 있고, 통계 정보를 제공받아 이전부다 편리하게 
 * 작업을 관리하고 목표달성을 위한 전략수립의 용이성, 그리고 리스크 상황에 대한 즉각적인 대처를 도울 수 있다.
 * 
 * 
 */
package com.msp_manager;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

/**
 * @author 윤성
 *
 */

public class CustomAdapterDaily extends BaseAdapter {
     
    // 문자열을 보관 할 ArrayList
    private ArrayList<ItemContentDaily>   m_List;
    private int m_currentItem=0; 
    // 생성자
    public CustomAdapterDaily() {
        m_List = new ArrayList<ItemContentDaily>();
    }
 
    // 현재 아이템의 수를 리턴
    @Override
    public int getCount() {
        return m_List.size();
    }
 
    // 현재 아이템의 오브젝트를 리턴, Object를 상황에 맞게 변경하거나 리턴받은 오브젝트를 캐스팅해서 사용
    @Override
    public Object getItem(int position) {
        return m_List.get(position);
    }
 
    // 아이템 position의 ID 값 리턴
    @Override
    public long getItemId(int position) {
        return position;
    }
 
    // 출력 될 아이템 관리
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();
        TextView        name    = null;
        TextView        success     = null;
        TextView 		fail =null;
        CustomHolder    holder  = null;
         
        // 리스트가 길어지면서 현재 화면에 보이지 않는 아이템은 converView가 null인 상태로 들어 옴
        if ( convertView == null ) {
            // view가 null일 경우 커스텀 레이아웃을 얻어 옴
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_item_daily, parent, false);
             
            name    = (TextView) convertView.findViewById(R.id.customItem_Daily_name);
            success     = (TextView) convertView.findViewById(R.id.customItem_Daily_success);
            fail		=	(TextView) convertView.findViewById(R.id.customItem_Daily_fail);
            
            
     
            // 홀더 생성 및 Tag로 등록
            holder = new CustomHolder();
            holder.mTextView_name   = name;
            holder.mTextView_success = success;
            holder.mTextView_fail	= name;
            convertView.setTag(holder);
        }
        else {
            holder  = (CustomHolder) convertView.getTag();
            name    = holder.mTextView_name;
            success    = holder.mTextView_success;
            fail 	=	holder.mTextView_fail;
        }
         
        ItemContentDaily tmpContent=(ItemContentDaily)m_List.get(position);
        // Text 등록
        name.setText(tmpContent.getItem_value_name());
        Log.i("Daily Adpater Class", "set Text name ("+position+") : "+name.getText().toString());
        success.setText(tmpContent.getItem_value_success());
        Log.i("Daily Adpater Class", "set Text name ("+position+") : "+success.getText().toString());
        fail.setText(tmpContent.getItem_value_fail());
        Log.i("Daily Adpater Class", "set Text name ("+position+") : "+fail.getText().toString());
        
        
        //editText.addTextChangedListener(new CustomTextWatcher(m_List, position));
        // 리스트 아이템을 터치 했을 때 이벤트 발생
        convertView.setOnClickListener(new OnClickListener() {
             
            @Override
            public void onClick(View v) {
                // 터치 시 해당 아이템 이름 출력
            }
        });
         
        // 리스트 아이템을 길게 터치 했을 떄 이벤트 발생
        convertView.setOnLongClickListener(new OnLongClickListener() {
             
            @Override
            public boolean onLongClick(View v) {
                // 터치 시 해당 아이템 이름 출력
                return true;
            }
        });
         
        return convertView;
    }
     
    // 외부에서 아이템 추가 요청 시 사용
    public void add(ItemContentDaily _item) {
        m_List.add(_item);
        m_currentItem++;
    }
     
    // 외부에서 아이템 삭제 요청 시 사용
    public void remove(int _position) {
        m_List.remove(_position);
    }
    
    public int getCurItemPosition(){
    	return this.m_currentItem;
    }

    

private class CustomHolder {
    TextView    mTextView_name;
    TextView	mTextView_success;
    TextView	mTextView_fail;
}

	
}