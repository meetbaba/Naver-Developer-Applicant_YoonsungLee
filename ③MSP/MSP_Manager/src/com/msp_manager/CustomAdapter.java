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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

/**
 * @author ����
 *
 */

public class CustomAdapter extends BaseAdapter {
     
    // ���ڿ��� ���� �� ArrayList
    private ArrayList<ItemContent>   m_List;
    private int m_currentItem=0; 
    // ������
    public CustomAdapter() {
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
        TextView        text    = null;
        EditText        editText     = null;
        TextView 		unit =null;
        CustomTextWatcher watcher =null;
        CustomHolder    holder  = null;
         
        // ����Ʈ�� ������鼭 ���� ȭ�鿡 ������ �ʴ� �������� converView�� null�� ���·� ��� ��
        if ( convertView == null ) {
            // view�� null�� ��� Ŀ���� ���̾ƿ��� ��� ��
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_item, parent, false);
             
            text    = (TextView) convertView.findViewById(R.id.customItem_textView);
            editText     = (EditText) convertView.findViewById(R.id.customItem_editText);
            unit		=	(TextView) convertView.findViewById(R.id.customItem_unit);
            watcher= new CustomTextWatcher(m_List, position);
            
            
     
            // Ȧ�� ���� �� Tag�� ���
            holder = new CustomHolder();
            holder.m_TextView   = text;
            holder.m_EditText = editText;
            holder.m_unit	= unit;
            holder.m_Watcher = watcher;
            holder.m_EditText.addTextChangedListener(holder.m_Watcher);
            convertView.setTag(holder);
        }
        else {
            holder  = (CustomHolder) convertView.getTag();
            text    = holder.m_TextView;
            editText    = holder.m_EditText;
            unit 	=	holder.m_unit;
            watcher = holder.m_Watcher;
        }
         
        // Text ���
        text.setText(m_List.get(position).getItem_value_textView());
        editText.setText(m_List.get(position).getItem_value_editText());
        unit.setText(m_List.get(position).getItem_value_unit());
        
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
    TextView    m_TextView;
    EditText	m_EditText;
    TextView	m_unit;
    CustomTextWatcher m_Watcher;
}

	private class CustomTextWatcher implements TextWatcher{
		ArrayList<ItemContent> mmArrayList;
		int position;
		
		public CustomTextWatcher(ArrayList<ItemContent> mmArrayList, int position) {
			// TODO Auto-generated constructor stub
			this.mmArrayList=mmArrayList;
			this.position=position;
		}
		
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
			String editValue=s.toString();
			
			Log.i("TextChaged", "Changed : "+s.toString());
			
			if(editValue.length()==0){
				m_List.get(position).setItem_value_editText("0");
			}else{
				m_List.get(position).setItem_value_editText(editValue);
			}
		}
		
	}
}