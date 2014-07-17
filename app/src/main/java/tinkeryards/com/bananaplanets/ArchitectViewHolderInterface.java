package tinkeryards.com.bananaplanets;

import android.location.LocationListener;

import com.wikitude.architect.ArchitectView.ArchitectUrlListener;
import com.wikitude.architect.ArchitectView.SensorAccuracyChangeListener;

/**
 * Created by Sanjeev on 09-07-2014.
 */
public interface ArchitectViewHolderInterface {

    // architectView's default culling distance . return this in getInitialCullingDistanceMeters()
    public static final int CULLING_DISTANCE_DEFAULT_METERS = 50*1000;

    // return path to the ARchitect world file, aka the index.html of the world, in the assets folder
    public String getARchitectWorldPath();

    // url listener fired once e.g. 'document.location = "architectsdk://foo?bar=123"' is called in JS
    public ArchitectUrlListener getUrlListener();

    // return layout id of the 'layout.xml' that contains the architectView
    public int getContentViewId();

    // Wikitude SDK license key, checkout www.wikitude.com for details
    public String getWikitudeSDKLicenseKey();

    // return layout id of the architectView
    public int getArchitectViewId();

    // implementation of a Location
    public ILocationProvider getLocationProvider(LocationListener locationListener);


    // implement your own location service that handles gps positions
    public static interface ILocationProvider {

        /**
         * Call when host-activity is resumed (usually within systems life-cycle method)
         */
        public void onResume();

        /**
         * Call when host-activity is paused (usually within systems life-cycle method)
         */
        public void onPause();

    }

    public SensorAccuracyChangeListener getSensorAccuracyListener();

    public float getInitialCullingDistanceMeters();
}
