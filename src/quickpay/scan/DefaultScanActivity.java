package quickpay.scan;

import java.util.ArrayList;

import quickpay.defaultViewfinder.DefaultBarcodeSkin;

import mobi.pdf417.Pdf417MobiScanData;
import mobi.pdf417.R;
import net.photopay.base.BaseBarcodeActivity;
import net.photopay.hardware.orientation.Orientation;
import net.photopay.view.viewfinder.AbstractViewFinder;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

public class DefaultScanActivity extends BaseBarcodeActivity {

    private int mScanCount = 0;
    private Handler mHandler = new Handler();
    private DefaultBarcodeSkin mSkin;
    public static final String TAG = "DefaultScanActivity.java";
    /**
     * Callback which is called when user clicks the back button.
     * 
     * @param view
     *            Reference to view that was clicked.
     */
    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED, null);
        finish();
    }

    @Override
    protected boolean isOrientationAllowed(Orientation orientation) {
        return true;
    }

    @Override
    protected AbstractViewFinder onCreateViewFinder() {
        LayoutInflater inflater = getLayoutInflater();

        ViewGroup defaultBarcodeLayout =
            (ViewGroup) inflater.inflate(R.layout.default_barcode_camera_overlay, null);
        mSkin = new DefaultBarcodeSkin(this, defaultBarcodeLayout, true);
        return mSkin;
    }
    
    @Override
    protected void onSetupViewFinder(AbstractViewFinder viewfinder) {
        super.onSetupViewFinder(viewfinder);
        mSkin.setupSkin();
    }
    
    /**
     * this activity will perform 5 scans of barcode and then return the last
     * scanned one
     */
    @Override
    protected void onScanningDone(ArrayList<Pdf417MobiScanData> scanDataList) {
        mScanCount++;
        StringBuilder sb = new StringBuilder();
        sb.append("Scanned ");
        sb.append(mScanCount);
        switch (mScanCount) {
        case 1:
            sb.append("st");
            break;
        case 2:
            sb.append("nd");
            break;
        case 3:
            sb.append("rd");
            break;
        default:
            sb.append("th");
            break;
        }
        sb.append(" barcode!");
        
        Log.i(TAG, sb.toString());
        
        Toast.makeText(this, sb.toString(), Toast.LENGTH_SHORT).show();
        if (mScanCount >= 5) {
            Intent intent = new Intent();
            intent.putExtra(BaseBarcodeActivity.EXTRAS_RESULT, scanDataList.get(0));
            intent.putParcelableArrayListExtra(BaseBarcodeActivity.EXTRAS_RESULT_LIST, scanDataList);
            setResult(BaseBarcodeActivity.RESULT_OK, intent);
            finish();
        } else {
            mHandler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    resumeScanning();
                }
            }, 2000);
        }
    }

}
