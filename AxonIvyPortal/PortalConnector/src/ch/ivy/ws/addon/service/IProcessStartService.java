package ch.ivy.ws.addon.service;

import ch.ivy.ws.addon.WSException;
import ch.ivy.ws.addon.bo.ProcessStartServiceResult;

/**
 * Process start service provides a set of service methods for available process starts
 * 
 * @author mde
 *
 */
public interface IProcessStartService {

  /**
   * Find all process starts by criteria
   */
  public ProcessStartServiceResult findProcessStartsByCriteria(ProcessSearchCriteria processSearchCriteria,
      String serverUrl) throws WSException;
}
