package controls;

import java.io.Serializable;
import java.util.Map;

import CordConverter.ControlTypes;

public class ControlsModel implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String Name;
	private String toolLengthCompensationFormat;
	private String dwellFormat;
	private String retractionFormatLowerLimit;
	private String retractionFormatUpperLimit;
	private String DrillingBottomFormat;
	private String boringretractionFormat;
	private String drillingDwellFormat;
	private String longHolesCycleAdvanceFormat;
	private String tappingAdditionalParametrs;
	

	private int sprindleCollantCode;
	private int defaultCollantCode;
	private int ceilingCollantCode;
	private Map<String,String> codeDictionary;
	private String baseDefinitionCode;
	
	
	
	
	public String getName()
	{
		return this.Name;
	}
	public void setName(String name)
	{
		this.Name=name;
	}
	
	public String getDwellFormat() {
		return dwellFormat;
	}
	public void setDwellFormat(String dwellFormat) {
		this.dwellFormat = dwellFormat;
	}
	
	public String getRetractionFormatLowerLimit() {
		return retractionFormatLowerLimit;
	}
	public void setRetractionFormatLowerLimit(String retractionFormatLowerLimit) {
		this.retractionFormatLowerLimit = retractionFormatLowerLimit;
	}
	public String getRetractionFormatUpperLimit() {
		return retractionFormatUpperLimit;
	}
	public void setRetractionFormatUpperLimit(String retractionFormatUpperLimit) {
		this.retractionFormatUpperLimit = retractionFormatUpperLimit;
	}
	public String getDrillingBottomFormat() {
		return DrillingBottomFormat;
	}
	public void setDrillingBottomFormat(String drillingBottomFormat) {
		DrillingBottomFormat = drillingBottomFormat;
	}
	public String getBoringretractionFormat() {
		return boringretractionFormat;
	}
	public void setBoringretractionFormat(String boringretractionFormat) {
		this.boringretractionFormat = boringretractionFormat;
	}
	public String getDrillingDwellFormat() {
		return drillingDwellFormat;
	}
	public void setDrillingDwellFormat(String drillingDwellFormat) {
		this.drillingDwellFormat = drillingDwellFormat;
	}
	public int getSprindleCollantCode() {
		return sprindleCollantCode;
	}
	public void setSprindleCollantCode(int sprindleCollantCode) {
		this.sprindleCollantCode = sprindleCollantCode;
	}
	public int getDefaultCollantCode() {
		return defaultCollantCode;
	}
	public void setDefaultCollantCode(int defaultCollantCode) {
		this.defaultCollantCode = defaultCollantCode;
	}
	public int getCeilingCollantCode() {
		return ceilingCollantCode;
	}
	public void setCeilingCollantCode(int ceilingCollantCode) {
		this.ceilingCollantCode = ceilingCollantCode;
	}
	public String getBaseDefinitionCode() {
		return baseDefinitionCode;
	}
	public void setBaseDefinitionCode(String baseDefinitionCode) {
		this.baseDefinitionCode = baseDefinitionCode;
	}

	
	public String getCodeDesciption(String code)
	{
		if(this.codeDictionary.containsValue(code))
		{
			return codeDictionary.get(code);
		}
		else return "";
	}
	
	public Map<String,String> getCodeMap()
	{
		return this.codeDictionary;
	}
	
	public String getToolLengthCompensationFormat() {
		return toolLengthCompensationFormat;
	}
	public void setToolLengthCompensationFormat(String toolLengthCompensationFormat) {
		this.toolLengthCompensationFormat = toolLengthCompensationFormat;
	}
	
	public String getLongHolesCycleAdvanceFormat() {
		return longHolesCycleAdvanceFormat;
	}
	public void setLongHolesCycleAdvanceFormat(String longHolesCycleAdvanceFormat) {
		this.longHolesCycleAdvanceFormat = longHolesCycleAdvanceFormat;
	}
	
	public String getTappingAdditionalParametrs() {
		return tappingAdditionalParametrs;
	}
	public void setTappingAdditionalParametrs(String tappingAdditionalParametrs) {
		this.tappingAdditionalParametrs = tappingAdditionalParametrs;
	}

	
	@Override
	public String toString()
	{
		return this.Name;
	}
	
}
