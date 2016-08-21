package data;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

/**
 * FTP Connection class. We can connect with FTP servers 
 * and upload files to server with an instance of the class.
 * 
 * @author Istvan
 *
 */
public class FtpConnection
{
	private String user, password, host, path;
	private Integer port = 21;
	private String connectionFile = "ftp_properties.csv";
	
	public FtpConnection() 
	{
		init();
	}
	
	/**
	 * Constructor. Connect server with the data given in the parameter file.
	 * The parameter must be Tab separated CSV file with valid properties
	 * (host, user, password, path, port)
	 * @param String filePath -  Path and file name of the FTP properties file.
	 */
	public FtpConnection(String filePath)
	{
		this.connectionFile = filePath;
		init();
	}
	
	/**
	 * Initialize object with FTP properties
	 * Used in constructors.
	 */
	public void init()
	{
		try 
		{
			HashMap <String, String> ftpprop = FileHandler.readFtpProperties(connectionFile);
		    this.user = ftpprop.get("user");
		    this.password = ftpprop.get("password");
		    this.host = ftpprop.get("host");
		    this.path = ftpprop.get("path");
		    this.port = Integer.parseInt(ftpprop.get("port"));
		} 
		catch (IOException ex) 
		{
			System.err.println("Error while read '"+connectionFile+"' file.");
			ex.printStackTrace();
		} 
	}
	
	/**
	 * Reset the connection with other properties CSV file
	 * @param String filePath
	 */
	public void setConnectionFile(String filePath)
	{
		this.connectionFile = filePath;
		init();
	}
	
	/**
	 * Return the file part of a path or URL
	 * @param String path
	 * @return String file part
	 */
	private static String getFileName(String path)
	{
		String fileName = path;
		int pos = (fileName.lastIndexOf("/") == -1)? 0: fileName.lastIndexOf("/");
		return fileName.substring(pos+1);
	}
	
	/**
	 * Upload the given file to the FTP server
	 * @param String uploadFilePath
	 */
	public void uploadFile(String uploadFilePath)
	{
		String ftpUrlString = String.format("ftp://%s:%s@%s:%d/%s", this.user, 
				this.password, this.host, this.port, this.path);
		ftpUrlString = ftpUrlString + "/" + getFileName(uploadFilePath);
	
		try
		{
			URL ftpUrl = new URL(ftpUrlString);
			URLConnection conn = ftpUrl.openConnection();
			
			//Copy input stream to output stream
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
			System.err.println("IO error while opening: "+ex);
			ex.printStackTrace();
		}
	}
}
