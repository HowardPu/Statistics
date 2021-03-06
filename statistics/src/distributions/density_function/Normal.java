package distributions.density_function;

/* 
 * This class defines the Normal distribution, this distribution
 * is especially useful dealing with large sample because of 
 * central limit theorem.
 */

public class Normal extends PDF {
	private double mean;
    private double sd;
    
    // Take mean and standard deviation as input(both are real numbers)
    // construct a Normal distribution with given mean and given standard deviation
    public Normal(double mean, double sd) {
        super(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, false);
        if(sd <= 0) {
            throw new IllegalArgumentException("Invalid standard deviation (σ) : " + sd);
        }
        this.mean = mean;
        this.sd = sd;
    }
    
    // Construct a standard Normal distribution with mean = 0 and standard deviation = 1
    public Normal() {
        this(0, 1);
    }
    
    // take a real number as input
    // return the probability density at given value
    public double density(double x) {
        double z = Math.pow((x - mean) / sd, 2);
        double coefficient = Math.sqrt(1 / (2 * Math.PI));
        return coefficient * Math.exp((-0.5) * z);
    }
    
    // return the median of this normal distribution,
    // which is always the same as mean
    public double median() {
        return mean;
    }
    
    // return the variance of this normal distribution,
    // which is the square of the standard deviation
    public double variance() {
        return sd * sd;
    }
    
    // return the mean of this normal distribution
    public double mean() {
        return mean;
    }
    
    // return the standard deviation of this normal distribution
    public double standardDeviation() {
    	return sd;
    }
    
    // take a real number as input
    // return the z score of the given real number correspond to this normal distribution
    public double zScore(double x) {
        return (x - mean) / sd;
    }
    
    // take a real number from 0 to 1 as input
    // return a real number such that the area under this Normal distribution
    // from negative infinity to given area is returned value
    // Note : throw IllegalArgumentException if the given area is less than 0 or greater than 1
    public double inverse(double area) {
        return super.inverse(-20.0, 20.0, area);
    }
    
    // take a real number as lower and a real number as upper bound as input
    // return the approximate area under this normal distribution from lower bound
    // to upper bound.
    // Note : throw IllegalStateException if the lower bound is greater than the upper bound
    public double cumulativeProbability(double lowerBound, double upperBound) {
    	if(lowerBound > upperBound) {
            throw new IllegalStateException("Illegal bound : lower bound : " + lowerBound + 
                                               " > upper bound : "  + upperBound);
        }
        double lowCorrection = 0;
        double lowZ = zScore(lowerBound);
        double highCorrection = 0;
        double highZ = zScore(upperBound);
        if(highZ >= 18) {
             highZ = 0;
             highCorrection = 0.5;
        }
        if(lowZ <= -18) {
             lowZ = 0;
             lowCorrection = 0.5;
        }
        return integral(highZ) - integral(lowZ) + lowCorrection + highCorrection;
    }
    
    // take a real number as input
    // return the indefinite integral(c = 0) at given value
    public double integral(double x) {
        double density = density(x * sd + mean);
        if(density == 0) {
            if(x < mean) {
                return 0;
            } else {
                return 1;
            }
        }
        return 0.5 + density * powerSeries(x, 1, 100) * x;
    }
    
    private double powerSeries(double x, int start, int degree) {
        if(start + 1 == degree) {
            return 1 + x * x / (2 * degree + 1);
        } else {
            return 1 + x * x / (2 * start + 1) * powerSeries(x, start + 1, degree); 
        }    
    }
    
    // return the string representation of this normal distribution
    // e.g. if this normal distribution's mean = 0, standard deviation = 1,
    // then it will return " ~ N(0, 1 ^ 2)"
    public String toString() {
        return " ~ N(" + mean + ", " + sd + " ^ 2)";
    }
    
    public static void main(String[] args) {
    	Normal normal = new Normal(2, 4);
    	System.out.println(normal.cumulativeProbability(-3.1, 1));
    	System.out.println(normal.cumulativeProbability(-99999, 0));
    	System.out.println(normal.fromNegativeInfinity(normal.inverse(0.0001)));
    }
}
