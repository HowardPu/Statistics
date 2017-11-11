package distributions.mass_functions;

import distributions.density_function.ChiSquare;

public class Poisson extends PMF {
    double lambda;
    
    public Poisson(double frequency) {
    	super(0, Double.POSITIVE_INFINITY);
        if(frequency <= 0) {
            throw new IllegalArgumentException("Invalid frequency : " + frequency);
        }
        lambda = frequency;
    }
    
    public double variance() {
        return lambda;
    }
    
    public double mean() {
        return lambda;
    }
    
    public double median() {
        return lambda + 1.0 / 3 - 0.02 / lambda;
    }
    
    // may not work properly is lambda is too large
    public double mass(int x) {
        double result = Math.exp(-lambda);   
        for(int i = 1; i <= x; i++) {
            result = result * lambda / i;
        }
        return result;
    }
    
    public double cumulativeProbability(int lowerBound, int upperBound) {
    	if(lowerBound > upperBound) {
            throw new IllegalStateException("Illegal bound : lower bound : " + lowerBound + 
                                               " > upper bound : "  + upperBound);
        }
    	if(lowerBound == upperBound) {
    		return mass(lowerBound);
    	}
    	ChiSquare upper = new ChiSquare(2 * (upperBound + 1));
    	if(lowerBound == super.getLowestBound()) {
    		return 1 - upper.integral(2 * lambda);
    	} else {
    		ChiSquare lower = new ChiSquare(2 * lowerBound);
    		return lower.integral(2 * lambda) - upper.integral(2 * lambda);
    	}
    }
    
    public static void main(String[] args) {
    	Poisson p = new Poisson(200);
    	int bound1 = 118;
    	int bound2 = 200;
    	double area = 0.5;
    	System.out.println("test 3 : " + p.cumulativeProbability(bound1, bound2));
    	//System.out.println(p.inverse(area));
    }
}
