package gov.utah.dps.dld.webservice.sdct;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import gov.utah.dps.dld.webservice.sdct.gen.*;

public class SdctWsServiceImpl implements SdctWsService {

	StudentDriverPortType port = null;

	public SdctWsServiceImpl() {
		StudentDriverPortTypePortBindingQSService service = new StudentDriverPortTypePortBindingQSServiceLocator();
		try {
			port = service.getStudentDriverPortTypePortBindingQSPort();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}

	public SdctWsServiceImpl(String url) {
		try {
			StudentDriverPortTypePortBindingQSService service = new StudentDriverPortTypePortBindingQSServiceLocator();
			URL serviceUrl = new URL(url);
			port = service.getStudentDriverPortTypePortBindingQSPort(serviceUrl);
			System.out.println("** Connecting to Web services: " + url);
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}

	@Override
	public QueryByDLResponseType queryByDL(QueryByDLRequestType request) {
		QueryByDLResponseType toReturn = null;
		try {
			toReturn = port.queryByDL(request);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return toReturn;
	}

	@Override
	public CompletionByDLResponseType completionByDL(CompletionByDLRequestType request) {
		CompletionByDLResponseType toReturn = null;
		try {
			toReturn = port.completionByDL(request);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return toReturn;
	}

	@Override
	public CompletionByNameResponseType completionByName(CompletionByNameRequestType request) {
		CompletionByNameResponseType toReturn = null;
		try {
			toReturn = port.completionByName(request);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return toReturn;
	}

	@Override
	public CompleteWrittenByNameResponseType completeWrittenByName(CompleteWrittenByNameRequestType request) {
		CompleteWrittenByNameResponseType toReturn = null;
		try {
			toReturn = port.completeWrittenByName(request);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return toReturn;
	}

	@Override
	public CompleteRoadByNameResponseType completeRoadByName(CompleteRoadByNameRequestType request) {
		CompleteRoadByNameResponseType toReturn = null;
		try {
			toReturn = port.completeRoadByName(request);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return toReturn;
	}

	/*
	 * NOT YET IMPLEMENTED
	 * 
	 * @Override public DeleteByDLResponse deleteByDL(DeleteByDLRequest request)
	 * { DeleteByDLResponse toReturn = null; try { toReturn =
	 * port.deleteByDL(request); } catch (RemoteException e) {
	 * e.printStackTrace(); } return toReturn; }
	 * 
	 * @Override public DeleteByNameResponse deleteByName(DeleteByNameRequest
	 * request) { DeleteByNameResponse toReturn = null; try { toReturn =
	 * port.deleteByName(request); } catch (RemoteException e) {
	 * e.printStackTrace(); } return toReturn; }
	 */
}
