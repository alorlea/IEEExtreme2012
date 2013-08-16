/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package F;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 *
 * @author Alberto Lorente Leal <albll@kth.se>, <a.lorenteleal@gmail.com>
 */
public class Solution {

    public static void main(String[] args) throws Exception {
        BufferedReader rd = new BufferedReader(new InputStreamReader(System.in));
        HashMap<char[], Integer> recibos = new HashMap();
        String line;

        while ((line = rd.readLine()) != null) {
            if (line.length() == 0) {
                break;
            }
            //System.out.println(line);
            String[] elements = line.split(" ");
            String key = elements[0];
            if (key.length() == 8) {
                key = "0" + key;
            }
            if (key.length() < 8) {
                continue;
            }
            int value = Integer.valueOf(elements[1]);
            if (recibos.containsKey(key.toCharArray())) {
                System.out.println(recibos.get(key.toCharArray()));
            }
            recibos.put(key.toCharArray(), value);
        }
        //System.out.println(recibos.size());
        // rd.close();
        //procesamos los recibos
        int suma = 0;
        int counter = 0;
        for (char[] s : recibos.keySet()) {

            int[] valores = new int[s.length];

            for (int i = 0; i < s.length; i++) {
                valores[i] = Integer.parseInt(s[i] + "");
            }
            //2) S = Α1 * 0 + Α2 * 2 + Α3 * 4 + Α4 * 8 + Α5 * 16 + Α6 * 32 + Α7 * 64 + Α8 * 128 + Α9 * 256
            int S = valores[8] * 0 + valores[7] * 2 + valores[6] * 4 + valores[5] * 8 + valores[4] * 16 + valores[3] * 32
                    + valores[2] * 64 + valores[1] * 128 + valores[0] * 256;
            int Y = S % 11;

            if (Y == 10 && valores[8] == 0) {
                suma += recibos.get(s);
                //System.out.println("Recibo valido: "+s+"suma por ahora: "+suma);

            } else if (Y == valores[8]) {
                suma += recibos.get(s);
                //System.out.println("Recibo valido: "+s+"suma por ahora: "+suma);

            } else {

                continue;
            }
        }
        System.out.println(suma);
        rd.close();
//            System.out.println ("Suma recibos: "+suma);
//            System.out.println("Sobra el recibo:"+recibos.keySet());
//            System.out.println(counter);
    }
}
