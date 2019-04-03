package cn.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

import cn.web.dao.CommentDao;
import cn.web.dao.NewsDao;
import cn.web.dao.TopicDao;
import cn.web.entity.Comment;
import cn.web.entity.News;
import cn.web.entity.NewsDetail;
import cn.web.entity.Result;
import cn.web.entity.Topic;

/**
 * 新闻内容的控制器
 */
public class NewsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final int PAGE_SIZE = 15;
	private static  int tid=-1; 
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");//post请求的乱码处理
		//相当于请求的入口参数
		String action = request.getParameter("action");
		String top = request.getParameter("top");
		if ("listNews".equals(action)) {
			doQueryNews(request, response);
			
		} else if ("detail".equals(action)) {
			doNewsDetail(request,response);
		}else if("doComment".equals(action)) {
			doComment(request,response);
		}else if("listNews".equals(action)&&"false".equals(top)) {
			topNews(request, response);
		}else if("doAjaxComment".equals(action)){
			doAjaxComment(request,response);
		}
		// 获取页码，默认是第1页

	}

	private void doAjaxComment(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter out =  response.getWriter();
		int cnid = Integer.parseInt(request.getParameter("cnid"));
		String cauthor = request.getParameter("cauthor");
		String cip = request.getParameter("cip");
		String ccontent = request.getParameter("ccontent");
		
		Result result = new Result();
		CommentDao commentDao = new CommentDao();
		Comment comment = new Comment(cnid, cauthor, cip, ccontent);
		
		int row = commentDao.addComment(comment); 
		result.setMsg(row+"");
		
		//查询最新的 评论列表
		List<Comment> list = commentDao.findCommentsByNews(cnid);
		result.setDatas(list);
//		String json = JSON.toJSONString(list);
//		System.out.println(json);
		String jsonObject = JSON.toJSONString(result);
		out.print(jsonObject);
		out.flush();
		out.close();
		
		//查询最新的评论列表
	}

	private void topNews(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		NewsDao newsDao = new NewsDao();
		TopicDao topicDao = new TopicDao();
		List<Topic> topiclist = topicDao.findAllTopic();
	
		
		request.getRequestDispatcher("/index.jsp").forward(request, response);
	}

	private void doComment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int cnid = Integer.parseInt(request.getParameter("cnid"));
		String cauthor = request.getParameter("cauthor");
		String cip = request.getParameter("cip");
		String ccontent = request.getParameter("ccontent");
		
		CommentDao commentDao = new CommentDao();
		Comment comment = new Comment(cnid, cauthor, cip, ccontent);
		commentDao.addComment(comment); 
		
		request.getRequestDispatcher("NewsServlet?action=detail&nid="+cnid).forward(request, response);
	}

	private void doNewsDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int  nid = Integer.parseInt(request.getParameter("nid"));
		NewsDao newsDao = new NewsDao();
		CommentDao commentDao = new CommentDao();
		List<News> allNewslsit = newsDao.findAllNews();
		NewsDetail newsDetail = newsDao.findNewsById(nid);
		List<Comment> commentlist = commentDao.findCommentsByNews(nid);
		String ip = request.getRemoteAddr();
		request.setAttribute("allNewslsit", allNewslsit);
		request.setAttribute("news", newsDetail);
		request.setAttribute("commentlist", commentlist);
		request.setAttribute("cip", ip);
		request.getRequestDispatcher("/news_ajax_read.jsp").forward(request, response);
		
	}

	private void doQueryNews(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int allCounts = 0;
		int pageNo = 1;
		if (request.getParameter("pageNo") != null) {
			pageNo = Integer.parseInt(request.getParameter("pageNo"));
		}

		NewsDao newsDao = new NewsDao();
		/**
		 * 判断执行全部还是分类新闻
		 */
		List<News> newslist = null;
//		int tid=-1;
		String var=null;
		if(request.getParameter("tid")!=null) {
			tid = Integer.parseInt(request.getParameter("tid"));
		}
		if(request.getParameter("var")!=null) {
			var = request.getParameter("var");
		}
		if("true".equals(var)&&tid!=-1) {
			newslist = newsDao.findNews(tid);
			allCounts = newslist.size();
		}else {
			tid=-1;
			newslist = newsDao.findNewsByPage(pageNo, PAGE_SIZE);
			allCounts = newsDao.getAllCounts();
		}
		
		// 计算总页数，先查询总记录数
		 
		int allPages = (allCounts % PAGE_SIZE == 0) ? (allCounts / PAGE_SIZE) : (allCounts / PAGE_SIZE + 1);
		// 计算上一页和下一页 prev next
		int prev = pageNo;
		int next = pageNo;

		// 判断页数边界
		// 判断是否有上下页和首页
				if (allPages <= 0) {// 当文本大小为0时，没有页数: pageNo=0;
					prev = 0;
					next = 0;
					pageNo = 0;
				} else if (allPages == 1) { // allPage==1时pageNo也等于1
					prev = 1;
					next = 1;

				} else if (allPages > 1 && pageNo >= 1 && pageNo < allPages) {
					if (pageNo == 1) {
						prev = 1;
					} else {
						prev--;
					}
					next++;
				} else if (pageNo >= allPages) {
					prev--;
					next = allPages;
				}


		
	
		
		TopicDao topicDao = new TopicDao();
		List<Topic> topiclist = topicDao.findAllTopic();
		List<News> allNewslsit = newsDao.findAllNews();
		request.setAttribute("topicList", topiclist);
		request.setAttribute("newslist", newslist);
		request.setAttribute("allPages", allPages);
		request.setAttribute("allCounts", allCounts);
		request.setAttribute("prev", prev);
		request.setAttribute("next", next);
		request.setAttribute("pageNo", pageNo);
		request.setAttribute("allNewslsit", allNewslsit);
		/*
		 * 转发不能少后面的forward 转发的路径中，建议加上‘/’，表示WebRoot根目录
		 */
		request.getRequestDispatcher("/index.jsp").forward(request, response);
		;
		/**
		 * 
		 * 重定向路径中'/'，表示整个工程的根目录
		 * reponse.sendRedriect(request.getContextPath()+"/index.jsp");防止重定向找不到页面
		 */

	}

}
