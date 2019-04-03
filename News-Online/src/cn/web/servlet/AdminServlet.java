package cn.web.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.alibaba.fastjson.JSON;

import cn.web.Utils.PageBean;
import cn.web.dao.CommentDao;
import cn.web.dao.NewsDao;
import cn.web.dao.TopicDao;
import cn.web.entity.Comment;
import cn.web.entity.News;
import cn.web.entity.NewsDetail;
import cn.web.entity.Result;
import cn.web.entity.Topic;

/**
 * Servlet implementation class AdminServlet
 */
public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		String action = request.getParameter("action");
		if("adminIndex".equals(action)) {//对主题和新闻进行分类分页
			doAdminQuery(request,response);
		}else if("toNewsAdd".equals(action)) {//查询所有新闻主题在管理员首页
			toNewsAdd(request,response);
		}else if("doNewsAdd".equals(action)) {//添加新闻
			doNewsAdd(request,response);
		}else if("doNewsDelete".equals(action)) {//删除新闻
			doNewsDelete(request,response);
		}else if("addTopic".equals(action)) {//添加新闻主题
			addTopic(request,response);
		}else if("editTopic".equals(action)) {//查询所有主题遍历到主题页面
			editTopic(request,response);
		}else if("delTopic".equals(action)) {//删除新闻主题
			delTopic(request,response);
		}else if("updTopic".equals(action)) {//将新闻ID和主题ID传入修改页面
			updTopic(request,response);
		}else if("toNewsUpdate".equals(action)) {//修改新闻
			toNewsUpdate(request,response);
		}else if("doUpdateSubmit".equals(action)) {//提交新闻修改
			doUpdateSubmit(request,response);
		}else if("doDelComment".equals(action)) {//删除评论内容
			doDelComment(request,response);
		}else if("doupdTopic".equals(action)) {//修改新闻主题
			doupdTopic(request,response);
		}
		
		
	}

	private void doupdTopic(HttpServletRequest request, HttpServletResponse response) throws IOException {
			TopicDao topicDao = new TopicDao();
			String [] topic = request.getParameterValues("topic");
			Topic topics = new Topic(Integer.parseInt(topic[0]),topic[1]);
			int row = topicDao.updateTopic(topics);
			response.sendRedirect(request.getContextPath()+"/AdminServlet?action=editTopic");
			
	}

	private void doDelComment(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int cid = Integer.parseInt(request.getParameter("cid"));
		int nid = Integer.parseInt(request.getParameter("nid"));
		
		CommentDao commentDao = new CommentDao();
		int row = commentDao.deleteComment(cid);
		
		List<Comment> list = commentDao.findCommentsByNews(nid);
		
		Result result = new Result();
		result.setMsg(row+"");
		result.setDatas(list);
		
		String json = JSON.toJSONString(result);
		System.out.println(json);
		PrintWriter out = response.getWriter();
		out.print(json);
		out.flush();
		out.close();
		
	}

	private void doUpdateSubmit(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			DiskFileItemFactory factory  = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			//设置文件上传的参数
			upload.setHeaderEncoding("UTF-8");
			//设置上传文件的最大容量
			upload.setFileSizeMax(1024*1024*3);
			List list = upload.parseRequest(request);
			News news = new News();
			 Iterator it = list.iterator();
			while(it.hasNext()) {
				FileItem item = (FileItem) it.next();//表示表单中的每一个元素的对象（文本或文化）
				
				if(item.isFormField()) {//判断当前fileItem的类别：1.form的普通输入框 2.file文件域
					String value = item.getString("UTF-8");
					
					if(item.getFieldName().equals("ntid")) {
						news.setNtid(Integer.parseInt(value));
					}else if(item.getFieldName().equals("nid")) {
						news.setNid(Integer.parseInt(value));
					}else if(item.getFieldName().equals("npicpath")) {
						news.setNpicpath(value);
					}else if(item.getFieldName().equals("ntitle")) {
						news.setNtitle(value);
					}else if(item.getFieldName().equals("nauthor")) {
						news.setNauthor(value);
					}else if(item.getFieldName().equals("nsummary")) {
						news.setNsummary(value);
					}else if(item.getFieldName().equals("ncontent")) {
						news.setNcontent(value);
					}
				}else {
					//判断是否有文件重新上传
					if (!"".equals(item.getName())) {
						// 获取upload在tomcat下的绝对路径
						String uploadfilePath = request.getServletContext().getRealPath("upload");
						// 获取原来图片的文件路径
						File oldFile = new File(uploadfilePath, news.getNpicpath());
						if (news.getNpicpath() != null && !"".equals(news.getNpicpath())) {
							if (oldFile.exists()) {
								oldFile.delete();
							}
						}
						// 获取upload的文件目录下的指定文件名的文件
						File targetFile = new File(uploadfilePath, item.getName());
						item.write(targetFile);
						news.setNpicpath(item.getName());
						System.out.println("上传完成");

					}
				}
				
			}
			//保存数据库
			NewsDao newsDao = new NewsDao();
			newsDao.updateNews(news);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//添加完后回到后台首页
		response.sendRedirect(request.getContextPath()+"/AdminServlet?action=adminIndex");
	}

	private void toNewsUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		TopicDao topicDao = new TopicDao();
		NewsDao newsDao = new NewsDao();
		CommentDao commentDao = new CommentDao();
		int nid = Integer.parseInt(request.getParameter("nid"));
		
		List<Topic> topicLsit = topicDao.findAllTopic();
		NewsDetail newsDetail = newsDao.findNewsById(nid);
		List<Comment> commentList = commentDao.findCommentsByNews(nid);
		
		request.setAttribute("topicList", topicLsit);
		request.setAttribute("news", newsDetail);
		request.setAttribute("commentList", commentList);
		
		request.getRequestDispatcher("/newspages/news_modify.jsp").forward(request, response);
	}

	private void updTopic(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int tid = Integer.parseInt(request.getParameter("tid"));
		String tname = request.getParameter("tname");
		request.setAttribute("tid", tid);
		request.setAttribute("tname", tname);
		request.getRequestDispatcher("/newspages/topic_modify.jsp").forward(request, response);
	}

	private void delTopic(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		TopicDao topicDao = new TopicDao();
		int tid = Integer.parseInt(request.getParameter("tid"));	
		topicDao.deleteTopic(tid);
		request.getRequestDispatcher("/AdminServlet?action=editTopic").forward(request, response);
	}

	private void editTopic(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		TopicDao topicDao = new TopicDao();
		List<Topic> list = topicDao.findAllTopic();
		request.setAttribute("topicList", list);
		request.getRequestDispatcher("/newspages/topic_list.jsp").forward(request, response);
	}

	private void addTopic(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		TopicDao topicDao = new TopicDao();
		Topic topic = new Topic();
		List<Topic> topics = topicDao.findAllTopic();
		int row = 0;
		if( request.getParameterValues("tname")!=null) {
			String[] tname = request.getParameterValues("tname");
			for(int i=0;i<topics.size();i++){
				if(tname[0].equals(topics.get(i).getTname())){
					request.setAttribute("addError", row);
					request.getRequestDispatcher("/newspages/topic_add.jsp").forward(request, response);
					return;
				}
			}

			topic.setTname(tname[0]);
			  row =  topicDao.addTopic(topic);
		}
		
		request.setAttribute("row", row);
		request.getRequestDispatcher("/AdminServlet?action=adminIndex").forward(request, response);
//		response.sendRedirect(request.getContextPath()+"/AdminServlet?action=adminIndex");
	}

	private void doNewsDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		NewsDao newsDao = new NewsDao();
		int nid = Integer.parseInt(request.getParameter("nid"));
		newsDao.deleteNews(nid);
		response.sendRedirect(request.getContextPath()+"/AdminServlet?action=adminIndex");
	}

	private void doNewsAdd(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			DiskFileItemFactory factory  = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			
			//设置文件上传的参数
			upload.setHeaderEncoding("UTF-8");
			//设置上传文件的最大容量
			upload.setFileSizeMax(1024*1024*3);
			
			List list = upload.parseRequest(request);
			News news = new News();
			 Iterator it = list.iterator();
			while(it.hasNext()) {
				FileItem item = (FileItem) it.next();//表示表单中的每一个元素的对象（文本或文化）
				
				if(item.isFormField()) {//判断当前fileItem的类别：1.form的普通输入框 2.file文件域
					String value = item.getString("UTF-8");
					
					if(item.getFieldName().equals("ntid")) {
						news.setNid(Integer.parseInt(value));
					}else if(item.getFieldName().equals("ntitle")) {
						news.setNtitle(value);
					}else if(item.getFieldName().equals("nauthor")) {
						news.setNauthor(value);
					}else if(item.getFieldName().equals("nsummary")) {
						news.setNsummary(value);
					}else if(item.getFieldName().equals("ncontent")) {
						news.setNcontent(value);
					}
				}else {
					if(!"".equals(item.getName())) {
					String path = request.getServletContext().getRealPath("upload");
					System.out.println(path);
					
					File targetFile = new File(path,item.getName());
					item.write(targetFile);
					news.setNpicpath(item.getName());

					System.out.println("上传完成！");
				}}
				
			}
			//保存数据库
			NewsDao newsDao = new NewsDao();
			newsDao.saveNews(news);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//添加完后回到后台首页
		response.sendRedirect(request.getContextPath()+"/AdminServlet?action=adminIndex");
	}

	private void toNewsAdd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Topic> topicList = new TopicDao().findAllTopic();
		request.setAttribute("topicList", topicList);
		request.getRequestDispatcher("/newspages/news_add.jsp").forward(request, response);
	}								

	private void doAdminQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		int pageNo = 1;
//		if(request.getParameter("pageNo")!=null) {
//			pageNo = Integer.parseInt(request.getParameter("pageNo"));
//		}
		PageBean pageBean = new PageBean((request.getParameter("pageNo")==null)?1:Integer.parseInt(request.getParameter("pageNo")));
//		pageBean.setPageSize(14); 如果不写则默认10条记录
		
		TopicDao topicDao = new TopicDao();
	    List<Topic> list = topicDao.findTopicAndNewsList(pageBean.getPageNo(), pageBean.getPageSize());
	    
	    int allCount = topicDao.getTopicCount();//记录左外连接后的总记录数
	    pageBean.setAllCount(allCount);
	    
	    request.setAttribute("topicList", list);
	    request.setAttribute("page", pageBean);
	    
		request.getRequestDispatcher("/newspages/admin.jsp").forward(request, response);
				
	}

}
