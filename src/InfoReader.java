import java.util.ArrayList;

public interface InfoReader<T> 
{
	void readFile(String filePath, ArrayList<T> dataList);
}