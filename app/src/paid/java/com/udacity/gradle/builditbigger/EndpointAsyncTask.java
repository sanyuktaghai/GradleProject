package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Pair;
import android.view.View;
import android.widget.ProgressBar;

import com.example.sanyukta.android_joke_library.JokeActivity;
import com.example.sanyukta.myapplication.backend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;
import java.lang.Override;
import java.lang.String;

/**
 * Created by sanyukta on 2/21/16.
 */
class EndpointAsyncTask extends AsyncTask<Pair<Context, String>, Void, String> {

    private static MyApi myApiService = null;
    private Context myContext;
    private String myResult;
    private ProgressBar myProgressBar;

    public EndpointAsyncTask(Context context, ProgressBar progressBar){
        myContext = context;
        myProgressBar = progressBar;
        MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                .setRootUrl("https://my-gradle-project.appspot.com/_ah/api/");
        myApiService = builder.build();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (myProgressBar != null) {
            myProgressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected String doInBackground(Pair<Context, String>... params) {
        try {
            return myApiService.tellMeAJoke().execute().getMyJoke();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if (myProgressBar != null) {
            myProgressBar.setVisibility(View.GONE);
        }
        myResult = result;
        startJokeActicity();
    }

    private void startJokeActicity() {
        Intent intent = new Intent(myContext, JokeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(JokeActivity.JOKE_KEY, myResult);
        myContext.startActivity(intent);
    }
}
