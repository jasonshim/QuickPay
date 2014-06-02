package quickpay.scan;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.math.BigDecimal;

import mobi.pdf417.R;

import org.json.JSONException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.paypal.android.sdk.payments.PayPalAuthorization;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalFuturePaymentActivity;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
public class FrontPageActivity extends Activity {

	
	private static final String TAG = "paymentExample";
    private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_NO_NETWORK;
    private static final String CONFIG_CLIENT_ID = "AfDPAxBrPL7C1xvJKu1uhV2OLPoZvhcRhFGu7yN6zRVK7KBZUjO6TYEH2NMK";

    private static final int REQUEST_CODE_PAYMENT = 1;
    
    private static PayPalConfiguration config = new PayPalConfiguration()
    .environment(CONFIG_ENVIRONMENT)
    .clientId(CONFIG_CLIENT_ID);
    
    EditText et1;
    Button bt1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_front_page);
		Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        startService(intent);
		 bt1 =(Button) findViewById(R.id.ButtonConDonate);
		 et1=(EditText) findViewById(R.id.editTextDonate);
		bt1.setOnClickListener(new View.OnClickListener()
		{ 
		 @Override
		 public void onClick(View v)
		 {
			
		        PayPalPayment thingToBuy = getThingToBuy(PayPalPayment.PAYMENT_INTENT_SALE);

		        Intent intent = new Intent(FrontPageActivity.this, PaymentActivity.class);

		        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);

		        startActivityForResult(intent, REQUEST_CODE_PAYMENT);
		 }
		});
	}
	
    private PayPalPayment getThingToBuy(String paymentIntent) {
    	
    	//value of the donation
    	 String amount =et1.getText().toString();
        return new PayPalPayment(new BigDecimal(amount), "CAD", "Donation Amount",
                paymentIntent);
    }
	
	
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                PaymentConfirmation confirm =
                        data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirm != null) {
                    try {
                        Log.i(TAG, confirm.toJSONObject().toString(4));
                        Log.i(TAG, confirm.getPayment().toJSONObject().toString(4));
                       
                        Toast.makeText(
                                getApplicationContext(),
                                "PaymentConfirmation info received from PayPal", Toast.LENGTH_LONG)
                                .show();
                        Intent intent = new Intent(FrontPageActivity.this,Pdf417CustomUIDemo.class);
                        String amount =et1.getText().toString();
                        intent.putExtra("Donated-amount", amount);
                        startActivity(intent);

                    } catch (JSONException e) {
                        Log.e(TAG, "an extremely unlikely failure occurred: ", e);
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.i(TAG, "The user canceled.");
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                Log.i(TAG,"An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
            }
        } 
    }
    
   
	
	
	public void onClick(View v) {
        int id = v.getId();
        switch (id) {
        case R.id.Button100D:{
        	et1.setText("100");
        	break;
        }
        case R.id.button250D:{
        	et1.setText("250");

        	break;
        }
        case R.id.Button500D:{
        	et1.setText("500");

        	break;
        }
        case R.id.Button1000D:{
        	et1.setText("1000");

        	break;
        }
        case R.id.Button2500D:{
        	et1.setText("2500");

        	break;
        }
        case R.id.Button5000D:{
        	et1.setText("5000");

        	break;
        }
        case R.id.Button10kD:{
        	et1.setText("10000");

        	break;
        }
      
        }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.front_page, menu);
		return true;
	}
	
	@Override
    public void onDestroy() {
        // Stop service when done
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }

}
