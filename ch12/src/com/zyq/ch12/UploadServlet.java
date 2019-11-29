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
		    // ����ContentType�ֶ�ֵ
		    response.setContentType("text/html;charset=utf-8");
			// ����DiskFileItemFactory��������
			DiskFileItemFactory factory = new DiskFileItemFactory();
			//�����ļ�����Ŀ¼�������Ŀ¼���������´���һ��
			File f = new File("C:\\upload");
			if (!f.exists()) {
				f.mkdirs();
			}
			// �����ļ��Ļ���·��
			factory.setRepository(f);
			// ���� ServletFileUpload����
			ServletFileUpload fileupload = new ServletFileUpload(factory);
			//�����ַ�����
			fileupload.setHeaderEncoding("utf-8");
			// ���� request���õ��ϴ��ļ���FileItem����
			List<FileItem> fileitems = fileupload.parseRequest(request);
			//��ȡ�ַ���
			PrintWriter writer = response.getWriter();
			// ��������
			for (FileItem fileitem : fileitems) { 
					// ��ȡ�ϴ����ļ���
					String filename = fileitem.getName();
					//�����ϴ��ļ�
					if(filename != null && !filename.equals("")){
						// ��ȡ���ļ���
					    filename = filename.substring(filename
.lastIndexOf("\\") + 1);								// �ļ�����ҪΨһ
						filename = UUID.randomUUID().toString() + "_" + filename;
						// ָ���ϴ��ļ���·��
						String webPath = "c:\\upload\\";
						//���ļ���·�����ļ�����ϳ�������·��
						String filepath = webPath + filename;
						// �����ļ�
						File file = new File(filepath);
						file.getParentFile().mkdirs();
						file.createNewFile();
						// ����ϴ��ļ���
						InputStream in = fileitem.getInputStream();
						// ʹ��FileOutputStream�򿪷������˵��ϴ��ļ�
						FileOutputStream out = new FileOutputStream(file);
						// ���ĶԿ�
						byte[] buffer = new byte[1024];//ÿ�ζ�ȡ1���ֽ�
						int len;
						//��ʼ��ȡ�ϴ��ļ����ֽڣ����������������˵��ϴ��ļ��������
						while ((len = in.read(buffer)) > 0)
							out.write(buffer, 0, len);
						// �ر���
						in.close();
						out.close();
						// ɾ����ʱ�ļ�
						fileitem.delete();
                         //���ϴ���Ϣ�����������ʾ
						writer.print("�ļ���"+
						       filename.substring(filename
						    		   .lastIndexOf('_')+1)+"--���ϴ��ɹ���<br />");
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

