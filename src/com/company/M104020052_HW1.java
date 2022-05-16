package com.company;

//M104020052 張孫杰
//todo: write code in the decode_password(String s) function and validation(String s) to decode password as shown in the expected output.
//DO NOT EDIT other functions NOR add global variables.

import java.util.*;
import java.lang.Math;

public class M104020052_HW1 {
		
    // This class should not be instantiated.
    private M104020052_HW1() { }
    
    // todo: write your code in this function
    private static void decode_password(String s) {
		//程式起點
    	if (!validation(s)) {
    		System.out.println("invalid password format");
    		return;
    	}
		Stack stack = new Stack();

		//遍歷字串 Traversal String
		for (int i =0; i< s.length();i++){
			if(Character.isDigit(s.charAt(i))){
				//處理連續數字
				int	wait_into_stack = 0;
				int number_of_digits = 0;
				while (Character.isDigit(s.charAt(i))){
					wait_into_stack = (int) ((wait_into_stack * Math.pow(10, number_of_digits)) + Character.getNumericValue(s.charAt(i)));
					number_of_digits++;
					i++;
				}
				i--;
				stack.push(wait_into_stack);
			}else if(s.charAt(i)== '['){
				//如果遇到左括弧，處理連續字元
				String continues_char ="";
				i++;
				while (s.charAt(i) != ']'){
					continues_char  += s.charAt(i);
					i++;
					//System.out.println(continues_char);
				}
				int poptimes = (Integer)stack.pop();
				for (int j=0; j<poptimes; j++){
					System.out.print(continues_char);
				}
			}else{
				System.out.print(s.charAt(i));
			}
		}
		System.out.println();
    }
    
    // todo: write your code in this function
    private static boolean validation(String s) {
		//stack用來判斷成對括弧
		Stack stack = new Stack();
		//驗證輸入字串是否合法
		for (int i=0 ; i < s.length() ; i++){
			//如果連續數字後，緊接的不是'['，不合法
			if (Character.isDigit(s.charAt(i))){
				while (Character.isDigit(s.charAt(i))){
					i++;
				}
				if (s.charAt(i) !='[') {
					return false;
				}
				i--;

			}else if(s.charAt(i)== '['){
				//將 '[' push入stack，之後讓遍歷字串的i++
				stack.push(s.charAt(i));
				i++;
				//如果括號裡面有除了正常行為外的數字，不合法
				while (s.charAt(i) != ']'){
					if (Character.isDigit(s.charAt(i))){
						while (Character.isDigit(s.charAt(i))){
							i++;
						}
						if (s.charAt(i) !='[') {
							return false;
						}else if (s.charAt(i) == '['){
							stack.push(s.charAt(i));
							i--;
						}
					}
					i++;
				}
				//pop掉成對括弧
				stack.pop();
			}
		}
		//如果stack不為空，則代表括弧沒有成對
		if(!stack.empty()) {
			return false;
		}
		return true;
    }
 
    public static void main(String[] args) {
    	decode_password("2[b]c"); // expected output: bbc
		decode_password("1[a]2[b]"); // expected output: abb
    	decode_password("1[a2[b]"); // expected output: invalid password format
    	decode_password("3[kk2323]"); // expected output: invalid password format
    	decode_password("b2[an]a"); // expected output: banana
    	decode_password("10[bd]2[an]2[sg]"); // expected output: bdbdbdbdbdbdbdbdbdbdanansgsg
    	decode_password("go2[d]e3[s]ip"); // expected output: goddesssip
		decode_password("2[effp]10[ac2[zn]]"); // expected output: effpeffpacznznacznznacznznacznznacznznacznznacznznacznznacznznacznzn

    }
}
