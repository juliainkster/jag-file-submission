package ca.bc.gov.open.jag.efilingcommons.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.UUID;

public class AccountDetails {

    private UUID universalId;
    private BigDecimal accountId;
    private BigDecimal clientId;
    private String firstName;
    private String lastName;
    private String email;
    private String middleName;
    private boolean fileRolePresent;

    protected AccountDetails(AccountDetails.Builder builder) {

        this.universalId = builder.universalId;
        this.accountId = builder.accountId;
        this.clientId = builder.clientId;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.email = builder.email;
        this.middleName = builder.middleName;
        this.fileRolePresent = builder.fileRolePresent;

    }

    public static AccountDetails.Builder builder() {

        return new AccountDetails.Builder();
    }

    @JsonCreator
    public AccountDetails(
            @JsonProperty("universalId") UUID universalId,
            @JsonProperty("accountId") BigDecimal accountId,
            @JsonProperty("clientId") BigDecimal clientId,
            @JsonProperty("fileRolePresent") boolean fileRolePresent,
            @JsonProperty("firstName") String firstName,
            @JsonProperty("lastName") String lastName,
            @JsonProperty("middleName") String middleName,
            @JsonProperty("email") String email) {
        this.universalId = universalId;
        this.accountId = accountId;
        this.clientId = clientId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.middleName = middleName;
        this.fileRolePresent = fileRolePresent;
    }

    public UUID getUniversalId() {
        return universalId;
    }

    public BigDecimal getAccountId() {
        return accountId;
    }

    public BigDecimal getClientId() {
        return clientId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getMiddleName() {
        return middleName;
    }

    public boolean isFileRolePresent() {
        return fileRolePresent;
    }

    public static class Builder {

        private UUID universalId;
        private BigDecimal accountId;
        private BigDecimal clientId;
        private String firstName;
        private String lastName;
        private String email;
        private String middleName;
        private boolean fileRolePresent;

        public Builder universalId(UUID universalId) {
            this.universalId = universalId;
            return this;
        }

        public Builder accountId(BigDecimal accountId) {
            this.accountId = accountId;
            return this;
        }

        public Builder clientId(BigDecimal clientId) {
            this.clientId = clientId;
            return this;
        }

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder middleName(String middleName) {
            this.middleName = middleName;
            return this;
        }

        public Builder fileRolePresent(boolean fileRolePresent) {
            this.fileRolePresent = fileRolePresent;
            return this;
        }

        public AccountDetails create() {
            return new AccountDetails(this);
        }
    }

}
