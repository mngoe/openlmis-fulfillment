package org.openlmis.fulfillment.web.errorhandler;

import static org.openlmis.fulfillment.i18n.MessageKeys.ERROR_DATA_INTEGRITY_VIOLATION;
import static org.openlmis.fulfillment.i18n.MessageKeys.ERROR_REFERENCE_DATA_RETRIEVE;

import org.openlmis.fulfillment.service.ConfigurationSettingNotFoundException;
import org.openlmis.fulfillment.service.DuplicateTransferPropertiesException;
import org.openlmis.fulfillment.service.IncorrectTransferPropertiesException;
import org.openlmis.fulfillment.service.OrderFileException;
import org.openlmis.fulfillment.service.OrderStorageException;
import org.openlmis.fulfillment.service.ReportingException;
import org.openlmis.fulfillment.service.referencedata.ReferenceDataRetrievalException;
import org.openlmis.fulfillment.util.Message;
import org.openlmis.fulfillment.web.MissingPermissionException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Controller advice responsible for handling errors specific to fulfilment.
 */
@ControllerAdvice
public class GeneralErrorHandling extends AbstractErrorHandling {

  @ExceptionHandler(OrderFileException.class)
  public Message.LocalizedMessage handleOrderFileGenerationError(OrderFileException ex) {
    return logErrorAndRespond("Unable to generate the order file", ex);
  }

  @ExceptionHandler(ReportingException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public Message.LocalizedMessage handlerReportingException(ReportingException ex) {
    return logErrorAndRespond("Reporting error", ex);
  }

  /**
   * Handles the {@link DataIntegrityViolationException} which signals a violation of some sort
   * of a db constraint like unique. Returns error 409 (CONFLICT) and a JSON representation of the
   * error as the body.
   *
   * @return the localized message
   */
  @ExceptionHandler(DataIntegrityViolationException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  @ResponseBody
  public Message.LocalizedMessage handleDataIntegrityViolation(DataIntegrityViolationException ex) {
    return logErrorAndRespond(
        "Data integrity violation",
        ERROR_DATA_INTEGRITY_VIOLATION, ex.getMessage()
    );
  }

  /**
   * Handles the {@link ReferenceDataRetrievalException} which we were unable to retrieve
   * reference data due to a communication error.
   *
   * @param ex the exception that caused the issue
   * @return the error response
   */
  @ExceptionHandler(ReferenceDataRetrievalException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ResponseBody
  public Message.LocalizedMessage handleRefDataException(ReferenceDataRetrievalException ex) {
    return logErrorAndRespond(
        "Error fetching from reference data",
        ERROR_REFERENCE_DATA_RETRIEVE, ex.getResource(), ex.getStatus().toString(), ex.getResponse()
    );
  }

  @ExceptionHandler(OrderStorageException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ResponseBody
  public Message.LocalizedMessage handleOrderStorageException(OrderStorageException ex) {
    return logErrorAndRespond("Unable to store the order", ex);
  }

  @ExceptionHandler(MissingPermissionException.class)
  @ResponseStatus(HttpStatus.FORBIDDEN)
  @ResponseBody
  public Message.LocalizedMessage handleMissingPermissionException(MissingPermissionException ex) {
    return logErrorAndRespond("Missing permission for this action", ex);
  }

  @ExceptionHandler(DuplicateTransferPropertiesException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public Message.LocalizedMessage handleDuplicateTransferPropertiesException(
      DuplicateTransferPropertiesException ex) {
    return logErrorAndRespond("Duplicate facility transfer properties", ex);
  }

  @ExceptionHandler(IncorrectTransferPropertiesException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public Message.LocalizedMessage handleIncorrectTransferPropertiesException(
      IncorrectTransferPropertiesException ex) {
    return logErrorAndRespond("Incorrect facility transfer properties", ex);
  }

  @ExceptionHandler(ConfigurationSettingNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ResponseBody
  public Message.LocalizedMessage handleConfigurationSettingNotFoundException(
      ConfigurationSettingNotFoundException ex) {
    return logErrorAndRespond("Cannot find configuration setting", ex);
  }
}
