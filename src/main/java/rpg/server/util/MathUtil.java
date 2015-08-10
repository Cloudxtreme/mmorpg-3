package rpg.server.util;

import java.util.Random;

public class MathUtil {

	/**
	 * 向上取整
	 *
	 * @param a
	 * @param b
	 * @return
	 */
	public static final int floor(int a, int b) {
		return a / b + (a % b == 0 ? 0 : 1);
	}

	/**
	 * 计算两点是否在某个范围内
	 *
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @param range
	 * @return
	 */
	public static final boolean isInRange(int x1, int y1, int x2, int y2,
			int range) {
		if (Math.abs(x1 - x2) > range) {
			return false;
		}
		if (Math.abs(y1 - y2) > range) {
			return false;
		}
		return true;
	}

	/**
	 * 计算两点距离
	 *
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @return
	 */
	public static final int distance(int x1, int y1, int x2, int y2) {
		return Math.abs(x1 - x2) + Math.abs(y1 - y2);
	}

	/**
	 * 判断某字符串是否为数字
	 *
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		if ((str == null) || str.equals("")) {
			return false;
		}
		for (int i = str.length(); --i >= 0;) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 在[x,y]范围内随机
	 *
	 * @param x
	 * @param y
	 * @return
	 */
	public static int randInRange(int x, int y) {
		if (x == y) {
			return x;
		}
		if (x > y) {
			int temp = x;
			x = y;
			y = temp;
		}
		return x + random.nextInt(y - x + 1);
	}

