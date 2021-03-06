package ca.bc.gov.open.jag.efilingapi.submission.models;

import ca.bc.gov.open.jag.efilingapi.api.model.ClientApplication;
import ca.bc.gov.open.jag.efilingapi.api.model.FilingPackage;
import ca.bc.gov.open.jag.efilingcommons.model.AccountDetails;
import ca.bc.gov.open.jag.efilingapi.api.model.Navigation;
import ca.bc.gov.open.jag.efilingcommons.model.AccountDetails;
import ca.bc.gov.open.jag.efilingcommons.model.ServiceFees;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.UUID;

/**
 * Represents the submission details for a transaction
 */
public class Submission {

    private UUID id;

    private long expiryDate;

    private Navigation navigation;

    private ClientApplication clientApplication;

    private FilingPackage filingPackage;

    private List<ServiceFees> fees;

    private AccountDetails accountDetails;

    protected Submission(Submission.Builder builder) {
        this.id = UUID.randomUUID();
        this.filingPackage = builder.filingPackage;
        this.navigation = builder.navigation;
        this.clientApplication = builder.clientApplication;
        this.fees = builder.fees;
        this.accountDetails = builder.accountDetails;
        this.expiryDate = builder.expiryDate;
    }

    public static Submission.Builder builder() {
        return new Submission.Builder();
    }

    @JsonCreator
    public Submission(
            @JsonProperty("id") UUID id,
            @JsonProperty("package") FilingPackage filingPackage,
            @JsonProperty("navigation") Navigation navigation,
            @JsonProperty("clientApplication") ClientApplication clientApplication,
            @JsonProperty("fees") List<ServiceFees> fees,
            @JsonProperty("accountDetails") AccountDetails accountDetails,
            @JsonProperty("expiryDate") long expiryDate) {
        this.id = id;
        this.filingPackage = filingPackage;
        this.navigation = navigation;
        this.clientApplication = clientApplication;
        this.fees = fees;
        this.accountDetails = accountDetails;
        this.expiryDate = expiryDate;
    }

    public UUID getId() { return id; }

    public FilingPackage getFilingPackage() {
        return filingPackage;
    }

    public Navigation getNavigation() {
        return navigation;
    }

    public ClientApplication getClientApplication() { return clientApplication; }

    public List<ServiceFees> getFees() {
        return fees;
    }

    public AccountDetails getAccountDetails() {
        return accountDetails;
    }

    public long getExpiryDate() {
        return expiryDate;
    }

    public static class Builder {

        private FilingPackage filingPackage;
        private Navigation navigation;
        private ClientApplication clientApplication;
        private List<ServiceFees> fees;
        private AccountDetails accountDetails;
        private long expiryDate;

        public Builder filingPackage(FilingPackage filingPackage) {
            this.filingPackage =  filingPackage;
            return this;
        }

        public Builder navigation(Navigation navigation) {
            this.navigation = navigation;
            return this;
        }

        public Builder clientApplication(ClientApplication clientApplication) {
            this.clientApplication = clientApplication;
            return this;
        }

        public Builder fees(List<ServiceFees> fees) {
            this.fees = fees;
            return this;
        }

        public Builder accountDetails(AccountDetails accountDetails) {
            this.accountDetails = accountDetails;
            return this;
        }

        public Builder expiryDate(long expiryDate) {
            this.expiryDate = expiryDate;
            return this;
        }

        public Submission create() {
            return new Submission(this);
        }
    }


}
