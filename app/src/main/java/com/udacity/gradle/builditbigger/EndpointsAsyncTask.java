package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.goldencrow.android.jokepresenter.JokeActivity;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;
import java.lang.ref.WeakReference;

/**
 * AsyncTask Test Things from:
 * http://marksunghunpark.blogspot.co.at/2015/05/how-to-test-asynctask-in-android.html
 */
public class EndpointsAsyncTask extends AsyncTask<String, Void, String> {

    private static MyApi myApiService = null;

    /**
     * Uses a weakContextReference, so that there is no MemoryLeak.
     */
    private WeakReference<Context> mContextRef;

    /**
     * The category of the joke.
     */
    private String category;

    /**
     * Listens for the completion of the asyncTask.
     * Is used by the Tests.
     */
    private EndpointsTaskListener mListener = null;

    /**
     * Contains information about the error for the listener (test).
     */
    private Exception mError = null;

    public interface EndpointsTaskListener {
        void onComplete(String jsonString, Exception e);
    }

    public EndpointsAsyncTask(Context context) {
        mContextRef = new WeakReference<>(context);
    }

    /**
     * doInBackground-method is from the following site:
     * https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/77e9910911d5412e5efede5fa681ec105a0f02ad/HelloEndpoints#2-connecting-your-android-app-to-the-backend
     * <p>
     * This background-Task requests a joke from the GCE and displays it in a new activity.
     */
    @Override
    protected final String doInBackground(String... params) {
        if(myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver


            myApiService = builder.build();
        }

        try {
            category = params[0];

            return myApiService.deliverJoke(category).execute().getJoke();
        } catch (IOException e) {
            mError = e;
            return e.getMessage();
        }
    }

    /**
     * This listener is required, so that this AsyncTask can be tested.
     *
     * @param listener  contains the method which will be executed once the task is finished.
     * @return          this task.
     */
    public EndpointsAsyncTask setListener(EndpointsTaskListener listener) {
        this.mListener = listener;
        return this;
    }

    /**
     * If a result was found from the GCE, then open a new activity with it - displaying it.
     *
     * @param result    the joke.
     */
    @Override
    protected void onPostExecute(String result) {
        if (this.mListener != null) {
            this.mListener.onComplete(result, mError);
        }

        Context context = mContextRef.get();

        Intent intent = new Intent(context, JokeActivity.class);
        intent.putExtra(JokeActivity.JOKE_KEY, result);
        intent.putExtra(JokeActivity.JOKE_CATEGORY_KEY, category);

        context.startActivity(intent);
    }

    /**
     * If the Request failed, tell the executioner that it failed.
     */
    @Override
    protected void onCancelled() {
        if (this.mListener != null) {
            mError = new InterruptedException("AsyncTask cancelled");
            this.mListener.onComplete(null, mError);
        }
    }
}
