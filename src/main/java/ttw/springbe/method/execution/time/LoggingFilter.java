//package ttw.springbe.method.execution.time;
//
//import ch.qos.logback.classic.spi.ILoggingEvent;
//import ch.qos.logback.core.filter.Filter;
//import ch.qos.logback.core.spi.FilterReply;
//
//public class LoggingFilter extends Filter<ILoggingEvent> {
//    @Override
//    public FilterReply decide(ILoggingEvent iLoggingEvent) {
//        System.out.println(iLoggingEvent.);
//        if (iLoggingEvent.getMessage().contains("LoggingAspect")) {
//            return FilterReply.ACCEPT;
//        } else {
//            return FilterReply.NEUTRAL;
//        }
//    }
//}
