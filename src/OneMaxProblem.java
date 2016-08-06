import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
 * implementation of one-max problem
 */
public class OneMaxProblem implements EAProblem {
	
	int size;
	int[] target;
	
	public OneMaxProblem(int size) {
		this.size = size;
		target = newTarget();
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
			for (int i : v.vector)
				if (i == 1)
					v.fitness += 1;
		}
		return population;
	}
	
	public boolean foundSolution(List<Vector> population) {
		for (Vector v : population) {
			if (v.fitness == size)
				return true;
		}
		return false;
	}
	/*
	public boolean foundSolution(List<Vector> population) {
		boolean found = true;
		for (Vector v : population) {
			int j = -1;
			for (int i : v.vector) {
				j++;
				if (i != target[j])
					found = false;
			}
			if (found) return true;
		}
		return false;
	}
	*/
	public int[] newTarget(){
		int[] tmp = new int[size];
		Random random = new Random();
		for (int i = 0; i < size; i++)
			tmp[i] = random.nextInt(2);
		return tmp;
	}
}
