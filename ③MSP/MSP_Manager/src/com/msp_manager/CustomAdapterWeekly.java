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
import android.widget.Toast;

/**
 * @author ����
 *
 */

public class CustomAdapterWeekly extends BaseAdapter {
     
    // ���ڿ��� ���� �� ArrayList
    private ArrayList<ItemContentWeekly>   m_List;
    private int m_currentItem=0; 
    // ������
    public CustomAdapterWeekly() {
        m_List = new ArrayList<ItemContentWeekly>();
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
        TextView        name    = null;
        Handler        handle     = null;
        CustomOnClickListener onClickListner = null;
        CustomHolder    holder  = null;
         
        ItemContentWeekly tmpItem;
        // ����Ʈ�� ������鼭 ���� ȭ�鿡 ������ �ʴ� �������� converView�� null�� ���·� ��� ��
        if ( convertView == null ) {
            // view�� null�� ��� Ŀ���� ���̾ƿ��� ��� ��
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_item_weekly, parent, false);
             
            name    = (TextView) convertView.findViewById(R.id.customItem_weekly_textView);
     
            // Ȧ�� ���� �� Tag�� ���
            tmpItem=m_List.get(pos);
            holder = new CustomHolder();
            holder.m_Name   = name;
            holder.m_Handler = tmpItem.getItem_value_handler();
            holder.m_onClickListener=new CustomOnClickListener(pos, context);
            convertView.setTag(holder);
        }
        else {
            holder  = (CustomHolder) convertView.getTag();
            name    = holder.m_Name;
            handle    = holder.m_Handler;
            onClickListner =holder.m_onClickListener;
        }
         
        // Text ���
        name.setText(m_List.get(position).getItem_value_name());
        
        //editText.addTextChangedListener(new CustomTextWatcher(m_List, position));
        // ����Ʈ �������� ��ġ ���� �� �̺�Ʈ �߻�
        convertView.setOnClickListener(holder.m_onClickListener);
         
        // ����Ʈ �������� ��� ��ġ ���� �� �̺�Ʈ �߻�
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
    public void add(ItemContentWeekly _item) {
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
    TextView    m_Name;
    Handler		m_Handler;
    CustomOnClickListener m_onClickListener;
}


public class CustomOnClickListener implements OnClickListener {
    
	ItemContentWeekly tmpItem;
	String flag;
	String[] content;
	int pos_onclick;
	Context context_onclick;
	
	public CustomOnClickListener(int pos, Context context){
		this.pos_onclick=pos;
		this.context_onclick=context;
	}
	
    @Override
    public void onClick(View v) {
        // ��ġ �� �ش� ������ �̸� ���
    	
    	/**
    	 * zzxxxŬ���� ����Ʈ�̸�:position
    	 */
    	tmpItem=m_List.get(this.pos_onclick);
    	Handler tmpHandler=tmpItem.getItem_value_handler();
    	
    	
    	Log.i("Weekly msg making", "Clicked");
		
    	
    	flag="zz";
		
		content=new String[2];
		content[0]=new String(""+tmpItem.getItem_value_name());
		content[1]=new String(""+this.pos_onclick);
		byte[] msgByte=Functions.makeMsgFromAndroid(flag, content);
    	
    	
    	
    	Message msg=Message.obtain(tmpHandler, msgByte.length, msgByte);
		
		tmpHandler.sendMessage(msg);
		
		Toast.makeText(context_onclick, "��� : "+tmpItem.getItem_value_name()+"("+pos_onclick+")" , Toast.LENGTH_LONG).show();
    }
}
}