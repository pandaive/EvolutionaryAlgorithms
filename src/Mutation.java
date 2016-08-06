import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
 * mutation of a genotype, each gene is mutated with given probabilty (threshold)
 */
public class Mutation {
	
	int symbols;
	
	public Mutation(int symbols)
	{
		this.symbols = symbols;
	}

	public int[] mutate(int[] a, double threshold){
		int[] mutated = new int[a.length];
		Random random = new Random();
		
		for (int i = 0; i < a.length; i++)
		{
			if (random.nextDouble() < threshold)
				mutated[i] = a[i];
			else
				mutated[i] = symbols - 1 - a[i];
		}
		return mutated;
	}
	
	public List<Vector> mutateAll(List<Vector> population, double threshold){
		List<Vector> mutated = new ArrayList<Vector>();
		for (Vector v : population)
			mutated.add(new Vector(mutate(v.vector, threshold)));
		
		return mutated;
	}
}
