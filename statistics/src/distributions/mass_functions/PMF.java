package distributions.mass_functions;

/*
 * This class defines a general probability mass functions,
 * which mean the probability distribution is discrete in the domain.
 * 
 * In order to extend this class a probability mass function like class should indicate
 *     1/ how to calculate the mass at each point -- mass(int x)
 *        (return NaN if given integer is out of domain)
 *     2/ how to get cumulative probability between two points(inclusive) 
 *        -- getCumulativeProbability(int lowerBound, int upperBound)
 *        (return NaN if given bounds are out of domain)
 *        (throw IllegalStateException if lowerBound is greater than upperBound)
 *     3/ how to get the variance of this probability mass function -- variance()
 *     4/ how to get the mean of this probability mass function -- mean()
 */

public abstract class PMF extends distributions.DistributionFunction {
    
	// lowestBound : the smallest real number that can be accepted for this PMF
    // highestBound : the highest real number that can be accepted for the PMF
    
    // Take lowestBound, highestBound, and isSkew as input,
    // construct a probability mass function for statistical calculation.
    public PMF(double lowestBound, double highestBound) {
        super(lowestBound, highestBound);
    }
    
    // take an integer as input
    // return the probability mass at given point
    // return NaN if given integer is out of domain
    public abstract double mass(int x);
    
    // take two integers as input
    // return the cumulative probability between two points(inclusive)
    // Note: return NaN if given integers are out of domain
    //       throw IllegalArgumentException if lowerBound is greater than upperBound
    public abstract double cumulativeProbability(int lowerBound, int upperBound);
    
    // take a real number between 0 and 1(inclusive) as input
    // return the point where the given area is between the area from 
    // from the lowest bound to returned value and the area from the lowest
    // bound to returned value + 1
    public double inverse(double area) {
    	if(area < 0 || area > 1) {
    		throw new IllegalArgumentException("Invalid area : " + area);
    	}
    	if(area == 1) {
    		return super.getHighestBound();
    	}
    	int lowestBound = (int) super.getLowestBound();
    	if(area < mass(lowestBound)) {
    		return lowestBound;
    	}
    	int highestBound = (int) median();
    	while(cumulativeProbability(lowestBound, highestBound) < area) {
    		highestBound = highestBound * 2;
    	}
    	int higherBound = highestBound;
    	int lowerBound = lowestBound;
    	int middle = (lowerBound + higherBound) / 2;
    	double prob1 = cumulativeProbability(lowestBound, middle);
    	double prob2 = cumulativeProbability(lowestBound, middle + 1);
    	while(area < prob1 || area > prob2) {
    		if(area < prob1) {
    			higherBound = middle;
    		} else {
    			lowerBound = middle;
    		}
    		middle = (lowerBound + higherBound) / 2;
    		System.out.println("middle : " + middle);
    		
        	prob1 = cumulativeProbability(lowestBound, middle);
        	prob2 = cumulativeProbability(lowestBound, middle + 1);
        	System.out.println("Prob 1 : " + prob1);
        	System.out.println("Prob 2 : " + prob2);
    	}
    	return middle;
    }
    
    // return the mean value of this probability mass function
    public abstract double mean();
    
    // return the variance of this probability mass function
    public abstract double variance();
    
    // return the cumulative probability from given point to infinity(inclusive)
    // Note : return NaN if given integers are out of domain
    //        throw IllegalArgumentException if the highest bound of this PMF is not positive infinity
    public double toInfinity(int x) {
    	if(super.getHighestBound() != Double.POSITIVE_INFINITY) {
    		throw new IllegalArgumentException("The highest bound of this PMF is not positive infinity");
    	}
    	if(x < super.getLowestBound()) {
    		return Double.NaN;
    	}
    	if(x == super.getLowestBound()) {
    		return 1;
    	} else {
    	    double result = 1 - cumulativeProbability((int) super.getLowestBound(), x - 1);
    	    if(result < 0) {
    	    	return 0;
    	    } else if(result > 1) {
    	    	return 1;
    	    } else {
    	    	return result;
    	    }
    	}
    }
}
