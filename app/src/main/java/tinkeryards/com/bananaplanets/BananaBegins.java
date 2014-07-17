package tinkeryards.com.bananaplanets;

import android.app.Activity;
import android.content.Intent;
import android.location.Criteria;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class BananaBegins extends Activity {

    BananaBegins instance;

    LocationManager locationManager;
    boolean isGpsEnabled;
    Criteria crit = new Criteria();
    Button start;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banana_begins);
        instance = this;

        start = (Button)findViewById(R.id.StartButton);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    final Intent bananaPlanetsIntent = new Intent(instance, PoiDataActivity.class);
                    instance.startActivity(bananaPlanetsIntent);
                }catch(Exception e){
            /*
			 * may never occur, as long as all SampleActivities exist and are
			 * listed in manifest
			 */

                }
            }
        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.banana_begins, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
