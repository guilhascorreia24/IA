package ia3;

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
    private static int dim;
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

        public String toString() {
            return x + " " + y;
        }
    }

    public Board(String str) throws IllegalStateException {
        dim = (int) Math.sqrt(str.split("").length);
        if (str.length() != dim * dim)
            throw new IllegalStateException("Invalid arg in Board constructor");
        board = new int[dim][dim];
        int si = 0;
        for (int i = 0; i < dim; i++)
            for (int j = 0; j < dim; j++) {
                board[i][j] = Character.getNumericValue(str.charAt(si++));
                list.put(board[i][j], new Ponto(i, j));
            }

    }

    public String toString() {
        String s = "";
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (board[i][j] <10)
                    s += board[i][j];
                else if(board[i][j]>=10){
                    s+=(char)(65+board[i][j]-10);
                }
                else
                    s += " ";
            }
            s += "\n";
        }
        return s;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Object clone() {
        Board clone = new Board();
        for (int i = 0; i < dim; i++)
            for (int j = 0; j < dim; j++)
                clone.board[i][j] = board[i][j];
        clone.list = (HashMap<Integer, Ponto>) list.clone();
        return clone;
    }

    @Override
    public List<Ilayout> children(Ilayout objective) {
        List<Ilayout> c = new ArrayList<>();
        int i = -1, j = -1;
        for (i = 0; i < dim; i++) {
            for (j = 0; j < dim; j++) {
                int k = 0;
                while (k != dim / 2) {
                    k++;
                    // System.out.println((dim-i-k)%dim);
                    // System.out.println(i+" "+j);
                    // System.out.println("---------------");
                    Board clone = (Board) clone();
                    // System.out.println(clone+" inicial");
                    clone.board[i][j] = board[Math.abs(dim - i - k) % dim][j];
                    clone.board[Math.abs(dim - i - k) % dim][j] = board[i][j];
                    clone.setG(value_change(board[i][j], board[Math.abs(dim - i - k) % dim][j]));
                    // System.out.println(clone);

                    if (!c.contains(clone) && clone.getG() % 2 != 0) {
                        // clone.h-=clone.g;
                        // clone.heuristc(objective, i, j);
                        // clone.h=clone.getH(objective);
                        c.add(clone);
                    }
                    clone = (Board) clone();
                    // System.out.println(clone+" inicial");
                    clone.board[i][j] = board[i][(j + k) % dim];
                    clone.board[i][(j + k) % dim] = board[i][j];
                    clone.setG(value_change(board[i][j], board[i][(j + k) % dim]));
                    // System.out.println(clone);
                    if (!c.contains(clone) && clone.getG() % 2 != 0) {
                        // clone.h-=clone.g;
                        // clone.heuristc(objective, i, j);
                        // clone.h=clone.getH(objective);
                        c.add(clone);
                    }

                    clone = (Board) clone();
                    // System.out.println(clone+" inicial");
                    clone.board[i][j] = board[i][Math.abs(dim - j - k) % dim];
                    clone.board[i][Math.abs(dim - j - k) % dim] = board[i][j];
                    clone.setG(value_change(board[i][j], board[i][Math.abs(dim - j - k) % dim]));
                    // System.out.println(clone);
                    if (!c.contains(clone) && clone.getG() % 2 != 0) {
                        // clone.h-=clone.g;
                        // clone.heuristc(objective, i, j);
                        // clone.h=clone.getH(objective);
                        c.add(clone);
                    }

                    clone = (Board) clone();
                    // System.out.println(this+" inicial");
                    clone.board[i][j] = board[(i + k) % dim][j];
                    clone.board[(i + k) % dim][j] = board[i][j];
                    clone.setG(value_change(board[i][j], board[(i + k) % dim][j]));
                    // System.out.println(clone);
                    if (!c.contains(clone) && clone.getG() % 2 != 0) {
                        // clone.h-=clone.g;
                        // clone.heuristc(objective, i, j);
                        // clone.h=clone.getH(objective);
                        c.add(clone);
                    }

                    clone = (Board) clone();
                    // System.out.println(this+" inicial");
                    clone.board[i][j] = board[(i + k) % dim][(j + k) % dim];
                    clone.board[(i + k) % dim][(j + k) % dim] = board[i][j];
                    clone.setG(value_change(board[i][j], board[(i + k) % dim][(j + k) % dim]));
                    // System.out.println(clone);
                    if (!c.contains(clone) && clone.getG() % 2 != 0) {
                        // clone.h-=clone.g;
                        // clone.heuristc(objective, i, j);
                        // clone.h=clone.getH(objective);
                        c.add(clone);
                    }

                    clone = (Board) clone();
                    // System.out.println(this+" inicial");
                    clone.board[i][j] = board[(i + k) % dim][Math.abs(dim - j - k) % dim];
                    clone.board[(i + k) % dim][Math.abs(dim - j - k) % dim] = board[i][j];
                    clone.setG(value_change(board[i][j], board[(i + k) % dim][Math.abs(dim - j - k) % dim]));
                    // System.out.println(clone);
                    if (!c.contains(clone) && clone.getG() % 2 != 0) {
                        // clone.h-=clone.g;
                        // clone.heuristc(objective, i, j);
                        // clone.h=clone.getH(objective);
                        c.add(clone);
                    }

                    clone = (Board) clone();
                    // System.out.println(this+" inicial");
                    clone.board[i][j] = board[Math.abs(dim - i - k) % dim][Math.abs(dim - j - k) % dim];
                    clone.board[Math.abs(dim - i - k) % dim][Math.abs(dim - j - k) % dim] = board[i][j];
                    clone.setG(
                            value_change(board[i][j], board[Math.abs(dim - i - k) % dim][Math.abs(dim - j - k) % dim]));
                    // System.out.println(clone);
                    if (!c.contains(clone) && clone.getG() % 2 != 0) {
                        // clone.h-=clone.g;
                        // clone.heuristc(objective, i, j);
                        // clone.h=clone.getH(objective);
                        c.add(clone);
                    }

                    clone = (Board) clone();
                    // System.out.println(this+" inicial");
                    clone.board[i][j] = board[Math.abs(dim - i - k) % dim][(j + k) % dim];
                    clone.board[Math.abs(dim - i - k) % dim][(j + k) % dim] = board[i][j];
                    clone.setG(value_change(board[i][j], board[Math.abs(dim - i - k) % dim][(j + k) % dim]));
                    // System.out.println(clone);
                    if (!c.contains(clone) && clone.getG() % 2 != 0) {
                        // clone.h-=clone.g;
                        // clone.heuristc(objective, i, j);
                        // clone.h=clone.getH(objective);
                        c.add(clone);
                    }
                }
            }
        }

        // System.out.println(c);
        // System.out.println("---------------");
        return c;
    }

    @Override
    public boolean isGoal(Ilayout l) {
        return equals(l);
    }

    @Override
    public boolean equals(Object b1) {
        if (b1 instanceof Board) {
            Board b = (Board) b1;

            for (int i = 0; i < dim; i++) {
                for (int j = 0; j < dim; j++) {
                    if (board[i][j] != b.board[i][j])
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
    public void setG(double x) {
        this.g = x;
    }

    private double value_change(int a, int b) {
        if (a != b) {
            if (a % 2 != 0 && b % 2 != 0)
                return 1;
            else if (a % 2 != 0 && b % 2 == 0 || a % 2 == 0 && b % 2 != 0)
                return 5;
            else
                return 20;
        }
        return 0;
    }

    @Override
    public double getF() {
        return g + h;
    }

    @Override
    public double getH(Ilayout b) {
       // Board clone = (Board) clone();
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                heuristc(b, i, j);
            }
        }
        return h;
    }

    public void heuristc(Ilayout b1, int i, int j) {
        Board b = (Board) b1;
        // System.out.println(clone.board[i][j]+"@"+b.board[i][j]);
        if (board[i][j] == b.board[i][j]) {
        } else if (board[i][j] % 2 != 0 && b.board[i][j] % 2 != 0)
            h += 1;
        else if ((board[i][j] % 2 == 0 && b.board[i][j] % 2 != 0) || (board[i][j] % 2 != 0 && b.board[i][j] % 2 == 0)) {
            h += 5;
        } else {
            h += 20;
        }
        // System.out.println(clone.list);

        // System.out.println(clone);
        // return h;
    }

}