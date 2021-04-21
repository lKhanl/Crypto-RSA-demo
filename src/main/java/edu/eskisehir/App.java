package edu.eskisehir;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Scanner;

@SuppressWarnings("ALL")
public class App {
    public static final char[] alfabe = {'A', 'B', 'C', 'Ç', 'D', 'E', 'F', 'G', 'Ğ', 'H', 'İ', 'I', 'J', 'K', 'L',
            'M', 'N', 'O', 'Ö', 'P', 'R', 'S', 'Ş', 'T', 'U', 'Ü', 'V', 'Y', 'Z',' '};

    public static void main(String[] args) {
        /** başlangıç */
        Scanner input = new Scanner(System.in);
        System.out.println("Şifrelemek istediğiniz kelimeyi giriniz: ");
        String girdi = input.nextLine();
        System.out.println("p: ");
        int p = input.nextInt();
        System.out.println("q: ");
        int q = input.nextInt();
        System.out.println("e: ");
        int e = input.nextInt();
        int m = p * q;
        int φM = (p - 1) * (q - 1);

        char[] harfler = girdi.toUpperCase().toCharArray();
        int[] indexler = new int[harfler.length];

        /** Girilen kelimenin kaçıncı harf olduklarını çıkarır */
        for (int i = 0; i < harfler.length; i++) {
            for (int j = 0; j < alfabe.length; j++) {
                if (harfler[i] == alfabe[j]) {
                    indexler[i] = j + 1;
                    break;
                }
            }
        }
        System.out.println("Girilen kelimenin kaçıncı harf oldukları: " + Arrays.toString(indexler));

        /** test */
        double[] şifre_index = new double[indexler.length];
        double[] b_sayıları = new double[şifre_index.length];
        for (int i = 0; i < indexler.length; i++) {
            double a = Math.pow(indexler[i], e);
            double b = (a % m);
            b_sayıları[i] = b;
            double temp = b % alfabe.length;
            double c = temp;
            şifre_index[i] = c;
        }
        System.out.println("Üstel fonksiyonumuz: " + Arrays.toString(b_sayıları));
        System.out.println("Şifrelenmiş mesajın kaçıncı harf olduğu: " + Arrays.toString(şifre_index));

        char[] şifre = new char[şifre_index.length];
        for (int i = 0; i < indexler.length; i++) {
            if (şifre_index[i]==0){
                şifre[i] = alfabe[(int) şifre_index[i] ];
            }else {
                şifre[i] = alfabe[(int) şifre_index[i] - 1];
            }
        }
        String girdi2 = String.valueOf(şifre);
        System.out.println("Şifreli hali: " + girdi2);

        System.out.println("----------Şifre çözme adımları----------");
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
        System.out.println("d sayısı: " + (int)d_sayısı);

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
        BigInteger[] tempMod29 = new BigInteger[big_b_sayıları.length];
        for (int i = 0; i < tempMod29.length; i++) {
            tempMod29[i] = temp[i].mod(BigInteger.valueOf(alfabe.length));
        }
        System.out.println("Deşifrelenmiş kelimenin mod 29'a göre kaçıncı harf oldukları: " + Arrays.toString(tempMod29));
        System.out.println("Deşifrelenmiş kelimenin kaçıncı harf oldukları: " + Arrays.toString(temp));

        char[] çıktı = new char[temp.length];
        for (int i = 0; i < çıktı.length; i++) {
            çıktı[i] = alfabe[Integer.parseInt(String.valueOf(temp[i].longValue())) - 1];
            //System.out.print(alfabe[Integer.parseInt(String.valueOf(temp[i].longValue())) - 1]);
        }
        //System.out.println();
        System.out.println("Deşifrelenmiş kelime: " + Arrays.toString(çıktı));


    }
}
