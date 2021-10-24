package ru.akirakozov.sd.refactoring.servlet;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.mockito.Mockito.*;

public class AddProductServletTest {
    private HttpServletRequest request;
    private HttpServletResponse response;
    private AddProductServlet servlet;
    private StringWriter stringWriter;

    @Before
    public void setUp() {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        servlet = new AddProductServlet();
        stringWriter = new StringWriter();
    }

    @Test
    public void doGetSimpleTest() throws Exception {
        when(request.getParameter("name")).thenReturn("iphone 7");
        when(request.getParameter("price")).thenReturn("10000");
        when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));

        servlet.doGet(request, response);

        Assert.assertEquals("OK" + System.lineSeparator(), stringWriter.toString());

        verify(response).setContentType("text/html");
        verify(response).setStatus(HttpServletResponse.SC_OK);
        verify(response).getWriter();
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

        servlet.doGet(request, response);
        servlet.doGet(request, response);

        Assert.assertEquals("OK" + System.lineSeparator()
                + "OK" + System.lineSeparator(), stringWriter.toString());

        verify(response, times(2)).setContentType("text/html");
        verify(response, times(2)).setStatus(HttpServletResponse.SC_OK);
        verify(response, times(2)).getWriter();
    }

    @Test
    public void doGetWithNullNameTest() throws Exception {
        when(request.getParameter("name")).thenReturn(null);
        when(request.getParameter("price")).thenReturn("10000");
        when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));

        servlet.doGet(request, response);

        Assert.assertEquals("OK" + System.lineSeparator(), stringWriter.toString());

        verify(response).setContentType("text/html");
        verify(response).setStatus(HttpServletResponse.SC_OK);
        verify(response).getWriter();
    }

    @Test(expected = NumberFormatException.class)
    public void doGetWithNullPriceTest() throws Exception {
        when(request.getParameter("name")).thenReturn("iphone 7");
        when(request.getParameter("price")).thenReturn(null);
        when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));

        servlet.doGet(request, response);
    }

    @Test
    public void doGetWithEmptyNameTest() throws Exception {
        when(request.getParameter("name")).thenReturn("");
        when(request.getParameter("price")).thenReturn("10000");
        when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));

        servlet.doGet(request, response);

        Assert.assertEquals("OK" + System.lineSeparator(), stringWriter.toString());

        verify(response).setContentType("text/html");
        verify(response).setStatus(HttpServletResponse.SC_OK);
        verify(response).getWriter();
    }

    @Test(expected = NumberFormatException.class)
    public void doGetWithEmptyPriceTest() throws Exception {
        when(request.getParameter("name")).thenReturn("iphone8");
        when(request.getParameter("price")).thenReturn("");
        when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));

        servlet.doGet(request, response);
    }

    @Test(expected = NullPointerException.class)
    public void doGetWithNullTest() throws Exception {
        servlet.doGet(null, null);
    }

}