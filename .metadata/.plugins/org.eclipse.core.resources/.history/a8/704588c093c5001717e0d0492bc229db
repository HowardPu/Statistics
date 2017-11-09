package distributions.density_function;
/* This class defines the exponential distribution, which is the density
 * function's version of Poission function. And this function is especially
 * helpful for modeling population growth 
 */

public class Exponential extends PDF {
    private double lambda;
    
    // Take a positive real number frequency(lambda) as input
    // construct a Exponential distribution with given frequency
    // Note: throw IllegalArgumentException if the given frequency
    //        is non-positive
    public Exponential(double frequency) {
        super(0, Double.POSITIVE_INFINITY, true);
        if(frequency <= 0) {
        	throw new IllegalArgumentException("frequency should be greater than 0 : " + lambda);
        }
        lambda = frequency;
    }
    
    // Take a real number as input
    // return the probability density at given value
    // Note: return NaN if the given value is out of domain
    //       (given value is negative)
    public double density(double x) {
        if(x < 0) {
            return Double.NaN;
        } else {
            return lambda * Math.exp(-lambda * x);
        }
    }
    
    // Take a real number as input
    // return the indefinite integral at given value
    // Note: return NaN if the given value is out of domain
    //       (given value is negative)
    public double integral(double x) {
    	if(x < 0) {
    		return Double.NaN;
    	}
        return -Math.exp(-x * lambda);
    }
    
    // return the variance of this exponential distribution
    public double variance() {
        return 1 / lambda / lambda;
    }
    
    // return the mean of this exponential distribution
    public double mean() {
        return  1 / lambda;
    }
    
    // return the median of this exponential distribution
    public double median() {
        return Math.log(2) / lambda;
    }
    
    // return the string representation of this exponential distribution
    // e.g. if the frequency is 2
    //      it will return " ~ EXP(2.0)"
    public String toString() {
        return " ~ EXP(" + lambda + ")";
    }
    
    // take area between 0 and 1(inclusive) as input
    // return a real number such that the area under this exponential distribution
    // from 0 to returned value is given area
    // Note: throw IllegalArgumentException if given area is not between 0 and 1
    public double inverse(double area) {
    	if(area < 0 || area > 1) {
            throw new IllegalArgumentException("Illeagl area : " + area);
        }
        return Math.log(1 - area) / -lambda;
    }
    
    public static void main(String[] args) {
    	Exponential e = new Exponential(5);
    	System.out.println(e.toInfinity(2));
    	System.out.println(e.cumulativeProbability(1, 2));
    	System.out.println(e.inverse(0.5));
    }
}
