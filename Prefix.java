import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;

public class Prefix {
	public static void main(String args[]) {
//		args = new String[] { "", "test.txt" };
		if (args.length > 2) {
			logError("Your file name is wrong\n Program end");
			return;
		} else {
			String fileName = args[0];
			try {
				BufferedReader br = new BufferedReader(new FileReader(fileName));
				String line;
				while ((line = br.readLine()) != null) {
					// process the line.
					String[] arr = line.split(" ");
					parse(arr);
				}
				br.close();
			} catch (Exception e) {
				e.printStackTrace();
				logError("Read file error");
			}
		}
		/*
		 * For Test
		 */
		// Scanner scan = new Scanner(System.in);
		// String line = scan.nextLine();
		// String[] arr = line.split(" ");
		// parse(arr);
	}

	private static void parse(String[] arr) {
		map = new HashMap<String, String>();
		// TODO Auto-generated method stub
		if (arr.length == 1) {
			if(isNumeric(arr[0]))	
				System.out.println(arr[0]);
			else
				System.out.println("undefined");
		} else {
			Stack<Node> myStack = new Stack<Node>();
			for (int i = 0; i < arr.length; i++) {
				/*
				 * For Test
				 */
				// System.out.print(arr[i]);
				if (arr[i].equals("(")) {
					Node node = new Node();
					node.brace = "(";
					myStack.push(node);
				} else if (arr[i].equals(")")) {
					Node cn = myStack.pop();
					String res = calculateExpression(cn.content);
					if (myStack.size() == 0)
						System.out.println(res);
					else if (res != null) {
						if (res.equals("undefined")) {
							logError("undefined");
							break;
						} else {
							myStack.peek().content.add(res);
						}
					}
				} else {
					myStack.peek().content.add(arr[i]);
				}
			}
		}
	}

	public static HashMap<String, String> map;

	private static String calculateExpression(ArrayList<String> content) {
		if (content.size() == 1) {
			if (map.containsKey(content.get(0))) {
				return map.get(content.get(0));
			}
			return content.get(0);
		}
		// TODO Auto-generated method stub
		if (content.size() == 2) {
			if (map.containsKey(content.get(1))) {
				map.put(content.get(0), map.get(content.get(1)));
			} else
				map.put(content.get(0), content.get(1));
			return null;
		} else if (content.size() == 3) {
			/* parse 2nd element */
			String two = content.get(1);
			int twoNum;
			if (isNumeric(two)) {
				twoNum = Integer.parseInt(two);
			} else {
				if (map.containsKey(two))
					twoNum = Integer.parseInt(map.get(two));
				else
					return "undefined";
			}
			/* parse 3rd element */
			String three = content.get(2);
			int threeNum;
			if (isNumeric(three)) {
				threeNum = Integer.parseInt(three);
			} else {
				if (map.containsKey(three))
					threeNum = Integer.parseInt(map.get(three));
				else
					return "undefined";
			}
			String one = content.get(0);
			if (one.equals("+")) {
				return (twoNum + threeNum) + "";
			}
			if (one.equals("-")) {
				return (twoNum - threeNum) + "";
			}
			if (one.equals("*")) {
				return (twoNum * threeNum) + "";
			}
			if (one.equals("/")) {
				return (twoNum / threeNum) + "";
			}
			if (one.equals("^")) {
				int res = (int) Math.pow(twoNum, threeNum);
				return res + "";
			}
			if (one.equals("%")) {
				return (twoNum % threeNum) + "";
			}
		}
		return null;
	}

	private static void logError(String error) {
		System.out.println(error);
	}

	public static boolean isNumeric(String str) {
		try {
			double d = Double.parseDouble(str);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}
}

class Node {
	String brace;
	ArrayList<String> content = new ArrayList<String>();

}
