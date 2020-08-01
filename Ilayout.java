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
}

class Board implements Ilayout, Cloneable {
    private static final int dim = 3;
    private int board[][];

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
                s += board[i][j];
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
        int i=0,j=0;
        boolean p=false;
        for(i=0;i<dim;i++){
            for(j=0;j<dim;j++){
                if(board[i][j]==0){
                    //System.out.println(i+" "+j);
                    p=true;
                    break;}
            }
            if(p) break;
        }
        //System.out.println(i+" "+j);
       // System.out.println("---------------");
        if(i-1>0){
            Board clone= (Board) clone();
            //System.out.println(clone+" inicial");
            clone.board[i][j]=board[i-1][j];
            clone.board[i-1][j]=0;
            //System.out.println(clone);
            c.add(clone);
        }
        if(j+1<dim){
            Board clone= (Board) clone();
            //System.out.println(clone+" inicial");
            clone.board[i][j]=board[i][j+1];
            clone.board[i][j+1]=0;
            //System.out.println(clone);
            c.add(clone);
        }
        if(j-1>0){
            Board clone= (Board) clone();
            //System.out.println(clone+" inicial");
            clone.board[i][j]=board[i][j-1];
            clone.board[i][j-1]=0;
            //System.out.println(clone);
            c.add(clone);
        }
        if(i+1<dim){
            Board clone= (Board) clone();
            //System.out.println(this+" inicial");
            clone.board[i][j]=board[i+1][j];
            clone.board[i+1][j]=0;
            //System.out.println(clone);
            c.add(clone);
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
    public double getG() {
        return 1;
    }
}