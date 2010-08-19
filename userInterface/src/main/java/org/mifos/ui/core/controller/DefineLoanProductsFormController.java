/*
 * Copyright (c) 2005-2010 Grameen Foundation USA
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 *
 * See also http://www.apache.org/licenses/LICENSE-2.0.html for an
 * explanation of the license and how it is applied.
 */

package org.mifos.ui.core.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.mifos.application.admin.servicefacade.AdminServiceFacade;
import org.mifos.dto.domain.LoanProductRequest;
import org.mifos.dto.domain.LowerUpperMinMaxDefaultDto;
import org.mifos.dto.domain.MinMaxDefaultDto;
import org.mifos.dto.domain.RepaymentDetailsDto;
import org.mifos.dto.screen.AccountingDetailsDto;
import org.mifos.dto.screen.LoanAmountDetailsDto;
import org.mifos.dto.screen.LoanProductDetails;
import org.mifos.dto.screen.LoanProductFormDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@RequestMapping("/defineLoanProducts")
@SessionAttributes("loanProduct")
public class DefineLoanProductsFormController {

    private static final String REDIRECT_TO_ADMIN_SCREEN = "redirect:/AdminAction.do?method=load";
    private static final String CANCEL_PARAM = "CANCEL";

    @Autowired
    private AdminServiceFacade adminServiceFacade;
    private LoanProductAssembler loanProductAssembler = new LoanProductAssembler();

    protected DefineLoanProductsFormController(){
        //for spring autowiring
    }

    public DefineLoanProductsFormController(final AdminServiceFacade adminServicefacade){
        this.adminServiceFacade=adminServicefacade;
    }

