package org.seancronin;

import java.util.Random;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

public class ScenarioGenerator {

    private final static Random randomNumber = new Random();
    private int[] randomVerbOrder;
    private int randomVerbPosition = 0;
    private int[] randomVictimOrder;
    private int randomVictimPosition = 0;
    private Resources res;

    
    public ScenarioGenerator(Context context) {
	res = context.getResources();
	
	/* Initialize random verb array */
	randomVerbOrder = new int[res.getStringArray(R.array.verbs).length];
	for (int i = 0; i < randomVerbOrder.length; i++) {
	    randomVerbOrder[i] = i;
	}
	shuffle(randomVerbOrder);
	
	randomVictimOrder = new int[res.getStringArray(R.array.victims).length];
	for (int i = 0; i < randomVictimOrder.length; i++) {
	    randomVictimOrder[i] = i;
	}
	shuffle(randomVictimOrder);
    }

    String generateScenario() {
	int syntax = randomNumber.nextInt(1000);
	
	if (syntax == 999) {
	    return "Nothing.";
	}
	else {
	    return randomVictim() + " " + randomVerb();
	}
    }
    
    String randomVerb() {
	String[] verbs = res.getStringArray(R.array.verbs);
	String temp = verbs[randomVerbOrder[randomVerbPosition]];
	
	randomVerbPosition++;
	if (randomVerbPosition >= verbs.length) {
	    shuffle(randomVerbOrder);
	    randomVerbPosition = 0;
	}
	
	return temp;
    }
    
    String randomVictim() {
	String[] subjects = res.getStringArray(R.array.victims);
	Log.i("WCGW", "randomVictimPosition = " + randomVictimPosition);
	String temp = subjects[randomVictimOrder[randomVictimPosition]];
	
	randomVictimPosition++;
	if (randomVictimPosition >= subjects.length) {
	    shuffle(randomVictimOrder);
	    randomVictimPosition = 0;
	}
	
	return temp;
    }

    
    public static void shuffle(int[] array) {
	for (int i = array.length; i > 1; i--) {
	    int j = randomNumber.nextInt(i);
	    int tmp = array[j];
	    array[j] = array[i - 1];
	    array[i - 1] = tmp;
	}
    }
}
