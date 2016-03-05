/**
 * <pre>
 * 1. 프로젝트명 : excavateTerm
 * 2. 패키지명(또는 디렉토리 경로) : com.excavateterm
 * 3. 파일명 : CustomAdapter_Sixth.java
 * 4. 작성일 : 2015. 8. 9. 오전 7:16:08
 * 5. 작성자 : 윤성
 * 6. 설명 :
 * </pre>
 */
package com.excavateterm;

import java.util.ArrayList;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 개발자 : 윤성
 * 작성일 : 2015. 8. 9. 오전 7:16:08
 * 설명 : 
 */


public class CustomAdapter_Sixth extends BaseAdapter {
     
	private static final String TAG="CustomAdapter_Sixhth";

    // 문자열을 보관 할 ArrayList
    private ArrayList<ItemContent>   m_List;
    private int m_currentItem=0; 
    // 생성자
    public CustomAdapter_Sixth() {
        m_List = new ArrayList<ItemContent>();
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

        TextView text = null;
        Handler handler=null;
        //CustomOnClickListener onClickListner = new CustomOnClickListener(position, context);
        CustomHolder holder=null;
         
        // 리스트가 길어지면서 현재 화면에 보이지 않는 아이템은 converView가 null인 상태로 들어 옴
        if ( convertView == null ) {
            // view가 null일 경우 커스텀 레이아웃을 얻어 옴
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_sixth, parent, false);
             
            text    = (TextView) convertView.findViewById(R.id.sixth_custom_item_textview);
            text.setClickable(true);
     
            // 홀더 생성 및 Tag로 등록
            holder = new CustomHolder();
            holder.mTextView   = text;
            holder.mHandler = m_List.get(pos).getHandler();
            //holder.m_onClickListener=onClickListner;
            text.setOnClickListener(holder.m_onClickListener);

            convertView.setTag(holder);
        }
        else {
            holder  = (CustomHolder) convertView.getTag();
            text    = holder.mTextView;
            handler = holder.mHandler;
            //onClickListner =holder.m_onClickListener;
            //text.setOnClickListener(holder.m_onClickListener);
            

        }
         
        // Textview 배경 설정
        ItemContent tmpItem=(ItemContent)m_List.get(position);
        if(tmpItem.getStatus()==StaticStrings.STATUS_PRE){
        	text.setBackgroundResource(R.drawable.sixth_improvement_textview_pre);
        }else if(tmpItem.getStatus()==StaticStrings.STATUS_CHOICE){
        	text.setBackgroundResource(R.drawable.sixth_improvement_textview_choice);

        }else if(tmpItem.getStatus()==StaticStrings.STATUS_COMPLETE){
        	text.setBackgroundResource(R.drawable.sixth_improvement_textview_complete);
        	text.setClickable(false);
        }
        text.setText(tmpItem.getText());

        final CustomOnClickListener listener=new CustomOnClickListener(pos, context);
        holder.mTextView.setOnClickListener(listener);
        
        Log.i(TAG, "GET VIEW :: position : "+position+"// view:"+convertView.getId());
    	
        
        //editText.addTextChangedListener(new CustomTextWatcher(m_List, position));
        // 리스트 아이템을 터치 했을 때 이벤트 발생
         
        //리스트 아이템을 길게 터치했을때 이벤트 발생 
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
    public void add(ItemContent _item) {
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
    TextView mTextView;
    Handler mHandler;
    CustomOnClickListener m_onClickListener;
}


public class CustomOnClickListener implements OnClickListener {
    
	ItemContent tmpItem;
	int flag;
	String[] content;
	
	int pos_onclick;
	Context context_onclick;
	
	public CustomOnClickListener(int pos, Context context){
		this.pos_onclick=pos;
		this.context_onclick=context;
	}
	
    @Override
    public void onClick(View v) {
    	/**
    	 * zzxxx클릭된 리스트이름:position
    	 */
    	Log.i(TAG, "Item Clicked position : "+this.pos_onclick);
    	tmpItem=m_List.get(this.pos_onclick);

    	Handler tmpHandler=tmpItem.getHandler();
    	
    	flag=StaticStrings.FLAG_SIX_CLICK;
		
    	Message msg=Message.obtain(tmpHandler, flag, pos_onclick);
		tmpHandler.sendMessage(msg);
		
    }
}

}