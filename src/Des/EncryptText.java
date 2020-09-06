package Des;



import java.util.ArrayList;

public class EncryptText extends Encryption {

    private ArrayList<Integer> Left=new ArrayList<>();
    private ArrayList<Integer> Right=new ArrayList<>();



    private static final int[] IP = {
            58, 50, 42, 34, 26, 18, 10, 2,
            60, 52, 44, 36, 28, 20, 12, 4,
            62, 54, 46, 38, 30, 22, 14, 6,
            64, 56, 48, 40, 32, 24, 16, 8,
            57, 49, 41, 33, 25, 17, 9,  1,
            59, 51, 43, 35, 27, 19, 11, 3,
            61, 53, 45, 37, 29, 21, 13, 5,
            63, 55, 47, 39, 31, 23, 15, 7
    };

    // Permutation table
    private static final int[] Permutation32 = {
            16, 7,  20, 21,
            29, 12, 28, 17,
            1,  15, 23, 26,
            5,  18, 31, 10,
            2,  8,  24, 14,
            32, 27, 3,  9,
            19, 13, 30, 6,
            22, 11, 4,  25
    };

    // Final permutation (aka Inverse permutation) table
    private static final int[] FP = {
            40, 8, 48, 16, 56, 24, 64, 32,
            39, 7, 47, 15, 55, 23, 63, 31,
            38, 6, 46, 14, 54, 22, 62, 30,
            37, 5, 45, 13, 53, 21, 61, 29,
            36, 4, 44, 12, 52, 20, 60, 28,
            35, 3, 43, 11, 51, 19, 59, 27,
            34, 2, 42, 10, 50, 18, 58, 26,
            33, 1, 41, 9, 49, 17, 57, 25
    };

    private static final int[] E = {
            32, 1,  2,  3,  4,  5,
            4,  5,  6,  7,  8,  9,
            8,  9,  10, 11, 12, 13,
            12, 13, 14, 15, 16, 17,
            16, 17, 18, 19, 20, 21,
            20, 21, 22, 23, 24, 25,
            24, 25, 26, 27, 28, 29,
            28, 29, 30, 31, 32, 1
    };

    private static final int[][] S = { {
            14, 4,  13, 1,  2,  15, 11, 8,  3,  10, 6,  12, 5,  9,  0,  7,
            0,  15, 7,  4,  14, 2,  13, 1,  10, 6,  12, 11, 9,  5,  3,  8,
            4,  1,  14, 8,  13, 6,  2,  11, 15, 12, 9,  7,  3,  10, 5,  0,
            15, 12, 8,  2,  4,  9,  1,  7,  5,  11, 3,  14, 10, 0,  6,  13
    }, {
            15, 1,  8,  14, 6,  11, 3,  4,  9,  7,  2,  13, 12, 0,  5,  10,
            3,  13, 4,  7,  15, 2,  8,  14, 12, 0,  1,  10, 6,  9,  11, 5,
            0,  14, 7,  11, 10, 4,  13, 1,  5,  8,  12, 6,  9,  3,  2,  15,
            13, 8,  10, 1,  3,  15, 4,  2,  11, 6,  7,  12, 0,  5,  14, 9
    }, {
            10, 0,  9,  14, 6,  3,  15, 5,  1,  13, 12, 7,  11, 4,  2,  8,
            13, 7,  0,  9,  3,  4,  6,  10, 2,  8,  5,  14, 12, 11, 15, 1,
            13, 6,  4,  9,  8,  15, 3,  0,  11, 1,  2,  12, 5,  10, 14, 7,
            1,  10, 13, 0,  6,  9,  8,  7,  4,  15, 14, 3,  11, 5,  2,  12
    }, {
            7,  13, 14, 3,  0,  6,  9,  10, 1,  2,  8,  5,  11, 12, 4,  15,
            13, 8,  11, 5,  6,  15, 0,  3,  4,  7,  2,  12, 1,  10, 14, 9,
            10, 6,  9,  0,  12, 11, 7,  13, 15, 1,  3,  14, 5,  2,  8,  4,
            3,  15, 0,  6,  10, 1,  13, 8,  9,  4,  5,  11, 12, 7,  2,  14
    }, {
            2,  12, 4,  1,  7,  10, 11, 6,  8,  5,  3,  15, 13, 0,  14, 9,
            14, 11, 2,  12, 4,  7,  13, 1,  5,  0,  15, 10, 3,  9,  8,  6,
            4,  2,  1,  11, 10, 13, 7,  8,  15, 9,  12, 5,  6,  3,  0,  14,
            11, 8,  12, 7,  1,  14, 2,  13, 6,  15, 0,  9,  10, 4,  5,  3
    }, {
            12, 1,  10, 15, 9,  2,  6,  8,  0,  13, 3,  4,  14, 7,  5,  11,
            10, 15, 4,  2,  7,  12, 9,  5,  6,  1,  13, 14, 0,  11, 3,  8,
            9,  14, 15, 5,  2,  8,  12, 3,  7,  0,  4,  10, 1,  13, 11, 6,
            4,  3,  2,  12, 9,  5,  15, 10, 11, 14, 1,  7,  6,  0,  8,  13
    }, {
            4,  11, 2,  14, 15, 0,  8,  13, 3,  12, 9,  7,  5,  10, 6,  1,
            13, 0,  11, 7,  4,  9,  1,  10, 14, 3,  5,  12, 2,  15, 8,  6,
            1,  4,  11, 13, 12, 3,  7,  14, 10, 15, 6,  8,  0,  5,  9,  2,
            6,  11, 13, 8,  1,  4,  10, 7,  9,  5,  0,  15, 14, 2,  3,  12
    }, {
            13, 2,  8,  4,  6,  15, 11, 1,  10, 9,  3,  14, 5,  0,  12, 7,
            1,  15, 13, 8,  10, 3,  7,  4,  12, 5,  6,  11, 0,  14, 9,  2,
            7,  11, 4,  1,  9,  12, 14, 2,  0,  6,  10, 13, 15, 3,  5,  8,
            2,  1,  14, 7,  4,  10, 8,  13, 15, 12, 9,  0,  3,  5,  6,  11
    } };

