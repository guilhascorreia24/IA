import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class Algoritmo {
    public static Random generator;

    public static class Individual {
        private String individual = "";
        private double fitness = 0;

        public Individual(String individual) {
            this.individual = individual;
        }

        public void setFitness(double fitness) {
            this.fitness = fitness;
        }

        public int size(){
            return individual.split("").length;
        }
        public Individual() {
        };

        public String toString() {
            return individual;
        }

        public Individual compareFitnesses(Individual individual2) {
            if (individual2.fitness > fitness) {
                return individual2;
            } else {
                return this;
            }
        }

        public int Square() {
            char[] bits = this.individual.toCharArray();
            int res = 0;
            for (int i = bits.length - 1; i >= 0; i--) {
                int exp = (bits.length - 1) - i;
                double n = (bits[i] == '0') ? 0 : Math.pow((bits[i] - '0') * 2, exp);
                // System.out.println((bits[i]-'0')+" "+exp+" "+bits[i]+" "+n);
                res += n;
            }
            return (int) (res * res);
        }

        public int Onemax() {
            char[] bits = this.individual.toCharArray();
            int res = 0;
            for (char bit : bits) {
                if (bit == 49) {
                    res++;
                }
            }
            return res;
        }

    }

    public static class Population implements Cloneable {
        private List<Individual> population = new ArrayList<>();
        private double fitness_total = 0;
        private int size_population = 0;

        public Population(int size, int ChromosomeSize) {
            for (int i = 0; i < size * ChromosomeSize; i++) {
                int h = (generator.nextDouble() < 0.5) ? 0 : 1;
                if (population.size() <= i / ChromosomeSize)
                    newIndividual(new Individual(String.valueOf(h)));
                else {
                    // System.out.println(i+" "+i/ChromosomeSize);
                    population.get(i / ChromosomeSize).individual += String.valueOf(h);
                }
            }
        }

        public Population() {
        };

        public void setFitnesses(List<Double> fitnesses) {
            int i = 0;
            for (Individual x : population) {
                x.setFitness(fitnesses.get(i));
                i++;
            }
            if (size_population > fitness_total)
                this.size_population += i - size_population;
        }

        public void newIndividual(Individual individ) {
            population.add(individ);
            this.size_population += 1;
            fitness_total += individ.fitness;
        }

        @Override
        public Object clone() throws CloneNotSupportedException {
            Population clone = (Population) super.clone();
            clone.population = new ArrayList<>(population);
            clone.fitness_total = fitness_total;
            return clone;
        }

        public String toString() {
            String s = "";
            for (Individual x : population) {
                s += x.toString() + "\n";
            }
            return s;
        }

    }

    public static Population tournament(Population pop, int size_population, int inicial_population) {
        int i = inicial_population;
        // int size_population = pop.population.size();
        Population tournament_res = new Population();
        while (i < size_population) {
            Individual individual1 = pop.population.get((int) (inicial_population
                    + Math.round(generator.nextDouble() * (pop.size_population - 1 - inicial_population))));
            Individual individual2 = pop.population.get((int) (inicial_population
                    + Math.round(generator.nextDouble() * (pop.size_population - 1 - inicial_population))));
            System.out.println(individual1 + " " + individual2);
            tournament_res.newIndividual(individual1.compareFitnesses(individual2));
            i++;
        }
        return tournament_res;
    }

    public static Population Rollete(Population pop) {
        int i = 0;
        int size_population = pop.population.size();
        Collections.sort(pop.population, new Comparator<Individual>() {
            @Override
            public int compare(Individual s, Individual s1) {
                if (s.fitness < s1.fitness)
                    return 1;
                if (s.fitness > s1.fitness)
                    return -1;
                return 0;
            }
        });
        Population rollete_res = new Population();
        while (i < size_population) {
            Individual individual1 = pop.population.get(i);
            double roll = generator.nextDouble();
            double fit_sum = 0;
            for (Individual ind : pop.population) {
                if (roll >= fit_sum && roll < fit_sum + ind.fitness / pop.fitness_total) {
                    individual1 = ind;
                    break;
                }
                fit_sum += ind.fitness / pop.fitness_total;
            }
            rollete_res.population.add(individual1);
            i++;
        }
        Collections.sort(rollete_res.population, new Comparator<Individual>() {
            @Override
            public int compare(Individual s, Individual s1) {
                if (s.Square() < s1.Square())
                    return -1;
                if (s.Square() > s1.Square())
                    return 1;
                return 0;
            }
        });
        return rollete_res;
    }

    public static Population SUS(Population pop) {
        double total_fitnesss = pop.fitness_total;
        double size_poppulation = pop.population.size();
        double distance_pointers = total_fitnesss / size_poppulation;
        double roll = generator.nextDouble() * distance_pointers;
        double[] pointers = new double[(int) size_poppulation];
        for (int i = 0; i < size_poppulation; i++) {
            pointers[i] = roll + i * distance_pointers;
        }
        return RWS(pop, pointers);
    }

    private static Population RWS(Population pop, double[] pointers) {
        Population keep = new Population();
        // System.out.println(pointers[0]);
        for (double pointer : pointers) {
            int i = 0;
            double sum_fit = pop.population.get(i).fitness;
            // System.out.println(pointer);
            while (sum_fit < pointer) {
                i++;
                sum_fit += pop.population.get(i).fitness;
            }
            keep.population.add(pop.population.get(i));
        }
        return keep;
    }

    public static Population crossoverOnePoint(Algoritmo.Individual parent1, Algoritmo.Individual parent2) {
        int point_cross = (int) (0 + Math.round(generator.nextDouble() * (parent1.size() - 0)));
        // System.out.println(point_cross);
        String cross1 = parent1.individual.substring(point_cross, parent1.size());
        String cross2 = parent2.individual.substring(point_cross, parent2.size());
        Individual child1 = new Individual(parent1.individual.substring(0, point_cross) + cross2);
        Individual child2 = new Individual(parent2.individual.substring(0, point_cross) + cross1);
        Population pop = new Population();
       // System.out.println(point_cross);
        pop.newIndividual(child1);
        pop.newIndividual(child2);
        return pop;
    }

    public static Population uniformCrossover(Algoritmo.Individual parent1, Algoritmo.Individual parent2) {
        Individual child1 = new Individual(), child2 = new Individual();
        Population childs = new Population();
        childs.newIndividual(child1);
        childs.newIndividual(child2);
        for (int i = 0; i < parent1.size(); i++) {
            double coin_toss = generator.nextDouble();
            if (coin_toss < 0.5) {
                child2.individual += parent1.individual.substring(i, i + 1);
                child1.individual += parent2.individual.substring(i, i + 1);
            } else {
                child1.individual += parent1.individual.substring(i, i + 1);
                child2.individual += parent2.individual.substring(i, i + 1);
            }
        }
        return childs;
    }

    public static Individual bit_flip_mutation(Algoritmo.Individual parent1, double pm) {
        Individual child1 = new Individual();
        for (int i = 0; i < parent1.size(); i++) {
            double coin_toss = generator.nextDouble();
            if (coin_toss < pm) {
                ;
                child1.individual += (Integer.parseInt(parent1.individual.substring(i, i + 1)) == 0) ? 1 : 0;
            } else {
                child1.individual += parent1.individual.substring(i, i + 1);
            }
        }
        return child1;
    }

    public static int[] Random_permutation(int N) {
        int[] res1 = new int[N];
        for (int i = 0; i < N; i++) {
            res1[i] = i;
        }
        for (int i = 0; i < N - 1; i++) {
            int r = (int) (i + Math.round(generator.nextDouble() * (N - 1 - i)));
            int exc = res1[r];
            res1[r] = res1[i];
            res1[i] = exc;
        }
        return res1;
    }

    public static Population TournamentSelectionWithoutReplacement(Population pop, int s)
            throws CloneNotSupportedException {
        int N = pop.size_population;
        Population clone = (Population) pop.clone(), clone2 = new Population();
        int j = 0;
        while (j < s) {
            int[] h = Random_permutation(N);
            for (int i = 0; i < N; i++) {
                clone.population.set(i, pop.population.get(h[i]));
            }
            for (int i = 0; i < N / s; i++) {
                Individual ind = Collections.max(clone.population.subList(i * s, i * s + s),
                        new Comparator<Individual>() {
                            @Override
                            public int compare(Individual s, Individual s1) {
                                if (s.fitness > s1.fitness)
                                    return 1;
                                if (s.fitness < s1.fitness)
                                    return -1;
                                return 0;
                            }
                        });
                clone2.newIndividual(ind);
            }
            clone = (Population) pop.clone();
            j++;
        }
        return clone2;
    }

    public static double[][] OneGenerationOnOonemax(Population pop, int s, double pm, double pc)
            throws CloneNotSupportedException {
        double[][] res = new double[2][3];
        int i = 0;
        while (i < 1) {
            System.out.println("inicial");
            for (Individual x : pop.population) {
                System.out.println(x + " " + x.Onemax());
            }
            Population pop1 = TournamentSelectionWithoutReplacement(pop, s);
            System.out.println("torneio");
            for (Individual x : pop1.population) {
                System.out.println(x + " " + x.Onemax());
            }
            System.out.println("crossover");
            for (int j = 0; j < pop1.size_population; j += 2) {
                Individual parent1 = pop1.population.get(j);
                Individual parent2 = pop1.population.get(j + 1);
                if(generator.nextDouble()<pc){
                    Population p=crossoverOnePoint(parent1, parent2);
                    pop.population.set(j, p.population.get(0));
                    pop.population.set(j+1,p.population.get(1));
                }

            }
            for (Individual x : pop.population) {
                System.out.println(x + " " + x.Onemax()+" "+x.size());
            }
            i++;
        }
        return null;
    }

}