    @SuppressWarnings("PMD")
    @RequestMapping(method = RequestMethod.GET)
    @ModelAttribute("loanProduct")
    public LoanProductFormBean showPopulatedForm() {

        LoanProductFormDto loanProductRefData = this.adminServiceFacade.retrieveLoanProductFormReferenceData();

        LoanProductFormBean loanProductFormBean = loanProductAssembler.populateWithReferenceData(loanProductRefData);

        // FIXME - Delegate to assembler
        DateTime startDate = new DateTime();

        GeneralProductDetails productDetails = new GeneralProductDetails();
        productDetails.setStartDateDay(startDate.getDayOfMonth());
        productDetails.setStartDateMonth(startDate.getMonthOfYear());
        productDetails.setStartDateYear(Integer.valueOf(startDate.getYearOfEra()).toString());

        loanProductFormBean.setGeneralDetails(productDetails);

        loanProductFormBean.setIncludeInLoanCycleCounter(false);
        loanProductFormBean.setInstallmentFrequencyRecurrenceEvery(Integer.valueOf(1));
        loanProductFormBean.setGracePeriodDurationInInstallments(Integer.valueOf(0));

        ByLastLoanAmountBean zero = new ByLastLoanAmountBean();
        zero.setLower(Double.valueOf("0"));
        ByLastLoanAmountBean one = new ByLastLoanAmountBean();
        ByLastLoanAmountBean two = new ByLastLoanAmountBean();
        ByLastLoanAmountBean three = new ByLastLoanAmountBean();
        ByLastLoanAmountBean four = new ByLastLoanAmountBean();
        ByLastLoanAmountBean five = new ByLastLoanAmountBean();

        loanProductFormBean.setLoanAmountByLastLoanAmount(new ByLastLoanAmountBean[] {zero, one, two, three, four, five});

        ByLoanCycleBean zeroCycle = new ByLoanCycleBean();
        ByLoanCycleBean oneCycle = new ByLoanCycleBean();
        ByLoanCycleBean twoCycle = new ByLoanCycleBean();
        ByLoanCycleBean threeCycle = new ByLoanCycleBean();
        ByLoanCycleBean fourCycle = new ByLoanCycleBean();
        ByLoanCycleBean greaterThanFourCycle = new ByLoanCycleBean();

        loanProductFormBean.setLoanAmountByLoanCycle(new ByLoanCycleBean[] {zeroCycle,oneCycle, twoCycle, threeCycle, fourCycle, greaterThanFourCycle});

        SameForAllLoanBean installmentsSameForAllLoans = new SameForAllLoanBean();
        installmentsSameForAllLoans.setMin(Double.valueOf("1"));
        loanProductFormBean.setInstallmentsSameForAllLoans(installmentsSameForAllLoans);

        loanProductFormBean.setInstallmentsByLastLoanAmount(new ByLastLoanAmountBean[] {zero, one, two, three, four, five});
        loanProductFormBean.setInstallmentsByLoanCycle(new ByLoanCycleBean[] {zeroCycle,oneCycle, twoCycle, threeCycle, fourCycle, greaterThanFourCycle});

        return loanProductFormBean;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String processFormSubmit(@RequestParam(value = CANCEL_PARAM, required = false) String cancel,
            @ModelAttribute("loanProduct") @Valid LoanProductFormBean loanProductFormBean, BindingResult result, SessionStatus status) {

        String viewName = REDIRECT_TO_ADMIN_SCREEN;

        if (StringUtils.isNotBlank(cancel)) {
            viewName = REDIRECT_TO_ADMIN_SCREEN;
            status.setComplete();
        } else if (result.hasErrors()) {
            viewName = "defineLoanProducts";
        } else {

            // FIXME - Delegate to assembler
            LoanProductRequest loanProduct = translateFrom(loanProductFormBean);

            adminServiceFacade.createLoanProduct(loanProduct);
            status.setComplete();
        }

        return viewName;
    }

    private LoanProductRequest translateFrom(LoanProductFormBean loanProductFormBean) {

        LoanProductDetails loanProductDetails = translateToLoanProductDetails(loanProductFormBean);

        LoanAmountDetailsDto loanAmountDetails = translateToLoanAmountDetails(loanProductFormBean);

        Integer interestRateType = Integer.valueOf(loanProductFormBean.getSelectedInterestRateCalculationType());
        Double maxInterest = Double.valueOf(loanProductFormBean.getMaxInterestRate());
        Double minInterest = Double.valueOf(loanProductFormBean.getMinInterestRate());
        Double defaultInterest = Double.valueOf(loanProductFormBean.getDefaultInterestRate());
        MinMaxDefaultDto interestRateRange = MinMaxDefaultDto.create(minInterest, maxInterest, defaultInterest);

        RepaymentDetailsDto repaymentDetails = translateToRepaymentDetails(loanProductFormBean);

        List<Integer> applicableFees = new ArrayList<Integer>();
        if (loanProductFormBean.getSelectedFees() != null) {
            for (String feeId : loanProductFormBean.getSelectedFees()) {
                applicableFees.add(Integer.valueOf(feeId));
            }
        }

        AccountingDetailsDto accountDetails = translateToAccountingDetails(loanProductFormBean);

        return new LoanProductRequest(loanProductDetails, loanAmountDetails, interestRateType, interestRateRange, repaymentDetails, applicableFees, accountDetails);
    }

    private AccountingDetailsDto translateToAccountingDetails(LoanProductFormBean loanProductFormBean) {

        List<Integer> applicableFunds = new ArrayList<Integer>();
        if (loanProductFormBean.getSelectedFunds() != null) {
            for (String fundId : loanProductFormBean.getSelectedFunds()) {
                applicableFunds.add(Integer.valueOf(fundId));
            }
        }
        Integer interestGlCodeId = Integer.valueOf(loanProductFormBean.getSelectedInterest());
        Integer principalClCodeId = Integer.valueOf(loanProductFormBean.getSelectedPrincipal());

        return new AccountingDetailsDto(applicableFunds, interestGlCodeId, principalClCodeId);
    }

    private LoanProductDetails translateToLoanProductDetails(LoanProductFormBean loanProductFormBean) {

        Integer category = Integer.valueOf(loanProductFormBean.getGeneralDetails().getSelectedCategory());
        Integer applicableFor = Integer.valueOf(loanProductFormBean.getGeneralDetails().getSelectedApplicableFor());
        DateTime startDate = new DateTime().withDate(Integer.valueOf(loanProductFormBean.getGeneralDetails().getStartDateYear()), loanProductFormBean.getGeneralDetails().getStartDateMonth(), loanProductFormBean.getGeneralDetails().getStartDateDay());
        DateTime endDate = null;
        if (StringUtils.isNotBlank(loanProductFormBean.getGeneralDetails().getEndDateYear())) {
            endDate = new DateTime().withDate(Integer.valueOf(loanProductFormBean.getGeneralDetails().getEndDateYear()), loanProductFormBean.getGeneralDetails().getEndDateMonth(), loanProductFormBean.getGeneralDetails().getEndDateDay());
        }

        return new LoanProductDetails(loanProductFormBean.getGeneralDetails().getName(), loanProductFormBean.getGeneralDetails().getShortName(), loanProductFormBean.getGeneralDetails().getDescription(), category,
                startDate, endDate, applicableFor, loanProductFormBean.isIncludeInLoanCycleCounter());
    }

    private LoanAmountDetailsDto translateToLoanAmountDetails(LoanProductFormBean loanProductFormBean) {
        Integer calculationType = Integer.valueOf(loanProductFormBean.getSelectedLoanAmountCalculationType());

        MinMaxDefaultDto sameForAllLoanRange = null;
        List<LowerUpperMinMaxDefaultDto>  byLastLoanAmountList = new ArrayList<LowerUpperMinMaxDefaultDto>();
        List<MinMaxDefaultDto> byLoanCycleList = new ArrayList<MinMaxDefaultDto>();
        if (Integer.valueOf(1).equals(calculationType)) {
            sameForAllLoanRange = translateSameForAllLoanBeanToMinMaxDefaultDto(loanProductFormBean.getLoanAmountSameForAllLoans());
        }

        if (Integer.valueOf(2).equals(calculationType)) {
            byLastLoanAmountList = translateByLastLoanAmountBeanToLowerUpperMinMaxDefaultDto(loanProductFormBean.getLoanAmountByLastLoanAmount());
        }

        if (Integer.valueOf(3).equals(calculationType)) {
            byLoanCycleList = translateLoanCycleBeanToMinMaxDefaultDto(loanProductFormBean.getLoanAmountByLoanCycle());
        }

        return new LoanAmountDetailsDto(calculationType, sameForAllLoanRange, byLastLoanAmountList, byLoanCycleList);
    }

    private LoanAmountDetailsDto translateToInstallmentDetails(LoanProductFormBean loanProductFormBean) {
        Integer calculationType = Integer.valueOf(loanProductFormBean.getSelectedInstallmentsCalculationType());

        MinMaxDefaultDto sameForAllLoanRange = null;
        List<LowerUpperMinMaxDefaultDto>  byLastLoanAmountList = new ArrayList<LowerUpperMinMaxDefaultDto>();
        List<MinMaxDefaultDto> byLoanCycleList = new ArrayList<MinMaxDefaultDto>();
        if (Integer.valueOf(1).equals(calculationType)) {
            sameForAllLoanRange = translateSameForAllLoanBeanToMinMaxDefaultDto(loanProductFormBean.getInstallmentsSameForAllLoans());
        }

        if (Integer.valueOf(2).equals(calculationType)) {
            byLastLoanAmountList = translateByLastLoanAmountBeanToLowerUpperMinMaxDefaultDto(loanProductFormBean.getInstallmentsByLastLoanAmount());
        }

        if (Integer.valueOf(3).equals(calculationType)) {
            byLoanCycleList = translateLoanCycleBeanToMinMaxDefaultDto(loanProductFormBean.getLoanAmountByLoanCycle());
        }

        return new LoanAmountDetailsDto(calculationType, sameForAllLoanRange, byLastLoanAmountList, byLoanCycleList);
    }

    private RepaymentDetailsDto translateToRepaymentDetails(LoanProductFormBean loanProductFormBean) {

        Integer frequencyType = Integer.valueOf(loanProductFormBean.getInstallmentFrequencyPeriod());
        Integer recurs = Integer.valueOf(loanProductFormBean.getInstallmentFrequencyRecurrenceEvery());

        LoanAmountDetailsDto installmentCalculationDetails = translateToInstallmentDetails(loanProductFormBean);

        Integer gracePeriodType = Integer.valueOf(loanProductFormBean.getSelectedGracePeriodType());
        Integer gracePeriodDuration = loanProductFormBean.getGracePeriodDurationInInstallments();

        RepaymentDetailsDto details = new RepaymentDetailsDto(frequencyType, recurs, installmentCalculationDetails, gracePeriodType, gracePeriodDuration);

        return details;
    }

    private MinMaxDefaultDto translateSameForAllLoanBeanToMinMaxDefaultDto(SameForAllLoanBean bean) {
        return MinMaxDefaultDto.create(bean.getMin(), bean.getMax(), bean.getTheDefault());
    }

    private List<MinMaxDefaultDto> translateLoanCycleBeanToMinMaxDefaultDto(ByLoanCycleBean[] loanAmountByLoanCycle) {
        List<MinMaxDefaultDto> byLoanCycleList = new ArrayList<MinMaxDefaultDto>();
        for (ByLoanCycleBean bean : loanAmountByLoanCycle) {
            byLoanCycleList.add(MinMaxDefaultDto.create(bean.getMin(), bean.getMax(), bean.getTheDefault()));
        }
        return byLoanCycleList;
    }

    private List<LowerUpperMinMaxDefaultDto> translateByLastLoanAmountBeanToLowerUpperMinMaxDefaultDto(ByLastLoanAmountBean[] loanAmountByLastLoanAmount) {
        List<LowerUpperMinMaxDefaultDto> list = new ArrayList<LowerUpperMinMaxDefaultDto>();
        for (ByLastLoanAmountBean bean : loanAmountByLastLoanAmount) {
            list.add(LowerUpperMinMaxDefaultDto.create(bean.getLower(), bean.getUpper(), bean.getMin(), bean.getMax(), bean.getTheDefault()));
        }
        return list;
    }

    public void setLoanProductAssembler(LoanProductAssembler loanProductAssembler) {
        this.loanProductAssembler = loanProductAssembler;
    }
}