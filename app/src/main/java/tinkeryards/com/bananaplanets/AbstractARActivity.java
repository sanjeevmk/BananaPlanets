package tinkeryards.com.bananaplanets;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.location.Location;
import android.location.LocationListener;
import android.opengl.GLES20;
import android.os.Bundle;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;
import com.wikitude.architect.ArchitectView;
import com.wikitude.architect.ArchitectView.SensorAccuracyChangeListener;
import com.wikitude.architect.ArchitectView.ArchitectUrlListener;
import com.wikitude.architect.ArchitectView.ArchitectConfig;

import java.io.IOException;

/**
 * Created by Sanjeev on 09-07-2014.
 */
public abstract class AbstractARActivity extends Activity implements ArchitectViewHolderInterface {

    // holds the wikitude sdk AR-View. This is where camera, markers etc are all rendered
    protected ArchitectView architectView;

    // in case you want to display calibration hints
    protected SensorAccuracyChangeListener sensorAccuracyListener;

    // last known location of User. Used internally for content loading after user location is fetched
    protected Location lastKnownLocation;

    // simple location strategy
    protected ILocationProvider locationProvider;

    // location listener receives location udpates and must forward them to architectView
    protected LocationListener locationListener;

    // urlListener handling "document.location= 'architectsdk://...' " calls in JavaScript"
    protected ArchitectUrlListener urlListener;

    @SuppressLint("NewApi")
    @Override

    public void onCreate( final Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        // getContentViewId() should be implemented in the class that extends this abstract
        this.setContentView(this.getContentViewId());

        // getTitle should be implemented in extending class
        this.setTitle(this.getActivityTitle());

        // set AR-View for Lifecycle notifications
        this.architectView = (ArchitectView)this.findViewById(this.getArchitectViewId());

        // pass SDK key if you have one
        final ArchitectConfig config = new ArchitectConfig(this.getWikitudeSDKLicenseKey());

        // first mandatory life cycle notification
        this.architectView.onCreate(config);

        // set accuracy listener if implemented eg. to show calibration prompt
        this.sensorAccuracyListener = this.getSensorAccuracyListener();

        // set the url listener for file calls made in JS files
        this.urlListener = this.getUrlListener();

        // register the listener before any content is loaded so as not to miss any event
        if(this.urlListener!=null){
            this.architectView.registerUrlListener(this.getUrlListener());
        }

        // listener passed over to locationProvider. Any location updates  handled here.
        /*this.locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(final Location location) {

                if(Constants.startLocation==null){
                    Constants.startLocation = location;
                    double myLatitude = location.getLatitude();
                    double myLongitude = location.getLongitude();
                    Constants.originLatLng = new LatLng(myLatitude,myLongitude);

                    LatLng planet1 = LatLngTool.travel(Constants.originLatLng,0,10, LengthUnit.METER);
                    Constants.latitude1 = planet1.getLatitude();
                    Constants.longitude1 = planet1.getLongitude();

                    Planet p1 = new Planet();

                }
                // forward location updates fired by locationProvider to architectView, you can set lat/long from any location strategy
                if(location!=null){
                    // backup last location, just in case.
                    AbstractARActivity.this.lastKnownLocation = location;

                    if(AbstractARActivity.this.architectView!=null){
                        // check if location has altitude at certain accuracy level & call right architect method (the one with altitude information)
                        if ( location.hasAltitude() && location.hasAccuracy() && location.getAccuracy()<7) {
                            AbstractARActivity.this.architectView.setLocation( location.getLatitude(), location.getLongitude(), location.getAltitude(), location.getAccuracy() );
                        } else {
                            AbstractARActivity.this.architectView.setLocation( location.getLatitude(), location.getLongitude(), location.hasAccuracy() ? location.getAccuracy() : 1000 );
                        }
                    }
                }
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };*/

        //this.locationProvider = this.getLocationProvider(this.locationListener);
    }

    @Override
    protected void onPostCreate( final Bundle savedInstanceState ) {
        super.onPostCreate( savedInstanceState );

        if ( this.architectView != null ) {

            // call mandatory live-cycle method of architectView
            this.architectView.onPostCreate();

            try {
                // load content via url in architectView, ensure '<script src="architect://architect.js"></script>' is part of this HTML file, have a look at wikitude.com's developer section for API references
                this.architectView.load( this.getARchitectWorldPath() );

                if (this.getInitialCullingDistanceMeters() != ArchitectViewHolderInterface.CULLING_DISTANCE_DEFAULT_METERS) {
                    // set the culling distance - meaning: the maximum distance to render geo-content
                    this.architectView.setCullingDistance( this.getInitialCullingDistanceMeters() );
                }

            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // call mandatory live-cycle method of architectView
        if ( this.architectView != null ) {
            this.architectView.onResume();

            // register accuracy listener in architectView, if set
            if (this.sensorAccuracyListener!=null) {
                this.architectView.registerSensorAccuracyChangeListener( this.sensorAccuracyListener );
            }
        }

        // tell locationProvider to resume, usually location is then (again) fetched, so the GPS indicator appears in status bar
        if ( this.locationProvider != null ) {
            this.locationProvider.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        // call mandatory live-cycle method of architectView
        if ( this.architectView != null ) {
            this.architectView.onPause();

            // unregister accuracy listener in architectView, if set
            if ( this.sensorAccuracyListener != null ) {
                this.architectView.unregisterSensorAccuracyChangeListener( this.sensorAccuracyListener );
            }
        }

        // tell locationProvider to pause, usually location is then no longer fetched, so the GPS indicator disappears in status bar
        if ( this.locationProvider != null ) {
            this.locationProvider.onPause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // call mandatory live-cycle method of architectView
        if ( this.architectView != null ) {
            this.architectView.onDestroy();
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if ( this.architectView != null ) {
            this.architectView.onLowMemory();
        }
    }

    /**
     * title shown in activity
     * @return
     */
    public abstract String getActivityTitle();

    /**
     * path to the architect-file (AR-Experience HTML) to launch
     * @return
     */
    @Override
    public abstract String getARchitectWorldPath();

    /**
     * url listener fired once e.g. 'document.location = "architectsdk://foo?bar=123"' is called in JS
     * @return
     */
    @Override
    public abstract ArchitectUrlListener getUrlListener();

    /**
     * @return layout id of your layout.xml that holds an ARchitect View, e.g. R.layout.camview
     */
    @Override
    public abstract int getContentViewId();

    /**
     * @return Wikitude SDK license key, checkout www.wikitude.com for details
     */
    @Override
    public abstract String getWikitudeSDKLicenseKey();

    /**
     * @return layout-id of architectView, e.g. R.id.architectView
     */
    @Override
    public abstract int getArchitectViewId();

    /**
     *
     * @return Implementation of a Location
     */
    @Override
    public abstract ILocationProvider getLocationProvider(final LocationListener locationListener);


    public abstract ArchitectView.SensorAccuracyChangeListener getSensorAccuracyListener();

    /**
     * helper to check if video-drawables are supported by this device. recommended to check before launching ARchitect Worlds with videodrawables
     * @return true if AR.VideoDrawables are supported, false if fallback rendering would apply (= show video fullscreen)
     */
    public static final boolean isVideoDrawablesSupported() {
        String extensions = GLES20.glGetString(GLES20.GL_EXTENSIONS);
        return extensions != null && extensions.contains( "GL_OES_EGL_image_external" ) && android.os.Build.VERSION.SDK_INT >= 14 ;
    }

}
