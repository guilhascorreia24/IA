import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

interface Ilayout {
    /**
     * @return the children of the receiver.
     * @throws CloneNotSupportedException
     */
    List<Ilayout> children(Ilayout objective);

    /**
     * @return true if the receiver equals the argumentl; return false otherwise.
     */
    boolean isGoal(Ilayout l);

    /** @return the cost for moving from the input config to the receiver. */
    double getG();

    double getF();

    double getH(Ilayout b);

    void setG(double x);
}

class Board implements Ilayout, Cloneable {
    private static final int dim = 3;
    private int board[][];
    private double g, h;
    private HashMap<Integer, Ponto> list = new HashMap<Integer, Ponto>();

    public Board() {
        board = new int[dim][dim];
    }

    class Ponto {
        private int x, y;

        public Ponto(int x, int y) {
            this.setX(x);
            this.setY(y);
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public String toString(){
            return x+" "+y;
        }
    }

    public Board(String str) throws IllegalStateException {
        if (str.length() != dim * dim)
            throw new IllegalStateException("Invalid arg in Board constructor");
        board = new int[dim][dim];
        int si = 0;
        for (int i = 0; i < dim; i++)
            for (int j = 0; j < dim; j++) {
                board[i][j] = Character.getNumericValue(str.charAt(si++));
                list.put(board[i][j], new Ponto(i,j));
            }

    }

    public String toString() {
        String s = "";
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if(board[i][j]!=0)
                    s += board[i][j];
                else
                    s+=" ";
            }
            s += "\n";
        }
        return s;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Object clone()  {
        Board clone=new Board();
        for(int i=0;i<dim;i++)
            for(int j=0;j<dim;j++)
                clone.board[i][j]=board[i][j];
        clone.list= (HashMap<Integer, Ponto>) list.clone();
        return clone;
    }

    @Override
    public List<Ilayout> children(Ilayout objective)  {
        List<Ilayout> c=new ArrayList<>();
        int i=-1,j=-1;
        for(i=0;i<dim;i++){
            for(j=0;j<dim;j++){
        //System.out.println(i+" "+j);
        // System.out.println("---------------");
            Board clone= (Board) clone();
            //System.out.println(clone+" inicial");
            clone.board[i][j]=board[(dim-i-1)%dim][j];
            clone.board[(dim-i-1)%dim][j]=board[i][j];
            clone.setG(value_change(board[i][j],board[(dim-i-1)%dim][j]));
            //System.out.println(clone);

            if(!c.contains(clone)){
                c.add(clone);}
             clone= (Board) clone();
            //System.out.println(clone+" inicial");
            clone.board[i][j]=board[i][(j+1)%dim];
            clone.board[i][(j+1)%dim]=board[i][j];
            clone.setG(value_change(board[i][j],board[i][(j+1)%dim]));
            //System.out.println(clone);
            if(!c.contains(clone)){
                clone.h-=clone.g;
                clone.heuristc(objective, i, j);
                c.add(clone);
            }

             clone= (Board) clone();
            //System.out.println(clone+" inicial");
            clone.board[i][j]=board[i][(dim-j-1)%dim];
            clone.board[i][(dim-j-1)%dim]=board[i][j];
            clone.setG(value_change(board[i][j], board[i][(dim-j-1)%dim]));
            //System.out.println(clone);
            if(!c.contains(clone)){
                clone.h-=clone.g;
                clone.heuristc(objective, i, j);
                c.add(clone);}

             clone= (Board) clone();
            //System.out.println(this+" inicial");
            clone.board[i][j]=board[(i+1)%dim][j];
            clone.board[(i+1)%dim][j]=board[i][j];
            clone.setG(value_change(board[i][j], board[(i+1)%dim][j]));
            //System.out.println(clone);
            if(!c.contains(clone)){
                clone.h-=clone.g;
                clone.heuristc(objective, i, j);
                c.add(clone);}

            clone= (Board) clone();
            //System.out.println(this+" inicial");
            clone.board[i][j]=board[(i+1)%dim][(j+1)%dim];
            clone.board[(i+1)%dim][(j+1)%dim]=board[i][j];
            clone.setG(value_change(board[i][j], board[(i+1)%dim][(j+1)%dim]));
            //System.out.println(clone);
            if(!c.contains(clone)){
                clone.h-=clone.g;
                clone.heuristc(objective, i, j);
                c.add(clone);
            }

            clone= (Board) clone();
            //System.out.println(this+" inicial");
            clone.board[i][j]=board[(i+1)%dim][(dim-j-1)%dim];
            clone.board[(i+1)%dim][(dim-j-1)%dim]=board[i][j];
            clone.setG(value_change(board[i][j], board[(i+1)%dim][(dim-j-1)%dim]));
            //System.out.println(clone);
            if(!c.contains(clone)){
                clone.h-=clone.g;
                clone.heuristc(objective, i, j);
                c.add(clone);
            }

            clone= (Board) clone();
            //System.out.println(this+" inicial");
            clone.board[i][j]=board[(dim-i-1)%dim][(dim-j-1)%dim];
            clone.board[(dim-i-1)%dim][(dim-j-1)%dim]=board[i][j];
            clone.setG(value_change(board[i][j], board[(dim-i-1)%dim][(dim-j-1)%dim]));
            //System.out.println(clone);
            if(!c.contains(clone)){
                clone.h-=clone.g;
                clone.heuristc(objective, i, j);
                c.add(clone);}

            clone= (Board) clone();
            //System.out.println(this+" inicial");
            clone.board[i][j]=board[(dim-i-1)%dim][(j+1)%dim];
            clone.board[(dim-i-1)%dim][(j+1)%dim]=board[i][j];
            clone.setG(value_change(board[i][j], board[(dim-i-1)%dim][(j+1)%dim]));
            //System.out.println(clone);
            if(!c.contains(clone)){
                clone.h-=clone.g;
                clone.heuristc(objective, i, j);
                c.add(clone);}
        }
    }

        //System.out.println(c);
        //System.out.println("---------------");
        return c;
    }

