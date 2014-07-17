package tinkeryards.com.bananaplanets;

import android.util.Log;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sanjeev on 10-07-2014.
 */
public class Planet {

    double distance, angle, bearingAngle, height;
    LatLng pLatLng;
    public Planet(){
        this.distance = distance;
        this.angle = angle;
        this.bearingAngle = 90 - angle;
        this.height = height;
        this.pLatLng = LatLngTool.travel(Constants.originLatLng,this.bearingAngle,this.distance, LengthUnit.METER);

        try {

            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://www.sanjeevmk.in/bananaplanets/populateplanets.php");

            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);

            nameValuePairs.add(new BasicNameValuePair("id1","1"));
            nameValuePairs.add(new BasicNameValuePair("latitude1",Double.toString(Constants.latitude1) ));
            nameValuePairs.add(new BasicNameValuePair("longitude1",Double.toString(Constants.longitude1) ));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpClient.execute(httppost);

            //BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            /*StringBuffer sb = new StringBuffer("");
            String line = "";

            while((line = in.readLine())!=null){
                sb.append(line);
            }
            */
            //in.close();

            Log.d("***TAG","Planet Created");

        }catch(Exception e){

        }
    }
}
