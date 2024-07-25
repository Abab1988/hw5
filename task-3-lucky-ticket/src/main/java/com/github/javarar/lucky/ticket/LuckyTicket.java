package com.github.javarar.lucky.ticket;

import java.util.List;
import java.util.Locale;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class LuckyTicket {

    public static long luckyTicketProbabilityLeningradskiy(int serialNumberLength) {
        String pattern = "%0" + serialNumberLength + "d";
        long to = (long) Math.pow(10, serialNumberLength);
        return LongStream.range(0, to)
                .parallel()
                .mapToObj(x -> String.format(pattern, x))
                .filter(LuckyTicket::isLeningradskiy)
                .count();
    }

    public static long luckyTicketProbabilityMoskowskiy(int serialNumberLength) {
        String pattern = "%0" + serialNumberLength + "d";
        long to = (long) Math.pow(10, serialNumberLength);
        return LongStream.range(0, to)
                .parallel()
                .mapToObj(x -> String.format(pattern, x))
                .filter(LuckyTicket::isMoskowskiy)
                .count();
    }

    static boolean isLeningradskiy(String ticket) {
        char[] digits = ticket.toCharArray();
        int sum = 0;
        int l = 0, r = digits.length - 1;
        while(l < r) {
            sum += (digits[l] -'0') - ((digits[r] -'0'));
            l++;
            r--;
        }
        return sum == 0;
    }

    static boolean isMoskowskiy(String ticket) {
        char[] digits = ticket.toCharArray();
        int sum = 0;
        int l = 0, r = l + 1;
        while(r < digits.length) {
            sum += (digits[l] -'0') - ((digits[r] -'0'));
            l += 2;
            r += 2;
        }
        return sum == 0;
    }

}