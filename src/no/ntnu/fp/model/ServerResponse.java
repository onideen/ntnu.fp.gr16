package no.ntnu.fp.model;

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
		success = XmlSerializer.readString(e, "success") == "true";
		returnData = e.getFirstChildElement("returnData");
	}
	
	public final boolean isSuccess() {
		return success;
	}

	public final Element getReturnData() {
		return returnData;
	}	
}
