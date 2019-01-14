package edu.nju.onlineorder.filter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class BufferedResponse extends HttpServletResponseWrapper {
    public static final short OT_NONE = 0, OT_WRITER = 1, OT_OUTPUT_STREAM = 2;
    private short outputType = OT_NONE;
    private PrintWriter writer = null;
    private BufServletOutputStream out = null;
    private String contentType;

    public BufferedResponse(HttpServletResponse response) {
        super(response);
        this.contentType = response.getContentType();
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        if(outputType == OT_WRITER){
            throw new IllegalStateException();
        }else if(outputType == OT_OUTPUT_STREAM){
            return out;
        }else {
            out = new BufServletOutputStream();
            outputType = OT_OUTPUT_STREAM;
            return (ServletOutputStream)out;
        }
    }

    public PrintWriter getWriter() throws IOException {
        System.out.println("getWriter()" + outputType);
        if(outputType == OT_OUTPUT_STREAM){
            throw new IllegalStateException();
        }else if (outputType == OT_WRITER){
            return writer;
        }else{
            writer = new PrintWriter(new OutputStreamWriter(getOutputStream(), getCharacterEncoding()));
            outputType = OT_WRITER;
            System.out.println("getWriter() "+outputType);
            return writer;
        }
    }

    public short getOutputType() {
        return outputType;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
        super.setContentType(contentType);
    }

    public void flushBuffer()throws IOException {
        if (outputType == OT_WRITER) {
            writer.flush();
        } else if (outputType == OT_OUTPUT_STREAM) {
            out.flush();
        }
    }

    public void reset() {
        resetBuffer();
        outputType = OT_NONE;
        super.reset();
    }

    public void resetBuffer() {
        if(null != out) {
            out.reset();
        }
    }

    public byte[] toByteArray() throws IOException {
        flushBuffer();
        if(null != out) {
            return(out.toByteArray());
        } else {
            return null;
        }
    }
}
