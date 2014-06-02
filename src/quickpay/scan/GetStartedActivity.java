package quickpay.scan;

import mobi.pdf417.R;
import mobi.pdf417.activity.Pdf417ScanActivity;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class GetStartedActivity extends Activity {
    Button btDonate;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_get_started);

		if(getIntent().getStringArrayExtra("required-Fields")!=null){
			btDonate=(Button) findViewById(R.id.buttonDonateList);
        	btDonate.setVisibility(View.VISIBLE);
    	}
	}
	
	public void onClick(View v) {
        int id = v.getId();
        switch (id) {
        case R.id.buttonGetStarted:{
        	
        	Intent intent = new Intent(this, FrontPageActivity.class);
        	startActivity(intent);
        	break;
        }
        }
	}
	
	public void onDonateList(View v) {
        int id = v.getId();
        switch (id) {
        case R.id.buttonDonateList:{
        	Intent intent = new Intent(this, ListDonorActivity.class);
        	String[] requiredFields=getIntent().getStringArrayExtra("required-Fields");
        	intent.putExtra("required-Fields", requiredFields);
        	startActivity(intent);
        	break;
        }
        }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.get_started, menu);
		return true;
	}

}
