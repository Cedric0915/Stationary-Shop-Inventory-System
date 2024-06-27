import java.util.ArrayList;

public interface DataReader<T> 
{
	int readFile(String filePath, ArrayList<T> dataList);
}