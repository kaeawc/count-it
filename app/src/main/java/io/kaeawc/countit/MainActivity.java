package io.kaeawc.countit;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {

    public Long mCount = 0L;

    public Button getCountButton() {
        return (Button) this.findViewById(R.id.count_button);
    }

    public TextView getCountDisplay() {
        return (TextView) this.findViewById(R.id.current_count);
    }

    public void incrementCounter() {

        if (mCount == null) {
            mCount = 0L;
        }

        mCount += 1L;

        updateCountDisplay(mCount);
        saveCount(mCount);
    }

    public View.OnClickListener countListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) { incrementCounter(); }
    };

    public void initializeCountButton() {

        Button counter = getCountButton();
        counter.setOnClickListener(countListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCount = loadCount();
        initializeCountButton();
        updateCountDisplay(mCount);
    }

    public void onPause() {
        super.onPause();
        saveCount(this.mCount);
    }

    public Long loadCount() {

        SharedPreferences prefs = getSharedPreferences("user_count", MODE_PRIVATE);

        if (prefs != null) {
            return prefs.getLong("counter", 0L);
        }

        // If we weren't able to load the count, reset it.
        return 0L;
    }

    public Boolean saveCount(Long count) {

        // Get the user's count
        SharedPreferences prefs = getSharedPreferences("user_count", MODE_PRIVATE);

        // Only attempt editing if able to lock shared preferences.
        if (prefs == null)
            return false;

        SharedPreferences.Editor editor = prefs.edit();

        // Only attempt saving if able to lock a preference editor.
        if (editor == null)
            return false;

        editor.putLong("counter", count);

        // Don't block the main thread when saving the count.
        editor.apply();

        // Assuming save happens correctly at this point.
        return true;
    }

    public void updateCountDisplay(Long count) {
        TextView countDisplay = getCountDisplay();

        if (countDisplay != null) {
            countDisplay.setText(count.toString());
        } else {
            Toast.makeText(getApplicationContext(), "Couldn't find display :(", Toast.LENGTH_SHORT).show();
        }
    }

}
