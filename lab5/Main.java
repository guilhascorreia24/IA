import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        Random generator = new Random(0);
        Algoritmo.generator = generator;
        Scanner sc = new Scanner(System.in);
        // F
        // Algoritmo.Population f=new Algoritmo.Population(sc.nextInt(), sc.nextInt());
        // System.out.print(f);

        // G
        // Algoritmo.Individual g=new
        // Algoritmo.Individual(sc.next());System.out.println(g.Onemax());

        // H
        // Algoritmo.Individual H=new
        // Algoritmo.Individual(sc.next());System.out.println(H.Square());

        // I

        Algoritmo.Population I = new Algoritmo.Population(sc.nextInt(), sc.nextInt());
        List<Double> fitnesses = new ArrayList<>();
        while (sc.hasNext()) {
            fitnesses.add(sc.nextDouble());
        }
        I.setFitnesses(fitnesses);
        System.out.print(Algoritmo.tournament(I, fitnesses.size(), 0));

        // J
        /*
         * Algoritmo.Population j=new Algoritmo.Population(); while(sc.hasNext()){
         * Algoritmo.Individual p=new Algoritmo.Individual(sc.next());
         * p.setFitness(Double.parseDouble(sc.next())); j.newIndividual(p); }
         * System.out.print(Algoritmo.Rollete(j));
         */

        // K
        /*
         * Algoritmo.Population K=new Algoritmo.Population(); while(sc.hasNext()){
         * Algoritmo.Individual p=new Algoritmo.Individual(sc.next());
         * p.setFitness(Double.parseDouble(sc.next())); K.newIndividual(p); }
         * System.out.print(Algoritmo.SUS(K));
         */

        // L
        /*
         * Algoritmo.Individual parent1=new Algoritmo.Individual(sc.next());
         * Algoritmo.Individual parent2=new Algoritmo.Individual(sc.next());
         * System.out.print(Algoritmo.crossoverOnePoint(parent1,parent2));
         */

        // M
        // System.out.print(Algoritmo.uniformCrossover(parent1, parent2));

        // N
        // double pm=Double.parseDouble(sc.next());
        /*
         * Algoritmo.Individual parent2=new Algoritmo.Individual(sc.next());
         * System.out.println(Algoritmo.bit_flip_mutation(parent2,pm));
         */

        // O
        /*
         * int n=sc.nextInt(); for(int perm:Algoritmo.Random_permutation(n)){
         * System.out.println(perm); }
         */

        // P
        /*
         * int s=Integer.parseInt(sc.next()); Algoritmo.Population P=new
         * Algoritmo.Population(); while(sc.hasNext()){ Algoritmo.Individual i=new
         * Algoritmo.Individual(sc.next()); i.setFitness(Double.parseDouble(sc.next()));
         * P.newIndividual(i); }
         * System.out.print(Algoritmo.TournamentSelectionWithoutReplacement(P, s));
         */

        // Q
        /*
         * Algoritmo.Population pop = new
         * Algoritmo.Population(Integer.parseInt(sc.next()),
         * Integer.parseInt(sc.next())); int s = Integer.parseInt(sc.next()); double pm
         * = Double.parseDouble(sc.next()); double pc = Double.parseDouble(sc.next());
         * //List<List<Double>> Q = Algoritmo.OneGenerationOnOonemax(pop, s, pm, pc);
         * 
         * int g=Integer.parseInt(sc.next()); List<List<Double>> Q =
         * Algoritmo.AGeneticAlgorithmOnOnemax(pop, s, pm, pc, g);
         * 
         * 
         * DecimalFormatSymbols unusualSymbols = new DecimalFormatSymbols();
         * unusualSymbols.setDecimalSeparator('.'); DecimalFormat df = new
         * DecimalFormat("0.00", unusualSymbols); int i=0; for (List<Double> x : Q) {
         * System.out.println(i+": "+df.format(x.get(0)) + " " + df.format(x.get(1)) +
         * " " + df.format(x.get(2))); i++; }
         */
        sc.close();
    }
}