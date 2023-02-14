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
        try {
            Resource resource = new ClassPathResource("application.properties");
            Properties props = PropertiesLoaderUtils.loadProperties(resource);
            if ("enabled".equalsIgnoreCase(props.getProperty("logging.execution.time"))) {
                isLoggingExecutionTimeEnabled = true;
            }
        } catch (Exception e) {
            log.error("Gagal read logging.execution.time", e);
        }
        return isLoggingExecutionTimeEnabled;
    }
}
