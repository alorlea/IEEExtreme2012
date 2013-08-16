/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package T;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author Alberto Lorente Leal <albll@kth.se>, <a.lorenteleal@gmail.com>
 */
public class Solution {
    public static void main(String[]args) throws Exception{
        InputStreamReader is=new InputStreamReader(System.in);
        BufferedReader rd= new BufferedReader(is);
        
        int N= Integer.parseInt(rd.readLine());
        double C= Double.parseDouble(rd.readLine());
        int percentage =Integer.parseInt(rd.readLine());
        MonthlySalary[] salaries= new MonthlySalary[N];
        for(int i=0;i<N;i++){
            String[] elements = rd.readLine().split(" ");
            double monthSal=Double.parseDouble( elements[0]);
            double annualBonus=Double.parseDouble(elements[1]);
            salaries[i]= new MonthlySalary(monthSal, annualBonus);            
        }
        
        double propertyTaxMonth= Double.parseDouble(rd.readLine());
        
        double propertyRegFee= Double.parseDouble(rd.readLine()); //paid only once per property
        
        Bob bob = new Bob(C, percentage, propertyTaxMonth, propertyRegFee, salaries);
        
        int condosPurchase=Integer.parseInt(rd.readLine());
        LinkedList<Condo> condos = new LinkedList();
        LinkedList<CondoDetail> details = new LinkedList();
        
        for(int i =0; i<condosPurchase;i++){
            String[] elements = rd.readLine().split(" ");
            int k=0;
            while(k<elements.length){
                double year=Double.parseDouble( elements[k++]);
                double month=Double.parseDouble(elements[k++]);
                double estimatedRent=Double.parseDouble(elements[k++]);
                double estimatedMarketValue =Double.parseDouble(elements[k++]);
                details.offer(new CondoDetail(year, month, estimatedRent, estimatedMarketValue));
           }
            condos.offer(new Condo(details));
        }
        double money=0.0;
        //Comenzamos el calculo
        for(int i=1;i<=N;i++){
            for(int m=1;m<=12;m++){
                
            }
        }
        
        //Goal: calcular el net worth, es decir en total lo que disponga en dinero liquido mas el valor de sus
        //propiedades a la hora de retirarse
//        
//        System.out.println("N "+N);
//        System.out.println("C "+C);
//        System.out.println("percentage "+percentage);
//        System.out.println(salaries.toString());
//        System.out.println("Property Tax Month: "+ propertyTaxMonth);
//        System.out.println("Property Reg Fee: "+ propertyRegFee);
//        System.out.println("condosPurchase: "+ condosPurchase);
//        System.out.println(condos.toString());
        //Goal: calcular el net worth, es decir en total lo que disponga en dinero liquido mas el valor de sus
        //propiedades a la hora de retirarse
        
        
        
    }
    
    
}


class Bob{
    double currentSavings;
    double percentMonthTaxSalary;
    double propertyTaxPercentage;
    double registrationFee;
    MonthlySalary[] salaries;

    public Bob(double currentSavings, double percentMonthTaxSalary, double propertTax, double propertyFee, MonthlySalary[] salaries) {
        this.currentSavings = currentSavings;
        this.percentMonthTaxSalary = percentMonthTaxSalary;
        this.propertyTaxPercentage=propertTax;
        this.registrationFee= propertyFee;
        this.salaries = salaries;
    }
    
    
}

class MonthlySalary{
        double monthSalYear;
        double annualBonusYear;

        public MonthlySalary(double monthSalYear, double annualBonusYear) {
            this.monthSalYear = monthSalYear;
            this.annualBonusYear = annualBonusYear;
        }

    @Override
    public String toString() {
        return "MonthlySalary{" + "monthSalYear=" + monthSalYear + ", annualBonusYear=" + annualBonusYear + '}';
    }
        
        
        
    }

class Condo{
    LinkedList<CondoDetail> details;
    boolean taxPayed;

    public Condo(LinkedList<CondoDetail> details) {
        this.details = details;
        taxPayed=false;
    }
    
    
}
class CondoDetail{
    double year;
    double month;
    double estimatedRent;
    double estimatedMarketValue;
    //boolean taxPayed;

    public CondoDetail(double year, double month, double estimatedRent, double estimatedMarketValue) {
        this.year = year;
        this.month = month;
        this.estimatedRent = estimatedRent;
        this.estimatedMarketValue = estimatedMarketValue;
       // this.taxPayed=false;
    }
   
    @Override
    public String toString() {
        return "Condo{" + "year=" + year + ", month=" + month + ", estimatedRent=" + estimatedRent + ", estimatedMarketValue=" + estimatedMarketValue + '}';
    }
    
    
}