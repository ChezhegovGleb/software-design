package ru.akirakozov.sd.refactoring.servlet;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;

import static org.mockito.Mockito.*;

public class GetProductsServletTest {
    private HttpServletRequest request;
    private HttpServletResponse response;
    private GetProductsServlet servlet;
    private StringWriter stringWriter;

    @Before
    public void setUp() {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        servlet = new GetProductsServlet();
        stringWriter = new StringWriter();
        DatabaseSimulator.create();
    }

    @After
    public void cleanResource() throws SQLException {
        DatabaseSimulator.clear();
    }

    @Test
    public void doGetSimpleTest() throws Exception {
        DatabaseSimulator.add("iphone 7", "10000");
        when(request.getParameter("name")).thenReturn("iphone 7");
        when(request.getParameter("price")).thenReturn("10000");
        when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));

        servlet.doGet(request, response);

        String expected = "<html><body>iphone\t7	10000</br> </body></html>".replaceAll("\\s+", "");

        Assert.assertEquals(expected, stringWriter.toString().trim().replaceAll("\\s+", ""));

        verify(response).setContentType("text/html");
        verify(response).setStatus(HttpServletResponse.SC_OK);
    }

    @Test
    public void doGetMediumTest() throws Exception {
        when(request.getParameter("name"))
                .thenReturn("iphone 7")
                .thenReturn("samsung");
        when(request.getParameter("price"))
                .thenReturn("10000")
                .thenReturn("5000");
        when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));

        DatabaseSimulator.add("iphone 7", "10000");
        DatabaseSimulator.add("samsung", "5000");

        servlet.doGet(request, response);

        String expected = ("<html><body>\n" +
                "iphone 7\t10000</br>\n" +
                "samsung\t5000</br>\n" +
                "</body></html>\n").replaceAll("\\s+", "");

        Assert.assertEquals(expected, stringWriter.toString().replaceAll("\\s+", ""));

        verify(response).setContentType("text/html");
        verify(response).setStatus(HttpServletResponse.SC_OK);
        verify(response, times(4)).getWriter();
    }

    @Test
    public void doGetEmptyTableTest() throws Exception {
        when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));

        servlet.doGet(request, response);

        String expected = "<html><body></body></html>".replaceAll("\\s+", "");
        Assert.assertEquals(expected, stringWriter.toString().replaceAll("\\s+", ""));

        verify(response).setContentType("text/html");
        verify(response).setStatus(HttpServletResponse.SC_OK);
        verify(response, times(2)).getWriter();
    }

    @Test(expected = RuntimeException.class)
    public void doGetWithNullTest() throws Exception {
        servlet.doGet(null, null);
    }
}