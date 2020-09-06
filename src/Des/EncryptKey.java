package Des;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class EncryptKey{

    ArrayList<Integer> listkey=new ArrayList<>();
    ArrayList<Integer> left=new ArrayList<>();
    ArrayList<Integer> right=new ArrayList<>();

    // Permuted Choice 1 table
    private static final int[] Permutation56 = {
            57, 49, 41, 33, 25, 17, 9,
            1,  58, 50, 42, 34, 26, 18,
            10, 2,  59, 51, 43, 35, 27,
            19, 11, 3,  60, 52, 44, 36,
            63, 55, 47, 39, 31, 23, 15,
            7,  62, 54, 46, 38, 30, 22,
            14, 6,  61, 53, 45, 37, 29,
            21, 13, 5,  28, 20, 12, 4
    };

    // Permuted Choice 2 table
    private static final int[] Permutation48 = {
            14, 17, 11, 24, 1,  5,
            3,  28, 15, 6,  21, 10,
            23, 19, 12, 4,  26, 8,
            16, 7,  27, 20, 13, 2,
            41, 52, 31, 37, 47, 55,
            30, 40, 51, 45, 33, 48,
            44, 49, 39, 56, 34, 53,
            46, 42, 50, 36, 29, 32
    };

    // Array to store the number of rotations that are to be done on each round
    private static final int[] ShiftRotationsLeft = {
            1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1
    };

    private static final int[] ShiftRotationsRight = {
            1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1
    };

    public EncryptKey(String string){

        int size=string.length();
        for (int i=0;i<size;i++){
            ArrayList<Integer> list=new ArrayList<>();
            String s = Integer.toBinaryString(Integer.parseInt(string.charAt(i) +"", 16));
            while (s.length() < 4) {
                s = "0" + s;
            }
            // Add the 4 bits we have extracted into the array of bits.
            for (int k = 0; k< 4; k++) {
                listkey.add(Integer.parseInt(s.charAt(k) + ""));
            }
        }
    }

    public void FirstPermutation(){

        ArrayList<Integer> list=new ArrayList<>();
        for (int i=0;i<Permutation56.length;i++){
            list.add(getElementListkey(Permutation56[i]-1));
        }
        setListkey(list);
    }

    public void ShiftRight(int index){
        if(index!=0) {
            ArrayList<Integer> listLeft = new ArrayList<>();
            ArrayList<Integer> listRight = new ArrayList<>();

            if (ShiftRotationsRight[index-1] == 1) {
                int first = getLeft().get(27);
                listLeft.add(first);
                for (int i = 0; i < this.getLeft().size() - 1; i++) {
                    listLeft.add(this.getLeft().get(i));
                }
                setLeft(listLeft);

                first = getRight().get(27);
                listRight.add(first);
                for (int i = 0; i < getRight().size() - 1; i++) {
                    listRight.add(this.getRight().get(i));
                }
                setRight(listRight);

            } else {
                int first = getLeft().get(27);
                int second = getLeft().get(26);
                listLeft.add(second);
                listLeft.add(first);
                for (int i = 0; i < this.getLeft().size() - 2; i++) {
                    listLeft.add(getLeft().get(i));
                }
                setLeft(listLeft);

                first = getRight().get(27);
                second = getRight().get(26);
                listRight.add(second);
                listRight.add(first);
                for (int i = 0; i < getRight().size() - 2; i++) {
                    listRight.add(getRight().get(i));
                }
                setRight(listRight);
            }
        }
    }




    public void ShiftLeft(int index){

        ArrayList<Integer> listLeft=new ArrayList<>();
        ArrayList<Integer> listRight=new ArrayList<>();

        if (ShiftRotationsLeft[index]==1){
            int first = getLeft().get(0);
            for (int i=1;i<this.getLeft().size();i++){
                listLeft.add(this.getLeft().get(i));
            }
            listLeft.add(first);
            setLeft(listLeft);

            first=getRight().get(0);
            for (int i=1;i<getRight().size();i++){
                listRight.add(this.getRight().get(i));
            }
            listRight.add(first);
            setRight(listRight);

        }
        else {
            int first= getLeft().get(0);
            int second= getLeft().get(1);
            for (int i=2;i<this.getLeft().size();i++){
                listLeft.add(getLeft().get(i));
            }
            listLeft.add(first);
            listLeft.add(second);
            setLeft(listLeft);

            first=getRight().get(0);
            second=getRight().get(1);
            for (int i=2;i<getRight().size();i++){
                listRight.add(getRight().get(i));
            }
            listRight.add(first);
            listRight.add(second);
            setRight(listRight);
        }

    }

    public ArrayList<Integer> AddTwoPart(){
        ArrayList<Integer> list=new ArrayList<>();
        for (int i=0;i<getLeft().size();i++){
            list.add(getLeft().get(i));
        }
        for (int i=0;i<getRight().size();i++){
            list.add(getRight().get(i));
        }
        return list;
    }

    public ArrayList<Integer> SecondPermutation(ArrayList<Integer> ListBeforePermutation){
        ArrayList<Integer> list=new ArrayList<>();
        for (int i=0;i<Permutation48.length;i++){
            list.add(ListBeforePermutation.get(Permutation48[i]-1));
        }
        return list;
    }

    public void InitializationLeft(){
        ArrayList<Integer> list= new ArrayList<>();
        for (int i=0;i<28;i++){
            list.add(getElementListkey(i));
        }
        setLeft(list);
    }

    public void InitializationRight(){
        ArrayList<Integer> list= new ArrayList<>();
        for (int i=28;i<56;i++){
            list.add(getElementListkey(i));
        }
        setRight(list);
    }


    public void setLeft(ArrayList<Integer> left) {
        this.left = left;
    }

    public void setRight(ArrayList<Integer> right) {
        this.right = right;
    }

    public void setListkey(ArrayList<Integer> listkey) {
        this.listkey = listkey;
    }

    public int getSizeListkey() {
        return listkey.size();
    }

    public int getElementListkey(int index) {
        return listkey.get(index);
    }

    public ArrayList<Integer> getRight() {
        return right;
    }

    public ArrayList<Integer> getLeft() {
        return left;
    }

    public ArrayList<Integer> getListkey() {
        return listkey;
    }
}

