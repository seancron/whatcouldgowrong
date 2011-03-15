package org.seancronin.wcgw;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map;
import java.util.Random;

import android.content.Context;
import android.content.res.Resources;

public class ScenarioGenerator {

    private Resources res;
    private final Hashtable<String, String[]> terms = new Hashtable<String, String[]>();
    private Map<String, int[]> termOrder = new Hashtable<String, int[]>();
    private Map<String, Integer> termPosition = new Hashtable<String, Integer>();
    private final static Random randomNumber = new Random();

    public ScenarioGenerator(Context context) {
	res = context.getResources();
	terms.put("victims", res.getStringArray(R.array.victims));
	terms.put("verbs", res.getStringArray(R.array.verbs));
	terms.put("villains", res.getStringArray(R.array.villains));
	/*terms.put("causes", res.getStringArray(R.array.causes));
	terms.put("effects", res.getStringArray(R.array.effects));
	terms.put("locations", res.getStringArray(R.array.locations));
	terms.put("standalones", res.getStringArray(R.array.standalones));*/
	terms.put("certainties", res.getStringArray(R.array.certainties));

	for (Enumeration<String> keys = terms.keys(); keys.hasMoreElements();) {
	    String temp = keys.nextElement();
	    termPosition.put(temp, 0);

	    // Generate random order
	    int[] tempOrder = new int[terms.get(temp).length];
	    for (int i = 0; i < tempOrder.length; i++) {
		tempOrder[i] = i;
	    }
	    shuffle(tempOrder);
	    termOrder.put(temp, tempOrder);
	}
    }

    String generateScenario() {
	int syntax = randomNumber.nextInt(1);

	if (syntax == 0) {
	    return String.format("%s %s %s by %s",
		    capitalize(randomTerm("victims")),
		    randomTerm("certainties"), randomTerm("verbs"),
		    randomTerm("villains"));
	} /*
	   * else if (syntax == 1) { return String.format(format, args) }
	   * 
	   * 
	   * else if (syntax == 1) { return capitalize(randomTerm("victims")) +
	   * " " + randomTerm("effects") + " " + randomTerm("causes"); } else
	   * if(syntax == 2) { return randomTerm("standalones"); }
	   */

	return "Nothing.";
    }

    String randomTerm(String key) {
	String[] values = terms.get(key);
	int position = termPosition.get(key);
	int[] order = termOrder.get(key);
	String temp = values[order[position]];

	position++;
	termPosition.put(key, position);

	// If all the values have been used, re-shuffle the order.
	if (position >= order.length) {
	    shuffle(order);
	    termOrder.put(key, order);
	    termPosition.put(key, 0);
	}

	return temp;
    }

    public String capitalize(String word) {
	return word.substring(0, 1).toUpperCase() + word.substring(1);
    }

    public void shuffle(int[] array) {
	for (int i = array.length; i > 1; i--) {
	    int j = randomNumber.nextInt(i);
	    int tmp = array[j];
	    array[j] = array[i - 1];
	    array[i - 1] = tmp;
	}
    }
}
