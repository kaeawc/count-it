package io.kaeawc.countit;

import android.widget.TextView;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import java.util.Random;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(Robo.class)
public class MainActivityTest {
    @Test
    public void testPersistingCount() {
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        Long previous = activity.loadCount();
        Random random = new Random();
        Long expected = random.nextLong();

        while (expected.equals(previous)) {
            expected = random.nextLong();
        }

        activity.saveCount(expected);
        Long actual = activity.loadCount();
        assertThat("Persisted count should match expected count.", actual, equalTo(expected));
    }
    @Test
    public void testIncrementingCount() {
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        activity.onCreate(null);
        Long initialExpected = 0L;
        activity.saveCount(initialExpected);
        Long initialActual = activity.loadCount();
        assertThat("Initially we expect the app to start at 0.", initialActual, equalTo(initialExpected));
        activity.mCount = initialActual;
        activity.incrementCounter();
        Long finalExpected = 1L;
        Long finalActual = activity.loadCount();
        assertThat("We expected the counter to incrememnt to 1.", finalActual, equalTo(finalExpected));
    }
    @Test
    public void testUpdatingCountDisplay() {
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        Long previous = activity.loadCount();
        Random random = new Random();
        Long expected = random.nextLong();

        while (expected.equals(previous)) {
            expected = random.nextLong();
        }
        activity.updateCountDisplay(expected);
        assertCountDisplayEquals(activity, expected);
    }

    private void assertCountDisplayEquals(MainActivity activity, Long expected) {
        Long actual = null;

        TextView countDisplay = activity.getCountDisplay();
        actual = Long.parseLong(countDisplay.getText().toString());

        assertThat("The displayed count should always match the persisted count.", actual, equalTo(expected));

    }
}