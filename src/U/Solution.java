/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package U;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 *
 * @author Alberto Lorente Leal <albll@kth.se>, <a.lorenteleal@gmail.com>
 */
public class Solution {
    public static void main(String[] args){
                try{
                        BufferedReader br = new BufferedReader( new InputStreamReader( System.in ) );
                        
                        String line = br.readLine();
                        if(line.matches("[^RGYPC ]*")){
                            throw new Exception();
                        }
                        String[] elements=  line.split("[ \t]");
                        if(elements.length>15){
                            throw new Exception();
                        }
                        for(int i= 0; i<elements.length;i++){
                            if(!"RGYPC".contains( elements[i])) throw new Exception();
                            if(elements[i].length()!=1){
                                throw new Exception();
                            }
                        }
                        if(!elements[0].equals("R")){
                            System.out.println("REJECT");
                            return;
                        }
                        boolean Okay= true;
                        for(int i= 0; i<elements.length;i++){
                            if(elements[i].length()!=1){
                                throw new Exception();
                            }
                            String estado= elements[i];
                            if(i==elements.length-1){
                                break;
                            }
                            
                            String nextState= elements[i+1];
                            
                            if(estado.equals("R")){
                                if(nextState.equals("G")||nextState.equals("P")||nextState.equals("C")){
                                    continue;
                                }
                                else{
                                    Okay= false;
                                    break;
                                }
                            }
                            else if(estado.equals("G")){
                                if(nextState.equals("Y")){
                                    continue;
                                }
                                else{
                                    Okay=false;
                                    break;
                                }
                            }
                            else if(estado.equals("Y")){
                                if(nextState.equals("R")){
                                    continue;
                                }
                                else{
                                    Okay=false;
                                    break;
                                }
                            }
                            else if(estado.equals("P")){
                                if(nextState.equals("C")){
                                    continue;
                                }
                                else{
                                    Okay=false;
                                    break;
                                }
                            }
                            else if(estado.equals("C")){
                                if(nextState.equals("Y")||nextState.equals("R")){
                                   continue;
                                }
                                else{
                                    Okay= false;
                                    break;
                                }
                            }
                        }
                        
                        if(Okay){
                            System.out.println("ACCEPT");
                            return;
                        }
                        else if(!Okay){
                            System.out.println("REJECT");
                            return;
                        }
                        
                        
               }
                catch(Exception e){
                    System.out.println("ERROR");
                }
    }
    
}
