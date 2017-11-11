package distributions.density_function;

/* 
 * This defines a Student-T distribution.
 * This distribution may be useful for analyzing sample
 * and bivariate correlation
 */

public class StudentT extends PDF {
    private int df;             
    private double coefficient;
    
    // Take degrees of freedom(a positive integer) as input
    // construct a StudentT distribution
    // Note : throw IllegalArgumentException if degrees of freedom is less than 1
    public StudentT(int df) {
        super(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, false);
        if(df <= 0) {
            throw new IllegalArgumentException("Illegal degree of freedom : less than 1");
        }
        this.df = df;
        coefficient = coefficient();
    }
    
    // Take a real number as input, 
    // return the probability density of this StudentT distribution
    public double density(double x) {
        if(df == 1) {
            return coefficient * 1 / (1 + x * x);
        } else if(df == 2) {
            return coefficient * 1 / Math.pow(2 + x * x, 1.5);
        } else if(df == 3) {
            return coefficient * 1 / Math.pow(3 + x * x, 2);
        } else {
            return coefficient * Math.pow(1 + (x * x / df), -0.5 * (df + 1));
        }
    }
    
    // return the degrees of freedom of this StudentT distribution
    public int df() {
        return df;
    }
    
    // take an area(a real from 0 to 1) as input,
    // return the value such that the area under the StudentT distribution
    // from negative infinity to returned value is given area
    // Note: throw IllegalArgumentException if the given area is less than 0 or greater than 1
    public double inverse(double area) {
        return super.inverse(-18, 18, area);
    }
    
    // return the median of StudentT distribution, which is always 0
    public double median() {
        return 0;
    }
    
    // return the mean of StudentT distribution, which is always 0
    public double mean() {
        return 0;
    }
    
    // Return the string representation of a StudentT distribution
    // e.g. For StudentT distribution with degrees of freedom 2
    //      it will return " ~ t(2)"
    public String toString() {
        return " ~ t(" + df + ")";
    }
    
    // Return the variance of this StudentT distribution
    // Note: return NaN if the degrees of freedom is not greater than 2
    //       (df <= 2)
    public double variance() {
        if(df <= 2) {
            return Double.NaN;
        } else {
            return (double) df / df - 2;
        }
    }
    
    // take a real number as input
    // return the indefinite integral of StudentT distribution with c = 0
    // at given real number
    public double integral(double x) {
    	if(df == 1) {
            return Math.atan(x) / Math.PI;
        } else if(df == 2) {
            return 0.5 * x / Math.sqrt(x * x + 2);
        } else if(df == 3) {
            return 6 * Math.sqrt(3) / (Math.PI * Math.pow(3 + x * x, 2));
        } else {
            double sin = x / Math.sqrt(df + x * x);
            double cos = Math.sqrt(df / (df + x * x));
            double result = 0;
            int degree = df - 1;
            if(degree % 2 == 0) {
                double angle = Math.atan(x / Math.sqrt(df));
                result = 0.5 * angle + 0.25 * Math.sin(2 * angle);
                degree = 2;
            } else {
                result = sin;
                degree = 1;
            }
            while(degree <= df - 3) {
                double next = sin * Math.pow(cos, degree + 1) / (degree + 2);
                result = result * (degree + 1) / (degree + 2);
                result += next;
                degree = degree + 2;
            }
            return Math.sqrt(df) * coefficient * result;
        }
    }
    
    private double coefficient() {
        if(df == 1) {
            return 1 / Math.PI;
        } else if (df == 2) {
            return 1;
        } else if (df == 3) {
            return 6 * Math.sqrt(3) / Math.PI;
        } else {
            double coefficient = 1;
            if(df % 2 == 0) {
                coefficient = 1 / (2.0 * Math.sqrt(df));
                for(int i = df - 1; i >= 3; i = i - 2) {
                    coefficient = coefficient * i / (i - 1);
                }
            } else {
               coefficient = (df - 1) / (Math.PI * Math.sqrt(df));
               for(int i = df - 3; i >= 2; i = i - 2) {
                   coefficient = coefficient * i / (i + 1);
               }
            }
            return coefficient;
        }
    }
    
    // Test function
    public static void main(String[] args) {
    	StudentT t = new StudentT(1);
    	System.out.println(t.integral(0));
    }
}