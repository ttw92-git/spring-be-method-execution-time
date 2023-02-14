package ttw.springbe.method.execution.time;

import lombok.Data;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

@Data
public class ExecutionTimeStopWatch {

    private Logger log = LoggerFactory.getLogger(ExecutionTimeStopWatch.class);

    private StopWatch stopWatch;
    private boolean isSuccessToStartStopWatch;
    private String loggingMessage;

    public ExecutionTimeStopWatch(){
        this.stopWatch = new StopWatch();
        this.isSuccessToStartStopWatch = false;
        this.loggingMessage = "";
    }

    public boolean startStopWatch(ProceedingJoinPoint proceedingJoinPoint) {
        try {
            MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();

            //Get intercepted method details
            String className = methodSignature.getDeclaringType().getSimpleName();
            String methodName = methodSignature.getName();
            this.loggingMessage += "Execution time of " + className + "." + methodName + " :: ";

            //Measure method execution time
            this.stopWatch.start();
            this.isSuccessToStartStopWatch = true;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return this.isSuccessToStartStopWatch;
    }

    public void stopStopWatch() {
        if (this.isSuccessToStartStopWatch) {
            try {
                this.stopWatch.stop();

                //Log method execution time
                this.loggingMessage += stopWatch.getTotalTimeMillis() + " ms";
                log.info(loggingMessage);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
    }
}
