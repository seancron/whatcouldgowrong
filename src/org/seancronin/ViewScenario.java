package org.seancronin;

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

public class ViewScenario extends Activity {
    private final int MENU_SUGGESTION = 1;
    private final int GROUP_DEFAULT = 0;
    private GestureDetector gestureDetector;
    TextView scenarioText;
    private ScenarioGenerator scenarioGenerator;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.main2);

	scenarioGenerator = new ScenarioGenerator(getApplicationContext());

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
	    Intent i = new Intent(Intent.ACTION_VIEW);
	    i.setData(Uri.parse("https://spreadsheets.google.com/viewform?formkey=dER4QW13LTMxUFRsV1lQZjZTN1FqVEE6MQ"));
	    startActivity(i);
	    return true;
	}
	return super.onOptionsItemSelected(item);
    }

    public void randomScenario() {
	String scenario = scenarioGenerator.generateScenario();

	scenarioText = (TextView) findViewById(R.id.ScenarioBody);
	scenarioText.setText(scenario);
    }
}