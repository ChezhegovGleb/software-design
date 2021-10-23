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

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class QueryServletTest {
    private HttpServletRequest request;
    private HttpServletResponse response;
    private QueryServlet servlet;
    private StringWriter stringWriter;

    @Before
    public void setUp() {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        servlet = new QueryServlet();
        stringWriter = new StringWriter();
        DatabaseSimulator.create();
    }

    @After
    public void cleanResource() throws SQLException {
        DatabaseSimulator.clear();
    }

    @Test
    public void doGetSumSimpleTest() throws Exception {
        DatabaseSimulator.add("iphone 7", "10000");
        when(request.getParameter("command")).thenReturn("sum");
        when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));

        servlet.doGet(request, response);

        String expected = "<html><body>\n" +
                "Summary price: \n" +
                "10000\n" +
                "</body></html>\n";

        Assert.assertEquals(expected, stringWriter.toString());

        verify(response).setContentType("text/html");
        verify(response).setStatus(HttpServletResponse.SC_OK);
    }

    @Test
    public void doGetSumMediumTest() throws Exception {
        DatabaseSimulator.add("iphone 7", "10000");
        DatabaseSimulator.add("samsung", "5000");

        when(request.getParameter("command")).thenReturn("sum");
        when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));

        servlet.doGet(request, response);

        String expected = "<html><body>\n" +
                "Summary price: \n" +
                "15000\n" +
                "</body></html>\n";

        Assert.assertEquals(expected, stringWriter.toString());

        verify(response).setContentType("text/html");
        verify(response).setStatus(HttpServletResponse.SC_OK);
        verify(response, times(4)).getWriter();
    }

    @Test
    public void doGetSumEmptyTest() throws Exception {
        when(request.getParameter("command")).thenReturn("sum");
        when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));

        servlet.doGet(request, response);

        String expected = "<html><body>\n" +
                "Summary price: \n" +
                "0\n" +
                "</body></html>\n";

        Assert.assertEquals(expected, stringWriter.toString());

        verify(response).setContentType("text/html");
        verify(response).setStatus(HttpServletResponse.SC_OK);
    }

    @Test
    public void doGetCountSimpleTest() throws Exception {
        DatabaseSimulator.add("iphone 7", "10000");
        when(request.getParameter("command")).thenReturn("count");
        when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));

        servlet.doGet(request, response);

        String expected = "<html><body>\n" +
                "Number of products: \n" +
                "1\n" +
                "</body></html>\n";

        Assert.assertEquals(expected, stringWriter.toString());

        verify(response).setContentType("text/html");
        verify(response).setStatus(HttpServletResponse.SC_OK);
    }

    @Test
    public void doGetCountMediumTest() throws Exception {
        DatabaseSimulator.add("iphone 7", "10000");
        DatabaseSimulator.add("samsung", "5000");

        when(request.getParameter("command")).thenReturn("count");
        when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));

        servlet.doGet(request, response);

        String expected = "<html><body>\n" +
                "Number of products: \n" +
                "2\n" +
                "</body></html>\n";

        Assert.assertEquals(expected, stringWriter.toString());

        verify(response).setContentType("text/html");
        verify(response).setStatus(HttpServletResponse.SC_OK);
        verify(response, times(4)).getWriter();
    }

    @Test
    public void doGetCountEmptyTest() throws Exception {
        when(request.getParameter("command")).thenReturn("count");
        when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));

        servlet.doGet(request, response);

        String expected = "<html><body>\n" +
                "Number of products: \n" +
                "0\n" +
                "</body></html>\n";

        Assert.assertEquals(expected, stringWriter.toString());

        verify(response).setContentType("text/html");
        verify(response).setStatus(HttpServletResponse.SC_OK);
    }

    @Test
    public void doGetMinSimpleTest() throws Exception {
        DatabaseSimulator.add("iphone 7", "10000");
        when(request.getParameter("command")).thenReturn("min");
        when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));

        servlet.doGet(request, response);

        String expected = "<html><body>\n" +
                "<h1>Product with min price: </h1>\n" +
                "iphone 7\t10000</br>\n" +
                "</body></html>\n";

        Assert.assertEquals(expected, stringWriter.toString());

        verify(response).setContentType("text/html");
        verify(response).setStatus(HttpServletResponse.SC_OK);
    }

    @Test
    public void doGetMinMediumTest() throws Exception {
        DatabaseSimulator.add("iphone 7", "10000");
        DatabaseSimulator.add("samsung", "5000");

        when(request.getParameter("command")).thenReturn("min");
        when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));

        servlet.doGet(request, response);

        String expected = "<html><body>\n" +
                "<h1>Product with min price: </h1>\n" +
                "samsung\t5000</br>\n" +
                "</body></html>\n";

        Assert.assertEquals(expected, stringWriter.toString());

        verify(response).setContentType("text/html");
        verify(response).setStatus(HttpServletResponse.SC_OK);
        verify(response, times(4)).getWriter();
    }

    @Test
    public void doGetMinEmptyTest() throws Exception {
        when(request.getParameter("command")).thenReturn("min");
        when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));

        servlet.doGet(request, response);

        String expected = "<html><body>\n" +
                "<h1>Product with min price: </h1>\n" +
                "</body></html>\n";

        Assert.assertEquals(expected, stringWriter.toString());

        verify(response).setContentType("text/html");
        verify(response).setStatus(HttpServletResponse.SC_OK);
    }

    @Test
    public void doGetMaxSimpleTest() throws Exception {
        DatabaseSimulator.add("iphone 7", "10000");
        when(request.getParameter("command")).thenReturn("max");
        when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));

        servlet.doGet(request, response);

        String expected = "<html><body>\n" +
                "<h1>Product with max price: </h1>\n" +
                "iphone 7\t10000</br>\n" +
                "</body></html>\n";

        Assert.assertEquals(expected, stringWriter.toString());

        verify(response).setContentType("text/html");
        verify(response).setStatus(HttpServletResponse.SC_OK);
    }

    @Test
    public void doGetMaxMediumTest() throws Exception {
        DatabaseSimulator.add("iphone 7", "10000");
        DatabaseSimulator.add("samsung", "5000");

        when(request.getParameter("command")).thenReturn("max");
        when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));

        servlet.doGet(request, response);

        String expected = "<html><body>\n" +
                "<h1>Product with max price: </h1>\n" +
                "iphone 7\t10000</br>\n" +
                "</body></html>\n";

        Assert.assertEquals(expected, stringWriter.toString());

        verify(response).setContentType("text/html");
        verify(response).setStatus(HttpServletResponse.SC_OK);
        verify(response, times(4)).getWriter();
    }

    @Test
    public void doGetMaxEmptyTest() throws Exception {
        when(request.getParameter("command")).thenReturn("max");
        when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));

        servlet.doGet(request, response);

        String expected = "<html><body>\n" +
                "<h1>Product with max price: </h1>\n" +
                "</body></html>\n";

        Assert.assertEquals(expected, stringWriter.toString());

        verify(response).setContentType("text/html");
        verify(response).setStatus(HttpServletResponse.SC_OK);
    }

    @Test(expected = RuntimeException.class)
    public void doGetWithNullTest() throws Exception {
        servlet.doGet(null, null);
    }
}