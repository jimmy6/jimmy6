/**
 * ServicesServiceTestCase.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package localhost.amms.webservice.axisWebservice;

public class ServicesServiceTestCase extends junit.framework.TestCase {
	public ServicesServiceTestCase(java.lang.String name) {
		super(name);
	}

	public void testaxisWebserviceWSDL() throws Exception {
		javax.xml.rpc.ServiceFactory serviceFactory = javax.xml.rpc.ServiceFactory.newInstance();
		java.net.URL url = new java.net.URL(new localhost.amms.webservice.axisWebservice.ServicesServiceLocator()
				.getaxisWebserviceAddress()
				+ "?WSDL");
		javax.xml.rpc.Service service = serviceFactory.createService(url,
				new localhost.amms.webservice.axisWebservice.ServicesServiceLocator().getServiceName());
		assertTrue(service != null);
	}

	public void test1axisWebserviceGetTotal() throws Exception {
		localhost.amms.webservice.axisWebservice.AxisWebserviceSoapBindingStub binding;
		try {
			binding = (localhost.amms.webservice.axisWebservice.AxisWebserviceSoapBindingStub) new localhost.amms.webservice.axisWebservice.ServicesServiceLocator()
					.getaxisWebservice();
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
		value = binding.getTotal();
		// TBD - validate results
	}

}
