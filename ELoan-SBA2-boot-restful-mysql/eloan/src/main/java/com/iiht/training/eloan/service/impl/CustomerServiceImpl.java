package com.iiht.training.eloan.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iiht.training.eloan.dto.LoanDto;
import com.iiht.training.eloan.dto.LoanOutputDto;
import com.iiht.training.eloan.dto.UserDto;
import com.iiht.training.eloan.entity.Loan;
import com.iiht.training.eloan.entity.Users;
import com.iiht.training.eloan.exception.CustomerNotFoundException;
import com.iiht.training.eloan.exception.InvalidDataException;
import com.iiht.training.eloan.exception.LoanNotFoundException;
import com.iiht.training.eloan.repository.LoanRepository;
import com.iiht.training.eloan.repository.ProcessingInfoRepository;
import com.iiht.training.eloan.repository.SanctionInfoRepository;
import com.iiht.training.eloan.repository.UsersRepository;
import com.iiht.training.eloan.service.AdminService;
import com.iiht.training.eloan.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private UsersRepository usersRepository;
	
	@Autowired
	private LoanRepository loanRepository;
	
	@Autowired
	private ProcessingInfoRepository pProcessingInfoRepository;
	
	@Autowired
	private SanctionInfoRepository sanctionInfoRepository;
	
	@Autowired
	private AdminService adminService;
	
	@Override
	public UserDto register(UserDto userDto) {
		if(adminService.isValidUserDetails(userDto)) {
			Users user = adminService.convertToUserEntity(userDto);
			user.setRole("Customer");
			usersRepository.save(user);
			userDto.setId(user.getId());
		}
		return userDto;
	}

	@Override
	public LoanOutputDto applyLoan(Long customerId, LoanDto loanDto) {
		LoanOutputDto loanOutputDto =  new LoanOutputDto();
		if(isValidLoanDetails(loanDto)) {
			if(! usersRepository.existsById(customerId)) {
				throw new CustomerNotFoundException("Customer with the id "+ customerId+ " does not exist!!");
			}
			Loan loan1 = convertToLoanEntity(loanDto);
			loan1.setStatus(0);
			Loan loan = loanRepository.save(loan1);
			
			loanOutputDto.setCustomerId(customerId);
			loanOutputDto.setLoanAppId(loan.getId());
			loanOutputDto.setLoanDto(loanDto);
			loanOutputDto.setUserDto(adminService.convertToUserDto((usersRepository.findById(customerId).get())));
			loanOutputDto.setStatus("Applied");
		}
		return loanOutputDto;
	}

	@Override
	public LoanOutputDto getStatus(Long loanAppId) {
		if(! loanRepository.existsById(loanAppId)) {
			throw  new LoanNotFoundException("Loan with the application id " +loanAppId+ " does not exist!!" );
		}
		Loan loan = loanRepository.findById(loanAppId).get();
		
		LoanOutputDto loanOutputDto =  new LoanOutputDto();
		loanOutputDto.setCustomerId(loan.getCustomerId());
		loanOutputDto.setLoanAppId(loan.getId());
		loanOutputDto.setLoanDto(convertToLoanDto(loan));
		loanOutputDto.setUserDto(adminService.convertToUserDto((usersRepository.findById(loan.getCustomerId()).get())));
		loanOutputDto.setRemark(loan.getRemark());
		switch(loan.getStatus()){
		case 0 : loanOutputDto.setStatus("Applied");
				 break;
		case 1 : loanOutputDto.setStatus("Processed");
		 		 break;
		case 2 : loanOutputDto.setStatus("Sanctioned");
				 break;
		case -1 :loanOutputDto.setStatus("Rejected");
		  		 break;
		}
				
		return loanOutputDto;
	}

	@Override
	public List<LoanOutputDto> getStatusAll(Long customerId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private boolean isValidLoanDetails(LoanDto loanDto) {
		boolean isValid = true;
		List<String> errMsgs = new ArrayList<>();
		
		if(loanDto!=null) {
			String loanName = loanDto.getLoanName();
			if(!(loanName != null && loanName.length()>=3 && loanName.length()<=100)) {
				isValid=false;
				errMsgs.add("Loan name cannot be left blank and must be of length between 3 and 100 characters");
			}
			
			Double loanAmount = loanDto.getLoanAmount();
			if(!(loanAmount != null && loanAmount != 0)) {
				isValid=false;
				errMsgs.add("Loan amount cannot be left blank and must not be zero");
			}
			
			if(!errMsgs.isEmpty()) {
				throw new InvalidDataException("Invalid details: "+errMsgs);
				}
			}else {
				isValid=false;
				throw new InvalidDataException("Details are not supplied");
			}

		return isValid;	
	}
	
	public Loan convertToLoanEntity(LoanDto loanDto) {
		
		Loan loan = new Loan();
		loan.setLoanName(loanDto.getLoanName());
		loan.setLoanAmount(loanDto.getLoanAmount());
		loan.setLoanApplicationDate(loanDto.getLoanApplicationDate());
		loan.setBusinessStructure(loanDto.getBusinessStructure());
		loan.setBillingIndicator(loanDto.getBillingIndicator());
		loan.setTaxIndicator(loanDto.getTaxIndicator());
		return loan;	
	}
	
	public LoanDto convertToLoanDto(Loan loan) {
		
		LoanDto loanDto = new LoanDto();
		loanDto.setLoanName(loan.getLoanName());
		loanDto.setLoanAmount(loan.getLoanAmount());
		loanDto.setLoanApplicationDate(loan.getLoanApplicationDate());
		loanDto.setBusinessStructure(loan.getBusinessStructure());
		loanDto.setBillingIndicator(loan.getBillingIndicator());
		loanDto.setTaxIndicator(loan.getTaxIndicator());
		return loanDto;	
	}
}
