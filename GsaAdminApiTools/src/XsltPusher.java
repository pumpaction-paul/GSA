import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringEscapeUtils;

import com.google.enterprise.apis.client.GsaClient;
import com.google.enterprise.apis.client.GsaEntry;
import com.google.gdata.util.ServiceException;

public class XsltPusher {
	
	 public static void main(String[] args){
		 
		 String XLST_FILE_PATH = "frontends/frontend.xslt";
		 String FRONTEND_NAME = "frontend";
		 
		 try {
			GsaClient myClient = GsaConn.getInstance().getMyClient();
			System.out.println("Updating frontend: " + FRONTEND_NAME + " on GSA: " + GsaConn.getInstance().getGsaAddress());
			XsltPusher.pushXslt(FRONTEND_NAME,XLST_FILE_PATH, myClient); 			
			System.out.println("Successfully updated frontend: " + FRONTEND_NAME);
		 } catch (Exception e) {
			e.printStackTrace();
		}
		
	 }
	
	 
	/**
	 * Read XSLT file in and push it to the GSA
	 *  
	 * @param frontendName
	 * @param xsltFilePath
	 * @param myClient
	 * @throws MalformedURLException
	 * @throws ServiceException
	 * @throws IOException
	 */
	public static void pushXslt(String frontendName, String xsltFilePath, GsaClient myClient) throws MalformedURLException, ServiceException, IOException{
		
		String str = FileUtils.readFileToString(new File(xsltFilePath));
		str = StringEscapeUtils.escapeHtml3(str);
		
		// Create an entry to hold properties to update
		GsaEntry updateEntry = new GsaEntry();
		updateEntry.setId(frontendName);
		 	
		// Add this line to update the style sheet content
		updateEntry.addGsaContent("styleSheetContent", str);

		// Send the request and print the response
		myClient.updateEntry("outputFormat", frontendName, updateEntry);
	}
	
	
}
