package cody.soap.demo;  
  
import java.io.File;
import java.io.FileOutputStream;  
import java.io.IOException;  
import java.io.OutputStream;  
import java.net.URL;  
  

import javax.activation.DataHandler;  
import javax.servlet.ServletException;  
import javax.servlet.http.HttpServlet;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;  
import javax.xml.soap.AttachmentPart;  
import javax.xml.soap.MessageFactory;  
import javax.xml.soap.SOAPBody;  
import javax.xml.soap.SOAPConnection;  
import javax.xml.soap.SOAPConnectionFactory;  
import javax.xml.soap.SOAPEnvelope;  
import javax.xml.soap.SOAPException;  
import javax.xml.soap.SOAPHeader;  
import javax.xml.soap.SOAPMessage;  
import javax.xml.soap.SOAPPart;  
  
public class DemoSend extends HttpServlet {  
  
    /** 
     *  
     */  
    private static final long serialVersionUID = 1L;  
  
    private SOAPConnection connection;  
    @Override  
    public void init() throws ServletException {  
        super.init();  
        try {  
            SOAPConnectionFactory connectionFactory = SOAPConnectionFactory.newInstance();  
            connection = connectionFactory.createConnection();  
        } catch (UnsupportedOperationException e) {  
            e.printStackTrace();  
        } catch (SOAPException e) {  
            e.printStackTrace();  
        }  
    }  
  
    @Override  
    protected void doGet(HttpServletRequest request, HttpServletResponse response)  
            throws ServletException, IOException {  
        String outString ="<HTML><H1>Sending and reading the SOAP Message</H1><P>";  
        try {  
            MessageFactory messageFactory = MessageFactory.newInstance();  
            SOAPMessage outgoingMessage = messageFactory.createMessage();  
            SOAPPart soappart = outgoingMessage.getSOAPPart();  
            SOAPEnvelope envelope = soappart.getEnvelope();  
            SOAPHeader header = envelope.getHeader();  
            SOAPBody body = envelope.getBody();  
              
            body.addBodyElement(envelope.createName("numberAvailable", "laptops", "http://ecodl.taobao.com/")).addTextNode("216");  
              
            StringBuffer serverUrl = new StringBuffer();  
            serverUrl.append(request.getScheme()).append("://").append(request.getServerName());  
            serverUrl.append(":").append(request.getServerPort()).append(request.getContextPath());  
            String baseUrl = serverUrl.toString();  
            URL url = new URL(baseUrl + "/test.html");  
              
            AttachmentPart attachmentpart = outgoingMessage.createAttachmentPart(new DataHandler(url));  
            attachmentpart.setContentType("text/html");  
            outgoingMessage.addAttachmentPart(attachmentpart);  
              
            URL client = new URL(baseUrl + "/DemoReceiver");  
            
              
            FileOutputStream outgoingFile = new FileOutputStream("out.msg"); 
            
            outgoingMessage.writeTo(outgoingFile);  
            outgoingFile.close();  
              
            outString += "SOAP outgoingMessage sent (see out.msg). <BR>"+baseUrl+"<BR>";  
              
            SOAPMessage incomingMessage = connection.call(outgoingMessage, client);  
              
            if (incomingMessage != null) {  
                FileOutputStream incomingFile = new FileOutputStream("in.msg");  
                incomingMessage.writeTo(incomingFile);  
                incomingFile.close();  
                outString += "SOAP outgoingMessage received (see in.msg).</HTML>";  
            }  
        } catch (SOAPException e) {  
            e.printStackTrace();  
        }  
          
        try {  
            OutputStream outputStream = response.getOutputStream();  
            outputStream.write(outString.getBytes());  
            outputStream.flush();  
            outputStream.close();  
        } catch (IOException e) {}  
    }  
  
    @Override  
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)  
            throws ServletException, IOException {  
        doGet(req, resp);  
    }  
  
      
}  