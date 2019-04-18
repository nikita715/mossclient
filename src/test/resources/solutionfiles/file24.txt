import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;



public class Range {




    private int right;
    private int left;

    private Range() {}





    private Range(final int left,final int right) {
        if(left > right){
            throw new IllegalArgumentException("lower bound is higher than upper bound");}

        this.left = left;
        this.right = right;

    }

    public static Range between(final int left, final int right) {

        return new Range(left,right);
    }





    public int leftBound() {

        return this.left;
    }




    public int rightBound() {

        return this.right;
    }




    public boolean isBefore(final Range other) {

        return this.right < other.left;
    }




    public boolean isAfter(final Range other) {

        return this.left > other.right;
    }




    public boolean isConcurrent(final Range other) {

        return !(this.isAfter(other) || this.isBefore(other));
    }



    public boolean contains(final int value) {

        return this.left<=value && this.right>=value;
    }




    public List<Integer> asList() {

        List<Integer> list = new ArrayList<>(this.right -this.left + 1);
        for (int i = this.leftBound(); i <= this.rightBound(); i++) {
            list.add(i);
        }
        return list;
    }






    public Iterator<Integer> asIterator() {
        return this.asList().iterator();
    }
}
