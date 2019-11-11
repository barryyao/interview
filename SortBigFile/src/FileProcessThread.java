import java.util.concurrent.Callable;

/**
 * @author Yao Pan
 * @date 2019/10/24
 */
public class FileProcessThread implements Callable<String> {
    private String filePath;
    private int fileNO;
    public FileProcessThread(String filePath, int fileNO) {
        this.filePath = filePath;
        this.fileNO = fileNO;
    }

    @Override
    public String call() {
        BigFile bigFile = new BigFile(filePath, fileNO);
        return bigFile.getSortedFileMD5();
    }
}
