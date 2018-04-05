package com.udacity.android.builditbigger;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.text.TextUtils;

import com.udacity.gradle.builditbigger.EndpointsAsyncTask;
import com.udacity.gradle.builditbigger.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 */
@RunWith(AndroidJUnit4.class)
public class AsyncJokeTaskTest {

    @Rule
    public ActivityTestRule<MainActivity> mMainActivityTestRule
            = new ActivityTestRule<>(MainActivity.class);

    private String mJoke = null;
    private Exception mError = null;
    private CountDownLatch signal = null;

    @Before
    public void setUp() throws Exception {
        signal = new CountDownLatch(1);
    }

    @After
    public void tearDown() throws Exception {
        signal.countDown();
    }

    /**
     * Part of this code is from:
     * http://marksunghunpark.blogspot.co.at/2015/05/how-to-test-asynctask-in-android.html
     *
     * @throws InterruptedException exception thrown if the CountDownLatch was interrupted.
     */
    @Test
    public void testAlbumGetTask() throws InterruptedException {

        EndpointsAsyncTask task = new EndpointsAsyncTask(mMainActivityTestRule.getActivity().getApplicationContext());
        task.setListener(new EndpointsAsyncTask.EndpointsTaskListener() {
            @Override
            public void onComplete(String jsonString, Exception e) {
                mJoke = jsonString;
                mError = e;
                signal.countDown();
            }
        }).execute("Physics");
        signal.await();

        assertNull(mError);
        assertFalse(TextUtils.isEmpty(mJoke));
        assertTrue(!mJoke.equals("No joke found :("));

    }

    @Test
    public void failingTest() {

        assertFalse(true);

    }
}
