package com.crossover.trial.awa.data;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.crossover.trial.awa.exception.ValueOutOfBoundsException;

/**
 * A collected point, including some information about the range of collected values
 *
 * @author code test administrator
 */
public class DataPoint {
	
	protected String type;
    protected double mean = 0.0;
    protected int lower = 0;
    protected int median = 0;
    protected int upper = 0;
    protected int count = 0;
    protected int min;
    protected int max;
    
    protected boolean isValid;
    
    protected DataPoint() {}
   
    /** private constructor, use the builder to create this object 
     * @throws ValueOutOfBoundsException */
    protected DataPoint(int lower, int median, int upper, double mean, int count) throws ValueOutOfBoundsException {
    	setMean(mean);
    	setLower(lower);
    	setMedian(median);
    	setUpper(upper);
    	setCount(count);
    	min = minValue();
    	max = maxValue();
    }
    
    protected DataPoint(DataPoint data) throws ValueOutOfBoundsException
    {
    	this(data.getLower(), data.getMedian(), data.getUpper(), data.getMean(), data.getCount());
    }
    
    // CR: Here, builder method pattern implementation is intended but was implemented incorrectly. 

   


    
    protected int minValue() { return Integer.MAX_VALUE; }
    protected int maxValue() { return Integer.MIN_VALUE; }
    
    
   
    /** 1st quartile -- useful as a lower bound */
    
    protected void setLower(int lower) throws ValueOutOfBoundsException {
    	if(minValue() <= lower && lower <= maxValue())
    		this.lower = lower;
    	
    	else
    		throw new ValueOutOfBoundsException();
    	isValid = true;
    }
    
    public int getLower() {
        return lower;
    }

    

    /** 2nd quartile -- median value */
    protected void setMedian(int median) throws ValueOutOfBoundsException {
    	if(minValue() <= median && median <= maxValue())
    		this.median = median;
    	else
    		throw new ValueOutOfBoundsException(Integer.toString(median));
    	isValid = true;
    	
    }
    
    public int getMedian() {
        return median;
    }


    /** 3rd quartile value -- less noisy upper value */
    
    protected void setUpper(int upper) throws ValueOutOfBoundsException {
    	if(minValue() <= upper && upper <= maxValue())
    		this.upper = upper;
    	else
    		throw new ValueOutOfBoundsException(Integer.toString(upper));
    	isValid = true;
    }
    
    public int getUpper() {
        return upper;
    }
    
    /** the mean of the observations */
    public double getMean() {
        return mean;
    }
    protected void setMean(double mean) throws ValueOutOfBoundsException {
    	if(minValue() <= mean && mean <= maxValue())
    		this.mean = mean;
    	else
    		throw new ValueOutOfBoundsException(Double.toString(mean));
    	isValid = true;
    }
    
    
    /** the total number of measurements */
    
    protected void setCount(int count) throws ValueOutOfBoundsException {
    	if(count < 0)
    		throw new ValueOutOfBoundsException("Count cannot be negative");
    }
    
    public int getCount() {
        return count;
    }
    
    /** type of the point */
    public String getType() {
    	return type;
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.NO_CLASS_NAME_STYLE);
    }

    public boolean equals(Object that) {
        return this.toString().equals(that.toString());
    }

}
