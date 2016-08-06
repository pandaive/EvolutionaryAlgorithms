import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class Main 
{
	//problems
	static final int ONEMAXPROBLEM = 0;
	static final int LOLZPROBLEM = 1;
	static final int SEQUENCES = 2;
	
	//adult selection
	static final int FULLGENERATIONREPLACEMENT = 0;
	static final int OVERPRODUCTIONREPLACEMENT = 1;
	static final int GENERATIANOALMIXING = 2;

	//parent selection
	static final int BESTFITNESS = 0;
	static final int FITNESSPROPORTIONATE = 1;
	static final int SIGMASCALING = 2;
	static final int TOURNAMENT = 3;
	
	
	static List<Vector> population;
	static List<Vector> genotypes;
	static List<Vector> parents;
	static List<Integer> fitnessHistory;
	
	static int problem;
	static OneMaxProblem oneMaxProblem;
	static LOLZProblem lolzProblem;
	static SurprisingSequences surprisingSequences;
	
	
	static int adultSelectionChoice;
	static int parentSelectionChoice;
	static AdultSelection adultSelection;
	static ParentSelection parentSelection;
	static Crossover crossover;
	static Mutation mutation;

	static int initPopulationSize = 20;
	static int maxAdults = 100;
	static int maxParents = 10;
	static double mutationThreshold = 0.9;
	static double crossoverThreshold = 0.5;
	static int symbols = 2;
	static int sequencesLength = 10;
	static int size = 20;
	static int leadingZeros = 11;
	static boolean globalSeq = false;
	static boolean findLongest = false;
	static int[] longest;
	
	private static void createPopulation(){
		switch(problem)
		{
		case ONEMAXPROBLEM:
			population = oneMaxProblem.createPopulation(initPopulationSize);
			break;
		case LOLZPROBLEM:
			population = lolzProblem.createPopulation(initPopulationSize);
			break;
		case SEQUENCES:
			population = surprisingSequences.createPopulation(initPopulationSize);
			break;
		}
	}
	
	private static void getFitnessValues(){
		switch(problem)
		{
		case ONEMAXPROBLEM:
			population = oneMaxProblem.getFitnessValues(population);
			break;
		case LOLZPROBLEM:
			population = lolzProblem.getFitnessValues(population);
			break;
		case SEQUENCES:
			population = surprisingSequences.getFitnessValues(population);
			break;
		}
	}
	
	private static boolean foundSolution(){
		switch(problem) 
		{
		case ONEMAXPROBLEM:
			return oneMaxProblem.foundSolution(population);
		case LOLZPROBLEM:
			return lolzProblem.foundSolution(population);
		case SEQUENCES:
			return surprisingSequences.foundSolution(population);
		}
		
		return false;
	}
	
	private static void getParams() throws NumberFormatException, IOException{
		BufferedReader br = new BufferedReader(new FileReader("parameters.txt"));
		br.readLine();
		problem = Integer.parseInt(br.readLine()); br.readLine();
		adultSelectionChoice = Integer.parseInt(br.readLine()); br.readLine();
		parentSelectionChoice = Integer.parseInt(br.readLine()); br.readLine();
		initPopulationSize = Integer.parseInt(br.readLine()); br.readLine();
		maxAdults = Integer.parseInt(br.readLine()); br.readLine();
		maxParents = Integer.parseInt(br.readLine()); br.readLine();
		symbols = Integer.parseInt(br.readLine()); br.readLine();
		mutationThreshold = (double)Integer.parseInt(br.readLine())/100.0; br.readLine();
		crossoverThreshold = (double)Integer.parseInt(br.readLine())/100.0; br.readLine();
		size = Integer.parseInt(br.readLine()); br.readLine();
		leadingZeros = Integer.parseInt(br.readLine()); br.readLine();
		sequencesLength = Integer.parseInt(br.readLine()); br.readLine();
		globalSeq = Integer.parseInt(br.readLine()) == 1 ? true : false; br.readLine();
		findLongest = Integer.parseInt(br.readLine()) == 1 ? true : false; br.readLine();
		br.close();
		
	}
	
	private static void saveFitnessHistory() throws FileNotFoundException, UnsupportedEncodingException{
		PrintWriter writer = new PrintWriter("results.txt", "UTF-8");
		for (Integer i : fitnessHistory)
			writer.println(i.toString());
		writer.close();
	}
	
	private static double avgFitness(){
		int sum = 0;
		for (Vector v : population)
			sum += v.fitness;
		return (double)sum/population.size();
	}
	
	private static double stdDev(){
		double variance = 0;
		double avgFitness = avgFitness();
		for (Vector v : population)
			variance += Math.pow((avgFitness - (double) v.fitness), 2);
		variance /= population.size();
				
		return Math.sqrt(variance);
	}
	
	private static void initMethods(){
		adultSelection = new AdultSelection(adultSelectionChoice, maxAdults);
		parentSelection = new ParentSelection(parentSelectionChoice);
		crossover = new Crossover();
		mutation = new Mutation(symbols);
	}
	
	public static void main(String[] args) throws NumberFormatException, IOException 
	{
		getParams();
		
		oneMaxProblem = new OneMaxProblem(size);
		lolzProblem = new LOLZProblem(size, leadingZeros);
		surprisingSequences = new SurprisingSequences(sequencesLength ,symbols, globalSeq);
		
		initMethods();
		if (!(problem == SEQUENCES && findLongest))
		{
			fitnessHistory = new ArrayList<Integer>();
			int iteration = 0;
			createPopulation();
			while (!foundSolution())
			{
				iteration++;
				population = adultSelection.selectAdults(population);
	
				parents = parentSelection.selectParents(population, maxParents);
	
				for (Vector v : population) {
					v.generation += 1;
				}
	
				for (int i = 0; i < parents.size()-1; i += 2) 
				{
					population.addAll(mutation.mutateAll(crossover.cross(parents.get(i).vector, parents.get(i+1).vector, crossoverThreshold), mutationThreshold));
				}
				
				getFitnessValues();
				System.out.println("Generation: " + iteration);
				Collections.sort(population);
				System.out.println("Best: " + Arrays.toString(population.get(population.size()-1).vector));
				System.out.println("Fitness: " + population.get(population.size()-1).fitness);
				fitnessHistory.add(population.get(population.size()-1).fitness);
				System.out.println("Average fitness " + avgFitness());
				System.out.println("Standard dev. " + stdDev());
				System.out.println();
			}
			System.out.println("Found in " + iteration + " iterations");
			saveFitnessHistory();
			
			/*
			for (Vector v : population)
				System.out.println("fit: " + v.fitness + " v: " + Arrays.toString(v.vector));
				*/
		}
		else
		{
			
			sequencesLength = symbols;
			while(true)
			{
				surprisingSequences = new SurprisingSequences(sequencesLength ,symbols, globalSeq);
				int iteration = 0;
				createPopulation();
				while (!foundSolution())
				{
					iteration++;
					population = adultSelection.selectAdults(population);
		
					parents = parentSelection.selectParents(population, maxParents);
		
					for (Vector v : population) {
						v.generation += 1;
					}
		
					for (int i = 0; i < parents.size()-1; i += 2) 
					{
						population.addAll(mutation.mutateAll(crossover.cross(parents.get(i).vector, parents.get(i+1).vector, crossoverThreshold), mutationThreshold));
					}
					
					getFitnessValues();
					Collections.sort(population);
				}
				System.out.println("Found " + sequencesLength + " long sequence in " + iteration + " iterations");
				longest = population.get(population.size()-1).vector;
				System.out.println(Arrays.toString(longest));
				
				sequencesLength++;
			}
		}
	}

}