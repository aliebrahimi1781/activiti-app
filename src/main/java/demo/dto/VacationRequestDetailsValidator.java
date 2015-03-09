package demo.dto;

import org.springframework.validation.Errors;

import java.util.Date;

public class VacationRequestDetailsValidator {

  public void validate(VacationRequestDetails vacationRequestDetails, Errors errors) {
    int numberOfDays = vacationRequestDetails.getNumberOfDays();
    if (numberOfDays<1) {
      errors.rejectValue("numberOfDays", "required", "Value should be at least 1");
    }
    Date startDate=vacationRequestDetails.getStartDate();
    if(startDate==null || startDate.compareTo(new Date())<0){
      errors.rejectValue("startDate", "required", "Start date should be after today");
    }
    if(vacationRequestDetails.getVacationMotivation().isEmpty()){
      errors.rejectValue("vacationMotivation", "required", "you should write motivation");
    }
  }
}