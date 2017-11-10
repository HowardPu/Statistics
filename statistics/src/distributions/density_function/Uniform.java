package distributions.density_function;

/*
 *  This class defines uniform distribution,
 * which means the probability density is the same 
 * in the domain of the uniform distribution.
 */

public class Uniform extends PDF {
    private double k;
    private double start;
    private double end;
    
    // Take start point and the end point as input
    // construct an uniform distribution corresponds to given parameter
    // Note: throw IllegalArgumentException if end point is not greater than the start point
    public Uniform(double start, double end) {
        super(start, end, false);
        this.start = start;
        this.end = end;    
        this.k = 1 / (end - start);
    }
    
    // return the variance of this uniform distribution
    public double variance() {
        return 1.0 / 12 * (end - start) * (end - start);
    }
    
    // return the mean of this uniform distribution
    public double mean() {
        return median();
    }
    
    // return the median of this uniform distribution
    public double median() {
        return 0.5 * (start + end);
    }
    
    // return the indefinite integral of this uniform distribution
    // at given real number x
    // Note: return NaN if x is out of domain
    public double integral(double x) {
        return density(x) * x;
    }
    
    // return the probability density of this uniform distribution
    // at given real number x
    // Note: return NaN if x is out of domain
    public double density(double x) {
        if(x < start || x > end) {
            return Double.NaN;
        }
        return k;
    }
    
    // take area between 0 and 1(inclusive) as input
    // return a real number such that the area under this uniform distribution
    // from 0 to returned value is given area
    // Note: throw IllegalArgumentException if given area is not between 0 and 1
    public double inverse(double area) {
    	if(area < 0 || area > 1) {
    		throw new IllegalArgumentException("Illegal area : " + area);
    	}
        return start + area / k;
    }
    
    // return the string representation of this uniform distribution
    // e.g. if the start point is 3 and end point is 4
    //      it will return " ~ unif(3.0, 4.0)"
    public String toString() {
        return " ~ unif(" + start + ", " + end + ")";
    }
}