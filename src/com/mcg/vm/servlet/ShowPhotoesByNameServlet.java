package com.mcg.vm.servlet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/*
* ʵ��Ŀ��������ƥ���ֶΣ���ʾ���ػ��߱�����Ŀ¼ָ���ļ����µ����ָ������ͷ��ͼƬ�ļ�
**/



public class ShowPhotoesByNameServlet extends HttpServlet{

	private String bolid;
	private static final long serialVersionUID = 1L;
	@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException,FileNotFoundException{
		// �����ļ�
		bolid =req.getParameter("bolid");
		req.setCharacterEncoding("utf-8");  //���ñ���
        InputStream instream = new ShowPhotoesByNameServlet().getClass().getResourceAsStream("/properties/uploadfile.properties"); 
    	Properties prop = new Properties();
    	prop.load(instream);
		//ע�͵ķ�ʽ�Ǵӱ���ĳ�������ȡͼƬ��jspҳ��չʾ�����á�
    	//String path= prop.getProperty("dir1")+File.separator +prop.getProperty("dir2")+File.separator +prop.getProperty("dir3");
    	String path=req.getSession().getServletContext().getRealPath("")+File.separator+"images";;
	    List<File> mlist = null;
	    List<String> list=new ArrayList<String>();
	    File mFile = new File(path);
		if("".equals(bolid)){
			list.add("timg.jpg");
		}else{
        if (mFile.exists() && mFile.isDirectory()) {
        	mlist= new ArrayList<File>();
            getAllFile(mFile, mlist,bolid);
            // �Ѿ���ȡ������ͼƬ
            if(mlist.isEmpty()){
            	list.add("timg.jpg");
            }else{
            	for (File file2 : mlist) {
                    list.add(file2.getName());
                }
            }
            
        }
		}
        HttpSession session=req.getSession();
        session.setAttribute("mlist",list);
        req.getRequestDispatcher("/showphotos.jsp").forward(req,resp);


	}
	@Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
	
	 private static void getAllFile(File mFile, List<File> mlist,String bolid) {
	        // 1.��ȡ��Ŀ¼
	        File[] files = mFile.listFiles();
	        // 2.�ж�files�Ƿ��ǿյ� ����������
	        
	        if (files != null) {

	            for (File file : files) {
	                if (file.isDirectory()) {
	                    getAllFile(file, mlist,bolid);//���õݹ�ķ�ʽ
	                } else {
	                    // 4. ��ӵ�������ȥ
	                    String fileName = file.getName();
	                    if (fileName.endsWith(".jpg")&&fileName.startsWith(bolid)) {
	                        mlist.add(file);//������⼸��ͼƬ��ʽ����ӽ�ȥ
	                    }
	                }
	            }
	        }
	    }


}
