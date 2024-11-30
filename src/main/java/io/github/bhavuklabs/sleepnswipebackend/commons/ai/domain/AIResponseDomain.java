package io.github.bhavuklabs.sleepnswipebackend.commons.ai.domain;


public class AIResponseDomain {

    private String generatedText;
    private String contextRetained;
    private String usageDetails;

    public String getGeneratedText() {
        return generatedText;
    }

    public void setGeneratedText(String generatedText) {
        this.generatedText = generatedText;
    }

    public String getContextRetained() {
        return contextRetained;
    }

    public void setContextRetained(String contextRetained) {
        this.contextRetained = contextRetained;
    }

    public String getUsageDetails() {
        return usageDetails;
    }

    public void setUsageDetails(String usageDetails) {
        this.usageDetails = usageDetails;
    }

    @Override
    public String toString() {
        return "AIResponseDomain{" +
                "generatedText='" + generatedText + '\'' +
                ", contextRetained='" + contextRetained + '\'' +
                ", usageDetails='" + usageDetails + '\'' +
                '}';
    }
}


