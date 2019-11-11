
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 1,将大文件分成若干个一千万行的小文件，每个小文件分别从大到小排序;
 *
 * 2,取每个排好序的小文件的第一行互相比较，其中最大的即为当前大文件的最大的一行;
 *
 * 3,求这一行的MD5值，再取这一行所在小文件的下一行与之前取出的小文件行比较大小，
 * 再得出最大行并求MD5值，循环即可得整个大文件排序后的MD5值;
 *
 * 4,比较所得10个大文件的MD5值，MD5值相同则equal
 */
public class App {
	public static void main(String[] args){
		long start = System.currentTimeMillis();
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		int num;
		try {
			num = Integer.parseInt(bufferedReader.readLine());
			Map<String, FutureTask> allFilePath = new HashMap<>();
			//多线程得到每个大文件排序后的MD5值
			for (int i = 0; i < num; i++) {
				String filePath = bufferedReader.readLine();
				FileProcessThread fpt = new FileProcessThread(filePath, i);
				FutureTask<String> ft = new FutureTask<>(fpt);
				allFilePath.put(filePath,ft);
				Thread t = new Thread(ft);
				t.start();
			}
			//有相同的MD5值的大文件的路径放入同一个list,再把md5作key，list作value
			Map<String, List<String>> md5Map = new HashMap<>();
			for (String filePath: allFilePath.keySet()) {
				FutureTask<String> f = allFilePath.get(filePath);
				String md5 = f.get();
				List<String> paths = md5Map.get(md5);
				if (paths == null) {
					paths = new ArrayList<>();
					paths.add(filePath);
					md5Map.put(md5, paths);
				} else {
					paths.add(filePath);
				}
			}
			//遍历md5,如果对应list的值大于1，则有equal的大文件
			boolean flag = false;
			for (String key : md5Map.keySet()) {
				List<String> paths = md5Map.get(key);
				if (paths!=null && paths.size()>1) {
					flag = true;
					System.out.println(paths + " 相同 ");
				}
			}
			if (!flag) {
				System.out.println("没有相同文件...");
			}
			System.out.println((System.currentTimeMillis()-start)+"ms");
		} catch (IOException | InterruptedException | ExecutionException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			System.out.println("输入数字错误！");
		}
	}
	
}	
