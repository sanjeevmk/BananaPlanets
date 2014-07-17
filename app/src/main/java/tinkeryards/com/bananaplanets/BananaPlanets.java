package tinkeryards.com.bananaplanets;

import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.widget.Toast;

import com.wikitude.architect.ArchitectView;

/**
 * Created by Sanjeev on 10-07-2014.
 */
public class BananaPlanets extends AbstractARActivity {

    /**
     * last time the calibration toast was shown, this avoids too many toast shown when compass needs calibration
     */
    private long lastCalibrationToastShownTimeMillis = System.currentTimeMillis();



    @Override
    public String getActivityTitle() {
        return "Banana Planets";
    }

    @Override
    public String getARchitectWorldPath() {
        return "index.html";
    }

    @Override
    public ArchitectView.ArchitectUrlListener getUrlListener() {
        return new ArchitectView.ArchitectUrlListener() {

            @Override
            public boolean urlWasInvoked(String uriString) {
                // by default: no action applied when url was invoked
                return false;
            }
        };
    }

    @Override
    public int getContentViewId() {
        return R.layout.banana_planets_view;
    }

    @Override
    public String getWikitudeSDKLicenseKey() {
        return WikitudeSDKConstants.WIKITUDE_SDK_KEY;
    }

    @Override
    public int getArchitectViewId() {
        return R.id.architectView;
    }

    @Override
    public ILocationProvider getLocationProvider(LocationListener locationListener) {
        return new LocationProvider(this,locationListener);
    }

    @Override
    public ArchitectView.SensorAccuracyChangeListener getSensorAccuracyListener() {
        return new ArchitectView.SensorAccuracyChangeListener() {
            @Override
            public void onCompassAccuracyChanged( int accuracy ) {
				/* UNRELIABLE = 0, LOW = 1, MEDIUM = 2, HIGH = 3 */
                if ( accuracy < SensorManager.SENSOR_STATUS_ACCURACY_MEDIUM && BananaPlanets.this != null && !BananaPlanets.this.isFinishing() && System.currentTimeMillis() - BananaPlanets.this.lastCalibrationToastShownTimeMillis > 5 * 1000) {
                    Toast.makeText(BananaPlanets.this, R.string.compass_accuracy_low, Toast.LENGTH_LONG).show();
                    BananaPlanets.this.lastCalibrationToastShownTimeMillis = System.currentTimeMillis();
                }
            }
        };
    }

    @Override
    public float getInitialCullingDistanceMeters() {
        // you need to adjust this in case your POIs are more than 50km away from user here while loading or in JS code (compare 'AR.context.scene.cullingDistance')
        return ArchitectViewHolderInterface.CULLING_DISTANCE_DEFAULT_METERS;
    }
}
