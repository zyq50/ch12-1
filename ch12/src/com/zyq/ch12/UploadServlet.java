package com.zyq.ch12;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;


@WebServlet("/UploadServlet")
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
		    // 设置ContentType字段值
		    response.setContentType("text/html;charset=utf-8");
			// 创建DiskFileItemFactory工厂对象
			DiskFileItemFactory factory = new DiskFileItemFactory();
			//设置文件缓存目录，如果该目录不存在则新创建一个
			File f = new File("C:\\upload");
			if (!f.exists()) {
				f.mkdirs();
			}
			// 设置文件的缓存路径
			factory.setRepository(f);
			// 创建 ServletFileUpload对象
			ServletFileUpload fileupload = new ServletFileUpload(factory);
			//设置字符编码
			fileupload.setHeaderEncoding("utf-8");
			// 解析 request，得到上传文件的FileItem对象
			List<FileItem> fileitems = fileupload.parseRequest(request);
			//获取字符流
			PrintWriter writer = response.getWriter();
			// 遍历集合
			for (FileItem fileitem : fileitems) { 
					// 获取上传的文件名
					String filename = fileitem.getName();
					//处理上传文件
					if(filename != null && !filename.equals("")){
						// 截取出文件名
					    filename = filename.substring(filename
.lastIndexOf("\\") + 1);								// 文件名需要唯一
						filename = UUID.randomUUID().toString() + "_" + filename;
						// 指定上传文件夹路径
						String webPath = "c:\\upload\\";
						//将文件夹路径与文件名组合成完整的路径
						String filepath = webPath + filename;
						// 创建文件
						File file = new File(filepath);
						file.getParentFile().mkdirs();
						file.createNewFile();
						// 获得上传文件流
						InputStream in = fileitem.getInputStream();
						// 使用FileOutputStream打开服务器端的上传文件
						FileOutputStream out = new FileOutputStream(file);
						// 流的对拷
						byte[] buffer = new byte[1024];//每次读取1个字节
						int len;
						//开始读取上传文件的字节，并将其输出到服务端的上传文件输出流中
						while ((len = in.read(buffer)) > 0)
							out.write(buffer, 0, len);
						// 关闭流
						in.close();
						out.close();
						// 删除临时文件
						fileitem.delete();
                         //将上传信息在浏览器中显示
						writer.print("文件："+
						       filename.substring(filename
						    		   .lastIndexOf('_')+1)+"--》上传成功！<br />");
					}										
				
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}

