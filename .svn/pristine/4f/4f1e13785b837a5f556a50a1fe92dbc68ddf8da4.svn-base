package com.xhcms.lottery.commons.util;

public class BetCalculator {

    private double minBonus;

    private double maxBonus;

    private int totalNote;

    private double[][] matchOdds;

    private double[] maxOdds;

    private double[] minOdds;

    public BetCalculator(double[][] matchOdds) {
        this.matchOdds = matchOdds;

        // 计算每场赛事的最大赔率
        this.maxOdds = new double[matchOdds.length];
        this.minOdds = new double[matchOdds.length];
        for (int i = 0; i < matchOdds.length; i++) {
            double[] mo = matchOdds[i];
            maxOdds[i] = mo[0];
            minOdds[i] = mo[0];
            for (int j = 1; j < mo.length; j++) {
                maxOdds[i] = Math.max(maxOdds[i], mo[j]);
                minOdds[i] = Math.min(minOdds[i], mo[j]);
            }
        }
    }

    public int handle(int[] index, int n, int m) {
        totalNote = 0;
        maxBonus = 0;
        minBonus = 0;
        if (m == 1) {
            cnm(index, n, n);
        } else {
            cnMultiM(index, n, m);
        }
        return totalNote;
    }

    private void cnMultiM(int[] index, int n, int m) {
        int minM = 0, maxM = 0;
        switch (n * 1000 + m) {
            case 3003:
            case 4006:
            case 5010:
            case 6015:
                minM = 2;
                break;
            case 3004:
            case 5020:
            case 6035:
                minM = 2;
                maxM = 3;
                break;
            case 4011:
            case 6050:
                minM = 2;
                maxM = 4;
                break;
            case 5026:
                minM = 2;
                maxM = 5;
                break;
            case 6057:
                minM = 2;
                maxM = 6;
                break;
            case 7120:
                minM = 2;
                maxM = 7;
                break;
            case 8247:
                minM = 2;
                maxM = 8;
                break;
            case 4004:
            case 6020:
                minM = 3;
                break;
            case 4005:
                minM = 3;
                maxM = 4;
                break;
            case 5016:
                minM = 3;
                maxM = 5;
                break;
            case 6042:
                minM = 3;
                maxM = 6;
                break;
            case 5005:
            case 7035:
            case 8070:
                minM = 4;
                break;
            case 5006:
                minM = 4;
                maxM = 5;
                break;
            case 6022:
                minM = 4;
                maxM = 6;
                break;
            case 6006:
            case 7021:
            case 8056:
                minM = 5;
                break;
            case 6007:
                minM = 5;
                maxM = 6;
                break;
            case 7007:
            case 8028:
                minM = 6;
                break;
            case 7008:
                minM = 6;
                maxM = 7;
                break;
            case 8008:
                minM = 7;
                break;
            case 8009:
                minM = 7;
                maxM = 8;
                break;
            default:
                return;
        }
        for (maxM = Math.max(minM, maxM); minM <= maxM; minM++) {
            cnm(index, n, minM);
        }
    }

    // 排列组合，n为下标，m为上标
    private void cnm(int[] index, int n, int m) {
        int[] x = new int[m];
        final int MAX = m - 1;
        final int MAX_VALUE = n - MAX;

        int c = 0; // 游标
        while (x[0] < MAX_VALUE) {
            c++;
            for (; c < m; c++) {
                x[c] = x[c - 1] + 1;
            }

            while (x[MAX] < n) {
                int note = 1;
                double min = 1;
                double max = 1;
                for (int i = 0, j = 0; i < x.length; i++) {
                    j = index[x[i]];
                    min *= minOdds[j];
                    max *= maxOdds[j];
                    note *= matchOdds[j].length;
                }

                minBonus += min;
                maxBonus += max;
                totalNote += note;
                x[MAX]++;
            }

            c = MAX - 1;
            while (c >= 0) {
                if (x[c] + 1 < x[c + 1]) {
                    x[c]++;
                    break;
                }
                c--;
            }
        }
    }

    public double getMaxBonus() {
        return 2 * maxBonus;
    }

    public double getMinBonus() {
        return 2 * minBonus;
    }

}
