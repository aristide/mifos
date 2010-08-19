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

import java.util.Map;

@SuppressWarnings("PMD")
@edu.umd.cs.findbugs.annotations.SuppressWarnings(value="EI_EXPOSE_REP", justification="..")
public class LoanProductFormBean {

    private GeneralProductDetails generalDetails;

    private boolean includeInLoanCycleCounter;

    private Map<String, String> loanAmountCalculationTypeOptions;

    private String selectedLoanAmountCalculationType;
    private SameForAllLoanBean loanAmountSameForAllLoans;
    private ByLastLoanAmountBean[] loanAmountByLastLoanAmount;
    private ByLoanCycleBean[] loanAmountByLoanCycle;

    private Map<String, String> interestRateCalculationTypeOptions;
    private String selectedInterestRateCalculationType;
    private Double maxInterestRate;
    private Double minInterestRate;
    private Double defaultInterestRate;

    private Map<String, String> installmentFrequencyPeriodOptions;
    private String installmentFrequencyPeriod;
    private Integer installmentFrequencyRecurrenceEvery;

    private Map<String, String> installmentsCalculationTypeOptions;

    private String selectedInstallmentsCalculationType;
    private SameForAllLoanBean installmentsSameForAllLoans;
    private ByLastLoanAmountBean[] installmentsByLastLoanAmount;
    private ByLoanCycleBean[] installmentsByLoanCycle;

    private String selectedGracePeriodType;
    private Map<String, String> gracePeriodTypeOptions;
    private Integer gracePeriodDurationInInstallments;

    private Map<String, String> applicableFeeOptions;
    private Map<String, String> selectedFeeOptions;
    private String[] applicableFees;
    private String[] selectedFees;

    private Map<String, String> applicableFundOptions;
    private Map<String, String> selectedFundOptions;
    private String[] applicableFunds;
    private String[] selectedFunds;

    private Map<String, String> interestGeneralLedgerOptions;
    private String selectedInterest;
    private Map<String, String> principalGeneralLedgerOptions;
    private String selectedPrincipal;

    public boolean isIncludeInLoanCycleCounter() {
        return this.includeInLoanCycleCounter;
    }

    public void setIncludeInLoanCycleCounter(boolean includeInLoanCycleCounter) {
        this.includeInLoanCycleCounter = includeInLoanCycleCounter;
    }

    public String getSelectedInterestRateCalculationType() {
        return this.selectedInterestRateCalculationType;
    }

    public void setSelectedInterestRateCalculationType(String selectedInterestRateCalculationType) {
        this.selectedInterestRateCalculationType = selectedInterestRateCalculationType;
    }

    public Map<String, String> getInterestRateCalculationTypeOptions() {
        return this.interestRateCalculationTypeOptions;
    }

    public void setInterestRateCalculationTypeOptions(Map<String, String> interestRateCalculationTypeOptions) {
        this.interestRateCalculationTypeOptions = interestRateCalculationTypeOptions;
    }

    public Double getMaxInterestRate() {
        return this.maxInterestRate;
    }

    public void setMaxInterestRate(Double maxInterestRate) {
        this.maxInterestRate = maxInterestRate;
    }

    public Double getMinInterestRate() {
        return this.minInterestRate;
    }

    public void setMinInterestRate(Double minInterestRate) {
        this.minInterestRate = minInterestRate;
    }

    public Double getDefaultInterestRate() {
        return this.defaultInterestRate;
    }

    public void setDefaultInterestRate(Double defaultInterestRate) {
        this.defaultInterestRate = defaultInterestRate;
    }

    public String getInstallmentFrequencyPeriod() {
        return this.installmentFrequencyPeriod;
    }

    public void setInstallmentFrequencyPeriod(String installmentFrequencyPeriod) {
        this.installmentFrequencyPeriod = installmentFrequencyPeriod;
    }

