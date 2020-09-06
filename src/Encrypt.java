import Des.DesAlgorithem;

import java.io.*;
import java.util.ArrayList;

public class Encrypt {

    private InputStream is=null;
    private byte[] content = null;
    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    public byte[] getFile(File f){

        try {
            //take bytes.
            is = new FileInputStream(f);
        } catch (FileNotFoundException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }

        try {
            // count the bytes
            content = new byte[is.available()];
            System.out.println("before encrypt=="+content.length);

        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        try {
            is.read(content);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return content;
    }

    public byte[] getContent() {
        return content;
    }

    public InputStream getIs() {
        return is;
    }

    public static File saveFile(byte[] bytes,String name) throws IOException {

        FileOutputStream fos = new FileOutputStream("C:\\Users\\edend\\Desktop\\"+name);
        fos.write(bytes);
        fos.close();
        File file=new File("C:\\Users\\edend\\Desktop\\"+name);
        return file;

    }
    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }

    private static String bytesToHex(byte[] hashInBytes) {

        StringBuilder sb = new StringBuilder();
        for (byte b : hashInBytes) {
            sb.append(String.format("%02x", b));
        }
        System.out.println(sb.toString());
        System.out.println("len==="+sb.length());
        return sb.toString();

    }

    public static int[] convertToArrayInt(ArrayList<Integer> list){
        int[] arr= new int[list.size()];
        for (int i=0;i<list.size();i++){
            arr[i]=list.get(i);
        }
        return arr;
    }

    private static String displayBits(int[] bits) {
        String str="";
        // Method to display int array bits as a hexadecimal string.
        for(int i=0 ; i < bits.length ; i+=4) {
            String output = new String();
            for(int j=0 ; j < 4 ; j++) {
                output += bits[i+j];
            }
            str=str+Integer.toHexString(Integer.parseInt(output, 2));
        }
        return str;
    }

    public File EncryptionMethod(File file, String key) throws IOException {
        Encrypt j = new Encrypt();
        DesAlgorithem des = new DesAlgorithem(bytesToHex(j.getFile(file)), key);
        des.Encrypt_Text();
        int[] bytes = new int[des.getText().getListOfLists().size() * 64];
        int indexaaray = 0;
        int sum = 0;
        for (int i = 0; i < des.getText().getListOfLists().size(); i++) {
            int[] arrayInt = convertToArrayInt(des.getText().getListOfListsList(i));
            sum = sum + des.getText().getListOfListsList(i).size();
            System.arraycopy(arrayInt, 0, bytes, indexaaray, arrayInt.length);
            indexaaray = indexaaray + arrayInt.length;
        }
        String string = displayBits(bytes);
        byte[] b = hexStringToByteArray(string);
        File file1=saveFile(b, "AssIGNMENT.pdf");
        return file1;
    }
    public File DecryptionMethod(File file,String key) throws IOException {
        Encrypt j = new Encrypt();
        DesAlgorithem des = new DesAlgorithem(bytesToHex(j.getFile(file)), key);
        des.DecryptionText();
        int[] bytes1 = new int[des.getText().getListOfLists().size()*64];
        int indexaaray1=0;
        for (int i=0;i<des.getText().getListOfLists().size();i++){
            int[] arrayInt=convertToArrayInt(des.getText().getListOfListsList(i));
            System.arraycopy(arrayInt,0,bytes1,indexaaray1,arrayInt.length);
            indexaaray1=indexaaray1+arrayInt.length;
        }
        String string1=displayBits(bytes1);
        System.out.println(bytes1.length);
        byte[] b2=hexStringToByteArray(string1);
        File file1=saveFile(b2,"DECRY.pdf");
        return file1;
    }
}
