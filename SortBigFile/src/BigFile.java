import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BigFile {
	private String filePath;
	private int fileNO;
	//每个小文件的行数
	private int subFileMaxLine = 1000000;

	public BigFile(String filePath, int fileNO) {
		this.filePath = filePath;
		this.fileNO = fileNO;
	}
	public String getSortedFileMD5() {
		String dirPath = "/temp"+fileNO;
		File dir = new File(dirPath);
		if (!dir.isDirectory()) {
			dir.mkdir();
		}
		List<BufferedReader> suBufferedReaders = generateSortSubFile(dirPath);
		return generateMD5(dirPath, suBufferedReaders);
	}
	public List<BufferedReader> generateSortSubFile(String dirPath){
		try {
			List<String> subContents = new ArrayList<>(subFileMaxLine);
			List<BufferedReader> suBufferedReaders = new ArrayList<>();
			BufferedReader bReader = new BufferedReader(new FileReader(filePath));
			String string = null;
			int lineNO = 0;
			int subFileNO = 0;

			while ((string = bReader.readLine()) != null) {
				lineNO++;
				subContents.add(string);
				if (lineNO == subFileMaxLine) {
					//每循环subFileMaxLine行就排序写入一个小文件
					writeSubFile(dirPath + "/temp" + subFileNO + ".txt", subContents);
					lineNO = 0;
					subContents.clear();
					subFileNO++;

				}
			}
			if (!subContents.isEmpty()) {
				writeSubFile(dirPath + "/temp" + subFileNO + ".txt", subContents);
			}
			File fileDir = new File(dirPath);
			if (!fileDir.isDirectory()) {
				fileDir.mkdir();
			}
			for (int i = 0; i < subFileNO; i++) {
				String subFilePath = dirPath + "/temp" + i + ".txt";
				File subFile = new File(subFilePath);
				if (!subFile.isFile()) {
					subFile.createNewFile();
				}
				BufferedReader br = new BufferedReader(new FileReader(subFilePath));
				suBufferedReaders.add(br);
			}
			bReader.close();
			return suBufferedReaders;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
	public String generateMD5(String dirPath,List<BufferedReader> suBufferedReaders){
		if (suBufferedReaders == null) {
			return null;
		}
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			List<String> strList = new ArrayList<>();

			for (int i = 0; i < suBufferedReaders.size(); i++) {
				String str = suBufferedReaders.get(i).readLine();
				if (str != null) {
					strList.add(str);
				} else {
					suBufferedReaders.remove(i);
				}
			}
			boolean flag = true;
			while (flag) {
				int smallest = 0;
				String temp = strList.get(0);
				for (int i = 0; i < strList.size(); i++) {
					if (temp.compareTo(strList.get(i)) > 0) {
						temp = strList.get(i);
						//最小的行所有的小文件在list中的下标
						smallest = i;
					}
				}
				if (temp != null) {
					md.update(temp.getBytes());
				}
				String newStr = suBufferedReaders.get(smallest).readLine();
				if (newStr != null) {
					strList.set(smallest, newStr);
				} else {
					//若为空则已经到文件末尾，关闭reader并从list中去掉
					suBufferedReaders.get(smallest).close();
					suBufferedReaders.remove(smallest);
					strList.remove(smallest);
				}
				if (suBufferedReaders.size() == 0) {
					flag = false;
				}
			}
			if (!strList.isEmpty()) {
				for (String s : strList) {
					if (s != null) {
						md.update(s.getBytes());
					}
				}
			}
			deleteDir(dirPath);
			return new BigInteger(1, md.digest()).toString(16);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void writeSubFile(String subFilePath,List<String> strings) {
		BufferedWriter bWriter = null;
		try {
			File file = new File(subFilePath);
			if (!file.isFile()) {
				file.createNewFile();
			}
			Collections.sort(strings);
			bWriter = new BufferedWriter(new FileWriter(subFilePath));
			for (String string : strings) {
				bWriter.write(string+"\n");
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (bWriter != null) {
				try {
					bWriter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}



	public  void deleteDir(String dirPath)
	{
		File file = new File(dirPath);
		if(file.isFile())
		{
			file.delete();
		} else {
			File[] files = file.listFiles();
			if(files == null)
			{
				file.delete();
			}else
			{
				for (int i = 0; i < files.length; i++)
				{
					deleteDir(files[i].getAbsolutePath());
				}
				file.delete();
			}
		}
	}

}
