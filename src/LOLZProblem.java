import java.util.ArrayList;
import java.util.List;

/*
 * implementation of LOLZ problem
 */
public class LOLZProblem implements EAProblem {

int size;
int threshold;
	
	public LOLZProblem(int size, int threshold) {
		this.size = size;
		this.threshold = threshold;
	}
	
	public List<Vector> createPopulation(int n) 
	{
		List<Vector> population = new ArrayList<Vector>();
		for (int i = 0; i < n; i++) 
		{
			Vector v = new Vector(size);
			population.add(v);
		}
		population = getFitnessValues(population);
		return population;
	}

	public List<Vector> getFitnessValues(List<Vector> population)
	{
		for (Vector v : population) 
		{
			v.fitness = 0;
			int count = 1;
			for (int i = 1; i < size; i++)
			{
				if (v.vector[i] == v.vector[i-1])
					count++;
				else
					break;
			}
			if (count > threshold && v.vector[0] == 0)
				count = threshold;
			v.fitness = count;
		}
		return population;
	}
	
	public boolean foundSolution(List<Vector> population) {
		for (Vector v : population) {
			if (v.fitness == size || leadZeros(population))
				return true;
		}
		return false;
	}
	
	private boolean leadZeros(List<Vector> population){
		for (Vector v : population)
		{
			boolean found = true;
			for (int i : v.vector)
				if (i == 1)
					found = false;
			if (found)
				return true;
		}
		return false;
	}

}
