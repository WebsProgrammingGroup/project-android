package com.example.lastactivity;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class WEBS_Board_Adapter extends BaseAdapter{
	Context ctx;
	int layout;
	ArrayList<WEBS_Board_DTO> list;
	LayoutInflater inf;
	public WEBS_Board_Adapter(Context ctx,int layout,ArrayList<WEBS_Board_DTO> list){
		this.ctx=ctx;
		this.layout=layout;
		this.list=list;
		inf=(LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(convertView ==null){
			convertView=inf.inflate(layout, null);
		}
		TextView category =(TextView)convertView.findViewById(R.id.webs_board_list_item);
		WEBS_Board_DTO dto=list.get(position);
		category.setText(dto.getCategory());
		return convertView;
	}
	
}
