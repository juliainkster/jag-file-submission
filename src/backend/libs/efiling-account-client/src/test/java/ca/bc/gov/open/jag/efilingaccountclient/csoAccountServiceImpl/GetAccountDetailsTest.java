package ca.bc.gov.open.jag.efilingaccountclient.csoAccountServiceImpl;

import brooks.roleregistry_source_roleregistry_ws_provider.roleregistry.RegisteredRole;
import brooks.roleregistry_source_roleregistry_ws_provider.roleregistry.RoleRegistry;
import brooks.roleregistry_source_roleregistry_ws_provider.roleregistry.RoleRegistryPortType;
import brooks.roleregistry_source_roleregistry_ws_provider.roleregistry.UserRoles;
import ca.bc.gov.ag.csows.accounts.AccountFacade;
import ca.bc.gov.ag.csows.accounts.AccountFacadeBean;
import ca.bc.gov.ag.csows.accounts.ClientProfile;
import ca.bc.gov.ag.csows.accounts.NestedEjbException_Exception;
import ca.bc.gov.open.jag.efilingaccountclient.CsoAccountServiceImpl;
import ca.bc.gov.open.jag.efilingaccountclient.CsoHelpers;
import ca.bc.gov.open.jag.efilingaccountclient.mappers.AccountDetailsMapper;
import ca.bc.gov.open.jag.efilingcommons.exceptions.CSOHasMultipleAccountException;
import ca.bc.gov.open.jag.efilingcommons.exceptions.EfilingAccountServiceException;
import ca.bc.gov.open.jag.efilingcommons.model.AccountDetails;
import ca.bceid.webservices.client.v9.*;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Get Account Details Test Suite")
public class GetAccountDetailsTest {

    private static final UUID USER_GUID_NO_ROLE = UUID.randomUUID();
    private static final UUID USER_GUID_WITH_FILE_ROLE = UUID.randomUUID();
    private static final UUID USER_GUID_WITH_NO_CSO = UUID.randomUUID();
    private static final UUID USER_GUID_WITH_EJB_EXCEPTION = UUID.randomUUID();
    private static final UUID USER_GUID_WITH_MULTI_PROFILE = UUID.randomUUID();
    public static final String DOMAIN = "Courts";
    public static final String APPLICATION = "CSO";
    public static final String IDENTIFIER_TYPE = "CAP";

    CsoAccountServiceImpl sut;

    @Mock
    AccountFacade accountFacadeMock;

    @Mock
    AccountFacadeBean accountFacadeBeanMock;

    @Mock
    RoleRegistry roleRegistryMock;

    @Mock
    RoleRegistryPortType roleRegistryPortTypeMock;

    @Mock
    BCeIDService bCeIDService;

    @Mock
    BCeIDServiceSoap bCeIDServiceSoapMock;

    @Mock
    AccountDetailsMapper accountDetailsMapperMock;

    @BeforeEach
    public void init() throws NestedEjbException_Exception {

        MockitoAnnotations.initMocks(this);
        initAccountFacadeMocks();
        initRoleRegistryMocks();
        initBceIdAccountMocks();

        sut = new CsoAccountServiceImpl(accountFacadeBeanMock, roleRegistryPortTypeMock, bCeIDServiceSoapMock, accountDetailsMapperMock);
    }

