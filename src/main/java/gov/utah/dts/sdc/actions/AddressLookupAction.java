package gov.utah.dts.sdc.actions;

import gov.utah.dts.addresslookup.client.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.PrincipalAware;
import org.apache.struts2.interceptor.PrincipalProxy;
import org.apache.struts2.interceptor.SessionAware;

public class AddressLookupAction extends SDCSupport implements SessionAware, PrincipalAware {

	protected static Log log = LogFactory.getLog(AddressLookupAction.class);
	private static final long serialVersionUID = 4386885373008047917L;
                   
    //AT
	//private String endpoint = "http://168.177.218.83:8080/addressLookup/services/AddressLookupService";
	private String endpoint = "http://api.dts.utah.gov/addressLookup/services/AddressLookupService";
    private String zip;
    private String src;
    private List<Address> addressList;
    private Integer addressListSize;
    private PrincipalProxy proxy;
    
    @SuppressWarnings("unchecked")
	public String cityStateLookup() throws Exception {
        log.debug("cityStateLookup...");
        addressList = new ArrayList<Address>();
        if (zip != null && !"".equals(zip)) {
            AddressLookupSOAPRequest addressLookupSOAPRequest = new AddressLookupSOAPRequest(endpoint);
            Address address = new Address();
            address.setZipCode(zip);
            addressList = addressLookupSOAPRequest.selectAddressInfo(address);
        }
        addressListSize = (Integer)addressList.size();
        return SUCCESS;
    }

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public List<Address> getAddressList() {
		return addressList;
	}

	public void setAddressList(List<Address> addressList) {
		this.addressList = addressList;
	}

	public Integer getListCount() {
		return addressListSize;
	}

	public void setAddressListSize(Integer addressListSize) {
		this.addressListSize = addressListSize;
	}

    public void setPrincipalProxy(PrincipalProxy proxy) {
        this.proxy = proxy;
    }
    
    public PrincipalProxy getProxy() {
        return proxy;
        
    }
}
