package no.ntnu.fp.model;

import java.text.ParseException;
import java.util.ArrayList;

import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;
import nu.xom.ParsingException;

public class ServerResponse {

	boolean success;
	Element returnData;
	
	/**
	 * Creates a server response with a success and return data.
	 * @param e
	 */
	public ServerResponse(Element e)
	{
		success = e.getFirstChildElement("success").getValue().equals("true");
		returnData = e.getFirstChildElement("returnData");
	}
	
	/**
	 * 
	 * @return Returns a boolean indicating whether the operation succeeded. 
	 */
	public final boolean isSuccess() {
		return success;
	}

	/**
	 * 
	 * @return Returns this response's return data.
	 */
	public final Element getReturnData() {
		return returnData;
	}	

	/**
	 * Returns this response's parameters as a list of objects.
	 * @return
	 */
	public Object[] getParameters() {
		
		ArrayList<Object> objects = new ArrayList<Object>();
		
		for (int i = 0; i < returnData.getChildElements().size(); i++) {
			Element e = returnData.getChildElements().get(i);
			
			try {
				objects.add(ServerRequest.createObjectFromElement(e));
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
		}
		
		return objects.toArray();
	}
}
