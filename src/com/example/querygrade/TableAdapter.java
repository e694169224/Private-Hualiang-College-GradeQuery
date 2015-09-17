package com.example.querygrade;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/***
 *自定义的适配器，用于在listView中显示成绩
 * @author yin
 *
 */
public class TableAdapter extends BaseAdapter{
	private List<Grade> list = new ArrayList<Grade>();
	private Context context;
	
	public TableAdapter(List<Grade> list, Context context) {
		this.list = list;
		this.context = context;
	}

	/**
	 * 获得list的大小，list有多大，适配器就会绘制几次
	 */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	/**
	 * 获得当前项的id
	 */
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
		Holder holder;
		
		if(convertView == null){
			convertView = LayoutInflater.from(context).inflate(R.layout.list_item, null);
			holder =  new Holder();
			holder.mCourseName = (TextView)convertView.findViewById(R.id.class_name);
			holder.mTeacher = (TextView)convertView.findViewById(R.id.teacher_name);
			holder.mUsuallyResults  = (TextView)convertView.findViewById(R.id.usually);
			holder.mFinalGrade = (TextView)convertView.findViewById(R.id.final1);
			holder.mSumGrade = (TextView)convertView.findViewById(R.id.sum);
			//holder.mGPA = (TextView)view.findViewById(R.id.gpa);
			holder.mCredit = (TextView)convertView.findViewById(R.id.credit_l);
			holder.mCreditGPA = (TextView)convertView.findViewById(R.id.credit_gpa_l);
			convertView.setTag(holder);
		}else{
			holder = (Holder) convertView.getTag();
		}
		Grade grade = (Grade) this.getItem(position);
		holder.mCourseName.setText(grade.getCourseName());
		holder.mTeacher.setText(grade.getTeacher());
		holder.mUsuallyResults.setText(grade.getUsuallyResults());
		holder.mFinalGrade.setText(grade.getFinalGrade());
		holder.mSumGrade.setText(grade.getSumGrade());
		//holder.mGPA.setText(grade.getGPA());
		holder.mCredit.setText(grade.getCredit());
		holder.mCreditGPA.setText(grade.getCreditGPA());
		return convertView;
	}
	
	final class Holder{
		private TextView mCourseName;
		private TextView mTeacher;
		private TextView mUsuallyResults;
		private TextView mFinalGrade;
		private TextView mSumGrade;
		private TextView mGPA;
		private TextView mCredit;
		private TextView mCreditGPA;
	}

}
