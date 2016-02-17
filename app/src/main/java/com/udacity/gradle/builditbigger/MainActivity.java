package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.sanyukta.android_joke_library.JokeActivity;
import com.example.sanyukta.myapplication.backend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;


/**
 * Created by sanyukta on 2/11/16.
 */
class EndpointAsyncTask extends AsyncTask<Pair<Context, String>, Void, String> {

    private static MyApi myApiService = null;
    private Context context;

    public EndpointAsyncTask(){
//        MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
//                new AndroidJsonFactory(), null)
//                // options for running against local devappserver
//                // - 10.0.2.2 is localhost's IP address in Android emulator
//                // - turn off compression when running against local devappserver
//                .setRootUrl("http://10.0.2.2:8080/_ah/api/")
//                .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
//                    @Override
//                    public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
//                        abstractGoogleClientRequest.setDisableGZipContent(true);
//                    }
//                });
//        // end options for devappserver
        MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                .setRootUrl("https://my-gradle-project.appspot.com/_ah/api/");
        myApiService = builder.build();
    }

    @Override
    protected String doInBackground(Pair<Context, String>... params) {
        context = params[0].first;
//        String name = params[0].second;

        try {
            return myApiService.tellMeAJoke().execute().getMyJoke();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
//        Toast.makeText(context, result, Toast.LENGTH_LONG).show();
        super.onPostExecute(result);
        Intent intent = new Intent(context, JokeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(JokeActivity.JOKE_KEY, result);
        context.startActivity(intent);
    }
}

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void tellJoke(View view){
        new EndpointAsyncTask().execute(new Pair<Context, String>(this, "Sanyukta"));
    }

}
