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
	
	public ServerResponse(Element e)
	{
		success = e.getFirstChildElement("success").getValue().equals("true");
		returnData = e.getFirstChildElement("returnData");
	}
	
	public final boolean isSuccess() {
		return success;
	}

	public final Element getReturnData() {
		return returnData;
	}	

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
