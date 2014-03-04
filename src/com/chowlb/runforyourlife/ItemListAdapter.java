package com.chowlb.runforyourlife;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

public class ItemListAdapter extends BaseAdapter {

	private List<Item> result;
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
			
			Item item = result.get(position);
			
		holder.item.setText(item.getName());
		holder.status.setText(item.getStatus());
		holder.status.setTextColor(item.getStatusColor(this.context));
		Log.e("chowlb", "Item IMAGE: " + item.getImage()+ "_item_img");
		holder.img.setImageResource(context.getResources().getIdentifier(item.getImage()+ "_item_img", "drawable", context.getPackageName()));	
		
		return rowView;
	}
	
//	public Filter getFilter() {
//		return mFilter;
//	}
//	
//	private class ItemFilter extends Filter {
//		@Override
//		protected FilterResults performFiltering(CharSequence constraint) {
//			
//			String filterString = constraint.toString().toLowerCase();
//			//Log.e("chowlb", "FilterString: '"+filterString+"'");
//			FilterResults results = new FilterResults();
//			
//			final List<Item> list = result;
// 
//			int count = list.size();
//			
//			//Log.e("chowlb", "Lis Size in filter: " + count);
//			
//			final ArrayList<RssItem> nlist = new ArrayList<RssItem>(count);
// 
//			String filterableString ;
//			
//			for (int i = 0; i < count; i++) {
//				filterableString = list.get(i).getDescription().toString();
//				if (filterableString.toLowerCase().contains(filterString)) {
//					nlist.add(list.get(i));
//				}
//			}
//			
//			results.values = nlist;
//			results.count = nlist.size();
// 
//			return results;
//		}
// 
//		@SuppressWarnings("unchecked")
//		@Override
//		protected void publishResults(CharSequence constraint, FilterResults results) {
//			filteredData = (ArrayList<RssItem>) results.values;
//			notifyDataSetChanged();
//		}
// 
//	}

}