    @Override
    public boolean isGoal(Ilayout l) {
        return equals(l);
    }

    @Override
    public boolean equals(Object b1){
        if(b1 instanceof Board){
            Board b=(Board) b1;
            
            for(int i=0;i<dim;i++){
                for(int j=0;j<dim;j++){
                    if(board[i][j]!=b.board[i][j])
                        return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public double getG() {
        return g;
    }

    @Override
    public void setG(double x){
        this.g=x;
    }

    private double value_change(int a,int b){
        if(a!=b){
        if(a%2!=0 && b%2!=0)
            return 1;
        else if(a%2!=0 && b%2==0 || a%2==0 && b%2!=0)
            return 5;
        else
            return 15;
        }
        return 0;
    }

    @Override
    public double getF(){
        return g+h;
    }

    @Override
    public double getH(Ilayout b){
        for(int i=0;i<dim;i++){
            for(int j=0;j<dim;j++){
                heuristc(b, i, j);
            }
        }
        return h;
    }

    public void heuristc(Ilayout b1,int i,int j){
        Board b=(Board) b1;
        Board clone=(Board) clone();
               // System.out.println(clone.board[i][j]+"@"+b.board[i][j]);
                if(clone.board[i][j]==b.board[i][j]){}
                else if(clone.board[i][j]%2!=0 && b.board[i][j]%2!=0)
                    h+=1;
                else if((clone.board[i][j]%2==0 && b.board[i][j]%2!=0) || (clone.board[i][j]%2!=0 && b.board[i][j]%2==0)){
                    h+=5;
                }else{
                    h+=15;
                }
                //System.out.println(clone.list);

                int u=clone.board[i][j];
                Ponto p=clone.list.get(b.board[i][j]);
                clone.board[p.x][p.y]=u;
                clone.list.put(b.board[i][j],new Ponto(i,j));
                clone.board[i][j]=b.board[i][j];
                clone.list.put(u,new Ponto(p.x,p.y));
        
        //System.out.println(clone);
        //return h;
    }

}