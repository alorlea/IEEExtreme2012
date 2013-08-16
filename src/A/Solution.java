/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package A;

import com.sun.org.apache.bcel.internal.generic.BREAKPOINT;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 *
 * @author Alberto Lorente Leal <albll@kth.se>, <a.lorenteleal@gmail.com>
 */
public class Solution {
    public static void main(String [] args) throws 
            Exception{
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        
        int initBunnies= Integer.parseInt(reader.readLine());
        
        int iteraciones= 365;
        int adultos=initBunnies;
        int bunnies=0;
        int newBunnies=0;
        //int newAdults=0;
        for (int i = 1; i <=iteraciones; i++) {
           
            if(i%15==0){
                bunnies+= (int)Math.floor(adultos*0.9);
            }
            if(i%30==0){
                adultos=(int)Math.floor(adultos*0.75);
            }
            if(i%45==0){
                int newAdults=(int)Math.floor(bunnies*0.7);
                adultos+=newAdults;
            }
            
            if(i%15==0){
                bunnies+= (int)Math.floor(adultos*0.9);
            }
        }
        
        System.out.println(adultos);
    }
}
