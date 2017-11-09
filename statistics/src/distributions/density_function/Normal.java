package distributions.density_function;

/* This class defines the Normal distribution, this distribution
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
    
    
    public double density(double x) {
        double z = Math.pow((x - mean) / sd, 2);
        double coefficient = Math.sqrt(1 / (2 * Math.PI));
        return coefficient * Math.exp((-0.5) * z);
    }
    
    public double median() {
        return mean;
    }
    
    public double variance() {
        return sd * sd;
    }
    
    public double mean() {
        return mean;
    }
    
    public double zScore(double x) {
        return (x - mean) / sd;
    }
    
    public double inverse(double area) {
        return super.inverse(-20.0, 20.0, area);
    }
    
    public double cumulativeProbability(double lowerBound, double upperBound) {
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
    
    public String toString() {
        return " ~ N(" + mean + ", " + sd + "^2)";
    }
    
    public static void main(String[] args) {
    	Normal normal = new Normal(2, 4);
    	System.out.println(normal.cumulativeProbability(-3.1, 1));
    	System.out.println(normal.cumulativeProbability(-99999, 0));
    	System.out.println(normal.fromNegativeInfinity(normal.inverse(0.0001)));
    }
}
