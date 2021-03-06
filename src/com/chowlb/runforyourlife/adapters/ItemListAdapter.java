package com.chowlb.runforyourlife.adapters;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.chowlb.runforyourlife.R;
import com.chowlb.runforyourlife.objects.Item;

public class ItemListAdapter extends BaseAdapter {

	private List<Item> result;
	private SparseArray<Item> checkedResultHashMap = new SparseArray<Item>();
	ArrayList<Integer> hlPositions = new ArrayList<Integer>();
	Context context;
	int[] imageId;
	private static LayoutInflater inflater=null;
	//private ItemFilter mFilter = new ItemFilter();
	
	
	public ItemListAdapter(Context mainActivity, List<Item> list) {
		result = list;
		context=mainActivity;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getCount() {
		return result.size();
	}

	@Override
	public Object getItem(int position) {
		return result.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	public class Holder{
		TextView item;
		TextView status;
		ImageView img;
		CheckBox checkBox;
	}
	
	public void addHighlight(int guid) {
		hlPositions.add(guid);
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		Holder holder = new Holder();
		View rowView;
		rowView = inflater.inflate(R.layout.item_list_row, null);
		holder.item=(TextView) rowView.findViewById(R.id.itemName);
		holder.status=(TextView) rowView.findViewById(R.id.item_Status);
		holder.img=(ImageView) rowView.findViewById(R.id.list_image);
		holder.checkBox=(CheckBox) rowView.findViewById(R.id.listRowCheckBox);
		
		Item item = result.get(position);
		//Log.e("chowlb", "ITEM ID: " + item.getItemId());
		holder.item.setText(item.getName());
		holder.status.setText(item.getStatus());
		holder.status.setTextColor(item.getStatusColor(this.context));
		holder.img.setImageResource(context.getResources().getIdentifier(item.getImage(), "drawable", context.getPackageName()));	
	
		holder.checkBox.setTag(item);
		holder.checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton checkBoxView, boolean isChecked) {
				Item item = (Item) checkBoxView.getTag();
				
				//Log.e("chowlb", "IS CHECKED: " + isChecked);
				if(isChecked) {
					//Log.e("chowlb", "IS CHECKED ITEMDBID: " + item.getItemDBID());
					checkedResultHashMap.put(item.getItemDBID(), item);
				}else {
					if(checkedResultHashMap.get(item.getItemDBID()) != null) {
						checkedResultHashMap.remove(item.getItemDBID());
					}
				}
				
			}
		});
		
		return rowView;
	}
	
	
	public SparseArray<Item> getCheckedItems() {
		return checkedResultHashMap;
    }
	


}
