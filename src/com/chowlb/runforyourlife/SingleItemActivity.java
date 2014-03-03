package com.chowlb.runforyourlife;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.ListView;
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
			
			Log.e("chowlb", "Item Passed: " + item.getItemId());
			ActionBar ab = getActionBar();
			ab.setIcon(R.drawable.ic_action_military_backpack_radio_256);
			ab.setTitle("");
			ab.setTitle(item.getName());
			
			TextView itemName = (TextView) findViewById(R.id.singleItemName);
			itemName.setText("");
			itemName.setText(item.getName());
			
			TextView itemStatus = (TextView) findViewById(R.id.singleItemStatus);
			itemStatus.setText("");
			itemStatus.setText(item.getStatus());
			
			ImageView itemImage = (ImageView) findViewById(R.id.itemBigImage);
			itemImage.setImageDrawable(getResources().getDrawable(R.drawable.driedbeef_item_img));
			
		}
		else{
			Toast.makeText(this, "Error retrieving inventory.",  Toast.LENGTH_LONG).show();
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.single_item, menu);
		return true;
	}

}