	/**
	 * 在[x, y]范围内随机
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public static float randInRange(float x, float y) {
		if (x == y)
			return x;
		if (x > y) {
			float temp = x;
			x = y;
			y = temp;
		}
		return x + (y - x) * random.nextFloat();
	}

	/**
	 * 以指定概率分布从多个组中随机选定一个组
	 *
	 * @param probs
	 *            ：依次列出选中各个组的概率（不必归一化）
	 * @return int：选中的组下标
	 */
	public static int randCategory(int[] probs) {
		if (probs == null || probs.length == 0) {
			return -1;
		}

		// 归一化处理
		int total = 0;
		for (int p : probs) {
			total += p;
		}

		// 防止nextInt()抛出异常
		if (total <= 0) {
			return -1;
		}

		int rand = random.nextInt(total);
		int current = 0;
		for (int i = 0; i < probs.length; i++) {
			current += probs[i];
			if (rand < current) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * 以100为分母计算权重，在指定的概率之外的返回-1 chenzhu 2012-6-13 例如{20，30}
	 * 则计算100以内的随机数，随机数在20，50之内返回下标0,1，在50之外返回-1
	 *
	 * @param probs
	 *            ：依次列出选中各个组的概率（总和小于100）
	 * @return int：选中的组下标
	 */
	public static int randCategoryHundred(int[] probs) {
		if (probs == null || probs.length == 0) {
			return -1;
		}

		// 归一化处理
		int total = 0;
		for (int p : probs) {
			total += p;
		}

		// 防止nextInt()抛出异常
		if (total <= 0) {
			return -1;
		}

		int rand = random.nextInt(100);
		if (rand > total) {
			return -1;
		}
		int current = 0;
		for (int i = 0; i < probs.length; i++) {
			current += probs[i];
			if (rand < current) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * 以指定概率分布从多个组中随机选定一个组
	 *
	 * @param probs
	 *            ：依次列出选中各个组的概率（不必归一化）
	 * @return int：选中的组下标
	 */
	public static int randCategory(Float[] probs) {
		if (probs == null || probs.length == 0) {
			return -1;
		}

		// 归一化处理
		float total = 0.0f;
		for (float p : probs) {
			total += p;
		}

		// 防止nextInt()抛出异常
		if (total <= 0) {
			return -1;
		}

		float rand = random.nextFloat();
		float current = 0;
		for (int i = 0; i < probs.length; i++) {
			current += probs[i] / total;
			if (rand < current) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * 从n个中随机选取m个 lincy 2010-01-25
	 *
	 * @param m
	 * @param n
	 * @return
	 */
	public static boolean[] randMOutofN(int m, int n) {
		boolean[] result = new boolean[n];
		if (m >= n) {
			for (int i = 0; i < n; i++) {
				result[i] = true;
			}
		} else {
			int now = 0;
			while (now < m) {
				int index = random.nextInt(n);
				if (!result[index]) {
					result[index] = true;
					now++;
				}
			}
		}
		return result;
	}

	/**
	 * 将m个随机分配到n个中 lincy 2010-12-30
	 *
	 * @param m
	 * @param n
	 * @return
	 */
	public static int[] randMIntoN(int m, int n) {
		int[] result = new int[n];
		do {
			result[random.nextInt(n)]++;
		} while (--m > 0);
		return result;
	}

	/**
	 * <p>
	 * 查找一组数字中最小的一个<br/>
	 * </p>
	 *
	 * @param num1
	 * @param num
	 * @return
	 */
	public static int min(int num1, int... num) {

		int temp = num1;
		for (int n : num) {

			temp = Math.min(temp, n);
		}
		return temp;
	}

	/**
	 * 求一组数字的最大值
	 *
	 * @param num1
	 * @param num
	 * @return
	 */
	public static int max(int num1, int... num) {
		int temp = num1;
		for (int n : num) {
			temp = Math.max(temp, n);
		}
		return temp;
	}

	/**
	 * 从指定的区间内获得一个随机数
	 *
	 * @param min
	 * @param max
	 * @return
	 */
	public static int getRandomNum(int min, int max) {
		if (min == 0 && max == 0) {
			return 0;
		}
		return random.nextInt(max) + min;
		// int iRet = random.nextInt();
		// if (iRet < 0) {
		// iRet = -iRet;
		// }
		// iRet = iRet % ((max + 1) - min) + min;
		// return iRet;

	}

	/**
	 * 根据指定模式，寻找arr中第一个与x满足指定关系的index <br/>
	 *
	 * @param x
	 * @param arr
	 * @param mode
	 *            0: x < arr[i] <br/>
	 *            1: x <= arr[i] <br/>
	 *            2：x = arr[i] <br/>
	 *            3: x >= arr[i] <br/>
	 *            4: x > arr[i]
	 * @return the first index whose element meets the requirement <br/>
	 *         or -1 if the requirement is never met through the whole array
	 */
	public static int getFirstIndex(int x, final int[] arr, int mode) {
		switch (mode) {
		case 0:
			for (int i = 0; i < arr.length; i++) {
				if (x < arr[i]) {
					return i;
				}
			}
			return -1;
		case 1:
			for (int i = 0; i < arr.length; i++) {
				if (x <= arr[i]) {
					return i;
				}
			}
			return -1;
		case 2:
			for (int i = 0; i < arr.length; i++) {
				if (x == arr[i]) {
					return i;
				}
			}
			return -1;
		case 3:
			for (int i = 0; i < arr.length; i++) {
				if (x >= arr[i]) {
					return i;
				}
			}
			return -1;
		case 4:
			for (int i = 0; i < arr.length; i++) {
				if (x > arr[i]) {
					return i;
				}
			}
			return -1;
		}
		return -1;
	}

	public static final Random random = new Random();

	/**
	 * 获取npc路径
	 * 
	 * @param str
	 * @return
	 */
	public static int[][] getNpcDestCR(String str, String delimiter) {
		String[] temp = str.split(delimiter);
		int len = temp.length / 2;
		int[][] destCR = new int[len][2];
		for (int i = 0; i < len; i++) {
			for (int j = 0; j < 2; j++) {
				destCR[i][j] = Integer.parseInt(temp[i * 2 + j]);
			}
		}
		return destCR;
	}

	/**
	 * 取[0,1)随机浮点小数
	 * 
	 * @return
	 */
	public static float randomDot() {
		return random.nextFloat();
	}

	/**
	 * 随机整数
	 * 
	 * @return
	 */
	public static int randomInt() {
		return random.nextInt();
	}

	/**
	 * 取[0,n)随机整数
	 * 
	 * @param n
	 * @return
	 */
	public static int randomInt(int n) {
		if (n <= 0) {
			return n;
		}
		return random.nextInt(n);
	}

	/**
	 * 随机bool
	 * 
	 * @return
	 */
	public static boolean randomBool() {
		int n = randomInt(2);
		return n == 1 ? true : false;
	}

	/**
	 * 计算数组[0,end]项和
	 * 
	 * @param array
	 * @param end
	 * @return
	 */
	public static long countTotal(int array[], int end) {
		if (end > array.length - 1) {
			end = array.length - 1;
		}
		long result = 0;
		for (int i = 0; i <= end; i++) {
			result += array[i];
		}
		return result;
	}

	/**
	 * 求和
	 * 
	 * @param val
	 * @return
	 */
	public static float sum(float... val) {
		float sum = 0.0f;
		for (float v : val)
			sum += v;
		return sum;
	}

	/**
	 * 求和
	 * 
	 * @param val
	 * @return
	 */
	public static int sum(int... val) {
		int sum = 0;
		for (int v : val)
			sum += v;
		return sum;
	}

	/**
	 * 平均值
	 * 
	 * @param val
	 * @return
	 */
	public static float avg(float... val) {
		if (val.length == 0)
			return 0;
		float sum = 0.0f;
		for (float v : val)
			sum += v;
		return sum / val.length;
	}

	/**
	 * 求和
	 * 
	 * @param val
	 * @return
	 */
	public static int avg(int... val) {
		if (val.length == 0)
			return 0;
		int sum = 0;
		for (int v : val)
			sum += v;
		return sum / val.length;
	}
}
