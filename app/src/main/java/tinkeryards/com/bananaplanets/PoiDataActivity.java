package tinkeryards.com.bananaplanets;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Sanjeev on 10-07-2014.
 */
public class PoiDataActivity extends BananaPlanets{

    protected JSONArray poiData;
    protected boolean isLoading = false;
    static double[] latitudes = new double[10];
    static double[] longitudes = new double[10];
    static double[] altitudes = new double[10];
    static String[] models = new String[10];
    static String[] titles = new String[10];
    static String[] descriptions = new String[10];
    static String[] scales = new String[10];

    static int numplaces=3;
    boolean once=false;
    /** Called when the activity is first created. */

    @Override
    public void onCreate( final Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );


        this.locationListener = new LocationListener() {

            @Override
            public void onStatusChanged( String provider, int status, Bundle extras ) {
            }

            @Override
            public void onProviderEnabled( String provider ) {
            }

            @Override
            public void onProviderDisabled( String provider ) {
            }

            @Override
            public void onLocationChanged( final Location location ) {
                Log.d("***TAG", "onLocationChanged called");
                if(Constants.startLocation==null){
                    Log.d("***TAG","startLocation=null");
                    Constants.startLocation = location;
                    double myLatitude = location.getLatitude();
                    double myLongitude = location.getLongitude();
                    Constants.originLatLng = new LatLng(myLatitude,myLongitude);

                    LatLng planet1 = LatLngTool.travel(Constants.originLatLng, 0, 70, LengthUnit.METER);
                    latitudes[0] = planet1.getLatitude();
                    longitudes[0] = planet1.getLongitude();
                    models[0] = "assets/mars.png";
                    titles[0] = "Mars";
                    descriptions[0] = "Mars is the fourth planet from the Sun and the second smallest planet in the Solar System. It is often described as the \"Red Planet\", as the iron oxide prevalent on its surface gives it a reddish appearance. Mars is a terrestrial planet with a thin atmosphere, having surface features reminiscent both of the impact craters of the Moon and the volcanoes, valleys, deserts, and polar ice caps of Earth. The rotational period and seasonal cycles of Mars are likewise similar to those of Earth, as is the tilt that produces the seasons. Mars is the site of Olympus Mons, the second highest known mountain within the Solar System (the tallest on a planet), and of Valles Marineris, one of the largest canyons.Mars has two known moons, Phobos and Deimos, which are small and irregularly shaped. ";
                    scales[0] = "1";


                    LatLng planet2 = LatLngTool.travel(Constants.originLatLng, 90, 30, LengthUnit.METER);
                    latitudes[1] = planet2.getLatitude();
                    longitudes[1] = planet2.getLongitude();
                    models[1] = "assets/jupiter.png";
                    titles[1] = "Jupiter";
                    descriptions[1] = "Jupiter is the fifth planet from the Sun and the largest planet in the Solar System It is a gas giant with mass one-thousandth that of the Sun but is two and a half times the mass of all the other planets in the Solar System combined. Jupiter is classified as a gas giant along with Saturn, Uranus and Neptune. When viewed from Earth, Jupiter can reach an apparent magnitude of −2.94, making it on average the third-brightest object in the night sky after the Moon and Venus. Jupiter is primarily composed of hydrogen with a quarter of its mass being helium. It may also have a rocky core of heavier elements, but like the other gas giants, Jupiter lacks a well-defined solid surface. Because of its rapid rotation, the planet's shape is that of an oblate spheroid .The outer atmosphere is visibly segregated into several bands at different latitudes, resulting in turbulence and storms along their interacting boundaries. A prominent result is the Great Red Spot, a giant storm.   There are also at least 67 moons.Jupiter has been explored on several occasions by robotic spacecraft, most notably during the early Pioneer and Voyager flyby missions and later by the Galileo orbiter. The most recent probe to visit Jupiter was the Pluto-bound New Horizons spacecraft in late February 2007. ";
                    scales[1] = "3";


                    LatLng planet3 = LatLngTool.travel(Constants.originLatLng, 180, 50, LengthUnit.METER);
                    latitudes[2] = planet3.getLatitude();
                    longitudes[2] = planet3.getLongitude();
                    models[2] = "assets/saturn.png";
                    titles[2] = "Saturn";
                    descriptions[2] = "Saturn is the sixth planet from the Sun and the second largest planet in the Solar System, after Jupiter. Saturn is a gas giant with an average radius about nine times that of Earth. While only one-eighth the average density of Earth, with its larger volume Saturn is just over 95 times more massive than Earth. Saturn's interior is probably composed of a core of iron, nickel and rock (silicon and oxygen compounds), surrounded by a deep layer of metallic hydrogen, an intermediate layer of liquid hydrogen and liquid helium and an outer gaseous layer. The planet exhibits a pale yellow hue due to ammonia crystals in its upper atmosphere. Saturn has a prominent ring system that consists of nine continuous main rings and three discontinuous arcs, composed mostly of ice particles with a smaller amount of rocky debris and dust. Sixty-two known moons orbit the planet; fifty-three are officially named. This does not include the hundreds of \"moonlets\" within the rings. ";
                    scales[2] = "1.5";

                    LatLng planet4 = LatLngTool.travel(Constants.originLatLng, 135, 20, LengthUnit.METER);
                    latitudes[3] = planet4.getLatitude();
                    longitudes[3] = planet4.getLongitude();
                    models[3] = "assets/venus.png";
                    titles[3] = "Venus";
                    descriptions[3] = "Venus is the second planet from the Sun, orbiting it every 224.7 Earth days.It has no natural satellite. It is named after the Roman goddess of love and beauty..";
                    scales[3] = "0.001";

                    LatLng planet5 = LatLngTool.travel(Constants.originLatLng, 180, 60, LengthUnit.METER);
                    latitudes[4] = planet5.getLatitude();
                    longitudes[4] = planet5.getLongitude();
                    models[4] = "assets/alien.png";
                    titles[4] = "Alien";
                    descriptions[4] = "Dodge the alien attack to move ahead";
                    scales[4] = "0.001";

                    LatLng planet6 = LatLngTool.travel(Constants.originLatLng, 225, 80, LengthUnit.METER);
                    latitudes[5] = planet6.getLatitude();
                    longitudes[5] = planet6.getLongitude();
                    models[5] = "assets/spacecoin.png";
                    titles[5] = "Space Coin";
                    descriptions[5] = "This is a space coin. Collect to buy special powers";
                    scales[5] = "0.001";

                    LatLng planet7 = LatLngTool.travel(Constants.originLatLng, 270, 15, LengthUnit.METER);
                    latitudes[6] = planet7.getLatitude();
                    longitudes[6] = planet7.getLongitude();
                    models[6] = "assets/spaceship.png";
                    titles[6] = "Spaceship";
                    descriptions[6] = "Come on board to command this ship";
                    scales[6] = "0.001";


                    LatLng planet8 = LatLngTool.travel(Constants.originLatLng, 315, 90, LengthUnit.METER);
                    latitudes[7] = planet8.getLatitude();
                    longitudes[7] = planet8.getLongitude();
                    models[7] = "assets/spaceship2.png";
                    titles[7] = "Spaceship";
                    descriptions[7] = "Come on board to command this ship";
                    scales[7] = "0.001";

                }

                if (location!=null) {
                    PoiDataActivity.this.lastKnownLocation = location;
                    if ( PoiDataActivity.this.architectView != null ) {
                        if ( location.hasAltitude() && location.hasAccuracy() && location.getAccuracy()<7) {
                            PoiDataActivity.this.architectView.setLocation( location.getLatitude(), location.getLongitude(), location.getAltitude(), location.getAccuracy() );
                        } else {
                            PoiDataActivity.this.architectView.setLocation( location.getLatitude(), location.getLongitude(), location.hasAccuracy() ? location.getAccuracy() : 1000 );
                        }
                    }
                }
            }
        };

