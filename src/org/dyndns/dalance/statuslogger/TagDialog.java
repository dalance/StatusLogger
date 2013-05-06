package org.dyndns.dalance.statuslogger;

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
	@SuppressWarnings("unused")
	private static final String TAG = TagDialog.class.getSimpleName();

	public  static final String TITLE = "title";
	public  static final String LISTENER = "listener";
	
	public interface SelectListner {
		public void onSelected(String str);
	}
	
	private static String title;
	static TagDialog.SelectListner listener;
	static ListView listView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (savedInstanceState==null){
			title = getArguments().getString(TITLE);
		}
	}
	
	@Override
	public Dialog onCreateDialog(final Bundle savedInstanceState) {
		LinearLayout layout = new LinearLayout(getActivity());
		layout.setOrientation(LinearLayout.VERTICAL);

		String[] definedTags = getResources().getStringArray(R.array.defined_tags);
		listView = new ListView(getActivity());
		listView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
		listView.setAdapter(new ArrayAdapter<String>(getActivity(), 0, definedTags){
			LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			String[] definedTags    = getResources().getStringArray(R.array.defined_tags);
			String[] tagDescription = getResources().getStringArray(R.array.tag_description);
				
			@Override
			public View getView(int position,View convertView,ViewGroup parent){
				View view = convertView==null ? inflater.inflate(R.layout.tag_dialog_row, null) : convertView;
				TextView view1 = (TextView)view.findViewById(R.id.textViewTag);
				view1.setText(definedTags[position]);
				TextView view2 = (TextView)view.findViewById(R.id.textViewDescription);
				view2.setText(tagDescription[position]);
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
