package cody.soap.demo;

import java.util.Iterator;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.xml.messaging.JAXMServlet; 
import javax.xml.messaging.ReqRespListener;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

public class DemoReceiver extends JAXMServlet implements ReqRespListener{

	/**
	 * Generated automatically
	 */
	private static final long serialVersionUID = 1L;
	private static MessageFactory messageFactory = null;
	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		super.init(servletConfig);
		try {
			messageFactory = MessageFactory.newInstance();
		} catch (SOAPException e) {
			e.printStackTrace();
		}
	}

	@Override
	public SOAPMessage onMessage(SOAPMessage msg) {
		SOAPPart soappart = msg.getSOAPPart();
        try {
        	SOAPEnvelope incomingEnvelope;		
			incomingEnvelope = soappart.getEnvelope();		
	        SOAPBody body = incomingEnvelope.getBody();
	
	        Iterator<?> iterator = body.getChildElements(incomingEnvelope.createName("numberAvailable", "laptops", "http://ecodl.taobao.com/"));
	
	        SOAPElement element;
	        element = (SOAPElement) iterator.next();
	
	        SOAPMessage message = messageFactory.createMessage();
	        SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
	
	        SOAPBody responsebody = envelope.getBody();
	        String responseText = "Got the SOAP message indicating there are " + element.getValue() + " laptops available.";
	        responsebody.addChildElement(envelope.createName("Response")).addTextNode(responseText);
	
	        return message;
        } catch (SOAPException e) {
			e.printStackTrace();
			return null;
		}
	}

}
