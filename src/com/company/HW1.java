package com.company;

//M104020052 張孫杰
//todo: write code in the decode_password(String s) function and validation(String s) to decode password as shown in the expected output.
//DO NOT EDIT other functions NOR add global variables.

import java.util.*;
import java.util.stream.Collectors;
import java.lang.StringBuilder;
import java.lang.Math; 

public class HW1 {
		
    // This class should not be instantiated.
    private HW1() { }
    
    // todo: write your code in this function
    private static void decode_password(String s) {
    	if (!validation(s)) {
    		System.out.println("invalid password format");
    		return;
    	}
    }
    
    // todo: write your code in this function
    private static boolean validation(String s) {
		return true;
    }
 
    public static void main(String[] args) {


		System.out.println("HIHIHI");
		System.out.println("test");
    	decode_password("2[b]c"); // expected output: bbc
    	decode_password("1[a]2[b]"); // expected output: abb
    	decode_password("1[a2[b]"); // expected output: invalid password format
    	decode_password("3[kk2323]"); // expected output: invalid password   format
    	decode_password("b2[an]a"); // expected output: banana
    	decode_password("10[bd]2[an]2[sg]"); // expected output: bdbdbdbdbdbdbdbdbdbdanansgsg
    	decode_password("go2[d]e3[s]ip"); // expected output: goddesssip
        decode_password("2[effp]10[ac2[zn]]"); // expected output: effpeffpacznznacznznacznznacznznacznznacznznacznznacznznacznznacznzn

    }
}
