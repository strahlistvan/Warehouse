package data;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

public class FtpConnection
{
	private String user, password, host, path;
	private String connectionFile = "ftp_properties.csv";
	
	public FtpConnection() 
	{
		init();
	}
	
	public FtpConnection(String filePath)
	{
		this.connectionFile = filePath;
		init();
	}
	
	public void init()
	{
		try 
		{
			HashMap <String, String> ftpprop = FileHandler.readFtpProperties(connectionFile);
		    this.user = ftpprop.get("user");
		    this.password = ftpprop.get("password");
		    this.host = ftpprop.get("host");
		    this.path = ftpprop.get("path");
		} 
		catch (IOException ex) 
		{
			System.err.println("Error while read '"+connectionFile+"' file.");
			ex.printStackTrace();
		} 
	}
	
	public void setConnectionFile(String filePath)
	{
		this.connectionFile = filePath;
	}
	
	private static String getFileName(String path)
	{
		String fileName = path;
	//	fileName = fileName.replaceAll("\", "/"); //Windows path
		int pos = (fileName.lastIndexOf("/") == -1)? 0: fileName.lastIndexOf("/");
	//	System.out.println(fileName.substring(pos+1));
		return fileName.substring(pos+1);
	}
	
	public void uploadFile(String uploadFilePath)
	{
		String ftpUrlString = String.format("ftp://%s:%s@%s/%s", this.user, 
				this.password, this.host, this.path);
		ftpUrlString = ftpUrlString + "/" + getFileName(uploadFilePath);
		System.out.println(ftpUrlString);
		
		try
		{
			URL ftpUrl = new URL(ftpUrlString);
			System.out.println(ftpUrl);
			URLConnection conn = ftpUrl.openConnection();
			System.out.println(conn);
			File file = new File (uploadFilePath);
			System.out.println(file);
			
			OutputStream output = conn.getOutputStream();
			

			FileInputStream input = new FileInputStream(uploadFilePath);
			
			int bytesRead = 0;
			byte[] buffer = new byte[1];
			while (bytesRead!=-1)
			{
				bytesRead = input.read(buffer);
				output.write(buffer);
			}
			input.close();
			output.close();
		} 
		catch (MalformedURLException ex) 
		{
			System.err.println("Not valid FTP url: "+ftpUrlString);
			ex.printStackTrace();
		} 
		catch (IOException ex) 
		{
			System.out.println("IO error while opening: "+ex);
			ex.printStackTrace();
		}
	}
}
