package quickpay.scan;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.SimpleAdapter;

public class ListDonorActivity extends ListActivity {
	ArrayList<Map<String, String>> list;
	HashMap<String, String> item;
	String[] requiredFields;
	String Name;
	String Amount;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 list= buildData();
			String[] from = { "name", "purpose" };
		    int[] to = { android.R.id.text1, android.R.id.text2 };

		    SimpleAdapter adapter = new SimpleAdapter(this, list,
		        android.R.layout.simple_list_item_2, from, to);
		    setListAdapter(adapter);
		  }
	
	 private ArrayList<Map<String, String>> buildData() {
		     list = new ArrayList<Map<String, String>>();
		     requiredFields=getIntent().getStringArrayExtra("required-Fields");
		     Name=requiredFields[0]+ "\n"+requiredFields[1];
		     Amount=requiredFields[6];
		    list.add(putData(Name, Amount));
		    
		    return list;
		  }
	
	 private HashMap<String, String> putData(String name, String purpose) {
		    item = new HashMap<String, String>();
		    item.put("name", name);
		    item.put("purpose", purpose);
		    return item;
		  }
	
	}

		