        this.architectView.registerSensorAccuracyChangeListener( this.sensorAccuracyListener );
        this.locationProvider = new LocationProvider( this, this.locationListener );
    }

    @Override
    protected void onPostCreate( final Bundle savedInstanceState ) {
        super.onPostCreate( savedInstanceState );
        this.loadData();

    }

    final Runnable loadData = new Runnable() {



        @Override
        public void run() {

            PoiDataActivity.this.isLoading = true;

            final int WAIT_FOR_LOCATION_STEP_MS = 2000;

            while (PoiDataActivity.this.lastKnownLocation==null && !PoiDataActivity.this.isFinishing()) {

                PoiDataActivity.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        Toast.makeText(PoiDataActivity.this, "zeroing on location", Toast.LENGTH_SHORT).show();
                    }
                });

                try {
                    Thread.sleep(WAIT_FOR_LOCATION_STEP_MS);
                } catch (InterruptedException e) {
                    break;
                }
            }

            if (PoiDataActivity.this.lastKnownLocation!=null && !PoiDataActivity.this.isFinishing()) {
                // TODO: you may replace this dummy implementation and instead load POI information e.g. from your database
                if(!once){
                    Log.d("***TAG","ONCE");
                    PoiDataActivity.this.poiData = PoiDataActivity.getPoiInformation(PoiDataActivity.this.lastKnownLocation, numplaces);
                    PoiDataActivity.this.callJavaScript("World.loadPoisFromJsonData", new String[] { PoiDataActivity.this.poiData.toString() });
                    once = true;
                }
            }

            PoiDataActivity.this.isLoading = false;
        }
    };

    protected void loadData() {
        if (!isLoading) {
            final Thread t = new Thread(loadData);
            t.start();
        }
    }

    /**
     * call JacaScript in architectView
     * @param methodName
     * @param arguments
     */
    private void callJavaScript(final String methodName, final String[] arguments) {
        final StringBuilder argumentsString = new StringBuilder("");
        for (int i= 0; i<arguments.length; i++) {
            argumentsString.append(arguments[i]);
            if (i<arguments.length-1) {
                argumentsString.append(", ");
            }
        }

        if (this.architectView!=null) {
            final String js = ( methodName + "( " + argumentsString.toString() + " );" );
            this.architectView.callJavascript(js);
        }
    }


    /**
     * loads poiInformation and returns them as JSONArray. Ensure attributeNames of JSON POIs are well known in JavaScript, so you can parse them easily
     * @param userLocation the location of the user
     * @param numberOfPlaces number of places to load (at max)
     * @return POI information in JSONArray
     */
    public static JSONArray getPoiInformation(final Location userLocation, final int numberOfPlaces) {

        if (userLocation==null) {
            return null;
        }

        final JSONArray pois = new JSONArray();

        // ensure these attributes are also used in JavaScript when extracting POI data
        final String ATTR_ID = "id";
        final String ATTR_NAME = "name";
        final String ATTR_DESCRIPTION = "description";
        final String ATTR_LATITUDE = "latitude";
        final String ATTR_LONGITUDE = "longitude";
        final String ATTR_ALTITUDE = "altitude";
        final String ATTR_MODEL = "model";
        final String ATTR_SCALE = "scale";
/*
        final HashMap<String, String> poiInformation = new HashMap<String, String>();
        poiInformation.put(ATTR_ID, String.valueOf(1));
        poiInformation.put(ATTR_NAME, "POI#" + 1);
        poiInformation.put(ATTR_DESCRIPTION, "This is the description of POI#" + 1);

        poiInformation.put(ATTR_LATITUDE, String.valueOf(Constants.latitude1));
        poiInformation.put(ATTR_LONGITUDE, String.valueOf(Constants.longitude1));
        final float UNKNOWN_ALTITUDE = -32768f;  // equals "AR.CONST.UNKNOWN_ALTITUDE" in JavaScript (compare AR.GeoLocation specification)
        // Use "AR.CONST.UNKNOWN_ALTITUDE" to tell ARchitect that altitude of places should be on user level. Be aware to handle altitude properly in locationManager in case you use valid POI altitude value (e.g. pass altitude only if GPS accuracy is <7m).
        poiInformation.put(ATTR_ALTITUDE, String.valueOf(UNKNOWN_ALTITUDE));
        pois.put(new JSONObject(poiInformation));

        final HashMap<String, String> poiInformation2 = new HashMap<String, String>();
        poiInformation2.put(ATTR_ID, String.valueOf(2));
        poiInformation2.put(ATTR_NAME, "POI#" + 2);
        poiInformation2.put(ATTR_DESCRIPTION, "This is the description of POI#" + 2);

        double[] poiLocationLatLon = getRandomLatLonNearby(userLocation.getLatitude(), userLocation.getLongitude());
        poiInformation.put(ATTR_LATITUDE, String.valueOf(poiLocationLatLon[0]));
        poiInformation.put(ATTR_LONGITUDE, String.valueOf(poiLocationLatLon[1]));;
        poiInformation2.put(ATTR_ALTITUDE, String.valueOf(UNKNOWN_ALTITUDE));


        pois.put(new JSONObject(poiInformation2));
*/

        for (int i=0;i < numberOfPlaces; i++) {
            final HashMap<String, String> poiInformation = new HashMap<String, String>();
            poiInformation.put(ATTR_ID, String.valueOf(i));
            poiInformation.put(ATTR_NAME, titles[i]);
            poiInformation.put(ATTR_DESCRIPTION, descriptions[i]);
            //double[] poiLocationLatLon = getRandomLatLonNearby(userLocation.getLatitude(), userLocation.getLongitude());
            poiInformation.put(ATTR_LATITUDE, String.valueOf(latitudes[i] ));
            poiInformation.put(ATTR_LONGITUDE, String.valueOf(longitudes[i]));
            final float UNKNOWN_ALTITUDE = -32768f;  // equals "AR.CONST.UNKNOWN_ALTITUDE" in JavaScript (compare AR.GeoLocation specification)
            // Use "AR.CONST.UNKNOWN_ALTITUDE" to tell ARchitect that altitude of places should be on user level. Be aware to handle altitude properly in locationManager in case you use valid POI altitude value (e.g. pass altitude only if GPS accuracy is <7m).
            poiInformation.put(ATTR_ALTITUDE, String.valueOf(UNKNOWN_ALTITUDE));
            poiInformation.put(ATTR_MODEL,models[i]);
            poiInformation.put(ATTR_SCALE,scales[i]);
            pois.put(new JSONObject(poiInformation));
        }

        return pois;
    }

    /**
     * helper for creation of dummy places.
     * @param lat center latitude
     * @param lon center longitude
     * @return lat/lon values in given position's vicinity
     */
    private static double[] getRandomLatLonNearby(final double lat, final double lon) {
        return new double[] { lat + Math.random()/5-0.1 , lon + Math.random()/5-0.1};
    }


}
