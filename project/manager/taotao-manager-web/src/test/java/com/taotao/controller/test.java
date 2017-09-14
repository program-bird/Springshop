package com.taotao.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.junit.Test;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
try {
	FTPClient ftpClient = new FTPClient();
			
			//创建ftp连接。默认是21端口
			ftpClient.connect("192.168.0.100", 21);
			//登录ftp服务器，使用用户名和密码
			ftpClient.login("liuch", "15302007287");
			
			//上传文件。
			//读取本地文件
			FileInputStream inputStream = new FileInputStream(new File("/home/open/下载/imagins/321.jpg"));
			//设置上传的路径
			ftpClient.changeWorkingDirectory("/home/ftpuser/www/imagins");
			//修改上传文件的格式
			
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			//第一个参数：服务器端文档名
			//第二个参数：上传文档的inputStream
			ftpClient.storeFile("hello1.jpg", inputStream);
			
			//关闭连接
			ftpClient.logout();
} catch (SocketException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
} catch (FileNotFoundException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
} catch (IOException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}

	}

}
