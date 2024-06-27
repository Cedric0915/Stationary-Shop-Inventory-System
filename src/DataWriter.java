import java.util.ArrayList;

public interface DataWriter<T> 
{
	void writeDataToFile(String filePath, ArrayList<T> dataList); 
}