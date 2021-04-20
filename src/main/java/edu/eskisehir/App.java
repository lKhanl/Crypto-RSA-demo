package edu.eskisehir;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

@SuppressWarnings("ALL")
public class App {
    public static final char[] alfabe = {'A', 'B', 'C', 'Ç', 'D', 'E', 'F', 'G', 'Ğ', 'H', 'İ', 'I', 'J', 'K', 'L',
            'M', 'N', 'O', 'Ö', 'P', 'R', 'S', 'Ş', 'T', 'U', 'Ü', 'V', 'Y', 'Z'};
    public static int p;
    public static int q;

    public static void main(String[] args) {
        /** başlangıç sayıları */

        Scanner input = new Scanner(System.in);
        System.out.println("Şifrelemek istediğiniz kelimeyi giriniz: ");
        String girdi = input.nextLine();
        System.out.println("p: ");
        p = input.nextInt();
        System.out.println("q: ");
        q = input.nextInt();
        System.out.println("e: ");
        int e = input.nextInt();
        int m = p * q;
        int φM = (p - 1) * (q - 1);

        char[] harfler = girdi.toUpperCase().toCharArray();
        int[] indexler = new int[harfler.length];

        /** şifreleme adımları */

        for (int i = 0; i < harfler.length; i++) {
            for (int j = 0; j < alfabe.length; j++) {
                if (harfler[i] == alfabe[j]) {
                    indexler[i] = j + 1;
                    break;
                }
            }
        }
        /** test */
        for (int i = 0; i < harfler.length; i++) {
            for (int j = 0; j < alfabe.length; j++) {
                if (harfler[i] == alfabe[j]) {
                    System.out.print(j + 1 + " ");
                }
            }
        }
        System.out.println();
        /** test */
        double[] d = new double[indexler.length];
        double[] b_sayıları = new double[d.length];
        for (int i = 0; i < indexler.length; i++) {
            double a = Math.pow(indexler[i], e);
            double b = (a % m);
            b_sayıları[i] = b;
            double temp = b % alfabe.length;
            double c = temp - 1;
            d[i] = c;
        }
        System.out.println(Arrays.toString(d));
        System.out.println(Arrays.toString(b_sayıları));


        char[] şifre = new char[d.length];
        for (int i = 0; i < indexler.length; i++) {
            şifre[i] = alfabe[(int) d[i]];
        }
        String girdi2 = String.valueOf(şifre);
        System.out.println("Şifreli hali: " + girdi2);
        /** çözme adımları */
        char[] harfler2 = girdi2.toUpperCase().toCharArray();
        int[] indexler2 = new int[harfler.length];

        /** d sayısını bulmak */
        double d_sayısı = 0;
        int sayaç = 1;
        while (true) {
            d_sayısı = (1 + (φM * sayaç)) / e;
            if ((1 + (φM * sayaç)) % e == 0) {
                break;
            } else {
                sayaç++;
            }
        }
        System.out.println("d sayısı: " + d_sayısı);

        BigInteger[] big_b_sayıları = new BigInteger[b_sayıları.length];
        for (int i = 0; i < b_sayıları.length; i++) {

            big_b_sayıları[i] = new BigInteger(String.valueOf(new Double(b_sayıları[i]).longValue()));
        }
        BigInteger big_d_sayısı = new BigInteger(String.valueOf(new Double(d_sayısı).longValue()));
        BigInteger big_m = new BigInteger(String.valueOf(new Double(m).longValue()));

        BigInteger[] temp = new BigInteger[big_b_sayıları.length];
        for (int i = 0; i < b_sayıları.length; i++) {
            temp[i] = big_b_sayıları[i].modPow(big_d_sayısı, big_m);
        }
        System.out.println(Arrays.toString(temp));

        char[] çıktı = new char[temp.length];
        for (int i = 0; i < çıktı.length; i++) {
            çıktı[i] = alfabe[Integer.parseInt(String.valueOf(temp[i].longValue())) - 1];
        }
        System.out.println(Arrays.toString(çıktı));


    }
    /*public static LinkedList<Integer> generateE(int number) {
        LinkedList<Integer> sayılar = new LinkedList<>();
        for (int i = 2; i < number; i++) {
            if (bigIntegerRelativelyPrime(i, number)) {
                sayılar.add(i);
            }
        }
        return sayılar;
    }

    public static boolean bigIntegerRelativelyPrime(int a, int b) {
        return BigInteger.valueOf(a).gcd(BigInteger.valueOf(b)).equals(BigInteger.ONE);
    }

    private static boolean isPrime(int inputNum) {
        if (inputNum <= 3 || inputNum % 2 == 0)
            return inputNum == 2 || inputNum == 3; //this returns false if number is <=1 & true if number = 2 or 3
        int divisor = 3;
        while ((divisor <= Math.sqrt(inputNum)) && (inputNum % divisor != 0))
            divisor += 2; //iterates through all possible divisors
        return inputNum % divisor != 0; //returns true/false
    }*/
}
