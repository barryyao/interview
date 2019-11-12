import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LotteryApp {
	public static Random random = new Random();
	public static void main(String[] args) throws IOException {

		BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
		String s = buffer.readLine();
		int input = Integer.parseInt(s);
		int changeWin=0,noChangeWin=0;
		for (int i = 0; i < input; i++) {
			List<Integer> initList = new ArrayList<>();
			List<Integer> excludeList = new ArrayList<>();
			for (int j = 0; j < 4; j++) {
				initList.add(j);
			}
			int prize = random.nextInt(4);
			int userChoose = random.nextInt(4);
			excludeList.add(prize);
			excludeList.add(userChoose);
			int hostChoose = chooseBox(initList, excludeList);
			List<Integer> excludeList1 = new ArrayList<>();
			for (int j : initList) {
				if (j == userChoose || j == hostChoose) {
					excludeList1.add(j);
				}
			}
			int afterChange = chooseBox(initList,excludeList1);
			if (prize == afterChange) {
				changeWin += 1;
			} 
			if (userChoose == prize) {
				noChangeWin += 1;
			} 

		}
		System.out.println(changeWin+","+noChangeWin);
		buffer.close();
	}
	public static int chooseBox(List<Integer> initList , List<Integer> excludeList) {
		List<Integer> tempList = new ArrayList<>();
		for (int i : initList) {
			if (!excludeList.contains(i)) {
				tempList.add(i);
			}
		}
		int d = random.nextInt(tempList.size());
		return tempList.get(d);
	}
}
