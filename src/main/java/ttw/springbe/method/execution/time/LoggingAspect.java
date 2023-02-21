package ttw.springbe.method.execution.time;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.util.Properties;

@Aspect
public class LoggingAspect {

    Logger log = LoggerFactory.getLogger(LoggingAspect.class);

    @Around("execution(* ttw.springbe.method.execution.time.services.*.*(..))")
    public Object logExecutionTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object result = null;

        if (isLoggingExecutionTimeEnabled()) {
            ExecutionTimeStopWatch executionTimeStopwatch = new ExecutionTimeStopWatch();
            executionTimeStopwatch.startStopWatch(proceedingJoinPoint);
            result = proceedingJoinPoint.proceed();
            executionTimeStopwatch.stopStopWatch();
        } else {
            result = proceedingJoinPoint.proceed();
        }
        return result;
    }

    private boolean isLoggingExecutionTimeEnabled() {
        boolean isLoggingExecutionTimeEnabled = false;
        if ("true".equalsIgnoreCase(getPropertiesValueByName("logging.execution.time.enabled"))) {
            isLoggingExecutionTimeEnabled = true;
        }
        return isLoggingExecutionTimeEnabled;
    }

    private String getPropertiesValueByName(String propertiesName) {
        String propertiesValue = "";

        try {
            String propertiesAddressInOpenshift = "file:///config/application.properties";
            Resource resource = new ClassPathResource(propertiesAddressInOpenshift);

            if (!resource.exists()) {
                resource = new ClassPathResource("application.properties");
                log.debug("Properties in " + propertiesAddressInOpenshift + " not found, read default properties");
            }
            Properties props = PropertiesLoaderUtils.loadProperties(resource);
            propertiesValue = props.getProperty(propertiesName);
            log.debug("Value for " + propertiesName + " is " + propertiesValue);
        } catch (Exception e) {
            log.debug("Gagal read properties, return empty string", e);
        }

        return propertiesValue;
    }
}
