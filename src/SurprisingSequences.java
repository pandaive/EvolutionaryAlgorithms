import java.util.ArrayList;
import java.util.List;

public class SurprisingSequences implements EAProblem {
	
	int symbols;
	int size;
	boolean global;
	int combinations;
	
	public SurprisingSequences(int size, int symbols, boolean global) {
		this.size = size;
		this.symbols = symbols;
		this.global = global;
		combinations = 0;
		int tmpSize = size;
		while((--tmpSize)>0)
			combinations+=tmpSize;
	}

	public List<Vector> createPopulation(int n) 
	{
		List<Vector> population = new ArrayList<Vector>();
		for (int i = 0; i < n; i++) 
		{
			Vector v = new Vector(size, symbols);
			population.add(v);
		}
		population = getFitnessValues(population);
		return population;
	}

	@Override
	public List<Vector> getFitnessValues(List<Vector> population) {
		if (!global)
		{
			int k = 0;
			for (Vector v : population)
			{
				int[][] local = zero2DMatrix();
				int tmpFit = 0;
				for (int i = 1; i < size; i++)
				{
					local[v.vector[i-1]][v.vector[i]]++;
				}
				for (int i = 0; i < symbols; i++)
					for (int j = 0; j < symbols; j++) {
						tmpFit += local[i][j]*local[i][j];
					}
				tmpFit = (size-1)*(size-1) - tmpFit;
				population.get(k).fitness = tmpFit;
				k++;
			}
			
			return population;
		}
		else
		{
			int k = 0;
			for (Vector v : population)
			{
				int[][][] global = zero3DMatrix();
				int tmpFit = 0;
				for(int i = 0; i < size; i++)
					for (int j = i+1; j < size; j++)
					{
						global[v.vector[i]][v.vector[j]][j-i-1]++;
					}
				for (int i = 0; i < symbols; i++)
					for (int j = 0; j < symbols; j++)
						for (int l = 0; l < size-1; l++)
						{
						tmpFit += global[i][j][l]*global[i][j][l];
						}
				tmpFit = (size-1)*(size-1)*(size-1) - tmpFit;
				population.get(k).fitness = tmpFit;
				k++;
			}
			return population;
		}
	}
	
	private int[][] zero2DMatrix()
	{
		int[][] matrix = new int[symbols][symbols];
		for (int i = 0; i < symbols; i++)
			for (int j = 0; j < symbols; j++)
				matrix[i][j] = 0;
		
		return matrix;
	}
	
	private int[][][] zero3DMatrix()
	{
		int[][][] matrix = new int[symbols][symbols][size-1];
		for (int i = 0; i < symbols; i++)
			for (int j = 0; j < symbols; j++)
				for (int k = 0; k < size-1; k++)
					matrix[i][j][k] = 0;
		
		return matrix;
	}
	

	@Override
	public boolean foundSolution(List<Vector> population) {
		if (!global)
		{
			for (Vector v : population)
				if (v.fitness >= ((size-1)*(size-1))-(size-1))
					return true;
		}
		else
		{
			for (Vector v : population)
				if (v.fitness >= ((size-1)*(size-1)*(size-1))-combinations)
				{
					return true;
				}
		}
		return false;
	}

}
