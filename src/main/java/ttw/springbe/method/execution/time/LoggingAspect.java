package ttw.springbe.method.execution.time;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.util.StopWatch;

import java.util.Properties;

@Aspect
public class LoggingAspect {

    Logger log = LoggerFactory.getLogger(LoggingAspect.class);

    @Around("execution(* ttw.springbe.method.execution.time.services.*.*(..))")
    public Object logExecutionTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object result = null;

        if (isLoggingExecutionTimeEnabled()) {
            try {
                MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();

                //Get intercepted method details
                String className = methodSignature.getDeclaringType().getSimpleName();
                String methodName = methodSignature.getName();

                final StopWatch stopWatch = new StopWatch();

                //Measure method execution time
                stopWatch.start();
                result = proceedingJoinPoint.proceed();
                stopWatch.stop();

                //Log method execution time
                log.info("Execution time of " + className + "." + methodName + " :: " + stopWatch.getTotalTimeMillis() + " ms");
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
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
            log.error(e.getMessage(), e);
        }
        return isLoggingExecutionTimeEnabled;
    }
}
