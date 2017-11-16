/*
Copyright � 1999 CERN - European Organization for Nuclear Research.
Permission to use, copy, modify, distribute and sell this software and its documentation for any purpose 
is hereby granted without fee, provided that the above copyright notice appear in all copies and 
that both that copyright notice and this permission notice appear in supporting documentation. 
CERN makes no representations about the suitability of this software for any purpose. 
It is provided "as is" without expressed or implied warranty.
*/

package distributions;


/* This class defines the general distribution function in Statistics, 
 * which is, the area under the DistributionFunction from the lowest bound to the highest bound
 * is 1. And this general distribution function also contains all functions that are needed for
 * some popular probability distribution function.
 * 
 * In order to extend this class, a Probability Distribution class should indicate
 *     1/ mean
 *     2/ variance 
 *     3/ median
 */
public abstract class DistributionFunction {
	private double highestBound;
	private double lowestBound;
	
	public DistributionFunction(double lowestBound, double highestBound) {
		if(lowestBound >= highestBound) {
            throw new IllegalArgumentException("Illegal domain of this distribution");
        }
        this.lowestBound = lowestBound;
        this.highestBound = highestBound;
	}
	
	// return the lowest bound of this probability distribution
	public double getLowestBound() {
		return lowestBound;
	}
	
	// return the highest bound of this probability distribution
	public double getHighestBound() {
		return highestBound;
	}
    
    public abstract double variance();
    public abstract double mean();
    public abstract double median();
}