package org.dyndns.dalance.statuslogger;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class TagDialog extends DialogFragment {
	private static final String TAG = TagDialog.class.getSimpleName();
	public  static final String TITLE = "title";
	public  static final String LISTENER = "listener";
	
	public interface SelectListner {
		public void onSelected(String str);
	}
	
	private static String title;
	static TagDialog.SelectListner listener;
	static ListView listView;
	static List<String> strList;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (savedInstanceState==null){
			title = getArguments().getString(TITLE);
			strList = new ArrayList<String>();
			for(String str : LoggerFormatter.KEYWORD) {
				strList.add(str);
			}
		}
	}
	
	@Override
	public Dialog onCreateDialog(final Bundle savedInstanceState) {
		LinearLayout layout = new LinearLayout(getActivity());
		layout.setOrientation(LinearLayout.VERTICAL);

		listView = new ListView(getActivity());
		listView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
		listView.setAdapter(new ArrayAdapter<String>(getActivity(), 0, LoggerFormatter.KEYWORD){
			LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
			@Override
			public View getView(int position,View convertView,ViewGroup parent){
				View view = convertView==null ? inflater.inflate(R.layout.tag_dialog_row, null) : convertView;
				TextView tv = (TextView)view.findViewById(R.id.textView);
	            String str = LoggerFormatter.KEYWORD[position];
	            tv.setText(str);
	            return view;
	         }
		});
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id){
				BaseAdapter adapter = (BaseAdapter)parent.getAdapter();
				String str = (String)adapter.getItem(position);
				listener.onSelected(str);
				dismiss();
			}
		});
		
		layout.addView(listView);
		listener = (SelectListner)getActivity();
		return new AlertDialog.Builder(getActivity())
			.setTitle(title)
			.setView(layout)
			.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
				@Override
				public void onClick(DialogInterface dialog, int which){
				}
			}).create();
	}
}
