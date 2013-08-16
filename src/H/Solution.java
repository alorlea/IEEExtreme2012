/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package H;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;

/**
 *
 * @author Alberto Lorente Leal <albll@kth.se>, <a.lorenteleal@gmail.com>
 */
public class Solution {
        public static void main(String[]args) throws Exception{
            BufferedReader rd= new BufferedReader(new InputStreamReader(System.in));
            //Obtencion de datos
            String [] matriz = rd.readLine().split(",");
            int rows= Integer.parseInt(matriz[0]);
            int columns= Integer.parseInt(matriz[1]);
            
            
            String [][] field = new String[rows][columns];
            for(int i=0;i<rows;i++){
                for(int j= 0;j<columns;j++){
                    field[i][j]="0";
                }
            }
            LinkedList<String>comands= new LinkedList();
            String comandDone="";
            //inicializo robots
            int robot1= Integer.parseInt(rd.readLine());
            int robot2= Integer.parseInt(rd.readLine());
            field[0][robot1] = "1";
            Robot r1 = new Robot(0, robot1);
            field[rows-1][robot2]="2";
            Robot r2= new Robot(rows-1,robot2);
            //ponemos la bola en juego
            int ballPos=Integer.parseInt(rd.readLine());
            Ball ball;
            //robot 1 tiene la bola, inicializo la posicion
            if(ballPos==1){
                ball= new Ball(0, robot1, 0, 0);
            }
            else{
                ball= new Ball(rows-1, robot2, 0, 0);
            }
            
            //metemos los obstaculos
           int obstaculos =Integer.parseInt(rd.readLine());
           for(int i= 0;i<obstaculos;i++){
               String [] coord= rd.readLine().split(",");
               int x= Integer.parseInt(coord[0]);
               int y= Integer.parseInt(coord[1]);
               field[y][x]= "%";
           }
           //lista de comando para los robots
           String temp= rd.readLine();
           for(int i =0;i<temp.length();i++){
               String aux= temp.charAt(i)+"";
               comands.offer(aux);
           }
            System.out.println("datos cargados en el sistema");
           draw(field);
           //bucle de juego
           while(true){
               //comprobamos posicion de la bola con la del robot1 y hacemos un comando
               if(ball.posX==r1.posX&&ball.posY==r1.posY){
                   //hago comando
                   if(comands.isEmpty()){
                       break;
                   }
                   else{
                       doCommand(comands,comandDone,ball,1);
                   }
                   
               }
               //sino puede ser el otro robot
               else if(ball.posX==r2.posX&&ball.posY==r2.posY){
                   //hago comando
                   if(comands.isEmpty()){
                       break;
                   }
                   else{
                       doCommand(comands,comandDone,ball,2);
                   }
               }
               
               //actualizo los movimientos de los objetos

                    draw(field);
                    moveBall(ball, field);
                    moveRobot(r1,field,1,ball);
                    moveRobot(r2,field,2,ball);
                    

                   
               
           }
           
           
           
            
            
            
        }
        static void update(){
            
        }
        static void moveBall(Ball ball,String[][] field) {
                //comprobar colision
            System.out.println("posicion bola: "+ball.posX+","+ball.posY);
                if(field[ball.posY][ball.posX].equals("%")){
                    //invertimos las velocidades
                    ball.velX=-ball.velX;
                    ball.velY=-ball.velY;
                     //field[ball.posY][ball.posX]="0";
                ball.posX+=ball.velX;
                ball.posY+=ball.velY;
                    field[ball.posY][ball.posX]="*";
                }
                //comprobar si nos salimos del tablero
                if(ball.posX==0||ball.posX==field[0].length-1){
                    ball.velX=-ball.velX;
                     field[ball.posY][ball.posX]="0";
                ball.posX+=ball.velX;
               ball.posY+=ball.velY;
                field[ball.posY][ball.posX]="*";
                }
                else{
                    field[ball.posY][ball.posX]="0";
                ball.posX+=ball.velX;
                ball.posY+=ball.velY;
                field[ball.posY][ball.posX]="*";
                }
        }
        
        static void moveRobot(Robot r, String[][] field,int robot,Ball ball){
            if(robot==1){
                if(ball.posX<r.posX)
                {
                    field[0][r.posX]="0";
                    r.posX-=1;
                    field[0][r.posX]="1";
                }
                if(ball.posX>r.posX){
                    field[0][r.posX]="0";
                    r.posX+=1;
                    field[0][r.posX]="1";
                }
                else{
                    if(r.posX==field[0].length-1){
                        field[0][r.posX]="0";
                        r.posX-=1;
                        field[0][r.posX]="1";
                    }
                    else{
                        field[0][r.posX]="0";
                        r.posX+=1;
                        field[0][r.posX]="1";
                    }
                }
            }
            else{
                if(ball.posX<r.posX)
                {
                    field[field.length-1][r.posX]="0";
                    r.posX-=1;
                    field[field.length-1][r.posX]="2";
                }
                if(ball.posX>r.posX){
                    field[field.length-1][r.posX]="0";
                    r.posX+=1;
                    field[field.length-1][r.posX]="2";
                }
                else{
                    if(r.posX==0){
                        field[field.length-1][r.posX]="0";
                        r.posX+=1;
                        field[field.length-1][r.posX]="2";
                    }
                    else{
                        field[field.length-1][r.posX]="0";
                        r.posX-=1;
                        field[field.length-1][r.posX]="2";
                    }
                }
            }
            
        }
        static void doCommand(LinkedList<String> comands,String comandDone,Ball ball,int robot){
            String command=comands.poll();
            if(command.equals("L")){
                comandDone+=command;
                if(robot==1){
                    ball.velX=-1;
                    ball.velY=1;
                }
                else{
                    ball.velX=1;
                    ball.velY=-1;

                }
            }
            if(command.equals("R")){
                comandDone+=command;
              
                if(robot==1){
                    ball.velX=1;
                    ball.velY=1;
                }
                else{
                    ball.velX=-1;
                    ball.velY=-1;
                }
            }
            else{
                comandDone+=command;

                if(robot==1){
                    ball.velX=0;
                    ball.velY=1;
                }
                else{
                    ball.velX=0;
                    ball.velY=-1;
                }
            }
        }
        
        static void draw(String[][] field){
            for(int i=0;i<field.length;i++){
                for(int j=0;j<field[0].length;j++){
                    System.out.print(field[i][j]);
                }
                System.out.println("");
            }
            System.out.println("");
        }
        //static String[][] updateField(String[][] field){
       //     String [][]
      //  }
}
class Ball{
    int posX;
    int posY;
    int velX;
    int velY;

    public Ball(int posX, int posY, int velX, int velY) {
        this.posX = posX;
        this.posY = posY;
        this.velX = velX;
        this.velY = velY;
    }
    
}

class Robot{
    int posX;
    int posY;

    public Robot(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }
    
    
}