    private void initAccountFacadeMocks() throws NestedEjbException_Exception {

        Mockito.when(accountFacadeMock.getAccountFacadeBeanPort()).thenReturn(accountFacadeBeanMock);

        ClientProfile profile =  new ClientProfile();
        profile.setAccountId(BigDecimal.TEN);
        profile.setClientId(BigDecimal.TEN);
        List<ClientProfile> profiles = new ArrayList<ClientProfile>();
        profiles.add(profile);

        Mockito.when(accountFacadeBeanMock.findProfiles(CsoHelpers.formatUserGuid(USER_GUID_NO_ROLE))).thenReturn(profiles);
        Mockito.when(accountFacadeBeanMock.findProfiles(CsoHelpers.formatUserGuid(USER_GUID_WITH_FILE_ROLE))).thenReturn(profiles);

        List<ClientProfile> emptyProfiles = new ArrayList<ClientProfile>();
        Mockito.when(accountFacadeBeanMock.findProfiles(CsoHelpers.formatUserGuid(USER_GUID_WITH_NO_CSO))).thenReturn(emptyProfiles);
        Mockito.when(accountFacadeBeanMock.findProfiles(CsoHelpers.formatUserGuid(USER_GUID_WITH_EJB_EXCEPTION))).thenThrow(new NestedEjbException_Exception("random"));

        List<ClientProfile> multiProfiles = new ArrayList<>();
        multiProfiles.add(profile);
        multiProfiles.add(profile);
        Mockito.when(accountFacadeBeanMock.findProfiles(CsoHelpers.formatUserGuid(USER_GUID_WITH_MULTI_PROFILE))).thenReturn(multiProfiles);

    }

    private void initRoleRegistryMocks() {

        RegisteredRole fileRole = new RegisteredRole();
        fileRole.setCode("FILE");
        UserRoles userRolesWithFileRole = new UserRoles();
        userRolesWithFileRole.getRoles().add(fileRole);

        RegisteredRole notFileRole = new RegisteredRole();
        notFileRole.setCode("NOTFILE");
        UserRoles userRolesWithoutFileRole = new UserRoles();
        userRolesWithoutFileRole.getRoles().add(notFileRole);

        Mockito.when(roleRegistryMock.getRoleRegistrySourceRoleRegistryWsProviderRoleRegistryPort()).thenReturn(roleRegistryPortTypeMock);
        Mockito.when(roleRegistryPortTypeMock.getRolesForIdentifier(DOMAIN, APPLICATION, CsoHelpers.formatUserGuid(USER_GUID_WITH_FILE_ROLE), IDENTIFIER_TYPE)).thenReturn(userRolesWithFileRole);
        Mockito.when(roleRegistryPortTypeMock.getRolesForIdentifier(DOMAIN, APPLICATION, CsoHelpers.formatUserGuid(USER_GUID_NO_ROLE), IDENTIFIER_TYPE)).thenReturn(userRolesWithoutFileRole);

        AccountDetails csoUserDetailsWithRole = new AccountDetails(UUID.randomUUID(), BigDecimal.TEN, BigDecimal.TEN, true, "firstName", "lastName", "middleName", "email");
        Mockito.when(accountDetailsMapperMock.toAccountDetails(Mockito.any(), Mockito.any(), Mockito.eq(true))).thenReturn(csoUserDetailsWithRole);

        AccountDetails csoUserDetailsWithoutRole = new AccountDetails(UUID.randomUUID(),BigDecimal.TEN, BigDecimal.TEN, false, "firstName", "lastName", "middleName","email");
        Mockito.when(accountDetailsMapperMock.toAccountDetails(Mockito.any(), Mockito.any(), Mockito.eq(false))).thenReturn(csoUserDetailsWithoutRole);
    }

    private void initBceIdAccountMocks() {

        BCeIDAccountContact contact = new BCeIDAccountContact();
        BCeIDString str = new BCeIDString();
        str.setValue("email@email.com");
        contact.setEmail(str);

        BCeIDName name = new BCeIDName();
        str.setValue("first");
        name.setFirstname(str);
        str.setValue("middle");
        name.setMiddleName(str);
        str.setValue("surname");
        name.setSurname(str);

        BCeIDIndividualIdentity identity = new BCeIDIndividualIdentity();
        identity.setName(name);

        BCeIDAccount bCeIDAccount = new BCeIDAccount();
        bCeIDAccount.setContact(contact);
        bCeIDAccount.setIndividualIdentity(identity);

        AccountDetailResponse bCeIDResponse = new AccountDetailResponse();
        bCeIDResponse.setCode(ResponseCode.SUCCESS);
        bCeIDResponse.setAccount(bCeIDAccount);

        Mockito.when(bCeIDService.getBCeIDServiceSoap()).thenReturn(bCeIDServiceSoapMock);
        Mockito.when(bCeIDServiceSoapMock.getAccountDetail(any())).thenReturn(bCeIDResponse);

        AccountDetails accountDetailsWithNoCso = new AccountDetails(UUID.randomUUID(), BigDecimal.ZERO, BigDecimal.ZERO, false, "firstName", "lastName", "middleName","email");
        Mockito.when(accountDetailsMapperMock.toAccountDetails(Mockito.any(), Mockito.any())).thenReturn(accountDetailsWithNoCso);
    }

