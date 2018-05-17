package edu.wctc.web.demo.bookwebapp.controller;

import edu.wctc.web.demo.bookwebapp.entity.Author;
import edu.wctc.web.demo.bookwebapp.entity.Book;
import edu.wctc.web.demo.bookwebapp.service.AuthorService;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * The main controller for author-related activities. This servlet is configured
 * in web.xml, so no annotations are used here.
 *
 * @author jlombardo
 */
public class AuthorController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // NO MAGIC NUMBERS!
    private static final String NO_PARAM_ERR_MSG = "No request parameter identified";
    private static final String LIST_PAGE = "/listAuthors.jsp";
    private static final String ADD_EDIT_AUTHORS_PAGE = "/addEditAuthors.jsp";
    private static final String LIST_ACTION = "list";
    private static final String ADD_EDIT_DELETE_ACTION = "addEditDelete";
    private static final String SUBMIT_ACTION = "submit";
    private static final String ADD_EDIT_ACTION = "Add/Edit";
    private static final String ACTION_PARAM = "action";
    private static final String SAVE_ACTION = "Save";
    private static final String CANCEL_ACTION = "Cancel";
    private static final String AJAX_LIST_ACTION = "listAjax";
    private static final String AJAX_FINDBY_ID = "findByIdAjax";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String destination = LIST_PAGE;
        String action = request.getParameter(ACTION_PARAM);
        Author author = null;
        
        /*
            This is how you inject a Spring service object into your servlet. Note
            that the bean name must match the name in your service class.
        */
        ServletContext sctx = getServletContext();
        WebApplicationContext ctx
                = WebApplicationContextUtils.getWebApplicationContext(sctx);
        AuthorService authService = (AuthorService) ctx.getBean("authorService");
         PrintWriter out = response.getWriter();

        try {
            /*
             Determine what action to take based on a passed in QueryString
             Parameter
             */
            switch (action) {
                case AJAX_LIST_ACTION:
                    List<Author> authors = authService.findAll();
                    JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();

                    authors.forEach((authorObj) -> {
                        jsonArrayBuilder.add(
                                Json.createObjectBuilder()
                                .add("authorId", authorObj.getAuthorId())
                                .add("authorName", authorObj.getAuthorName())
                                .add("dateAdded", authorObj.getDateAdded().toString())
                        );
                    });

                    JsonArray authorsJson = jsonArrayBuilder.build();
                    response.setContentType("application/json");
                    out.write(authorsJson.toString());
                    out.flush();
                    return; // must not continue at bottom!
                    
                case AJAX_FINDBY_ID:
                    out = response.getWriter();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader br = request.getReader();
                    try {
                        String line;
                        while ((line = br.readLine()) != null) {
                            sb.append(line).append('\n');
                        }
                    } finally {
                        br.close();
                    }

                    String jsonPayload = sb.toString();
                    JsonReader reader = Json.createReader(new StringReader(jsonPayload));
                    JsonObject authorIdObj = reader.readObject();
                    Author authorObj = authService.findById(authorIdObj.getString("authorId"));
                    JsonObjectBuilder builder = Json.createObjectBuilder()
                            .add("authorId", authorObj.getAuthorId())
                            .add("authorName", authorObj.getAuthorName())
                            .add("dateAdded", authorObj.getDateAdded().toString());

                    JsonObject authorJson = builder.build();
                    response.setContentType("application/json");
                    out.write(authorJson.toString());
                    out.flush();
                    return;

                case LIST_ACTION:
                    this.refreshList(request, authService);
                    destination = LIST_PAGE;
                    break;

                case ADD_EDIT_DELETE_ACTION:
                    String subAction = request.getParameter(SUBMIT_ACTION);

                    if (subAction.equals(ADD_EDIT_ACTION)) {
                        // must be add or edit, go to addEdit page
                        String[] authorIds = request.getParameterValues("authorId");
                        if (authorIds == null) {
                            // must be an add action, nothing to do but
                            // go to edit page
                        } else {
                            // must be an edit action, need to get data
                            // for edit and then forward to edit page
                            
                            // Only process first row selected
                            String authorId = authorIds[0];
                            
                            author = authService.findByIdAndFetchBooksEagerly(authorId);
                            if(author == null) {
                                author = authService.findById(authorId);
                                author.setBookSet(new LinkedHashSet<Book>());
                            }
                            request.setAttribute("author", author);
                        }

                        destination = ADD_EDIT_AUTHORS_PAGE;

                    } else {
                        // must be DELETE
                        // get array based on records checked
                        String[] authorIds = request.getParameterValues("authorId");
                        for (String id : authorIds) {
                            author = authService.findByIdAndFetchBooksEagerly(id);
                            if(author == null) {
                                author = authService.findById(id);
                                author.setBookSet(new LinkedHashSet<Book>());
                            }
                            authService.remove(author);
                        }

                        this.refreshList(request, authService);
                        destination = LIST_PAGE;
                    }
                    break;
                    
                case SAVE_ACTION:
                    String authorName = request.getParameter("authorName");
                    String authorId = request.getParameter("authorId");
                    if(authorId == null) {
                        // it must be new
                        author = new Author(0);
                        author.setAuthorName(authorName);
                        author.setDateAdded(new Date());
                    } else {
                        // it must be an update
                        author = authService.findByIdAndFetchBooksEagerly(authorId);
                        if(author == null) {
                                author = authService.findById(authorId);
                                author.setBookSet(new LinkedHashSet<Book>());
                            }
                        author.setAuthorName(authorName);
                    }
                    
                    authService.edit(author);
                    this.refreshList(request, authService);
                    destination = LIST_PAGE;
                    break;
                    
                case CANCEL_ACTION:
                    this.refreshList(request, authService);
                    destination = LIST_PAGE;
                    break;

                default:
                    // no param identified in request, must be an error
                    request.setAttribute("errMsg", NO_PARAM_ERR_MSG);
                    destination = LIST_PAGE;
                    break;
            }

        } catch (Exception e) {
            request.setAttribute("errMsg", e.getCause().getMessage());
        }

        // Forward to destination page
        RequestDispatcher dispatcher
                = getServletContext().getRequestDispatcher(destination);
        dispatcher.forward(request, response);
        

    }

    // Avoid D-R-Y
    private void refreshList(HttpServletRequest request, AuthorService authService) throws Exception {
        List<Author> authors = authService.findAll();
        request.setAttribute("authors", authors);
    }

    /**
     * Called after the constructor is called by the container. This is the
     * correct place to do one-time initialization.
     *
     * @throws ServletException
     */
    @Override
    public void init() throws ServletException {
        // Get init params from web.xml


        // You can't do the Java Reflection stuff here because exceptions
        // are thrown that can't be handled by this stock init() method
        // because the method signature can't be modified -- remember, you 
        // are overriding the method.
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
