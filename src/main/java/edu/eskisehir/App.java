package edu.eskisehir;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

@SuppressWarnings("ALL")
public class App {
    public static final char[] alfabe = {'A', 'B', 'C', 'Ç', 'D', 'E', 'F', 'G', 'Ğ', 'H', 'İ', 'I', 'J', 'K', 'L',
            'M', 'N', 'O', 'Ö', 'P', 'R', 'S', 'Ş', 'T', 'U', 'Ü', 'V', 'Y', 'Z', ' '};

    public static int ebob(int sayı1, int sayı2) {
        int ebob = 1;
        for (int i = 1; i <= sayı1 && i <= sayı2; ++i) {
            if (sayı1 % i == 0 && sayı2 % i == 0)
                ebob = i;
        }
        return ebob;
    }

    public static void main(String[] args) {
        /** başlangıç */
        Scanner input = new Scanner(System.in);
        System.out.println("Şifrelemek istediğiniz kelimeyi giriniz: ");
        String girdi = input.nextLine();
        System.out.print("Bir asal sayı giriniz p: ");
        BigInteger p = BigInteger.valueOf(input.nextInt());
        System.out.print("Bir asal sayı daha giriniz q: ");
        BigInteger q = BigInteger.valueOf(input.nextInt());
        BigInteger big_φM = (p.subtract(BigInteger.valueOf(1))).multiply((q.subtract(BigInteger.valueOf(1))));
        int φM = big_φM.intValueExact();
        System.out.println("φM: " + φM);

        ArrayList<BigInteger> e_sayıları = new ArrayList<>();
        System.out.print("Olası e sayıları: ");
        for (int i = 2; i < φM; i++) {
            if (ebob(φM, i) == 1) {
                e_sayıları.add(BigInteger.valueOf(i));
                System.out.print(i + " ");
            }
        }
        System.out.println();
        BigInteger e;
        while (true) {
            System.out.print("e: ");
            e = BigInteger.valueOf(input.nextInt());
            if (e_sayıları.contains(e)) {
                break;
            }
        }

        BigInteger m = p.multiply(q);
        char[] harfler = girdi.toUpperCase().toCharArray();
        BigInteger[] indexler = new BigInteger[harfler.length];

        /** Girilen kelimenin kaçıncı harf olduklarını çıkarır */
        for (int i = 0; i < harfler.length; i++) {
            for (int j = 0; j < alfabe.length; j++) {
                if (harfler[i] == alfabe[j]) {
                    indexler[i] = BigInteger.valueOf(j + 1);
                    break;
                }
            }
        }
        System.out.println("Girilen kelimenin kaçıncı harf oldukları: " + Arrays.toString(indexler));

        /** test */
        BigInteger[] şifre_index = new BigInteger[indexler.length];
        BigInteger[] b_sayıları = new BigInteger[şifre_index.length];
        for (int i = 0; i < indexler.length; i++) {
            BigInteger a = indexler[i].pow(e.intValueExact());
            BigInteger b = a.mod(m);
            b_sayıları[i] = b;
            BigInteger temp = b.mod(BigInteger.valueOf(alfabe.length));
            BigInteger c = temp;
            şifre_index[i] = c;
        }
        System.out.println("Üstel fonksiyonumuz: " + Arrays.toString(b_sayıları));
        System.out.println("Şifrelenmiş mesajın kaçıncı harf olduğu: " + Arrays.toString(şifre_index));

        char[] şifre = new char[şifre_index.length];
        for (int i = 0; i < indexler.length; i++) {
            if (şifre_index[i].equals(BigInteger.valueOf(0))) {
                şifre[i] = alfabe[şifre_index[i].intValueExact()];
            } else {
                şifre[i] = alfabe[şifre_index[i].subtract(BigInteger.valueOf(1)).intValueExact()];
            }
        }
        String girdi2 = String.valueOf(şifre);
        System.out.println("Şifreli hali: " + girdi2);

        System.out.println("----------Şifre çözme adımları----------");
        /** çözme adımları */
        char[] harfler2 = girdi2.toUpperCase().toCharArray();
        BigInteger[] indexler2 = new BigInteger[harfler.length];

        /** d sayısını bulmak */
        BigInteger d_sayısı = BigInteger.valueOf(0);
        BigInteger sayaç = BigInteger.valueOf(1);
        while (true) {
            d_sayısı = big_φM.multiply(sayaç).add(BigInteger.valueOf(1)).divide(e);
            if (big_φM.multiply(sayaç).add(BigInteger.valueOf(1)).mod(e).equals(BigInteger.valueOf(0))) {
                break;
            } else {
                sayaç = sayaç.add(BigInteger.valueOf(1));
            }
        }
        System.out.println("d sayısı: " + d_sayısı);

        /*BigInteger[] big_b_sayıları = new BigInteger[b_sayıları.length];
        for (int i = 0; i < b_sayıları.length; i++) {
            big_b_sayıları[i] = new BigInteger(String.valueOf(new Double(b_sayıları[i]).longValue()));
        }*/
        //BigInteger big_d_sayısı = new BigInteger(String.valueOf(new Double(d_sayısı).longValue()));
        //BigInteger big_m = new BigInteger(String.valueOf(new Double(m).longValue()));

        BigInteger[] temp = new BigInteger[b_sayıları.length];
        for (int i = 0; i < b_sayıları.length; i++) {
            temp[i] = b_sayıları[i].modPow(d_sayısı, m);
        }
        BigInteger[] tempMod29 = new BigInteger[b_sayıları.length];
        for (int i = 0; i < tempMod29.length; i++) {
            tempMod29[i] = temp[i].mod(BigInteger.valueOf(alfabe.length));
        }
        System.out.println("Deşifrelenmiş kelimenin kaçıncı harf oldukları: " + Arrays.toString(temp));
        System.out.println("Deşifrelenmiş kelimenin mod 30'a göre kaçıncı harf oldukları: " + Arrays.toString(tempMod29));

        char[] çıktı = new char[temp.length];
        for (int i = 0; i < çıktı.length; i++) {
            çıktı[i] = alfabe[Integer.parseInt(String.valueOf(tempMod29[i].longValue())) - 1];
            //System.out.print(alfabe[Integer.parseInt(String.valueOf(temp[i].longValue())) - 1]);
        }
        //System.out.println();
        System.out.println("Deşifrelenmiş kelime: " + Arrays.toString(çıktı));


    }
}
