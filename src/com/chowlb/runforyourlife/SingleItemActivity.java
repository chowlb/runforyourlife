package com.chowlb.runforyourlife;

import com.chowlb.runforyourlife.objects.Item;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SingleItemActivity extends Activity {
	Item item;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_single_item);
		
		Bundle extras = getIntent().getExtras();
		if(extras != null) {
			item = extras.getParcelable("ITEM");
			int type = extras.getInt("TYPE");
			
			ActionBar ab = getActionBar();
			if(type == 0) {
				ab.setIcon(R.drawable.ic_action_ic_action_military_backpack_radio);
			}else if(type==1){
				ab.setIcon(R.drawable.ic_crate);
			}else if(type == 2){
				ab.setIcon(R.drawable.briefcase_drop_personal_img);
			}else {
				ab.setIcon(R.drawable.briefcase_drop_img);
			}
				
			
			ab.setTitle("");
			ab.setTitle(item.getName());
			
			TextView itemName = (TextView) findViewById(R.id.singleItemName);
			itemName.setText("");
			itemName.setText(item.getName());
			
			TextView itemStatus = (TextView) findViewById(R.id.singleItemStatus);
			itemStatus.setText("");
			itemStatus.setText(item.getStatus());
			itemStatus.setTextColor(item.getStatusColor(this));
			
			TextView itemDesc = (TextView) findViewById(R.id.singleItemDescription);
			itemDesc.setText("");
			itemDesc.setText(item.getDescription());
			
			TextView itemRarity = (TextView) findViewById(R.id.singleItemRarity);
			itemRarity.setText("");
			itemRarity.setText(item.getRarity());
			
			ImageView itemImage = (ImageView) findViewById(R.id.itemBigImage);
			
			itemImage.setImageResource(getResources().getIdentifier(item.getImage(), "drawable", getPackageName()));
			
			
		}
		else{
			Toast.makeText(this, "Error retrieving inventory.",  Toast.LENGTH_LONG).show();
		}
		
	}
}