    public Integer getInstallmentFrequencyRecurrenceEvery() {
        return this.installmentFrequencyRecurrenceEvery;
    }

    public void setInstallmentFrequencyRecurrenceEvery(Integer installmentFrequencyRecurrenceEvery) {
        this.installmentFrequencyRecurrenceEvery = installmentFrequencyRecurrenceEvery;
    }

    public String getSelectedGracePeriodType() {
        return this.selectedGracePeriodType;
    }

    public void setSelectedGracePeriodType(String selectedGracePeriodType) {
        this.selectedGracePeriodType = selectedGracePeriodType;
    }

    public Map<String, String> getGracePeriodTypeOptions() {
        return this.gracePeriodTypeOptions;
    }

    public void setGracePeriodTypeOptions(Map<String, String> gracePeriodTypeOptions) {
        this.gracePeriodTypeOptions = gracePeriodTypeOptions;
    }

    public Integer getGracePeriodDurationInInstallments() {
        return this.gracePeriodDurationInInstallments;
    }

    public void setGracePeriodDurationInInstallments(Integer gracePeriodDurationInInstallments) {
        this.gracePeriodDurationInInstallments = gracePeriodDurationInInstallments;
    }

    public Map<String, String> getApplicableFeeOptions() {
        return this.applicableFeeOptions;
    }

    public void setApplicableFeeOptions(Map<String, String> applicableFeeOptions) {
        this.applicableFeeOptions = applicableFeeOptions;
    }

    public Map<String, String> getSelectedFeeOptions() {
        return this.selectedFeeOptions;
    }

    public void setSelectedFeeOptions(Map<String, String> selectedFeeOptions) {
        this.selectedFeeOptions = selectedFeeOptions;
    }

    public Map<String, String> getApplicableFundOptions() {
        return this.applicableFundOptions;
    }

    public void setApplicableFundOptions(Map<String, String> applicableFundOptions) {
        this.applicableFundOptions = applicableFundOptions;
    }

    public Map<String, String> getSelectedFundOptions() {
        return this.selectedFundOptions;
    }

    public void setSelectedFundOptions(Map<String, String> selectedFundOptions) {
        this.selectedFundOptions = selectedFundOptions;
    }

    public Map<String, String> getInterestGeneralLedgerOptions() {
        return this.interestGeneralLedgerOptions;
    }

    public void setInterestGeneralLedgerOptions(Map<String, String> interestGeneralLedgerOptions) {
        this.interestGeneralLedgerOptions = interestGeneralLedgerOptions;
    }

    public Map<String, String> getPrincipalGeneralLedgerOptions() {
        return this.principalGeneralLedgerOptions;
    }

    public void setPrincipalGeneralLedgerOptions(Map<String, String> principalGeneralLedgerOptions) {
        this.principalGeneralLedgerOptions = principalGeneralLedgerOptions;
    }

    public Map<String, String> getLoanAmountCalculationTypeOptions() {
        return this.loanAmountCalculationTypeOptions;
    }

    public void setLoanAmountCalculationTypeOptions(Map<String, String> loanAmountCalculationTypeOptions) {
        this.loanAmountCalculationTypeOptions = loanAmountCalculationTypeOptions;
    }

    public Map<String, String> getInstallmentFrequencyPeriodOptions() {
        return this.installmentFrequencyPeriodOptions;
    }

    public void setInstallmentFrequencyPeriodOptions(Map<String, String> installmentFrequencyPeriodOptions) {
        this.installmentFrequencyPeriodOptions = installmentFrequencyPeriodOptions;
    }

    public Map<String, String> getInstallmentsCalculationTypeOptions() {
        return this.installmentsCalculationTypeOptions;
    }

    public void setInstallmentsCalculationTypeOptions(Map<String, String> installmentsCalculationTypeOptions) {
        this.installmentsCalculationTypeOptions = installmentsCalculationTypeOptions;
    }

    public String[] getSelectedFees() {
        return this.selectedFees;
    }

