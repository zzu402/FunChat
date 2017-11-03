package com.hzz.util;

import java.text.NumberFormat;
import java.util.Locale;

public class StringUtils {

	/**
	 * 
	 * 相似度转百分比
	 */

	public static String similarityResult(double resule) {

		return NumberFormat.getPercentInstance(new Locale("en ", "US "))
				.format(resule);

	}

	/**
	 * 
	 * 相似度比较
	 * 
	 * @param strA
	 * 
	 * @param strB
	 * 
	 * @return
	 */

	public static double SimilarDegree(String strA, String strB) {

		String newStrA = removeSign(strA);

		String newStrB = removeSign(strB);

		int temp = Math.max(newStrA.length(), newStrB.length());

		int temp2 = longestCommonSubstring(newStrA, newStrB);

		return temp2 * 1.0 / temp;

	}

	private static String removeSign(String str) {

		StringBuffer sb = new StringBuffer();

		for (char item : str.toCharArray())

			if (charReg(item)) {
				sb.append(item);

			}

		return sb.toString();

	}

	private static boolean charReg(char charValue) {

		return (charValue >= 0x4E00 && charValue <= 0X9FA5)

		|| (charValue >= 'a' && charValue <= 'z')

		|| (charValue >= 'A' && charValue <= 'Z')

		|| (charValue >= '0' && charValue <= '9');

	}

	private static int longestCommonSubstring(String str1, String str2) {

		int size_1 = str1.length();// 字符串1长度
		int size_2 = str2.length(); // 字符串长度2
		if (size_1 == 0 || size_2 == 0) {
			return 0;
		}

		int longest = 0; // 最长公共子串长度
		int index = 0; // 最长公共子串起始位置
		/*
		 * l[i][j] 代表str1.charAt(i)到str2.charAt(j)的最长公共子串长度 【注】str.charAt(index)
		 * 即代表字符串str的第index字符
		 */
		int l[][] = new int[size_1 + 1][size_2 + 1];

		/*
		 * 初始化l[0][i]的长度，即判断str1的第一个字符和str2的所有字符是否相同，相同则初始化1，否则为0
		 */
		for (int i = 0; i < size_2; i++) {
			if (str1.charAt(0) == str2.charAt(i))
				l[0][i] = 1;
			else
				l[0][i] = 0;
		}
		/*
		 * 同理，计算l[i][0]的长度
		 */
		for (int i = 1; i < size_1; i++) {
			if (str1.charAt(i) == str2.charAt(0))
				l[i][0] = 1;
			else
				l[i][0] = 0;
		}

		/*
		 * 核心代码，计算l[i][j]的长度。 如果str1.charAt[i]==str2.charAt[j],则str1.char[i]同
		 * str2[j]构成公共子串关系，那么l[i][j]等于l[i-1][j-1]+1
		 */

		for (int i = 1; i < size_1; i++) {
			for (int j = 1; j < size_2; j++)
				if (str1.charAt(i) == str2.charAt(j)) {
					l[i][j] = l[i - 1][j - 1] + 1;
					if (longest < l[i][j]) {
						longest = l[i][j]; // longest 记录当前最长公共子串长度
						index = i - longest + 1; // index 记录当前最长公共子串起始位置
					}
				}
		}
		return  longest;
	}

}
