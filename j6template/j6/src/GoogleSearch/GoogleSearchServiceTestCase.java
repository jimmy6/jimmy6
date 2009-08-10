/**
 * GoogleSearchServiceTestCase.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package GoogleSearch;

public class GoogleSearchServiceTestCase extends junit.framework.TestCase {
	public GoogleSearchServiceTestCase(java.lang.String name) {
		super(name);
	}

	public void testGoogleSearchPortWSDL() throws Exception {
		javax.xml.rpc.ServiceFactory serviceFactory = javax.xml.rpc.ServiceFactory.newInstance();
		java.net.URL url = new java.net.URL(new GoogleSearch.GoogleSearchServiceLocator().getGoogleSearchPortAddress()
				+ "?WSDL");
		javax.xml.rpc.Service service = serviceFactory.createService(url, new GoogleSearch.GoogleSearchServiceLocator()
				.getServiceName());
		assertTrue(service != null);
	}

	public void test1GoogleSearchPortDoGetCachedPage() throws Exception {
		GoogleSearch.GoogleSearchBindingStub binding;
		try {
			binding = (GoogleSearch.GoogleSearchBindingStub) new GoogleSearch.GoogleSearchServiceLocator()
					.getGoogleSearchPort();
		} catch (javax.xml.rpc.ServiceException jre) {
			if (jre.getLinkedCause() != null)
				jre.getLinkedCause().printStackTrace();
			throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
		}
		assertNotNull("binding is null", binding);

		// Time out after a minute
		binding.setTimeout(60000);

		// Test operation
		byte[] value = null;
		value = binding.doGetCachedPage(new java.lang.String(), new java.lang.String());
		// TBD - validate results
	}

	public void test2GoogleSearchPortDoSpellingSuggestion() throws Exception {
		GoogleSearch.GoogleSearchBindingStub binding;
		try {
			binding = (GoogleSearch.GoogleSearchBindingStub) new GoogleSearch.GoogleSearchServiceLocator()
					.getGoogleSearchPort();
		} catch (javax.xml.rpc.ServiceException jre) {
			if (jre.getLinkedCause() != null)
				jre.getLinkedCause().printStackTrace();
			throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
		}
		assertNotNull("binding is null", binding);

		// Time out after a minute
		binding.setTimeout(60000);

		// Test operation
		java.lang.String value = null;
		value = binding.doSpellingSuggestion(new java.lang.String(), new java.lang.String());
		// TBD - validate results
	}

	public void test3GoogleSearchPortDoGoogleSearch() throws Exception {
		GoogleSearch.GoogleSearchBindingStub binding;
		try {
			binding = (GoogleSearch.GoogleSearchBindingStub) new GoogleSearch.GoogleSearchServiceLocator()
					.getGoogleSearchPort();
		} catch (javax.xml.rpc.ServiceException jre) {
			if (jre.getLinkedCause() != null)
				jre.getLinkedCause().printStackTrace();
			throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
		}
		assertNotNull("binding is null", binding);

		// Time out after a minute
		binding.setTimeout(60000);

		// Test operation
		GoogleSearch.GoogleSearchResult value = null;
		value = binding.doGoogleSearch(new java.lang.String(), new java.lang.String(), 0, 0, true,
				new java.lang.String(), true, new java.lang.String(), new java.lang.String(), new java.lang.String());
		// TBD - validate results
	}

}
