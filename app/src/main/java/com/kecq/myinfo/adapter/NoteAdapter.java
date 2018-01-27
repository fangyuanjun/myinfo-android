package com.kecq.myinfo.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;



/*
 * SimpleAdapter listItemAdapter = new SimpleAdapter(
 NoteActivity.this, data,
 R.layout.activity_note_item, 
 new String[] {"noteContent", "noteLocation","noteDatetime" },
 new int[] {R.id.item_content, R.id.item_location,
 R.id.item_date });
 */
public class NoteAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private List<Map<String, Object>> list;

	public NoteAdapter(Context context, List<Map<String, Object>> list) {
		super();
		if (list == null) {
			list = new ArrayList<Map<String, Object>>();
		}
		this.list = list;
		this.inflater = LayoutInflater.from(context);
	}
	
	public NoteAdapter(LayoutInflater inflater, List<Map<String, Object>> list) {
		super();
		if (list == null) {
			list = new ArrayList<Map<String, Object>>();
		}
		this.list = list;
		this.inflater = inflater;
	}
	
	public int getCount() {
		// TODO Auto-generated method stub
		if(list==null)
			return 0;
		
		return list.size();  
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
  /*
		ViewHolder holder=null;
		if (convertView == null) {

			if(position==0)
			{
				convertView = inflater.inflate(R.layout.head, null);
			}
			else
			if(position==getCount()-1)
			{
				convertView = inflater.inflate(R.layout.footer, null);
			}
			else
			{

			convertView = inflater.inflate(R.layout.activity_note_item, null);
		    holder=new ViewHolder(); 
		    holder.item_content=(TextView)convertView.findViewById(R.id.item_content);
		    holder.item_location=(TextView)convertView.findViewById(R.id.item_location);
		    holder.item_date=(TextView)convertView.findViewById(R.id.item_date);
		    convertView.setTag(holder);
			}
		}
		else
		{
			holder=(ViewHolder)convertView.getTag();
		}

		String location="来自"+list.get(position).get("noteDevice").toString()+" "
				+list.get(position).get("noteFrom").toString()+" "
				+list.get(position).get("noteLocation").toString();

		Map<String,Object> map=list.get(position);
		String location=map.get("noteLocation").toString().replace("中国", "").replace("邮政编码:", "").replaceAll("\\d{6}", "");
		holder.item_content.setText((String)(map.get("noteText")));
		holder.item_location.setText(location);
		holder.item_date.setText((String)(map.get("UPDATE_DATE")));
		return convertView;
holder.item_content.setText(list.get(position).get("cateDisplay").toString().replace("&nbsp;", " "));
		*/

  return null;
	}

	private final class ViewHolder {
		private TextView item_content;
		private TextView item_location;
		private TextView item_date;
	}
	
	public void InsertAtFirst(List<Map<String, Object>> data)
	{
		list.addAll(0, data);
	}
	
	public void InsertAtLast(List<Map<String, Object>> data)
	{
		list.addAll(data);
	}
	
	//设置数据
	public void SetData(List<Map<String, Object>> list)
	{
		this.list=list;
	}

	public void remove(int position)
	{
		this.list.remove(position);
	}
	
	public String GetJson()
	{
	 // return JsonHelper.ListMapToJson(list);

		return "";

	}
}
