import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        Random generator = new Random(0);
        Algoritmo.generator = generator;
        Scanner sc = new Scanner(System.in);
        //F
        //Algoritmo.Population f=new Algoritmo.Population(sc.nextInt(), sc.nextInt());System.out.print(f);
        
        //G
        //Algoritmo.Individual g=new Algoritmo.Individual(sc.next());System.out.println(g.Onemax());
        
        //H
        //Algoritmo.Individual H=new Algoritmo.Individual(sc.next());System.out.println(H.Square());
        
        //I
        /*Algoritmo.Population I= new Algoritmo.Population(sc.nextInt(), sc.nextInt());
        List<Double> fitnesses=new ArrayList<>();
        while(sc.hasNext()){
            fitnesses.add(sc.nextDouble());
        }
        I.setFitnesses(fitnesses);
        System.out.print(Algoritmo.tournament(I));*/
        //J
        /*Algoritmo.Population j=new Algoritmo.Population();
        while(sc.hasNext()){
            Algoritmo.Individual p=new Algoritmo.Individual(sc.next());
            p.setFitness(Double.parseDouble(sc.next()));
            j.newIndividual(p);
        }
        System.out.print(Algoritmo.Rollete(j));*/
        //K
        /*Algoritmo.Population K=new Algoritmo.Population();
        while(sc.hasNext()){
            Algoritmo.Individual p=new Algoritmo.Individual(sc.next());
            p.setFitness(Double.parseDouble(sc.next()));
            K.newIndividual(p);
        }
        System.out.print(Algoritmo.SUS(K));*/
        
        //L
        //Algoritmo.Individual parent1=new Algoritmo.Individual(sc.next());
        //Algoritmo.Individual parent2=new Algoritmo.Individual(sc.next());
        //System.out.print(Algoritmo.crossoverOnePoint(L1,L2));

        //M
        //Algoritmo.Individual parent1=new Algoritmo.Individual(sc.next());
        //Algoritmo.Individual parent2=new Algoritmo.Individual(sc.next());
        //System.out.print(Algoritmo.uniformCrossover(parent1, parent2));

        //N
        /*double pm=Double.parseDouble(sc.next());
        Algoritmo.Individual parent2=new Algoritmo.Individual(sc.next());
        System.out.println(Algoritmo.bit_flip_mutation(parent2,pm));*/

        //O
        /*int n=sc.nextInt();
        for(int perm:Algoritmo.Random_permutation(n)){
            System.out.println(perm);
        }*/

        //P
        
        sc.close();
    }
}