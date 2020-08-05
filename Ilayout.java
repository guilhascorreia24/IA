import java.util.ArrayList;
import java.util.List;

interface Ilayout {
    /**
     * @return the children of the receiver.
     * @throws CloneNotSupportedException
     */
    List<Ilayout> children() throws CloneNotSupportedException;

    /**
     * @return true if the receiver equals the argumentl; return false otherwise.
     */
    boolean isGoal(Ilayout l);

    /** @return the cost for moving from the input config to the receiver. */
    double getG();

    double getF();

    double getH();

    void setG(double x);
}

class Board implements Ilayout, Cloneable {
    private static final int dim = 3;
    private int board[][];
    private double g,f,h;

    public Board() {
        board = new int[dim][dim];
    }

    public Board(String str) throws IllegalStateException {
        if (str.length() != dim * dim)
            throw new IllegalStateException("Invalid arg in Board constructor");
        board = new int[dim][dim];
        int si = 0;
        for (int i = 0; i < dim; i++)
            for (int j = 0; j < dim; j++) {
                board[i][j] = Character.getNumericValue(str.charAt(si++));
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

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Board clone=new Board();
        for(int i=0;i<dim;i++)
            for(int j=0;j<dim;j++)
                clone.board[i][j]=board[i][j];
        return clone;
    }

    @Override
    public List<Ilayout> children() throws CloneNotSupportedException  {
        List<Ilayout> c=new ArrayList<>();
        int i=-1,j=-1;
        boolean p=false;
        for(i=0;i<dim;i++){
            for(j=0;j<dim;j++){
        //System.out.println(i+" "+j);
        // System.out.println("---------------");
            Board clone= (Board) clone();
            //System.out.println(clone+" inicial");
            clone.board[i][j]=board[(dim-i-1)%dim][j];
            clone.board[(dim-i-1)%dim][j]=board[i][j];
            clone.setG(value_change(board[i][j],board[(dim-i-1)%dim][j]));
            clone.setH(heuristc(i, j, (dim-i-1)%dim, j));
            //System.out.println(clone);

            if(!c.contains(clone))
                c.add(clone);
             clone= (Board) clone();
            //System.out.println(clone+" inicial");
            clone.board[i][j]=board[i][(j+1)%dim];
            clone.board[i][(j+1)%dim]=board[i][j];
            clone.setG(value_change(board[i][j],board[i][(j+1)%dim]));
            //System.out.println(clone);
            if(!c.contains(clone))
                c.add(clone);

             clone= (Board) clone();
            //System.out.println(clone+" inicial");
            clone.board[i][j]=board[i][(dim-j-1)%dim];
            clone.board[i][(dim-j-1)%dim]=board[i][j];
            clone.setG(value_change(board[i][j], board[i][(dim-j-1)%dim]));
            //System.out.println(clone);
            if(!c.contains(clone))
                c.add(clone);

             clone= (Board) clone();
            //System.out.println(this+" inicial");
            clone.board[i][j]=board[(i+1)%dim][j];
            clone.board[(i+1)%dim][j]=board[i][j];
            clone.setG(value_change(board[i][j], board[(i+1)%dim][j]));
            //System.out.println(clone);
            if(!c.contains(clone))
                c.add(clone);

            clone= (Board) clone();
            //System.out.println(this+" inicial");
            clone.board[i][j]=board[(i+1)%dim][(j+1)%dim];
            clone.board[(i+1)%dim][(j+1)%dim]=board[i][j];
            clone.setG(value_change(board[i][j], board[(i+1)%dim][(j+1)%dim]));
            //System.out.println(clone);
            if(!c.contains(clone))
                c.add(clone);

            clone= (Board) clone();
            //System.out.println(this+" inicial");
            clone.board[i][j]=board[(i+1)%dim][(dim-j-1)%dim];
            clone.board[(i+1)%dim][(dim-j-1)%dim]=board[i][j];
            clone.setG(value_change(board[i][j], board[(i+1)%dim][(dim-j-1)%dim]));
            //System.out.println(clone);
            if(!c.contains(clone))
                c.add(clone);

            clone= (Board) clone();
            //System.out.println(this+" inicial");
            clone.board[i][j]=board[(dim-i-1)%dim][(dim-j-1)%dim];
            clone.board[(dim-i-1)%dim][(dim-j-1)%dim]=board[i][j];
            clone.setG(value_change(board[i][j], board[(dim-i-1)%dim][(dim-j-1)%dim]));
            //System.out.println(clone);
            if(!c.contains(clone))
                c.add(clone);

            clone= (Board) clone();
            //System.out.println(this+" inicial");
            clone.board[i][j]=board[(dim-i-1)%dim][(j+1)%dim];
            clone.board[(dim-i-1)%dim][(j+1)%dim]=board[i][j];
            clone.setG(value_change(board[i][j], board[(dim-i-1)%dim][(j+1)%dim]));
            //System.out.println(clone);
            if(!c.contains(clone))
                c.add(clone);
        }
    }

        //System.out.println(c);
        //System.out.println("---------------");
        return c;
    }

    @Override
    public boolean isGoal(Ilayout l) {
        Board b=(Board) l;
        for(int i=0;i<dim;i++){
            for(int j=0;j<dim;j++){
                if(board[i][j]!=b.board[i][j])
                    return false;
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object b){
        if(b instanceof Board){
            Board b1=(Board) b;
            return isGoal(b1);
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

    private double value_change(int ax,int ay,int bx,int by){
        
        if(board[ax][ay]%2!=0 && board[bx][by]%2!=0)
            return 1;
        else if()
        else if(board[ax][ay]%2==0 && board[bx][by]%2==0)
            return 20;
        else
            return 5;
    }

    @Override
    public double getF(){
        return g+h;
    }

    @Override
    public double getH(){
        return h;
    }

    public void setH(double h){
        this.h=h;
    }

    public double heuristc(Board b){
        for(int i=0;i<dim;i++){
            for(int j=0;j<dim;j++){

            }
        }
    }
}