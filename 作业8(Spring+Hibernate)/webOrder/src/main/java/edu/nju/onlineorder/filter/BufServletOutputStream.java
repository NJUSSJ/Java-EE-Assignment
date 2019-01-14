package edu.nju.onlineorder.filter;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class BufServletOutputStream extends ServletOutputStream {
    ByteArrayOutputStream bufferedOut;

    public BufServletOutputStream() {
        bufferedOut = new ByteArrayOutputStream();
    }
    @Override
    public void write(int b) throws IOException {
        bufferedOut.write(b);
    }
    public byte[] toByteArray(){
        return bufferedOut.toByteArray();
    }

    public void reset(){
        bufferedOut.reset();
    }

    public boolean isReady(){
        return false;
    }

    @Override
    public void setWriteListener(WriteListener writeListener) {

    }

    public void setWriterLisenter(WriteListener arg0){

    }
}
