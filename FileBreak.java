package files;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

/**
 * @author Dasun
 *
 */
public class FileBreak
{

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception
	{
		File fileIn = new File(args[0]);
		FileInputStream fInStream = new FileInputStream(fileIn);
		
		FileChannel channelIn = fInStream.getChannel();
		long inputFileSize = channelIn.size();
		System.out.println("inputFileSize="+inputFileSize);
		if(args[1].startsWith("%")) 
		{
			//break file with percentage
			int partitionPercentage = Integer.parseInt(args[1].substring(1));
			System.out.println("partitionPercentage="+partitionPercentage);
			
			long partSize = (inputFileSize*partitionPercentage/100);
			System.out.println("partSize="+partSize);
			int i =1;
			long lastReadPoint = 0;
			while(true) 
			{
				FileOutputStream fOutStream = new FileOutputStream(new File(args[0]+"."+i));
				FileChannel out = fOutStream.getChannel();
				out.position(0);
				System.out.println("lastReadPoint="+lastReadPoint);
				System.out.println("(fileSize - lastReadPoint - partSize)="+(inputFileSize - lastReadPoint - partSize));
				if((inputFileSize - lastReadPoint - partSize) < partSize) 
				{
					partSize = inputFileSize-lastReadPoint;
					System.out.println("last partition size="+partSize);
				}
				
				long writeCount = channelIn.transferTo(lastReadPoint, partSize, out);
				lastReadPoint+=writeCount;
				
				System.out.println("writeCount="+writeCount);
				
				out.force(true);
				out.close();
				fOutStream.close();
				
				i++;
				if(lastReadPoint >= inputFileSize) 
				{
					System.out.println("end of file generation");
					break;
				}
			}
			channelIn.close();
			fInStream.close();
		}
		
	}

}
