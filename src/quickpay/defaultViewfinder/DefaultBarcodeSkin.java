package quickpay.defaultViewfinder;

import mobi.pdf417.R;
import net.photopay.geometry.Point;
import net.photopay.view.viewfinder.AbstractViewFinder;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

// NOTE: this example uses resources from Pdf417MobiSdk library project
// basically this is the implementation of PDF417.mobi's default skin
public class DefaultBarcodeSkin extends AbstractViewFinder {
    
    /** This is a back button */
    private Button mBackButton = null;
    /** This is a torch button */
    private Button mTorchButton = null;
    /** This variable holds the torch state */
    private boolean mTorchEnabled = false;
    /** This varaiable holds the View that will be rotated with device */
    private ViewGroup mLayout = null;
    /** This variable holds the viewfinder View that draws the lines and PDF417.mobi logo */
    private DefaultBarcodeViewfinder mViewFinder = null;
    
    public DefaultBarcodeSkin(final Activity scanActivity, ViewGroup layout, boolean drawOverlay) {
        mLayout = layout;
        // create a new viewfinder
        mViewFinder = new DefaultBarcodeViewfinder(scanActivity, null);
        // define whether it should draw PDF417.mobi logo
        mViewFinder.setDrawOverlay(drawOverlay);
        // register viewfinder interface so viewfinder can contact us
        mViewFinder.setViewfinderInterface(this);
        
        // setup back button
        mBackButton = (Button) layout.findViewById(R.id.backButton);
        mBackButton.setText(scanActivity.getString(R.string.photopayHome));
        
        mBackButton.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                scanActivity.onBackPressed();
            }
        });
        
        // obtain a reference to torch button, but make it invisible
        // we will make it appear only if device supports torch control
        mTorchButton = (Button) layout.findViewById(R.id.torchButton);
        mTorchButton.setVisibility(View.GONE);
    }
    
    public void setupSkin() {
        // if device supports torch, make torch button visible and setup it
        // isCameraTorchSupported returns true if device supports controlling the torch
        if(isCameraTorchSupported()) {
            mTorchButton.setVisibility(View.VISIBLE);
            mTorchButton.setOnClickListener(new View.OnClickListener() {
                
                @Override
                public void onClick(View v) {
                    // setTorchEnabled returns true if torch turning off/on has succeeded
                    boolean success = setTorchEnabled(!mTorchEnabled);
                    if(success) {
                        mTorchEnabled = !mTorchEnabled;
                        if(mTorchEnabled) {
                            mTorchButton.setText(R.string.LightOn);
                        } else {
                            mTorchButton.setText(R.string.LightOff);
                        }
                    }
                }
            });
        }
    }
    
    @Override
    public void displayAutofocusFailed() {
        mViewFinder.displayAutofocusFailed();
    }

    @Override
    public void setDefaultTarget(int detectionStatus, boolean showProgress) {
        mViewFinder.setDefaultTarget();
        mViewFinder.publishDetectionStatus(detectionStatus, showProgress);
    }

    @Override
    public void setNewTarget(Point uleft, Point uright, Point lleft, Point lright, int uleftIndex,
            int detectionStatus, boolean showProgress) {
        mViewFinder.setNewTarget(uleft, uright, lleft, lright, uleftIndex);
        mViewFinder.publishDetectionStatus(detectionStatus, showProgress);
    }

    @Override
    public void setPointSet(float[] points, boolean biColorPointSet) {
        mViewFinder.setPointSet(points, biColorPointSet);
    }

    @Override
    public View getRotatableView() {
        return mLayout;
    }

    @Override
    public View getFixedView() {
        return mViewFinder;
    }

    @Override
    public boolean isAnimationInProgress() {
        return mViewFinder.isAnimationInProgress();
    }

}
