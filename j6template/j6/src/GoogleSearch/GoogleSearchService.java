/**
 * GoogleSearchService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package GoogleSearch;

public interface GoogleSearchService extends javax.xml.rpc.Service {
	public java.lang.String getGoogleSearchPortAddress();

	public GoogleSearch.GoogleSearchPort getGoogleSearchPort() throws javax.xml.rpc.ServiceException;

	public GoogleSearch.GoogleSearchPort getGoogleSearchPort(java.net.URL portAddress)
			throws javax.xml.rpc.ServiceException;
}
