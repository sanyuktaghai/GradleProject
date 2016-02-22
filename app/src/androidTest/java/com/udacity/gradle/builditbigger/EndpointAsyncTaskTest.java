package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.test.AndroidTestCase;
import android.util.Log;
import android.util.Pair;

/**
 * Created by sanyukta on 2/16/16.
 */
public class EndpointAsyncTaskTest extends AndroidTestCase {

    private static final String LOG_TAG = EndpointAsyncTask.class.getSimpleName();
    @SuppressWarnings("unchecked")
    public void testEndpointAsyncTask() {

        // Testing that Async task successfully retrieves a non-empty string
        // You can test this by right click on androidTest -> Run 'Tests' in ... in an emulator
        String result = null;
        EndpointAsyncTask endpointAsyncTask = new EndpointAsyncTask(getContext(),null);
        endpointAsyncTask.execute();
        //endpointAsyncTask.execute(new Pair<Context, String>(getContext(), "Sanyukta"));
        try {
            result = endpointAsyncTask.get();
            Log.d(LOG_TAG, "Retrieved a non-empty string successfully: " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertNotNull(result);
    }
}