    public EncryptText(String string) {
        super(string);
    }


    public void FirstPermutation(ArrayList<Integer> ListBeforePermutation, int index){
        ArrayList<Integer> list=new ArrayList<>();
        for (int i=0;i<IP.length;i++){
            list.add(ListBeforePermutation.get(IP[i]-1));
        }
        setListOfListsIndexList(list, index);
    }

    public ArrayList<Integer> RightWithE(ArrayList<Integer> listRight){
        ArrayList<Integer> list=new ArrayList<>();
        for (int i=0;i<E.length;i++){
            list.add(listRight.get(E[i]-1));
        }
        return list;
    }

    public ArrayList<Integer> RightXORKey(ArrayList<Integer> listRight, ArrayList<Integer> key){
        ArrayList<Integer> list=new ArrayList<>();
        for (int i=0;i<listRight.size();i++){
            list.add(listRight.get(i) ^ key.get(i));
        }
        return list;
    }

    public ArrayList<Integer> S_BOX(ArrayList<Integer> listFunction){
        int l=0;
        ArrayList<Integer> list= new ArrayList<>();
        for (int i=0;i<8;i++){
            int num1=listFunction.get(l);
            int num3=listFunction.get(l+1)*8;
            int num4=listFunction.get(l+2)*4;
            int num5=listFunction.get(l+3)*2;
            int num6=listFunction.get(l+4);
            int ans=num3+num4+num5+num6;
            int num2=listFunction.get(l+5);
            int g=num1*2+num2;
            int result=(g*16)+ans;

            int result1=S[i][result];
            String s = Integer.toBinaryString(result1);
            while (s.length() < 4) {
                s = "0" + s;
            }
            // Add the 4 bits we have extracted into the array of bits.
            for (int k = 0; k< 4; k++) {
                list.add(Integer.parseInt(s.charAt(k) + ""));
            }
            l=l+6;

        }
        return list;
    }

    public ArrayList<Integer> SecondPermutation(ArrayList<Integer> listForPermutation){
        ArrayList<Integer> list=new ArrayList<>();
        for (int i=0;i<Permutation32.length;i++){
            list.add(listForPermutation.get(Permutation32[i]-1));
        }
        return list;
    }

    public ArrayList<Integer> LeftXORList(ArrayList<Integer> ListAfterPermutation){
        ArrayList<Integer> list=new ArrayList<>();
        for (int i=0;i<ListAfterPermutation.size();i++){
            list.add(ListAfterPermutation.get(i) ^ getLeft().get(i));
        }
        return list;
    }

    public void TransferRightLeft(ArrayList<Integer> list){

        setLeft(getRight());
        setRight(list);
    }

    public ArrayList<Integer> AddSides(){
        ArrayList<Integer> list=new ArrayList<>();
        for (int i=0;i<getLeft().size();i++){
            list.add(getLeft().get(i));
        }
        for (int i=0;i<getRight().size();i++){
            list.add(getRight().get(i));
        }
        return list;
    }

    public ArrayList<Integer> LastPermutation(ArrayList<Integer> ListBeforePermutation){
        ArrayList<Integer> list=new ArrayList<>();
        for (int i=0;i<ListBeforePermutation.size();i++){
            list.add(ListBeforePermutation.get(FP[i]-1));
        }
        return list;
    }


    public void InitializationLeft(ArrayList<Integer> list){
        ArrayList<Integer> list1=new ArrayList<>();
        for (int i=0;i<32;i++){
            list1.add(list.get(i));
        }
        setLeft(list1);
    }

    public void InitializationRight(ArrayList<Integer> list){
        ArrayList<Integer> list1=new ArrayList<>();
        for (int i=32;i<64;i++){
            list1.add(list.get(i));
        }
        setRight(list1);
    }

    public void setRight(ArrayList<Integer> right) {
        Right = right;
    }

    public void setLeft(ArrayList<Integer> left) {
        Left = left;
    }

    public ArrayList<Integer> getLeft() {
        return Left;
    }

    public ArrayList<Integer> getRight() {
        return Right;
    }

    @Override
    public void setListOfListsIndexList(ArrayList<Integer> list, int index) {
        super.setListOfListsIndexList(list, index);
    }
}

