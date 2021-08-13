package redirector;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RedirectServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter pw = resp.getWriter();
        resp.setContentType("text/html");
        String method = req.getMethod();
        String originalUri = req.getRequestURI();
        pw.println("<html><body onload=\"document.forms[0].submit();\">");
        pw.println("<form method=\"" + method + "\" action=\"/help" + originalUri + "\">");
        for (Iterator<Map.Entry<String, String[]>> it = req.getParameterMap().entrySet().iterator(); it.hasNext();) {
            Map.Entry<String, String[]> entry = it.next();
            String name = entry.getKey();
            String[] values = entry.getValue();
            String value = "";
            if (values != null) {
                for (int i = 0; i < values.length; i++) {
                    value += (i > 0 ? "," : "") + values[i];
                }
            }
            pw.println("<input type=\"hidden\" name=\"" + name + "\" value=\"" + value +"\" />");
        }
        pw.println("</form></body></html>");
        pw.flush();
    }
}