    public void setSelectedFees(String[] selectedFees) {
        this.selectedFees = selectedFees;
    }

    public String[] getSelectedFunds() {
        return this.selectedFunds;
    }

    public void setSelectedFunds(String[] selectedFunds) {
        this.selectedFunds = selectedFunds;
    }

    public String getSelectedInterest() {
        return this.selectedInterest;
    }

    public void setSelectedInterest(String selectedInterest) {
        this.selectedInterest = selectedInterest;
    }

    public String getSelectedPrincipal() {
        return this.selectedPrincipal;
    }

    public void setSelectedPrincipal(String selectedPrincipal) {
        this.selectedPrincipal = selectedPrincipal;
    }

    public String getSelectedLoanAmountCalculationType() {
        return this.selectedLoanAmountCalculationType;
    }

    public void setSelectedLoanAmountCalculationType(String selectedLoanAmountCalculationType) {
        this.selectedLoanAmountCalculationType = selectedLoanAmountCalculationType;
    }

    public SameForAllLoanBean getLoanAmountSameForAllLoans() {
        return this.loanAmountSameForAllLoans;
    }

    public void setLoanAmountSameForAllLoans(SameForAllLoanBean loanAmountSameForAllLoans) {
        this.loanAmountSameForAllLoans = loanAmountSameForAllLoans;
    }

    public ByLastLoanAmountBean[] getLoanAmountByLastLoanAmount() {
        return this.loanAmountByLastLoanAmount;
    }

    public void setLoanAmountByLastLoanAmount(ByLastLoanAmountBean[] loanAmountByLastLoanAmount) {
        this.loanAmountByLastLoanAmount = loanAmountByLastLoanAmount;
    }

    public ByLoanCycleBean[] getLoanAmountByLoanCycle() {
        return this.loanAmountByLoanCycle;
    }

    public void setLoanAmountByLoanCycle(ByLoanCycleBean[] loanAmountByLoanCycle) {
        this.loanAmountByLoanCycle = loanAmountByLoanCycle;
    }

    public String getSelectedInstallmentsCalculationType() {
        return this.selectedInstallmentsCalculationType;
    }

    public void setSelectedInstallmentsCalculationType(String selectedInstallmentsCalculationType) {
        this.selectedInstallmentsCalculationType = selectedInstallmentsCalculationType;
    }

    public SameForAllLoanBean getInstallmentsSameForAllLoans() {
        return this.installmentsSameForAllLoans;
    }

    public void setInstallmentsSameForAllLoans(SameForAllLoanBean installmentsSameForAllLoans) {
        this.installmentsSameForAllLoans = installmentsSameForAllLoans;
    }

    public ByLastLoanAmountBean[] getInstallmentsByLastLoanAmount() {
        return this.installmentsByLastLoanAmount;
    }

    public void setInstallmentsByLastLoanAmount(ByLastLoanAmountBean[] installmentsByLastLoanAmount) {
        this.installmentsByLastLoanAmount = installmentsByLastLoanAmount;
    }

    public ByLoanCycleBean[] getInstallmentsByLoanCycle() {
        return this.installmentsByLoanCycle;
    }

    public void setInstallmentsByLoanCycle(ByLoanCycleBean[] installmentsByLoanCycle) {
        this.installmentsByLoanCycle = installmentsByLoanCycle;
    }

    public String[] getApplicableFees() {
        return this.applicableFees;
    }

    public void setApplicableFees(String[] applicableFees) {
        this.applicableFees = applicableFees;
    }

    public String[] getApplicableFunds() {
        return this.applicableFunds;
    }

    public void setApplicableFunds(String[] applicableFunds) {
        this.applicableFunds = applicableFunds;
    }

    public GeneralProductDetails getGeneralDetails() {
        return this.generalDetails;
    }

    public void setGeneralDetails(GeneralProductDetails generalDetails) {
        this.generalDetails = generalDetails;
    }
}