package org.seancronin;

import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.widget.TextView;
import android.widget.Toast;

public class ViewScenario extends Activity {
    private final int MENU_SUGGESTION = 1;
    private final int GROUP_DEFAULT = 0;
    private GestureDetector gestureDetector;
    TextView scenarioText;
    final static Random scenarioNumber = new Random();
    private int[] scenarioOrder;
    private int shufflePosition = 0;
    private ScenarioGenerator scenarioGenerator;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.main2);

	//Resources res = getResources();
	
	scenarioGenerator = new ScenarioGenerator(getApplicationContext());

	/*
	scenarioOrder = new int[res.getStringArray(R.array.scenarios).length];
	for (int i = 0; i < scenarioOrder.length; i++) {
	    scenarioOrder[i] = i;
	}

	shuffle(scenarioOrder);
	*/

	gestureDetector = new GestureDetector(this,
		new SimpleOnGestureListener() {
		    @Override
		    public boolean onSingleTapUp(MotionEvent e) {
			randomScenario();
			return true;
		    }
		});
	
	randomScenario();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
	super.onSaveInstanceState(outState);

	scenarioText = (TextView) findViewById(R.id.ScenarioBody);

	outState.putIntArray("scenarioOrder", scenarioOrder);
	outState.putInt("shufflePosition", shufflePosition);
	outState.putCharSequence("scenarioText", scenarioText.getText());
    }

    @Override
    protected void onResume() {
	super.onResume();
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
	super.onRestoreInstanceState(savedInstanceState);

	scenarioText = (TextView) findViewById(R.id.ScenarioBody);

	scenarioOrder = savedInstanceState.getIntArray("scenarioOrder");
	shufflePosition = savedInstanceState.getInt("shufflePosition");
	scenarioText
		.setText(savedInstanceState.getCharSequence("scenarioText"));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
	return gestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
	menu.add(GROUP_DEFAULT, MENU_SUGGESTION, 0, "Suggest a scenario");

	return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
	switch (item.getItemId()) {
	case MENU_SUGGESTION:
	    /* TODO: Open form in browser */
	    Intent i = new Intent(Intent.ACTION_VIEW);
	    i.setData(Uri.parse("https://spreadsheets.google.com/viewform?formkey=dER4QW13LTMxUFRsV1lQZjZTN1FqVEE6MQ"));
	    startActivity(i);
	    return true;
	}
	return super.onOptionsItemSelected(item);
    }

    public void createAlert() {
	Toast.makeText(this, "Next scenario", Toast.LENGTH_SHORT).show();
    }

    /**
     * 
     * @param array
     * @see <a
     *      href="http://en.wikipedia.org/w/index.php?title=Fisher-Yates_shuffle">Fisher-Yates
     *      Shuffle (Wikipedia)</a>
     */
    public static void shuffle(int[] array) {
	for (int i = array.length; i > 1; i--) {
	    int j = scenarioNumber.nextInt(i);
	    int tmp = array[j];
	    array[j] = array[i - 1];
	    array[i - 1] = tmp;
	}
    }

    public void randomScenario() {
	//Resources res = getResources();
	//String[] scenarios = res.getStringArray(R.array.scenarios);
	String scenario = scenarioGenerator.generateScenario();

	scenarioText = (TextView) findViewById(R.id.ScenarioBody);
	scenarioText.setText(scenario);

	/* shufflePosition++;
	if (shufflePosition >= scenarios.length) {
	    shuffle(scenarioOrder);
	    shufflePosition = 0;
	} */
    }
}