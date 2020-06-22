package ca.bc.gov.open.jag.efilinglookupclient.config;

import ca.bc.gov.ag.csows.LookupsLookupFacade;
import ca.bc.gov.open.jag.efilinglookupclient.CSOLookupServiceImpl;
import ca.bc.gov.open.jag.efilinglookupclient.EfilingLookupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.namespace.QName;
import java.net.MalformedURLException;
import java.net.URL;

@Configuration
@EnableConfigurationProperties(CSOLookupProperties.class)
public class AutoConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(AutoConfiguration.class);
    private final CSOLookupProperties csoLookupProperties;

    public AutoConfiguration(CSOLookupProperties csoLookupProperties) {

        this.csoLookupProperties = csoLookupProperties;
    }


    public LookupsLookupFacade lookupsLookupFacade() {

        LookupsLookupFacade lookupsLookupFacade = null;

        try {

            QName serviceName = new QName("http://ag.gov.bc.ca/csows", "lookups.LookupFacade");
            URL url = new URL(csoLookupProperties.getFilingLookupSoapUri());
            lookupsLookupFacade = new LookupsLookupFacade(url, serviceName);

        } catch(MalformedURLException e) {

            LOGGER.error("Malformed URL exception :" + e.getMessage());

        }

        return lookupsLookupFacade;

    }

    @Bean
    @ConditionalOnMissingBean({EfilingLookupService.class})
    public EfilingLookupService efilingLookupService() {
        return new CSOLookupServiceImpl(lookupsLookupFacade());
    }

}
