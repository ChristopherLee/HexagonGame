/** 
 * Score.java
 *
 * This source file can be used for educational purpose only.
 * Originally developed by Chunzhao Zheng.
 * 
 * Copyright (c) 2011 Chunzhao, College of Computer and Information Science,
 * Northeastern University, Boston, MA 02115. All rights reserved.
 * Redistribution and use in source and binary forms, with or without modification, 
 * are permitted provided that the following conditions are met: 
 *     -  Redistributions of source code must retain the above copyright notice.
 *     -  Redistributions in binary form must reproduce the above copyright notice.
 *
 * Revision History
 *       Date             Programmer            Notes
 * ------------------ ------------------ -------------------
 *  Feb 1, 2011        Chunzhao, Zheng        Initial
 *  Feb 1, 2011        Christopher, Lee       Initial
 *********************************************************************
*/
package ingenious.board;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.java.contract.Requires;

import ingenious.board.util.Constants;

/**
 * Score is a data structure to represent a score that is obtained after 
 * each placement.
 */
public class Score implements Map<Color, Integer>{
    
    /** Each instance of Score would have an array of int for different color **/
    private final Map<Color, Integer> score;
    
    public Score() {
        score = new HashMap<Color, Integer> ();
        for (Color color : Color.values())
        	score.put(color, 0);
    }
    
    /*public Score(int[] score) {
        this.score = score;
    }*/
    
    public void setValue(Color color, int value) {
        if(color != null) {
            score.put(color, value);
        }
    }
    
    public void addValue(Color color, int value) {
        if(color != null) {
            score.put(color, score.get(color) + value);
        }
    }
    
    @Override
    public String toString() {
        String str = "(";
        for (Color color : score.keySet()) {
            str += color.name() + ": " + score.get(color) + ", ";
        }
        str += ")";
        return str;
    }
    
    @Override
    public boolean equals (Object o) {
    	if (o instanceof Score) {
    		Score otherScore = (Score)o;
    		for (Color color : score.keySet()) {
    			if (!otherScore.get(color).equals(score.get(color)))
    				return false;
    		}
    		return true;
    			
    	}
    	return false;
    }
    
    public int compareScoreDiff(Score otherScore) {
    	int scoreDiff = 0;
    	for (Color color : score.keySet()) {
    		scoreDiff += get(color) - otherScore.get(color);
    	}
    	return scoreDiff;
    }
    
    public Score deepCopy() {
    	Score copy = new Score();
    	for (Color color : score.keySet()) {
    		copy.put(color, score.get(color));
    	}
    	return copy;
    }
    
    /**
     * Adds otherscore to this score
     */
    public void add(Score otherScore){
    	for (Color color : score.keySet()) {
    		 put(color, get(color) + otherScore.get(color));
    	}
    }
    
    /**
     * get the lowest score in the list based on the index.  
     * If index is 0, get the first lowest score, if index is 1, get second lowest score 
     */
     private int getLowestScore (int index) {
    	ArrayList<Integer> copy = new ArrayList<Integer>();
    	for (Color color : score.keySet()) {
    		copy.add(get(color));
    	}
    	Collections.sort(copy);
    	return copy.get(index);
    	
    }
    
    /**
     * return the score which has the largest smallest value.  
     * If it is a tie, go to the second largest smallest value 
     * 
     */
    public Score pickBestSmallestScore(Score score1, Score score2) {
    	return pickBestSmallestScore(score1, score2, 0);
    }
    /**
     * helper function, uses index to determine which largest smallest value we are evaluating
     */
    public Score pickBestSmallestScore(Score score1, Score score2, int index) {
    	if (index >= Color.values().length)
    		return score1;
    	int lowestScore1 = score1.getLowestScore(index);
    	int lowestScore2 = score2.getLowestScore(index);
    	if (lowestScore1 > lowestScore2)
    		return score1;
    	else if (lowestScore1 < lowestScore2)
    		return score2;
    	else 
    		return pickBestSmallestScore(score1, score2, index+1);
    }
    /**
     * compares this score with other score
     */
    public int compareTo(Score otherScore){
    	ArrayList<Integer> copys1 = new ArrayList<Integer>(); 
    	ArrayList<Integer> copys2 = new ArrayList<Integer>();
    	for (Color color : score.keySet()) {
    		copys1.add(get(color));
    		copys2.add(otherScore.get(color));
    	}
    	
    	Collections.sort(copys1);
    	Collections.sort(copys2);
    	
    	for (int i = 0; i < copys1.size(); i++) {
    		if (copys1.get(i) == copys2.get(i))
    			continue;
    		else
    			return copys1.get(i).compareTo(copys2.get(i));
    	}
    	return 0;
    }

	@Override
	public void clear() {
		score.clear();
	}

	@Override
	public boolean containsKey(Object arg0) {
		return score.containsKey(arg0);
	}

	@Override
	public boolean containsValue(Object arg0) {
		return score.containsValue(arg0);
	}

	@Override
	public Set<java.util.Map.Entry<Color, Integer>> entrySet() {
		return score.entrySet();
	}

	@Override
	public Integer get(Object arg0) {
		return score.get(arg0);
	}

	@Override
	public boolean isEmpty() {
		return score.isEmpty();
	}

	@Override
	public Set<Color> keySet() {
		return score.keySet();
	}

	@Override
	public Integer put(Color arg0, Integer arg1) {
		return score.put(arg0, arg1);
	}

	@Override
	public void putAll(Map<? extends Color, ? extends Integer> arg0) {
		score.putAll(arg0);
		
	}

	@Override
	public Integer remove(Object arg0) {
		return score.remove(arg0);
	}

	@Override
	public int size() {
		return score.size();
	}

	@Override
	public Collection<Integer> values() {
		return score.values();
	}
	
	/**
	 * returns number of colors over 17
	 */
	public int colorsOver17() {
		int accumulator = 0;
		for (Color color : score.keySet()) {
			if (score.get(color) > 17)
				accumulator++;
		}
		return accumulator;
	}
	
	/**
	 * returns the value of the lowest score
	 * Note: we initialize the lowestScore with an arbitrary color value
	 */
	private int findLowestScore() {
		int lowestScore = score.get(Color.BLUE);
		for (Integer value : score.values()) {
			if (lowestScore > value)
				lowestScore = value;
		}
		return lowestScore;
	}
	
	/**
	 * returns a list of all colors that have the value of the lowest score
	 */
	public List<Color> getLowestScoringColors () {
		List<Color> lowestColors = new ArrayList<Color>();
		
		int lowestScore = findLowestScore();
		for (Color color : score.keySet()) {
			if (score.get(color) == lowestScore)
				lowestColors.add(color);
		}
		return lowestColors;		
	}

}
