package ia2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;



class BestFirst {
    static class State {
        private Ilayout layout;
        private State father;
        private double g;

        public State(Ilayout l, State n) {
            layout = l;
            father = n;
            if (father != null)
                g = father.g + l.getG();
            else
                g = 0.0;
        }

        public String toString() {
            return layout.toString();
        }

        public double getG() {
            return g;
        }

        @Override
        public boolean equals(Object b){
            if(b instanceof State){
                State b1=(State) b;
                return layout.equals(b1.layout) && g==b1.g && father.equals(b1);
            }
            return false;
        }
    }

    protected Queue<State> abertos;
    private List<State> fechados;
    private State actual;
    private Ilayout objective;

    final private List<State> sucessores(State n) throws CloneNotSupportedException {
        List<State> sucs = new ArrayList<>();
        List<Ilayout> children = n.layout.children();
        for(Ilayout e: children) {
            if(n.father== null|| !e.equals(n.father.layout)){
                State nn = new State(e, n);sucs.add(nn);
            }}
            return sucs;}
    
    final public Iterator<State> solve(Ilayout s, Ilayout goal) throws CloneNotSupportedException {
        objective= goal;
        abertos = new PriorityQueue<>(10, (s1, s2) -> (int) Math.signum(s1.getG()-s2.getG()));
        fechados = new ArrayList<>();
        abertos.add(new State(s, null));
        actual=new State(s, null);
        List<State> sucs;
        while(!goal.equals(actual.layout)){
            if(!abertos.isEmpty()){
                actual=abertos.poll();
            }
            if(goal.equals(actual.layout))
                break;
            else{
            //System.out.println(actual);
            sucs = sucessores(actual);
            fechados.add(actual);
            for(State s1:sucs){
                //System.out.println(s1);
                if(!fechados.contains(s1))
                    abertos.add(s1);
                if(s1.layout.equals(objective))
                    actual=s1;
            }}
           // System.out.print("fechados:"+fechados+"\nabertos:"+abertos);
            if(actual.getG()==5){
                //System.out.println("fim"+actual.getG());
                //System.out.println(actual);
                //System.out.println(goal);
                //System.out.println(sucs);
                //System.out.println(goal.equals(actual.layout));
            }
        }
        //abertos.clear();fechados.clear();
        List<State> l=new ArrayList<>();
        l.add(actual);
        while(!actual.layout.equals(s)){
            actual=actual.father;
            l.add(actual);
        }
        //System.out.println(l);
        Collections.reverse(l);
        return  l.iterator();
    }
}