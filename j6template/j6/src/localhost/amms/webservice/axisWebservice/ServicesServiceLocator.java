/**
 * ServicesServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package localhost.amms.webservice.axisWebservice;

public class ServicesServiceLocator extends org.apache.axis.client.Service implements
		localhost.amms.webservice.axisWebservice.ServicesService {

	public ServicesServiceLocator() {
	}

	public ServicesServiceLocator(org.apache.axis.EngineConfiguration config) {
		super(config);
	}

	public ServicesServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName)
			throws javax.xml.rpc.ServiceException {
		super(wsdlLoc, sName);
	}

	// Use to get a proxy class for axisWebservice
	private java.lang.String axisWebservice_address = "http://localhost/amms/webservice/axisWebservice";

	public java.lang.String getaxisWebserviceAddress() {
		return axisWebservice_address;
	}

	// The WSDD service name defaults to the port name.
	private java.lang.String axisWebserviceWSDDServiceName = "axisWebservice";

	public java.lang.String getaxisWebserviceWSDDServiceName() {
		return axisWebserviceWSDDServiceName;
	}

	public void setaxisWebserviceWSDDServiceName(java.lang.String name) {
		axisWebserviceWSDDServiceName = name;
	}

	public localhost.amms.webservice.axisWebservice.Services getaxisWebservice() throws javax.xml.rpc.ServiceException {
		java.net.URL endpoint;
		try {
			endpoint = new java.net.URL(axisWebservice_address);
		} catch (java.net.MalformedURLException e) {
			throw new javax.xml.rpc.ServiceException(e);
		}
		return getaxisWebservice(endpoint);
	}

	public localhost.amms.webservice.axisWebservice.Services getaxisWebservice(java.net.URL portAddress)
			throws javax.xml.rpc.ServiceException {
		try {
			localhost.amms.webservice.axisWebservice.AxisWebserviceSoapBindingStub _stub = new localhost.amms.webservice.axisWebservice.AxisWebserviceSoapBindingStub(
					portAddress, this);
			_stub.setPortName(getaxisWebserviceWSDDServiceName());
			return _stub;
		} catch (org.apache.axis.AxisFault e) {
			return null;
		}
	}

	public void setaxisWebserviceEndpointAddress(java.lang.String address) {
		axisWebservice_address = address;
	}

	/**
	 * For the given interface, get the stub implementation. If this service has no port for the given interface, then
	 * ServiceException is thrown.
	 */
	public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
		try {
			if (localhost.amms.webservice.axisWebservice.Services.class.isAssignableFrom(serviceEndpointInterface)) {
				localhost.amms.webservice.axisWebservice.AxisWebserviceSoapBindingStub _stub = new localhost.amms.webservice.axisWebservice.AxisWebserviceSoapBindingStub(
						new java.net.URL(axisWebservice_address), this);
				_stub.setPortName(getaxisWebserviceWSDDServiceName());
				return _stub;
			}
		} catch (java.lang.Throwable t) {
			throw new javax.xml.rpc.ServiceException(t);
		}
		throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  "
				+ (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
	}

	/**
	 * For the given interface, get the stub implementation. If this service has no port for the given interface, then
	 * ServiceException is thrown.
	 */
	public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface)
			throws javax.xml.rpc.ServiceException {
		if (portName == null) {
			return getPort(serviceEndpointInterface);
		}
		java.lang.String inputPortName = portName.getLocalPart();
		if ("axisWebservice".equals(inputPortName)) {
			return getaxisWebservice();
		} else {
			java.rmi.Remote _stub = getPort(serviceEndpointInterface);
			((org.apache.axis.client.Stub) _stub).setPortName(portName);
			return _stub;
		}
	}

	public javax.xml.namespace.QName getServiceName() {
		return new javax.xml.namespace.QName("http://localhost/amms/webservice/axisWebservice", "ServicesService");
	}

	private java.util.HashSet ports = null;

	public java.util.Iterator getPorts() {
		if (ports == null) {
			ports = new java.util.HashSet();
			ports
					.add(new javax.xml.namespace.QName("http://localhost/amms/webservice/axisWebservice",
							"axisWebservice"));
		}
		return ports.iterator();
	}

	/**
	 * Set the endpoint address for the specified port name.
	 */
	public void setEndpointAddress(java.lang.String portName, java.lang.String address)
			throws javax.xml.rpc.ServiceException {

		if ("axisWebservice".equals(portName)) {
			setaxisWebserviceEndpointAddress(address);
		} else { // Unknown Port Name
			throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
		}
	}

	/**
	 * Set the endpoint address for the specified port name.
	 */
	public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address)
			throws javax.xml.rpc.ServiceException {
		setEndpointAddress(portName.getLocalPart(), address);
	}

}
