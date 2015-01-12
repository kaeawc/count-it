package io.kaeawc.countit;
import java.util.Random;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.widget.TextView;

public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private final String TAG = "CountTest";

    public MainActivityTest() {
        super(MainActivity.class);
    }

    private MainActivity mActivity = null;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        setActivityInitialTouchMode(false);
        mActivity = getActivity();
    }

    private Long getDifferentCount() {

        Long previous = mActivity.loadCount();
        Random random = new Random();
        Long expected = random.nextLong();

        while (expected.equals(previous)) {
            expected = random.nextLong();
        }

        return 0L;
    }

    private void assertCountDisplayEquals(Long expected) {
        Long actual = null;

        try {
            TextView countDisplay = mActivity.getCountDisplay();
            actual = Long.parseLong(countDisplay.getText().toString());
        } catch (Exception ex) {
            fail("Count display should always be a number.");
        }

        assertEquals(expected, actual);

    }

    public void testPersistingCount() {
        mActivity.runOnUiThread(
                new Runnable() {
                    public void run() {
                        Long expected = getDifferentCount();
                        mActivity.saveCount(expected);
                        Long actual = mActivity.loadCount();
                        assertEquals(expected, actual);
                    }
                }
        );
    }

    public void testIncrementingCount() {
        mActivity.runOnUiThread(
                new Runnable() {
                    public void run() {
                        mActivity.onCreate(null);
                        Long initialExpected = 0L;
                        mActivity.saveCount(initialExpected);
                        Long initialActual = mActivity.loadCount();
                        assertEquals(initialExpected, initialActual);
                        mActivity.mCount = initialActual;

                        if (mActivity == null)
                            Log.i(TAG, "Activity no longer exists.");

                        mActivity.incrementCounter();

                        if (mActivity == null)
                            Log.i(TAG, "Activity no longer exists, incrementing the counter destroyed it.");

                        Long finalExpected = 1L;
                        Long finalActual = mActivity.loadCount();
                        assertEquals(finalExpected, finalActual);
                    }
                }
        );
    }

    public void testUpdatingCountDisplay() {
        mActivity.runOnUiThread(
                new Runnable() {
                    public void run() {
                        Long expected = getDifferentCount();
                        mActivity.updateCountDisplay(expected);
                        assertCountDisplayEquals(expected);
                    }
                }
        );
    }
}
