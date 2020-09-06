package Des;

import java.util.ArrayList;
import java.util.List;

public abstract class Encryption {

    private ArrayList<ArrayList<Integer>> listOfLists = new ArrayList<ArrayList<Integer>>();

    public Encryption(String string){

        ConvertNDivision(string);

    }

    public void ConvertNDivision(String string){
        int index=0;
        int size;
        if (string.length()%16==0){
            size=(int)string.length()/16;
        }
        else{
            size=((int)string.length()/16)+1;
        }
        System.out.println("in encryption =="+ size);

        for (int i=0;i<size;i++){
            ArrayList<Integer> list=new ArrayList<Integer>();

            //System.out.println(i+":::");
            if (index+15<string.length()){
                for (int j=index;j<=index+15;j++) {
                    // System.out.println("in 2 loop"+string.charAt(j));
                    String s = Integer.toBinaryString(Integer.parseInt(string.charAt(j) + "", 16));
                    while (s.length() < 4) {

                        s = "0" + s;
                    }
                    // Add the 4 bits we have extracted into the array of bits.
                    for (int k = 0; k< 4; k++) {
                        list.add(Integer.parseInt(s.charAt(k) + ""));
                    }
                }
                index=index+16;
                listOfLists.add(list);
            }
            else{
                int k=0;
                for (int j=0;j<64;j++)
                {
                    list.add(0);
                }
                for (int j=index;j<string.length();j++){
                    String s = Integer.toBinaryString(Integer.parseInt(string.charAt(j) + "", 16));
                    while (s.length() < 4) {
                        s = "0" + s;
                    }
                    for (int p = 0; p< 4; p++) {
                        list.set(k,Integer.parseInt(s.charAt(p) + ""));
                        k++;
                    }
                }
                listOfLists.add(list);
            }
        }
        System.out.println("list=="+getListOfLists().size());
    }

    public int getIndexListOfListsList(int index1, int index2) {
        return listOfLists.get(index1).get(index2);
    }



    public ArrayList<Integer> getListOfListsList(int index) {
        return listOfLists.get(index);
    }

    public ArrayList<ArrayList<Integer>> getListOfLists() {
        return listOfLists;
    }

    public void setListOfListsIndexList(ArrayList<Integer> list, int index) {
        this.listOfLists.set(index,list);
    }

    public void setListOfLists(ArrayList<ArrayList<Integer>> listOfLists) {
        this.listOfLists = listOfLists;
    }
}