    @DisplayName("OK: getAccountDetails called with userGuid with file role")
    @Test
    public void testWithFileRoleEnabled() throws NestedEjbException_Exception {

        AccountDetails details = sut.getAccountDetails(USER_GUID_WITH_FILE_ROLE, "");
        Assertions.assertNotEquals(null, details);
        Assertions.assertEquals(BigDecimal.TEN, details.getAccountId());
        Assertions.assertEquals(BigDecimal.TEN, details.getClientId());
        Assertions.assertEquals(true, details.isFileRolePresent());
        Assertions.assertEquals("firstName", details.getFirstName());
        Assertions.assertEquals("lastName", details.getLastName());
        Assertions.assertEquals("email", details.getEmail());

    }

    @DisplayName("OK: getAccountDetails called with a userGuid that does not have file role")
    @Test
    public void testWithFileRoleDisabled() throws NestedEjbException_Exception {

        AccountDetails details = sut.getAccountDetails(USER_GUID_NO_ROLE, "");
        Assertions.assertNotEquals(null, details);
        Assertions.assertEquals(BigDecimal.TEN, details.getAccountId());
        Assertions.assertEquals(BigDecimal.TEN, details.getClientId());
        Assertions.assertEquals(false, details.isFileRolePresent());
        Assertions.assertEquals("firstName", details.getFirstName());
        Assertions.assertEquals("lastName", details.getLastName());
        Assertions.assertEquals("email", details.getEmail());

    }

    @DisplayName("OK: getAccountDetails called with a userGuid that does not have cso account")
    @Test
    public void withNoCsoAccountShouldReturnBceidUser() throws NestedEjbException_Exception {

        AccountDetails details = sut.getAccountDetails(USER_GUID_WITH_NO_CSO, "Individual");
        Assertions.assertNotEquals(null, details);
        Assertions.assertEquals(BigDecimal.ZERO, details.getAccountId());
        Assertions.assertEquals(BigDecimal.ZERO, details.getClientId());
        Assertions.assertEquals(false, details.isFileRolePresent());
        Assertions.assertEquals("firstName", details.getFirstName());
        Assertions.assertEquals("lastName", details.getLastName());
        Assertions.assertEquals("email", details.getEmail());
    }

    @DisplayName("Exception: with NestedEjbException_Exception should throw EfilingAccountServiceException")
    @Test
    public void withNestedEjbExceptionExceptionShouldThrowEfilingAccountServiceException() {


        EfilingAccountServiceException actual = Assertions.assertThrows(EfilingAccountServiceException.class, () -> {
            sut.getAccountDetails(USER_GUID_WITH_EJB_EXCEPTION, "Individual");
        });

        Assertions.assertEquals(NestedEjbException_Exception.class, actual.getCause().getClass());
        Assertions.assertEquals("Exception while fetching account details", actual.getMessage());

    }

    @DisplayName("Exception: with multiprofiles should throw CSOHasMultipleAccountException")
    @Test
    public void withMultiProflieShouldThrowCSOHasMultipleAccountException() {


        CSOHasMultipleAccountException actual = Assertions.assertThrows(CSOHasMultipleAccountException.class, () -> {
            sut.getAccountDetails(USER_GUID_WITH_MULTI_PROFILE, "Individual");
        });

        Assertions.assertEquals("Client 10 has multiple CSO profiles", actual.getMessage());

    }




}
