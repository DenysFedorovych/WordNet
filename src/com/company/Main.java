package com.company;

public class Main {

    public static void main(String[] args) {
	String str1 = new String("wer");
	String str2 = "wer";
        System.out.println(str1==str2);
        String[] str = new String[3];
        str = str1.split(" ");
        for(String each : str){
            if(each.equals(str1)){
            System.out.println(each);}
        }
    }
}
