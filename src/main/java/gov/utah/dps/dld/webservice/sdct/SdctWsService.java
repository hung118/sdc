package gov.utah.dps.dld.webservice.sdct;

import gov.utah.dps.dld.webservice.sdct.gen.*;

public interface SdctWsService {
	
	public QueryByDLResponseType queryByDL(QueryByDLRequestType request);

	public CompletionByDLResponseType completionByDL(CompletionByDLRequestType request);

	public CompletionByNameResponseType completionByName(CompletionByNameRequestType request);

	public CompleteWrittenByNameResponseType completeWrittenByName(CompleteWrittenByNameRequestType request);

	public CompleteRoadByNameResponseType completeRoadByName(CompleteRoadByNameRequestType request);

	/*
	 * NOT YET IMPLEMENTED
	 * 
	 * public DeleteByDLResponse deleteByDL(DeleteByDLRequest request); public
	 * DeleteByNameResponse deleteByName(DeleteByNameRequest request);
	 */
}
