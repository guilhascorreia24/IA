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
        private double g,h,f;

        public State(Ilayout l, State n,Ilayout objective) {
            layout = l;
            father = n;
            if (father != null){
                g = father.g + l.getG();
                }
            else{
                g = 0.0;
            }
            h=l.getH(objective);
            f=l.getF();
        }

        public String toString() {
            return layout.toString();
        }

        public double getG() {
            return g;
        }

        public double getF(){
            return f;
        }

        public double getH(){
            return h;
        }

        @Override
        public boolean equals(Object b){
            if(b instanceof State){
                State b1=(State) b;
                return layout.equals(b1.layout);
            }
            return false;
        }
    }

    protected Queue<State> abertos;
    private List<State> fechados;
    private State actual;
    private double max;
    private Ilayout objective;

    final private List<State> sucessores(State n) throws CloneNotSupportedException {
        List<State> sucs = new ArrayList<>();
        List<Ilayout> children = n.layout.children(objective);
        for(Ilayout e: children) {
            if(n.father== null|| !e.equals(n.father.layout)){
                State nn = new State(e, n,objective);sucs.add(nn);
            }}
            return sucs;}
    
    final public Iterator<State> solve(Ilayout s, Ilayout goal) throws CloneNotSupportedException {
        objective= goal;
        abertos = new PriorityQueue<>(10, (s1, s2) -> (int) Math.signum(s1.getF()-s2.getF()));
        fechados = new ArrayList<>();
        actual=new State(s, null,objective);
        abertos.add(actual);
         max=actual.getH();
        List<State> sucs;
        //System.out.println(actual.h);
        while(!goal.equals(actual.layout)){
            if(!abertos.isEmpty()){
                actual=abertos.remove();
                //System.out.println(actual);
            }
            if(goal.equals(actual.layout))
                break;
            else{
            //System.out.println(actual);
            sucs = sucessores(actual);
            
            fechados.add(actual);
            for(State s1:sucs){
                if(actual.father==null){
                    //System.out.println(s1+" "+s1.getG()+"+"+s1.getH());
                }
               // System.out.println(fechados.contains(s1));
                if(!fechados.contains(s1))
                    abertos.add(s1);
                if(s1.layout.equals(objective)){
                    //System.out.println("aqui");
                    actual=s1;}
            }}
           // System.out.print("fechados:"+fechados+"\nabertos:"+abertos);
            if(actual.getG()==12){
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