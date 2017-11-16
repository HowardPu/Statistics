package distributions.density_function;

import Utility.Utility;

/*
 *  This class defines ChiSquare distribution,
 *  which is especially useful for ChiSquare test
 *  with two category variables
 */

public class ChiSquare extends PDF {
    private int df;
    
    // Take degrees of freedom (positive integer) as input
    // construct a ChiSquare distribution with given degrees of freedom
    // Note: throw IllegalArgumentException if given degrees of freedom is not positive
    public ChiSquare(int df) {
        super(0, Double.POSITIVE_INFINITY, true);
        if(df <= 0) {
            throw new IllegalArgumentException("Invalid degree of freedom : " + df);
        }
        this.df = df;
    }
    
    // return the variance of this ChiSquare distribution
    public double variance() {
        return 2 * df;
    }
    
    // return the mean of this ChiSquare distribution
    public double mean() {
        return df;
    }
    
    // return the median of this ChiSquare distribution
    public double median() {
        return df * Math.pow(1 - 2.0 / 9 * df, 3);
    }
    
    // take a non-negative real number as input
    // return the probability density at given point
    // Note: return NaN if given real number is less than 0 (out of domain)
    public double density(double x) {
        if(x < 0) {
            return Double.NaN;
        } else {
            return Math.exp(Math.log(x) * (df / 2.0 - 1) - x / 2 - Math.log(2) * df / 2.0) / Utility.gamma(df / 2.0);
        }
    }
    
    // take area between 0 and 1(inclusive) as input
    // return a real number such that the area under this ChiSquare distribution
    // from 0 to returned value is given area
    // Note: throw IllegalArgumentException if given area is not between 0 and 1
    public double inverse(double area) {
        return super.inverse(0, 10 * df, area);
    }
    
    // return the string representation of this ChiSquare distribution
    // e.g. if the degrees of freedom is 3 
    //      it will return " ~ χ^2(3)"
    public String toString() {
        return " ~ χ^2(" + df + ")";
    }
    
    // Take a real number as input
    // return the indefinite integral at given value
    // Note: return NaN if the given value is out of domain
    //       (given value is negative)
    
    public double integral(double x) {
    	if(x < 0.0) {
    		return Double.NaN;
    	}
    	return Utility.incompleteGamma(df / 2.0, x / 2.0);
    }
    
    public static void main(String[] args) {
        ChiSquare cs = new ChiSquare(4000);
        double lowerBound = 300;
        double upperBound = 400;
        System.out.println(cs.integral(upperBound) - cs.integral(lowerBound));
        System.out.println(cs.density(30));
    }
}