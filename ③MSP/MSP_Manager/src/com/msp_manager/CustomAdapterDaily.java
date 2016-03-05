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
 * @author ����
 *
 */

public class CustomAdapterDaily extends BaseAdapter {
     
    // ���ڿ��� ���� �� ArrayList
    private ArrayList<ItemContentDaily>   m_List;
    private int m_currentItem=0; 
    // ������
    public CustomAdapterDaily() {
        m_List = new ArrayList<ItemContentDaily>();
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
        TextView        success     = null;
        TextView 		fail =null;
        CustomHolder    holder  = null;
         
        // ����Ʈ�� ������鼭 ���� ȭ�鿡 ������ �ʴ� �������� converView�� null�� ���·� ��� ��
        if ( convertView == null ) {
            // view�� null�� ��� Ŀ���� ���̾ƿ��� ��� ��
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_item_daily, parent, false);
             
            name    = (TextView) convertView.findViewById(R.id.customItem_Daily_name);
            success     = (TextView) convertView.findViewById(R.id.customItem_Daily_success);
            fail		=	(TextView) convertView.findViewById(R.id.customItem_Daily_fail);
            
            
     
            // Ȧ�� ���� �� Tag�� ���
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
        // Text ���
        name.setText(tmpContent.getItem_value_name());
        Log.i("Daily Adpater Class", "set Text name ("+position+") : "+name.getText().toString());
        success.setText(tmpContent.getItem_value_success());
        Log.i("Daily Adpater Class", "set Text name ("+position+") : "+success.getText().toString());
        fail.setText(tmpContent.getItem_value_fail());
        Log.i("Daily Adpater Class", "set Text name ("+position+") : "+fail.getText().toString());
        
        
        //editText.addTextChangedListener(new CustomTextWatcher(m_List, position));
        // ����Ʈ �������� ��ġ ���� �� �̺�Ʈ �߻�
        convertView.setOnClickListener(new OnClickListener() {
             
            @Override
            public void onClick(View v) {
                // ��ġ �� �ش� ������ �̸� ���
            }
        });
         
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
    public void add(ItemContentDaily _item) {
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
    TextView    mTextView_name;
    TextView	mTextView_success;
    TextView	mTextView_fail;
}

	
}