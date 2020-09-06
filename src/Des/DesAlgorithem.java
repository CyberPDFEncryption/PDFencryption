package Des;




import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Scanner;

public class DesAlgorithem {

    private EncryptKey Key;
    private EncryptKey DecKey;
    private EncryptText Text;
    private ArrayList<ArrayList<Integer>> keys=new ArrayList<ArrayList<Integer>>();
    private ArrayList<ArrayList<Integer>> DecKeys=new ArrayList<ArrayList<Integer>>();


    public DesAlgorithem(String text, String key ){
        Key=new EncryptKey(key);
        Text=new EncryptText(text);
        DecKey=new EncryptKey(key);
    }

    public void CreateKeys(){
        Key.FirstPermutation();
        Key.InitializationLeft();
        Key.InitializationRight();
        for (int i=0;i<16;i++) {
            Key.ShiftLeft(i);
            this.keys.add(Key.SecondPermutation(Key.AddTwoPart()));
        }
    }

    public void CreateDecKeys(){

        DecKey.FirstPermutation();
        DecKey.InitializationLeft();
        DecKey.InitializationRight();
        for (int i=0;i<16;i++) {
            DecKey.ShiftRight(i);
            this.DecKeys.add(DecKey.SecondPermutation(DecKey.AddTwoPart()));
        }
    }

    public void Encrypt_Text(){
        CreateKeys();
        ArrayList<Integer> list=new ArrayList<>();
        for (int i=0;i<Text.getListOfLists().size();i++){
            Text.FirstPermutation(Text.getListOfListsList(i),i);
            Text.InitializationLeft(Text.getListOfListsList(i));
            Text.InitializationRight(Text.getListOfListsList(i));

            for (int j=0;j<16;j++){
                ArrayList<Integer> arrayList=new ArrayList<>();
                arrayList=Text.RightWithE(Text.getRight());
                arrayList=Text.RightXORKey(arrayList,keys.get(j));
                arrayList=Text.S_BOX(arrayList);
                arrayList=Text.SecondPermutation(arrayList);
                arrayList=Text.LeftXORList(arrayList);
                Text.TransferRightLeft(arrayList);

                if (j == 15){
                    Text.TransferRightLeft(Text.getLeft());
                    Text.setListOfListsIndexList(Text.LastPermutation(Text.AddSides()),i);;

                }
            }

        }
    }

    public ArrayList<ArrayList<Integer>> getDecKeys() {
        return DecKeys;
    }

    public ArrayList<ArrayList<Integer>> getKeys() {
        return keys;
    }

    public EncryptKey getDecKey() {
        return DecKey;
    }

    public EncryptKey getKey() {
        return Key;
    }

    public EncryptText getText() {
        return Text;
    }

    public void DecryptionText(){
        CreateDecKeys();
        ArrayList<Integer> list=new ArrayList<>();
        for (int i=0;i<Text.getListOfLists().size();i++){
            Text.FirstPermutation(Text.getListOfListsList(i),i);
            Text.InitializationLeft(Text.getListOfListsList(i));
            Text.InitializationRight(Text.getListOfListsList(i));
            for (int j=0;j<16;j++){
                ArrayList<Integer> arrayList=new ArrayList<>();
                arrayList=Text.RightWithE(Text.getRight());
                arrayList=Text.RightXORKey(arrayList,DecKeys.get(j));
                arrayList=Text.S_BOX(arrayList);
                arrayList=Text.SecondPermutation(arrayList);
                arrayList=Text.LeftXORList(arrayList);
                Text.TransferRightLeft(arrayList);

                if (j == 15){
                    Text.TransferRightLeft(Text.getLeft());
                    //;
                    Text.setListOfListsIndexList(Text.LastPermutation(Text.AddSides()),i);;

                }
            }

        }


    }

    public void setKey(EncryptKey key) {
        Key = key;
    }

}
