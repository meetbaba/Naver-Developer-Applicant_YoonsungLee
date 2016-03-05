/**
 * <pre>
 * 1. ������Ʈ�� : excavateTerm
 * 2. ��Ű����(�Ǵ� ���丮 ���) : com.excavateterm
 * 3. ���ϸ� : CustomAdapter_Sixth.java
 * 4. �ۼ��� : 2015. 8. 9. ���� 7:16:08
 * 5. �ۼ��� : ����
 * 6. ���� :
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
 * ������ : ����
 * �ۼ��� : 2015. 8. 9. ���� 7:16:08
 * ���� : 
 */


public class CustomAdapter_Sixth extends BaseAdapter {
     
	private static final String TAG="CustomAdapter_Sixhth";

    // ���ڿ��� ���� �� ArrayList
    private ArrayList<ItemContent>   m_List;
    private int m_currentItem=0; 
    // ������
    public CustomAdapter_Sixth() {
        m_List = new ArrayList<ItemContent>();
    }
 
    // ���� �������� ���� ����
    @Override
    public int getCount() {
        return m_List.size();
    }
 
    // ���� �������� ������Ʈ�� ����, Object�� ��Ȳ�� �°� �����ϰų� ���Ϲ��� ������Ʈ�� ĳ�����ؼ� ���
    @Override
    public Object getItem(int position) {
        return m_List.get(position);
    }
 
    // ������ position�� ID �� ����
    @Override
    public long getItemId(int position) {
        return position;
    }
 
    // ��� �� ������ ����
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
    	
    	
        final int pos = position;
        final Context context = parent.getContext();

        TextView text = null;
        Handler handler=null;
        //CustomOnClickListener onClickListner = new CustomOnClickListener(position, context);
        CustomHolder holder=null;
         
        // ����Ʈ�� ������鼭 ���� ȭ�鿡 ������ �ʴ� �������� converView�� null�� ���·� ��� ��
        if ( convertView == null ) {
            // view�� null�� ��� Ŀ���� ���̾ƿ��� ��� ��
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_sixth, parent, false);
             
            text    = (TextView) convertView.findViewById(R.id.sixth_custom_item_textview);
            text.setClickable(true);
     
            // Ȧ�� ���� �� Tag�� ���
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
         
        // Textview ��� ����
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
        // ����Ʈ �������� ��ġ ���� �� �̺�Ʈ �߻�
         
        //����Ʈ �������� ��� ��ġ������ �̺�Ʈ �߻� 
        convertView.setOnLongClickListener(new OnLongClickListener() {
             
            @Override
            public boolean onLongClick(View v) {
                // ��ġ �� �ش� ������ �̸� ���
                return true;
            }
        });
         
        return convertView;
    }
     
    // �ܺο��� ������ �߰� ��û �� ���
    public void add(ItemContent _item) {
        m_List.add(_item);
        
        m_currentItem++;
    }
     
    // �ܺο��� ������ ���� ��û �� ���
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
    	 * zzxxxŬ���� ����Ʈ�̸�:position
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