package quickpay.scan;

import mobi.pdf417.R;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class VerifyDataActivity extends Activity {

	EditText et1;
	EditText et2;
	EditText et3;
	EditText et4;
	EditText et5;
	EditText et6;
	EditText et7;
	TextView tv1;
    Button   bt1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_data);
        
        
        
        et1 =(EditText) findViewById(R.id.EditText01);
        et2 =(EditText) findViewById(R.id.EditText02);
        et3 =(EditText) findViewById(R.id.EditText03);
        et4 =(EditText) findViewById(R.id.EditText04);
        et5 =(EditText) findViewById(R.id.EditText05);
        et6 =(EditText) findViewById(R.id.EditText06);
        et7 =(EditText) findViewById(R.id.EditText07);
        tv1= (TextView)   findViewById(R.id.tvAmount);
        bt1 =(Button)  findViewById(R.id.button1);
        
        if(getIntent().getStringArrayExtra("required-Fields")!=null)
        {
        String[] requiredFields = getIntent().getStringArrayExtra("required-Fields");
         et1.setText(requiredFields[1]);
         et2.setText(requiredFields[0]);
         et3.setText(requiredFields[2]);
         et4.setText(requiredFields[3]);
         et5.setText(requiredFields[4]);
         et6.setText(requiredFields[5]);
         tv1.setText(requiredFields[6]);
        }
        else
        {
        	tv1.setText(getIntent().getExtras().getString("required-amount"));
        }
         bt1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				new AlertDialog.Builder(VerifyDataActivity.this)
			    .setTitle("Tax Receipt Issued")
			    .setMessage("The tax receipt will be emailed to " + et7.getText())
			    .setPositiveButton("Finish", new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int which) { 
			        	if(getIntent().getStringArrayExtra("required-Fields")!=null)
			            {
			            String[] requiredFields = getIntent().getStringArrayExtra("required-Fields");
			        	Intent intent = new Intent(getBaseContext(), GetStartedActivity.class);
		            	intent.putExtra("required-Fields", requiredFields);
		            	startActivity(intent);	
			            }
			        	else
			        	{
			        		String[] requiredFields={et1.getText().toString(),et2.getText().toString(),et3.getText().toString(),et4.getText().toString(),et5.getText().toString(),et6.getText().toString(),getIntent().getExtras().getString("required-amount")};
			        		Intent intent = new Intent(getBaseContext(), GetStartedActivity.class);
			            	intent.putExtra("required-Fields", requiredFields);
			            	startActivity(intent);
			        	}	}
			     })
			   /* .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int which) { 
			            // do nothing
			        }
			     })*/
			    .setIcon(android.R.drawable.ic_dialog_alert)
			     .show();
				
			}});



        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.verify_data, menu);
        return true;
    }
    
}
