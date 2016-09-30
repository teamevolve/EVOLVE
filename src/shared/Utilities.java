package shared;

import java.util.Random;

public class Utilities {
	
	public static double nextGaussianRand(Random rng, double mean, double stdDev) {
		assert(rng != null);
		double answer = rng.nextGaussian() * stdDev + mean;
		System.out.printf("Generated %f from mean %f and stddev %f\n", answer, mean, stdDev);
		return rng.nextGaussian() * stdDev + mean;
	}

}
